// port-lint: source formatters/terse.rs
package io.github.kotlinmania.rustlang.test.formatters.terse

import io.github.kotlinmania.rustlang.core.fmt.Write
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.test.NamePadding
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.bench.fmtBenchSamples
import io.github.kotlinmania.rustlang.test.console.ConsoleTestDiscoveryState
import io.github.kotlinmania.rustlang.test.console.ConsoleTestState
import io.github.kotlinmania.rustlang.test.console.OutputLocation
import io.github.kotlinmania.rustlang.test.term.color.CYAN
import io.github.kotlinmania.rustlang.test.term.color.Color
import io.github.kotlinmania.rustlang.test.term.color.GREEN
import io.github.kotlinmania.rustlang.test.term.color.RED
import io.github.kotlinmania.rustlang.test.term.color.YELLOW
import io.github.kotlinmania.rustlang.test.testresult.TestResult
import io.github.kotlinmania.rustlang.test.time.TEST_WARN_TIMEOUT_S
import io.github.kotlinmania.rustlang.test.time.TestExecTime
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

// We insert a '\n' when the output hits 100 columns in quiet mode. 88 test
// result chars leaves 12 chars for a progress count like " 11704/12853".
public val QUIET_MODE_MAX_COLUMN: Int = 88

public open class TerseFormatter<T>(
    public var out: OutputLocation<T>,
    public var useColor: Boolean,
    public var isMultithreaded: Boolean,
    /**
     * Number of columns to fill when aligning names
     */
    public var maxNameLen: Int,
    public var testCount: Int,
    public var testColumn: Int,
    public var totalTestCount: Int,
) {
    public open fun writeOk(): Result<Unit> = this.writeShortResult(".", GREEN)

    public open fun writeFailed(name: String): Result<Unit> {
        // Put failed tests on their own line and include the test name, so that it's faster
        // to see which test failed without having to wait for them all to run.
        // normally, we write the progress unconditionally, even if the previous line was cut short.
        // but if this is the very first column, no short results will have been printed and we'll end up with *only* the progress on the line.
        // avoid this.
        if (this.testColumn != 0) {
            when (val tryResult = this.writeProgress()) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        }
        this.testCount += 1
        when (val tryResult = this.writePlain("$name --- ")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        when (val tryResult = this.writePretty("FAILED", RED)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        return this.writePlain("\n")
    }

    public open fun writeIgnored(): Result<Unit> = this.writeShortResult("i", YELLOW)

    public open fun writeBench(): Result<Unit> = this.writePretty("bench", CYAN)

    public open fun writeShortResult(result: String, color: Color): Result<Unit> {
        when (val tryResult = this.writePretty(result, color)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        this.testCount += 1
        this.testColumn += 1
        if (this.testColumn % QUIET_MODE_MAX_COLUMN == QUIET_MODE_MAX_COLUMN - 1) {
            // We insert a new line regularly in order to flush the
            // screen when dealing with line-buffered output (e.g., piping to
            // `stamp` in the Rust CI).
            when (val tryResult = this.writeProgress()) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        }
        return RustResult.Ok(Unit)
    }

    public open fun writeProgress(): Result<Unit> {
        val out = " ${this.testCount}/${this.totalTestCount}\n"
        when (val tryResult = this.writePlain(out)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        this.testColumn = 0
        return RustResult.Ok(Unit)
    }

    public open fun writePretty(word: String, color: Color): Result<Unit> {
        return when (val matchVal = this.out) {
            is OutputLocation.Pretty<*> -> {
                val (term) = matchVal
                if (this.useColor) {
                    when (val tryResult = term.fg(color)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
                }
                when (val tryResult = term.writeAll(word.encodeToByteArray().map { it.toUByte() })) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
                if (this.useColor) {
                    when (val tryResult = term.reset()) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
                }
                term.flush()
            }
            is OutputLocation.Raw<*> -> {
                val (stdout) = matchVal
                when (val tryResult = stdout.writeAll(word.encodeToByteArray().map { it.toUByte() })) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
                stdout.flush()
            }
        }
    }

    public open fun writePlain(s: String): Result<Unit> {
        val s1 = s
        when (val tryResult = this.out.writeAll(s1.encodeToByteArray().map { it.toUByte() })) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        return this.out.flush()
    }

    public open fun writeOutputs(state: ConsoleTestState): Result<Unit> {
        when (val tryResult = this.writePlain("\nsuccesses:\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        val successes = mutableListOf()
        var stdouts = ""
        for ((f, stdout) in state.notFailures) {
            successes.add(f.name.toString())
            if (!stdout.isEmpty()) {
                stdouts += "---- ${f.name} stdout ----\n"
                val output = String.fromUtf8Lossy(stdout)
                stdouts += output
                stdouts += '\n'
            }
        }
        if (!stdouts.isEmpty()) {
            when (val tryResult = this.writePlain("\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
            when (val tryResult = this.writePlain(stdouts)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        }
        when (val tryResult = this.writePlain("\nsuccesses:\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        successes.sort()
        for (name in successes) {
            when (val tryResult = this.writePlain("    $name\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        }
        return RustResult.Ok(Unit)
    }

    public open fun writeFailures(state: ConsoleTestState): Result<Unit> {
        when (val tryResult = this.writePlain("\nfailures:\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        val failures = mutableListOf()
        var failOut = ""
        for ((f, stdout) in state.failures) {
            failures.add(f.name.toString())
            if (!stdout.isEmpty()) {
                failOut += "---- ${f.name} stdout ----\n"
                val output = String.fromUtf8Lossy(stdout)
                failOut += output
                failOut += '\n'
            }
        }
        if (!failOut.isEmpty()) {
            when (val tryResult = this.writePlain("\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
            when (val tryResult = this.writePlain(failOut)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        }
        when (val tryResult = this.writePlain("\nfailures:\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        failures.sort()
        for (name in failures) {
            when (val tryResult = this.writePlain("    $name\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
        }
        return RustResult.Ok(Unit)
    }

    public open fun writeTestName(desc: TestDesc): Result<Unit> {
        val name = desc.paddedName(this.maxNameLen, desc.name.padding())
        when (val matchVal = desc.testMode()) {
            null -> {
                when (val tryResult = this.writePlain("test $name ... ")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
            }
            else -> {
                val testMode = matchVal
                when (val tryResult = this.writePlain("test $name - $testMode ... ")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
            }
        }
        return RustResult.Ok(Unit)
    }

    public companion object {
        public fun <T> new(out: OutputLocation<T>, useColor: Boolean, maxNameLen: Int, isMultithreaded: Boolean): TerseFormatter<T> = TerseFormatter(out = out, useColor = useColor, maxNameLen = maxNameLen, isMultithreaded = isMultithreaded, testCount = 0, testColumn = 0, totalTestCount = 0)
    }
}

public fun <T : Write> TerseFormatter<T>.writeDiscoveryStart(): Result<Unit> = RustResult.Ok(Unit)

public fun <T : Write> TerseFormatter<T>.writeTestDiscovered(desc: TestDesc, testType: String): Result<Unit> = this.writePlain("${desc.name}: $testType\n")

public fun <T : Write> TerseFormatter<T>.writeDiscoveryFinish(state: ConsoleTestDiscoveryState): Result<Unit> = RustResult.Ok(Unit)

public fun <T : Write> TerseFormatter<T>.writeRunStart(testCount: Int, shuffleSeed: ULong?): Result<Unit> {
    this.totalTestCount = testCount
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

public fun <T : Write> TerseFormatter<T>.writeTestStart(desc: TestDesc): Result<Unit> {
    // Remnants from old libtest code that used the padding value
    // in order to indicate benchmarks.
    // When running benchmarks, terse-mode should still print their name as if
    // it is the Pretty formatter.
    if (!this.isMultithreaded && desc.name.padding() == NamePadding.PadOnRight) {
        when (val tryResult = this.writeTestName(desc)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
    }
    return RustResult.Ok(Unit)
}

public fun <T : Write> TerseFormatter<T>.writeResult(desc: TestDesc, result: TestResult, arg2: TestExecTime?, arg3: List<UByte>, arg4: ConsoleTestState): Result<Unit> {
    return when (result) {
        TestResult.TrOk -> this.writeOk()
        TestResult.TrFailed -> {
            this.writeFailed(desc.name.asSlice())
        }
        is TestResult.TrFailedMsg -> {
            this.writeFailed(desc.name.asSlice())
        }
        TestResult.TrTimedFail -> {
            this.writeFailed(desc.name.asSlice())
        }
        TestResult.TrIgnored -> this.writeIgnored()
        is TestResult.TrBench -> {
            val (bs) = result
            if (this.isMultithreaded) {
                when (val tryResult = this.writeTestName(desc)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
            }
            when (val tryResult = this.writeBench()) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
            this.writePlain(": ${fmtBenchSamples(bs)}\n")
        }
    }
}

public fun <T : Write> TerseFormatter<T>.writeTimeout(desc: TestDesc): Result<Unit> = this.writePlain("test ${desc.name} has been running for over $TEST_WARN_TIMEOUT_S seconds\n")

public fun <T : Write> TerseFormatter<T>.writeRunFinish(state: ConsoleTestState): Result<Boolean> {
    if (state.options.displayOutput) {
        when (val tryResult = this.writeOutputs(state)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Boolean, Error>(tryResult.error) }
    }
    val success = state.failed == 0
    if (!success) {
        when (val tryResult = this.writeFailures(state)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Boolean, Error>(tryResult.error) }
    }
    when (val tryResult = this.writePlain("\ntest result: ")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Boolean, Error>(tryResult.error) }
    if (success) {
        // There's no parallelism at this point so it's safe to use color
        when (val tryResult = this.writePretty("ok", GREEN)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Boolean, Error>(tryResult.error) }
    } else {
        when (val tryResult = this.writePretty("FAILED", RED)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Boolean, Error>(tryResult.error) }
    }
    val s = ". ${state.passed} passed; ${state.failed} failed; ${state.ignored} ignored; ${state.measured} measured; ${state.filteredOut} filtered out"
    when (val tryResult = this.writePlain(s)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Boolean, Error>(tryResult.error) }
    when (val matchVal = state.execTime) {
        null -> {}
        else -> {
            val execTime = matchVal
            val timeStr = "; finished in $execTime"
            when (val tryResult = this.writePlain(timeStr)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Boolean, Error>(tryResult.error) }
        }
    }
    when (val tryResult = this.writePlain("\n\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Boolean, Error>(tryResult.error) }
    // Custom handling of cases where there is only 1 test to execute and that test was ignored.
    // We want to show more detailed information(why was the test ignored) for investigation purposes.
    if (this.totalTestCount == 1 && state.ignores.len() == 1) {
        val testDesc = state.ignores[0].first
        when (val matchVal1 = testDesc.ignoreMessage) {
            null -> {}
            else -> {
                val im = matchVal1
                when (val tryResult = this.writePlain("test: ${testDesc.name}, ignore_message: $im\n\n")) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Boolean, Error>(tryResult.error) }
            }
        }
    }
    return RustResult.Ok(success)
}

public fun <T : Write> TerseFormatter<T>.writeMergedDoctestsTimes(totalTime: Double, compilationTime: Double): Result<Unit> = this.writePlain("all doctests ran in ${totalTime}s; merged doctests compilation took ${compilationTime}s\n")

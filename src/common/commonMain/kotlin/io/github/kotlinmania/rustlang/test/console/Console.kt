// port-lint: source console.rs
package io.github.kotlinmania.rustlang.test.console

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.fmt.Write
import io.github.kotlinmania.rustlang.core.fmt.write
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.time.Instant
import io.github.kotlinmania.rustlang.test.TestFn.*
import io.github.kotlinmania.rustlang.test.bench.fmtBenchSamples
import io.github.kotlinmania.rustlang.test.cli.TestOpts
import io.github.kotlinmania.rustlang.test.event.CompletedTest
import io.github.kotlinmania.rustlang.test.event.TestEvent
import io.github.kotlinmania.rustlang.test.filterTests
import io.github.kotlinmania.rustlang.test.formatters.JsonFormatter
import io.github.kotlinmania.rustlang.test.formatters.JunitFormatter
import io.github.kotlinmania.rustlang.test.formatters.OutputFormatter
import io.github.kotlinmania.rustlang.test.formatters.PrettyFormatter
import io.github.kotlinmania.rustlang.test.formatters.TerseFormatter
import io.github.kotlinmania.rustlang.test.helpers.concurrency.getConcurrency
import io.github.kotlinmania.rustlang.test.helpers.metrics.MetricMap
import io.github.kotlinmania.rustlang.test.options.Options
import io.github.kotlinmania.rustlang.test.options.OutputFormat
import io.github.kotlinmania.rustlang.test.runTests
import io.github.kotlinmania.rustlang.test.term.StdoutTerminal
import io.github.kotlinmania.rustlang.test.term.stdout
import io.github.kotlinmania.rustlang.test.testresult.TestResult
import io.github.kotlinmania.rustlang.test.time.TestExecTime
import io.github.kotlinmania.rustlang.test.time.TestSuiteExecTime
import io.github.kotlinmania.rustlang.test.types.NamePadding
import io.github.kotlinmania.rustlang.test.types.TestDesc
import io.github.kotlinmania.rustlang.test.types.TestDescAndFn
import io.github.kotlinmania.rustlang.test.types.TestList
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

// Module providing interface for running tests in the console.

/**
 * Generic wrapper over stdout.
 */
public sealed class OutputLocation<T> {
    public data class Pretty<T>(public var value: StdoutTerminal) : OutputLocation<T>()
    public data class Raw<T>(public var value: T) : OutputLocation<T>()
}

public fun <T : Write> OutputLocation<T>.write(buf: List<UByte>): Result<Int> =
    when (this) {
        is OutputLocation.Pretty<*> -> {
            val (term) = this as OutputLocation.Pretty<T>
            term.write(buf)
        }
        is OutputLocation.Raw<*> -> {
            val (stdout) = this as OutputLocation.Raw<T>
            stdout.write(buf)
        }
    }

public fun <T : Write> OutputLocation<T>.flush(): Result<Unit> =
    when (this) {
        is OutputLocation.Pretty<*> -> {
            val (term) = this as OutputLocation.Pretty<T>
            term.flush()
        }
        is OutputLocation.Raw<*> -> {
            val (stdout) = this as OutputLocation.Raw<T>
            stdout.flush()
        }
    }

public open class ConsoleTestDiscoveryState(
    public var logOut: File?,
    public var tests: Int,
    public var benchmarks: Int,
    public var ignored: Int,
) {
    public open fun writeLog(msg: () -> S): Result<Unit> =
        when (val matchVal = this.logOut) {
            null -> RustResult.Ok(Unit)
            else -> {
                val o = matchVal
                val msg1 = msg()
                val msg2 = msg1.asRef()
                o.writeAll(msg2.encodeToByteArray().map { it.toUByte() })
            }
        }

    public companion object {
        public fun new(opts: TestOpts): Result<ConsoleTestDiscoveryState> {
            val logOut =
                when (val matchVal = opts.logfile) {
                    null -> null
                    else -> {
                        val path = matchVal
                        when (val tryResult = File.create(path)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ConsoleTestDiscoveryState, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                    }
                }
            return RustResult.Ok(ConsoleTestDiscoveryState(logOut = logOut, tests = 0, benchmarks = 0, ignored = 0))
        }
    }
}

public open class ConsoleTestState(
    public var logOut: File?,
    public var total: Int,
    public var passed: Int,
    public var failed: Int,
    public var ignored: Int,
    public var filteredOut: Int,
    public var measured: Int,
    public var execTime: TestSuiteExecTime?,
    public var metrics: MetricMap,
    public var failures: List<Pair<TestDesc, List<UByte>>>,
    public var notFailures: List<Pair<TestDesc, List<UByte>>>,
    public var ignores: List<Pair<TestDesc, List<UByte>>>,
    public var timeFailures: List<Pair<TestDesc, List<UByte>>>,
    public var options: Options,
) {
    public open fun writeLog(msg: () -> S): Result<Unit> =
        when (val matchVal = this.logOut) {
            null -> RustResult.Ok(Unit)
            else -> {
                val o = matchVal
                val msg1 = msg()
                val msg2 = msg1.asRef()
                o.writeAll(msg2.encodeToByteArray().map { it.toUByte() })
            }
        }

    public open fun writeLogResult(test: TestDesc, result: TestResult, execTime: TestExecTime?): Result<Unit> {
        when (val tryResult = this.writeLog({
            val name = test.name
            val ignoreMessage = test.ignoreMessage
            (when (result) {
                TestResult.TrOk -> "ok".toOwned()
                TestResult.TrFailed -> "failed".toOwned()
                is TestResult.TrFailedMsg -> {
                    val (msg) = result
                    "failed: $msg"
                }
                TestResult.TrIgnored -> {
                    if (ignoreMessage != null) {
                        "ignored: $msg"
                    } else {
                        "ignored".toOwned()
                    }
                }
                is TestResult.TrBench -> {
                    val (bs) = result
                    fmtBenchSamples(bs)
                }
                TestResult.TrTimedFail -> "failed (time limit exceeded)".toOwned()
            }).toString() + " " + (name).toString()
        })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        if (execTime != null) {
            when (val tryResult = this.writeLog({ " <$execTime>" })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        return this.writeLog({ "\n" })
    }

    public open fun currentTestCount(): Int = this.passed + this.failed + this.ignored + this.measured

    public companion object {
        public fun new(opts: TestOpts): Result<ConsoleTestState> {
            val logOut =
                when (val matchVal = opts.logfile) {
                    null -> null
                    else -> {
                        val path = matchVal
                        when (val tryResult = File.create(path)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ConsoleTestState, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                    }
                }
            return RustResult.Ok(ConsoleTestState(logOut = logOut, total = 0, passed = 0, failed = 0, ignored = 0, filteredOut = 0, measured = 0, execTime = null, metrics = MetricMap.new(), failures = mutableListOf(), notFailures = mutableListOf(), ignores = mutableListOf(), timeFailures = mutableListOf(), options = opts.options))
        }
    }
}

// List the tests to console, and optionally to logfile. Filters are honored.
public fun listTestsConsole(opts: TestOpts, tests: TestList): Result<Unit> {
    val output =
        when (val matchVal = stdout()) {
            null -> OutputLocation.Raw(io.github.kotlinmania.rustlang.std.io.stdio.stdout().lock())
            else -> {
                val t = matchVal
                OutputLocation.Pretty(t)
            }
        }
    var out: OutputFormatter =
        when (opts.format) {
            OutputFormat.Pretty, OutputFormat.Junit -> {
                PrettyFormatter.new(output, false, 0, false, null)
            }
            OutputFormat.Terse -> TerseFormatter.new(output, false, 0, false)
            OutputFormat.Json -> JsonFormatter.new(output)
        }
    val st = when (val tryResult = ConsoleTestDiscoveryState.new(opts)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    when (val tryResult = out.writeDiscoveryStart()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    for (test in filterTests(opts, tests)) {
        val desc = test.desc
        val testfn = test.testfn
        val fntype =
            when (testfn) {
                is StaticTestFn -> {
                    st.tests += 1
                    "test"
                }
                is DynTestFn -> {
                    st.tests += 1
                    "test"
                }
                is StaticBenchAsTestFn -> {
                    st.tests += 1
                    "test"
                }
                is DynBenchAsTestFn -> {
                    st.tests += 1
                    "test"
                }
                is StaticBenchFn -> {
                    st.benchmarks += 1
                    "benchmark"
                }
                is DynBenchFn -> {
                    st.benchmarks += 1
                    "benchmark"
                }
            }
        st.ignored +=
            if (desc.ignore) {
                1
            } else {
                0
            }
        when (val tryResult = out.writeTestDiscovered(desc, fntype)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        when (val tryResult = st.writeLog({ "$fntype ${desc.name}\n" })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    }
    return out.writeDiscoveryFinish(st)
}

// Updates `ConsoleTestState` depending on result of the test execution.
public fun handleTestResult(st: ConsoleTestState, completedTest: CompletedTest) {
    val test = completedTest.desc
    val stdout = completedTest.stdout
    when (val matchVal = completedTest.result) {
        TestResult.TrOk -> {
            st.passed += 1
            st.notFailures.push(Pair(test, stdout))
        }
        TestResult.TrIgnored -> {
            st.ignored += 1
            st.ignores.push(Pair(test, stdout))
        }
        is TestResult.TrBench -> {
            val (bs) = matchVal
            st.metrics.insertMetric(test.name.asSlice(), bs.nsIterSumm.median, bs.nsIterSumm.max - bs.nsIterSumm.min)
            st.measured += 1
        }
        TestResult.TrFailed -> {
            st.failed += 1
            st.failures.push(Pair(test, stdout))
        }
        is TestResult.TrFailedMsg -> {
            val (msg) = matchVal
            st.failed += 1
            var stdout1 = stdout
            stdout1.extendFromSlice(("note: $msg").encodeToByteArray().map { it.toUByte() })
            st.failures.push(Pair(test, stdout1))
        }
        TestResult.TrTimedFail -> {
            st.failed += 1
            st.timeFailures.push(Pair(test, stdout))
        }
    }
}

// Handler for events that occur during test execution.
// It is provided as a callback to the `runTests` function.
public fun onTestEvent(event: TestEvent, st: ConsoleTestState, out: OutputFormatter): Result<Unit> {
    when (val matchVal = event.clone()) {
        is TestEvent.TeFiltered -> {
            val (filteredTests, shuffleSeed) = matchVal
            st.total = filteredTests
            when (val tryResult = out.writeRunStart(filteredTests, shuffleSeed)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        is TestEvent.TeFilteredOut -> {
            val (filteredOut) = matchVal
            st.filteredOut = filteredOut
        }
        is TestEvent.TeWait -> {
            val (test) = matchVal
            when (val tryResult = out.writeTestStart(test)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        is TestEvent.TeTimeout -> {
            val (test1) = matchVal
            when (val tryResult = out.writeTimeout(test1)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        is TestEvent.TeResult -> {
            val (completedTest) = matchVal
            val test2 = completedTest.desc
            val result = completedTest.result
            val execTime = completedTest.execTime
            val stdout = completedTest.stdout
            when (val tryResult = st.writeLogResult(test2, result, execTime.asRef())) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            when (val tryResult = out.writeResult(test2, result, execTime.asRef(), stdout, st)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            handleTestResult(st, completedTest)
        }
    }
    return RustResult.Ok(Unit)
}

public fun getFormatter(opts: TestOpts, maxNameLen: Int): OutputFormatter {
    val output =
        when (val matchVal = stdout()) {
            null -> OutputLocation.Raw(io.github.kotlinmania.rustlang.std.io.stdio.stdout())
            else -> {
                val t = matchVal
                OutputLocation.Pretty(t)
            }
        }
    val isMultithreaded = opts.testThreads.unwrapOrElse(getConcurrency) > 1
    return when (opts.format) {
        OutputFormat.Pretty -> PrettyFormatter.new(output, opts.useColor(), maxNameLen, isMultithreaded, opts.timeOptions)
        OutputFormat.Terse -> {
            TerseFormatter.new(output, opts.useColor(), maxNameLen, isMultithreaded)
        }
        OutputFormat.Json -> JsonFormatter.new(output)
        OutputFormat.Junit -> JunitFormatter.new(output)
    }
}

/**
 * A simple console test runner.
 * Runs provided tests reporting process and results to the stdout.
 */
public fun runTestsConsole(opts: TestOpts, tests: TestList): Result<Boolean> {
    val allTestLen = tests.tests.len()
    val filteredTests = filterTests(opts, tests)
    // Only iterate the filtered tests: only those are actually printed, and also the
    // full list can be very long and we want to avoid ever iterating that list in Miri.
    val maxNameLen = filteredTests.iter().maxByKey({ t -> lenIfPadded(t) }).map({ t -> t.desc.name.asSlice().len() }).unwrapOr(0)
    val out = getFormatter(opts, maxNameLen)
    val st = when (val tryResult = ConsoleTestState.new(opts)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    // Prevent the usage of `Instant` in some cases:
    // - It's currently not supported for wasm targets without Emscripten nor WASI.
    // - It's currently not supported for zkvm targets.
    val isInstantUnsupported = (false && false) || false
    val startTime = (!isInstantUnsupported).then(Instant::now)
    when (val tryResult = runTests(opts, filteredTests, allTestLen, { x -> onTestEvent(x, st, out) })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    st.execTime = startTime.map({ t -> TestSuiteExecTime(t.elapsed()) })
    check(opts.failFast || st.currentTestCount() == st.total)
    return out.writeRunFinish(st)
}

// Calculates padding for given test description.
public fun lenIfPadded(t: TestDescAndFn): Int =
    when (t.testfn.padding()) {
        NamePadding.PadNone -> 0
        NamePadding.PadOnRight -> t.desc.name.asSlice().len()
    }

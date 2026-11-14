// port-lint: source test_result.rs
package io.github.kotlinmania.rustlang.test.testresult

import io.github.kotlinmania.libc.fuchsia.SIGABRT
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.result.orElse
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.std.process.ExitStatus
import io.github.kotlinmania.rustlang.test.bench.BenchSamples
import io.github.kotlinmania.rustlang.test.options.ShouldPanic
import io.github.kotlinmania.rustlang.test.time.TestExecTime
import io.github.kotlinmania.rustlang.test.time.TestTimeOptions
import io.github.kotlinmania.rustlang.test.types.TestDesc

// Return code for secondary process.
// Start somewhere other than 0 so we know the return code means what we think
// it means.
internal val TR_OK: Int = 50

// On Windows we use __fastfail to abort, which is documented to use this
// exception code.
public val STATUS_FAIL_FAST_EXCEPTION: Int = 0xC0000409u.toInt()

public sealed class TestResult {
    public data object TrOk : TestResult()
    public data object TrFailed : TestResult()
    public data class TrFailedMsg(public var value: String) : TestResult()
    public data object TrIgnored : TestResult()
    public data class TrBench(public var value: BenchSamples) : TestResult()
    public data object TrTimedFail : TestResult()

    public fun rustEquals(other: TestResult): Boolean = this == other
}

/**
 * Creates a `TestResult` depending on the raw result of test execution
 * and associated data.
 */
internal fun calcResult(desc: TestDesc, panicPayload: RustAny?, timeOpts: TestTimeOptions?, execTime: TestExecTime?): TestResult {
    val result =
        run {
            val matchVal = Pair(desc.shouldPanic, panicPayload)
            when {
                // The test did or didn't panic, as expected.
                matchVal.first == ShouldPanic.No && matchVal.second == null -> TestResult.TrOk
                matchVal.first == ShouldPanic.Yes && matchVal.second != null -> TestResult.TrOk
                // Check the actual panic message against the expected message.
                matchVal.first is ShouldPanic.YesWithMessage && matchVal.second != null -> {
                    val (msg) = matchVal.first as ShouldPanic.YesWithMessage
                    val err = matchVal.second
                    val maybePanicStr = err.downcastRef<String>().map({ e -> e }).orElse({ err.downcastRef<String>().copied() })
                    if (maybePanicStr.map({ e -> e.contains(msg) }).unwrapOr(false)) {
                        TestResult.TrOk
                    } else if (maybePanicStr != null) {
                        val panicStr = maybePanicStr
                        TestResult.TrFailedMsg("panic did not contain expected string\n      panic message: $panicStr\n expected substring: $msg")
                    } else {
                        TestResult.TrFailedMsg("expected panic with string value,\n found non-string value: `${RustAny.from(err).typeId}`\n     expected substring: $msg")
                    }
                }
                // The test should have panicked, but didn't panic.
                matchVal.first == ShouldPanic.Yes && matchVal.second == null -> {
                    val fnLocation =
                        if (!desc.sourceFile.isEmpty()) {
                            " at ${desc.sourceFile}:${desc.startLine}:${desc.startCol}"
                        } else {
                            ""
                        }
                    TestResult.TrFailedMsg("test did not panic as expected$fnLocation")
                }
                matchVal.first is ShouldPanic.YesWithMessage && matchVal.second == null -> {
                    val fnLocation1 =
                        if (!desc.sourceFile.isEmpty()) {
                            " at ${desc.sourceFile}:${desc.startLine}:${desc.startCol}"
                        } else {
                            ""
                        }
                    TestResult.TrFailedMsg("test did not panic as expected$fnLocation1")
                }
                // The test should not have panicked, but did panic.
                else -> TestResult.TrFailed
            }
        }
    // If test is already failed (or allowed to fail), do not change the result.
    if (result != TestResult.TrOk) {
        return result
    }
    // Check if test is failed due to timeout.
    if (Pair(timeOpts, execTime) != null) {
        val opts = Pair(timeOpts, execTime).first
        val time = Pair(timeOpts, execTime).second
        if (opts.errorOnExcess && opts.isCritical(desc, time)) {
            return TestResult.TrTimedFail
        }
    }
    return result
}

/**
 * Creates a `TestResult` depending on the exit code of test subprocess.
 */
internal fun getResultFromExitCode(desc: TestDesc, status: ExitStatus, timeOpts: TestTimeOptions?, execTime: TestExecTime?): TestResult {
    val result =
        when (val matchVal = status.code()) {
            TR_OK -> TestResult.TrOk
            STATUS_FAIL_FAST_EXCEPTION -> TestResult.TrFailed
            null -> run {
                val matchVal1 = status.signal()
                when {
                    matchVal1 != null && matchVal1 == SIGABRT -> TestResult.TrFailed
                    matchVal1 != null -> {
                        val signal = matchVal1
                        TestResult.TrFailedMsg("child process exited with signal $signal")
                    }
                    else -> error("status.code() returned None but status.signal() was None")
                }
            }
            null -> TestResult.TrFailedMsg("unknown return code")
            else -> {
                val code = matchVal
                TestResult.TrFailedMsg("got unexpected return code $code")
            }
        }
    // If test is already failed (or allowed to fail), do not change the result.
    if (result != TestResult.TrOk) {
        return result
    }
    // Check if test is failed due to timeout.
    if (Pair(timeOpts, execTime) != null) {
        val opts = Pair(timeOpts, execTime).first
        val time = Pair(timeOpts, execTime).second
        if (opts.errorOnExcess && opts.isCritical(desc, time)) {
            return TestResult.TrTimedFail
        }
    }
    return result
}

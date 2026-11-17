// port-lint: source formatters/junit.rs
package io.github.kotlinmania.rustlang.test.formatters.junit

import io.github.kotlinmania.rustlang.core.fmt.Write
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.TestType
import io.github.kotlinmania.rustlang.test.console.ConsoleTestDiscoveryState
import io.github.kotlinmania.rustlang.test.console.ConsoleTestState
import io.github.kotlinmania.rustlang.test.console.OutputLocation
import io.github.kotlinmania.rustlang.test.testresult.TestResult
import io.github.kotlinmania.rustlang.test.time.TestExecTime
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

public open class JunitFormatter<T>(
    public var out: OutputLocation<T>,
    public var results: List<List<Any?>>,
) {
    public open fun writeMessage(s: String): Result<Unit> {
        check(!s.contains('\n'))
        return this.out.writeAll(s)
    }

    public companion object {
        public fun <T> new(out: OutputLocation<T>): JunitFormatter<T> = JunitFormatter(out = out, results = mutableListOf())
    }
}

public fun strToCdata(s: String): String {
    // Drop the stdout in a cdata. Unfortunately, you can't put either of `]]>` or
    // `<?'` in a CDATA block, so the escaping gets a little weird.
    val escapedOutput = s.replace("]]>", "]]]]><![CDATA[>")
    val escapedOutput1 = escapedOutput.replace("<?", "<]]><![CDATA[?")
    // We also smuggle newlines as &#xa so as to keep all the output on one line
    val escapedOutput2 = escapedOutput1.replace('\n', "]]>&#xA;<![CDATA[")
    // Prune empty CDATA blocks resulting from any escaping
    val escapedOutput3 = escapedOutput2.replace("<![CDATA[]]>", "")
    return "<![CDATA[$escapedOutput3]]>"
}

public fun <T : Write> JunitFormatter<T>.writeDiscoveryStart(): Result<Unit> = RustResult.Err(io.constError(ErrorKind.NotFound, "not yet implemented!"))

public fun <T : Write> JunitFormatter<T>.writeTestDiscovered(desc: TestDesc, testType: String): Result<Unit> = RustResult.Err(io.constError(ErrorKind.NotFound, "not yet implemented!"))

public fun <T : Write> JunitFormatter<T>.writeDiscoveryFinish(state: ConsoleTestDiscoveryState): Result<Unit> = RustResult.Err(io.constError(ErrorKind.NotFound, "not yet implemented!"))

public fun <T : Write> JunitFormatter<T>.writeRunStart(testCount: Int, shuffleSeed: ULong?): Result<Unit> {
    // We write xml header on run start
    return this.writeMessage("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
}

public fun <T : Write> JunitFormatter<T>.writeTestStart(desc: TestDesc): Result<Unit> {
    // We do not output anything on test start.
    return RustResult.Ok(Unit)
}

public fun <T : Write> JunitFormatter<T>.writeTimeout(desc: TestDesc): Result<Unit> {
    // We do not output anything on test timeout.
    return RustResult.Ok(Unit)
}

public fun <T : Write> JunitFormatter<T>.writeResult(desc: TestDesc, result: TestResult, execTime: TestExecTime?, stdout: List<UByte>, state: ConsoleTestState): Result<Unit> {
    // Because the testsuite node holds some of the information as attributes, we can't write it
    // until all of the tests have finished. Instead of writing every result as they come in, we add
    // them to a Vec and write them all at once when run is complete.
    val duration = execTime?.let({ t -> t.first }).unwrapOrDefault()
    this.results.add(listOf(desc.clone(), result.clone(), duration, stdout.toMutableList()))
    return RustResult.Ok(Unit)
}

public fun <T : Write> JunitFormatter<T>.writeRunFinish(state: ConsoleTestState): Result<Boolean> {
    when (val tryResult = this.writeMessage("<testsuites>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    when (val tryResult = this.writeMessage("<testsuite name=\"test\" package=\"test\" id=\"0\" errors=\"0\" failures=\"${state.failed}\" tests=\"${state.total}\" skipped=\"${state.ignored}\" >")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    for ((desc, result, duration, stdout) in take(this.results)) {
        val (className, testName) = parseClassName(desc)
        when (result) {
            TestResult.TrIgnored -> {
                // no-op
            }
            TestResult.TrFailed -> {
                when (val tryResult = this.writeMessage("<testcase classname=\"$className\" name=\"$testName\" time=\"${duration.asSecsF64()}\">")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                when (val tryResult = this.writeMessage("<failure type=\"assert\"/>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                if (!stdout.isEmpty()) {
                    when (val tryResult = this.writeMessage("<system-out>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                    when (val tryResult = this.writeMessage(strToCdata(String.fromUtf8Lossy(stdout)))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                    when (val tryResult = this.writeMessage("</system-out>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.writeMessage("</testcase>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            }
            is TestResult.TrFailedMsg -> {
                val (m) = result
                when (val tryResult = this.writeMessage("<testcase classname=\"$className\" name=\"$testName\" time=\"${duration.asSecsF64()}\">")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                when (val tryResult = this.writeMessage("<failure message=\"$m\" type=\"assert\"/>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                if (!stdout.isEmpty()) {
                    when (val tryResult = this.writeMessage("<system-out>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                    when (val tryResult = this.writeMessage(strToCdata(String.fromUtf8Lossy(stdout)))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                    when (val tryResult = this.writeMessage("</system-out>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.writeMessage("</testcase>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            }
            TestResult.TrTimedFail -> {
                when (val tryResult = this.writeMessage("<testcase classname=\"$className\" name=\"$testName\" time=\"${duration.asSecsF64()}\">")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                when (val tryResult = this.writeMessage("<failure type=\"timeout\"/>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                when (val tryResult = this.writeMessage("</testcase>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            }
            is TestResult.TrBench -> {
                val (b) = result
                when (val tryResult = this.writeMessage("<testcase classname=\"benchmark::$className\" name=\"$testName\" time=\"${b.nsIterSumm.sum}\" />")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            }
            TestResult.TrOk -> {
                when (val tryResult = this.writeMessage("<testcase classname=\"$className\" name=\"$testName\" time=\"${duration.asSecsF64()}\"")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                if (stdout.isEmpty() || !state.options.displayOutput) {
                    when (val tryResult = this.writeMessage("/>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                } else {
                    when (val tryResult = this.writeMessage("><system-out>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                    when (val tryResult = this.writeMessage(strToCdata(String.fromUtf8Lossy(stdout)))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                    when (val tryResult = this.writeMessage("</system-out>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                    when (val tryResult = this.writeMessage("</testcase>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                }
            }
        }
    }
    when (val tryResult = this.writeMessage("<system-out/>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    when (val tryResult = this.writeMessage("<system-err/>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    when (val tryResult = this.writeMessage("</testsuite>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    when (val tryResult = this.writeMessage("</testsuites>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    when (val tryResult = this.out.writeAll("\n".encodeToByteArray())) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok(state.failed == 0)
}

public fun <T : Write> JunitFormatter<T>.writeMergedDoctestsTimes(totalTime: Double, compilationTime: Double): Result<Unit> {
    when (val tryResult = this.writeMessage("<report total_time=\"$totalTime\" compilation_time=\"$compilationTime\"></report>")) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    when (val tryResult = this.out.writeAll("\n".encodeToByteArray())) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok(Unit)
}

public fun parseClassName(desc: TestDesc): Pair<String, String> =
    when (desc.testType) {
        TestType.UnitTest -> parseClassNameUnit(desc)
        TestType.DocTest -> parseClassNameDoc(desc)
        TestType.IntegrationTest -> parseClassNameIntegration(desc)
        TestType.Unknown -> Pair("unknown", (desc.name.asSlice()).toString())
    }

public fun parseClassNameUnit(desc: TestDesc): Pair<String, String> {
    // Module path => classname
    // Function name => name
    val moduleSegments: List<String> = desc.name.asSlice().split("::").toMutableList()
    val (className, testName) =
        when {
            moduleSegments.size == 1 -> {
                val test = moduleSegments[0]
                Pair("crate", (test).toString())
            }
            moduleSegments.size >= 1 -> {
                val path = moduleSegments.subList(0, moduleSegments.size - 1)
                val test1 = moduleSegments[moduleSegments.size - 1]
                Pair(path.join("::"), (test1).toString())
            }
            else -> error("unreachable")
        }
    return Pair(className, testName)
}

public fun parseClassNameDoc(desc: TestDesc): Pair<String, String> {
    // File path => classname
    // Line # => test name
    val segments: List<String> = desc.name.asSlice().split(" - ").toMutableList()
    val (className, testName) =
        when {
            segments.size == 2 -> {
                val file = segments[0]
                val line = segments[1]
                Pair((file.trim()).toString(), (line.trim()).toString())
            }
            else -> error("unreachable")
        }
    return Pair(className, testName)
}

public fun parseClassNameIntegration(desc: TestDesc): Pair<String, String> = Pair("integration", (desc.name.asSlice()).toString())

// port-lint: source formatters/junit.rs
package io.github.kotlinmania.rustlang.test.formatters.junit

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.TestType
import io.github.kotlinmania.rustlang.test.console.ConsoleTestDiscoveryState
import io.github.kotlinmania.rustlang.test.console.ConsoleTestState
import io.github.kotlinmania.rustlang.test.console.OutputLocation
import io.github.kotlinmania.rustlang.test.formatters.OutputFormatter
import io.github.kotlinmania.rustlang.test.testresult.TestResult
import io.github.kotlinmania.rustlang.test.time.TestExecTime

internal open class JunitFormatter<T>(
    public var out: OutputLocation<T>,
    public var results: MutableList<List<Any?>>,
) : OutputFormatter {
    public open fun writeMessage(s: String): Result<Unit, Error> {
        check(!s.contains('\n'))
        return this.out.writeAll(s)
    }

    public override fun writeDiscoveryStart(): Result<Unit, Error> = Result.Err(io.constError(ErrorKind.NotFound, "not yet implemented!"))

    public override fun writeTestDiscovered(desc: TestDesc, testType: String): Result<Unit, Error> = Result.Err(io.constError(ErrorKind.NotFound, "not yet implemented!"))

    public override fun writeDiscoveryFinish(state: ConsoleTestDiscoveryState): Result<Unit, Error> = Result.Err(io.constError(ErrorKind.NotFound, "not yet implemented!"))

    public override fun writeRunStart(testCount: Int, shuffleSeed: ULong?): Result<Unit, Error> {
        // We write xml header on run start
        return this.writeMessage("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    }

    public override fun writeTestStart(desc: TestDesc): Result<Unit, Error> {
        // We do not output anything on test start.
        return Result.Ok(Unit)
    }

    public override fun writeTimeout(desc: TestDesc): Result<Unit, Error> {
        // We do not output anything on test timeout.
        return Result.Ok(Unit)
    }

    public override fun writeResult(desc: TestDesc, result: TestResult, execTime: TestExecTime?, stdout: List<UByte>, state: ConsoleTestState): Result<Unit, Error> {
        // Because the testsuite node holds some of the information as attributes, we can't write it
        // until all of the tests have finished. Instead of writing every result as they come in, we add
        // them to a Vec and write them all at once when run is complete.
        val duration = execTime?.let({ t -> t.first }).unwrapOrDefault()
        this.results.add(listOf(desc.clone(), result.clone(), duration, stdout.toMutableList()))
        return Result.Ok(Unit)
    }

    public override fun writeRunFinish(state: ConsoleTestState): Result<Boolean, Error> {
        when (val tryResult = this.writeMessage("<testsuites>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        when (val tryResult = this.writeMessage("<testsuite name=\"test\" package=\"test\" id=\"0\" errors=\"0\" failures=\"${state.failed}\" tests=\"${state.total}\" skipped=\"${state.ignored}\" >")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        for ((desc, result, duration, stdout) in take(this.results)) {
            val (className, testName) = parseClassName(desc)
            when (result) {
                TestResult.TrIgnored -> {
                    // no-op
                }
                TestResult.TrFailed -> {
                    when (val tryResult = this.writeMessage("<testcase classname=\"$className\" name=\"$testName\" time=\"${duration.asSecsF64()}\">")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    when (val tryResult = this.writeMessage("<failure type=\"assert\"/>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    if (!stdout.isEmpty()) {
                        when (val tryResult = this.writeMessage("<system-out>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                        when (val tryResult = this.writeMessage(strToCdata(String.fromUtf8Lossy(stdout)))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                        when (val tryResult = this.writeMessage("</system-out>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    }
                    when (val tryResult = this.writeMessage("</testcase>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                is TestResult.TrFailedMsg -> {
                    val (m) = result
                    when (val tryResult = this.writeMessage("<testcase classname=\"$className\" name=\"$testName\" time=\"${duration.asSecsF64()}\">")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    when (val tryResult = this.writeMessage("<failure message=\"$m\" type=\"assert\"/>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    if (!stdout.isEmpty()) {
                        when (val tryResult = this.writeMessage("<system-out>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                        when (val tryResult = this.writeMessage(strToCdata(String.fromUtf8Lossy(stdout)))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                        when (val tryResult = this.writeMessage("</system-out>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    }
                    when (val tryResult = this.writeMessage("</testcase>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                TestResult.TrTimedFail -> {
                    when (val tryResult = this.writeMessage("<testcase classname=\"$className\" name=\"$testName\" time=\"${duration.asSecsF64()}\">")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    when (val tryResult = this.writeMessage("<failure type=\"timeout\"/>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    when (val tryResult = this.writeMessage("</testcase>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                is TestResult.TrBench -> {
                    val (b) = result
                    when (val tryResult = this.writeMessage("<testcase classname=\"benchmark::$className\" name=\"$testName\" time=\"${b.nsIterSumm.sum}\" />")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                TestResult.TrOk -> {
                    when (val tryResult = this.writeMessage("<testcase classname=\"$className\" name=\"$testName\" time=\"${duration.asSecsF64()}\"")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    if (stdout.isEmpty() || !state.options.displayOutput) {
                        when (val tryResult = this.writeMessage("/>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    } else {
                        when (val tryResult = this.writeMessage("><system-out>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                        when (val tryResult = this.writeMessage(strToCdata(String.fromUtf8Lossy(stdout)))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                        when (val tryResult = this.writeMessage("</system-out>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                        when (val tryResult = this.writeMessage("</testcase>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    }
                }
            }
        }
        when (val tryResult = this.writeMessage("<system-out/>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        when (val tryResult = this.writeMessage("<system-err/>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        when (val tryResult = this.writeMessage("</testsuite>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        when (val tryResult = this.writeMessage("</testsuites>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        when (val tryResult = this.out.writeAll("\n".encodeToByteArray())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(state.failed == 0)
    }

    public override fun writeMergedDoctestsTimes(totalTime: Double, compilationTime: Double): Result<Unit, Error> {
        when (val tryResult = this.writeMessage("<report total_time=\"$totalTime\" compilation_time=\"$compilationTime\"></report>")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        when (val tryResult = this.out.writeAll("\n".encodeToByteArray())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public companion object {
        internal fun <T> new(out: OutputLocation<T>): JunitFormatter<T> = JunitFormatter(out = out, results = mutableListOf())
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

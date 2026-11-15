// port-lint: source formatters/json.rs
package io.github.kotlinmania.rustlang.test.formatters.json

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.chars
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.console.ConsoleTestDiscoveryState
import io.github.kotlinmania.rustlang.test.console.ConsoleTestState
import io.github.kotlinmania.rustlang.test.console.OutputLocation
import io.github.kotlinmania.rustlang.test.formatters.OutputFormatter
import io.github.kotlinmania.rustlang.test.testresult.TestResult
import io.github.kotlinmania.rustlang.test.time.TestExecTime

public open class JsonFormatter<T>(
    public var out: OutputLocation<T>,
) : OutputFormatter {
    public open fun writelnMessage(s: String): Result<Unit> {
        // self.out will take a lock, but that lock is released when writeAll returns. This
        // results in a race condition and json output may not end with a new line. We avoid this
        // by issuing `writeAll` calls line-by-line.
        check(s.chars().last() == '\n')
        return this.out.writeAll(s)
    }

    public open fun writeEvent(ty: String, name: String, event: String, execTime: TestExecTime?, stdout: String?, extra: String?): Result<Unit> {
        // A doc test's name includes a filename which must be escaped for correct json.
        val execTimeJson =
            if (execTime != null) {
                val execTime1 = execTime
                ", \"exec_time\": ${execTime1.value.asSecsF64()}"
            } else {
                ""
            }
        val stdoutJson =
            if (stdout != null) {
                val stdout1 = stdout
                ", \"stdout\": \"${EscapedString(stdout1)}\""
            } else {
                ""
            }
        val extraJson =
            if (extra != null) {
                ", $extra"
            } else {
                ""
            }
        val newline = "\n"
        return this.writelnMessage("{ \"type\": \"$ty\", \"name\": \"$name\", \"event\": \"$event\"$execTimeJson$stdoutJson$extraJson }$newline")
    }

    public override fun writeDiscoveryStart(): Result<Unit> = this.writelnMessage("{ \"type\": \"suite\", \"event\": \"discovery\" }\n")

    public override fun writeTestDiscovered(desc: TestDesc, testType: String): Result<Unit> {
        val name = desc.name
        val ignore = desc.ignore
        val ignoreMessage = desc.ignoreMessage
        val sourceFile = desc.sourceFile
        val startLine = desc.startLine
        val startCol = desc.startCol
        val endLine = desc.endLine
        val endCol = desc.endCol
        val sourcePath = EscapedString(sourceFile)
        val newline = "\n"
        return this.writelnMessage("{ \"type\": \"$testType\", \"event\": \"discovered\", \"name\": \"$name\", \"ignore\": $ignore, \"ignore_message\": \"$ignoreMessage\", \"source_path\": \"$sourcePath\", \"start_line\": $startLine, \"start_col\": $startCol, \"end_line\": $endLine, \"end_col\": $endCol }$newline")
    }

    public override fun writeDiscoveryFinish(state: ConsoleTestDiscoveryState): Result<Unit> {
        val tests = state.tests
        val benchmarks = state.benchmarks
        val ignored = state.ignored
        val total = tests + benchmarks
        val newline = "\n"
        return this.writelnMessage("{ \"type\": \"suite\", \"event\": \"completed\", \"tests\": $tests, \"benchmarks\": $benchmarks, \"total\": $total, \"ignored\": $ignored }$newline")
    }

    public override fun writeRunStart(testCount: Int, shuffleSeed: ULong?): Result<Unit> {
        val shuffleSeedJson =
            if (shuffleSeed != null) {
                ", \"shuffle_seed\": $shuffleSeed"
            } else {
                ""
            }
        val newline = "\n"
        return this.writelnMessage("{ \"type\": \"suite\", \"event\": \"started\", \"test_count\": $testCount$shuffleSeedJson }$newline")
    }

    public override fun writeTestStart(desc: TestDesc): Result<Unit> {
        val name = EscapedString(desc.name.asSlice())
        val newline = "\n"
        return this.writelnMessage("{ \"type\": \"test\", \"event\": \"started\", \"name\": \"$name\" }$newline")
    }

    public override fun writeResult(desc: TestDesc, result: TestResult, execTime: TestExecTime?, stdout: List<UByte>, state: ConsoleTestState): Result<Unit> {
        val displayStdout = state.options.displayOutput || result != TestResult.TrOk
        val stdout1 =
            if (displayStdout && !stdout.isEmpty()) {
                String.fromUtf8Lossy(stdout)
            } else {
                null
            }
        return when (result) {
            TestResult.TrOk -> {
                this.writeEvent("test", desc.name.asSlice(), "ok", execTime, stdout1, null)
            }
            TestResult.TrFailed -> {
                this.writeEvent("test", desc.name.asSlice(), "failed", execTime, stdout1, null)
            }
            TestResult.TrTimedFail -> this.writeEvent("test", desc.name.asSlice(), "failed", execTime, stdout1, "\"reason\": \"time limit exceeded\"")
            is TestResult.TrFailedMsg -> {
                val (m) = result
                this.writeEvent("test", desc.name.asSlice(), "failed", execTime, stdout1, "\"message\": \"${EscapedString(m)}\"")
            }
            TestResult.TrIgnored -> this.writeEvent("test", desc.name.asSlice(), "ignored", execTime, stdout1, desc.ignoreMessage.map({ msg -> "\"message\": \"${EscapedString(msg)}\"" }).asDeref())
            is TestResult.TrBench -> {
                val (bs) = result
                val median = bs.nsIterSumm.median
                val deviation = bs.nsIterSumm.max - bs.nsIterSumm.min
                val mbps =
                    if (bs.mbS == 0) {
                        ""
                    } else {
                        ", \"mib_per_second\": ${bs.mbS}"
                    }
                val name = EscapedString(desc.name.asSlice())
                this.writelnMessage("{ \"type\": \"bench\", \"name\": \"$name\", \"median\": $median, \"deviation\": $deviation$mbps }\n")
            }
        }
    }

    public override fun writeTimeout(desc: TestDesc): Result<Unit> {
        val name = EscapedString(desc.name.asSlice())
        val newline = "\n"
        return this.writelnMessage("{ \"type\": \"test\", \"event\": \"timeout\", \"name\": \"$name\" }$newline")
    }

    public override fun writeRunFinish(state: ConsoleTestState): Result<Boolean> {
        val event =
            if (state.failed == 0) {
                "ok"
            } else {
                "failed"
            }
        val passed = state.passed
        val failed = state.failed
        val ignored = state.ignored
        val measured = state.measured
        val filteredOut = state.filteredOut
        val execTimeJson =
            when (val matchVal = state.execTime) {
                null -> {
                    ""
                }
                else -> {
                    val execTime = matchVal
                    ", \"exec_time\": ${execTime.first.asSecsF64()}"
                }
            }
        val newline = "\n"
        when (val tryResult = this.writelnMessage("{ \"type\": \"suite\", \"event\": \"$event\", \"passed\": $passed, \"failed\": $failed, \"ignored\": $ignored, \"measured\": $measured, \"filtered_out\": $filteredOut$execTimeJson }$newline")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok(state.failed == 0)
    }

    public override fun writeMergedDoctestsTimes(totalTime: Double, compilationTime: Double): Result<Unit> {
        val newline = "\n"
        return this.writelnMessage("{ \"type\": \"report\", \"total_time\": $totalTime, \"compilation_time\": $compilationTime }$newline")
    }

    public companion object {
        public fun <T> new(out: OutputLocation<T>): JsonFormatter<T> = JsonFormatter(out = out)
    }
}

/**
 * A formatting utility used to print strings with characters in need of escaping.
 * Base code taken form `libserialize.json.escapeStr`
 */
public open class EscapedString<S : AsRef<String>>(
    public var value: S,
) {
    public var field0: S
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder) {
        var start = 0
        for ((i, byte) in this.value.bytes().mapIndexed { index, item -> Pair(index, item) }) {
            val escaped =
                when (byte) {
                    34u -> "\\\""
                    92u -> "\\\\"
                    0u -> "\\u0000"
                    1u -> "\\u0001"
                    2u -> "\\u0002"
                    3u -> "\\u0003"
                    4u -> "\\u0004"
                    5u -> "\\u0005"
                    6u -> "\\u0006"
                    7u -> "\\u0007"
                    8u -> "\\b"
                    9u -> "\\t"
                    10u -> "\\n"
                    11u -> "\\u000b"
                    12u -> "\\f"
                    13u -> "\\r"
                    14u -> "\\u000e"
                    15u -> "\\u000f"
                    16u -> "\\u0010"
                    17u -> "\\u0011"
                    18u -> "\\u0012"
                    19u -> "\\u0013"
                    20u -> "\\u0014"
                    21u -> "\\u0015"
                    22u -> "\\u0016"
                    23u -> "\\u0017"
                    24u -> "\\u0018"
                    25u -> "\\u0019"
                    26u -> "\\u001a"
                    27u -> "\\u001b"
                    28u -> "\\u001c"
                    29u -> "\\u001d"
                    30u -> "\\u001e"
                    31u -> "\\u001f"
                    127u -> "\\u007f"
                    else -> {
                        continue
                    }
                }
            if (start < i) {
                f.append(this.value[start until i])
            }
            f.append(escaped)
            start = i + 1
        }
        if (start != this.value.len()) {
            f.append(this.value[start until this.value.size])
        }
    }

    public companion object {
        public fun <S : AsRef<String>> typeLayout(typeLayoutS: TypeLayout<S>): TypeLayout<EscapedString<S>> =
            object : TypeLayout<EscapedString<S>> {
                override val alignBytes: Long = listOf(1L, typeLayoutS.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutS.sizeBytes, alignBytes)
                override fun readNative(addr: Long): EscapedString<S> =
                    EscapedString(
                        (typeLayoutS).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: EscapedString<S>) {
                    (typeLayoutS).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EscapedString<S> =
                    EscapedString(
                        (typeLayoutS).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EscapedString<S>) {
                    (typeLayoutS).writeBytes(buf, offset, value.value)
                }
            }
    }
}

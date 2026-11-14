package io.github.kotlinmania.rustlang.test.formatters

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.console.ConsoleTestDiscoveryState
import io.github.kotlinmania.rustlang.test.console.ConsoleTestState
import io.github.kotlinmania.rustlang.test.testresult.TestResult
import io.github.kotlinmania.rustlang.test.time.TestExecTime

internal interface OutputFormatter {
    public fun writeDiscoveryStart(): Result<Unit, Error>

    public fun writeTestDiscovered(desc: TestDesc, testType: String): Result<Unit, Error>

    public fun writeDiscoveryFinish(state: ConsoleTestDiscoveryState): Result<Unit, Error>

    public fun writeRunStart(testCount: Int, shuffleSeed: ULong?): Result<Unit, Error>

    public fun writeTestStart(desc: TestDesc): Result<Unit, Error>

    public fun writeTimeout(desc: TestDesc): Result<Unit, Error>

    public fun writeResult(desc: TestDesc, result: TestResult, execTime: TestExecTime?, stdout: List<UByte>, state: ConsoleTestState): Result<Unit, Error>

    public fun writeRunFinish(state: ConsoleTestState): Result<Boolean, Error>

    public fun writeMergedDoctestsTimes(totalTime: Double, compilationTime: Double): Result<Unit, Error>
}

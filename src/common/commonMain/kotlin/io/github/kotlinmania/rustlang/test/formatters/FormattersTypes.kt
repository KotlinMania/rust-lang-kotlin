package io.github.kotlinmania.rustlang.test.formatters

import io.github.kotlinmania.io.Result
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.console.ConsoleTestDiscoveryState
import io.github.kotlinmania.rustlang.test.console.ConsoleTestState
import io.github.kotlinmania.rustlang.test.testresult.TestResult
import io.github.kotlinmania.rustlang.test.time.TestExecTime

internal interface OutputFormatter {
    public fun writeDiscoveryStart(): Result<Unit>

    public fun writeTestDiscovered(desc: TestDesc, testType: String): Result<Unit>

    public fun writeDiscoveryFinish(state: ConsoleTestDiscoveryState): Result<Unit>

    public fun writeRunStart(testCount: Int, shuffleSeed: ULong?): Result<Unit>

    public fun writeTestStart(desc: TestDesc): Result<Unit>

    public fun writeTimeout(desc: TestDesc): Result<Unit>

    public fun writeResult(desc: TestDesc, result: TestResult, execTime: TestExecTime?, stdout: List<UByte>, state: ConsoleTestState): Result<Unit>

    public fun writeRunFinish(state: ConsoleTestState): Result<Boolean>

    public fun writeMergedDoctestsTimes(totalTime: Double, compilationTime: Double): Result<Unit>
}

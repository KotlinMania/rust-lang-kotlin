// port-lint: source event.rs
package io.github.kotlinmania.rustlang.test.event

import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.TestExecTime
import io.github.kotlinmania.rustlang.test.TestId
import io.github.kotlinmania.rustlang.test.TestResult

// Module containing different events that can occur
// during tests execution process.

public open class CompletedTest(
    public var id: TestId,
    public var desc: TestDesc,
    public var result: TestResult,
    public var execTime: TestExecTime?,
    public var stdout: List<UByte>,
) {
    public open fun clone(): CompletedTest = CompletedTest(id, desc, result, execTime, stdout)

    public companion object {
        public fun new(id: TestId, desc: TestDesc, result: TestResult, execTime: TestExecTime?, stdout: List<UByte>): CompletedTest = CompletedTest(id = id, desc = desc, result = result, execTime = execTime, stdout = stdout)
    }
}

public sealed class TestEvent {
    public data class TeFiltered(public var field0: Int, public var field1: ULong?) : TestEvent()
    public data class TeWait(public var value: TestDesc) : TestEvent()
    public data class TeResult(public var value: CompletedTest) : TestEvent()
    public data class TeTimeout(public var value: TestDesc) : TestEvent()
    public data class TeFilteredOut(public var value: Int) : TestEvent()
}

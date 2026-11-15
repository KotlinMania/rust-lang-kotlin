package io.github.kotlinmania.rustlang.test.formatters

import io.github.kotlinmania.rustlang.test.TestName

public fun writeStderrDelimiter(testOutput: List<UByte>, testName: TestName) {
    run {
        val matchVal = testOutput.last()
        when {
            matchVal != null && matchVal == 10u -> Unit
            matchVal != null -> testOutput.push(10u)
            else -> Unit
        }
    }
    testOutput.appendLine("---- $testName stderr ----").unwrap()
}

package io.github.kotlinmania.rustlang.test.formatters

import io.github.kotlinmania.rustlang.test.TestName

public fun writeStderrDelimiter(testOutput: MutableList<UByte>, testName: TestName) {
    run {
        val matchVal = testOutput.last()
        when {
            matchVal != null && matchVal == 10u.toUByte() -> Unit
            matchVal != null -> testOutput.add(10u.toUByte())
            else -> Unit
        }
    }
    testOutput.appendLine("---- $testName stderr ----").unwrap()
}

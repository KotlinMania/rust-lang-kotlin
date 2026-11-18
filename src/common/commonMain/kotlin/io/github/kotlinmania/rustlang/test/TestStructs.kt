package io.github.kotlinmania.rustlang.test

import io.github.kotlinmania.rustlang.core.result.Result

public open class FilteredTests(
    public var tests: MutableList<Pair<TestId, TestDescAndFn>>,
    public var benches: MutableList<Pair<TestId, TestDescAndFn>>,
    public var nextId: Int,
) {
    public open fun addBench(desc: TestDesc, testfn: TestFn) {
        val test = TestDescAndFn(desc = desc, testfn = testfn)
        this.benches.add(Pair(TestId(this.nextId), test))
        this.nextId += 1
    }

    public open fun addTest(desc: TestDesc, testfn: TestFn) {
        val test = TestDescAndFn(desc = desc, testfn = testfn)
        this.tests.add(Pair(TestId(this.nextId), test))
        this.nextId += 1
    }

    public open fun totalLen(): Int = this.tests.size + this.benches.size

    public companion object
}

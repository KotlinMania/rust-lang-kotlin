// port-lint: source types.rs
package io.github.kotlinmania.rustlang.test

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.rustSaturatingSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sync.mpsc.Sender
import io.github.kotlinmania.rustlang.test.bench.Bencher
import io.github.kotlinmania.rustlang.test.bench.benchmark
import io.github.kotlinmania.rustlang.test.bench.runOnce
import io.github.kotlinmania.rustlang.test.event.CompletedTest
import io.github.kotlinmania.rustlang.test.options.Options

// Common types used by `libtest`.

public fun interface FnCallback {
    public operator fun invoke(): Result<Unit, String>
}

public fun interface FnCallback1 {
    public operator fun invoke(arg0: Bencher): Result<Unit, String>
}

/**
 * Type of the test according to the [Rust book](https://doc.rust-lang.org/cargo/guide/tests.html)
 * conventions.
 */
public sealed class TestType {
    /**
     * Unit-tests are expected to be in the `src` folder of the crate.
     */
    public data object UnitTest : TestType()
    /**
     * Integration-style tests are expected to be in the `tests` folder of the crate.
     */
    public data object IntegrationTest : TestType()
    /**
     * Doctests are created by the `librustdoc` manually, so it's a different type of test.
     */
    public data object DocTest : TestType()
    /**
     * Tests for the sources that don't follow the project layout convention
     * (e.g. tests in raw `main.rs` compiled by calling `rustc - - test` directly).
     */
    public data object Unknown : TestType()

    public fun rustEquals(other: TestType): Boolean = this == other
}

public sealed class NamePadding {
    public data object PadNone : NamePadding()
    public data object PadOnRight : NamePadding()

    public fun rustEquals(other: NamePadding): Boolean = this == other
}

// The name of a test. By convention this follows the rules for rust
// paths; i.e., it should be a series of identifiers separated by double
// colons. This way if some test runner wants to arrange the tests
// hierarchically it may.
public sealed class TestName {
    public data class StaticTestName(public var value: String) : TestName()
    public data class DynTestName(public var value: String) : TestName()
    public data class AlignedTestName(public var field0: String, public var field1: NamePadding) : TestName()

    public fun rustEquals(other: TestName): Boolean = this == other

    public open fun asSlice(): String =
        when (this) {
            is StaticTestName -> {
                val (s) = this
                s
            }
            is DynTestName -> {
                val (s1) = this
                s1
            }
            is AlignedTestName -> {
                val (s2, _) = this
                s2
            }
        }

    public open fun padding(): NamePadding =
        when (this) {
            is AlignedTestName -> {
                val (_, p) = this
                p
            }
            else -> PadNone
        }

    public open fun withPadding(padding: NamePadding): TestName {
        val name3 =
            when (this) {
                is TestName.StaticTestName -> {
                    val (name) = this
                    name
                }
                is TestName.DynTestName -> {
                    val (name1) = this
                    name1.clone()
                }
                is TestName.AlignedTestName -> {
                    val (name2, _) = this
                    name2.clone()
                }
            }
        return TestName.AlignedTestName(name3, padding)
    }

    public open fun fmt(f: StringBuilder) {
        f.append((this.asSlice()).toString())
    }
}

// A function that runs a test. If the function returns successfully,
// the test succeeds; if the function panics or returns Result.Err
// then the test fails. We may need to come up with a more clever
// definition of test in order to support isolation of tests into
// threads.
public sealed class TestFn {
    public data class StaticTestFn(public var value: FnCallback) : TestFn()
    public data class StaticBenchFn(public var value: FnCallback1) : TestFn()
    public data class StaticBenchAsTestFn(public var value: FnCallback1) : TestFn()
    public class DynTestFn internal constructor(valueCtorArg: () -> Result<Unit, String>) : TestFn() {
        public var value: () -> Result<Unit, String> = valueCtorArg
            internal set

        public operator fun component1(): () -> Result<Unit, String> = this.value

        public fun copy(value: () -> Result<Unit, String> = this.value): DynTestFn = DynTestFn(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is DynTestFn) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: () -> Result<Unit, String>,
            ): DynTestFn = DynTestFn(
                valueCtorArg = value,
            )
        }
    }
    public class DynBenchFn internal constructor(valueCtorArg: (Bencher) -> Result<Unit, String>) : TestFn() {
        public var value: (Bencher) -> Result<Unit, String> = valueCtorArg
            internal set

        public operator fun component1(): (Bencher) -> Result<Unit, String> = this.value

        public fun copy(value: (Bencher) -> Result<Unit, String> = this.value): DynBenchFn = DynBenchFn(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is DynBenchFn) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: (Bencher) -> Result<Unit, String>,
            ): DynBenchFn = DynBenchFn(
                valueCtorArg = value,
            )
        }
    }
    public class DynBenchAsTestFn internal constructor(valueCtorArg: (Bencher) -> Result<Unit, String>) : TestFn() {
        public var value: (Bencher) -> Result<Unit, String> = valueCtorArg
            internal set

        public operator fun component1(): (Bencher) -> Result<Unit, String> = this.value

        public fun copy(value: (Bencher) -> Result<Unit, String> = this.value): DynBenchAsTestFn = DynBenchAsTestFn(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is DynBenchAsTestFn) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: (Bencher) -> Result<Unit, String>,
            ): DynBenchAsTestFn = DynBenchAsTestFn(
                valueCtorArg = value,
            )
        }
    }

    public open fun padding(): NamePadding =
        when (this) {
            is StaticTestFn -> PadNone
            is StaticBenchFn -> PadOnRight
            is StaticBenchAsTestFn -> PadNone
            is DynTestFn -> PadNone
            is DynBenchFn -> PadOnRight
            is DynBenchAsTestFn -> PadNone
        }

    internal open fun intoRunnable(): Runnable =
        when (this) {
            is StaticTestFn -> {
                val (f) = this
                Runnable.Test(RunnableTest.Static(f))
            }
            is StaticBenchFn -> {
                val (f1) = this
                Runnable.Bench(RunnableBench.Static(f1))
            }
            is StaticBenchAsTestFn -> {
                val (f2) = this
                Runnable.Test(RunnableTest.StaticBenchAsTest(f2))
            }
            is DynTestFn -> {
                val (f3) = this
                Runnable.Test(RunnableTest.Dynamic(f3))
            }
            is DynBenchFn -> {
                val (f4) = this
                Runnable.Bench(RunnableBench.Dynamic(f4))
            }
            is DynBenchAsTestFn -> {
                val (f5) = this
                Runnable.Test(RunnableTest.DynamicBenchAsTest(f5))
            }
        }

    public open fun fmt(f: StringBuilder) {
        f.append(when (this) {
            is StaticTestFn -> "StaticTestFn(..)"
            is StaticBenchFn -> "StaticBenchFn(..)"
            is StaticBenchAsTestFn -> "StaticBenchAsTestFn(..)"
            is DynTestFn -> "DynTestFn(..)"
            is DynBenchFn -> "DynBenchFn(..)"
            is DynBenchAsTestFn -> "DynBenchAsTestFn(..)"
        })
    }
}

internal sealed class Runnable {
    public data class Test(public var value: RunnableTest) : Runnable()
    public data class Bench(public var value: RunnableBench) : Runnable()
}

internal sealed class RunnableTest {
    public data class Static(public var value: FnCallback) : RunnableTest()
    public data class Dynamic(public var value: () -> Result<Unit, String>) : RunnableTest()
    public data class StaticBenchAsTest(public var value: FnCallback1) : RunnableTest()
    public data class DynamicBenchAsTest(public var value: (Bencher) -> Result<Unit, String>) : RunnableTest()

    internal open fun run(): Result<Unit, String> =
        when (this) {
            is RunnableTest.Static -> {
                val (f) = this
                rustBeginShortBacktrace(f)
            }
            is RunnableTest.Dynamic -> {
                val (f1) = this
                rustBeginShortBacktrace({ f1() })
            }
            is RunnableTest.StaticBenchAsTest -> {
                val (f2) = this
                runOnce({ b -> rustBeginShortBacktrace({ f2(b) }) })
            }
            is RunnableTest.DynamicBenchAsTest -> {
                val (f3) = this
                runOnce({ b -> rustBeginShortBacktrace({ f3(b) }) })
            }
        }

    internal open fun isDynamic(): Boolean =
        when (this) {
            is RunnableTest.Static -> false
            is RunnableTest.StaticBenchAsTest -> false
            is RunnableTest.Dynamic -> true
            is RunnableTest.DynamicBenchAsTest -> true
        }
}

internal sealed class RunnableBench {
    public data class Static(public var value: FnCallback1) : RunnableBench()
    public data class Dynamic(public var value: (Bencher) -> Result<Unit, String>) : RunnableBench()

    internal open fun run(id: TestId, desc: TestDesc, monitorCh: Sender<CompletedTest>, nocapture: Boolean) {
        when (this) {
            is RunnableBench.Static -> {
                val (f) = this
                benchmark(id, desc.clone(), monitorCh.clone(), nocapture, f)
            }
            is RunnableBench.Dynamic -> {
                val (f1) = this
                benchmark(id, desc.clone(), monitorCh.clone(), nocapture, { b -> f1(b) })
            }
        }
    }
}

// A unique integer associated with each test.
public open class TestId(
    public var value: Int,
) {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<TestId> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<TestId> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): TestId =
                    TestId(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: TestId) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TestId =
                    TestId(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TestId) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.value)
                }
            }
    }
}

// The definition of a single test. A test runner will run a list of
// these.
public open class TestDesc(
    public var name: TestName,
    public var ignore: Boolean,
    public var ignoreMessage: String?,
    public var sourceFile: String,
    public var startLine: Int,
    public var startCol: Int,
    public var endLine: Int,
    public var endCol: Int,
    public var shouldPanic: ShouldPanic,
    public var compileFail: Boolean,
    public var noRun: Boolean,
    public var testType: TestType,
) {
    public open fun paddedName(columnCount: Int, align: NamePadding): String {
        var name = this.name.asSlice()
        val fill = rustSaturatingSub(columnCount, name.length)
        val pad = " ".repeat(fill)
        return when (align) {
            PadNone -> name
            PadOnRight -> {
                name += pad
                name
            }
        }
    }

    /**
     * Returns None for ignored test or tests that are just run, otherwise returns a description of the type of test.
     * Descriptions include "should panic", "compile fail" and "compile".
     */
    public open fun testMode(): String? {
        if (this.ignore) {
            return null
        }
        when (val matchVal = this.shouldPanic) {
            Options.ShouldPanic.Yes -> {
                return "should panic"
            }
            is ShouldPanic.YesWithMessage -> {
                return "should panic"
            }
            Options.ShouldPanic.No -> {}
        }
        if (this.compileFail) {
            return "compile fail"
        }
        if (this.noRun) {
            return "compile"
        }
        return null
    }
}

public open class TestDescAndFn(
    public var desc: TestDesc,
    public var testfn: TestFn,
) {
    public companion object {
        public fun newDoctest(testName: String, ignore: Boolean, sourceFile: String, startLine: Int, noRun: Boolean, shouldPanic: Boolean, testfn: TestFn): TestDescAndFn =
            TestDescAndFn(desc = TestDesc(name = StaticTestName(testName), ignore = ignore, ignoreMessage = null, sourceFile = sourceFile, startLine = startLine, startCol = 0, endLine = 0, endCol = 0, compileFail = false, noRun = noRun, shouldPanic = if (shouldPanic) {
                Options.ShouldPanic.Yes
            } else {
                Options.ShouldPanic.No
            }, testType = TestType.DocTest), testfn = testfn)
    }
}

/**
 * Whether a [TestList]s tests are known to be sorted by name.
 *
 * When tests are sorted, `filterTests` can use binary search for `- - exact`
 * matches instead of a linear scan.
 */
public sealed class TestListOrder {
    /**
     * Tests are sorted by name. This is guaranteed for tests generated by
     * `rustc - - test` (see `mkTestsSlice` in
     * `compiler / rustcBuiltinMacros / src / testHarness.rs`).
     */
    public data object Sorted : TestListOrder()
    /**
     * Test order is unknown; binary search must not be used.
     */
    public data object Unsorted : TestListOrder()

    public fun rustEquals(other: TestListOrder): Boolean = this == other
}

/**
 * A list of tests, tagged with whether they are sorted by name.
 */
public open class TestList(
    public var tests: List<TestDescAndFn>,
    public var order: TestListOrder,
) {
    public companion object {
        public fun new(tests: List<TestDescAndFn>, order: TestListOrder): TestList = TestList(tests = tests, order = order)
    }
}

// port-lint: source options.rs
package io.github.kotlinmania.rustlang.test.options

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout

// Enums denoting options for test execution.

/**
 * Number of times to run a benchmarked function
 */
internal sealed class BenchMode {
    public data object Auto : BenchMode()
    public data object Single : BenchMode()

    public fun rustEquals(other: BenchMode): Boolean = this == other
}

/**
 * Whether test is expected to panic or not
 */
public sealed class ShouldPanic {
    public data object No : ShouldPanic()
    public data object Yes : ShouldPanic()
    public data class YesWithMessage(public var value: String) : ShouldPanic()

    public fun rustEquals(other: ShouldPanic): Boolean = this == other
}

/**
 * Whether should console output be colored or not
 */
public sealed class ColorConfig {
    public data object AutoColor : ColorConfig()
    public data object AlwaysColor : ColorConfig()
    public data object NeverColor : ColorConfig()
}

/**
 * Format of the test results output
 */
public sealed class OutputFormat {
    /**
     * Verbose output
     */
    public data object Pretty : OutputFormat()
    /**
     * Quiet output
     */
    public data object Terse : OutputFormat()
    /**
     * JSON output
     */
    public data object Json : OutputFormat()
    /**
     * JUnit output
     */
    public data object Junit : OutputFormat()

    public fun rustEquals(other: OutputFormat): Boolean = this == other
}

/**
 * Whether ignored test should be run or not
 */
public sealed class RunIgnored {
    public data object Yes : RunIgnored()
    public data object No : RunIgnored()
    /**
     * Run only ignored tests
     */
    public data object Only : RunIgnored()

    public fun rustEquals(other: RunIgnored): Boolean = this == other
}

public sealed class RunStrategy {
    /**
     * Runs the test in the current process, and sends the result back over the
     * supplied channel.
     */
    public data object InProcess : RunStrategy()
    /**
     * Spawns a subprocess to run the test, and sends the result back over the
     * supplied channel. Requires `argv[0]` to exist and point to the binary
     * that's currently running.
     */
    public data object SpawnPrimary : RunStrategy()
}

/**
 * Options for the test run defined by the caller (instead of CLI arguments).
 * In case we want to add other options as well, just add them in this struct.
 */
public open class Options(
    public var displayOutput: Boolean,
    public var panicAbort: Boolean,
) {
    public open fun displayOutput(displayOutput: Boolean): Options {
        this.displayOutput = displayOutput
        return this
    }

    public open fun panicAbort(panicAbort: Boolean): Options {
        this.panicAbort = panicAbort
        return this
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Options> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Options> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): Options =
                    Options(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 1L),
                    )
                override fun writeNative(addr: Long, value: Options) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.displayOutput)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.panicAbort)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Options =
                    Options(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Options) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.displayOutput)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.panicAbort)
                }
            }

        public fun new(): Options = Options(displayOutput = false, panicAbort = false)
    }
}

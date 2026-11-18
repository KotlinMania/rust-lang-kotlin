// port-lint: tests capture.rs
package io.github.kotlinmania.rustlang.backtrace.capture

import io.github.kotlinmania.rustlang.backtrace.backtrace.trace
import io.github.kotlinmania.rustlang.core.clone.clone

public class CaptureInlineTests {
    @kotlin.test.Test
    public fun testFrameConversion() {
        val frames = mutableListOf<BacktraceFrame>()
        trace({ frame ->
            val converted = BacktraceFrame.from(frame.clone())
            frames.add(converted)
            true
        })
        val manual = Backtrace.from(frames)
        manual.resolve()
        val frames1 = manual.frames()
        for (frame in frames1) {
            println("${frame.ip()}")
            println("${frame.symbolAddress()}")
            println("${frame.moduleBaseAddress()}")
            println("${frame.symbols()}")
        }
    }
}

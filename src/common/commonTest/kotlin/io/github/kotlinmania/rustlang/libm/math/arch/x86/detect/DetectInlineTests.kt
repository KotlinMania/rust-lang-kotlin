// port-lint: tests math/arch/x86/detect.rs
package io.github.kotlinmania.rustlang.libm.math.arch.x86.detect

// Using runtime feature detection requires atomics. Currently there are no x86 targets
// that support sse but not `AtomicPtr`.

public class DetectInlineTests {
    @kotlin.test.Test
    public fun checkMatchesStd() {
        val features = getCpuFeatures()
        for (i in 0 until ALL.len()) {
            val flag = ALL[i]
            val name = NAMES[i]
            val stdDetected =
                when (flag) {
                    sse3 -> isX86FeatureDetected("sse3")
                    f16c -> isX86FeatureDetected("f16c")
                    sse -> isX86FeatureDetected("sse")
                    sse2 -> isX86FeatureDetected("sse2")
                    ermsb -> isX86FeatureDetected("ermsb")
                    movrs -> { continue }
                    // only very recent support in std
                    fma -> isX86FeatureDetected("fma")
                    fma4 -> { continue }
                    // not yet supported in std
                    avx512fp16 -> isX86FeatureDetected("avx512fp16")
                    avx512bf16 -> isX86FeatureDetected("avx512bf16")
                    else -> error("untested CPU flag $name")
                }
            check(stdDetected == features.contains(flag))
        }
    }
}

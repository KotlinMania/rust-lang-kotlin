// port-lint: source detect/arch/mips64.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.checkFor

// Run-time feature detection on MIPS64.

/**
 * Each variant denotes a position in a bitset for a particular feature.
 *
 * PLEASE: do not use this, it is an implementation detail subject
 * to change.
 */
public sealed class Feature {
    /**
     * MIPS SIMD Architecture (MSA)
     */
    public data object Msa : Feature()

    public open fun toStr(): String =
        when (this) {
            Feature.msa -> "msa"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature::msa)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "msa" -> Result.Ok(Feature::msa)
                else -> Result.Err(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun msa(): Boolean = checkFor(Feature::msa)

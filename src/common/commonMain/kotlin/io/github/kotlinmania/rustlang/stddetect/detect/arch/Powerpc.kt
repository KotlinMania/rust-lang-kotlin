// port-lint: source detect/arch/powerpc.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.checkFor

// Run-time feature detection on PowerPC.

/**
 * Each variant denotes a position in a bitset for a particular feature.
 *
 * PLEASE: do not use this, it is an implementation detail subject
 * to change.
 */
public sealed class Feature {
    /**
     * Altivec
     */
    public data object Altivec : Feature()
    /**
     * VSX
     */
    public data object Vsx : Feature()
    /**
     * Power8
     */
    public data object Power8 : Feature()
    /**
     * Power8 altivec
     */
    public data object Power8Altivec : Feature()
    /**
     * Power8 vector
     */
    public data object Power8Vector : Feature()
    /**
     * Power8 crypto
     */
    public data object Power8Crypto : Feature()
    /**
     * Power9
     */
    public data object Power9 : Feature()
    /**
     * Power9 altivec
     */
    public data object Power9Altivec : Feature()
    /**
     * Power9 vector
     */
    public data object Power9Vector : Feature()

    public open fun toStr(): String =
        when (this) {
            Feature.altivec -> "altivec"
            Feature.vsx -> "vsx"
            Feature.power8 -> "power8"
            Feature.power8Altivec -> "power8-altivec"
            Feature.power8Vector -> "power8-vector"
            Feature.power8Crypto -> "power8-crypto"
            Feature.power9 -> "power9"
            Feature.power9Altivec -> "power9-altivec"
            Feature.power9Vector -> "power9-vector"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature::altivec, Feature::vsx, Feature::power8, Feature::power8Altivec, Feature::power8Vector, Feature::power8Crypto, Feature::power9, Feature::power9Altivec, Feature::power9Vector)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "altivec" -> Result.Ok(Feature::altivec)
                "vsx" -> Result.Ok(Feature::vsx)
                "power8" -> Result.Ok(Feature::power8)
                "power8-altivec" -> Result.Ok(Feature::power8Altivec)
                "power8-vector" -> Result.Ok(Feature::power8Vector)
                "power8-crypto" -> Result.Ok(Feature::power8Crypto)
                "power9" -> Result.Ok(Feature::power9)
                "power9-altivec" -> Result.Ok(Feature::power9Altivec)
                "power9-vector" -> Result.Ok(Feature::power9Vector)
                else -> Result.Err(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun altivec(): Boolean = checkFor(Feature::altivec)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vsx(): Boolean = checkFor(Feature::vsx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power8(): Boolean = checkFor(Feature::power8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power8Altivec(): Boolean = checkFor(Feature::power8Altivec)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power8Vector(): Boolean = checkFor(Feature::power8Vector)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power8Crypto(): Boolean = checkFor(Feature::power8Crypto)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power9(): Boolean = checkFor(Feature::power9)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power9Altivec(): Boolean = checkFor(Feature::power9Altivec)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power9Vector(): Boolean = checkFor(Feature::power9Vector)

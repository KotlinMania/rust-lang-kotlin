// port-lint: source detect/arch/powerpc.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch.powerpc

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

    private fun kmDiscriminant(): Int =
        when (this) {
        Altivec -> 0
        Vsx -> 1
        Power8 -> 2
        Power8Altivec -> 3
        Power8Vector -> 4
        Power8Crypto -> 5
        Power9 -> 6
        Power9Altivec -> 7
        Power9Vector -> 8
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public open fun toStr(): String =
        when (this) {
            Feature.Altivec -> "altivec"
            Feature.Vsx -> "vsx"
            Feature.Power8 -> "power8"
            Feature.Power8Altivec -> "power8-altivec"
            Feature.Power8Vector -> "power8-vector"
            Feature.Power8Crypto -> "power8-crypto"
            Feature.Power9 -> "power9"
            Feature.Power9Altivec -> "power9-altivec"
            Feature.Power9Vector -> "power9-vector"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature.Altivec, Feature.Vsx, Feature.Power8, Feature.Power8Altivec, Feature.Power8Vector, Feature.Power8Crypto, Feature.Power9, Feature.Power9Altivec, Feature.Power9Vector)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "altivec" -> Result.Ok<Feature, Unit>(Feature.Altivec)
                "vsx" -> Result.Ok<Feature, Unit>(Feature.Vsx)
                "power8" -> Result.Ok<Feature, Unit>(Feature.Power8)
                "power8-altivec" -> Result.Ok<Feature, Unit>(Feature.Power8Altivec)
                "power8-vector" -> Result.Ok<Feature, Unit>(Feature.Power8Vector)
                "power8-crypto" -> Result.Ok<Feature, Unit>(Feature.Power8Crypto)
                "power9" -> Result.Ok<Feature, Unit>(Feature.Power9)
                "power9-altivec" -> Result.Ok<Feature, Unit>(Feature.Power9Altivec)
                "power9-vector" -> Result.Ok<Feature, Unit>(Feature.Power9Vector)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun altivec(): Boolean = checkFor(Feature.Altivec)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vsx(): Boolean = checkFor(Feature.Vsx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power8(): Boolean = checkFor(Feature.Power8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power8Altivec(): Boolean = checkFor(Feature.Power8Altivec)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power8Vector(): Boolean = checkFor(Feature.Power8Vector)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power8Crypto(): Boolean = checkFor(Feature.Power8Crypto)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power9(): Boolean = checkFor(Feature.Power9)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power9Altivec(): Boolean = checkFor(Feature.Power9Altivec)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun power9Vector(): Boolean = checkFor(Feature.Power9Vector)

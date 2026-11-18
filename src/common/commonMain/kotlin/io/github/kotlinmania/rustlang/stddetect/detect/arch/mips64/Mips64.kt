// port-lint: source detect/arch/mips64.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch.mips64

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

    private fun kmDiscriminant(): Int =
        when (this) {
        Msa -> 0
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
            Feature.Msa -> "msa"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature.Msa)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "msa" -> Result.Ok<Feature, Unit>(Feature.Msa)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun msa(): Boolean = checkFor(Feature.Msa)

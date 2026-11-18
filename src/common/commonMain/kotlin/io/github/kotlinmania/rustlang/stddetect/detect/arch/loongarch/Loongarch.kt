// port-lint: source detect/arch/loongarch.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch.loongarch

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.checkFor

// Run-time feature detection on LoongArch.

/**
 * Each variant denotes a position in a bitset for a particular feature.
 *
 * PLEASE: do not use this, it is an implementation detail subject
 * to change.
 */
public sealed class Feature {
    /**
     * 32S
     */
    public data object T32s : Feature()
    /**
     * F
     */
    public data object F : Feature()
    /**
     * D
     */
    public data object D : Feature()
    /**
     * Frecipe
     */
    public data object Frecipe : Feature()
    /**
     * Div32
     */
    public data object Div32 : Feature()
    /**
     * LSX
     */
    public data object Lsx : Feature()
    /**
     * LASX
     */
    public data object Lasx : Feature()
    /**
     * LAM-BH
     */
    public data object LamBh : Feature()
    /**
     * LAM-CAS
     */
    public data object Lamcas : Feature()
    /**
     * LD-SEQ-SA
     */
    public data object LdSeqSa : Feature()
    /**
     * SCQ
     */
    public data object Scq : Feature()
    /**
     * LBT
     */
    public data object Lbt : Feature()
    /**
     * LVZ
     */
    public data object Lvz : Feature()
    /**
     * UAL
     */
    public data object Ual : Feature()

    private fun kmDiscriminant(): Int =
        when (this) {
        T32s -> 0
        F -> 1
        D -> 2
        Frecipe -> 3
        Div32 -> 4
        Lsx -> 5
        Lasx -> 6
        LamBh -> 7
        Lamcas -> 8
        LdSeqSa -> 9
        Scq -> 10
        Lbt -> 11
        Lvz -> 12
        Ual -> 13
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
            Feature.T32s -> "32s"
            Feature.F -> "f"
            Feature.D -> "d"
            Feature.Frecipe -> "frecipe"
            Feature.Div32 -> "div32"
            Feature.Lsx -> "lsx"
            Feature.Lasx -> "lasx"
            Feature.LamBh -> "lam-bh"
            Feature.Lamcas -> "lamcas"
            Feature.LdSeqSa -> "ld-seq-sa"
            Feature.Scq -> "scq"
            Feature.Lbt -> "lbt"
            Feature.Lvz -> "lvz"
            Feature.Ual -> "ual"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature.T32s, Feature.F, Feature.D, Feature.Frecipe, Feature.Div32, Feature.Lsx, Feature.Lasx, Feature.LamBh, Feature.Lamcas, Feature.LdSeqSa, Feature.Scq, Feature.Lbt, Feature.Lvz, Feature.Ual)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "32s" -> Result.Ok<Feature, Unit>(Feature.T32s)
                "f" -> Result.Ok<Feature, Unit>(Feature.F)
                "d" -> Result.Ok<Feature, Unit>(Feature.D)
                "frecipe" -> Result.Ok<Feature, Unit>(Feature.Frecipe)
                "div32" -> Result.Ok<Feature, Unit>(Feature.Div32)
                "lsx" -> Result.Ok<Feature, Unit>(Feature.Lsx)
                "lasx" -> Result.Ok<Feature, Unit>(Feature.Lasx)
                "lam-bh" -> Result.Ok<Feature, Unit>(Feature.LamBh)
                "lamcas" -> Result.Ok<Feature, Unit>(Feature.Lamcas)
                "ld-seq-sa" -> Result.Ok<Feature, Unit>(Feature.LdSeqSa)
                "scq" -> Result.Ok<Feature, Unit>(Feature.Scq)
                "lbt" -> Result.Ok<Feature, Unit>(Feature.Lbt)
                "lvz" -> Result.Ok<Feature, Unit>(Feature.Lvz)
                "ual" -> Result.Ok<Feature, Unit>(Feature.Ual)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun v32s(): Boolean = checkFor(Feature.T32s)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f(): Boolean = checkFor(Feature.F)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun d(): Boolean = checkFor(Feature.D)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun frecipe(): Boolean = checkFor(Feature.Frecipe)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun div32(): Boolean = checkFor(Feature.Div32)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lsx(): Boolean = checkFor(Feature.Lsx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lasx(): Boolean = checkFor(Feature.Lasx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lamBh(): Boolean = checkFor(Feature.LamBh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lamcas(): Boolean = checkFor(Feature.Lamcas)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ldSeqSa(): Boolean = checkFor(Feature.LdSeqSa)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun scq(): Boolean = checkFor(Feature.Scq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lbt(): Boolean = checkFor(Feature.Lbt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lvz(): Boolean = checkFor(Feature.Lvz)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ual(): Boolean = checkFor(Feature.Ual)

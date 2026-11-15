// port-lint: source detect/arch/loongarch.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch

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

    public open fun toStr(): String =
        when (this) {
            Feature.v32s -> "32s"
            Feature.f -> "f"
            Feature.d -> "d"
            Feature.frecipe -> "frecipe"
            Feature.div32 -> "div32"
            Feature.lsx -> "lsx"
            Feature.lasx -> "lasx"
            Feature.lamBh -> "lam-bh"
            Feature.lamcas -> "lamcas"
            Feature.ldSeqSa -> "ld-seq-sa"
            Feature.scq -> "scq"
            Feature.lbt -> "lbt"
            Feature.lvz -> "lvz"
            Feature.ual -> "ual"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature.v32s, Feature::f, Feature::d, Feature::frecipe, Feature::div32, Feature::lsx, Feature::lasx, Feature::lamBh, Feature::lamcas, Feature::ldSeqSa, Feature::scq, Feature::lbt, Feature::lvz, Feature::ual)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "32s" -> Result.Ok(Feature.v32s)
                "f" -> Result.Ok(Feature::f)
                "d" -> Result.Ok(Feature::d)
                "frecipe" -> Result.Ok(Feature::frecipe)
                "div32" -> Result.Ok(Feature::div32)
                "lsx" -> Result.Ok(Feature::lsx)
                "lasx" -> Result.Ok(Feature::lasx)
                "lam-bh" -> Result.Ok(Feature::lamBh)
                "lamcas" -> Result.Ok(Feature::lamcas)
                "ld-seq-sa" -> Result.Ok(Feature::ldSeqSa)
                "scq" -> Result.Ok(Feature::scq)
                "lbt" -> Result.Ok(Feature::lbt)
                "lvz" -> Result.Ok(Feature::lvz)
                "ual" -> Result.Ok(Feature::ual)
                else -> Result.Err(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun v32s(): Boolean = checkFor(v32s)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f(): Boolean = checkFor(::f)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun d(): Boolean = checkFor(::d)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun frecipe(): Boolean = checkFor(::frecipe)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun div32(): Boolean = checkFor(::div32)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lsx(): Boolean = checkFor(::lsx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lasx(): Boolean = checkFor(::lasx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lamBh(): Boolean = checkFor(::lamBh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lamcas(): Boolean = checkFor(::lamcas)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ldSeqSa(): Boolean = checkFor(::ldSeqSa)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun scq(): Boolean = checkFor(::scq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lbt(): Boolean = checkFor(::lbt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lvz(): Boolean = checkFor(::lvz)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ual(): Boolean = checkFor(::ual)

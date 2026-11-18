// port-lint: source detect/arch/s390x.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch.s390x

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.checkFor

// Run-time feature detection on s390x.

/**
 * Each variant denotes a position in a bitset for a particular feature.
 *
 * PLEASE: do not use this, it is an implementation detail subject
 * to change.
 */
public sealed class Feature {
    /**
     * s390x concurrent-functions facility
     */
    public data object ConcurrentFunctions : Feature()
    /**
     * s390x deflate-conversion facility
     */
    public data object DeflateConversion : Feature()
    /**
     * s390x enhanced-sort facility
     */
    public data object EnhancedSort : Feature()
    /**
     * s390x guarded-storage facility
     */
    public data object GuardedStorage : Feature()
    /**
     * s390x high-word facility
     */
    public data object HighWord : Feature()
    /**
     * s390x message-security-assist-extension3 facility
     */
    public data object MessageSecurityAssistExtension3 : Feature()
    /**
     * s390x message-security-assist-extension4 facility
     */
    public data object MessageSecurityAssistExtension4 : Feature()
    /**
     * s390x message-security-assist-extension5 facility
     */
    public data object MessageSecurityAssistExtension5 : Feature()
    /**
     * s390x message-security-assist-extension8 facility
     */
    public data object MessageSecurityAssistExtension8 : Feature()
    /**
     * s390x message-security-assist-extension9 facility
     */
    public data object MessageSecurityAssistExtension9 : Feature()
    /**
     * s390x message-security-assist-extension12 facility
     */
    public data object MessageSecurityAssistExtension12 : Feature()
    /**
     * s390x miscellaneous-extensions-2 facility
     */
    public data object MiscellaneousExtensions2 : Feature()
    /**
     * s390x miscellaneous-extensions-3 facility
     */
    public data object MiscellaneousExtensions3 : Feature()
    /**
     * s390x miscellaneous-extensions-4 facility
     */
    public data object MiscellaneousExtensions4 : Feature()
    /**
     * s390x nnp-assist facility
     */
    public data object NnpAssist : Feature()
    /**
     * s390x transactional-execution facility
     */
    public data object TransactionalExecution : Feature()
    /**
     * s390x vector facility
     */
    public data object Vector : Feature()
    /**
     * s390x vector-enhancements-1 facility
     */
    public data object VectorEnhancements1 : Feature()
    /**
     * s390x vector-enhancements-2 facility
     */
    public data object VectorEnhancements2 : Feature()
    /**
     * s390x vector-enhancements-3 facility
     */
    public data object VectorEnhancements3 : Feature()
    /**
     * s390x vector-packed-decimal facility
     */
    public data object VectorPackedDecimal : Feature()
    /**
     * s390x vector-packed-decimal-enhancement facility
     */
    public data object VectorPackedDecimalEnhancement : Feature()
    /**
     * s390x vector-packed-decimal-enhancement-2 facility
     */
    public data object VectorPackedDecimalEnhancement2 : Feature()
    /**
     * s390x vector-packed-decimal-enhancement-3 facility
     */
    public data object VectorPackedDecimalEnhancement3 : Feature()

    private fun kmDiscriminant(): Int =
        when (this) {
        ConcurrentFunctions -> 0
        DeflateConversion -> 1
        EnhancedSort -> 2
        GuardedStorage -> 3
        HighWord -> 4
        MessageSecurityAssistExtension3 -> 5
        MessageSecurityAssistExtension4 -> 6
        MessageSecurityAssistExtension5 -> 7
        MessageSecurityAssistExtension8 -> 8
        MessageSecurityAssistExtension9 -> 9
        MessageSecurityAssistExtension12 -> 10
        MiscellaneousExtensions2 -> 11
        MiscellaneousExtensions3 -> 12
        MiscellaneousExtensions4 -> 13
        NnpAssist -> 14
        TransactionalExecution -> 15
        Vector -> 16
        VectorEnhancements1 -> 17
        VectorEnhancements2 -> 18
        VectorEnhancements3 -> 19
        VectorPackedDecimal -> 20
        VectorPackedDecimalEnhancement -> 21
        VectorPackedDecimalEnhancement2 -> 22
        VectorPackedDecimalEnhancement3 -> 23
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
            Feature.ConcurrentFunctions -> "concurrent-functions"
            Feature.DeflateConversion -> "deflate-conversion"
            Feature.EnhancedSort -> "enhanced-sort"
            Feature.GuardedStorage -> "guarded-storage"
            Feature.HighWord -> "high-word"
            Feature.MessageSecurityAssistExtension3 -> "message-security-assist-extension3"
            Feature.MessageSecurityAssistExtension4 -> "message-security-assist-extension4"
            Feature.MessageSecurityAssistExtension5 -> "message-security-assist-extension5"
            Feature.MessageSecurityAssistExtension8 -> "message-security-assist-extension8"
            Feature.MessageSecurityAssistExtension9 -> "message-security-assist-extension9"
            Feature.MessageSecurityAssistExtension12 -> "message-security-assist-extension12"
            Feature.MiscellaneousExtensions2 -> "miscellaneous-extensions-2"
            Feature.MiscellaneousExtensions3 -> "miscellaneous-extensions-3"
            Feature.MiscellaneousExtensions4 -> "miscellaneous-extensions-4"
            Feature.NnpAssist -> "nnp-assist"
            Feature.TransactionalExecution -> "transactional-execution"
            Feature.Vector -> "vector"
            Feature.VectorEnhancements1 -> "vector-enhancements-1"
            Feature.VectorEnhancements2 -> "vector-enhancements-2"
            Feature.VectorEnhancements3 -> "vector-enhancements-3"
            Feature.VectorPackedDecimal -> "vector-packed-decimal"
            Feature.VectorPackedDecimalEnhancement -> "vector-packed-decimal-enhancement"
            Feature.VectorPackedDecimalEnhancement2 -> "vector-packed-decimal-enhancement-2"
            Feature.VectorPackedDecimalEnhancement3 -> "vector-packed-decimal-enhancement-3"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature.ConcurrentFunctions, Feature.DeflateConversion, Feature.EnhancedSort, Feature.GuardedStorage, Feature.HighWord, Feature.MessageSecurityAssistExtension3, Feature.MessageSecurityAssistExtension4, Feature.MessageSecurityAssistExtension5, Feature.MessageSecurityAssistExtension8, Feature.MessageSecurityAssistExtension9, Feature.MessageSecurityAssistExtension12, Feature.MiscellaneousExtensions2, Feature.MiscellaneousExtensions3, Feature.MiscellaneousExtensions4, Feature.NnpAssist, Feature.TransactionalExecution, Feature.Vector, Feature.VectorEnhancements1, Feature.VectorEnhancements2, Feature.VectorEnhancements3, Feature.VectorPackedDecimal, Feature.VectorPackedDecimalEnhancement, Feature.VectorPackedDecimalEnhancement2, Feature.VectorPackedDecimalEnhancement3)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "concurrent-functions" -> Result.Ok<Feature, Unit>(Feature.ConcurrentFunctions)
                "deflate-conversion" -> Result.Ok<Feature, Unit>(Feature.DeflateConversion)
                "enhanced-sort" -> Result.Ok<Feature, Unit>(Feature.EnhancedSort)
                "guarded-storage" -> Result.Ok<Feature, Unit>(Feature.GuardedStorage)
                "high-word" -> Result.Ok<Feature, Unit>(Feature.HighWord)
                "message-security-assist-extension3" -> Result.Ok<Feature, Unit>(Feature.MessageSecurityAssistExtension3)
                "message-security-assist-extension4" -> Result.Ok<Feature, Unit>(Feature.MessageSecurityAssistExtension4)
                "message-security-assist-extension5" -> Result.Ok<Feature, Unit>(Feature.MessageSecurityAssistExtension5)
                "message-security-assist-extension8" -> Result.Ok<Feature, Unit>(Feature.MessageSecurityAssistExtension8)
                "message-security-assist-extension9" -> Result.Ok<Feature, Unit>(Feature.MessageSecurityAssistExtension9)
                "message-security-assist-extension12" -> Result.Ok<Feature, Unit>(Feature.MessageSecurityAssistExtension12)
                "miscellaneous-extensions-2" -> Result.Ok<Feature, Unit>(Feature.MiscellaneousExtensions2)
                "miscellaneous-extensions-3" -> Result.Ok<Feature, Unit>(Feature.MiscellaneousExtensions3)
                "miscellaneous-extensions-4" -> Result.Ok<Feature, Unit>(Feature.MiscellaneousExtensions4)
                "nnp-assist" -> Result.Ok<Feature, Unit>(Feature.NnpAssist)
                "transactional-execution" -> Result.Ok<Feature, Unit>(Feature.TransactionalExecution)
                "vector" -> Result.Ok<Feature, Unit>(Feature.Vector)
                "vector-enhancements-1" -> Result.Ok<Feature, Unit>(Feature.VectorEnhancements1)
                "vector-enhancements-2" -> Result.Ok<Feature, Unit>(Feature.VectorEnhancements2)
                "vector-enhancements-3" -> Result.Ok<Feature, Unit>(Feature.VectorEnhancements3)
                "vector-packed-decimal" -> Result.Ok<Feature, Unit>(Feature.VectorPackedDecimal)
                "vector-packed-decimal-enhancement" -> Result.Ok<Feature, Unit>(Feature.VectorPackedDecimalEnhancement)
                "vector-packed-decimal-enhancement-2" -> Result.Ok<Feature, Unit>(Feature.VectorPackedDecimalEnhancement2)
                "vector-packed-decimal-enhancement-3" -> Result.Ok<Feature, Unit>(Feature.VectorPackedDecimalEnhancement3)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun concurrentFunctions(): Boolean = checkFor(Feature.ConcurrentFunctions)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun deflateConversion(): Boolean = checkFor(Feature.DeflateConversion)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun enhancedSort(): Boolean = checkFor(Feature.EnhancedSort)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun guardedStorage(): Boolean = checkFor(Feature.GuardedStorage)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun highWord(): Boolean = checkFor(Feature.HighWord)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension3(): Boolean = checkFor(Feature.MessageSecurityAssistExtension3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension4(): Boolean = checkFor(Feature.MessageSecurityAssistExtension4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension5(): Boolean = checkFor(Feature.MessageSecurityAssistExtension5)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension8(): Boolean = checkFor(Feature.MessageSecurityAssistExtension8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension9(): Boolean = checkFor(Feature.MessageSecurityAssistExtension9)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension12(): Boolean = checkFor(Feature.MessageSecurityAssistExtension12)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun miscellaneousExtensions2(): Boolean = checkFor(Feature.MiscellaneousExtensions2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun miscellaneousExtensions3(): Boolean = checkFor(Feature.MiscellaneousExtensions3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun miscellaneousExtensions4(): Boolean = checkFor(Feature.MiscellaneousExtensions4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun nnpAssist(): Boolean = checkFor(Feature.NnpAssist)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun transactionalExecution(): Boolean = checkFor(Feature.TransactionalExecution)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vector(): Boolean = checkFor(Feature.Vector)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorEnhancements1(): Boolean = checkFor(Feature.VectorEnhancements1)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorEnhancements2(): Boolean = checkFor(Feature.VectorEnhancements2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorEnhancements3(): Boolean = checkFor(Feature.VectorEnhancements3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorPackedDecimal(): Boolean = checkFor(Feature.VectorPackedDecimal)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorPackedDecimalEnhancement(): Boolean = checkFor(Feature.VectorPackedDecimalEnhancement)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorPackedDecimalEnhancement2(): Boolean = checkFor(Feature.VectorPackedDecimalEnhancement2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorPackedDecimalEnhancement3(): Boolean = checkFor(Feature.VectorPackedDecimalEnhancement3)

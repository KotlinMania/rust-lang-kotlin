// port-lint: source detect/arch/s390x.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch

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

    public open fun toStr(): String =
        when (this) {
            Feature.concurrentFunctions -> "concurrent-functions"
            Feature.deflateConversion -> "deflate-conversion"
            Feature.enhancedSort -> "enhanced-sort"
            Feature.guardedStorage -> "guarded-storage"
            Feature.highWord -> "high-word"
            Feature.messageSecurityAssistExtension3 -> "message-security-assist-extension3"
            Feature.messageSecurityAssistExtension4 -> "message-security-assist-extension4"
            Feature.messageSecurityAssistExtension5 -> "message-security-assist-extension5"
            Feature.messageSecurityAssistExtension8 -> "message-security-assist-extension8"
            Feature.messageSecurityAssistExtension9 -> "message-security-assist-extension9"
            Feature.messageSecurityAssistExtension12 -> "message-security-assist-extension12"
            Feature.miscellaneousExtensions2 -> "miscellaneous-extensions-2"
            Feature.miscellaneousExtensions3 -> "miscellaneous-extensions-3"
            Feature.miscellaneousExtensions4 -> "miscellaneous-extensions-4"
            Feature.nnpAssist -> "nnp-assist"
            Feature.transactionalExecution -> "transactional-execution"
            Feature.vector -> "vector"
            Feature.vectorEnhancements1 -> "vector-enhancements-1"
            Feature.vectorEnhancements2 -> "vector-enhancements-2"
            Feature.vectorEnhancements3 -> "vector-enhancements-3"
            Feature.vectorPackedDecimal -> "vector-packed-decimal"
            Feature.vectorPackedDecimalEnhancement -> "vector-packed-decimal-enhancement"
            Feature.vectorPackedDecimalEnhancement2 -> "vector-packed-decimal-enhancement-2"
            Feature.vectorPackedDecimalEnhancement3 -> "vector-packed-decimal-enhancement-3"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature::concurrentFunctions, Feature::deflateConversion, Feature::enhancedSort, Feature::guardedStorage, Feature::highWord, Feature::messageSecurityAssistExtension3, Feature::messageSecurityAssistExtension4, Feature::messageSecurityAssistExtension5, Feature::messageSecurityAssistExtension8, Feature::messageSecurityAssistExtension9, Feature::messageSecurityAssistExtension12, Feature::miscellaneousExtensions2, Feature::miscellaneousExtensions3, Feature::miscellaneousExtensions4, Feature::nnpAssist, Feature::transactionalExecution, Feature::vector, Feature::vectorEnhancements1, Feature::vectorEnhancements2, Feature::vectorEnhancements3, Feature::vectorPackedDecimal, Feature::vectorPackedDecimalEnhancement, Feature::vectorPackedDecimalEnhancement2, Feature::vectorPackedDecimalEnhancement3)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "concurrent-functions" -> Result.Ok<Feature, Unit>(Feature::concurrentFunctions)
                "deflate-conversion" -> Result.Ok<Feature, Unit>(Feature::deflateConversion)
                "enhanced-sort" -> Result.Ok<Feature, Unit>(Feature::enhancedSort)
                "guarded-storage" -> Result.Ok<Feature, Unit>(Feature::guardedStorage)
                "high-word" -> Result.Ok<Feature, Unit>(Feature::highWord)
                "message-security-assist-extension3" -> Result.Ok<Feature, Unit>(Feature::messageSecurityAssistExtension3)
                "message-security-assist-extension4" -> Result.Ok<Feature, Unit>(Feature::messageSecurityAssistExtension4)
                "message-security-assist-extension5" -> Result.Ok<Feature, Unit>(Feature::messageSecurityAssistExtension5)
                "message-security-assist-extension8" -> Result.Ok<Feature, Unit>(Feature::messageSecurityAssistExtension8)
                "message-security-assist-extension9" -> Result.Ok<Feature, Unit>(Feature::messageSecurityAssistExtension9)
                "message-security-assist-extension12" -> Result.Ok<Feature, Unit>(Feature::messageSecurityAssistExtension12)
                "miscellaneous-extensions-2" -> Result.Ok<Feature, Unit>(Feature::miscellaneousExtensions2)
                "miscellaneous-extensions-3" -> Result.Ok<Feature, Unit>(Feature::miscellaneousExtensions3)
                "miscellaneous-extensions-4" -> Result.Ok<Feature, Unit>(Feature::miscellaneousExtensions4)
                "nnp-assist" -> Result.Ok<Feature, Unit>(Feature::nnpAssist)
                "transactional-execution" -> Result.Ok<Feature, Unit>(Feature::transactionalExecution)
                "vector" -> Result.Ok<Feature, Unit>(Feature::vector)
                "vector-enhancements-1" -> Result.Ok<Feature, Unit>(Feature::vectorEnhancements1)
                "vector-enhancements-2" -> Result.Ok<Feature, Unit>(Feature::vectorEnhancements2)
                "vector-enhancements-3" -> Result.Ok<Feature, Unit>(Feature::vectorEnhancements3)
                "vector-packed-decimal" -> Result.Ok<Feature, Unit>(Feature::vectorPackedDecimal)
                "vector-packed-decimal-enhancement" -> Result.Ok<Feature, Unit>(Feature::vectorPackedDecimalEnhancement)
                "vector-packed-decimal-enhancement-2" -> Result.Ok<Feature, Unit>(Feature::vectorPackedDecimalEnhancement2)
                "vector-packed-decimal-enhancement-3" -> Result.Ok<Feature, Unit>(Feature::vectorPackedDecimalEnhancement3)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun concurrentFunctions(): Boolean = checkFor(::concurrentFunctions)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun deflateConversion(): Boolean = checkFor(::deflateConversion)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun enhancedSort(): Boolean = checkFor(::enhancedSort)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun guardedStorage(): Boolean = checkFor(::guardedStorage)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun highWord(): Boolean = checkFor(::highWord)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension3(): Boolean = checkFor(::messageSecurityAssistExtension3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension4(): Boolean = checkFor(::messageSecurityAssistExtension4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension5(): Boolean = checkFor(::messageSecurityAssistExtension5)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension8(): Boolean = checkFor(::messageSecurityAssistExtension8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension9(): Boolean = checkFor(::messageSecurityAssistExtension9)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun messageSecurityAssistExtension12(): Boolean = checkFor(::messageSecurityAssistExtension12)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun miscellaneousExtensions2(): Boolean = checkFor(::miscellaneousExtensions2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun miscellaneousExtensions3(): Boolean = checkFor(::miscellaneousExtensions3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun miscellaneousExtensions4(): Boolean = checkFor(::miscellaneousExtensions4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun nnpAssist(): Boolean = checkFor(::nnpAssist)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun transactionalExecution(): Boolean = checkFor(::transactionalExecution)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vector(): Boolean = checkFor(::vector)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorEnhancements1(): Boolean = checkFor(::vectorEnhancements1)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorEnhancements2(): Boolean = checkFor(::vectorEnhancements2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorEnhancements3(): Boolean = checkFor(::vectorEnhancements3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorPackedDecimal(): Boolean = checkFor(::vectorPackedDecimal)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorPackedDecimalEnhancement(): Boolean = checkFor(::vectorPackedDecimalEnhancement)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorPackedDecimalEnhancement2(): Boolean = checkFor(::vectorPackedDecimalEnhancement2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vectorPackedDecimalEnhancement3(): Boolean = checkFor(::vectorPackedDecimalEnhancement3)

package io.github.kotlinmania.rustlang.stddetect.detect.arch

import io.github.kotlinmania.rustlang.core.result.Result

// Note architecture:
public sealed class Feature {
    public data object Null : Feature()

    public open fun toStr(): String = ""

    public companion object {
        public val ALL: List<Feature> = listOf()

        public fun fromStr(s: String): Result<Feature, Unit> = Result.Err<Feature, Unit>(Unit)
    }
}

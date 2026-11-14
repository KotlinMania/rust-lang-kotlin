package io.github.kotlinmania.rustlang.stddetect.detect.arch

import io.github.kotlinmania.rustlang.core.result.Result

// Note architecture:
internal sealed class Feature {
    public data object Null : Feature()

    internal open fun toStr(): String = ""

    public companion object {
        internal val ALL: List<Feature> = listOf()

        internal fun fromStr(s: String): Result<Feature, Unit> = Result.Err(Unit)
    }
}

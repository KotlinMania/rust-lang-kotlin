package io.github.kotlinmania.rustlang.stddetect.detect.arch

import io.github.kotlinmania.rustlang.core.result.Result

// Note architecture:
public sealed class Feature {
    public data object Null : Feature()

    private fun kmDiscriminant(): Int =
        when (this) {
        Null -> 0
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public open fun toStr(): String = ""

    public companion object {
        public val ALL: List<Feature> = listOf()

        public fun fromStr(s: String): Result<Feature, Unit> = Result.Err<Feature, Unit>(Unit)
    }
}

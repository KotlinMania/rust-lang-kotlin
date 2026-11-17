package io.github.kotlinmania.rustlang.alloc.rawvec

import io.github.kotlinmania.rustlang.alloc.collections.TryReserveErrorKind.*

public sealed class AllocInit {
    /**
     * The contents of the new memory are uninitialized.
     */
    public data object Uninitialized : AllocInit()
    /**
     * The new memory is guaranteed to be zeroed.
     */
    public data object Zeroed : AllocInit()

    private fun kmDiscriminant(): Int =
        when (this) {
        Uninitialized -> 0
        Zeroed -> 1
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

public typealias Cap = UsizeNoHighBit

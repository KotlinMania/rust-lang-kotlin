package io.github.kotlinmania.rustlang.std.net

/**
 * Possible values which can be passed to the [TcpStream.shutdown] method.
 */
public sealed class Shutdown {
    /**
     * The reading portion of the [TcpStream] should be shut down.
     *
     * All currently blocked and future [reads] will return <code>[Ok]\(0)</code>.
     */
    public data object Read : Shutdown()
    /**
     * The writing portion of the [TcpStream] should be shut down.
     *
     * All currently blocked and future [writes] will return an error.
     */
    public data object Write : Shutdown()
    /**
     * Both the reading and the writing portions of the [TcpStream] should be shut down.
     *
     * See [Shutdown.Read] and [Shutdown.Write] for more information.
     */
    public data object Both : Shutdown()

    public fun rustEquals(other: Shutdown): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Read -> 0
        Write -> 1
        Both -> 2
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

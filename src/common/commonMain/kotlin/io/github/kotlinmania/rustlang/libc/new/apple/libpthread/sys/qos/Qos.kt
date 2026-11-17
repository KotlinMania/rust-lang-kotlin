// port-lint: source new/apple/libpthread/sys/qos.rs
package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.sys.qos

import io.github.kotlinmania.rustlang.libc.*

// Header: `sys / qos.h`
//
// <https://github.com/apple-oss-distributions/libpthread/blob/main/include/sys/qos.h>

public sealed class QosClassT(
    public val value: UInt,
) {
    public data object QosClassUserInteractive : QosClassT(0x21u)
    public data object QosClassUserInitiated : QosClassT(0x19u)
    public data object QosClassDefault : QosClassT(0x15u)
    public data object QosClassUtility : QosClassT(0x11u)
    public data object QosClassBackground : QosClassT(0x09u)
    public data object QosClassUnspecified : QosClassT(0x00u)

    public fun toByte(): Byte = value.toByte()

    public fun toUByte(): UByte = value.toUByte()

    public fun toShort(): Short = value.toShort()

    public fun toUShort(): UShort = value.toUShort()

    public fun toInt(): Int = value.toInt()

    public fun toUInt(): UInt = value

    public fun toLong(): Long = value.toLong()

    public fun toULong(): ULong = value.toULong()

    public open fun clone(): QosClassT = this
}

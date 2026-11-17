// port-lint: source to_bytes.rs
package io.github.kotlinmania.rustlang.coresimd.tobytes

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.coresimd.Simd

/**
 * Converts SIMD vectors to vectors of bytes
 */
public interface ToBytes<T, Bytes> {
    /**
     * Returns the memory representation of this integer as a byte array in native byte
     * order.
     */
    public fun toNeBytes(): Bytes

    /**
     * Returns the memory representation of this integer as a byte array in big-endian
     * (network) byte order.
     */
    public fun toBeBytes(): Bytes

    /**
     * Returns the memory representation of this integer as a byte array in little-endian
     * byte order.
     */
    public fun toLeBytes(): Bytes

    /**
     * Creates a native endian integer value from its memory representation as a byte array
     * in native endianness.
     */
    public fun fromNeBytes(bytes: Bytes): T

    /**
     * Creates an integer value from its representation as a byte array in big endian.
     */
    public fun fromBeBytes(bytes: Bytes): T

    /**
     * Creates an integer value from its representation as a byte array in little endian.
     */
    public fun fromLeBytes(bytes: Bytes): T
}

public fun Simd<UByte>.toNeBytes(): Simd<UByte> = this

public fun Simd<UByte>.toBeBytes(): Simd<UByte> {
    var selfMut = this
    if (!false) {
        selfMut = selfMut.swapBytes()
    }
    return selfMut.toNeBytes()
}

public fun Simd<UByte>.toLeBytes(): Simd<UByte> {
    var selfMut = this
    if (!true) {
        selfMut = selfMut.swapBytes()
    }
    return selfMut.toNeBytes()
}

public fun toBytesFromNeBytesSimdUByte(bytes: Simd<UByte>): Simd<UByte> = bytes

public fun toBytesFromBeBytesSimdUByte(bytes: Simd<UByte>): Simd<UByte> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromLeBytesSimdUByte(bytes: Simd<UByte>): Simd<UByte> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromNeBytesSimdUShort(bytes: Simd<UByte>): Simd<UShort> = transmuteWithLayouts<Simd<UByte>, Simd<UShort>>(bytes, UnresolvedTypeLayout<Simd<UByte>>(), UnresolvedTypeLayout<Simd<UShort>>())

public fun toBytesFromBeBytesSimdUShort(bytes: Simd<UByte>): Simd<UShort> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromLeBytesSimdUShort(bytes: Simd<UByte>): Simd<UShort> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromNeBytesSimdUInt(bytes: Simd<UByte>): Simd<UInt> = transmuteWithLayouts<Simd<UByte>, Simd<UInt>>(bytes, UnresolvedTypeLayout<Simd<UByte>>(), UnresolvedTypeLayout<Simd<UInt>>())

public fun toBytesFromBeBytesSimdUInt(bytes: Simd<UByte>): Simd<UInt> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromLeBytesSimdUInt(bytes: Simd<UByte>): Simd<UInt> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromNeBytesSimdULong(bytes: Simd<UByte>): Simd<ULong> = transmuteWithLayouts<Simd<UByte>, Simd<ULong>>(bytes, UnresolvedTypeLayout<Simd<UByte>>(), UnresolvedTypeLayout<Simd<ULong>>())

public fun toBytesFromBeBytesSimdULong(bytes: Simd<UByte>): Simd<ULong> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromLeBytesSimdULong(bytes: Simd<UByte>): Simd<ULong> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromNeBytesSimdInt(bytes: Simd<UByte>): Simd<Int> = transmuteWithLayouts<Simd<UByte>, Simd<Int>>(bytes, UnresolvedTypeLayout<Simd<UByte>>(), UnresolvedTypeLayout<Simd<Int>>())

public fun toBytesFromBeBytesSimdInt(bytes: Simd<UByte>): Simd<Int> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromLeBytesSimdInt(bytes: Simd<UByte>): Simd<Int> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromNeBytesSimdByte(bytes: Simd<UByte>): Simd<Byte> = transmuteWithLayouts<Simd<UByte>, Simd<Byte>>(bytes, UnresolvedTypeLayout<Simd<UByte>>(), UnresolvedTypeLayout<Simd<Byte>>())

public fun toBytesFromBeBytesSimdByte(bytes: Simd<UByte>): Simd<Byte> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromLeBytesSimdByte(bytes: Simd<UByte>): Simd<Byte> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromNeBytesSimdShort(bytes: Simd<UByte>): Simd<Short> = transmuteWithLayouts<Simd<UByte>, Simd<Short>>(bytes, UnresolvedTypeLayout<Simd<UByte>>(), UnresolvedTypeLayout<Simd<Short>>())

public fun toBytesFromBeBytesSimdShort(bytes: Simd<UByte>): Simd<Short> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromLeBytesSimdShort(bytes: Simd<UByte>): Simd<Short> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromNeBytesSimdLong(bytes: Simd<UByte>): Simd<Long> = transmuteWithLayouts<Simd<UByte>, Simd<Long>>(bytes, UnresolvedTypeLayout<Simd<UByte>>(), UnresolvedTypeLayout<Simd<Long>>())

public fun toBytesFromBeBytesSimdLong(bytes: Simd<UByte>): Simd<Long> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromLeBytesSimdLong(bytes: Simd<UByte>): Simd<Long> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        ret.swapBytes()
    }
}

public fun toBytesFromNeBytesSimdFloat(bytes: Simd<UByte>): Simd<Float> = transmuteWithLayouts<Simd<UByte>, Simd<Float>>(bytes, UnresolvedTypeLayout<Simd<UByte>>(), UnresolvedTypeLayout<Simd<Float>>())

public fun toBytesFromBeBytesSimdFloat(bytes: Simd<UByte>): Simd<Float> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        Simd.fromBits(run { val swapVal0 = (ret.toBits()); ((((swapVal0) shl 56)) or (((swapVal0) and 0x000000000000FF00uL) shl 40) or (((swapVal0) and 0x0000000000FF0000uL) shl 24) or (((swapVal0) and 0x00000000FF000000uL) shl 8) or ((((swapVal0) shr 8) and 0x00000000FF000000uL)) or ((((swapVal0) shr 24) and 0x0000000000FF0000uL)) or ((((swapVal0) shr 40) and 0x000000000000FF00uL)) or ((swapVal0) shr 56)) })
    }
}

public fun toBytesFromLeBytesSimdFloat(bytes: Simd<UByte>): Simd<Float> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        Simd.fromBits(run { val swapVal0 = (ret.toBits()); ((((swapVal0) shl 56)) or (((swapVal0) and 0x000000000000FF00uL) shl 40) or (((swapVal0) and 0x0000000000FF0000uL) shl 24) or (((swapVal0) and 0x00000000FF000000uL) shl 8) or ((((swapVal0) shr 8) and 0x00000000FF000000uL)) or ((((swapVal0) shr 24) and 0x0000000000FF0000uL)) or ((((swapVal0) shr 40) and 0x000000000000FF00uL)) or ((swapVal0) shr 56)) })
    }
}

public fun toBytesFromNeBytesSimdDouble(bytes: Simd<UByte>): Simd<Double> = transmuteWithLayouts<Simd<UByte>, Simd<Double>>(bytes, UnresolvedTypeLayout<Simd<UByte>>(), UnresolvedTypeLayout<Simd<Double>>())

public fun toBytesFromBeBytesSimdDouble(bytes: Simd<UByte>): Simd<Double> {
    val ret = Simd.fromNeBytes(bytes)
    return if (false) {
        ret
    } else {
        Simd.fromBits(run { val swapVal0 = (ret.toBits()); ((((swapVal0) shl 56)) or (((swapVal0) and 0x000000000000FF00uL) shl 40) or (((swapVal0) and 0x0000000000FF0000uL) shl 24) or (((swapVal0) and 0x00000000FF000000uL) shl 8) or ((((swapVal0) shr 8) and 0x00000000FF000000uL)) or ((((swapVal0) shr 24) and 0x0000000000FF0000uL)) or ((((swapVal0) shr 40) and 0x000000000000FF00uL)) or ((swapVal0) shr 56)) })
    }
}

public fun toBytesFromLeBytesSimdDouble(bytes: Simd<UByte>): Simd<Double> {
    val ret = Simd.fromNeBytes(bytes)
    return if (true) {
        ret
    } else {
        Simd.fromBits(run { val swapVal0 = (ret.toBits()); ((((swapVal0) shl 56)) or (((swapVal0) and 0x000000000000FF00uL) shl 40) or (((swapVal0) and 0x0000000000FF0000uL) shl 24) or (((swapVal0) and 0x00000000FF000000uL) shl 8) or ((((swapVal0) shr 8) and 0x00000000FF000000uL)) or ((((swapVal0) shr 24) and 0x0000000000FF0000uL)) or ((((swapVal0) shr 40) and 0x000000000000FF00uL)) or ((swapVal0) shr 56)) })
    }
}

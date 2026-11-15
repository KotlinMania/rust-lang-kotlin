// port-lint: source to_bytes.rs
package io.github.kotlinmania.rustlang.coresimd.tobytes

import io.github.kotlinmania.rustlang.Simd
import io.github.kotlinmania.rustlang.core.mem.transmute

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

public fun Simd<UByte>.toNeBytes(): Simd<UByte> = transmute(this)

public fun Simd<UByte>.toBeBytes(): Simd<UByte> {
    if (!false) {
        this = this.swapBytes()
    }
    return this.toNeBytes()
}

public fun Simd<UByte>.toLeBytes(): Simd<UByte> {
    if (!true) {
        this = this.swapBytes()
    }
    return this.toNeBytes()
}

public fun toBytesFromNeBytesSimdUByte(bytes: Simd<UByte>): Simd<UByte> = transmute(bytes)

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

public fun toBytesFromNeBytesSimdUShort(bytes: Simd<UByte>): Simd<UShort> = transmute(bytes)

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

public fun toBytesFromNeBytesSimdUInt(bytes: Simd<UByte>): Simd<UInt> = transmute(bytes)

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

public fun toBytesFromNeBytesSimdULong(bytes: Simd<UByte>): Simd<ULong> = transmute(bytes)

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

public fun toBytesFromNeBytesSimdInt(bytes: Simd<UByte>): Simd<Int> = transmute(bytes)

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

public fun toBytesFromNeBytesSimdByte(bytes: Simd<UByte>): Simd<Byte> = transmute(bytes)

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

public fun toBytesFromNeBytesSimdShort(bytes: Simd<UByte>): Simd<Short> = transmute(bytes)

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

public fun toBytesFromNeBytesSimdLong(bytes: Simd<UByte>): Simd<Long> = transmute(bytes)

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

public fun toBytesFromNeBytesSimdFloat(bytes: Simd<UByte>): Simd<Float> = transmute(bytes)

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

public fun toBytesFromNeBytesSimdDouble(bytes: Simd<UByte>): Simd<Double> = transmute(bytes)

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

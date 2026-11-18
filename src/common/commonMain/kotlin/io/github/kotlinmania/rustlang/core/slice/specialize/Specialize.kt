// port-lint: source slice/specialize.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.slice.specialize

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.intrinsics.kmWriteBytes
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.num.castUnsigned
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.splitLastMut
import kotlin.experimental.or

public interface SpecFill<T> {
    public fun specFill(value: T)
}

public fun <T> List<T>.specFill(value: T) {
    when (val matchVal = this.splitLastMut()) {
        null -> {}
        else -> {
            val last = matchVal.first
            val elems = matchVal.second
            for (el in elems) {
                el.cloneFrom(value)
            }
            last = value
        }
    }
}

public fun <T : TrivialClone<T>> List<T>.specFillT(value: T) {
    for (idx in this.indices) {
        this[idx] = read(value, typeLayoutT = UnresolvedTypeLayout<T>())
    }
}

public fun List<UByte>.specFill(value: UByte) {
    // SAFETY: The pointer is derived from a reference, so it's writable.
    run {
        kmWriteBytes(this.asMutPtr().rawAddress, (value).toUByte(), (this.size).toLong())
    }
}

public fun List<Byte>.specFill(value: Byte) {
    // SAFETY: The pointer is derived from a reference, so it's writable.
    run {
        kmWriteBytes(this.asMutPtr().rawAddress, (value.castUnsigned()).toUByte(), (this.size).toLong())
    }
}

public fun List<UShort>.specFill(value: UShort) {
    if ((false && this.size > 32) || false) {
        val bytes = run { val rawBits = value.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }
        if (value == run { val bytesTmp0 = UByteArray((sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)).toInt()) { bytes[0] }; ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() }) {
            run {
                kmWriteBytes(this.asMutPtr().rawAddress, (bytes[0]).toUByte(), (this.size).toLong())
            }
            return
        }
    }
    for (idx in this.indices) {
        this[idx] = value
    }
}

public fun List<Short>.specFill(value: Short) {
    if ((false && this.size > 32) || false) {
        val bytes = run { val rawBits = value.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }
        if (value == run { val bytesTmp1 = UByteArray((sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()) { bytes[0] }; ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toShort() }) {
            run {
                kmWriteBytes(this.asMutPtr().rawAddress, (bytes[0]).toUByte(), (this.size).toLong())
            }
            return
        }
    }
    for (idx in this.indices) {
        this[idx] = value
    }
}

public fun List<UInt>.specFill(value: UInt) {
    if ((false && this.size > 32) || false) {
        val bytes = run { val rawBits = value; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }
        if (value == run { val bytesTmp2 = UByteArray((sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32)).toInt()) { bytes[0] }; ((bytesTmp2[3].toUInt() shl 24) or (bytesTmp2[2].toUInt() shl 16) or (bytesTmp2[1].toUInt() shl 8) or bytesTmp2[0].toUInt()) }) {
            run {
                kmWriteBytes(this.asMutPtr().rawAddress, (bytes[0]).toUByte(), (this.size).toLong())
            }
            return
        }
    }
    for (idx in this.indices) {
        this[idx] = value
    }
}

public fun List<Int>.specFill(value: Int) {
    if ((false && this.size > 32) || false) {
        val bytes = run { val rawBits = value.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }
        if (value == run { val bytesTmp3 = UByteArray((sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()) { bytes[0] }; ((bytesTmp3[3].toInt() shl 24) or (bytesTmp3[2].toInt() shl 16) or (bytesTmp3[1].toInt() shl 8) or bytesTmp3[0].toInt()) }) {
            run {
                kmWriteBytes(this.asMutPtr().rawAddress, (bytes[0]).toUByte(), (this.size).toLong())
            }
            return
        }
    }
    for (idx in this.indices) {
        this[idx] = value
    }
}

public fun List<ULong>.specFill(value: ULong) {
    if ((false && this.size > 32) || false) {
        val bytes = run { val rawBits = value; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }
        if (value == run { val bytesTmp4 = UByteArray((sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)).toInt()) { bytes[0] }; ((bytesTmp4[7].toULong() shl 56) or (bytesTmp4[6].toULong() shl 48) or (bytesTmp4[5].toULong() shl 40) or (bytesTmp4[4].toULong() shl 32) or (bytesTmp4[3].toULong() shl 24) or (bytesTmp4[2].toULong() shl 16) or (bytesTmp4[1].toULong() shl 8) or bytesTmp4[0].toULong()) }) {
            run {
                kmWriteBytes(this.asMutPtr().rawAddress, (bytes[0]).toUByte(), (this.size).toLong())
            }
            return
        }
    }
    for (idx in this.indices) {
        this[idx] = value
    }
}

public fun List<Long>.specFill(value: Long) {
    if ((false && this.size > 32) || false) {
        val bytes = run { val rawBits = value.toULong(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }
        if (value == run { val bytesTmp5 = UByteArray((sizeOf<Long>(typeLayoutT = PrimitiveLayouts.I64)).toInt()) { bytes[0] }; ((bytesTmp5[7].toLong() shl 56) or (bytesTmp5[6].toLong() shl 48) or (bytesTmp5[5].toLong() shl 40) or (bytesTmp5[4].toLong() shl 32) or (bytesTmp5[3].toLong() shl 24) or (bytesTmp5[2].toLong() shl 16) or (bytesTmp5[1].toLong() shl 8) or bytesTmp5[0].toLong()) }) {
            run {
                kmWriteBytes(this.asMutPtr().rawAddress, (bytes[0]).toUByte(), (this.size).toLong())
            }
            return
        }
    }
    for (idx in this.indices) {
        this[idx] = value
    }
}

public fun List<U128>.specFill(value: U128) {
    if ((false && this.size > 32) || false) {
        val bytes = value.toLeBytes()
        if (value == U128.fromLeBytes(UByteArray((sizeOf<U128>(typeLayoutT = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))).toInt()) { bytes[0] })) {
            run {
                kmWriteBytes(this.asMutPtr().rawAddress, (bytes[0]).toUByte(), (this.size).toLong())
            }
            return
        }
    }
    for (idx in this.indices) {
        this[idx] = value
    }
}

public fun List<I128>.specFill(value: I128) {
    if ((false && this.size > 32) || false) {
        val bytes = value.toLeBytes()
        if (value == I128.fromLeBytes(UByteArray((sizeOf<I128>(typeLayoutT = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))).toInt()) { bytes[0] })) {
            run {
                kmWriteBytes(this.asMutPtr().rawAddress, (bytes[0]).toUByte(), (this.size).toLong())
            }
            return
        }
    }
    for (idx in this.indices) {
        this[idx] = value
    }
}

public fun List<Int>.specFillInt(value: Int) {
    if ((false && this.size > 32) || false) {
        val bytes = run { val rawBits = value.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }
        if (value == run { val bytesTmp6 = UByteArray((sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)).toInt()) { bytes[0] }; ((bytesTmp6[3].toInt() shl 24) or (bytesTmp6[2].toInt() shl 16) or (bytesTmp6[1].toInt() shl 8) or bytesTmp6[0].toInt()) }) {
            run {
                kmWriteBytes(this.asMutPtr().rawAddress, (bytes[0]).toUByte(), (this.size).toLong())
            }
            return
        }
    }
    for (idx in this.indices) {
        this[idx] = value
    }
}

// port-lint: source bridge/rpc.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.procmacro.bridge

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.procmacro.buffer.Buffer

// Serialization for client-server communication.

public interface Encode<S> {
    public fun encode(w: Buffer, s: S)
}

public interface Decode<S, T> {
    public fun decode(r: List<UByte>, s: S): T
}

public fun <S> Unit.encode(arg0: Buffer, arg1: S) {}

public fun <S> decode(arg0: List<UByte>, arg1: S) {}

public fun <S> UByte.encode(w: Buffer, arg1: S) {
    w.push(this)
}

public fun <S> decodeUByte(r: List<UByte>, arg1: S): UByte {
    var r1 = r
    val x = r1[0]
    r1 = ((r1).subList(1, (r1).size))
    return x
}

public fun <S> UInt.encode(w: Buffer, arg1: S) {
    w.extendFromArray(run { val rawBits = this; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) })
}

public fun <S> decodeUInt(r: List<UByte>, arg1: S): UInt {
    var r1 = r
    val N: Int = sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32)
    val bytes = MutableList((N).toInt()) { 0 }
    bytes.copyFromSlice(((r1).subList(0, N)))
    r1 = ((r1).subList(N, (r1).size))
    return UInt.fromLeBytes(bytes)
}

public fun <S> Int.encode(w: Buffer, arg1: S) {
    w.extendFromArray(run { val rawBits = this.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) })
}

public fun <S> decodeInt(r: List<UByte>, arg1: S): Int {
    var r1 = r
    val N: Int = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)
    val bytes = MutableList((N).toInt()) { 0 }
    bytes.copyFromSlice(((r1).subList(0, N)))
    r1 = ((r1).subList(N, (r1).size))
    return Int.fromLeBytes(bytes)
}

public fun <S> Boolean.encode(w: Buffer, s: S) {
    (this.toUByte()).encode(w, s)
}

public fun <S> decodeBoolean(r: List<UByte>, s: S): Boolean =
    when (UByte.Decode(r, s)) {
        0 -> false
        1 -> true
        else -> error("unreachable")
    }

public fun <S> NonZero<UInt>.encode(w: Buffer, s: S) {
    this.get().encode(w, s)
}

public fun <S> decodeNonZeroUInt(r: List<UByte>, s: S): NonZero<UInt> = NonZero.new(UInt.Decode(r, s)).unwrap()

public fun <S, A : Encode<S>, B : Encode<S>> Pair<A, B>.encode(w: Buffer, s: S) {
    this.value.encode(w, s)
    this.field1.encode(w, s)
}

public fun <S, A : Decode<S, A>, B : Decode<S, B>> decodeAB(r: List<UByte>, s: S): Pair<A, B> = Pair(Decode.Decode(r, s), Decode.Decode(r, s))

public fun <S> String.encode(w: Buffer, s: S) {
    val bytes = this.encodeToByteArray().map { it.toUByte() }
    bytes.size.encode(w, s)
    w.extendFromSlice(bytes)
}

public fun <S> decodeString(r: List<UByte>, s: S): String {
    var r1 = r
    val len = Int.Decode(r1, s)
    val xs = ((r1).subList(0, len))
    r1 = ((r1).subList(len, (r1).size))
    return String.fromUtf8(xs).unwrap()
}

public fun <S> String.encodeS(w: Buffer, s: S) {
    this.encode(w, s)
}

public fun <S, T : Encode<S>> List<T>.encode(w: Buffer, s: S) {
    this.size.encode(w, s)
    for (x in this) {
        x.encode(w, s)
    }
}

public fun <S, T : Decode<S, T>> decodeVecT(r: List<UByte>, s: S, decodeTFn: (List<UByte>, S) -> T): List<T> {
    val len = Int.Decode(r, s)
    val vec = ArrayList<T>(len)
    for (unused in 0 until len) {
        vec.add(decodeTFn(r, s))
    }
    return vec
}

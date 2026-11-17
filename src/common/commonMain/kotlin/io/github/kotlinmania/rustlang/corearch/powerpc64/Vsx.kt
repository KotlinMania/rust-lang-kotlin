// port-lint: source powerpc64/vsx.rs
package io.github.kotlinmania.rustlang.corearch.powerpc64

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorSignedInt

// PowerPC Vector Scalar eXtensions (VSX) intrinsics.
//
// The references are: [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA
// NVlink)] and [POWER ISA v3.0B (for POWER9)].
//
// [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA NVlink)]: https://ibm.box.com/s/jd5w15gz301s5b5dt375mshpq9c3lh4u
// [POWER ISA v3.0B (for POWER9)]: https://ibm.box.com/s/1hzcwkwf8rbju5h9iyf44wm94amnlcrv

public expect fun lxvl(a: ConstPtr, l: ULong): VectorSignedInt

public expect fun stxvl(v: VectorSignedInt, a: MutPtr, l: ULong)

public fun vecLxvl(p: ConstPtr, l: ULong): VectorSignedInt = lxvl(p, l shl 56)

public interface VectorXloads<out Result> {
    public fun vecXlLen(l: Int): Result
}

public fun ConstPtr.vecXlLen(l: Int): TTL = transmuteWithLayouts<VectorSignedInt, TTL>(vecLxvl(ConstPtr(this.rawAddress, this.rawMeta), l), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<TTL>())

public fun ConstPtr.vecXlLenFromConstPtrUByte(l: Int): TTL = transmuteWithLayouts<VectorSignedInt, TTL>(vecLxvl(this, l), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<TTL>())

public fun ConstPtr.vecXlLenFromConstPtrShort(l: Int): TTL = transmuteWithLayouts<VectorSignedInt, TTL>(vecLxvl(ConstPtr(this.rawAddress, this.rawMeta), l), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<TTL>())

public fun ConstPtr.vecXlLenFromConstPtrUShort(l: Int): TTL = transmuteWithLayouts<VectorSignedInt, TTL>(vecLxvl(ConstPtr(this.rawAddress, this.rawMeta), l), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<TTL>())

public fun ConstPtr.vecXlLenFromConstPtrInt(l: Int): TTL = transmuteWithLayouts<VectorSignedInt, TTL>(vecLxvl(ConstPtr(this.rawAddress, this.rawMeta), l), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<TTL>())

public fun ConstPtr.vecXlLenFromConstPtrUInt(l: Int): TTL = transmuteWithLayouts<VectorSignedInt, TTL>(vecLxvl(ConstPtr(this.rawAddress, this.rawMeta), l), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<TTL>())

public fun ConstPtr.vecXlLenFromConstPtrFloat(l: Int): TTL = transmuteWithLayouts<VectorSignedInt, TTL>(vecLxvl(ConstPtr(this.rawAddress, this.rawMeta), l), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<TTL>())

public fun vecStxvl(v: VectorSignedInt, a: MutPtr, l: ULong) {
    stxvl(v, a, l shl 56)
}

public interface VectorXstores<Out> {
    public fun vecXstLen(p: Out, l: Int)
}

public fun TTL.vecXstLen(a: MutPtr, l: ULong) {
    stxvl(transmuteWithLayouts<TTL, Unit>(this, UnresolvedTypeLayout<TTL>(), UnresolvedTypeLayout<Unit>()), MutPtr(a.rawAddress, a.rawMeta), l)
}

/**
 * Vector Load with Length
 *
 * ## Purpose
 * Loads a vector of a specified byte length.
 *
 * ## Result value
 * Loads the number of bytes specified by b from the address specified in a.
 * Initializes elements in order from the byte stream (as defined by the endianness of the
 * target). Any bytes of elements that cannot be initialized from the number of loaded bytes have
 * a zero value.
 *
 * Between 0 and 16 bytes, inclusive, will be loaded. The length is specified by the
 * least-significant byte of b, as min (b mod 256, 16). The behavior is undefined if the length
 * argument is outside of the range 0–255, or if it is not a multiple of the vector element size.
 *
 * ## Notes
 * vecXlLen should not be used to load from cache-inhibited memory.
 */
public fun <Result, T> vecXlLen(p: T, len: Int): Result where T : VectorXloads<Result> = p.vecXlLen(len)

/**
 * Vector Store with Length
 *
 * ## Purpose
 *
 * Stores a vector of a specified byte length.
 *
 * ## Operation
 *
 * Stores the number of bytes specified by c of the vector a to the address specified
 * in b. The bytes are obtained starting from the lowest-numbered byte of the lowest-numbered
 * element (as defined by the endianness of the target). All bytes of an element are accessed
 * before proceeding to the next higher element.
 *
 * Between 0 and 16 bytes, inclusive, will be stored. The length is specified by the
 * least-significant byte of c, as min (c mod 256, 16). The behavior is undefined if the length
 * argument is outside of the range 0–255, or if it is not a multiple of the vector element size.
 *
 * ## Notes
 * vecXstLen should not be used to store to cache-inhibited memory.
 */
public fun <Out, T> vecXstLen(v: T, a: Out, l: Int) where T : VectorXstores<Out> {
    v.vecXstLen(a, l)
}

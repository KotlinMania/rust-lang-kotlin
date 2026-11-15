// port-lint: source bridge/fxhash.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.fxhash

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.rustRotateLeft

// This is a copy of the `rustcHash` crate, adapted to work as a module.
//
// If in the future it becomes more reasonable to add dependencies to
// `procMacro`, this module should be removed and replaced with a dependency
// on the `rustcHash` crate.

/**
 * Type alias for a hashmap using the `fx` hash algorithm.
 */
public typealias FxHashMap<K, V> = MutableMap<K, V>

/**
 * A speedy hash algorithm for use within rustc. The hashmap in alloc by
 * default uses SipHash which isn't quite as speedy as we want. In the compiler
 * we're not really worried about DOS attempts, so we use a fast
 * non-cryptographic hash.
 *
 * This is the same as the algorithm used by Firefox -- which is a homespun
 * one not based on any widely-known algorithm -- though modified to produce
 * 64-bit hash values instead of 32-bit hash values. It consistently
 * out-performs an FNV-based hash within rustc itself -- the collision rate is
 * similar or slightly worse than FNV, but the speed of the hash function
 * itself is much higher because it works on up to 8 bytes at a time.
 */
public open class FxHasher(
    public var hash: Int,
) {
    public open fun addToHash(i: Int) {
        val K: Int = 0x517cc1b727220a95L.toInt()
        this.hash = rustRotateLeft(this.hash, 5u).bitxor(i).wrappingMul(K)
    }

    public open fun write(bytes: List<UByte>) {
        var bytes1 = bytes
        val hash = FxHasher(hash = this.hash)
        check(sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) <= 8)
        while (bytes1.size >= sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)) {
            hash.addToHash(run { val bytesTmp0 = ((bytes1).subList(0, sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))); ((bytesTmp0[3].toInt() shl 24) or (bytesTmp0[2].toInt() shl 16) or (bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()) })
            bytes1 = ((bytes1).subList(sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64), (bytes1).size))
        }
        if ((sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) > 4) && (bytes1.size >= 4)) {
            hash.addToHash((run { val bytesTmp1 = ((bytes1).subList(0, 4)); ((bytesTmp1[3].toUInt() shl 24) or (bytesTmp1[2].toUInt() shl 16) or (bytesTmp1[1].toUInt() shl 8) or bytesTmp1[0].toUInt()) }).toInt())
            bytes1 = ((bytes1).subList(4, (bytes1).size))
        }
        if ((sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) > 2) && bytes1.size >= 2) {
            hash.addToHash((run { val bytesTmp2 = ((bytes1).subList(0, 2)); ((bytesTmp2[1].toInt() shl 8) or bytesTmp2[0].toInt()).toUShort() }).toInt())
            bytes1 = ((bytes1).subList(2, (bytes1).size))
        }
        if ((sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) > 1) && !bytes1.isEmpty()) {
            hash.addToHash(bytes1[0].toInt())
        }
        this.hash = hash.hash
    }

    public open fun writeU8(i: UByte) {
        this.addToHash(i.toInt())
    }

    public open fun writeU16(i: UShort) {
        this.addToHash(i.toInt())
    }

    public open fun writeU32(i: UInt) {
        this.addToHash(i.toInt())
    }

    public open fun writeU64(i: ULong) {
        this.addToHash(i.toInt())
    }

    public open fun writeUsize(i: Int) {
        this.addToHash(i)
    }

    public open fun finish(): ULong = this.hash.toULong()

    public companion object {
        public fun typeLayout(): TypeLayout<FxHasher> =
            object : TypeLayout<FxHasher> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FxHasher =
                    FxHasher(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FxHasher) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.hash)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FxHasher =
                    FxHasher(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FxHasher) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.hash)
                }
            }
    }
}

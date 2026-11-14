// port-lint: source hash/sip.rs
package io.github.kotlinmania.rustlang.core.hash.sip

import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.rustRotateLeft
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

// An implementation of SipHash.
//
// Loads an integer of the desired type from a byte stream, in LE order. Uses
// `copyNonoverlapping` to let the compiler generate the most efficient way
// to load it from a possibly unaligned address.
//
// Safety: this performs unchecked indexing of `$ buf` at
// `$ i .. $ i + sizeOf<$ intTy>()`, so that must be in-bounds.

/**
 * An implementation of SipHash 1-3.
 *
 * This is currently the default hashing function used by standard library
 * (e.g., `collections.HashMap` uses it by default).
 *
 * See: <https://github.com/veorq/SipHash>
 */
public open class SipHasher13 internal constructor(
    hasherCtorArg: Hasher<Sip13Rounds>,
) {
    public var hasher: Hasher<Sip13Rounds> = hasherCtorArg
        internal set

    public open fun write(msg: List<UByte>) {
        this.hasher.write(msg)
    }

    public open fun writeStr(s: String) {
        this.hasher.append(s)
    }

    public open fun finish(): ULong = this.hasher.finish()

    public companion object {
        public operator fun invoke(
            hasher: Hasher<Sip13Rounds>,
        ): SipHasher13 = SipHasher13(
            hasherCtorArg = hasher,
        )

        /**
         * Creates a new `SipHasher13` with the two initial keys set to 0.
         */
        public fun new(): SipHasher13 = SipHasher13.newWithKeys(0uL, 0uL)

        /**
         * Creates a `SipHasher13` that is keyed off the provided keys.
         */
        public fun newWithKeys(key0: ULong, key1: ULong): SipHasher13 = SipHasher13(hasher = Hasher.newWithKeys(key0, key1))
    }
}

/**
 * An implementation of SipHash 2-4.
 *
 * See: <https://github.com/veorq/SipHash>
 */
public open class SipHasher24 internal constructor(
    hasherCtorArg: Hasher<Sip24Rounds>,
) {
    public var hasher: Hasher<Sip24Rounds> = hasherCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            hasher: Hasher<Sip24Rounds>,
        ): SipHasher24 = SipHasher24(
            hasherCtorArg = hasher,
        )
    }
}

/**
 * An implementation of SipHash 2-4.
 *
 * See: <https://github.com/veorq/SipHash>
 *
 * SipHash is a general-purpose hashing function: it runs at a good
 * speed (competitive with Spooky and City) and permits strong _keyed_
 * hashing. This lets you key your hash tables from a strong RNG, such as
 * [`rand.os.OsRng`](https://docs.rs/rand/latest/rand/rngs/struct.OsRng.html).
 *
 * Although the SipHash algorithm is considered to be generally strong,
 * it is not intended for cryptographic purposes. As such, all
 * cryptographic uses of this implementation are _strongly discouraged_.
 */
public open class SipHasher(
    public var value: SipHasher24,
) {
    public var field0: SipHasher24
        get() = value
        set(v) {
            value = v
        }

    public open fun write(msg: List<UByte>) {
        this.value.hasher.write(msg)
    }

    public open fun writeStr(s: String) {
        this.value.hasher.append(s)
    }

    public open fun finish(): ULong = this.value.hasher.finish()

    public companion object {
        /**
         * Creates a new `SipHasher` with the two initial keys set to 0.
         */
        public fun new(): SipHasher = SipHasher.newWithKeys(0uL, 0uL)

        /**
         * Creates a `SipHasher` that is keyed off the provided keys.
         */
        public fun newWithKeys(key0: ULong, key1: ULong): SipHasher = SipHasher(SipHasher24(hasher = Hasher.newWithKeys(key0, key1)))
    }
}

public open class Hasher<S : Sip>(
    public var k0: ULong,
    public var k1: ULong,
    public var length: Int,
    // how many bytes we've processed
    public var state: State,
    // hash State
    public var tail: ULong,
    // unprocessed bytes le
    public var ntail: Int,
    // how many bytes in tail are valid
    public var marker: Unit,
) {
    public open fun reset() {
        this.length = 0
        this.state.v0 = this.k0 xor 0x736f6d6570736575uL
        this.state.v1 = this.k1 xor 0x646f72616e646f6duL
        this.state.v2 = this.k0 xor 0x6c7967656e657261uL
        this.state.v3 = this.k1 xor 0x7465646279746573uL
        this.ntail = 0
    }

    // Note: no integer hashing methods (`writeU *`, `writeI *`) are defined
    // for this type. We could add them, copy the `shortWrite` implementation
    // in librustcDataStructures/sip128.rs, and add `writeU *`/`writeI *`
    // methods to `SipHasher`, `SipHasher13`, and `DefaultHasher`. This would
    // greatly speed up integer hashing by those hashers, at the cost of
    // slightly slowing down compile speeds on some benchmarks. See #69152 for
    // details.
    public open fun write(msg: List<UByte>, cRoundsSFn: (State) -> Unit) {
        val length = msg.size
        this.length += length
        var needed = 0
        if (this.ntail != 0) {
            needed = 8 - this.ntail
            // SAFETY: `cmp.min(length, needed)` is guaranteed to not be over `length`
            this.tail = this.tail or (u8to64Le(msg, 0, minOf(length, needed)) shl (8 * this.ntail))
            if (length < needed) {
                this.ntail += length
                return
            } else {
                this.state.v3 = this.state.v3 xor this.tail
                cRoundsSFn(this.state)
                this.state.v0 = this.state.v0 xor this.tail
                this.ntail = 0
            }
        }
        // Buffered tail is now flushed, process new input.
        val len = length - needed
        val left = len and 0x7
        // len % 8
        var i = needed
        while (i < len - left) {
            // SAFETY: because `len - left` is the biggest multiple of 8 under
            // `len`, and because `i` starts at `needed` where `len` is `length - needed`,
            // `i + 8` is guaranteed to be less than or equal to `length`.
            val mi =
                run {
                    if (false) {
                        check(i + sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64) <= msg.size)
                    }
                    val localStorageL1C0 = AddressableLocal(0.toULong(), PrimitiveLayouts.U64)
                    val data by localStorageL1C0
                    ptr.copyNonoverlapping(ConstPtr((msg.asPtr()).rawAddress + ((i).toLong() * (PrimitiveLayouts.U8).strideBytes)), (MutPtr(localStorageL1C0.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64))
                    data
                }
            this.state.v3 = this.state.v3 xor mi
            cRoundsSFn(this.state)
            this.state.v0 = this.state.v0 xor mi
            i += 8
        }
        // SAFETY: `i` is now `needed + len.divEuclid(8) * 8`,
        // so `i + left` = `needed + len` = `length`, which is by
        // definition equal to `msg.len()`.
        this.tail = u8to64Le(msg, i, left)
        this.ntail = left
    }

    public open fun writeStr(s: String) {
        // This hasher works byte-wise, and `0xFF` cannot show up in a `String`,
        // so just hashing the one extra byte is enough to be prefix-free.
        this.write(s.encodeToByteArray().map { it.toUByte() })
        this.writeU8(0xFF)
    }

    public open fun finish(cRoundsSFn: (State) -> Unit, dRoundsSFn: (State) -> Unit): ULong {
        val state = this.state
        val b: ULong = ((this.length.toULong() and 0xffuL) shl 56) or this.tail
        state.v3 = state.v3 xor b
        cRoundsSFn(state)
        state.v0 = state.v0 xor b
        state.v2 = state.v2 xor 0xffuL
        dRoundsSFn(state)
        return state.v0 xor state.v1 xor state.v2 xor state.v3
    }

    public open fun clone(): Hasher<S> = Hasher(k0 = this.k0, k1 = this.k1, length = this.length, state = this.state, tail = this.tail, ntail = this.ntail, marker = this.marker)

    public companion object {
        public fun <S : Sip> newWithKeys(key0: ULong, key1: ULong): Hasher<S> {
            val state = Hasher(k0 = key0, k1 = key1, length = 0, state = State(v0 = 0uL, v1 = 0uL, v2 = 0uL, v3 = 0uL), tail = 0uL, ntail = 0, marker = Unit)
            state.reset()
            return state
        }

        /**
         * Creates a `Hasher<S>` with the two initial keys set to 0.
         */
        public fun <S : Sip> default(): Hasher<S> = Hasher.newWithKeys(0, 0)
    }
}

public open class State(
    // v0, v2 and v1, v3 show up in pairs in the algorithm,
    // and simd implementations of SipHash will use vectors
    // of v02 and v13. By placing them in this order in the struct,
    // the compiler can pick up on just a few simd optimizations by itself.
    public var v0: ULong,
    public var v2: ULong,
    public var v1: ULong,
    public var v3: ULong,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<State> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<State> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): State =
                    State(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: State) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.v0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 8L, value.v2)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 16L, value.v1)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 24L, value.v3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): State =
                    State(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: State) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.v0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.v2)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.v1)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.v3)
                }
            }
    }
}

/**
 * Loads a u64 using up to 7 bytes of a byte slice. It looks clumsy but the
 * `copyNonoverlapping` calls that occur (via `loadIntLe`) all have fixed
 * sizes and avoid calling `memcpy`, which is good for speed.
 *
 * Safety: this performs unchecked indexing of `buf` at `start .. start + len`, so
 * that must be in-bounds.
 */
public fun u8to64Le(buf: List<UByte>, start: Int, len: Int): ULong {
    if (false) {
        check(len < 8)
    }
    var i = 0
    // current byte index (from LSB) in the output u64
    var out = 0uL
    if (i + 3 < len) {
        // SAFETY: `i` cannot be greater than `len`, and the caller must guarantee
        // that the index start..start+len is in bounds.
        out = (run {
            if (false) {
                check(start + i + sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) <= buf.size)
            }
            val localStorageL1C0 = AddressableLocal(0.toUInt(), PrimitiveLayouts.U32)
            val data by localStorageL1C0
            ptr.copyNonoverlapping(ConstPtr((buf.asPtr()).rawAddress + ((start + i).toLong() * (PrimitiveLayouts.U8).strideBytes)), (MutPtr(localStorageL1C0.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32))
            data
        }).toULong()
        i += 4
    }
    if (i + 1 < len) {
        // SAFETY: same as above.
        out = out or (((run {
            if (false) {
                check(start + i + sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16) <= buf.size)
            }
            val localStorageL1C0 = AddressableLocal(0.toUShort(), PrimitiveLayouts.U16)
            val data1 by localStorageL1C0
            ptr.copyNonoverlapping(ConstPtr((buf.asPtr()).rawAddress + ((start + i).toLong() * (PrimitiveLayouts.U8).strideBytes)), (MutPtr(localStorageL1C0.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16))
            data1
        }).toULong()) shl (i * 8).toInt())
        i += 2
    }
    if (i < len) {
        // SAFETY: same as above.
        out = out or (((buf.getUnchecked(start + i)).toULong()) shl (i * 8).toInt())
        i += 1
    }
    if (false) {
        when (val matchVal = Pair(i, len)) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (!(leftVal == rightVal)) {
                    val kind = AssertKind.Eq
                    assertFailed(kind, leftVal, rightVal, Option.None)
                }
            }
        }
    }
    return out
}

public interface Sip {
    public fun cRounds(arg0: State)

    public fun dRounds(arg0: State)
}

public open class Sip13Rounds : Sip {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Sip13Rounds> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Sip13Rounds> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Sip13Rounds =
                    Sip13Rounds()
                override fun writeNative(addr: Long, value: Sip13Rounds) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sip13Rounds =
                    Sip13Rounds()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sip13Rounds) {
                }
            }

        public fun cRounds(state: State) {
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
        }

        public fun dRounds(state: State) {
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
        }
    }
}

public open class Sip24Rounds : Sip {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Sip24Rounds> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Sip24Rounds> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Sip24Rounds =
                    Sip24Rounds()
                override fun writeNative(addr: Long, value: Sip24Rounds) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sip24Rounds =
                    Sip24Rounds()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sip24Rounds) {
                }
            }

        public fun cRounds(state: State) {
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
        }

        public fun dRounds(state: State) {
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
            run {
                state.v0 = (state.v0 + (state.v1.toULong()))
                state.v2 = (state.v2 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 13u)
                state.v1 = state.v1 xor state.v0
                state.v3 = rustRotateLeft(state.v3, 16u)
                state.v3 = state.v3 xor state.v2
                state.v0 = rustRotateLeft(state.v0, 32u)
                state.v2 = (state.v2 + (state.v1.toULong()))
                state.v0 = (state.v0 + (state.v3.toULong()))
                state.v1 = rustRotateLeft(state.v1, 17u)
                state.v1 = state.v1 xor state.v2
                state.v3 = rustRotateLeft(state.v3, 21u)
                state.v3 = state.v3 xor state.v0
                state.v2 = rustRotateLeft(state.v2, 32u)
            }
        }
    }
}

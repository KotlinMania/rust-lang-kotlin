@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.alloc.string

import io.github.kotlinmania.rustlang.core.intrinsics.kmAlloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmRealloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU8
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.kmBytesLoadU64Le
import io.github.kotlinmania.rustlang.core.mem.kmBytesStoreU64Le
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8 as coreFromUtf8

/**
 * A UTF-8 encoded, growable string stored in native linear memory (`alloc::string::String`).
 *
 * Named `RustString` so it does not collide with `kotlin.String` or `Swift.String`.
 */
public class RustString private constructor(
    private var ptrAddr: Long,
    private var byteLen: Int,
    private var cap: Int,
) : Comparable<RustString> {
    private fun nonNullAddr(): Long = if (ptrAddr != 0L) ptrAddr else 1L

    private fun ensureCapacityExact(newCap: Int) {
        if (newCap <= cap) return
        if (ptrAddr == 0L || cap == 0) {
            ptrAddr = kmAlloc(newCap.toLong(), 1L)
        } else {
            ptrAddr = kmRealloc(ptrAddr, cap.toLong(), 1L, newCap.toLong())
        }
        cap = newCap
    }

    public fun asPtr(): ConstPtr = ConstPtr(nonNullAddr())

    public fun asMutPtr(): MutPtr = MutPtr(nonNullAddr())

    public fun len(): Int = byteLen

    public fun isEmpty(): Boolean = byteLen == 0

    public fun capacity(): Int = cap

    public fun reserve(additional: Int) {
        require(additional >= 0) { "additional capacity must be non-negative, got $additional" }
        val needed = byteLen + additional
        if (needed <= cap) return
        val newCap = maxOf(needed, maxOf(8, cap * 2))
        ensureCapacityExact(newCap)
    }

    public fun reserveExact(additional: Int) {
        require(additional >= 0) { "additional capacity must be non-negative, got $additional" }
        val needed = byteLen + additional
        if (needed <= cap) return
        ensureCapacityExact(needed)
    }

    public fun shrinkToFit() {
        if (cap == byteLen) return
        if (byteLen == 0) {
            if (ptrAddr != 0L && cap > 0) {
                kmDealloc(ptrAddr, cap.toLong(), 1L)
            }
            ptrAddr = 0L
            cap = 0
            return
        }
        ptrAddr = kmRealloc(ptrAddr, cap.toLong(), 1L, byteLen.toLong())
        cap = byteLen
    }

    public fun pushStr(string: String) {
        val encoded = string.encodeToByteArray()
        if (encoded.isEmpty()) return
        reserve(encoded.size)
        for (i in encoded.indices) {
            kmStoreU8(ptrAddr + (byteLen + i).toLong(), encoded[i].toUByte())
        }
        byteLen += encoded.size
    }

    public fun push(ch: Char) {
        pushStr(ch.toString())
    }

    public fun truncate(newLen: Int) {
        if (newLen < byteLen) {
            require(
                newLen >= 0 &&
                    (newLen == 0 || (kmLoadU8(ptrAddr + newLen.toLong()).toInt() and 0xC0) != 0x80),
            ) {
                "newLen $newLen does not lie on a UTF-8 char boundary"
            }
            byteLen = newLen
        }
    }

    public fun clear() {
        byteLen = 0
    }

    public fun asBytesArray(): UByteArray = UByteArray(byteLen) { i -> kmLoadU8(ptrAddr + i.toLong()) }

    public fun intoBytes(): List<UByte> = List(byteLen) { i -> kmLoadU8(ptrAddr + i.toLong()) }

    public fun asStr(): String =
        if (byteLen == 0) {
            ""
        } else {
            ByteArray(byteLen) { i -> kmLoadU8(ptrAddr + i.toLong()).toByte() }.decodeToString()
        }

    public fun cloneRustString(): RustString {
        if (byteLen == 0) return RustString(0L, 0, 0)
        val clonedPtr = kmAlloc(byteLen.toLong(), 1L)
        kmCopyNonoverlapping(clonedPtr, ptrAddr, byteLen.toLong())
        return RustString(clonedPtr, byteLen, byteLen)
    }

    public fun deallocate() {
        if (ptrAddr != 0L && cap > 0) {
            kmDealloc(ptrAddr, cap.toLong(), 1L)
        }
        ptrAddr = 0L
        byteLen = 0
        cap = 0
    }

    override fun compareTo(other: RustString): Int = asStr().compareTo(other.asStr())

    override fun equals(other: Any?): Boolean = other is RustString && asStr() == other.asStr()

    override fun hashCode(): Int = asStr().hashCode()

    override fun toString(): String = asStr()

    public companion object {
        private val LAYOUT: TypeLayout<RustString> =
            object : TypeLayout<RustString> {
                override val sizeBytes: Long = 24L
                override val alignBytes: Long = 8L

                override fun readNative(addr: Long): RustString =
                    RustString(
                        ptrAddr = kmLoadU64(addr).toLong(),
                        byteLen = kmLoadU64(addr + 8L).toInt(),
                        cap = kmLoadU64(addr + 16L).toInt(),
                    )

                override fun writeNative(addr: Long, value: RustString) {
                    kmStoreU64(addr, value.ptrAddr.toULong())
                    kmStoreU64(addr + 8L, value.byteLen.toLong().toULong())
                    kmStoreU64(addr + 16L, value.cap.toLong().toULong())
                }

                override fun readBytes(buf: ByteArray, offset: Int): RustString =
                    RustString(
                        ptrAddr = kmBytesLoadU64Le(buf, offset).toLong(),
                        byteLen = kmBytesLoadU64Le(buf, offset + 8).toInt(),
                        cap = kmBytesLoadU64Le(buf, offset + 16).toInt(),
                    )

                override fun writeBytes(buf: ByteArray, offset: Int, value: RustString) {
                    kmBytesStoreU64Le(buf, offset, value.ptrAddr.toULong())
                    kmBytesStoreU64Le(buf, offset + 8, value.byteLen.toLong().toULong())
                    kmBytesStoreU64Le(buf, offset + 16, value.cap.toLong().toULong())
                }
            }

        public fun typeLayout(): TypeLayout<RustString> = LAYOUT

        public fun new(): RustString = RustString(0L, 0, 0)

        public fun withCapacity(capacity: Int): RustString {
            require(capacity >= 0) { "capacity must be non-negative, got $capacity" }
            if (capacity == 0) return RustString(0L, 0, 0)
            return RustString(kmAlloc(capacity.toLong(), 1L), 0, capacity)
        }

        public fun from(text: String): RustString {
            val encoded = text.encodeToByteArray()
            if (encoded.isEmpty()) return RustString(0L, 0, 0)
            val addr = kmAlloc(encoded.size.toLong(), 1L)
            for (i in encoded.indices) {
                kmStoreU8(addr + i.toLong(), encoded[i].toUByte())
            }
            return RustString(addr, encoded.size, encoded.size)
        }

        public fun fromUtf8(bytes: List<UByte>): Result<RustString, FromUtf8Error> =
            when (val checked = coreFromUtf8(bytes)) {
                is Result.Ok -> Result.Ok(fromUtf8Unchecked(bytes))
                is Result.Err -> Result.Err(FromUtf8Error(bytes, checked.value))
            }

        public fun fromUtf8Lossy(bytes: UByteArray): RustString =
            from(bytes.asByteArray().decodeToString())

        public fun fromUtf8Lossy(bytes: List<UByte>): RustString =
            from(ByteArray(bytes.size) { i -> bytes[i].toByte() }.decodeToString())

        public fun fromUtf8Unchecked(bytes: List<UByte>): RustString {
            if (bytes.isEmpty()) return RustString(0L, 0, 0)
            val addr = kmAlloc(bytes.size.toLong(), 1L)
            for (i in bytes.indices) {
                kmStoreU8(addr + i.toLong(), bytes[i])
            }
            return RustString(addr, bytes.size, bytes.size)
        }

        public fun fromRawParts(
            buf: MutPtr,
            length: Int,
            capacity: Int,
        ): RustString {
            require(length >= 0 && capacity >= length) {
                "invalid raw parts for RustString: len=$length cap=$capacity"
            }
            val addr = if (capacity == 0) 0L else buf.rawAddress
            return RustString(addr, length, capacity)
        }
    }
}

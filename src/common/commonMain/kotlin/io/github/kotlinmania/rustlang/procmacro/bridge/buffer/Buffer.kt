// port-lint: source bridge/buffer.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.procmacro.bridge.buffer

import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.asPtr
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Buffer management for same-process client<->server communication.

public fun interface BufferReserveCallback {
    public operator fun invoke(arg0: Buffer, arg1: ULong): Buffer
}

public fun interface BufferDropCallback {
    public operator fun invoke(arg0: Buffer)
}

public open class Buffer internal constructor(
    dataCtorArg: MutPtr,
    public var len: Int,
    public var capacity: Int,
    public var reserve: BufferReserveCallback,
    public var drop: BufferDropCallback,
) {
    public var data: MutPtr = dataCtorArg
        internal set

    public open fun deref(): List<UByte> = Slice.fromRawParts((this.data).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, this.len)

    public open fun clear() {
        this.len = 0
    }

    public open fun take(): Buffer = io.github.kotlinmania.rustlang.core.mem.take(this)

    // We have the array method separate from extending from a slice. This is
    // because in the case of small arrays, codegen can be more efficient
    // (avoiding a memmove call). With extendFromSlice, LLVM at least
    // currently is not able to make that optimization.
    public open fun extendFromArray(xs: UByteArray, n: Int = 0) {
        var selfMut = this
        if (xs.size > (selfMut.capacity - selfMut.len)) {
            val b = selfMut.also { selfMut = null }
            selfMut = (b.reserve)(b, xs.size)
        }
        run {
            kmCopyNonoverlapping((xs.asPtr()).rawAddress, (MutPtr((selfMut.data).rawAddress + ((selfMut.len).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, (xs.size).toLong() * (PrimitiveLayouts.U8).sizeBytes)
            selfMut.len += xs.size
        }
    }

    public open fun extendFromSlice(xs: List<UByte>) {
        var selfMut = this
        if (xs.size > (selfMut.capacity - selfMut.len)) {
            val b = selfMut.also { selfMut = null }
            selfMut = (b.reserve)(b, xs.size)
        }
        run {
            kmCopyNonoverlapping((xs.asPtr()).rawAddress, (MutPtr((selfMut.data).rawAddress + ((selfMut.len).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, (xs.size).toLong() * (PrimitiveLayouts.U8).sizeBytes)
            selfMut.len += xs.size
        }
    }

    public open fun push(v: UByte) {
        var selfMut = this
        // The code here is taken from Vec.push, and we know that reserve()
        // will panic if we're exceeding isize.Max bytes and so there's no need
        // to check for overflow.
        if (selfMut.len == selfMut.capacity) {
            val b = selfMut.also { selfMut = null }
            selfMut = (b.reserve)(b, 1)
        }
        run {
            (PrimitiveLayouts.U8).writeNative((MutPtr((selfMut.data).rawAddress + ((selfMut.len).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, v)
            selfMut.len += 1
        }
    }

    public open fun drop() {
        val b = this.also { this = null }
        (b.drop)(b)
    }

    public companion object {
        public operator fun invoke(
            data: MutPtr,
            len: Int,
            capacity: Int,
            reserve: BufferReserveCallback,
            drop: BufferDropCallback,
        ): Buffer = Buffer(
            dataCtorArg = data,
            len = len,
            capacity = capacity,
            reserve = reserve,
            drop = drop,
        )

        public fun default(): Buffer = Buffer.from(mutableListOf())

        public fun new(): Buffer = Buffer.default()

        public fun from(v: List<UByte>): Buffer {
            // This utility function is nested in here because it can *only*
            // be safely called on `Buffer`s created by *this* `procMacro`.
            fun toVec(b: Buffer): List<UByte> =
                run {
                    val b1 = b
                    Vec.fromRawParts(b1.data, b1.len, b1.capacity)
                }
            fun reserve(b: Buffer, additional: Int): Buffer {
                val v1 = toVec(b)
                v1.reserve(additional)
                return Buffer.from(v1)
            }
            fun drop(b: Buffer) {
                io.github.kotlinmania.rustlang.core.mem.drop(toVec(b))
            }
            val (data, len, capacity) = v.intoRawParts()
            return Buffer(data = data, len = len, capacity = capacity, reserve = ::reserve, drop = ::drop)
        }
    }
}

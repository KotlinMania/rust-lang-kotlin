// rust-kotlin: manual
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.intrinsics.kmCompareBytes
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopy
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmWriteBytes
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

private class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public sealed class CVoid {
    public data object Variant1 : CVoid()
    public data object Variant2 : CVoid()

    private fun kmDiscriminant(): Int =
        when (this) {
            Variant1 -> 0
            Variant2 -> 1
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            DebugStruct(f, "c_void").finish()
            Result.Ok<Unit, Unit>(Unit)
        }
}

public fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr {
    kmCopyNonoverlapping(dest.rawAddress, src.rawAddress, n.toLong())
    return dest
}

public fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr {
    kmCopy(dest.rawAddress, src.rawAddress, n.toLong())
    return dest
}

public fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr {
    kmWriteBytes(s.rawAddress, (c and 0xFF).toUByte(), n.toLong())
    return s
}

public fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    kmCompareBytes(s1.rawAddress, s2.rawAddress, n.toLong())

public fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    kmCompareBytes(s1.rawAddress, s2.rawAddress, n.toLong())

public fun strlen(s: ConstPtr): ULong {
    var offset = 0L
    while (kmLoadU8(s.rawAddress + offset) != 0u.toUByte()) {
        offset++
    }
    return offset.toULong()
}

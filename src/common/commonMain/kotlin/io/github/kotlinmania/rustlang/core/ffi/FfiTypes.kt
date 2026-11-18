package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
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

// N.B., for LLVM to recognize the void pointer type and by extension
// functions like malloc(), we need to have it represented as i8* in
// LLVM bitcode. The enum used here ensures this and prevents misuse
// of the "raw" type by only having private variants. We need two
// variants, because the compiler complains about the repr attribute
// otherwise and we need at least one variant as otherwise the enum
// would be uninhabited and at least dereferencing such pointers would
// be UB.
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

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "c_void").finish(); Result.Ok<Unit, Unit>(Unit) }
}

public expect fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr

public expect fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr

public expect fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr

public expect fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt

public expect fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt

public expect fun strlen(s: ConstPtr): ULong

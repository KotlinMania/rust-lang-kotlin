// port-lint: source os/darwin/objc.rs
package io.github.kotlinmania.rustlang.core.os.darwin.objc

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.result.Result

// Defines types and macros for Objective-C interoperability.

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

/**
 * Equivalent to Objective-C’s `class objcClass` type.
 */
public sealed class ObjcClass {
    public data object Variant1 : ObjcClass()
    public data object Variant2 : ObjcClass()

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

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "objc_class").finish(); Result.Ok<Unit, Unit>(Unit) }
}

/**
 * Equivalent to Objective-C’s `class objcSelector` type.
 */
public sealed class ObjcSelector {
    public data object Variant1 : ObjcSelector()
    public data object Variant2 : ObjcSelector()

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

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "objc_selector").finish(); Result.Ok<Unit, Unit>(Unit) }
}

/**
 * Equivalent to Objective-C’s `Class` type.
 */
public typealias Class = MutPtr

/**
 * Equivalent to Objective-C’s `SEL` type.
 */
public typealias Sel = MutPtr

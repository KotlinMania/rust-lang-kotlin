// port-lint: source fmt/num_buffer.rs
package io.github.kotlinmania.rustlang.core.fmt.numbuffer

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.result.Result

public class DebugStruct(
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
 * Trait used to describe the maximum number of digits in decimal base of the implemented integer.
 */
public interface NumBufferTrait<out Buf> {
    /**
     * Used for initializing the `NumberBuffer` value.
     */
    public val DEFAULT: Buf
}

public val DEFAULT: List<MaybeUninit<UByte>> = List(Byte.MAX_VALUE.ilog10().toInt() + 2) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT1: List<MaybeUninit<UByte>> = List(UByte.MAX_VALUE.ilog10().toInt() + 1) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT2: List<MaybeUninit<UByte>> = List(Short.MAX_VALUE.ilog10().toInt() + 2) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT3: List<MaybeUninit<UByte>> = List(UShort.MAX_VALUE.ilog10().toInt() + 1) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT4: List<MaybeUninit<UByte>> = List(Int.MAX_VALUE.ilog10().toInt() + 2) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT5: List<MaybeUninit<UByte>> = List(UInt.MAX_VALUE.ilog10().toInt() + 1) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT6: List<MaybeUninit<UByte>> = List(Long.MAX_VALUE.ilog10().toInt() + 2) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT7: List<MaybeUninit<UByte>> = List(ULong.MAX_VALUE.ilog10().toInt() + 1) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT8: List<MaybeUninit<UByte>> = List(Int.MAX_VALUE.ilog10().toInt() + 2) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT9: List<MaybeUninit<UByte>> = List(Int.MAX_VALUE.ilog10().toInt() + 1) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT10: List<MaybeUninit<UByte>> = List(I128.MAX_VALUE.ilog10().toInt() + 2) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

public val DEFAULT11: List<MaybeUninit<UByte>> = List(U128.MAX_VALUE.ilog10().toInt() + 1) { MaybeUninit.uninit(PrimitiveLayouts.U8) }

/**
 * Memory for formatting numbers using [`T.formatInto`][u8.formatInto].
 *
 * This type consists of enough memory to hold the longest decimal string representation
 * a number of type `T` could have.
 * It is used only by calling `formatInto()`; there is no other way to access its contents.
 * Its purpose is to allow formatting numbers without involving the dynamic dispatch of the
 * [fmt] system, which may be more efficient when [fmt] is not otherwise used.
 *
 * # Examples
 *
 * ```kotlin
 * import core.fmt.NumBuffer
 *
 * var buf = NumBuffer.new()
 * val n1 = 1972u
 * check(n1.formatInto(buf) == "1972")
 * // Formatting a negative integer includes the sign.
 * var buf1 = NumBuffer.new()
 * val n2 = -1972
 * check(n2.formatInto(buf1) == "-1972")
 * ```
 */
public open class NumBuffer<T, Buf>(
    public var buf: Buf,
    public var phantom: Unit,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "NumBuffer").finish()

    public companion object {
        /**
         * Initializes internal buffer.
         */
        public fun <Buf, T> new(): NumBuffer<T, Buf> = NumBuffer(buf = T.DEFAULT, phantom = Unit)

        public fun <Buf, T> default(): NumBuffer<T, Buf> = NumBuffer.new()
    }
}

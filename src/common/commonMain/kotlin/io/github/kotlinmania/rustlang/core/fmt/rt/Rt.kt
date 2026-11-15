// port-lint: source fmt/rt.rs
package io.github.kotlinmania.rustlang.core.fmt.rt

import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.fmt.Formatter
import io.github.kotlinmania.rustlang.fmt.LowerExp
import io.github.kotlinmania.rustlang.fmt.Pointer
import io.github.kotlinmania.rustlang.fmt.UpperExp
import io.github.kotlinmania.rustlang.hint.unreachableUnchecked
import io.github.kotlinmania.rustlang.ptr.nonnull.NonNull

// All types and methods in this file are used by the compiler in
// the expansion/lowering of formatArgs().
//
// Do not modify them without understanding the consequences for the formatArgs() macro.

public fun interface FnCallback {
    public operator fun invoke(arg0: NonNull, arg1: Formatter): Result
}

public sealed class ArgumentType {
    public class sampleValue internal constructor(valueCtorArg: NonNull, public var formatter: FnCallback, public var lifetime: Unit) : ArgumentType() {
        public var value: NonNull = valueCtorArg
            internal set

        public operator fun component1(): NonNull = this.value

        public operator fun component2(): FnCallback = this.formatter

        public operator fun component3(): Unit = this.lifetime

        public fun copy(value: NonNull = this.value, formatter: FnCallback = this.formatter, lifetime: Unit = this.lifetime): sampleValue = sampleValue(valueCtorArg = value, formatter = formatter, lifetime = lifetime)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is sampleValue) return false
            if (this.value != other.value) return false
            if (this.formatter != other.formatter) return false
            if (this.lifetime != other.lifetime) return false
            return true
        }

        override fun hashCode(): Int {
            var result = this.value.hashCode()
            result = 31 * result + this.formatter.hashCode()
            result = 31 * result + this.lifetime.hashCode()
            return result
        }

        public companion object {
            public operator fun invoke(
                value: NonNull,
                formatter: FnCallback,
                lifetime: Unit,
            ): sampleValue = sampleValue(
                valueCtorArg = value,
                formatter = formatter,
                lifetime = lifetime,
            )
        }
    }
    public data class Count(public var value: UShort) : ArgumentType()
}

// To ensure pointers to this struct always have their lowest bit cleared.

/**
 * This struct represents a generic "argument" which is taken by formatArgs().
 *
 * This can be either a Note argument or a count argument.
 * * A Note argument contains a function to format the given value. At
 *   compile time it is ensured that the function and the value have the correct
 *   types, and then this struct is used to canonicalize arguments to one type.
 *   Note arguments are essentially an optimized partially applied formatting
 *   function, equivalent to `exists T.(&T, fn(&T, Formatter<_>) -> Result`.
 * * A count argument contains a count for dynamic formatting parameters like
 *   precision and width.
 */
public open class Argument internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<ArgumentType>,
) {
    public var ty: ArgumentType
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(ty: ArgumentType, fieldLayout: TypeLayout<ArgumentType>) : this(allocateNativeCell(ty, AlignedTypeLayout(fieldLayout, 2L)), AlignedTypeLayout(fieldLayout, 2L))

    public fun pointerLayout(): TypeLayout<Argument> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Argument(ptr, layout) }, { it.ty })

    public open fun clone(): Argument = Argument(ty, fieldLayout)

    /**
     * Format this Note argument.
     *
     * # Safety
     *
     * This argument must actually be a Note argument.
     */
    public open fun fmt(f: Formatter): Result =
        when (val matchVal = this.ty) {
            // SAFETY:
            // Because of the invariant that if `formatter` had the type
            // `fun(T, _) -> _` then `value` has type `T` where `b` is
            // the scope of the `ArgumentType`, and because references
            // and `NonNull` are ABI-compatible, this is completely equivalent
            // to calling the original function passed to `new` with the
            // original reference, which is sound.
            is ArgumentType.sampleValue -> {
                val formatter = matchVal.formatter
                val value = matchVal.value
                formatter(value, f)
            }
            // SAFETY: the caller promised this.
            is ArgumentType.Count -> unreachableUnchecked()
        }

    public open fun asU16(): UShort? =
        when (val matchVal = this.ty) {
            is ArgumentType.Count -> {
                val (count) = matchVal
                count
            }
            is ArgumentType.sampleValue -> null
        }

    public companion object {
        public operator fun invoke(ty: ArgumentType, fieldLayout: TypeLayout<ArgumentType>): Argument = Argument(ty, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ArgumentType>): Argument = Argument(fieldStorage, fieldLayout)

        public fun <T> newDisplay(x: T, fieldLayout: TypeLayout<ArgumentType>, fmtTFn: (T, Formatter) -> Result): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = fmtTFn
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun <T> newDebug(x: T, fieldLayout: TypeLayout<ArgumentType>, fmtTFn: (T, Formatter) -> Result): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = fmtTFn
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun <T> newDebugNoop(x: T, fieldLayout: TypeLayout<ArgumentType>): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = { Result.Ok(Unit) }
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun <T> newOctal(x: T, fieldLayout: TypeLayout<ArgumentType>, fmtTFn: (T, Formatter) -> Result): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = fmtTFn
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun <T> newLowerHex(x: T, fieldLayout: TypeLayout<ArgumentType>, fmtTFn: (T, Formatter) -> Result): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = fmtTFn
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun <T> newUpperHex(x: T, fieldLayout: TypeLayout<ArgumentType>, fmtTFn: (T, Formatter) -> Result): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = fmtTFn
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun <T : Pointer> newPointer(x: T, fieldLayout: TypeLayout<ArgumentType>, fmtTFn: (T, Formatter) -> Result): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = fmtTFn
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun <T> newBinary(x: T, fieldLayout: TypeLayout<ArgumentType>, fmtTFn: (T, Formatter) -> Result): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = fmtTFn
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun <T : LowerExp> newLowerExp(x: T, fieldLayout: TypeLayout<ArgumentType>, fmtTFn: (T, Formatter) -> Result): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = fmtTFn
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun <T : UpperExp> newUpperExp(x: T, fieldLayout: TypeLayout<ArgumentType>, fmtTFn: (T, Formatter) -> Result): Argument =
            Argument(ty = ArgumentType.sampleValue(value = NonNull.fromRef(x).cast(), formatter = run {
                val f: (T, Formatter) -> Result = fmtTFn
                transmute(f)
            }, lifetime = Unit), fieldLayout = fieldLayout)

        public fun fromUsize(x: Int, fieldLayout: TypeLayout<ArgumentType>): Argument {
            if (x > UShort.MAX_VALUE.toInt()) {
                error("Formatting argument out of range")
            }
            return Argument(ty = ArgumentType.Count(x.toUShort()), fieldLayout = fieldLayout)
        }
    }
}

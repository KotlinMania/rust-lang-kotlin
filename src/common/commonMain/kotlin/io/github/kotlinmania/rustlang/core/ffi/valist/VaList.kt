// port-lint: source ffi/va_list.rs
package io.github.kotlinmania.rustlang.core.ffi.valist

import io.github.kotlinmania.rustlang.core.error.tags.Type
import io.github.kotlinmania.rustlang.core.intrinsics.vaArg
import io.github.kotlinmania.rustlang.core.intrinsics.vaCopy
import io.github.kotlinmania.rustlang.core.intrinsics.vaEnd
import io.github.kotlinmania.rustlang.core.marker.variance.PhantomCovariantLifetime
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result

// C's "variable arguments"
//
// Better known as "varargs".

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * A variable argument list, ABI-compatible with `vaList` in C.
 *
 * This type is created in c-variadic functions when `...` is desugared. A `VaList`
 * is automatically initialized (equivalent to calling `vaStart` in C).
 *
 * ```kotlin
 * import std.ffi.VaList
 *
 * / **
 *  * # Safety
 *  * Must be passed at least `count` arguments of type `Int`.
 *  * /
 * fun vmyFunc(count: UInt, ap: VaList): Int {
 *     var sum = 0
 *     for (unused in 0 until count) {
 *         sum += ap.nextArg<Int>()
 *     }
 *     return sum
 * }
 * / **
 *  * # Safety
 *  * Must be passed at least `count` arguments of type `Int`.
 *  * /
 * fun myFunc(count: UInt): Int {
 *     return vmyFunc(count, ap)
 * }
 * check(myFunc(1, 42) == 42)
 * check(myFunc(3, 42, -7, 20) == 55)
 * ```
 *
 * The [VaList.nextArg] method reads the next argument from the variable argument list,
 * and is equivalent to C `vaArg`.
 *
 * Cloning a `VaList` performs the equivalent of C `vaCopy`, producing an independent cursor
 * that arguments can be read from without affecting the original. Dropping a `VaList` performs
 * the equivalent of C `vaEnd`.
 *
 * A `VaList` can be used across an FFI boundary, and fully matches the platform's `vaList` in
 * terms of layout and ABI.
 */
public open class VaList(
    public var inner: VaListInner,
    public var marker: PhantomCovariantLifetime,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        // No need to include `_marker` in debug output.
        return DebugTuple(f, "VaList").field(this.inner).finish()
    }

    // Helper used in the implementation of the `vaCopy` intrinsic.
    internal open fun duplicate(): VaList = VaList(inner = this.inner, marker = this.marker)

    /**
     * Clone the [VaList], producing a second independent cursor into the variable argument list.
     *
     * Corresponds to `vaCopy` in C.
     */
    public open fun clone(): VaList {
        // We only implement Clone and not Copy because some future target might not be able to
        // implement Copy (e.g. because it allocates). For the same reason we use an intrinsic
        // to do the copying: the fact that on all current targets, this is just `memcpy`, is an implementation
        // detail. The intrinsic lets Miri catch UB from code incorrectly relying on that implementation detail.
        return vaCopy(this)
    }

    /**
     * Drop the [VaList].
     *
     * Corresponds to `vaEnd` in C.
     */
    public open fun drop() {
        // Call the rust `vaEnd` intrinsic, which is a no-op and does not map to LLVM `vaEnd`.
        // The rust intrinsic exists as a hook for Miri to check for UB.
        //
        // SAFETY: this variable argument list is being dropped, so won't be read from again.
        vaEnd(this)
    }

    // Avoid codegen when not used to help backends that don't support VaList.
    // even without panics, this helps for Miri backtraces

    /**
     * Read the next argument from the variable argument list.
     *
     * Only types that implement [VaArgSafe] can be read from a variable argument list.
     *
     * # Safety
     *
     * This function is safe to call only if all of the following conditions are satisfied:
     *
     * - There is another c-variadic argument to read.
     * - The actual type of the argument `U` is compatible with `T` (as defined below).
     * - If `U` and `T` are both integer types, then the value passed by the caller must be
     * representable in both types.
     * - If `T` is not [Copy], then it must not have already been read using `nextArg`
     * on a [`clone`][VaList.clone]d copy of this `VaList`.
     * (Currently, all types implementing [VaArgSafe] also implement [Copy],
     * but this may change in the future.)
     *
     * Types `T` and `U` are compatible when:
     *
     * - `T` and `U` are the same type.
     * - `T` and `U` are integer types of the same size.
     * - `T` and `U` are both pointers, and their target types are compatible.
     * - `T` is a pointer to [cVoid] and `U` is a pointer to [i8] or [u8], or vice versa.
     */
    public open fun <T : VaArgSafe> nextArg(): T {
        // SAFETY: the caller must uphold the safety contract for `vaArg`.
        return vaArg(this)
    }
}

// We may unseal this trait in the future, but currently our `vaArg` implementations don't support
// types with a non-scalar layout. Inline assembly can be used to accept unsupported types in the
// meantime.

/**
 * Types that are valid to read using [VaList.nextArg].
 *
 * This trait is implemented for primitive types that have a variable argument application-binary
 * interface (ABI) on the current platform. It is always implemented for:
 *
 * - [cInt], [cLong] and [cLonglong]
 * - [cUint], [cUlong] and [cUlonglong]
 * - [cDouble]
 * - `* const T` and `* T`
 *
 * Implementations for e.g. `Int` or `Int` shouldn't be relied upon directly,
 * because they may not be available on all platforms.
 *
 * # Safety
 *
 * When C passes variable arguments, signed integers smaller than [cInt] are promoted
 * to [cInt], unsigned integers smaller than [cUint] are promoted to [cUint],
 * and [cFloat] is promoted to [cDouble]. Implementing this trait for types that are
 * subject to this promotion rule is invalid.
 *
 * This trait is only implemented for 128-bit integers when the platform defines the `__int128`
 * type.
 */
public interface VaArgSafe

public fun <T : VaArgSafe> vaArgSafeCheck() {}

public open class Type(
    public var value: Int,
) {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Type> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Type> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Type =
                    Type(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Type) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Type =
                    Type(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Type) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public fun c(arg0: Type) {}

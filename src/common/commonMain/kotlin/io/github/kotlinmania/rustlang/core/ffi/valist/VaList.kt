// port-lint: source ffi/va_list.rs
package io.github.kotlinmania.rustlang.core.ffi.valist

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.error.Type
import io.github.kotlinmania.rustlang.intrinsics.vaArg
import io.github.kotlinmania.rustlang.intrinsics.vaCopy
import io.github.kotlinmania.rustlang.intrinsics.vaEnd
import io.github.kotlinmania.rustlang.marker.variance.PhantomCovariantLifetime

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

// There are currently three flavors of how a C `vaList` is implemented for
// targets that Rust supports:
//
// - `vaList` is an opaque pointer
// - `vaList` is a struct
// - `vaList` is a single-element array, containing a struct
//
// The opaque pointer approach is the simplest to implement: the pointer just
// points to an array of arguments on the caller's stack.
//
// The struct and single-element array variants are more complex, but
// potentially more efficient because the additional state makes it
// possible to pass variadic arguments via registers.
//
// The Rust `VaList` type is ABI-compatible with the C `vaList`.
// The struct and pointer cases straightforwardly map to their Rust equivalents,
// but the single-element array case is special: in C, this type is subject to
// array-to-pointer decay.
//
// The `@rustcPassIndirectlyInNonRusticAbis` attribute is used to match
// the pointer decay behavior in Rust, while otherwise matching Rust semantics.
// This attribute ensures that the compiler uses the correct ABI for functions
// like `extern "C" fun takesVaList(va: VaList<>)` by passing `va` indirectly.
//
// The Clang `BuiltinVaListKind` enumerates the `vaList` variations that Clang supports,
// and we mirror these here.
//
// For all current LLVM targets, `vaCopy` lowers to `memcpy`. Hence the inner structs below all
// derive `Copy`. However, in the future we might want to support a target where `vaCopy`
// allocates, or otherwise violates the requirements of `Copy`. Therefore `VaList` is only `Clone`.

/**
 * AArch64 ABI implementation of a `vaList`.
 *
 * See the [AArch64 Procedure Call Standard] for more details.
 *
 * `vaCopy` is `memcpy`: <https://github.com/llvm/llvm-project/blob/5aee01a3df011e660f26660bc30a8c94a1651d8e/llvm/lib/Target/AArch64/AArch64ISelLowering.cpp#L12682-L12700>
 *
 * [AArch64 Procedure Call Standard]:
 * http://infocenter.arm.com/help/topic/com.arm.doc.ihi0055b/Ihi0055bAapcs64.pdf
 */
public open class VaListInner internal constructor(
    stackCtorArg: ConstPtr,
    grTopCtorArg: ConstPtr,
    vrTopCtorArg: ConstPtr,
    public var grOffs: Int,
    public var vrOffs: Int,
) {
    public var stack: ConstPtr = stackCtorArg
        internal set

    public var grTop: ConstPtr = grTopCtorArg
        internal set

    public var vrTop: ConstPtr = vrTopCtorArg
        internal set

    public open fun clone(): VaListInner = VaListInner(stack, grTop, vrTop, grOffs, vrOffs)

    public companion object {
        public fun typeLayout(): TypeLayout<VaListInner> =
            object : TypeLayout<VaListInner> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): VaListInner =
                    VaListInner(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: VaListInner) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.stack)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 8L, value.grTop)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 16L, value.vrTop)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.grOffs)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.vrOffs)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VaListInner =
                    VaListInner(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VaListInner) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.stack)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 8, value.grTop)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 16, value.vrTop)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.grOffs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.vrOffs)
                }
            }

        public operator fun invoke(
            stack: ConstPtr,
            grTop: ConstPtr,
            vrTop: ConstPtr,
            grOffs: Int,
            vrOffs: Int,
        ): VaListInner = VaListInner(
            stackCtorArg = stack,
            grTopCtorArg = grTop,
            vrTopCtorArg = vrTop,
            grOffs = grOffs,
            vrOffs = vrOffs,
        )
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
    public open fun duplicate(): VaList = VaList(inner = this.inner, marker = this.marker)

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

// Implement `VaArgSafe` for 128-bit integers on targets where clang provides `__int128`.
//
// GCC does not implement `__int128` for any 16-bit/32-bit target:
//
// https://gcc.gnu.org/onlinedocs/gcc-15.2.0/gcc/_005f_005fint128.html
//
// > There is no support in GCC for expressing an integer constant of type __int128 for targets
// > with long long integer less than 128 bits wide.
//
// Per https://learn.microsoft.com/en-us/cpp/cpp/data-type-ranges?view=msvc-170, MSVC does not
// define `__int128`.
//
// Clang is slightly more permissive: it defines `__int128` on wasm32 (a 32-bit target) and also
// does provide `__int128` on 64-bit `* - pc - windows - msvc`, and we follow suit.

// When there are no actual implementations on i128, declare the cVariadicInt128 feature
// on a private type so that the feature is defined on all targets.
public open class S internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int>,
) {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<S> = TransparentTypeLayout(fieldLayout, { ptr, layout -> S(ptr, layout) }, { it.value })

    public open fun clone(): S = S(value, fieldLayout)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int>): S = S(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int>): S = S(fieldStorage, fieldLayout)
    }
}

// Implement `VaArgSafe` for f128 on targets where either:
//
// - clang provides `__float128`
// - `long double` is IEEE f128 on the platform.
//
// When updating this cfg, also update the tests to match. Currently this condition
// is duplicated in:
//
// - tests/ui/c-variadic/roundtrip.rs
// - tests/run-make/c-link-to-rust-va-list-fn/checkrust.rs
//
// # Known incompatibilities
//
// Testing versus clang exposed bugs in clang. GCC has no known incompatibilities.
//
// - Clang <= 23 on sparc, see https://github.com/llvm/llvm-project/pull/214981.
// - Clang <= 23 on x86, see https://github.com/llvm/llvm-project/issues/217747.

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
        public fun typeLayout(): TypeLayout<Type> =
            object : TypeLayout<Type> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Type =
                    Type(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Type) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Type =
                    Type(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Type) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public fun c(arg0: Type) {}

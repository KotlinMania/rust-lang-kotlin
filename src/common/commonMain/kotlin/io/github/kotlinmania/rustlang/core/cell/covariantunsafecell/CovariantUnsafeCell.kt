// port-lint: source cell/covariant_unsafe_cell.rs
package io.github.kotlinmania.rustlang.core.cell.covariantunsafecell

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
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

// Implementation note:
//
// We could make `CovariantUnsafeCell` be the canonical lang item and make `UnsafeCell` a wrapper
// over it, with `PhantomData< * T>`. That would however be a huge compiler change, without clear
// benefit.
//
// As such, `CovariantUnsafeCell` is wrapping `UnsafeCell` instead. It is a lang-item only to
// hardcode its variance to be **co**variant in `T`, even though it is wrapping `UnsafeCell` which
// is **in**variant in `T`.

/**
 * **Co**variant version of [UnsafeCell].
 */
public open class CovariantUnsafeCell<T> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UnsafeCell<T>>,
) {
    public var field0: UnsafeCell<T>
        get() = value
        set(v) {
            value = v
        }

    public var value: UnsafeCell<T>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UnsafeCell<T>, fieldLayout: TypeLayout<UnsafeCell<T>>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<CovariantUnsafeCell<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> CovariantUnsafeCell<T>(ptr, layout) }, { it.value })

    /**
     * Unwraps the value, consuming the cell.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.CovariantUnsafeCell
     *
     * @feature(covariantUnsafeCell)
     * public fun doctestEntry() {
     *     val uc = CovariantUnsafeCell.new(5)
     *     val five = uc.intoInner()
     * }
     * ```
     */
    public open fun intoInner(): T = this.value.intoInner()

    /**
     * Gets a mutable non-null pointer to the wrapped value.
     *
     * This can be cast to a pointer of any kind. When creating (shared or mutable) references, you
     * must uphold the aliasing rules; see [the `UnsafeCell` type-level docs] for more discussion
     * and caveats.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.CovariantUnsafeCell
     * import std.ptr.NonNull
     *
     * @feature(covariantUnsafeCell)
     * public fun doctestEntry() {
     *     val uc = CovariantUnsafeCell.new(5)
     *     val ptr: NonNull = uc.get()
     * }
     * ```
     */
    public open fun get(): NonNull {
        // We can just cast the pointer from `CovariantUnsafeCell<T>` to `T` because of
        // @repr(transparent).
        //
        // Note that this is also known to be allowed for user code as per
        // `@rustcPubTransparent`.
        // SAFETY: the pointer is not null, as it comes from a reference
        return NonNull.newUnchecked(MutPtr((this).rawAddress))
    }

    /**
     * Returns a mutable reference to the underlying data.
     *
     * This call borrows the `CovariantUnsafeCell` mutably (at compile-time) which guarantees that
     * we possess the only reference.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.CovariantUnsafeCell
     *
     * @feature(covariantUnsafeCell)
     * public fun doctestEntry() {
     *     var c = CovariantUnsafeCell.new(5)
     *     c.getMut() += 1
     *     check(c.getMut() == 6)
     * }
     * ```
     */
    public open fun getMut(): T = this.value.getMut()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "CovariantUnsafeCell").finishNonExhaustive()

    public companion object {
        public operator fun <T> invoke(value: UnsafeCell<T>, fieldLayout: TypeLayout<UnsafeCell<T>>): CovariantUnsafeCell<T> = CovariantUnsafeCell<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<T>>): CovariantUnsafeCell<T> = CovariantUnsafeCell<T>(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of `CovariantUnsafeCell` which will wrap the specified value.
         *
         * All access to the inner value through `CovariantUnsafeCell<T>` requires `unchecked` code.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.CovariantUnsafeCell
         *
         * @feature(covariantUnsafeCell)
         * public fun doctestEntry() {
         *     val uc = CovariantUnsafeCell.new(5)
         * }
         * ```
         */
        public fun <T> new(value: T, fieldLayout: TypeLayout<T>): CovariantUnsafeCell<T> = CovariantUnsafeCell(UnsafeCell.new(value), fieldLayout = TransparentTypeLayout(fieldLayout, { ptr, layout -> UnsafeCell<T>(ptr, layout) }, { it.value }))

        /**
         * Gets a mutable pointer to the wrapped value.
         * The difference from [get] is that this function accepts a raw pointer,
         * which is useful to avoid the creation of temporary references.
         *
         * This can be cast to a pointer of any kind. When creating (shared or mutable) references, you
         * must uphold the aliasing rules; see [the `UnsafeCell` type-level docs] for more discussion
         * and caveats.
         *
         * [get]: CovariantUnsafeCell.get()
         *
         * # Examples
         *
         * Gradual initialization of an `CovariantUnsafeCell` requires `rawGet`, as
         * calling `get` would require creating a reference to uninitialized data:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
         * import std.cell.CovariantUnsafeCell
         * import std.mem.MaybeUninit
         *
         * @feature(covariantUnsafeCell)
         * public fun doctestEntry() {
         *     val m = MaybeUninit.uninit(ObjectSlotLayout<CovariantUnsafeCell<Int>>())
         *     run {
         *         CovariantUnsafeCell.rawGet(m.asPtr()).write(5)
         *     }
         *     // avoid below which references to uninitialized data
         *     // unchecked { CovariantUnsafeCell.get(&*m.asPtr()).write(5); }
         *     val uc = m.assumeInit()
         *     check(uc.intoInner() == 5)
         * }
         * ```
         */
        public fun rawGet(thisRef: ConstPtr): MutPtr {
            // We can just cast the pointer from `UnsafeCell<T>` to `T` because of
            // @repr(transparent).
            //
            // Note that this is also known to be allowed for user code as per
            // `@rustcPubTransparent`.
            return (ConstPtr(thisRef.rawAddress, thisRef.rawMeta)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        }
    }
}

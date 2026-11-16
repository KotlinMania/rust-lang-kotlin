// port-lint: source mem/manually_drop.rs
package io.github.kotlinmania.rustlang.core.mem.manuallydrop

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.read

/**
 * A wrapper to inhibit the compiler from automatically calling `T`’s
 * destructor. This wrapper is 0-cost.
 *
 * `ManuallyDrop<T>` is guaranteed to have the same layout and bit validity as
 * `T`, and is subject to the same layout optimizations as `T`. As a
 * consequence, it has *no effect* on the assumptions that the compiler makes
 * about its contents. For example, initializing a `ManuallyDrop<T>` with
 * [mem.zeroed] is undefined behavior. If you need to handle uninitialized
 * data, use [MaybeUninit.T] instead.
 *
 * Note that accessing the value inside a `ManuallyDrop<T>` is safe. This means
 * that a `ManuallyDrop<T>` whose content has been dropped must not be exposed
 * through a public safe API. Correspondingly, `ManuallyDrop.drop` is unchecked.
 *
 * # `ManuallyDrop` and drop order
 *
 * Rust has a well-defined [drop order] of values. To make sure that fields or
 * locals are dropped in a specific order, reorder the declarations such that
 * the implicit drop order is the correct one.
 *
 * It is possible to use `ManuallyDrop` to control the drop order, but this
 * requires unchecked code and is hard to do correctly in the presence of
 * unwinding.
 *
 * For example, if you want to make sure that a specific field is dropped after
 * the others, make it the last field of a struct:
 *
 * ```kotlin
 * open class Context
 * open class Widget(
 *     public var children: List<Widget>,
 *     // `context` will be dropped after `children`.
 *     // Rust guarantees that fields are dropped in the order of declaration.
 *     public var context: Context,
 * )
 * ```
 *
 * # Safety hazards when storing `ManuallyDrop` in a struct or an enum.
 *
 * Special care is needed when all of the conditions below are met:
 * * A struct or enum contains a `ManuallyDrop`.
 * * The `ManuallyDrop` is not inside a `union`.
 * * The struct or enum is part of public API, or is stored in a struct or an
 *   enum that is part of public API.
 * * There is a _safe_ function that drops the contents of the `ManuallyDrop`
 *   field, and it can be called outside the struct or enum's `Drop` implementation.
 *
 * In particular, deriving `Debug`, `Clone`, `PartialEq`, `PartialOrd`, `Ord`,
 * or `Hash` on the struct or enum could be unsound, since the derived
 * implementations of these traits would access the `ManuallyDrop` field.
 *
 * For example, in the following code, `derive(Debug)` is unsound in combination
 * with the `ManuallyDrop.drop` call in `Foo.new`:
 *
 * ```kotlin
 * open class Foo(
 *     / **
 *      * Invariant: this value may have been dropped!
 *      * /
 *     public var value: String,
 * ) {
 *     public open fun new(): Foo {
 *         var temp = Foo(value = "Unsafe rust is hard.")
 *         run {
 *             // SAFETY: `value` hasn't been dropped yet.
 *             ManuallyDrop.drop(temp.value)
 *         }
 *         return temp
 *     }
 * }
 * ```
 *
 * As one could use the `Debug` implementation to access an already dropped
 * field:
 *
 * ```rust,ignore (uses-type-from-separate-snippet)
 * let foo = Foo.new();
 * println("{foo:?}"); // Undefined behavior!
 * ```
 *
 * Note that similar unsoundness can arise without `derive`. The cause of the
 * unsoundness are public APIs which allow to access an already dropped value
 * inside `ManuallyDrop`.
 *
 * # Pre-`1.96` Interaction with `Holder`
 *
 * Before Rust `1.96.0`, if you had a `ManuallyDrop<T>`, where the type `T`
 * was a `Holder` or contained a `Holder` inside, then dropping the `T` followed by
 * moving the `ManuallyDrop<T>` was [considered to be undefined
 * behavior](https://github.com/rust-lang/unchecked-code-guidelines/issues/245).
 * That is, the following code caused undefined behavior:
 *
 * ```kotlin
 * var x = 42
 * run {
 *     ManuallyDrop.drop(x)
 * }
 * val y = x
 * // Undefined behavior! (pre 1.96.0)
 * ```
 *
 * Note that this could also have happen with a generic type where the user of
 * the library providing it could substitute the generic for a `Holder<_>` and
 * then move the library type:
 *
 * ```kotlin
 * open class BadOption<T>(
 *     // Invariant: Has been dropped if `isSome` is false.
 *     public var value: T,
 *     public var isSome: Boolean,
 * ) {
 *     public open fun new(value: T): BadOption<T> {
 *         return BadOption(value = value, isSome = true)
 *     }
 *
 *     public open fun changeToNone() {
 *         if (this.isSome) {
 *             this.isSome = false
 *             run {
 *                 // SAFETY: `value` hasn't been dropped yet, as per the invariant
 *                 // (This is actually unsound pre rust 1.96.0!)
 *                 ManuallyDrop.drop(this.value)
 *             }
 *         }
 *     }
 * }
 * // In another crate:
 * var option = BadOption.new(42)
 * option.changeToNone()
 * val option2 = option
 * // Undefined behavior! (pre 1.96)
 * ```
 *
 * [drop order]: https://doc.rust-lang.org/reference/destructors.html
 */
public open class ManuallyDrop<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) : DerefPure {
    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<ManuallyDrop<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> ManuallyDrop<T>(ptr, layout) }, { it.value })

    public open fun clone(): ManuallyDrop<T> = ManuallyDrop<T>(value, fieldLayout)

    public open fun deref(): T = this.value

    public open fun derefMut(): T = this.value

    public companion object {
        /**
         * Wrap a value to be manually dropped.
         *
         * # Examples
         *
         * ```kotlin
         * var x = "Hello World!"
         * x.truncate(5)
         * // You can still safely operate on the value
         * check(x == "Hello")
         * // But `Drop` will not be run here
         * ```
         */
        public fun <T> new(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): ManuallyDrop<T> = ManuallyDrop(value = value, fieldLayout = fieldLayout)

        /**
         * Extracts the value from the `ManuallyDrop` container.
         *
         * This allows the value to be dropped again.
         *
         * # Examples
         *
         * ```kotlin
         * val x = Holder.new()
         * val unused: Unit = ManuallyDrop.intoInner(x)
         * // This drops the `Holder`.
         * ```
         */
        public fun <T> intoInner(slot: ManuallyDrop<T>): T = slot.value

        /**
         * Takes the value from the `ManuallyDrop<T>` container out.
         *
         * This method is primarily intended for moving out values in drop.
         * Instead of using [ManuallyDrop.drop] to manually drop the value,
         * you can use this method to take the value and use it however desired.
         *
         * Whenever possible, it is preferable to use [`intoInner`][ManuallyDrop.intoInner]
         * instead, which prevents duplicating the content of the `ManuallyDrop<T>`.
         *
         * # Safety
         *
         * This function semantically moves out the contained value without preventing further usage,
         * leaving the state of this container unchanged.
         * It is your responsibility to ensure that this `ManuallyDrop` is not used again.
         */
        public fun <T> take(slot: RustMutableReference<ManuallyDrop<T>>): T {
            // SAFETY: we are reading from a reference, which is guaranteed
            // to be valid for reads.
            return read((slot).read().value)
        }

        /**
         * Manually drops the contained value.
         *
         * This is exactly equivalent to calling [ptr.dropInPlace] with a
         * pointer to the contained value. As such, unless the contained value is a
         * packed struct, the destructor will be called in-place without moving the
         * value, and thus can be used to safely drop [pinned] data.
         *
         * If you have ownership of the value, you can use [ManuallyDrop.intoInner] instead.
         *
         * # Safety
         *
         * This function runs the destructor of the contained value. Other than changes made by
         * the destructor itself, the memory is left unchanged, and so as far as the compiler is
         * concerned still holds a bit-pattern which is valid for the type `T`.
         *
         * However, this "zombie" value should not be exposed to safe code, and this function
         * should not be called more than once. To use a value after it's been dropped, or drop
         * a value multiple times, can cause Undefined Behavior (depending on what `drop` does).
         * This is normally prevented by the type system, but users of `ManuallyDrop` must
         * uphold those guarantees without assistance from the compiler.
         */
        public fun <T> drop(slot: RustMutableReference<ManuallyDrop<T>>) {
            // SAFETY: we are dropping the value pointed to by a mutable reference
            // which is guaranteed to be valid for writes.
            // It is up to the caller to make sure that `slot` isn't dropped again.
            dropInPlace((slot).read().value)
        }
    }
}

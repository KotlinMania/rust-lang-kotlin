// port-lint: source pin/unsafe_pinned.rs
package io.github.kotlinmania.rustlang.core.pin.unsafepinned

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
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
 * This type provides a way to entirely opt-out of typical aliasing rules;
 * specifically, `UnsafePinned<T>` is not guaranteed to be a unique pointer.
 * This also subsumes the effects of `UnsafeCell`, i.e., `UnsafePinned<T>` may point to data
 * that is being mutated.
 *
 * However, even if you define your type like `public class Wrapper(UnsafePinned<...>)`, it is still
 * very risky to have an `Wrapper` that aliases anything else. Many functions that work
 * generically on `T` assume that the memory that stores `T` is uniquely owned (such as
 * `mem.swap`). In other words, while having aliasing with `Wrapper` is not immediate
 * Undefined Behavior, it is still unsound to expose such a mutable reference to code you do not
 * control! Techniques such as pinning via [Pin] are needed to ensure soundness.
 *
 * Similar to [`UnsafeCell`](cell.UnsafeCell), `UnsafePinned` will not usually show up in
 * the public API of a library. It is an internal implementation detail of libraries that need to
 * support aliasing mutable references.
 *
 * This type blocks niches the same way `UnsafeCell` does.
 */
public open class UnsafePinned<T> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UnsafeCell<T>>,
) {
    public var value: UnsafeCell<T>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) {
            fieldLayout.writeNative(fieldStorage.rawAddress, value)
        }

    internal constructor(value: UnsafeCell<T>, fieldLayout: TypeLayout<UnsafeCell<T>>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<UnsafePinned<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> UnsafePinned<T>(ptr, layout) }, { it.value })

    // Override the manual `!Sync` in `UnsafeCell`.

    /**
     * When this type is used, that almost certainly means safe APIs need to use pinning to avoid the
     * aliases from becoming invalidated. Therefore let's mark this as `!Unpin`. You can always opt
     * back in to `Unpin` with an `implementation` block, provided your API is still sound while unpinned.
     */

    // `Send` and `Sync` are inherited from `T`. This is similar to `SyncUnsafeCell`, since
    // we eventually concluded that `UnsafeCell` implicitly making things `!Sync` is sometimes
    // unergonomic. A type that needs to be `!Send`/`!Sync` should really have an explicit
    // opt-out itself, e.g. via an `PhantomData< * T>` or (one day) via `impl Send`/`impl Sync`.

    /**
     * Unwraps the value, consuming this `UnsafePinned`.
     */
    public open fun intoInner(): T = this.value.intoInner()

    /**
     * Get read-write access to the contents of a pinned `UnsafePinned`.
     */
    public open fun getMutPinned(): MutPtr {
        // SAFETY: we're not using `getUncheckedMut` to unpin anything
        return this.getUncheckedMut().getMutUnchecked()
    }

    /**
     * Get read-write access to the contents of an `UnsafePinned`.
     *
     * You should usually be using `getMutPinned` instead to explicitly track the fact that this
     * memory is "pinned" due to there being aliases.
     */
    public open fun getMutUnchecked(): MutPtr = this

    /**
     * Get mutable access to the contents of a shared `UnsafePinned`.
     *
     * This can be cast to a pointer of any kind. When creating references, you must uphold the
     * aliasing rules; see [UnsafeCell] for more discussion and caveats.
     *
     * ```kotlin
     * import std.pin.UnsafePinned
     *
     * @feature(unsafePinned)
     * public fun doctestEntry() {
     *     run {
     *         var x = UnsafePinned.new(0)
     *         val ptr = x.get()
     *         x.getMutUnchecked().write(1)
     *         check(ptr.read() == 1)
     *     }
     * }
     * ```
     */
    public open fun get(): MutPtr = this.value.get()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "UnsafePinned").finishNonExhaustive()

    // @unstable(feature = "unsafePinned", issue = "125735")

    // Allow types that wrap `UnsafePinned` to also implement `DispatchFromDyn`
    // and become dynamic-compatible method receivers.
    // Note that currently `UnsafePinned` itself cannot be a method receiver
    // because it does not implement Deref.
    // In other words:
    // `self: UnsafePinned<Self>` won't work
    // `self: UnsafePinned<Self>` becomes possible
    // Note(unsafePinned) this logic is copied from UnsafeCell, is it still sound?
    // @unstable(feature = "unsafePinned", issue = "125735")

    public companion object {
        public operator fun <T> invoke(value: UnsafeCell<T>, fieldLayout: TypeLayout<UnsafeCell<T>>): UnsafePinned<T> = UnsafePinned<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<T>>): UnsafePinned<T> = UnsafePinned<T>(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of `UnsafePinned` which will wrap the specified value.
         *
         * All access to the inner value through `UnsafePinned<T>` or `UnsafePinned<T>` or
         * `Pin<UnsafePinned<T>>` requires `unchecked` code.
         */
        public fun <T> new(value: T, fieldLayout: TypeLayout<T>): UnsafePinned<T> = UnsafePinned(value = UnsafeCell.new(value), fieldLayout = TransparentTypeLayout(fieldLayout, { ptr, layout -> UnsafeCell<T>(ptr, layout) }, { it.value }))

        /**
         * Gets an immutable pointer to the wrapped value.
         *
         * The difference from [get] is that this function accepts a raw pointer, which is useful to
         * avoid the creation of temporary references.
         *
         * [get]: UnsafePinned.get
         */
        public fun rawGet(thisRef: ConstPtr): MutPtr = (ConstPtr(thisRef.rawAddress, thisRef.rawMeta)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

        /**
         * Gets a mutable pointer to the wrapped value.
         *
         * The difference from [getMutPinned] and [getMutUnchecked] is that this function
         * accepts a raw pointer, which is useful to avoid the creation of temporary references.
         *
         * [getMutPinned]: UnsafePinned.getMutPinned
         * [getMutUnchecked]: UnsafePinned.getMutUnchecked
         */
        public fun rawGetMut(thisRef: MutPtr): MutPtr = MutPtr(thisRef.rawAddress, thisRef.rawMeta)

        /**
         * Creates an `UnsafePinned`, with the `Default` value for T.
         */
        public fun <T> default(fieldLayout: TypeLayout<T>, defaultTFn: () -> T): UnsafePinned<T> = UnsafePinned.new(defaultTFn(), fieldLayout = fieldLayout)

        /**
         * Creates a new `UnsafePinned<T>` containing the given value.
         */
        public fun <T> from(value: T, fieldLayout: TypeLayout<T>): UnsafePinned<T> = UnsafePinned.new(value, fieldLayout = fieldLayout)
    }
}

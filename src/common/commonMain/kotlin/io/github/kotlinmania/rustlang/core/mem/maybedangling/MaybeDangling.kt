// port-lint: source mem/maybe_dangling.rs
package io.github.kotlinmania.rustlang.core.mem.maybedangling

import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.ptr.read

// Note: remove `noRun` once the semantics are actually implemented

/**
 * Allows wrapped [references] and [boxes] to dangle.
 *
 * That is, if a reference (or a `Holder`) is wrapped in `MaybeDangling` (including when in a
 * (nested) field of a compound type wrapped in `MaybeDangling`), it does not have to follow
 * pointer aliasing rules or be dereferenceable.
 *
 * This can be useful when the value can become dangling while the function holding it is still
 * executing (particularly in concurrent code). As a somewhat absurd example, consider this code:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 *
 * var boxed = 0u
 * val ptr = Holder.asMutPtr(boxed)
 * // Safety: the pointer comes from a box and thus was allocated before; `box` is not used afterwards
 * dealloc(ptr.cast(), Layout.new<UInt>(typeLayoutT = PrimitiveLayouts.U32))
 * forget(boxed)
 * // <-- this is UB!
 * ```
 *
 * Even though the `Holder`s destructor is not run (and thus we don't have a double free bug), this
 * code is still UB. This is because when moving `boxed` into `forget`, its validity invariants
 * are asserted, causing UB since the `Holder` is dangling. The safety comment is as such wrong, as
 * moving the `boxed` variable as part of the `forget` call *is* a use.
 *
 * To fix this we could use `MaybeDangling`:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 *
 * @feature(maybeDangling)
 * public fun doctestEntry() {
 *     var boxed = MaybeDangling.new(0u)
 *     val ptr = Holder.asMutPtr(boxed.asMut())
 *     // Safety: the pointer comes from a box and thus was allocated before; `box` is not used afterwards
 *     dealloc(ptr.cast(), Layout.new<UInt>(typeLayoutT = PrimitiveLayouts.U32))
 *     forget(boxed)
 *     // <-- this is OK!
 * }
 * ```
 *
 * Note that the bit pattern must still be valid for the wrapped type. That is, [references]
 * (and [boxes]) still must be aligned and non-null.
 *
 * Additionally note that safe code can still assume that the inner value in a `MaybeDangling` is
 * **not** dangling -- functions like [asRef] and [intoInner] are safe. It is not sound to
 * return a dangling reference in a `MaybeDangling` to safe code. However, it *is* sound
 * to hold such values internally inside your code -- and there's no way to do that without
 * this type. Note that other types can use this type and thus get the same effect; in particular,
 * [ManuallyDrop] will use `MaybeDangling`.
 *
 * Note that `MaybeDangling` doesn't prevent drops from being run, which can lead to UB if the
 * drop observes a dangling value. If you need to prevent drops from being run use [ManuallyDrop]
 * instead.
 *
 * [references]: prim@reference
 * [boxes]: ../../std/boxed/struct.Holder.html
 * [intoInner]: MaybeDangling.intoInner
 * [asRef]: MaybeDangling.asRef
 */
public open class MaybeDangling<P>(
    public var value: P,
) {
    public var field0: P
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): MaybeDangling<P> = MaybeDangling(value)

    /**
     * Returns a reference to the inner value.
     *
     * Note that this is UB if the inner value is currently dangling.
     */
    public open fun asRef(): P = this.value

    /**
     * Returns a mutable reference to the inner value.
     *
     * Note that this is UB if the inner value is currently dangling.
     */
    public open fun asMut(): P = this.value

    /**
     * Extracts the value from the `MaybeDangling` container.
     *
     * Note that this is UB if the inner value is currently dangling.
     */
    public open fun intoInner(): P {
        // Note: replace this with `self.0` when const checker can figure out that `self` isn't actually dropped
        // SAFETY: this is equivalent to `self.0`
        val x = read(this.value)
        forget(this)
        return x
    }

    public companion object {
        /**
         * Wraps a value in a `MaybeDangling`, allowing it to dangle.
         */
        public fun <P> new(x: P): MaybeDangling<P> = MaybeDangling(x)
    }
}

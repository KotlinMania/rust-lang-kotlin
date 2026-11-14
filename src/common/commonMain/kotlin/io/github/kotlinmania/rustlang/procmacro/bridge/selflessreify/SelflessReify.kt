// port-lint: source bridge/selfless_reify.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.selflessreify

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.procmacro.bridge.BridgeConfig

// Abstraction for creating `fun` pointers from any callable that *effectively*
// has the equivalent of implementing `Default`, even if the compiler neither
// provides `Default` nor allows reifying closures (i.e. creating `fun` pointers)
// other than those with absolutely no captures.
//
// More specifically, for a closure-like type to be "effectively `Default`":
// * it must be a ZST (zero-sized type): no information contained within, so
//   that `Default`s return value (if it were implemented) is unambiguous
// * it must be `Copy`: no captured "unique ZST tokens" or any other similar
//   types that would make duplicating values at will unsound
//   * combined with the ZST requirement, this confers a kind of "telecopy"
//     ability: similar to `Copy`, but without keeping the value around, and
//     instead "reconstructing" it (a noop given it's a ZST) when needed
// * it must be *provably* inhabited: no captured uninhabited types or any
//   other types that cannot be constructed by the user of this abstraction
//   * the proof is a value of the closure-like type itself, in a sense the
//     "seed" for the "telecopy" process made possible by ZST + `Copy`
//   * this requirement is the only reason an abstraction limited to a specific
//     usecase is required: ZST + `Copy` can be checked with *at worst* a panic
//     at the "attempted `.default()` call" time, but that doesn't guarantee
//     that the value can be soundly created, and attempting to use the typical
//     "proof ZST token" approach leads yet again to having a ZST + `Copy` type
//     that is not proof of anything without a value (i.e. isomorphic to a
//     newtype of the type it's trying to prove the inhabitation of)
//
// A more flexible (and safer) solution to the general problem could exist once
// `const`-generic parameters can have type parameters in their types:
//
// ```kotlin
// fun <A, R> ffiWrapper(arg: A, f: (A) -> R): R {
//     return f(arg)
// }
// ```

internal fun <R> reifyToExternCFnHrtBridge(f: (BridgeConfig) -> R): (BridgeConfig) -> R {
    inline fun <reified R> wrapper(bridge: BridgeConfig): R {
        val f1 = mem.conjureZst<(BridgeConfig) -> R>()
        return f1(bridge)
    }
    // Note(eddyb) describe the `F` type (e.g. via `typeName<F>`) once panic
    // formatting becomes possible in `const fun`.
    run {
        check(sizeOf<(BridgeConfig) -> R>(typeLayoutT = ObjectSlotLayout<(BridgeConfig) -> R>()) == 0) { "selfless_reify: closure must be zero-sized" }
    }
    return wrapper<R, (BridgeConfig) -> R>
}

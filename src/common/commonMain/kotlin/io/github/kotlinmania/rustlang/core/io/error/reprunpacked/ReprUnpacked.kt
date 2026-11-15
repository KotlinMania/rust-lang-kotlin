// port-lint: source io/error/repr_unpacked.rs
package io.github.kotlinmania.rustlang.core.io.error.reprunpacked

import io.github.kotlinmania.rustlang.core.io.error.Custom
import io.github.kotlinmania.rustlang.core.io.error.CustomOwner
import io.github.kotlinmania.rustlang.core.io.error.ErrorData
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.RawOsError
import io.github.kotlinmania.rustlang.core.io.error.SimpleMessage

// This is a fairly simple unpacked error representation that's used on
// non-64bit targets, where the packed 64 bit representation wouldn't work, and
// would have no benefit.

public typealias Inner = ErrorData<CustomOwner>

public open class Repr internal constructor(
    valueCtorArg: Inner,
) {
    public var value: Inner = valueCtorArg
        internal set

    public var field0: Inner
        get() = value
        set(v) {
            value = v
        }

    public open fun intoData(): ErrorData<CustomOwner> = this.value

    public open fun data(): ErrorData<Custom> =
        when (val matchVal = this.value) {
            is Inner.Os -> {
                val (c) = matchVal
                ErrorData.Os(c)
            }
            is Inner.Simple -> {
                val (k) = matchVal
                ErrorData.Simple(k)
            }
            is Inner.SimpleMessage -> {
                val (m) = matchVal
                ErrorData.SimpleMessage(m)
            }
            is Inner.Custom -> {
                val (m1) = matchVal
                ErrorData.Custom(m1.customRef())
            }
        }

    public open fun dataMut(): ErrorData<Custom> =
        when (val matchVal = this.value) {
            is Inner.Os -> {
                val (c) = matchVal
                ErrorData.Os(c)
            }
            is Inner.Simple -> {
                val (k) = matchVal
                ErrorData.Simple(k)
            }
            is Inner.SimpleMessage -> {
                val (m) = matchVal
                ErrorData.SimpleMessage(m)
            }
            is Inner.Custom -> {
                val (m1) = matchVal
                ErrorData.Custom(m1.customMut())
            }
        }

    public companion object {
        public operator fun invoke(
            value: Inner,
        ): Repr =
            Repr(
                valueCtorArg = value,
            )

        public fun newCustom(b: CustomOwner): Repr = Repr(Inner.Custom(b))

        public fun newOs(code: RawOsError): Repr = Repr(Inner.Os(code))

        public fun newSimple(kind: ErrorKind): Repr = Repr(Inner.Simple(kind))

        public fun newSimpleMessage(m: SimpleMessage): Repr = Repr(Inner.SimpleMessage(m))
    }
}

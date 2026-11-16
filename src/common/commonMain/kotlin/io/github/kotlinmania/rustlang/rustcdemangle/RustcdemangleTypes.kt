package io.github.kotlinmania.rustlang.rustcdemangle

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result

public sealed class DemangleStyle {
    public data class Legacy(public var value: io.github.kotlinmania.rustlang.rustcdemangle.legacy.Demangle) : DemangleStyle()
    public data class V0(public var value: io.github.kotlinmania.rustlang.rustcdemangle.v0.Demangle) : DemangleStyle()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is DemangleStyle.Legacy -> {
                val (d) = this
                run { f.append((d).toString()); Result.Ok(Unit) }
            }
            is DemangleStyle.V0 -> {
                val (d1) = this
                run { f.append((d1).toString()); Result.Ok(Unit) }
            }
        }
}

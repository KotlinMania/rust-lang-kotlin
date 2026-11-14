package io.github.kotlinmania.rustlang.rustcdemangle

public sealed class DemangleStyle {
    public data class Legacy(public var value: io.github.kotlinmania.rustlang.rustcdemangle.legacy.Demangle) : DemangleStyle()
    public data class V0(public var value: io.github.kotlinmania.rustlang.rustcdemangle.v0.Demangle) : DemangleStyle()

    public open fun fmt(f: StringBuilder) {
        when (this) {
            is DemangleStyle.Legacy -> {
                val (d) = this
                f.append((d).toString())
            }
            is DemangleStyle.V0 -> {
                val (d1) = this
                f.append((d1).toString())
            }
        }
    }
}

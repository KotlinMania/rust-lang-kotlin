// port-lint: source symbolize/noop.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.noop

import io.github.kotlinmania.rustlang.backtrace.symbolize.ResolveWhat
import io.github.kotlinmania.rustlang.backtrace.symbolize.SymbolName
import io.github.kotlinmania.rustlang.backtrace.types.BytesOrWideString
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Empty symbolication strategy used to compile for platforms that have no
// support.

public fun resolve(addr: ResolveWhat, cb: (io.github.kotlinmania.rustlang.backtrace.symbolize.Symbol) -> Unit) {}

public open class Symbol(
    public var marker: Unit,
) {
    public open fun name(): SymbolName? = null

    public open fun addr(): MutPtr? = null

    public open fun filenameRaw(): BytesOrWideString? = null

    public open fun filename(): Path? = null

    public open fun lineno(): UInt? = null

    public open fun colno(): UInt? = null

    public companion object
}

public fun clearSymbolCache() {}

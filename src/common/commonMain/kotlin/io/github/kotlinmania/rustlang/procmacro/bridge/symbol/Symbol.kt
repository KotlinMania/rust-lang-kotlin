// port-lint: source bridge/symbol.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.symbol

import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU32Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.procmacro.bridge.Decode
import io.github.kotlinmania.rustlang.procmacro.bridge.Encode
import io.github.kotlinmania.rustlang.procmacro.bridge.Mark
import io.github.kotlinmania.rustlang.procmacro.bridge.arena.Arena
import io.github.kotlinmania.rustlang.procmacro.bridge.buffer.Buffer
import io.github.kotlinmania.rustlang.procmacro.bridge.client.Methods
import io.github.kotlinmania.rustlang.procmacro.bridge.decode
import io.github.kotlinmania.rustlang.procmacro.bridge.encode
import io.github.kotlinmania.rustlang.procmacro.bridge.fxhash.FxHashMap
import io.github.kotlinmania.rustlang.procmacro.bridge.mark
import io.github.kotlinmania.rustlang.procmacro.bridge.server.HandleStore
import io.github.kotlinmania.rustlang.procmacro.bridge.server.MarkedSymbol
import io.github.kotlinmania.rustlang.procmacro.bridge.server.Server

// Client-side interner used for symbols.
//
// This is roughly based on the symbol interner from `rustcSpan` and the
// DroplessArena from `rustcArena`. It is unfortunately a complete
// copy/re-implementation rather than a dependency as it is difficult to depend
// on crates from within `procMacro`, due to it being built at the same time
// as `std`.
//
// If at some point in the future it becomes easier to add dependencies to
// procMacro, this module should probably be removed or simplified.

/**
 * Handle for a symbol string stored within the Interner.
 */
public open class Symbol internal constructor(
    valueCtorArg: NonZero<UInt, NonZeroU32Inner>,
) : Encode<S>, Decode<S, Symbol> {
    public var value: NonZero<UInt, NonZeroU32Inner> = valueCtorArg
        internal set

    public var field0: NonZero<UInt, NonZeroU32Inner>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Symbol = Symbol(value)

    /**
     * Run a callback with the symbol's string value.
     */
    public open fun <R> with(f: (String) -> R): R = INTERNER.withBorrow({ i -> f(i.get(this)) })

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.with({ s -> f.append((s).toString()) })

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = this.with({ s -> f.append((s).toString()) })

    public override fun encode(w: Buffer, s: S) {
        this.with({ sym -> sym.encode(w, s) })
    }

    public companion object {
        public operator fun invoke(
            value: NonZero<UInt, NonZeroU32Inner>,
        ): Symbol = Symbol(
            valueCtorArg = value,
        )

        /**
         * Intern a new `Symbol`
         */
        public fun new(string: String): Symbol = INTERNER.withBorrowMut({ i -> i.intern(string) })

        /**
         * Creates a new `Symbol` for an identifier.
         *
         * Validates and normalizes before converting it to a symbol.
         */
        public fun newIdent(string: String, isRaw: Boolean): Symbol {
            // Fast-path: check if this is a valid ASCII identifier
            if (Symbol.isValidAsciiIdent(string.encodeToByteArray().map { it.toUByte() }) || string == "\$crate") {
                if (isRaw && !Symbol.canBeRaw(string)) {
                    error("`$string` cannot be a raw identifier")
                }
                return Symbol.new(string)
            }
            // Slow-path: If the string is already ASCII we're done, otherwise ask
            // our server to do this for us over RPC.
            // We don't need to check for identifiers which can't be raw here,
            // because all of them are ASCII.
            return if (string.isAscii()) {
                Result.Err(Unit)
            } else {
                Methods.symbolNormalizeAndValidateIdent(string)
            }.unwrapOrElse({ error("`$string` is not a valid identifier") })
        }

        /**
         * Clear out the thread-local symbol interner, making all previously
         * created symbols invalid such that `with` will panic when called on them.
         */
        public fun invalidateAll() {
            INTERNER.withBorrowMut({ i -> i.clear() })
        }

        /**
         * Checks if the ident is a valid ASCII identifier.
         *
         * This is a short-circuit which is cheap to implement within the
         * proc-macro client to avoid RPC when creating simple idents, but may
         * return `false` for a valid identifier if it contains non-ASCII
         * characters.
         */
        public fun isValidAsciiIdent(bytes: List<UByte>): Boolean =
            when (val matchVal = bytes.first()) {
                95u, in 97u..122u, in 65u..90u -> true
                else -> false
            } &&
                ((bytes).subList(1, (bytes).size)).all({ b -> when (b) {
                    95u, in 97u..122u, in 65u..90u, in 48u..57u -> true
                    else -> false
                } })

        // Mimics the behavior of `Symbol.canBeRaw` from `rustcSpan`
        public fun canBeRaw(string: String): Boolean =
            !when (string) {
                "_", "super", "self", "Self", "crate", "\$crate" -> true
                else -> false
            }

        public fun <S> decode(r: List<UByte>, s: S): Symbol = Symbol.new(String.decode(r, s))
    }
}

public fun <TokenStream, Span, Symbol, S : Server<TokenStream, Span, Symbol>> decodeMarkedSymbolS(r: List<UByte>, s: HandleStore<S>, internSymbolSFn: (String) -> Symbol): MarkedSymbol<S> = Mark.mark(internSymbolSFn(String.decode(r, s)))

public fun <TokenStream, Span, Symbol, S : Server<TokenStream, Span, Symbol>> MarkedSymbol<S>.encode(w: Buffer, s: HandleStore<S>, withSymbolStringSFn: (Symbol, (String) -> Unit) -> Unit) {
    withSymbolStringSFn(this.unmark(), { sym -> sym.encode(w, s) })
}

/**
 * Basic interner for a `Symbol`, inspired by the one in `rustcSpan`.
 */
public open class Interner internal constructor(
    public var arena: Arena,
    namesCtorArg: FxHashMap<String, Symbol>,
    public var strings: MutableList<String>,
    symBaseCtorArg: NonZero<UInt, NonZeroU32Inner>,
) {
    // SAFETY: These `static` scopes are actually references to data owned
    // by the Arena. This is safe, as we never return them as static references
    // from `Interner`.
    public var names: FxHashMap<String, Symbol> = namesCtorArg
        internal set

    // The offset to apply to symbol names stored in the interner. This is used
    // to ensure that symbol names are not re-used after the interner is
    // cleared.
    public var symBase: NonZero<UInt, NonZeroU32Inner> = symBaseCtorArg
        internal set

    public open fun intern(string: String): Symbol {
        when (val matchVal = this.names.get(string)) {
            null -> {}
            else -> {
                val name = matchVal
                return name
            }
        }
        val name1 = Symbol((this.symBase.checkedAdd(this.strings.size.toUInt()) ?: error("`proc_macro` symbol name overflow")))
        val string1: String = this.arena.allocStr(string)
        // SAFETY: we can extend the arena allocation to `static` because we
        // only access these while the arena is still alive.
        val string2: String = (UnresolvedTypeLayout<String>()).readNative(((constPtrFromRustCast(string1))).rawAddress)
        this.strings.add(string2)
        this.names.insert(string2, name1)
        return name1
    }

    /**
     * Reads a symbol's value from the store while it is held.
     */
    public open fun get(symbol: Symbol): String {
        // NOTE: Subtract out the offset which was added to make the symbol
        // nonzero and prevent symbol name re-use.
        val name = (symbol.value.get().checkedSub(this.symBase.get()) ?: error("use-after-free of `proc_macro` symbol"))
        return this.strings[name.toInt()]
    }

    /**
     * Clear all symbols from the store, invalidating them such that `get` will
     * panic if they are accessed in the future.
     */
    public open fun clear() {
        // NOTE: Be careful not to panic here, as we may be called on the client
        // when a `catchUnwind` isn't installed.
        this.symBase = this.symBase.saturatingAdd(this.strings.size.toUInt())
        this.names.clear()
        this.strings.clear()
        // SAFETY: This is cleared after the names and strings tables are
        // cleared out, so no references into the arena should remain.
        this.arena = Arena.new()
    }

    public companion object {
        public operator fun invoke(
            arena: Arena,
            names: FxHashMap<String, Symbol>,
            strings: MutableList<String>,
            symBase: NonZero<UInt, NonZeroU32Inner>,
        ): Interner = Interner(
            arena = arena,
            namesCtorArg = names,
            strings = strings,
            symBaseCtorArg = symBase,
        )
    }
}

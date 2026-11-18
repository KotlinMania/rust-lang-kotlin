// port-lint: source bridge/client.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.client

import io.github.kotlinmania.rustlang.core.cell.RefCell
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.panic.unwindsafe.AssertUnwindSafe
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.procmacro.bridge.ApiTags
import io.github.kotlinmania.rustlang.procmacro.bridge.BridgeConfig
import io.github.kotlinmania.rustlang.procmacro.bridge.Diagnostic
import io.github.kotlinmania.rustlang.procmacro.bridge.ExpnGlobals
import io.github.kotlinmania.rustlang.procmacro.bridge.Literal
import io.github.kotlinmania.rustlang.procmacro.bridge.Methods
import io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree
import io.github.kotlinmania.rustlang.procmacro.bridge.buffer.Buffer
import io.github.kotlinmania.rustlang.procmacro.bridge.closure.Closure
import io.github.kotlinmania.rustlang.procmacro.bridge.handle.Handle
import io.github.kotlinmania.rustlang.procmacro.bridge.panicmessage.PanicMessage
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.Decode
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.decode
import io.github.kotlinmania.rustlang.procmacro.bridge.selflessreify.reifyToExternCFnHrtBridge
import io.github.kotlinmania.rustlang.procmacro.bridge.symbol.Symbol
import io.github.kotlinmania.rustlang.std.panic.catchUnwind
import io.github.kotlinmania.rustlang.std.panic.resumeUnwind
import io.github.kotlinmania.rustlang.std.panicking.setHook
import io.github.kotlinmania.rustlang.std.panicking.takeHook
import io.github.kotlinmania.rustlang.std.sync.once.Once

// Client-side types.

public fun interface ClientRunCallback {
    public operator fun invoke(arg0: BridgeConfig): Buffer
}

public open class TokenStream(
    public var handle: Handle,
) {
    // Forward `Drop.drop` to the inherent `drop` method.
    public open fun drop() {
        Methods.tsDrop(TokenStream(handle = this.handle))
    }

    public open fun clone(): TokenStream = Methods.tsClone(this)
}

public fun <S> TokenStream.encode(w: Buffer, s: S) {
    this.handle.encode(w, s)
}

public fun <S> TokenStream.encodeS(w: Buffer, s: S) {
    this.handle.encode(w, s)
}

public fun <S> decodeTokenStream(r: List<UByte>, s: S): TokenStream = TokenStream(handle = Handle.decode(r, s))

public fun TokenStream.encode(w: Buffer, s: Unit) {
    this.value.encode(w, s)
}

public fun decodeTokenStream(r: List<UByte>, s: Unit): TokenStream = TokenStream(Decode.decode(r, s))

public open class Span(
    public var handle: Handle,
) {
    public open fun clone(): Span = Span(handle)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append(Methods.spanDebug(this)); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun defSite(): Span = Bridge.with({ bridge -> bridge.globals.defSite })

        public fun callSite(): Span = Bridge.with({ bridge -> bridge.globals.callSite })

        public fun mixedSite(): Span = Bridge.with({ bridge -> bridge.globals.mixedSite })
    }
}

public fun <S> Span.encode(w: Buffer, s: S) {
    this.handle.encode(w, s)
}

public fun <S> decodeSpan(r: List<UByte>, s: S): Span = Span(handle = Handle.decode(r, s))

public fun Methods.Companion.trackEnvVar(variable: String, value: String?) {
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TrackEnvVar.encode(buf, Unit)
        variable.encode(buf, Unit)
        value.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })
}

public fun Methods.Companion.trackPath(path: String) {
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TrackPath.encode(buf, Unit)
        path.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })
}

public fun Methods.Companion.literalFromStr(s: String): Result<Literal<Span, Symbol>, String> =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.LiteralFromStr.encode(buf, Unit)
        s.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.emitDiagnostic(diagnostic: Diagnostic<Span>) {
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.EmitDiagnostic.encode(buf, Unit)
        diagnostic.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })
}

public fun Methods.Companion.tsDrop(stream: TokenStream) {
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsDrop.encode(buf, Unit)
        stream.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })
}

public fun Methods.Companion.tsClone(stream: TokenStream): TokenStream =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsClone.encode(buf, Unit)
        stream.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.tsIsEmpty(stream: TokenStream): Boolean =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsIsEmpty.encode(buf, Unit)
        stream.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.tsExpandExpr(stream: TokenStream): Result<TokenStream, Unit> =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsExpandExpr.encode(buf, Unit)
        stream.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.tsFromStr(src: String): Result<TokenStream, String> =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsFromStr.encode(buf, Unit)
        src.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.tsToString(stream: TokenStream): String =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsToString.encode(buf, Unit)
        stream.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.tsFromTokenTree(tree: TokenTree<TokenStream, Span, Symbol>): TokenStream =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsFromTokenTree.encode(buf, Unit)
        tree.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.tsConcatTrees(base: TokenStream?, trees: List<TokenTree<TokenStream, Span, Symbol>>): TokenStream =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsConcatTrees.encode(buf, Unit)
        base.encode(buf, Unit)
        trees.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.tsConcatStreams(base: TokenStream?, streams: List<TokenStream>): TokenStream =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsConcatStreams.encode(buf, Unit)
        base.encode(buf, Unit)
        streams.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.tsIntoTrees(stream: TokenStream): List<TokenTree<TokenStream, Span, Symbol>> =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.TsIntoTrees.encode(buf, Unit)
        stream.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanDebug(span: Span): String =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanDebug.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanParent(span: Span): Span? =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanParent.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanSource(span: Span): Span =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanSource.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanByteRange(span: Span): IntRange =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanByteRange.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanStart(span: Span): Span =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanStart.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanEnd(span: Span): Span =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanEnd.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanLine(span: Span): Int =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanLine.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanColumn(span: Span): Int =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanColumn.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanFile(span: Span): String =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanFile.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanLocalFile(span: Span): String? =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanLocalFile.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanJoin(span: Span, other: Span): Span? =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanJoin.encode(buf, Unit)
        span.encode(buf, Unit)
        other.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanSubspan(span: Span, start: Bound<Int>, end: Bound<Int>): Span? =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanSubspan.encode(buf, Unit)
        span.encode(buf, Unit)
        start.encode(buf, Unit)
        end.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanResolvedAt(span: Span, at: Span): Span =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanResolvedAt.encode(buf, Unit)
        span.encode(buf, Unit)
        at.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanSourceText(span: Span): String? =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanSourceText.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanSaveSpan(span: Span): Int =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanSaveSpan.encode(buf, Unit)
        span.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.spanRecoverProcMacroSpan(id: Int): Span =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SpanRecoverProcMacroSpan.encode(buf, Unit)
        id.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public fun Methods.Companion.symbolNormalizeAndValidateIdent(string: String): Result<Symbol, Unit> =
    Bridge.with({ bridge ->
        var buf = bridge.cachedBuffer.also { bridge.cachedBuffer = null }
        buf.clear()
        ApiTags.SymbolNormalizeAndValidateIdent.encode(buf, Unit)
        string.encode(buf, Unit)
        buf = bridge.dispatch.call(buf)
        val r = Result.decode(buf, Unit)
        bridge.cachedBuffer = buf
        r.unwrapOrElse({ e -> resumeUnwind(e.into()) })
    })

public open class Bridge internal constructor(
    /**
     * Reusable buffer (only `clear`-ed, never shrunk), primarily
     * used for making requests.
     */
    public var cachedBuffer: Buffer,
    /**
     * Server-side function that the client uses to make requests.
     */
    public var dispatch: Closure,
    globalsCtorArg: ExpnGlobals<Span>,
) {
    /**
     * Provided globals for this macro expansion.
     */
    public var globals: ExpnGlobals<Span> = globalsCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            cachedBuffer: Buffer,
            dispatch: Closure,
            globals: ExpnGlobals<Span>,
        ): Bridge = Bridge(
            cachedBuffer = cachedBuffer,
            dispatch = dispatch,
            globalsCtorArg = globals,
        )

        public fun <R> with(f: (Bridge) -> R): R =
            with({ state ->
                val bridge = state.expect("procedural macro API is used outside of a procedural macro")
                var bridge1 = bridge.tryBorrowMut().expect("procedural macro API is used while it's already in use")
                f(bridge1)
            })
    }
}

public fun <R> set(state: RefCell<Bridge>, f: () -> R): R {
    open class RestoreOnDrop(
        public var value: ConstPtr,
    ) {
        public var field0: ConstPtr
            get() = value
            set(v) {
                value = v
            }

        public open fun drop() {
            BRIDGE_STATE.set(this.value)
        }
    }
    return f()
}

public fun <R> with(f: (RefCell<Bridge>?) -> R): R {
    val state = BRIDGE_STATE.get()
    // SAFETY: the only place where the pointer is set is in `set`. It puts
    // back the previous value after the inner call has returned, so we know
    // that as long as the pointer is not null, it came from a reference to
    // a `RefCell<Bridge>` that outlasts the call to this function. Since `f`
    // works the same for any scope of the bridge, including the actual
    // one, we can lie here and say that the scope is `static` without
    // anyone noticing.
    val bridge = state.cast().asRef()
    return f(bridge)
}

public fun isAvailable(): Boolean = with({ s -> s != null })

/**
 * A client-side RPC entry-point, which may be using a different `procMacro`
 * from the one used by the server, but can be invoked compatibly.
 *
 * Note that the input and output type parameters are erased. They do not
 * participate in the ABI, so while using the wrong runN method will likely
 * result in a panic, it will not result in UB.
 */
public open class Client(
    public var run: ClientRunCallback,
) {
    public open fun clone(): Client = Client(run)

    public companion object {
        public fun expand1(f: (TokenStream) -> TokenStream): Client =
            Client(run = reifyToExternCFnHrtBridge({ bridge ->
                runClient(bridge, f)
            }))

        public fun expand2(f: (TokenStream, TokenStream) -> TokenStream): Client =
            Client(run = reifyToExternCFnHrtBridge({ bridge ->
                runClient(bridge, { (input, input2) -> f(input, input2) })
            }))
    }
}

public fun maybeInstallPanicHook(forceShowPanics: Boolean) {
    // Hide the default panic output within `procMacro` expansions.
    // NB. the server can't do this because it may use a different std.
    val HIDE_PANICS_DURING_EXPANSION: Once  = Once.new()
    HIDE_PANICS_DURING_EXPANSION.callOnce({
        val prev = takeHook()
        setHook({ info ->
            // We normally report panics by catching unwinds and passing the payload from the
            // unwind back to the compiler, but if the panic doesn't unwind we'll abort before
            // the compiler has a chance to print an error. So we special-case PanicInfo where
            // canUnwind is false.
            if (forceShowPanics || !isAvailable() || !info.canUnwind()) {
                prev(info)
            }
        })
    })
}

/**
 * Client-side helper for handling client panics, entering the bridge,
 * deserializing input and serializing output.
 */
public fun <A : Decode<Unit, A>> runClient(config: BridgeConfig, f: (A) -> TokenStream): Buffer {
    var buf = config.input
    val dispatch = config.dispatch
    val forceShowPanics = config.forceShowPanics
    val res =
        catchUnwind(AssertUnwindSafe({
            maybeInstallPanicHook(forceShowPanics)
            // Make sure the symbol store is empty before decoding inputs.
            Symbol.invalidateAll()
            val reader = buf
            val (globals, input) = Pair.decode(reader, Unit)
            // Put the buffer we used for input back in the `Bridge` for requests.
            val state = RefCell.new(Bridge(cachedBuffer = buf.also { buf = null }, dispatch = dispatch, globals = globals))
            val output = set(state, { f(input) })
            // Take the `cachedBuffer` back out, for the output value.
            buf = RefCell.intoInner(state).cachedBuffer
            output
        }))
    // Serialize response of type `Result<R, PanicMessage>`.
    buf.clear()
    res.mapErr(PanicMessage::from).encode(buf, Unit)
    // Now that a response has been serialized, invalidate all symbols
    // registered with the interner.
    Symbol.invalidateAll()
    return buf
}

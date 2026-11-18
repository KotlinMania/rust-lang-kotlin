// port-lint: source bridge/server.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.server

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.panic.unwindsafe.AssertUnwindSafe
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.procmacro.bridge.ApiTags
import io.github.kotlinmania.rustlang.procmacro.bridge.BridgeConfig
import io.github.kotlinmania.rustlang.procmacro.bridge.Diagnostic
import io.github.kotlinmania.rustlang.procmacro.bridge.ExpnGlobals
import io.github.kotlinmania.rustlang.procmacro.bridge.Literal
import io.github.kotlinmania.rustlang.procmacro.bridge.Mark
import io.github.kotlinmania.rustlang.procmacro.bridge.Marked
import io.github.kotlinmania.rustlang.procmacro.bridge.TokenTree
import io.github.kotlinmania.rustlang.procmacro.bridge.buffer.Buffer
import io.github.kotlinmania.rustlang.procmacro.bridge.client.Client
import io.github.kotlinmania.rustlang.procmacro.bridge.client.Span
import io.github.kotlinmania.rustlang.procmacro.bridge.client.Symbol
import io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream
import io.github.kotlinmania.rustlang.procmacro.bridge.handle.Handle
import io.github.kotlinmania.rustlang.procmacro.bridge.handle.InternedStore
import io.github.kotlinmania.rustlang.procmacro.bridge.handle.OwnedStore
import io.github.kotlinmania.rustlang.procmacro.bridge.mark
import io.github.kotlinmania.rustlang.procmacro.bridge.panicmessage.PanicMessage
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.Decode
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.Encode
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.decode
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.encode
import io.github.kotlinmania.rustlang.std.panic.catchUnwind
import io.github.kotlinmania.rustlang.std.sync.mpsc.Receiver
import io.github.kotlinmania.rustlang.std.sync.mpsc.SyncSender
import io.github.kotlinmania.rustlang.std.sync.mpsc.syncChannel

// Server-side traits.

public fun interface RunBridgeAndClientRunClientCallback {
    public operator fun invoke(arg0: BridgeConfig): Buffer
}

public open class HandleStore<S : Server<TokenStream, Span, Symbol>, TokenStream, Span, Symbol> internal constructor(
    tokenStreamCtorArg: OwnedStore<MarkedTokenStream<S>>,
    spanCtorArg: InternedStore<MarkedSpan<S>>,
) {
    public var tokenStream: OwnedStore<MarkedTokenStream<S>> = tokenStreamCtorArg
        internal set

    public var span: InternedStore<MarkedSpan<S>> = spanCtorArg
        internal set

    public companion object {
        public operator fun <S : Server<TokenStream, Span, Symbol>, TokenStream, Span, Symbol> invoke(
            tokenStream: OwnedStore<MarkedTokenStream<S>>,
            span: InternedStore<MarkedSpan<S>>,
        ): HandleStore<S, TokenStream, Span, Symbol> = HandleStore<S, TokenStream, Span, Symbol>(
            tokenStreamCtorArg = tokenStream,
            spanCtorArg = span,
        )

        public fun <TokenStream, Span, Symbol, S : Server<TokenStream, Span, Symbol>> new(): HandleStore<S, TokenStream, Span, Symbol> {
            val TOKEN_STREAM: AtomicU32  = AtomicU32.new(1)
            val SPAN: AtomicU32  = AtomicU32.new(1)
            return HandleStore(tokenStream = OwnedStore.new(TOKEN_STREAM), span = InternedStore.new(SPAN))
        }
    }
}

public typealias MarkedTokenStream<S> = Marked<TokenStream, TokenStream>

public typealias MarkedSpan<S> = Marked<Span, Span>

public typealias MarkedSymbol<S> = Marked<Symbol, Symbol>

public fun <TokenStream, Span, Symbol, S : Server<TokenStream, Span, Symbol>> MarkedTokenStream<S>.encode(w: Buffer, s: HandleStore<S, TokenStream, Span, Symbol>) {
    s.tokenStream.alloc(this).encode(w, s)
}

public fun <TokenStream, Span, Symbol, S : Server<TokenStream, Span, Symbol>> decodeMarkedTokenStreamS(r: List<UByte>, s: HandleStore<S, TokenStream, Span, Symbol>): MarkedTokenStream<S> = s.tokenStream.take(Handle.decode(r, Unit))

public fun <TokenStream, Span, Symbol, S : Server<TokenStream, Span, Symbol>> MarkedSpan<S>.encode(w: Buffer, s: HandleStore<S, TokenStream, Span, Symbol>) {
    s.span.alloc(this).encode(w, s)
}

public fun <TokenStream, Span, Symbol, S : Server<TokenStream, Span, Symbol>> decodeMarkedSpanS(r: List<UByte>, s: HandleStore<S, TokenStream, Span, Symbol>): MarkedSpan<S> = s.span.copy(Handle.decode(r, Unit))

public interface Server<TokenStream, Span, Symbol> {
    public fun globals(): ExpnGlobals<Span>

    /**
     * Intern a symbol received from RPC
     */
    public fun internSymbol(ident: String): Symbol

    /**
     * Recover the string value of a symbol, and invoke a callback with it.
     */
    public fun withSymbolString(symbol: Symbol, f: (String) -> Unit)

    public fun trackEnvVar(variable: String, value: String?)

    public fun trackPath(path: String)

    public fun literalFromStr(s: String): Result<Literal<Span, Symbol>, String>

    public fun emitDiagnostic(diagnostic: Diagnostic<Span>)

    public fun tsDrop(stream: TokenStream)

    public fun tsClone(stream: TokenStream): TokenStream

    public fun tsIsEmpty(stream: TokenStream): Boolean

    public fun tsExpandExpr(stream: TokenStream): Result<TokenStream, Unit>

    public fun tsFromStr(src: String): Result<TokenStream, String>

    public fun tsToString(stream: TokenStream): String

    public fun tsFromTokenTree(tree: TokenTree<TokenStream, Span, Symbol>): TokenStream

    public fun tsConcatTrees(base: TokenStream?, trees: List<TokenTree<TokenStream, Span, Symbol>>): TokenStream

    public fun tsConcatStreams(base: TokenStream?, streams: List<TokenStream>): TokenStream

    public fun tsIntoTrees(stream: TokenStream): List<TokenTree<TokenStream, Span, Symbol>>

    public fun spanDebug(span: Span): String

    public fun spanParent(span: Span): Span?

    public fun spanSource(span: Span): Span

    public fun spanByteRange(span: Span): IntRange

    public fun spanStart(span: Span): Span

    public fun spanEnd(span: Span): Span

    public fun spanLine(span: Span): Int

    public fun spanColumn(span: Span): Int

    public fun spanFile(span: Span): String

    public fun spanLocalFile(span: Span): String?

    public fun spanJoin(span: Span, other: Span): Span?

    public fun spanSubspan(span: Span, start: Bound<Int>, end: Bound<Int>): Span?

    public fun spanResolvedAt(span: Span, at: Span): Span

    public fun spanSourceText(span: Span): String?

    public fun spanSaveSpan(span: Span): Int

    public fun spanRecoverProcMacroSpan(id: Int): Span

    public fun symbolNormalizeAndValidateIdent(string: String): Result<Symbol, Unit>
}

// Note(eddyb) `public` only for `ExecutionStrategy` below.
public open class Dispatcher<S : Server<TokenStream, Span, Symbol>, TokenStream, Span, Symbol> internal constructor(
    handleStoreCtorArg: HandleStore<S, TokenStream, Span, Symbol>,
    public var server: S,
) {
    public var handleStore: HandleStore<S, TokenStream, Span, Symbol> = handleStoreCtorArg
        internal set

    public open fun dispatch(buf: Buffer): Buffer {
        val handleStore = this.handleStore
        val server = this.server
        val reader = buf
        when (ApiTags.decode(reader, Unit)) {
            ApiTags.TrackEnvVar -> {
                var callMethod =
                    {
                        val variable = String.decode(reader, handleStore).unmark()
                        val value = String?.decode(reader, handleStore).unmark()
                        val r = server.trackEnvVar(variable, value)
                        r
                    }
                val r =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r.encode(buf, handleStore)
            }
            ApiTags.TrackPath -> {
                var callMethod1 =
                    {
                        val path = String.decode(reader, handleStore).unmark()
                        val r1 = server.trackPath(path)
                        r1
                    }
                val r1 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod1())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod1)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r1.encode(buf, handleStore)
            }
            ApiTags.LiteralFromStr -> {
                var callMethod2 =
                    {
                        val s = String.decode(reader, handleStore).unmark()
                        val r2 = server.literalFromStr(s)
                        val r3: Result<Literal<MarkedSpan<S>, MarkedSymbol<S>>, String> = Mark.mark(r2)
                        r3
                    }
                val r2 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod2())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod2)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r2.encode(buf, handleStore)
            }
            ApiTags.EmitDiagnostic -> {
                var callMethod3 =
                    {
                        val diagnostic = Diagnostic.decode(reader, handleStore).unmark()
                        val r3 = server.emitDiagnostic(diagnostic)
                        r3
                    }
                val r3 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod3())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod3)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r3.encode(buf, handleStore)
            }
            ApiTags.TsDrop -> {
                var callMethod4 =
                    {
                        val stream = MarkedTokenStream.decode(reader, handleStore).unmark()
                        val r4 = server.tsDrop(stream)
                        r4
                    }
                val r4 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod4())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod4)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r4.encode(buf, handleStore)
            }
            ApiTags.TsClone -> {
                var callMethod5 =
                    {
                        val stream = MarkedTokenStream.decode(reader, handleStore).unmark()
                        val r5 = server.tsClone(stream)
                        val r6: MarkedTokenStream<S> = Mark.mark(r5)
                        r6
                    }
                val r5 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod5())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod5)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r5.encode(buf, handleStore)
            }
            ApiTags.TsIsEmpty -> {
                var callMethod6 =
                    {
                        val stream = MarkedTokenStream.decode(reader, handleStore).unmark()
                        val r6 = server.tsIsEmpty(stream)
                        val r7: Boolean = Mark.mark(r6)
                        r7
                    }
                val r6 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod6())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod6)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r6.encode(buf, handleStore)
            }
            ApiTags.TsExpandExpr -> {
                var callMethod7 =
                    {
                        val stream = MarkedTokenStream.decode(reader, handleStore).unmark()
                        val r7 = server.tsExpandExpr(stream)
                        val r8: Result<MarkedTokenStream<S>, Unit> = Mark.mark(r7)
                        r8
                    }
                val r7 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod7())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod7)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r7.encode(buf, handleStore)
            }
            ApiTags.TsFromStr -> {
                var callMethod8 =
                    {
                        val src = String.decode(reader, handleStore).unmark()
                        val r8 = server.tsFromStr(src)
                        val r9: Result<MarkedTokenStream<S>, String> = Mark.mark(r8)
                        r9
                    }
                val r8 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod8())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod8)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r8.encode(buf, handleStore)
            }
            ApiTags.TsToString -> {
                var callMethod9 =
                    {
                        val stream = MarkedTokenStream.decode(reader, handleStore).unmark()
                        val r9 = server.tsToString(stream)
                        val r10: String = Mark.mark(r9)
                        r10
                    }
                val r9 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod9())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod9)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r9.encode(buf, handleStore)
            }
            ApiTags.TsFromTokenTree -> {
                var callMethod10 =
                    {
                        val tree = TokenTree.decode(reader, handleStore).unmark()
                        val r10 = server.tsFromTokenTree(tree)
                        val r11: MarkedTokenStream<S> = Mark.mark(r10)
                        r11
                    }
                val r10 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod10())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod10)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r10.encode(buf, handleStore)
            }
            ApiTags.TsConcatTrees -> {
                var callMethod11 =
                    {
                        val base = MarkedTokenStream.decode(reader, handleStore).unmark()
                        val trees = List.decode(reader, handleStore).unmark()
                        val r11 = server.tsConcatTrees(base, trees)
                        val r12: MarkedTokenStream<S> = Mark.mark(r11)
                        r12
                    }
                val r11 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod11())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod11)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r11.encode(buf, handleStore)
            }
            ApiTags.TsConcatStreams -> {
                var callMethod12 =
                    {
                        val base = MarkedTokenStream.decode(reader, handleStore).unmark()
                        val streams = List.decode(reader, handleStore).unmark()
                        val r12 = server.tsConcatStreams(base, streams)
                        val r13: MarkedTokenStream<S> = Mark.mark(r12)
                        r13
                    }
                val r12 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod12())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod12)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r12.encode(buf, handleStore)
            }
            ApiTags.TsIntoTrees -> {
                var callMethod13 =
                    {
                        val stream = MarkedTokenStream.decode(reader, handleStore).unmark()
                        val r13 = server.tsIntoTrees(stream)
                        val r14: List<TokenTree<MarkedTokenStream<S>, MarkedSpan<S>, MarkedSymbol<S>>> = Mark.mark(r13)
                        r14
                    }
                val r13 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod13())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod13)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r13.encode(buf, handleStore)
            }
            ApiTags.SpanDebug -> {
                var callMethod14 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r14 = server.spanDebug(span)
                        val r15: String = Mark.mark(r14)
                        r15
                    }
                val r14 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod14())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod14)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r14.encode(buf, handleStore)
            }
            ApiTags.SpanParent -> {
                var callMethod15 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r15 = server.spanParent(span)
                        val r16: MarkedSpan<S>? = Mark.mark(r15)
                        r16
                    }
                val r15 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod15())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod15)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r15.encode(buf, handleStore)
            }
            ApiTags.SpanSource -> {
                var callMethod16 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r16 = server.spanSource(span)
                        val r17: MarkedSpan<S> = Mark.mark(r16)
                        r17
                    }
                val r16 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod16())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod16)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r16.encode(buf, handleStore)
            }
            ApiTags.SpanByteRange -> {
                var callMethod17 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r17 = server.spanByteRange(span)
                        val r18: IntRange = Mark.mark(r17)
                        r18
                    }
                val r17 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod17())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod17)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r17.encode(buf, handleStore)
            }
            ApiTags.SpanStart -> {
                var callMethod18 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r18 = server.spanStart(span)
                        val r19: MarkedSpan<S> = Mark.mark(r18)
                        r19
                    }
                val r18 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod18())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod18)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r18.encode(buf, handleStore)
            }
            ApiTags.SpanEnd -> {
                var callMethod19 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r19 = server.spanEnd(span)
                        val r20: MarkedSpan<S> = Mark.mark(r19)
                        r20
                    }
                val r19 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod19())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod19)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r19.encode(buf, handleStore)
            }
            ApiTags.SpanLine -> {
                var callMethod20 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r20 = server.spanLine(span)
                        val r21: Int = Mark.mark(r20)
                        r21
                    }
                val r20 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod20())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod20)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r20.encode(buf, handleStore)
            }
            ApiTags.SpanColumn -> {
                var callMethod21 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r21 = server.spanColumn(span)
                        val r22: Int = Mark.mark(r21)
                        r22
                    }
                val r21 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod21())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod21)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r21.encode(buf, handleStore)
            }
            ApiTags.SpanFile -> {
                var callMethod22 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r22 = server.spanFile(span)
                        val r23: String = Mark.mark(r22)
                        r23
                    }
                val r22 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod22())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod22)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r22.encode(buf, handleStore)
            }
            ApiTags.SpanLocalFile -> {
                var callMethod23 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r23 = server.spanLocalFile(span)
                        val r24: String? = Mark.mark(r23)
                        r24
                    }
                val r23 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod23())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod23)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r23.encode(buf, handleStore)
            }
            ApiTags.SpanJoin -> {
                var callMethod24 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val other = MarkedSpan.decode(reader, handleStore).unmark()
                        val r24 = server.spanJoin(span, other)
                        val r25: MarkedSpan<S>? = Mark.mark(r24)
                        r25
                    }
                val r24 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod24())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod24)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r24.encode(buf, handleStore)
            }
            ApiTags.SpanSubspan -> {
                var callMethod25 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val start = Bound.decode(reader, handleStore).unmark()
                        val end = Bound.decode(reader, handleStore).unmark()
                        val r25 = server.spanSubspan(span, start, end)
                        val r26: MarkedSpan<S>? = Mark.mark(r25)
                        r26
                    }
                val r25 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod25())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod25)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r25.encode(buf, handleStore)
            }
            ApiTags.SpanResolvedAt -> {
                var callMethod26 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val at = MarkedSpan.decode(reader, handleStore).unmark()
                        val r26 = server.spanResolvedAt(span, at)
                        val r27: MarkedSpan<S> = Mark.mark(r26)
                        r27
                    }
                val r26 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod26())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod26)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r26.encode(buf, handleStore)
            }
            ApiTags.SpanSourceText -> {
                var callMethod27 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r27 = server.spanSourceText(span)
                        val r28: String? = Mark.mark(r27)
                        r28
                    }
                val r27 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod27())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod27)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r27.encode(buf, handleStore)
            }
            ApiTags.SpanSaveSpan -> {
                var callMethod28 =
                    {
                        val span = MarkedSpan.decode(reader, handleStore).unmark()
                        val r28 = server.spanSaveSpan(span)
                        val r29: Int = Mark.mark(r28)
                        r29
                    }
                val r28 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod28())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod28)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r28.encode(buf, handleStore)
            }
            ApiTags.SpanRecoverProcMacroSpan -> {
                var callMethod29 =
                    {
                        val id = Int.decode(reader, handleStore).unmark()
                        val r29 = server.spanRecoverProcMacroSpan(id)
                        val r30: MarkedSpan<S> = Mark.mark(r29)
                        r30
                    }
                val r29 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod29())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod29)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r29.encode(buf, handleStore)
            }
            ApiTags.SymbolNormalizeAndValidateIdent -> {
                var callMethod30 =
                    {
                        val string = String.decode(reader, handleStore).unmark()
                        val r30 = server.symbolNormalizeAndValidateIdent(string)
                        val r31: Result<MarkedSymbol<S>, Unit> = Mark.mark(r30)
                        r31
                    }
                val r30 =
                    if (Thread.panicking()) {
                        Result.Ok(callMethod30())
                    } else {
                        catchUnwind(AssertUnwindSafe(callMethod30)).mapErr(PanicMessage::from)
                    }
                buf.clear()
                r30.encode(buf, handleStore)
            }
        }
        return buf
    }

    public companion object {
        public operator fun <S : Server<TokenStream, Span, Symbol>, TokenStream, Span, Symbol> invoke(
            handleStore: HandleStore<S, TokenStream, Span, Symbol>,
            server: S,
        ): Dispatcher<S, TokenStream, Span, Symbol> = Dispatcher<S, TokenStream, Span, Symbol>(
            handleStoreCtorArg = handleStore,
            server = server,
        )
    }
}

// This trait is currently only implemented and used once, inside of this crate.
// We keep it public to allow implementing more complex execution strategies in
// the future, such as wasm proc-macros.
public interface ExecutionStrategy {
    public fun runBridgeAndClient(dispatcher: Dispatcher<Server, TokenStream, Span, Symbol>, input: Buffer, runClient: RunBridgeAndClientRunClientCallback, forceShowPanics: Boolean): Buffer
}

/**
 * Keep `ALREADY_RUNNING_SAME_THREAD` (see also its documentation)
 * set to `true`, preventing same-thread reentrance.
 */
public open class RunningSameThreadGuard(
    public var value: Unit,
) {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun drop() {
        ALREADY_RUNNING_SAME_THREAD.set(false)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<RunningSameThreadGuard> =
            object : TypeLayout<RunningSameThreadGuard> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): RunningSameThreadGuard =
                    RunningSameThreadGuard(
                        (PrimitiveLayouts.UNIT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: RunningSameThreadGuard) {
                    (PrimitiveLayouts.UNIT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RunningSameThreadGuard =
                    RunningSameThreadGuard(
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RunningSameThreadGuard) {
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset, value.value)
                }
            }

        public fun new(): RunningSameThreadGuard {
            val alreadyRunning = ALREADY_RUNNING_SAME_THREAD.replace(true)
            check(!alreadyRunning) { "same-thread nesting (\"reentrance\") of proc macro executions is not supported" }
            return RunningSameThreadGuard(Unit)
        }
    }
}

public open class MaybeCrossThread(
    public var crossThread: Boolean,
) : ExecutionStrategy {
    public override fun runBridgeAndClient(dispatcher: Dispatcher<Server, TokenStream, Span, Symbol>, input: Buffer, runClient: RunBridgeAndClientRunClientCallback, forceShowPanics: Boolean): Buffer =
        if (this.crossThread || ALREADY_RUNNING_SAME_THREAD.get()) {
            var (server, client) = MessagePipe.new()
            val joinHandle =
                Thread.spawn({
                    var dispatch =
                        { b: Buffer ->
                            client.send(b)
                            client.recv().expect("server died while client waiting for reply")
                        }
                    runClient(BridgeConfig(input = input, dispatch = (dispatch).into(), forceShowPanics = forceShowPanics))
                })
            while (true) {
                val matchVal = server.recv()
                if (matchVal == null) break
                val b = matchVal
                server.send(dispatcher.dispatch(b))
            }
            joinHandle.join().unwrap()
        } else {
            var dispatch = { buf -> dispatcher.dispatch(buf) }
            runClient(BridgeConfig(input = input, dispatch = (dispatch).into(), forceShowPanics = forceShowPanics))
        }

    public companion object {
        public fun typeLayout(): TypeLayout<MaybeCrossThread> =
            object : TypeLayout<MaybeCrossThread> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): MaybeCrossThread =
                    MaybeCrossThread(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MaybeCrossThread) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.crossThread)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MaybeCrossThread =
                    MaybeCrossThread(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MaybeCrossThread) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.crossThread)
                }
            }
    }
}

public val SAME_THREAD: MaybeCrossThread = MaybeCrossThread(crossThread = false)

public val CROSS_THREAD: MaybeCrossThread = MaybeCrossThread(crossThread = true)

/**
 * A message pipe used for communicating between server and client threads.
 */
public open class MessagePipe<T>(
    public var tx: SyncSender<T>,
    public var rx: Receiver<T>,
) {
    /**
     * Send a message to the other endpoint of this pipe.
     */
    public open fun send(value: T) {
        this.tx.send(value).unwrap()
    }

    /**
     * Receive a message from the other endpoint of this pipe.
     *
     * Returns `None` if the other end of the pipe has been destroyed, and no
     * message was received.
     */
    public open fun recv(): T? = this.rx.recv().ok()

    public companion object {
        /**
         * Creates a new pair of endpoints for the message pipe.
         */
        public fun <T> new(): Pair<MessagePipe<T>, MessagePipe<T>> {
            val (tx1, rx1) = syncChannel(1)
            val (tx2, rx2) = syncChannel(1)
            return Pair(MessagePipe(tx = tx1, rx = rx2), MessagePipe(tx = tx2, rx = rx1))
        }
    }
}

public fun <TokenStream, Span, Symbol, S : Server<TokenStream, Span, Symbol>, I : Encode<HandleStore<S, TokenStream, Span, Symbol>>, O : Decode<HandleStore<S, TokenStream, Span, Symbol>, O>> runServer(strategy: ExecutionStrategy, server: S, input: I, runClient: RunBridgeAndClientRunClientCallback, forceShowPanics: Boolean): Result<O, PanicMessage> {
    val dispatcher = Dispatcher(handleStore = HandleStore.new(), server = server)
    val globals = dispatcher.server.globals()
    var buf = Buffer.new()
    Pair(ExpnGlobals.mark(globals), input).encode(buf, dispatcher.handleStore)
    buf = strategy.runBridgeAndClient(dispatcher, buf, runClient, forceShowPanics)
    return Result.decode(buf, dispatcher.handleStore)
}

public fun <TokenStream, Span, Symbol, S> Client.run1(strategy: ExecutionStrategy, server: S, input: TokenStream, forceShowPanics: Boolean): Result<TokenStream, PanicMessage> where S : Server<TokenStream, Span, Symbol> {
    val run = this.run
    return runServer(strategy, server, MarkedTokenStream.mark(input), run, forceShowPanics).map({ s -> MarkedTokenStream.unmark(s).unwrapOrDefault() })
}

public fun <TokenStream, Span, Symbol, S> Client.run2(strategy: ExecutionStrategy, server: S, input: TokenStream, input2: TokenStream, forceShowPanics: Boolean): Result<TokenStream, PanicMessage> where S : Server<TokenStream, Span, Symbol> {
    val run = this.run
    return runServer(strategy, server, Pair(MarkedTokenStream.mark(input), MarkedTokenStream.mark(input2)), run, forceShowPanics).map({ s -> MarkedTokenStream.unmark(s).unwrapOrDefault() })
}

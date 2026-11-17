package io.github.kotlinmania.rustlang.procmacro.bridge

import io.github.kotlinmania.rustlang.core.marker.PhantomData
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.procmacro.Delimiter
import io.github.kotlinmania.rustlang.procmacro.Span
import io.github.kotlinmania.rustlang.procmacro.bridge.buffer.Buffer
import io.github.kotlinmania.rustlang.procmacro.bridge.client.TokenStream
import io.github.kotlinmania.rustlang.procmacro.bridge.closure.Closure
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.Decode
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.Encode
import io.github.kotlinmania.rustlang.procmacro.bridge.symbol.Symbol
import io.github.kotlinmania.rustlang.procmacro.diagnostic.Level

public open class Methods {
    public companion object {
        public fun typeLayout(): TypeLayout<Methods> =
            object : TypeLayout<Methods> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Methods =
                    Methods()
                override fun writeNative(addr: Long, value: Methods) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Methods =
                    Methods()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Methods) {
                }
            }
    }
}

/**
 * Configuration for establishing an active connection between a server and a
 * client.  The server creates the bridge config (`runServer` in `server.rs`),
 * then passes it to the client through the function pointer in the `run` field
 * of `client.Client`. The client constructs a local `Bridge` from the config
 * in TLS during its execution (`Bridge.( enter, with )` in `client.rs`).
 */
public open class BridgeConfig(
    /**
     * Buffer used to pass initial input to the client.
     */
    public var input: Buffer,
    /**
     * Server-side function that the client uses to make requests.
     */
    public var dispatch: Closure,
    /**
     * If `true`, always invoke the default panic hook
     */
    public var forceShowPanics: Boolean,
)

public open class Marked<T, M>(
    public var value: T,
    public var marker: Unit,
) : Mark<Marked<T, M>, T> {
    public open fun clone(): Marked<T, M> = Marked(value, marker)

    public override fun unmark(): T = this.value

    public companion object {
        public fun <T, M> mark(unmarked: T): Marked<T, M> = Marked(value = unmarked, marker = PhantomData)
    }
}

public fun <T> markMarkedTTokenStream(arg0: T): Marked<T, TokenStream> {
    error("unreachable")
}

public fun <T> Marked<T, TokenStream>.unmark(): T = this.value

public open class DelimSpan<Span>(
    public var open: Span,
    public var close: Span,
    public var entire: Span,
) : Encode<S>, Decode<S, DelimSpan<Span>>, Mark<DelimSpan<Span>, DelimSpan<Unmarked>> {
    public open fun clone(): DelimSpan<Span> = DelimSpan(open, close, entire)

    public override fun encode(w: Buffer, s: S) {
        this.open.encode(w, s)
        this.close.encode(w, s)
        this.entire.encode(w, s)
    }

    public override fun unmark(): DelimSpan<Unmarked> = DelimSpan(open = this.open.unmark(), close = this.close.unmark(), entire = this.entire.unmark())

    public companion object {
        public fun <Span> typeLayout(typeLayoutSpan: TypeLayout<Span>): TypeLayout<DelimSpan<Span>> =
            object : TypeLayout<DelimSpan<Span>> {
                override val alignBytes: Long = listOf(1L, typeLayoutSpan.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutSpan.sizeBytes, typeLayoutSpan.alignBytes)
                private val off2: Long = alignUpBytes((off1 + typeLayoutSpan.sizeBytes), typeLayoutSpan.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off2 + typeLayoutSpan.sizeBytes), alignBytes)
                override fun readNative(addr: Long): DelimSpan<Span> =
                    DelimSpan(
                        (typeLayoutSpan).readNative(addr),
                        (typeLayoutSpan).readNative(addr + off1),
                        (typeLayoutSpan).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: DelimSpan<Span>) {
                    (typeLayoutSpan).writeNative(addr, value.open)
                    (typeLayoutSpan).writeNative(addr + off1, value.close)
                    (typeLayoutSpan).writeNative(addr + off2, value.entire)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DelimSpan<Span> =
                    DelimSpan(
                        (typeLayoutSpan).readBytes(buf, offset),
                        (typeLayoutSpan).readBytes(buf, offset + off1.toInt()),
                        (typeLayoutSpan).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DelimSpan<Span>) {
                    (typeLayoutSpan).writeBytes(buf, offset, value.open)
                    (typeLayoutSpan).writeBytes(buf, offset + off1.toInt(), value.close)
                    (typeLayoutSpan).writeBytes(buf, offset + off2.toInt(), value.entire)
                }
            }

        public fun <Span> fromSingle(span: Span): DelimSpan<Span> = DelimSpan(open = span, close = span, entire = span)

        public fun <Span, S> decode(r: List<UByte>, s: S): DelimSpan<Span> = DelimSpan(open = Decode.decode(r, s), close = Decode.decode(r, s), entire = Decode.decode(r, s))

        public fun <Unmarked, Span> mark(unmarked: DelimSpan<Unmarked>): DelimSpan<Span> = DelimSpan(open = Mark.Mark(unmarked.open), close = Mark.Mark(unmarked.close), entire = Mark.Mark(unmarked.entire))
    }
}

public open class Group<TokenStream, Span>(
    public var delimiter: Delimiter,
    public var stream: TokenStream?,
    public var span: DelimSpan<Span>,
) : Encode<S>, Decode<S, Group<TokenStream, Span>>, Mark<Group<TokenStream, Span>, Group<Unmarked, Unmarked>> {
    public open fun clone(): Group<TokenStream, Span> = Group(delimiter, stream, span)

    public override fun encode(w: Buffer, s: S) {
        this.delimiter.encode(w, s)
        this.stream.encode(w, s)
        this.span.encode(w, s)
    }

    public override fun unmark(): Group<Unmarked, Unmarked> = Group(delimiter = this.delimiter.unmark(), stream = this.stream.unmark(), span = this.span.unmark())

    public companion object {
        public fun <TokenStream, Span, S> decode(r: List<UByte>, s: S): Group<TokenStream, Span> = Group(delimiter = Decode.decode(r, s), stream = Decode.decode(r, s), span = Decode.decode(r, s))

        public fun <Unmarked, TokenStream, Span> mark(unmarked: Group<Unmarked, Unmarked>): Group<TokenStream, Span> = Group(delimiter = Mark.Mark(unmarked.delimiter), stream = Mark.Mark(unmarked.stream), span = Mark.Mark(unmarked.span))
    }
}

public open class Punct<Span>(
    public var ch: UByte,
    public var joint: Boolean,
    public var span: Span,
) : Encode<S>, Decode<S, Punct<Span>>, Mark<Punct<Span>, Punct<Unmarked>> {
    public open fun clone(): Punct<Span> = Punct(ch, joint, span)

    public override fun encode(w: Buffer, s: S) {
        this.ch.encode(w, s)
        this.joint.encode(w, s)
        this.span.encode(w, s)
    }

    public override fun unmark(): Punct<Unmarked> = Punct(ch = this.ch.unmark(), joint = this.joint.unmark(), span = this.span.unmark())

    public companion object {
        public fun <Span> typeLayout(typeLayoutSpan: TypeLayout<Span>): TypeLayout<Punct<Span>> =
            object : TypeLayout<Punct<Span>> {
                override val alignBytes: Long = listOf(1L, typeLayoutSpan.alignBytes).max()
                private val off2: Long = alignUpBytes(2L, typeLayoutSpan.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off2 + typeLayoutSpan.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Punct<Span> =
                    Punct(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (typeLayoutSpan).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: Punct<Span>) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.ch)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.joint)
                    (typeLayoutSpan).writeNative(addr + off2, value.span)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Punct<Span> =
                    Punct(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                        (typeLayoutSpan).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Punct<Span>) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.ch)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.joint)
                    (typeLayoutSpan).writeBytes(buf, offset + off2.toInt(), value.span)
                }
            }

        public fun <Span, S> decode(r: List<UByte>, s: S): Punct<Span> = Punct(ch = Decode.decode(r, s), joint = Decode.decode(r, s), span = Decode.decode(r, s))

        public fun <Unmarked, Span> mark(unmarked: Punct<Unmarked>): Punct<Span> = Punct(ch = Mark.Mark(unmarked.ch), joint = Mark.Mark(unmarked.joint), span = Mark.Mark(unmarked.span))
    }
}

public open class Ident<Span, Symbol>(
    public var sym: Symbol,
    public var kind: IdentKind,
    public var span: Span,
) : Encode<S>, Decode<S, Ident<Span, Symbol>>, Mark<Ident<Span, Symbol>, Ident<Unmarked, Unmarked>> {
    public open fun clone(): Ident<Span, Symbol> = Ident(sym, kind, span)

    public override fun encode(w: Buffer, s: S) {
        this.sym.encode(w, s)
        this.kind.encode(w, s)
        this.span.encode(w, s)
    }

    public override fun unmark(): Ident<Unmarked, Unmarked> = Ident(sym = this.sym.unmark(), kind = this.kind.unmark(), span = this.span.unmark())

    public companion object {
        public fun <Span, Symbol, S> decode(r: List<UByte>, s: S): Ident<Span, Symbol> = Ident(sym = Decode.decode(r, s), kind = Decode.decode(r, s), span = Decode.decode(r, s))

        public fun <Unmarked, Span, Symbol> mark(unmarked: Ident<Unmarked, Unmarked>): Ident<Span, Symbol> = Ident(sym = Mark.Mark(unmarked.sym), kind = Mark.Mark(unmarked.kind), span = Mark.Mark(unmarked.span))
    }
}

public open class Literal<Span, Symbol>(
    public var kind: LitKind,
    public var symbol: Symbol,
    public var suffix: Symbol?,
    public var span: Span,
) : Encode<S>, Decode<S, Literal<Span, Symbol>>, Mark<Literal<Span, Symbol>, Literal<Unmarked, Unmarked>> {
    public open fun clone(): Literal<Span, Symbol> = Literal(kind, symbol, suffix, span)

    public override fun encode(w: Buffer, s: S) {
        this.kind.encode(w, s)
        this.symbol.encode(w, s)
        this.suffix.encode(w, s)
        this.span.encode(w, s)
    }

    public override fun unmark(): Literal<Unmarked, Unmarked> = Literal(kind = this.kind.unmark(), symbol = this.symbol.unmark(), suffix = this.suffix.unmark(), span = this.span.unmark())

    public companion object {
        public fun <Span, Symbol, S> decode(r: List<UByte>, s: S): Literal<Span, Symbol> = Literal(kind = Decode.decode(r, s), symbol = Decode.decode(r, s), suffix = Decode.decode(r, s), span = Decode.decode(r, s))

        public fun <Unmarked, Span, Symbol> mark(unmarked: Literal<Unmarked, Unmarked>): Literal<Span, Symbol> = Literal(kind = Mark.Mark(unmarked.kind), symbol = Mark.Mark(unmarked.symbol), suffix = Mark.Mark(unmarked.suffix), span = Mark.Mark(unmarked.span))
    }
}

public open class Diagnostic<Span>(
    public var level: Level,
    public var message: String,
    public var spans: List<Span>,
    public var children: List<Diagnostic<Span>>,
) : Encode<S>, Decode<S, Diagnostic<Span>>, Mark<Diagnostic<Span>, Diagnostic<Unmarked>> {
    public open fun clone(): Diagnostic<Span> = Diagnostic(level, message, spans, children)

    public override fun encode(w: Buffer, s: S) {
        this.level.encode(w, s)
        this.message.encode(w, s)
        this.spans.encode(w, s)
        this.children.encode(w, s)
    }

    public override fun unmark(): Diagnostic<Unmarked> = Diagnostic(level = this.level.unmark(), message = this.message.unmark(), spans = this.spans.unmark(), children = this.children.unmark())

    public companion object {
        public fun <Span, S> decode(r: List<UByte>, s: S): Diagnostic<Span> = Diagnostic(level = Decode.decode(r, s), message = Decode.decode(r, s), spans = Decode.decode(r, s), children = Decode.decode(r, s))

        public fun <Unmarked, Span> mark(unmarked: Diagnostic<Unmarked>): Diagnostic<Span> = Diagnostic(level = Mark.Mark(unmarked.level), message = Mark.Mark(unmarked.message), spans = Mark.Mark(unmarked.spans), children = Mark.Mark(unmarked.children))
    }
}

/**
 * Globals provided alongside the initial inputs for a macro expansion.
 * Provides values such as spans which are used frequently to avoid RPC.
 */
public open class ExpnGlobals<Span>(
    public var defSite: Span,
    public var callSite: Span,
    public var mixedSite: Span,
) : Encode<S>, Decode<S, ExpnGlobals<Span>>, Mark<ExpnGlobals<Span>, ExpnGlobals<Unmarked>> {
    public open fun clone(): ExpnGlobals<Span> = ExpnGlobals(defSite, callSite, mixedSite)

    public override fun encode(w: Buffer, s: S) {
        this.defSite.encode(w, s)
        this.callSite.encode(w, s)
        this.mixedSite.encode(w, s)
    }

    public override fun unmark(): ExpnGlobals<Unmarked> = ExpnGlobals(defSite = this.defSite.unmark(), callSite = this.callSite.unmark(), mixedSite = this.mixedSite.unmark())

    public companion object {
        public fun <Span> typeLayout(typeLayoutSpan: TypeLayout<Span>): TypeLayout<ExpnGlobals<Span>> =
            object : TypeLayout<ExpnGlobals<Span>> {
                override val alignBytes: Long = listOf(1L, typeLayoutSpan.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutSpan.sizeBytes, typeLayoutSpan.alignBytes)
                private val off2: Long = alignUpBytes((off1 + typeLayoutSpan.sizeBytes), typeLayoutSpan.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off2 + typeLayoutSpan.sizeBytes), alignBytes)
                override fun readNative(addr: Long): ExpnGlobals<Span> =
                    ExpnGlobals(
                        (typeLayoutSpan).readNative(addr),
                        (typeLayoutSpan).readNative(addr + off1),
                        (typeLayoutSpan).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: ExpnGlobals<Span>) {
                    (typeLayoutSpan).writeNative(addr, value.defSite)
                    (typeLayoutSpan).writeNative(addr + off1, value.callSite)
                    (typeLayoutSpan).writeNative(addr + off2, value.mixedSite)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExpnGlobals<Span> =
                    ExpnGlobals(
                        (typeLayoutSpan).readBytes(buf, offset),
                        (typeLayoutSpan).readBytes(buf, offset + off1.toInt()),
                        (typeLayoutSpan).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExpnGlobals<Span>) {
                    (typeLayoutSpan).writeBytes(buf, offset, value.defSite)
                    (typeLayoutSpan).writeBytes(buf, offset + off1.toInt(), value.callSite)
                    (typeLayoutSpan).writeBytes(buf, offset + off2.toInt(), value.mixedSite)
                }
            }

        public fun <Span, S> decode(r: List<UByte>, s: S): ExpnGlobals<Span> = ExpnGlobals(defSite = Decode.decode(r, s), callSite = Decode.decode(r, s), mixedSite = Decode.decode(r, s))

        public fun <Unmarked, Span> mark(unmarked: ExpnGlobals<Unmarked>): ExpnGlobals<Span> = ExpnGlobals(defSite = Mark.Mark(unmarked.defSite), callSite = Mark.Mark(unmarked.callSite), mixedSite = Mark.Mark(unmarked.mixedSite))
    }
}

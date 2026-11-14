// port-lint: source diagnostic.rs
package io.github.kotlinmania.rustlang.procmacro.diagnostic

import io.github.kotlinmania.rustlang.procmacro.Span
import io.github.kotlinmania.rustlang.procmacro.bridge.Methods

/**
 * An enum representing a diagnostic level.
 */
public sealed class Level {
    /**
     * An error.
     */
    public data object Error : Level()
    /**
     * A warning.
     */
    public data object Warning : Level()
    /**
     * A note.
     */
    public data object Note : Level()
    /**
     * A help message.
     */
    public data object Help : Level()
}

/**
 * Trait implemented by types that can be converted into a set of `Span`s.
 */
public interface MultiSpan {
    /**
     * Converts `self` into a `List<Span>`.
     */
    public fun intoSpans(): List<Span>
}

public fun Span.intoSpans(): List<Span> = mutableListOf(this)

public fun List<Span>.intoSpans(): List<Span> = this

/**
 * A structure representing a diagnostic message and associated children
 * messages.
 */
public open class Diagnostic(
    public var level: Level,
    public var message: String,
    public var spans: List<Span>,
    public var children: MutableList<Diagnostic>,
) {
    public open fun <S> spanError(spans: S, message: String): Diagnostic where S : MultiSpan {
        this.children.add(Diagnostic.spanned(spans, Level.Error, message))
        return this
    }

    public open fun error(message: String): Diagnostic {
        this.children.add(Diagnostic.new(Level.Error, message))
        return this
    }

    public open fun <S> spanWarning(spans: S, message: String): Diagnostic where S : MultiSpan {
        this.children.add(Diagnostic.spanned(spans, Level.Warning, message))
        return this
    }

    public open fun warning(message: String): Diagnostic {
        this.children.add(Diagnostic.new(Level.Warning, message))
        return this
    }

    public open fun <S> spanNote(spans: S, message: String): Diagnostic where S : MultiSpan {
        this.children.add(Diagnostic.spanned(spans, Level.Note, message))
        return this
    }

    public open fun note(message: String): Diagnostic {
        this.children.add(Diagnostic.new(Level.Note, message))
        return this
    }

    public open fun <S> spanHelp(spans: S, message: String): Diagnostic where S : MultiSpan {
        this.children.add(Diagnostic.spanned(spans, Level.Help, message))
        return this
    }

    public open fun help(message: String): Diagnostic {
        this.children.add(Diagnostic.new(Level.Help, message))
        return this
    }

    /**
     * Returns the diagnostic `level` for `self`.
     */
    public open fun level(): Level = this.level

    /**
     * Sets the level in `self` to `level`.
     */
    public open fun setLevel(level: Level) {
        this.level = level
    }

    /**
     * Returns the message in `self`.
     */
    public open fun message(): String = this.message

    /**
     * Sets the message in `self` to `message`.
     */
    public open fun setMessage(message: String) {
        this.message = message
    }

    /**
     * Returns the `Span`s in `self`.
     */
    public open fun spans(): List<Span> = this.spans

    /**
     * Sets the `Span`s in `self` to `spans`.
     */
    public open fun <S : MultiSpan> setSpans(spans: S) {
        this.spans = spans.intoSpans()
    }

    /**
     * Returns an iterator over the children diagnostics of `self`.
     */
    public open fun children(): Children = Children(ArrayDeque(this.children))

    /**
     * Emit the diagnostic.
     */
    public open fun emit() {
        fun toInternal(diag: Diagnostic): io.github.kotlinmania.rustlang.procmacro.bridge.Diagnostic<io.github.kotlinmania.rustlang.procmacro.bridge.client.Span> = io.github.kotlinmania.rustlang.procmacro.bridge.Diagnostic(level = diag.level, message = diag.message, spans = diag.spans.map({ s -> s.first }).toMutableList(), children = diag.children.map(toInternal).toMutableList())
        Methods.emitDiagnostic(toInternal(this))
    }

    public companion object {
        /**
         * Creates a new diagnostic with the given `level` and `message`.
         */
        public fun new(level: Level, message: String): Diagnostic = Diagnostic(level = level, message = message, spans = mutableListOf(), children = mutableListOf())

        /**
         * Creates a new diagnostic with the given `level` and `message` pointing to
         * the given set of `spans`.
         */
        public fun <S> spanned(spans: S, level: Level, message: String): Diagnostic where S : MultiSpan = Diagnostic(level = level, message = message, spans = spans.intoSpans(), children = mutableListOf())
    }
}

/**
 * Iterator over the children diagnostics of a `Diagnostic`.
 */
public open class Children(
    public var value: ArrayDeque<Diagnostic>,
) : Iterable<Diagnostic> {
    public var field0: ArrayDeque<Diagnostic>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): kotlin.collections.Iterator<Diagnostic> = object : AbstractIterator<Diagnostic>() {
        override fun computeNext() {
            when (val v = this@Children.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Diagnostic? = this.value.next()
}

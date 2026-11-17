// port-lint: source quote.rs
package io.github.kotlinmania.rustlang.procmacro.quote

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.procmacro.BitOr
import io.github.kotlinmania.rustlang.procmacro.Delimiter
import io.github.kotlinmania.rustlang.procmacro.Group
import io.github.kotlinmania.rustlang.procmacro.Ident
import io.github.kotlinmania.rustlang.procmacro.Literal
import io.github.kotlinmania.rustlang.procmacro.Punct
import io.github.kotlinmania.rustlang.procmacro.Spacing
import io.github.kotlinmania.rustlang.procmacro.Span
import io.github.kotlinmania.rustlang.procmacro.TokenStream
import io.github.kotlinmania.rustlang.procmacro.TokenTree
import io.github.kotlinmania.rustlang.procmacro.totokens.ToTokens
import io.github.kotlinmania.rustlang.procmacro.totokens.toTokens
import io.github.kotlinmania.rustlang.std.path.Iter
import io.github.kotlinmania.rustlang.procmacro.quote.HasIterator as HasIter
import io.github.kotlinmania.rustlang.procmacro.quote.ThereIsNoIteratorInRepetition as DoesNotHaveIter

// # Quasiquoter
// This file contains the implementation internals of the quasiquoter provided by `quote`.
// This quasiquoter uses macros 2.0 hygiene to reliably access
// items from `procMacro`, to build a `procMacro.TokenStream`.
//
// Simpler version of the real `quote` macro, implemented solely
// through `macroRules`, for bootstrapping the real implementation
// (see the `quote` function), which does not have access to the
// real `quote` macro due to the `procMacro` crate not being
// able to depend on itself.
//
// Note: supported tokens are a subset of the real `quote`, but
// unquoting is different: instead of `$ x`, this uses `(@expr)`.

public open class HasIterator : BitOr<ThereIsNoIteratorInRepetition, HasIterator> {
    public open fun bitor(rhs: ThereIsNoIteratorInRepetition): HasIterator = HasIterator()

    public open fun bitor(rhs: HasIterator): HasIterator = HasIterator()

    public companion object {
        public fun typeLayout(): TypeLayout<HasIterator> =
            object : TypeLayout<HasIterator> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): HasIterator =
                    HasIterator()
                override fun writeNative(addr: Long, value: HasIterator) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): HasIterator =
                    HasIterator()
                override fun writeBytes(buf: ByteArray, offset: Int, value: HasIterator) {
                }
            }
    }
}

// True
public open class ThereIsNoIteratorInRepetition : BitOr<ThereIsNoIteratorInRepetition, ThereIsNoIteratorInRepetition> {
    // False
    public open fun bitor(rhs: ThereIsNoIteratorInRepetition): ThereIsNoIteratorInRepetition = ThereIsNoIteratorInRepetition()

    public open fun bitor(rhs: HasIterator): HasIterator = HasIterator()

    public companion object {
        public fun typeLayout(): TypeLayout<ThereIsNoIteratorInRepetition> =
            object : TypeLayout<ThereIsNoIteratorInRepetition> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): ThereIsNoIteratorInRepetition =
                    ThereIsNoIteratorInRepetition()
                override fun writeNative(addr: Long, value: ThereIsNoIteratorInRepetition) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThereIsNoIteratorInRepetition =
                    ThereIsNoIteratorInRepetition()
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThereIsNoIteratorInRepetition) {
                }
            }
    }
}

/**
 * Extension trait providing the `quoteIntoIter` method on iterators.
 */
public interface RepIteratorExt<T> {
    public fun quoteIntoIter(): Pair<T, HasIter> = Pair(this, HasIter)
}

/**
 * Extension trait providing the `quoteIntoIter` method for
 * non-iterable types. These types interpolate the same value in each
 * iteration of the repetition.
 */
public interface RepToTokensExt<T> {
    /**
     * Pretend to be an iterator for the purposes of `quoteIntoIter`.
     * This allows repeated calls to `quoteIntoIter` to continue
     * correctly returning DoesNotHaveIter.
     */
    public fun next(): T? = this

    public fun quoteIntoIter(): Pair<T, DoesNotHaveIter> = Pair(this, DoesNotHaveIter)
}

/**
 * Extension trait providing the `quoteIntoIter` method for types that
 * can be referenced as an iterator.
 */
public interface RepAsIteratorExt<out Iter> {
    public fun quoteIntoIter(): Pair<Iter, HasIter>
}

public fun <T> List<T>.quoteIntoIter(): Pair<ArrayDeque<T>, HasIter> = Pair(this.iter(), HasIter)

public fun <T> List<T>.quoteIntoIterFromTArrayn(n: Int): Pair<ArrayDeque<T>, HasIter> = Pair(this.iter(), HasIter)

public fun <T> MutableSet<T>.quoteIntoIter(): Pair<Iter<T>, HasIter> = Pair(this.iter(), HasIter)

public fun <Iter, T : RepAsIteratorExt<Iter>> RepInterp<T>.quoteIntoIter(): Pair<Iter, HasIter> = this.value.quoteIntoIter()

// Helper type used within interpolations to allow for repeated binding names.
// Implements the relevant traits, and exports a dummy `next()` method.
public open class RepInterp<T>(
    public var value: T,
) : Iterable<Item> {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@RepInterp.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): RepInterp<T> = RepInterp(value)

    // This method is intended to look like `Iterator.next`, and is called when
    // a name is bound multiple times, as the previous binding will shadow the
    // original `Iterator` object. This allows us to avoid advancing the
    // iterator multiple times per iteration.
    public open fun next(): T? = this.value

    public open fun nextIterator(): Item? = this.value.next()

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<RepInterp<T>> =
            object : TypeLayout<RepInterp<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): RepInterp<T> =
                    RepInterp(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: RepInterp<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RepInterp<T> =
                    RepInterp(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RepInterp<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public fun <T : ToTokens> RepInterp<T>.toTokens(tokens: TokenStream) {
    this.value.toTokens(tokens)
}

/**
 * Quote a `TokenStream` into a `TokenStream`.
 * This is the actual implementation of the `quote()` proc macro.
 *
 * It is loaded by the compiler in `registerBuiltinMacros`.
 */
public fun quote(stream: TokenStream): TokenStream {
    if (stream.isEmpty()) {
        return run {
            val ts = TokenStream.new()
            ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts)
            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts)
            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts)
            ToTokens.ToTokens(TokenTree.from(Ident.new("TokenStream", Span.defSite())), ts)
            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts)
            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts)
            ToTokens.ToTokens(TokenTree.from(Ident.new("new", Span.defSite())), ts)
            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts)
            ts
        }
    }
    val procMacroCrate = run {
        val ts1 = TokenStream.new()
        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts1)
        ts1
    }
    var afterDollar = false
    val tokens = TokenStream.new()
    val iter = ArrayDeque((stream).toList()).peekable()
    while (true) {
        val matchVal = iter.removeFirstOrNull()
        if (matchVal == null) break
        val tree = matchVal
        if (afterDollar) {
            afterDollar = false
            when {
                tree is TokenTree.Group<*, *, *> -> {
                    val (tt) = tree
                    // Handles repetition by expanding `$(CONTENTS) SEP_OPT *` to `( REP_EXPANDED )`.
                    val contents = tt.stream()
                    // The `*` token is also consumed here.
                    val sepOpt: Punct? =
                        run {
                            val matchVal1 = Pair(iter.removeFirstOrNull(), iter.peek())
                            when {
                                matchVal1.first != null && matchVal1.first is TokenTree.Punct<*, *, *> && matchVal1.second != null && matchVal1.second is TokenTree.Punct<*, *, *> && (matchVal1.first as TokenTree.Punct<*, *, *>).value.spacing() == Spacing.Joint && (matchVal1.second as TokenTree.Punct<*, *, *>).value.asChar() == '*' -> {
                                    val (sep) = matchVal1.first as TokenTree.Punct<*, *, *>
                                    val (star) = matchVal1.second as TokenTree.Punct<*, *, *>
                                    iter.removeFirstOrNull()
                                    sep
                                }
                                matchVal1.first != null && matchVal1.first is TokenTree.Punct<*, *, *> && (matchVal1.first as TokenTree.Punct<*, *, *>).value.asChar() == '*' -> {
                                    val (star1) = matchVal1.first as TokenTree.Punct<*, *, *>
                                    null
                                }
                                else -> error("`\$(...)` must be followed by `*` in `quote!`")
                            }
                        }
                    var repExpanded = TokenStream.new()
                    // Append setup code for a `while`, where recursively quoted `CONTENTS`
                    // and `SEP_OPT` are repeatedly processed, to `REP_EXPANDED`.
                    val metaVars = collectMetaVars(contents.clone())
                    run {
                        val ts2 = TokenStream.new()
                        ToTokens.ToTokens(TokenTree.from(Ident.new("use", Span.defSite())), ts2)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts2)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("ext", Span.defSite())), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new('*', Spacing.Alone)), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts2)
                        ToTokens.ToTokens(if (sepOpt != null) {
                            run {
                                val ts3 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Ident.new("let", Span.defSite())), ts3)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts3)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("_i", Span.defSite())), ts3)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts3)
                                ToTokens.ToTokens(TokenTree.from("0usize".parse<Literal>().unwrap()), ts3)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts3)
                                ts3
                            }
                        } else {
                            run {
                                val ts4 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts4)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts4)
                                ts4
                            }
                        }, ts2)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("let", Span.defSite())), ts2)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("has_iter", Span.defSite())), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts2)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts2)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("ThereIsNoIteratorInRepetition", Span.defSite())), ts2)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts2)
                        ts2
                    }.toTokens(repExpanded)
                    for (metaVar in metaVars) {
                        run {
                            val ts5 = TokenStream.new()
                            ToTokens.ToTokens(TokenTree.from(Punct.new('#', Spacing.Alone)), ts5)
                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Bracket, run {
                                val ts6 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Ident.new("allow", Span.defSite())), ts6)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts7 = TokenStream.new()
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("unused_mut", Span.defSite())), ts7)
                                    ts7
                                })), ts6)
                                ts6
                            })), ts5)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("let", Span.defSite())), ts5)
                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                val ts8 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts8)
                                ToTokens.ToTokens(metaVar, ts8)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts8)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("i", Span.defSite())), ts8)
                                ts8
                            })), ts5)
                            ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts5)
                            ToTokens.ToTokens(metaVar, ts5)
                            ToTokens.ToTokens(TokenTree.from(Punct.new('.', Spacing.Alone)), ts5)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("quote_into_iter", Span.defSite())), ts5)
                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts5)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts5)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("let", Span.defSite())), ts5)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("has_iter", Span.defSite())), ts5)
                            ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts5)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("has_iter", Span.defSite())), ts5)
                            ToTokens.ToTokens(TokenTree.from(Punct.new('|', Spacing.Alone)), ts5)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("i", Span.defSite())), ts5)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts5)
                            ts5
                        }.toTokens(repExpanded)
                    }
                    run {
                        val ts9 = TokenStream.new()
                        ToTokens.ToTokens(TokenTree.from(Ident.new("let", Span.defSite())), ts9)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("_", Span.defSite())), ts9)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts9)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts9)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts9)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts9)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("HasIterator", Span.defSite())), ts9)
                        ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts9)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("has_iter", Span.defSite())), ts9)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts9)
                        ts9
                    }.toTokens(repExpanded)
                    // Append the `while` to `REP_EXPANDED`.
                    var whileBody = TokenStream.new()
                    for (metaVar1 in metaVars) {
                        run {
                            val ts10 = TokenStream.new()
                            ToTokens.ToTokens(TokenTree.from(Ident.new("let", Span.defSite())), ts10)
                            ToTokens.ToTokens(metaVar1, ts10)
                            ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts10)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("match", Span.defSite())), ts10)
                            ToTokens.ToTokens(metaVar1, ts10)
                            ToTokens.ToTokens(TokenTree.from(Punct.new('.', Spacing.Alone)), ts10)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("next", Span.defSite())), ts10)
                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts10)
                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Brace, run {
                                val ts11 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Ident.new("Some", Span.defSite())), ts11)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts12 = TokenStream.new()
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("_x", Span.defSite())), ts12)
                                    ts12
                                })), ts11)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts11)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('>', Spacing.Alone)), ts11)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts11)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts11)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts11)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("RepInterp", Span.defSite())), ts11)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts13 = TokenStream.new()
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("_x", Span.defSite())), ts13)
                                    ts13
                                })), ts11)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts11)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("None", Span.defSite())), ts11)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts11)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('>', Spacing.Alone)), ts11)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("break", Span.defSite())), ts11)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts11)
                                ts11
                            })), ts10)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts10)
                            ts10
                        }.toTokens(whileBody)
                    }
                    run {
                        val ts14 = TokenStream.new()
                        ToTokens.ToTokens(if (sepOpt != null) {
                            val sep1 = sepOpt
                            run {
                                val ts15 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Ident.new("if", Span.defSite())), ts15)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("_i", Span.defSite())), ts15)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('>', Spacing.Alone)), ts15)
                                ToTokens.ToTokens(TokenTree.from("0".parse<Literal>().unwrap()), ts15)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Brace, run {
                                    val ts16 = TokenStream.new()
                                    ToTokens.ToTokens(run {
                                        val ts17 = TokenStream.new()
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts17)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts17)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts17)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("ToTokens", Span.defSite())), ts17)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts17)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts17)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("to_tokens", Span.defSite())), ts17)
                                        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                            val ts18 = TokenStream.new()
                                            ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Ident.new("TokenTree", Span.defSite())), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Ident.new("Punct", Span.defSite())), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                                val ts19 = TokenStream.new()
                                                ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts19)
                                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts19)
                                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts19)
                                                ToTokens.ToTokens(TokenTree.from(Ident.new("Punct", Span.defSite())), ts19)
                                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts19)
                                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts19)
                                                ToTokens.ToTokens(TokenTree.from(Ident.new("new", Span.defSite())), ts19)
                                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                                    val ts20 = TokenStream.new()
                                                    ToTokens.ToTokens(TokenTree.from(Literal.character(sep1.asChar())), ts20)
                                                    ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts20)
                                                    ToTokens.ToTokens(run {
                                                        val ts21 = TokenStream.new()
                                                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts21)
                                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts21)
                                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts21)
                                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Spacing", Span.defSite())), ts21)
                                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts21)
                                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts21)
                                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Alone", Span.defSite())), ts21)
                                                        ts21
                                                    }, ts20)
                                                    ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts20)
                                                    ts20
                                                })), ts19)
                                                ts19
                                            })), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts18)
                                            ToTokens.ToTokens(TokenTree.from(Ident.new("ts", Span.defSite())), ts18)
                                            ts18
                                        })), ts17)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts17)
                                        ts17
                                    }, ts16)
                                    ts16
                                })), ts15)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("_i", Span.defSite())), ts15)
                                ToTokens.ToTokens(TokenTree.from(minimalQuoteTt()), ts15)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts15)
                                ToTokens.ToTokens(TokenTree.from("1".parse<Literal>().unwrap()), ts15)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts15)
                                ts15
                            }
                        } else {
                            run {
                                val ts22 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts22)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts22)
                                ts22
                            }
                        }, ts14)
                        ToTokens.ToTokens(quote(contents.clone()), ts14)
                        ToTokens.ToTokens(TokenTree.from(Punct.new('.', Spacing.Alone)), ts14)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("to_tokens", Span.defSite())), ts14)
                        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts23 = TokenStream.new()
                            ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts23)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts23)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("ts", Span.defSite())), ts23)
                            ts23
                        })), ts14)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts14)
                        ts14
                    }.toTokens(whileBody)
                    repExpanded.extend(mutableListOf(TokenTree.Ident(Ident.new("while", Span.callSite())), TokenTree.Ident(Ident.new("true", Span.callSite())), TokenTree.Group(Group.new(Delimiter.Brace, whileBody))))
                    run {
                        val ts24 = TokenStream.new()
                        ToTokens.ToTokens(TokenTree.Group(Group.new(Delimiter.Brace, repExpanded)), ts24)
                        ts24
                    }.toTokens(tokens)
                    continue
                }
                tree is TokenTree.Ident<*, *, *> -> {
                    run {
                        val ts25 = TokenStream.new()
                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts25)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts25)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts25)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("ToTokens", Span.defSite())), ts25)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts25)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts25)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("to_tokens", Span.defSite())), ts25)
                        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts26 = TokenStream.new()
                            ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts26)
                            ToTokens.ToTokens(tree, ts26)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts26)
                            ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts26)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts26)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("ts", Span.defSite())), ts26)
                            ts26
                        })), ts25)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts25)
                        ts25
                    }.toTokens(tokens)
                    continue
                }
                tree is TokenTree.Punct<*, *, *> && tree.value.asChar() == '$' -> {
                    val (tt1) = tree
                }
                else -> error("`\$` must be followed by an ident or `\$` or a repetition group in `quote!`")
            }
        } else if (tree is TokenTree.Punct<*, *, *>) {
            val (tt2) = tree
            if (tt2.asChar() == '$') {
                afterDollar = true
                continue
            }
        }
        when (tree) {
            is TokenTree.Punct<*, *, *> -> {
                val (tt3) = tree
                run {
                    val ts27 = TokenStream.new()
                    ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts27)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts27)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts27)
                    ToTokens.ToTokens(TokenTree.from(Ident.new("ToTokens", Span.defSite())), ts27)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts27)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts27)
                    ToTokens.ToTokens(TokenTree.from(Ident.new("to_tokens", Span.defSite())), ts27)
                    ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                        val ts28 = TokenStream.new()
                        ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts28)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts28)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts28)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts28)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("TokenTree", Span.defSite())), ts28)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts28)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts28)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("Punct", Span.defSite())), ts28)
                        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts29 = TokenStream.new()
                            ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts29)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts29)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts29)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("Punct", Span.defSite())), ts29)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts29)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts29)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("new", Span.defSite())), ts29)
                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                val ts30 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Literal.character(tt3.asChar())), ts30)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts30)
                                ToTokens.ToTokens(when (tt3.spacing()) {
                                    Spacing.Alone -> {
                                        val ts31 = TokenStream.new()
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts31)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts31)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts31)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Spacing", Span.defSite())), ts31)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts31)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts31)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Alone", Span.defSite())), ts31)
                                        ts31
                                    }
                                    Spacing.Joint -> {
                                        val ts32 = TokenStream.new()
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts32)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts32)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts32)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Spacing", Span.defSite())), ts32)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts32)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts32)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Joint", Span.defSite())), ts32)
                                        ts32
                                    }
                                }, ts30)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts30)
                                ts30
                            })), ts29)
                            ts29
                        })), ts28)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts28)
                        ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts28)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts28)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("ts", Span.defSite())), ts28)
                        ts28
                    })), ts27)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts27)
                    ts27
                }
            }
            is TokenTree.Group<*, *, *> -> {
                val (tt4) = tree
                run {
                    val ts33 = TokenStream.new()
                    ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts33)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts33)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts33)
                    ToTokens.ToTokens(TokenTree.from(Ident.new("ToTokens", Span.defSite())), ts33)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts33)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts33)
                    ToTokens.ToTokens(TokenTree.from(Ident.new("to_tokens", Span.defSite())), ts33)
                    ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                        val ts34 = TokenStream.new()
                        ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts34)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts34)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts34)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts34)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("TokenTree", Span.defSite())), ts34)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts34)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts34)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("Group", Span.defSite())), ts34)
                        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts35 = TokenStream.new()
                            ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts35)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts35)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts35)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("Group", Span.defSite())), ts35)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts35)
                            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts35)
                            ToTokens.ToTokens(TokenTree.from(Ident.new("new", Span.defSite())), ts35)
                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                val ts36 = TokenStream.new()
                                ToTokens.ToTokens(when (tt4.delimiter()) {
                                    Delimiter.Parenthesis -> {
                                        val ts37 = TokenStream.new()
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts37)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts37)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts37)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Delimiter", Span.defSite())), ts37)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts37)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts37)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Parenthesis", Span.defSite())), ts37)
                                        ts37
                                    }
                                    Delimiter.Brace -> {
                                        val ts38 = TokenStream.new()
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts38)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts38)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts38)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Delimiter", Span.defSite())), ts38)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts38)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts38)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Brace", Span.defSite())), ts38)
                                        ts38
                                    }
                                    Delimiter.Bracket -> {
                                        val ts39 = TokenStream.new()
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts39)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts39)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts39)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Delimiter", Span.defSite())), ts39)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts39)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts39)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Bracket", Span.defSite())), ts39)
                                        ts39
                                    }
                                    Delimiter.None -> {
                                        val ts40 = TokenStream.new()
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts40)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts40)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts40)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Delimiter", Span.defSite())), ts40)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts40)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts40)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("None", Span.defSite())), ts40)
                                        ts40
                                    }
                                }, ts36)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts36)
                                ToTokens.ToTokens(quote(tt4.stream()), ts36)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts36)
                                ts36
                            })), ts35)
                            ts35
                        })), ts34)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts34)
                        ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts34)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts34)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("ts", Span.defSite())), ts34)
                        ts34
                    })), ts33)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts33)
                    ts33
                }
            }
            is TokenTree.Ident<*, *, *> -> {
                val (tt5) = tree
                val literal = tt5.toString()
                val (literal1, ctor) =
                    when (val matchVal2 = literal.takeIf { it.startsWith("r#") }?.removePrefix("r#")) {
                        null -> {
                            Pair(literal.asStr(), run {
                                val ts41 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts41)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts41)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts41)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("Ident", Span.defSite())), ts41)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts41)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts41)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("new", Span.defSite())), ts41)
                                ts41
                            })
                        }
                        else -> {
                            val stripped = matchVal2
                            Pair(stripped, run {
                                val ts42 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts42)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts42)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts42)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("Ident", Span.defSite())), ts42)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts42)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts42)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("new_raw", Span.defSite())), ts42)
                                ts42
                            })
                        }
                    }
                run {
                    val ts43 = TokenStream.new()
                    ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts43)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts43)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts43)
                    ToTokens.ToTokens(TokenTree.from(Ident.new("ToTokens", Span.defSite())), ts43)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts43)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts43)
                    ToTokens.ToTokens(TokenTree.from(Ident.new("to_tokens", Span.defSite())), ts43)
                    ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                        val ts44 = TokenStream.new()
                        ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts44)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts44)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts44)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts44)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("TokenTree", Span.defSite())), ts44)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts44)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts44)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("Ident", Span.defSite())), ts44)
                        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts45 = TokenStream.new()
                            ToTokens.ToTokens(ctor, ts45)
                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                val ts46 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Literal.string(literal1)), ts46)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts46)
                                ToTokens.ToTokens(quoteSpan(procMacroCrate.clone(), tt5.span()), ts46)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts46)
                                ts46
                            })), ts45)
                            ts45
                        })), ts44)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts44)
                        ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts44)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts44)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("ts", Span.defSite())), ts44)
                        ts44
                    })), ts43)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts43)
                    ts43
                }
            }
            is TokenTree.Literal<*, *, *> -> {
                val (tt6) = tree
                run {
                    val ts47 = TokenStream.new()
                    ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts47)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts47)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts47)
                    ToTokens.ToTokens(TokenTree.from(Ident.new("ToTokens", Span.defSite())), ts47)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts47)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts47)
                    ToTokens.ToTokens(TokenTree.from(Ident.new("to_tokens", Span.defSite())), ts47)
                    ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                        val ts48 = TokenStream.new()
                        ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts48)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts48)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts48)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts48)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("TokenTree", Span.defSite())), ts48)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts48)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts48)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("Literal", Span.defSite())), ts48)
                        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts49 = TokenStream.new()
                            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Brace, run {
                                val ts50 = TokenStream.new()
                                ToTokens.ToTokens(TokenTree.from(Ident.new("let", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("iter", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Literal.string(tt6.toString())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('.', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("parse", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('<', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("TokenStream", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('>', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('.', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("unwrap", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('.', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("into_iter", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("if", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("let", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts51 = TokenStream.new()
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("Some", Span.defSite())), ts51)
                                    ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                        val ts52 = TokenStream.new()
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts52)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts52)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts52)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("TokenTree", Span.defSite())), ts52)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts52)
                                        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts52)
                                        ToTokens.ToTokens(TokenTree.from(Ident.new("Literal", Span.defSite())), ts52)
                                        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                            val ts53 = TokenStream.new()
                                            ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts53)
                                            ToTokens.ToTokens(TokenTree.from(Ident.new("lit", Span.defSite())), ts53)
                                            ts53
                                        })), ts52)
                                        ts52
                                    })), ts51)
                                    ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts51)
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("None", Span.defSite())), ts51)
                                    ts51
                                })), ts50)
                                ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts50)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts54 = TokenStream.new()
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("iter", Span.defSite())), ts54)
                                    ToTokens.ToTokens(TokenTree.from(Punct.new('.', Spacing.Alone)), ts54)
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("next", Span.defSite())), ts54)
                                    ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts54)
                                    ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts54)
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("iter", Span.defSite())), ts54)
                                    ToTokens.ToTokens(TokenTree.from(Punct.new('.', Spacing.Alone)), ts54)
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("next", Span.defSite())), ts54)
                                    ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts54)
                                    ts54
                                })), ts50)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Brace, run {
                                    val ts55 = TokenStream.new()
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("lit", Span.defSite())), ts55)
                                    ToTokens.ToTokens(TokenTree.from(Punct.new('.', Spacing.Alone)), ts55)
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("set_span", Span.defSite())), ts55)
                                    ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                        val ts56 = TokenStream.new()
                                        ToTokens.ToTokens(quoteSpan(procMacroCrate.clone(), tt6.span()), ts56)
                                        ts56
                                    })), ts55)
                                    ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts55)
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("lit", Span.defSite())), ts55)
                                    ts55
                                })), ts50)
                                ToTokens.ToTokens(TokenTree.from(Ident.new("else", Span.defSite())), ts50)
                                ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Brace, run {
                                    val ts57 = TokenStream.new()
                                    ToTokens.ToTokens(TokenTree.from(Ident.new("unreachable", Span.defSite())), ts57)
                                    ToTokens.ToTokens(TokenTree.from(Punct.new('!', Spacing.Alone)), ts57)
                                    ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts57)
                                    ts57
                                })), ts50)
                                ts50
                            })), ts49)
                            ts49
                        })), ts48)
                        ToTokens.ToTokens(TokenTree.from(Punct.new(',', Spacing.Alone)), ts48)
                        ToTokens.ToTokens(TokenTree.from(Punct.new('&', Spacing.Alone)), ts48)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts48)
                        ToTokens.ToTokens(TokenTree.from(Ident.new("ts", Span.defSite())), ts48)
                        ts48
                    })), ts47)
                    ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts47)
                    ts47
                }
            }
        }.toTokens(tokens)
    }
    if (afterDollar) {
        error("unexpected trailing `\$` in `quote!`")
    }
    return run {
        val ts58 = TokenStream.new()
        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Brace, run {
            val ts59 = TokenStream.new()
            ToTokens.ToTokens(TokenTree.from(Ident.new("let", Span.defSite())), ts59)
            ToTokens.ToTokens(TokenTree.from(Ident.new("mut", Span.defSite())), ts59)
            ToTokens.ToTokens(TokenTree.from(Ident.new("ts", Span.defSite())), ts59)
            ToTokens.ToTokens(TokenTree.from(Punct.new('=', Spacing.Alone)), ts59)
            ToTokens.ToTokens(TokenTree.from(Ident.new("crate", Span.defSite())), ts59)
            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts59)
            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts59)
            ToTokens.ToTokens(TokenTree.from(Ident.new("TokenStream", Span.defSite())), ts59)
            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts59)
            ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts59)
            ToTokens.ToTokens(TokenTree.from(Ident.new("new", Span.defSite())), ts59)
            ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())), ts59)
            ToTokens.ToTokens(TokenTree.from(Punct.new(';', Spacing.Alone)), ts59)
            ToTokens.ToTokens(tokens, ts59)
            ToTokens.ToTokens(TokenTree.from(Ident.new("ts", Span.defSite())), ts59)
            ts59
        })), ts58)
        ts58
    }
}

/**
 * Helper function to support macro repetitions like `$(CONTENTS) SEP_OPT *` in `quote`.
 * Recursively collects all `Ident`s (meta-variables) that follow a `$`
 * from the given `CONTENTS` stream, preserving their order of appearance.
 */
public fun collectMetaVars(contentStream: TokenStream): List<Ident> {
    fun helper(stream: TokenStream, out: List<Ident>) {
        val iter = ArrayDeque((stream).toList()).peekable()
        while (true) {
            val matchVal = iter.removeFirstOrNull()
            if (matchVal == null) break
            val tree = matchVal
            when {
                tree is TokenTree.Punct<*, *, *> && tree.value.asChar() == '$' -> {
                    val (tt) = tree
                    when (val matchVal1 = iter.peek()) {
                        is Some -> {
                            val (id) = matchVal1 as TokenTree.Ident<*, *, *>
                            out.push(id.clone())
                            iter.removeFirstOrNull()
                        }
                        else -> {}
                    }
                }
                tree is TokenTree.Group<*, *, *> -> {
                    val (tt1) = tree
                    helper(tt1.stream(), out)
                }
                else -> {}
            }
        }
    }
    val vars = mutableListOf<Ident>()
    helper(contentStream, vars)
    return vars
}

/**
 * Quote a `Span` into a `TokenStream`.
 * This is needed to implement a custom quoter.
 */
public fun quoteSpan(procMacroCrate: TokenStream, span: Span): TokenStream {
    val id = span.saveSpan()
    return run {
        val ts = TokenStream.new()
        ToTokens.ToTokens(procMacroCrate, ts)
        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts)
        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts)
        ToTokens.ToTokens(TokenTree.from(Ident.new("Span", Span.defSite())), ts)
        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts)
        ToTokens.ToTokens(TokenTree.from(Punct.new(':', Spacing.Alone)), ts)
        ToTokens.ToTokens(TokenTree.from(Ident.new("recover_proc_macro_span", Span.defSite())), ts)
        ToTokens.ToTokens(TokenTree.from(Group.new(Delimiter.Parenthesis, run {
            val ts1 = TokenStream.new()
            ToTokens.ToTokens(TokenTree.from(Literal.usizeUnsuffixed(id)), ts1)
            ts1
        })), ts)
        ts
    }
}

// port-lint: source quote.rs
package io.github.kotlinmania.rustlang.procmacro.quote

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
            TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts)
            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts)
            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts)
            TokenTree.from(Ident.new("TokenStream", Span.defSite())).toTokens(ts)
            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts)
            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts)
            TokenTree.from(Ident.new("new", Span.defSite())).toTokens(ts)
            TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts)
            ts
        }
    }
    val procMacroCrate = run {
        val ts1 = TokenStream.new()
        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts1)
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
                        TokenTree.from(Ident.new("use", Span.defSite())).toTokens(ts2)
                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts2)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts2)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts2)
                        TokenTree.from(Ident.new("ext", Span.defSite())).toTokens(ts2)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts2)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts2)
                        TokenTree.from(Punct.new('*', Spacing.Alone)).toTokens(ts2)
                        TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts2)
                        (if (sepOpt != null) {
                            run {
                                val ts3 = TokenStream.new()
                                TokenTree.from(Ident.new("let", Span.defSite())).toTokens(ts3)
                                TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts3)
                                TokenTree.from(Ident.new("_i", Span.defSite())).toTokens(ts3)
                                TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts3)
                                TokenTree.from("0usize".parse<Literal>().unwrap()).toTokens(ts3)
                                TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts3)
                                ts3
                            }
                        } else {
                            run {
                                val ts4 = TokenStream.new()
                                TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts4)
                                TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts4)
                                ts4
                            }
                        }).toTokens(ts2)
                        TokenTree.from(Ident.new("let", Span.defSite())).toTokens(ts2)
                        TokenTree.from(Ident.new("has_iter", Span.defSite())).toTokens(ts2)
                        TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts2)
                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts2)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts2)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts2)
                        TokenTree.from(Ident.new("ThereIsNoIteratorInRepetition", Span.defSite())).toTokens(ts2)
                        TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts2)
                        ts2
                    }.toTokens(repExpanded)
                    for (metaVar in metaVars) {
                        run {
                            val ts5 = TokenStream.new()
                            TokenTree.from(Punct.new('#', Spacing.Alone)).toTokens(ts5)
                            TokenTree.from(Group.new(Delimiter.Bracket, run {
                                val ts6 = TokenStream.new()
                                TokenTree.from(Ident.new("allow", Span.defSite())).toTokens(ts6)
                                TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts7 = TokenStream.new()
                                    TokenTree.from(Ident.new("unused_mut", Span.defSite())).toTokens(ts7)
                                    ts7
                                })).toTokens(ts6)
                                ts6
                            })).toTokens(ts5)
                            TokenTree.from(Ident.new("let", Span.defSite())).toTokens(ts5)
                            TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                val ts8 = TokenStream.new()
                                TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts8)
                                metaVar.toTokens(ts8)
                                TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts8)
                                TokenTree.from(Ident.new("i", Span.defSite())).toTokens(ts8)
                                ts8
                            })).toTokens(ts5)
                            TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts5)
                            metaVar.toTokens(ts5)
                            TokenTree.from(Punct.new('.', Spacing.Alone)).toTokens(ts5)
                            TokenTree.from(Ident.new("quote_into_iter", Span.defSite())).toTokens(ts5)
                            TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts5)
                            TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts5)
                            TokenTree.from(Ident.new("let", Span.defSite())).toTokens(ts5)
                            TokenTree.from(Ident.new("has_iter", Span.defSite())).toTokens(ts5)
                            TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts5)
                            TokenTree.from(Ident.new("has_iter", Span.defSite())).toTokens(ts5)
                            TokenTree.from(Punct.new('|', Spacing.Alone)).toTokens(ts5)
                            TokenTree.from(Ident.new("i", Span.defSite())).toTokens(ts5)
                            TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts5)
                            ts5
                        }.toTokens(repExpanded)
                    }
                    run {
                        val ts9 = TokenStream.new()
                        TokenTree.from(Ident.new("let", Span.defSite())).toTokens(ts9)
                        TokenTree.from(Ident.new("_", Span.defSite())).toTokens(ts9)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts9)
                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts9)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts9)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts9)
                        TokenTree.from(Ident.new("HasIterator", Span.defSite())).toTokens(ts9)
                        TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts9)
                        TokenTree.from(Ident.new("has_iter", Span.defSite())).toTokens(ts9)
                        TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts9)
                        ts9
                    }.toTokens(repExpanded)
                    // Append the `while` to `REP_EXPANDED`.
                    var whileBody = TokenStream.new()
                    for (metaVar1 in metaVars) {
                        run {
                            val ts10 = TokenStream.new()
                            TokenTree.from(Ident.new("let", Span.defSite())).toTokens(ts10)
                            metaVar1.toTokens(ts10)
                            TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts10)
                            TokenTree.from(Ident.new("match", Span.defSite())).toTokens(ts10)
                            metaVar1.toTokens(ts10)
                            TokenTree.from(Punct.new('.', Spacing.Alone)).toTokens(ts10)
                            TokenTree.from(Ident.new("next", Span.defSite())).toTokens(ts10)
                            TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts10)
                            TokenTree.from(Group.new(Delimiter.Brace, run {
                                val ts11 = TokenStream.new()
                                TokenTree.from(Ident.new("Some", Span.defSite())).toTokens(ts11)
                                TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts12 = TokenStream.new()
                                    TokenTree.from(Ident.new("_x", Span.defSite())).toTokens(ts12)
                                    ts12
                                })).toTokens(ts11)
                                TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts11)
                                TokenTree.from(Punct.new('>', Spacing.Alone)).toTokens(ts11)
                                TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts11)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts11)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts11)
                                TokenTree.from(Ident.new("RepInterp", Span.defSite())).toTokens(ts11)
                                TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts13 = TokenStream.new()
                                    TokenTree.from(Ident.new("_x", Span.defSite())).toTokens(ts13)
                                    ts13
                                })).toTokens(ts11)
                                TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts11)
                                TokenTree.from(Ident.new("None", Span.defSite())).toTokens(ts11)
                                TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts11)
                                TokenTree.from(Punct.new('>', Spacing.Alone)).toTokens(ts11)
                                TokenTree.from(Ident.new("break", Span.defSite())).toTokens(ts11)
                                TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts11)
                                ts11
                            })).toTokens(ts10)
                            TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts10)
                            ts10
                        }.toTokens(whileBody)
                    }
                    run {
                        val ts14 = TokenStream.new()
                        (if (sepOpt != null) {
                            val sep1 = sepOpt
                            run {
                                val ts15 = TokenStream.new()
                                TokenTree.from(Ident.new("if", Span.defSite())).toTokens(ts15)
                                TokenTree.from(Ident.new("_i", Span.defSite())).toTokens(ts15)
                                TokenTree.from(Punct.new('>', Spacing.Alone)).toTokens(ts15)
                                TokenTree.from("0".parse<Literal>().unwrap()).toTokens(ts15)
                                TokenTree.from(Group.new(Delimiter.Brace, run {
                                    val ts16 = TokenStream.new()
                                    (run {
                                        val ts17 = TokenStream.new()
                                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts17)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts17)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts17)
                                        TokenTree.from(Ident.new("ToTokens", Span.defSite())).toTokens(ts17)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts17)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts17)
                                        TokenTree.from(Ident.new("to_tokens", Span.defSite())).toTokens(ts17)
                                        TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                            val ts18 = TokenStream.new()
                                            TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts18)
                                            TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts18)
                                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts18)
                                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts18)
                                            TokenTree.from(Ident.new("TokenTree", Span.defSite())).toTokens(ts18)
                                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts18)
                                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts18)
                                            TokenTree.from(Ident.new("Punct", Span.defSite())).toTokens(ts18)
                                            TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                                val ts19 = TokenStream.new()
                                                TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts19)
                                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts19)
                                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts19)
                                                TokenTree.from(Ident.new("Punct", Span.defSite())).toTokens(ts19)
                                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts19)
                                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts19)
                                                TokenTree.from(Ident.new("new", Span.defSite())).toTokens(ts19)
                                                TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                                    val ts20 = TokenStream.new()
                                                    TokenTree.from(Literal.character(sep1.asChar())).toTokens(ts20)
                                                    TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts20)
                                                    (run {
                                                        val ts21 = TokenStream.new()
                                                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts21)
                                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts21)
                                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts21)
                                                        TokenTree.from(Ident.new("Spacing", Span.defSite())).toTokens(ts21)
                                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts21)
                                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts21)
                                                        TokenTree.from(Ident.new("Alone", Span.defSite())).toTokens(ts21)
                                                        ts21
                                                    }).toTokens(ts20)
                                                    TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts20)
                                                    ts20
                                                })).toTokens(ts19)
                                                ts19
                                            })).toTokens(ts18)
                                            TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts18)
                                            TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts18)
                                            TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts18)
                                            TokenTree.from(Ident.new("ts", Span.defSite())).toTokens(ts18)
                                            ts18
                                        })).toTokens(ts17)
                                        TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts17)
                                        ts17
                                    }).toTokens(ts16)
                                    ts16
                                })).toTokens(ts15)
                                TokenTree.from(Ident.new("_i", Span.defSite())).toTokens(ts15)
                                TokenTree.from(minimalQuoteTt()).toTokens(ts15)
                                TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts15)
                                TokenTree.from("1".parse<Literal>().unwrap()).toTokens(ts15)
                                TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts15)
                                ts15
                            }
                        } else {
                            run {
                                val ts22 = TokenStream.new()
                                TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts22)
                                TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts22)
                                ts22
                            }
                        }).toTokens(ts14)
                        quote(contents.clone()).toTokens(ts14)
                        TokenTree.from(Punct.new('.', Spacing.Alone)).toTokens(ts14)
                        TokenTree.from(Ident.new("to_tokens", Span.defSite())).toTokens(ts14)
                        TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts23 = TokenStream.new()
                            TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts23)
                            TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts23)
                            TokenTree.from(Ident.new("ts", Span.defSite())).toTokens(ts23)
                            ts23
                        })).toTokens(ts14)
                        TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts14)
                        ts14
                    }.toTokens(whileBody)
                    repExpanded.extend(mutableListOf(TokenTree.Ident(Ident.new("while", Span.callSite())), TokenTree.Ident(Ident.new("true", Span.callSite())), TokenTree.Group(Group.new(Delimiter.Brace, whileBody))))
                    run {
                        val ts24 = TokenStream.new()
                        TokenTree.Group(Group.new(Delimiter.Brace, repExpanded)).toTokens(ts24)
                        ts24
                    }.toTokens(tokens)
                    continue
                }
                tree is TokenTree.Ident<*, *, *> -> {
                    run {
                        val ts25 = TokenStream.new()
                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts25)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts25)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts25)
                        TokenTree.from(Ident.new("ToTokens", Span.defSite())).toTokens(ts25)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts25)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts25)
                        TokenTree.from(Ident.new("to_tokens", Span.defSite())).toTokens(ts25)
                        TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts26 = TokenStream.new()
                            TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts26)
                            tree.toTokens(ts26)
                            TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts26)
                            TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts26)
                            TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts26)
                            TokenTree.from(Ident.new("ts", Span.defSite())).toTokens(ts26)
                            ts26
                        })).toTokens(ts25)
                        TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts25)
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
                    TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts27)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts27)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts27)
                    TokenTree.from(Ident.new("ToTokens", Span.defSite())).toTokens(ts27)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts27)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts27)
                    TokenTree.from(Ident.new("to_tokens", Span.defSite())).toTokens(ts27)
                    TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                        val ts28 = TokenStream.new()
                        TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts28)
                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts28)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts28)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts28)
                        TokenTree.from(Ident.new("TokenTree", Span.defSite())).toTokens(ts28)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts28)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts28)
                        TokenTree.from(Ident.new("Punct", Span.defSite())).toTokens(ts28)
                        TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts29 = TokenStream.new()
                            TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts29)
                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts29)
                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts29)
                            TokenTree.from(Ident.new("Punct", Span.defSite())).toTokens(ts29)
                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts29)
                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts29)
                            TokenTree.from(Ident.new("new", Span.defSite())).toTokens(ts29)
                            TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                val ts30 = TokenStream.new()
                                TokenTree.from(Literal.character(tt3.asChar())).toTokens(ts30)
                                TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts30)
                                (when (tt3.spacing()) {
                                    Spacing.Alone -> {
                                        val ts31 = TokenStream.new()
                                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts31)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts31)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts31)
                                        TokenTree.from(Ident.new("Spacing", Span.defSite())).toTokens(ts31)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts31)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts31)
                                        TokenTree.from(Ident.new("Alone", Span.defSite())).toTokens(ts31)
                                        ts31
                                    }
                                    Spacing.Joint -> {
                                        val ts32 = TokenStream.new()
                                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts32)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts32)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts32)
                                        TokenTree.from(Ident.new("Spacing", Span.defSite())).toTokens(ts32)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts32)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts32)
                                        TokenTree.from(Ident.new("Joint", Span.defSite())).toTokens(ts32)
                                        ts32
                                    }
                                }).toTokens(ts30)
                                TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts30)
                                ts30
                            })).toTokens(ts29)
                            ts29
                        })).toTokens(ts28)
                        TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts28)
                        TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts28)
                        TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts28)
                        TokenTree.from(Ident.new("ts", Span.defSite())).toTokens(ts28)
                        ts28
                    })).toTokens(ts27)
                    TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts27)
                    ts27
                }
            }
            is TokenTree.Group<*, *, *> -> {
                val (tt4) = tree
                run {
                    val ts33 = TokenStream.new()
                    TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts33)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts33)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts33)
                    TokenTree.from(Ident.new("ToTokens", Span.defSite())).toTokens(ts33)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts33)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts33)
                    TokenTree.from(Ident.new("to_tokens", Span.defSite())).toTokens(ts33)
                    TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                        val ts34 = TokenStream.new()
                        TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts34)
                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts34)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts34)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts34)
                        TokenTree.from(Ident.new("TokenTree", Span.defSite())).toTokens(ts34)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts34)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts34)
                        TokenTree.from(Ident.new("Group", Span.defSite())).toTokens(ts34)
                        TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts35 = TokenStream.new()
                            TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts35)
                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts35)
                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts35)
                            TokenTree.from(Ident.new("Group", Span.defSite())).toTokens(ts35)
                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts35)
                            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts35)
                            TokenTree.from(Ident.new("new", Span.defSite())).toTokens(ts35)
                            TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                val ts36 = TokenStream.new()
                                (when (tt4.delimiter()) {
                                    Delimiter.Parenthesis -> {
                                        val ts37 = TokenStream.new()
                                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts37)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts37)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts37)
                                        TokenTree.from(Ident.new("Delimiter", Span.defSite())).toTokens(ts37)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts37)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts37)
                                        TokenTree.from(Ident.new("Parenthesis", Span.defSite())).toTokens(ts37)
                                        ts37
                                    }
                                    Delimiter.Brace -> {
                                        val ts38 = TokenStream.new()
                                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts38)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts38)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts38)
                                        TokenTree.from(Ident.new("Delimiter", Span.defSite())).toTokens(ts38)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts38)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts38)
                                        TokenTree.from(Ident.new("Brace", Span.defSite())).toTokens(ts38)
                                        ts38
                                    }
                                    Delimiter.Bracket -> {
                                        val ts39 = TokenStream.new()
                                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts39)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts39)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts39)
                                        TokenTree.from(Ident.new("Delimiter", Span.defSite())).toTokens(ts39)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts39)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts39)
                                        TokenTree.from(Ident.new("Bracket", Span.defSite())).toTokens(ts39)
                                        ts39
                                    }
                                    Delimiter.None -> {
                                        val ts40 = TokenStream.new()
                                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts40)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts40)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts40)
                                        TokenTree.from(Ident.new("Delimiter", Span.defSite())).toTokens(ts40)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts40)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts40)
                                        TokenTree.from(Ident.new("None", Span.defSite())).toTokens(ts40)
                                        ts40
                                    }
                                }).toTokens(ts36)
                                TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts36)
                                quote(tt4.stream()).toTokens(ts36)
                                TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts36)
                                ts36
                            })).toTokens(ts35)
                            ts35
                        })).toTokens(ts34)
                        TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts34)
                        TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts34)
                        TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts34)
                        TokenTree.from(Ident.new("ts", Span.defSite())).toTokens(ts34)
                        ts34
                    })).toTokens(ts33)
                    TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts33)
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
                                TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts41)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts41)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts41)
                                TokenTree.from(Ident.new("Ident", Span.defSite())).toTokens(ts41)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts41)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts41)
                                TokenTree.from(Ident.new("new", Span.defSite())).toTokens(ts41)
                                ts41
                            })
                        }
                        else -> {
                            val stripped = matchVal2
                            Pair(stripped, run {
                                val ts42 = TokenStream.new()
                                TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts42)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts42)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts42)
                                TokenTree.from(Ident.new("Ident", Span.defSite())).toTokens(ts42)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts42)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts42)
                                TokenTree.from(Ident.new("new_raw", Span.defSite())).toTokens(ts42)
                                ts42
                            })
                        }
                    }
                run {
                    val ts43 = TokenStream.new()
                    TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts43)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts43)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts43)
                    TokenTree.from(Ident.new("ToTokens", Span.defSite())).toTokens(ts43)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts43)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts43)
                    TokenTree.from(Ident.new("to_tokens", Span.defSite())).toTokens(ts43)
                    TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                        val ts44 = TokenStream.new()
                        TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts44)
                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts44)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts44)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts44)
                        TokenTree.from(Ident.new("TokenTree", Span.defSite())).toTokens(ts44)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts44)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts44)
                        TokenTree.from(Ident.new("Ident", Span.defSite())).toTokens(ts44)
                        TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts45 = TokenStream.new()
                            ctor.toTokens(ts45)
                            TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                val ts46 = TokenStream.new()
                                TokenTree.from(Literal.string(literal1)).toTokens(ts46)
                                TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts46)
                                quoteSpan(procMacroCrate.clone(), tt5.span()).toTokens(ts46)
                                TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts46)
                                ts46
                            })).toTokens(ts45)
                            ts45
                        })).toTokens(ts44)
                        TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts44)
                        TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts44)
                        TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts44)
                        TokenTree.from(Ident.new("ts", Span.defSite())).toTokens(ts44)
                        ts44
                    })).toTokens(ts43)
                    TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts43)
                    ts43
                }
            }
            is TokenTree.Literal<*, *, *> -> {
                val (tt6) = tree
                run {
                    val ts47 = TokenStream.new()
                    TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts47)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts47)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts47)
                    TokenTree.from(Ident.new("ToTokens", Span.defSite())).toTokens(ts47)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts47)
                    TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts47)
                    TokenTree.from(Ident.new("to_tokens", Span.defSite())).toTokens(ts47)
                    TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                        val ts48 = TokenStream.new()
                        TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts48)
                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts48)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts48)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts48)
                        TokenTree.from(Ident.new("TokenTree", Span.defSite())).toTokens(ts48)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts48)
                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts48)
                        TokenTree.from(Ident.new("Literal", Span.defSite())).toTokens(ts48)
                        TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                            val ts49 = TokenStream.new()
                            TokenTree.from(Group.new(Delimiter.Brace, run {
                                val ts50 = TokenStream.new()
                                TokenTree.from(Ident.new("let", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Ident.new("iter", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Literal.string(tt6.toString())).toTokens(ts50)
                                TokenTree.from(Punct.new('.', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Ident.new("parse", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Punct.new('<', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Ident.new("TokenStream", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Punct.new('>', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts50)
                                TokenTree.from(Punct.new('.', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Ident.new("unwrap", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts50)
                                TokenTree.from(Punct.new('.', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Ident.new("into_iter", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts50)
                                TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Ident.new("if", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Ident.new("let", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts51 = TokenStream.new()
                                    TokenTree.from(Ident.new("Some", Span.defSite())).toTokens(ts51)
                                    TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                        val ts52 = TokenStream.new()
                                        TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts52)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts52)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts52)
                                        TokenTree.from(Ident.new("TokenTree", Span.defSite())).toTokens(ts52)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts52)
                                        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts52)
                                        TokenTree.from(Ident.new("Literal", Span.defSite())).toTokens(ts52)
                                        TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                            val ts53 = TokenStream.new()
                                            TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts53)
                                            TokenTree.from(Ident.new("lit", Span.defSite())).toTokens(ts53)
                                            ts53
                                        })).toTokens(ts52)
                                        ts52
                                    })).toTokens(ts51)
                                    TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts51)
                                    TokenTree.from(Ident.new("None", Span.defSite())).toTokens(ts51)
                                    ts51
                                })).toTokens(ts50)
                                TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts50)
                                TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                    val ts54 = TokenStream.new()
                                    TokenTree.from(Ident.new("iter", Span.defSite())).toTokens(ts54)
                                    TokenTree.from(Punct.new('.', Spacing.Alone)).toTokens(ts54)
                                    TokenTree.from(Ident.new("next", Span.defSite())).toTokens(ts54)
                                    TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts54)
                                    TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts54)
                                    TokenTree.from(Ident.new("iter", Span.defSite())).toTokens(ts54)
                                    TokenTree.from(Punct.new('.', Spacing.Alone)).toTokens(ts54)
                                    TokenTree.from(Ident.new("next", Span.defSite())).toTokens(ts54)
                                    TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts54)
                                    ts54
                                })).toTokens(ts50)
                                TokenTree.from(Group.new(Delimiter.Brace, run {
                                    val ts55 = TokenStream.new()
                                    TokenTree.from(Ident.new("lit", Span.defSite())).toTokens(ts55)
                                    TokenTree.from(Punct.new('.', Spacing.Alone)).toTokens(ts55)
                                    TokenTree.from(Ident.new("set_span", Span.defSite())).toTokens(ts55)
                                    TokenTree.from(Group.new(Delimiter.Parenthesis, run {
                                        val ts56 = TokenStream.new()
                                        quoteSpan(procMacroCrate.clone(), tt6.span()).toTokens(ts56)
                                        ts56
                                    })).toTokens(ts55)
                                    TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts55)
                                    TokenTree.from(Ident.new("lit", Span.defSite())).toTokens(ts55)
                                    ts55
                                })).toTokens(ts50)
                                TokenTree.from(Ident.new("else", Span.defSite())).toTokens(ts50)
                                TokenTree.from(Group.new(Delimiter.Brace, run {
                                    val ts57 = TokenStream.new()
                                    TokenTree.from(Ident.new("unreachable", Span.defSite())).toTokens(ts57)
                                    TokenTree.from(Punct.new('!', Spacing.Alone)).toTokens(ts57)
                                    TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts57)
                                    ts57
                                })).toTokens(ts50)
                                ts50
                            })).toTokens(ts49)
                            ts49
                        })).toTokens(ts48)
                        TokenTree.from(Punct.new(',', Spacing.Alone)).toTokens(ts48)
                        TokenTree.from(Punct.new('&', Spacing.Alone)).toTokens(ts48)
                        TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts48)
                        TokenTree.from(Ident.new("ts", Span.defSite())).toTokens(ts48)
                        ts48
                    })).toTokens(ts47)
                    TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts47)
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
        TokenTree.from(Group.new(Delimiter.Brace, run {
            val ts59 = TokenStream.new()
            TokenTree.from(Ident.new("let", Span.defSite())).toTokens(ts59)
            TokenTree.from(Ident.new("mut", Span.defSite())).toTokens(ts59)
            TokenTree.from(Ident.new("ts", Span.defSite())).toTokens(ts59)
            TokenTree.from(Punct.new('=', Spacing.Alone)).toTokens(ts59)
            TokenTree.from(Ident.new("crate", Span.defSite())).toTokens(ts59)
            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts59)
            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts59)
            TokenTree.from(Ident.new("TokenStream", Span.defSite())).toTokens(ts59)
            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts59)
            TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts59)
            TokenTree.from(Ident.new("new", Span.defSite())).toTokens(ts59)
            TokenTree.from(Group.new(Delimiter.Parenthesis, TokenStream.new())).toTokens(ts59)
            TokenTree.from(Punct.new(';', Spacing.Alone)).toTokens(ts59)
            tokens.toTokens(ts59)
            TokenTree.from(Ident.new("ts", Span.defSite())).toTokens(ts59)
            ts59
        })).toTokens(ts58)
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
        procMacroCrate.toTokens(ts)
        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts)
        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts)
        TokenTree.from(Ident.new("Span", Span.defSite())).toTokens(ts)
        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts)
        TokenTree.from(Punct.new(':', Spacing.Alone)).toTokens(ts)
        TokenTree.from(Ident.new("recover_proc_macro_span", Span.defSite())).toTokens(ts)
        TokenTree.from(Group.new(Delimiter.Parenthesis, run {
            val ts1 = TokenStream.new()
            TokenTree.from(Literal.usizeUnsuffixed(id)).toTokens(ts1)
            ts1
        })).toTokens(ts)
        ts
    }
}

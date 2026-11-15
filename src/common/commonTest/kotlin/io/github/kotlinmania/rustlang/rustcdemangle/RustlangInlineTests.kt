package io.github.kotlinmania.rustlang.rustcdemangle

import io.github.kotlinmania.rustlang.core.result.Result

// Demangle Rust compiler symbol names.
//
// This crate provides a `demangle` function which will return a `Demangle`
// sentinel value that can be used to learn about the demangled version of a
// symbol name. The demangled representation will be the same as the original
// if it doesn't look like a mangled symbol name.
//
// `Demangle` can be formatted with the `Display` trait. The alternate
// modifier (`#`) can be used to format the symbol name without the
// trailing hash value.
//
// # Examples
//
// ```kotlin
// import std.prelude.v1.*
//
// check(demangle("_ZN4testE").toString() == "test")
// check(demangle("_ZN3foo3barE").toString() == "foo.bar")
// check(demangle("foo").toString() == "foo")
// // With hash
// check((demangle("_ZN3foo17h05af221e174051e9E")).toString() == "foo.h05af221e174051e9")
// // Without hash
// check((demangle("_ZN3foo17h05af221e174051e9E")).toString() == "foo")
// ```

public fun ok(sym: String, expected: String): Boolean =
    run {
        val matchVal = io.github.kotlinmania.rustlang.tryDemangle(sym)
        when (matchVal) { is Result.Ok<*, *> -> {
            val s = matchVal.value
            if (s.toString() == expected) {
                true
            } else {
                println("\n$s\n!=\n$expected\n")
                false
            }
        }; is Result.Err<*, *> -> {
            println("error demangling")
            false
        } }
    }

public fun okErr(sym: String): Boolean =
    run {
        val matchVal = io.github.kotlinmania.rustlang.tryDemangle(sym)
        when (matchVal) { is Result.Ok<*, *> -> {
            println("succeeded in demangling")
            false
        }; is Result.Err<*, *> -> {
            io.github.kotlinmania.rustlang.demangle(sym).toString() == sym
        } }
    }

public fun demangleStr(input: String): String {
    val output = mutableListOf()
    io.github.kotlinmania.rustlang.demangleLine(input, output, false)
    return String.fromUtf8(output).unwrap()
}

public class RustlangInlineTests {
    @kotlin.test.Test
    public fun demangle() {
        check(okErr("test"))
        check(ok("_ZN4testE", "test"))
        check(okErr("_ZN4test"))
        check(ok("_ZN4test1a2bcE", "test::a::bc"))
    }

    @kotlin.test.Test
    public fun demangleDollars() {
        check(ok("_ZN4\$RP\$E", ")"))
        check(ok("_ZN8\$RF\$testE", "&test"))
        check(ok("_ZN8\$BP\$test4foobE", "*test::foob"))
        check(ok("_ZN9\$u20\$test4foobE", " test::foob"))
        check(ok("_ZN35Bar\$LT\$\$u5b\$u32\$u3b\$\$u20\$4\$u5d\$\$GT\$E", "Bar<[u32; 4]>"))
    }

    @kotlin.test.Test
    public fun demangleManyDollars() {
        check(ok("_ZN13test\$u20\$test4foobE", "test test::foob"))
        check(ok("_ZN12test\$BP\$test4foobE", "test*test::foob"))
    }

    @kotlin.test.Test
    public fun demangleOsx() {
        check(ok("__ZN5alloc9allocator6Layout9for_value17h02a996811f781011E", "alloc::allocator::Layout::for_value::h02a996811f781011"))
        check(ok("__ZN38_\$LT\$core..option..Option\$LT\$T\$GT\$\$GT\$6unwrap18_MSG_FILE_LINE_COL17haf7cb8d5824ee659E", "<core::option::Option<T>>::unwrap::_MSG_FILE_LINE_COL::haf7cb8d5824ee659"))
        check(ok("__ZN4core5slice89_\$LT\$impl\$u20\$core..iter..traits..IntoIterator\$u20\$for\$u20\$\$RF\$\$u27\$a\$u20\$\$u5b\$T\$u5d\$\$GT\$9into_iter17h450e234d27262170E", "core::slice::<impl core::iter::traits::IntoIterator for &'a [T]>::into_iter::h450e234d27262170"))
    }

    @kotlin.test.Test
    public fun demangleWindows() {
        check(ok("ZN4testE", "test"))
        check(ok("ZN13test\$u20\$test4foobE", "test test::foob"))
        check(ok("ZN12test\$RF\$test4foobE", "test&test::foob"))
    }

    @kotlin.test.Test
    public fun demangleElementsBeginningWithUnderscore() {
        check(ok("_ZN13_\$LT\$test\$GT\$E", "<test>"))
        check(ok("_ZN28_\$u7b\$\$u7b\$closure\$u7d\$\$u7d\$E", "{{closure}}"))
        check(ok("_ZN15__STATIC_FMTSTRE", "__STATIC_FMTSTR"))
    }

    @kotlin.test.Test
    public fun demangleTraitImpls() {
        check(ok("_ZN71_\$LT\$Test\$u20\$\$u2b\$\$u20\$\$u27\$static\$u20\$as\$u20\$foo..Bar\$LT\$Test\$GT\$\$GT\$3barE", "<Test + 'static as foo::Bar<Test>>::bar"))
    }

    @kotlin.test.Test
    public fun demangleWithoutHash() {
        val s = "_ZN3foo17h05af221e174051e9E"
        check(ok(s, "foo::h05af221e174051e9"))
        run {
            check("${io.github.kotlinmania.rustlang.demangle(s)}" == "foo")
        }
    }

    @kotlin.test.Test
    public fun demangleWithoutHashEdgecases() {
        run {
            check((io.github.kotlinmania.rustlang.demangle("_ZN3fooE")).toString() == "foo")
        }
        run {
            check((io.github.kotlinmania.rustlang.demangle("_ZN3foo3barE")).toString() == "foo::bar")
        }
        run {
            check((io.github.kotlinmania.rustlang.demangle("_ZN3foo20h05af221e174051e9abcE")).toString() == "foo")
        }
        run {
            check((io.github.kotlinmania.rustlang.demangle("_ZN3foo5h05afE")).toString() == "foo")
        }
        run {
            check((io.github.kotlinmania.rustlang.demangle("_ZN17h05af221e174051e93fooE")).toString() == "h05af221e174051e9::foo")
        }
        run {
            check((io.github.kotlinmania.rustlang.demangle("_ZN3foo16ffaf221e174051e9E")).toString() == "foo::ffaf221e174051e9")
        }
        run {
            check((io.github.kotlinmania.rustlang.demangle("_ZN3foo17hg5af221e174051e9E")).toString() == "foo::hg5af221e174051e9")
        }
    }

    @kotlin.test.Test
    public fun demangleThinlto() {
        check(ok("_ZN3fooE.llvm.9D1C9369", "foo"))
        check(ok("_ZN3fooE.llvm.9D1C9369@@16", "foo"))
        run {
            check((io.github.kotlinmania.rustlang.demangle("_ZN9backtrace3foo17hbb467fcdaea5d79bE.llvm.A5310EB9")).toString() == "backtrace::foo")
        }
    }

    @kotlin.test.Test
    public fun demangleLlvmIrBranchLabels() {
        check(ok("_ZN4core5slice77_\$LT\$impl\$u20\$core..ops..index..IndexMut\$LT\$I\$GT\$\$u20\$for\$u20\$\$u5b\$T\$u5d\$\$GT\$9index_mut17haf9727c2edfbc47bE.exit.i.i", "core::slice::<impl core::ops::index::IndexMut<I> for [T]>::index_mut::haf9727c2edfbc47b.exit.i.i"))
        run {
            check((io.github.kotlinmania.rustlang.demangle("_ZN4core5slice77_\$LT\$impl\$u20\$core..ops..index..IndexMut\$LT\$I\$GT\$\$u20\$for\$u20\$\$u5b\$T\$u5d\$\$GT\$9index_mut17haf9727c2edfbc47bE.exit.i.i")).toString() == "core::slice::<impl core::ops::index::IndexMut<I> for [T]>::index_mut.exit.i.i")
        }
    }

    @kotlin.test.Test
    public fun demangleIgnoresSuffixThatDoesntLookLikeASymbol() {
        check(okErr("_ZN3fooE.llvm moocow"))
    }

    @kotlin.test.Test
    public fun dontPanic() {
        io.github.kotlinmania.rustlang.demangle("_ZN2222222222222222222222EE").toString()
        io.github.kotlinmania.rustlang.demangle("_ZN5*70527e27.ll34csaғE").toString()
        io.github.kotlinmania.rustlang.demangle("_ZN5*70527a54.ll34_\$b.1E").toString()
        io.github.kotlinmania.rustlang.demangle("_ZN5~saäb4e\n2734cOsbE\n5usage20h)3\u0000\u0000\u0000\u0000\u0000\u0000\u00007e2734cOsbE").toString()
    }

    @kotlin.test.Test
    public fun invalidNoChop() {
        check(okErr("_ZNfooE"))
    }

    @kotlin.test.Test
    public fun handleAssocTypes() {
        check(ok("_ZN151_\$LT\$alloc..boxed..Box\$LT\$alloc..boxed..FnBox\$LT\$A\$C\$\$u20\$Output\$u3d\$R\$GT\$\$u20\$\$u2b\$\$u20\$\$u27\$a\$GT\$\$u20\$as\$u20\$core..ops..function..FnOnce\$LT\$A\$GT\$\$GT\$9call_once17h69e8f44b3723e1caE", "<alloc::boxed::Box<alloc::boxed::FnBox<A, Output=R> + 'a> as core::ops::function::FnOnce<A>>::call_once::h69e8f44b3723e1ca"))
    }

    @kotlin.test.Test
    public fun handleBang() {
        check(ok("_ZN88_\$LT\$core..result..Result\$LT\$\$u21\$\$C\$\$u20\$E\$GT\$\$u20\$as\$u20\$std..process..Termination\$GT\$6report17hfc41d0da4a40b3e8E", "<core::result::Result<!, E> as std::process::Termination>::report::hfc41d0da4a40b3e8"))
    }

    @kotlin.test.Test
    public fun limitRecursion() {
        run {
            val (s, needle) = Pair(io.github.kotlinmania.rustlang.demangle("_RNvB_1a").toString(), "{recursion limit reached}")
            check(s.contains(needle)) { "$s should've contained $needle" }
        }
        run {
            val (s1, needle1) = Pair(io.github.kotlinmania.rustlang.demangle("_RMC0RB2_").toString(), "{recursion limit reached}")
            check(s1.contains(needle1)) { "$s1 should've contained $needle1" }
        }
    }

    @kotlin.test.Test
    public fun limitOutput() {
        run {
            val (s, suffix) = Pair(io.github.kotlinmania.rustlang.demangle("RYFG_FGyyEvRYFF_EvRYFFEvERLB_B_B_ERLRjB_B_B_").toString(), "{size limit reached}")
            check(s.endsWith(suffix)) { "$s should've ended in $suffix" }
        }
        run {
            val (s1, suffix1) = Pair(io.github.kotlinmania.rustlang.demangle("_RMC0FGZZZ_Eu").toString(), "{size limit reached}")
            check(s1.endsWith(suffix1)) { "$s1 should've ended in $suffix1" }
        }
    }

    @kotlin.test.Test
    public fun findMultiple() {
        check(demangleStr("_ZN3fooE.llvm moocow _ZN3fooE.llvm") == "foo.llvm moocow foo.llvm")
    }

    @kotlin.test.Test
    public fun interleavedNewLegacy() {
        check(demangleStr("_ZN3fooE.llvm moocow _RNvMNtNtNtNtCs8a2262Dv4r_3mio3sys4unix8selector5epollNtB2_8Selector6select _ZN3fooE.llvm") == "foo.llvm moocow <mio::sys::unix::selector::epoll::Selector>::select foo.llvm")
    }
}

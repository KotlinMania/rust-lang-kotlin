// port-lint: tests legacy.rs
package io.github.kotlinmania.rustlang.rustcdemangle.legacy

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.rustcdemangle.tryDemangle

public fun ok(sym: String, expected: String): Boolean =
    run {
        val matchVal = tryDemangle(sym)
        when (matchVal) { is Result.Ok<*> -> {
            val s = matchVal.value
            if (s.toString() == expected) {
                true
            } else {
                println("\n$s\n!=\n$expected\n")
                false
            }
        }; is Result.Err<*> -> {
            println("error demangling")
            false
        } }
    }

public fun okErr(sym: String): Boolean =
    run {
        val matchVal = tryDemangle(sym)
        when (matchVal) { is Result.Ok<*> -> {
            println("succeeded in demangling")
            false
        }; is Result.Err<*> -> {
            demangle(sym).toString() == sym
        } }
    }

public class LegacyInlineTests {
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
            check("${demangle(s)}" == "foo")
        }
    }

    @kotlin.test.Test
    public fun demangleWithoutHashEdgecases() {
        run {
            check((demangle("_ZN3fooE")).toString() == "foo")
        }
        run {
            check((demangle("_ZN3foo3barE")).toString() == "foo::bar")
        }
        run {
            check((demangle("_ZN3foo20h05af221e174051e9abcE")).toString() == "foo")
        }
        run {
            check((demangle("_ZN3foo5h05afE")).toString() == "foo")
        }
        run {
            check((demangle("_ZN17h05af221e174051e93fooE")).toString() == "h05af221e174051e9::foo")
        }
        run {
            check((demangle("_ZN3foo16ffaf221e174051e9E")).toString() == "foo::ffaf221e174051e9")
        }
        run {
            check((demangle("_ZN3foo17hg5af221e174051e9E")).toString() == "foo::hg5af221e174051e9")
        }
    }

    @kotlin.test.Test
    public fun demangleThinlto() {
        check(ok("_ZN3fooE.llvm.9D1C9369", "foo"))
        check(ok("_ZN3fooE.llvm.9D1C9369@@16", "foo"))
        run {
            check((demangle("_ZN9backtrace3foo17hbb467fcdaea5d79bE.llvm.A5310EB9")).toString() == "backtrace::foo")
        }
    }

    @kotlin.test.Test
    public fun demangleLlvmIrBranchLabels() {
        check(ok("_ZN4core5slice77_\$LT\$impl\$u20\$core..ops..index..IndexMut\$LT\$I\$GT\$\$u20\$for\$u20\$\$u5b\$T\$u5d\$\$GT\$9index_mut17haf9727c2edfbc47bE.exit.i.i", "core::slice::<impl core::ops::index::IndexMut<I> for [T]>::index_mut::haf9727c2edfbc47b.exit.i.i"))
        run {
            check((demangle("_ZN4core5slice77_\$LT\$impl\$u20\$core..ops..index..IndexMut\$LT\$I\$GT\$\$u20\$for\$u20\$\$u5b\$T\$u5d\$\$GT\$9index_mut17haf9727c2edfbc47bE.exit.i.i")).toString() == "core::slice::<impl core::ops::index::IndexMut<I> for [T]>::index_mut.exit.i.i")
        }
    }

    @kotlin.test.Test
    public fun demangleIgnoresSuffixThatDoesntLookLikeASymbol() {
        check(okErr("_ZN3fooE.llvm moocow"))
    }

    @kotlin.test.Test
    public fun dontPanic() {
        demangle("_ZN2222222222222222222222EE").toString()
        demangle("_ZN5*70527e27.ll34csaғE").toString()
        demangle("_ZN5*70527a54.ll34_\$b.1E").toString()
        demangle("_ZN5~saäb4e\n2734cOsbE\n5usage20h)3\u0000\u0000\u0000\u0000\u0000\u0000\u00007e2734cOsbE").toString()
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
    public fun demangleUtf8Idents() {
        run {
            check((demangle("_ZN11utf8_idents157_\$u10e1\$\$u10d0\$\$u10ed\$\$u10db\$\$u10d4\$\$u10da\$\$u10d0\$\$u10d3\$_\$u10d2\$\$u10d4\$\$u10db\$\$u10e0\$\$u10d8\$\$u10d4\$\$u10da\$\$u10d8\$_\$u10e1\$\$u10d0\$\$u10d3\$\$u10d8\$\$u10da\$\$u10d8\$17h21634fd5714000aaE")).toString() == "utf8_idents::საჭმელად_გემრიელი_სადილი")
        }
    }

    @kotlin.test.Test
    public fun demangleIssue60925() {
        run {
            check((demangle("_ZN11issue_609253foo37Foo\$LT\$issue_60925..llv\$u6d\$..Foo\$GT\$3foo17h059a991a004536adE")).toString() == "issue_60925::foo::Foo<issue_60925::llvm::Foo>::foo")
        }
    }
}

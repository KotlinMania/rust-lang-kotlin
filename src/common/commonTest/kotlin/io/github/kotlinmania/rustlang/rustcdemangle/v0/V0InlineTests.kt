// port-lint: tests v0.rs
package io.github.kotlinmania.rustlang.rustcdemangle.v0

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.v0.MAX_DEPTH

public class V0InlineTests {
    @kotlin.test.Test
    public fun demangleCrateWithLeadingDigit() {
        run {
            check((demangle("_RNvC6_123foo3bar")).toString() == "123foo::bar")
        }
    }

    @kotlin.test.Test
    public fun demangleCrateWithZeroDisambiguator() {
        run {
            check((demangle("_RC4f128")).toString() == "f128")
        }
        run {
            check((demangle("_RC4f128")).toString() == "f128")
        }
    }

    @kotlin.test.Test
    public fun demangleUtf8Idents() {
        run {
            check((demangle("_RNqCs4fqI2P2rA04_11utf8_identsu30____7hkackfecea1cbdathfdh9hlq6y")).toString() == "utf8_idents::საჭმელად_გემრიელი_სადილი")
        }
    }

    @kotlin.test.Test
    public fun demangleClosure() {
        run {
            check((demangle("_RNCNCNgCs6DXkGYLi8lr_2cc5spawn00B5_")).toString() == "cc::spawn::{closure#0}::{closure#0}")
        }
        run {
            check((demangle("_RNCINkXs25_NgCsbmNqQUJIY6D_4core5sliceINyB9_4IterhENuNgNoBb_4iter8iterator8Iterator9rpositionNCNgNpB9_6memchr7memrchrs_0E0Bb_")).toString() == "<core::slice::Iter<u8> as core::iter::iterator::Iterator>::rposition::<core::slice::memchr::memrchr::{closure#1}>::{closure#0}")
        }
    }

    @kotlin.test.Test
    public fun demangleDynTrait() {
        run {
            check((demangle("_RINbNbCskIICzLVDPPb_5alloc5alloc8box_freeDINbNiB4_5boxed5FnBoxuEp6OutputuEL_ECs1iopQbuBiw2_3std")).toString() == "alloc::alloc::box_free::<dyn alloc::boxed::FnBox<(), Output = ()>>")
        }
    }

    @kotlin.test.Test
    public fun demangleConstGenericsPreview() {
        run {
            check((demangle("_RMC0INtC8arrayvec8ArrayVechKj7b_E")).toString() == "<arrayvec::ArrayVec<u8, 123>>")
        }
        run {
            run {
                check((demangle("_RIC0Kj7b_E")).toString() == "::<123>")
            }
            run {
                check((demangle("_RIC0Kj7b_E")).toString() == "::<123usize>")
            }
        }
    }

    @kotlin.test.Test
    public fun demangleMinConstGenerics() {
        run {
            check((demangle("_RIC0KpE")).toString() == "::<_>")
        }
        run {
            run {
                check((demangle("_RIC0Khb_E")).toString() == "::<11>")
            }
            run {
                check((demangle("_RIC0Khb_E")).toString() == "::<11u8>")
            }
        }
        run {
            run {
                check((demangle("_RIC0Koff00ff00ff00ff00ff_E")).toString() == "::<0xff00ff00ff00ff00ff>")
            }
            run {
                check((demangle("_RIC0Koff00ff00ff00ff00ff_E")).toString() == "::<0xff00ff00ff00ff00ffu128>")
            }
        }
        run {
            run {
                check((demangle("_RIC0Ks98_E")).toString() == "::<152>")
            }
            run {
                check((demangle("_RIC0Ks98_E")).toString() == "::<152i16>")
            }
        }
        run {
            run {
                check((demangle("_RIC0Kanb_E")).toString() == "::<-11>")
            }
            run {
                check((demangle("_RIC0Kanb_E")).toString() == "::<-11i8>")
            }
        }
        run {
            check((demangle("_RIC0Kb0_E")).toString() == "::<false>")
        }
        run {
            check((demangle("_RIC0Kb1_E")).toString() == "::<true>")
        }
        run {
            check((demangle("_RIC0Kc76_E")).toString() == "::<'v'>")
        }
        run {
            check((demangle("_RIC0Kc22_E")).toString() == "::<'\"'>")
        }
        run {
            check((demangle("_RIC0Kca_E")).toString() == "::<'\\n'>")
        }
        run {
            check((demangle("_RIC0Kc2202_E")).toString() == "::<'∂'>")
        }
    }

    @kotlin.test.Test
    public fun demangleConstStr() {
        run {
            check((demangle("_RIC0Ke616263_E")).toString() == "::<{*\"abc\"}>")
        }
        run {
            check((demangle("_RIC0Ke27_E")).toString() == "::<{*\"'\"}>")
        }
        run {
            check((demangle("_RIC0Ke090a_E")).toString() == "::<{*\"\\t\\n\"}>")
        }
        run {
            check((demangle("_RIC0Kee28882c3bc_E")).toString() == "::<{*\"∂ü\"}>")
        }
        run {
            check((demangle("_RIC0Kee183a1e18390e183ade1839be18394e1839ae18390e183935fe18392e18394e1839be183a0e18398e18394e1839ae183985fe183a1e18390e18393e18398e1839ae18398_E")).toString() == "::<{*\"საჭმელად_გემრიელი_სადილი\"}>")
        }
        run {
            check((demangle("_RIC0Kef09f908af09fa688f09fa686f09f90ae20c2a720f09f90b6f09f9192e29895f09f94a520c2a720f09fa7a1f09f929bf09f929af09f9299f09f929c_E")).toString() == "::<{*\"🐊🦈🦆🐮 § 🐶👒☕🔥 § 🧡💛💚💙💜\"}>")
        }
    }

    // NOTE(eddyb) this uses the same strings as `demangleConstStr` and should
    // be kept in sync with it - while a macro could be used to generate both
    // `String` and `String` tests, from a single list of strings, this seems clearer.
    @kotlin.test.Test
    public fun demangleConstRefStr() {
        run {
            check((demangle("_RIC0KRe616263_E")).toString() == "::<\"abc\">")
        }
        run {
            check((demangle("_RIC0KRe27_E")).toString() == "::<\"'\">")
        }
        run {
            check((demangle("_RIC0KRe090a_E")).toString() == "::<\"\\t\\n\">")
        }
        run {
            check((demangle("_RIC0KRee28882c3bc_E")).toString() == "::<\"∂ü\">")
        }
        run {
            check((demangle("_RIC0KRee183a1e18390e183ade1839be18394e1839ae18390e183935fe18392e18394e1839be183a0e18398e18394e1839ae183985fe183a1e18390e18393e18398e1839ae18398_E")).toString() == "::<\"საჭმელად_გემრიელი_სადილი\">")
        }
        run {
            check((demangle("_RIC0KRef09f908af09fa688f09fa686f09f90ae20c2a720f09f90b6f09f9192e29895f09f94a520c2a720f09fa7a1f09f929bf09f929af09f9299f09f929c_E")).toString() == "::<\"🐊🦈🦆🐮 § 🐶👒☕🔥 § 🧡💛💚💙💜\">")
        }
    }

    @kotlin.test.Test
    public fun demangleConstRef() {
        run {
            check((demangle("_RIC0KRpE")).toString() == "::<{&_}>")
        }
        run {
            check((demangle("_RIC0KRh7b_E")).toString() == "::<{&123}>")
        }
        run {
            check((demangle("_RIC0KRb0_E")).toString() == "::<{&false}>")
        }
        run {
            check((demangle("_RIC0KRc58_E")).toString() == "::<{&'X'}>")
        }
        run {
            check((demangle("_RIC0KRRRh0_E")).toString() == "::<{&&&0}>")
        }
        run {
            check((demangle("_RIC0KRRRe_E")).toString() == "::<{&&\"\"}>")
        }
        run {
            check((demangle("_RIC0KQAEE")).toString() == "::<{&mut []}>")
        }
    }

    @kotlin.test.Test
    public fun demangleConstArray() {
        run {
            check((demangle("_RIC0KAEE")).toString() == "::<{[]}>")
        }
        run {
            check((demangle("_RIC0KAj0_EE")).toString() == "::<{[0]}>")
        }
        run {
            check((demangle("_RIC0KAh1_h2_h3_EE")).toString() == "::<{[1, 2, 3]}>")
        }
        run {
            check((demangle("_RIC0KARe61_Re62_Re63_EE")).toString() == "::<{[\"a\", \"b\", \"c\"]}>")
        }
        run {
            check((demangle("_RIC0KAAh1_h2_EAh3_h4_EEE")).toString() == "::<{[[1, 2], [3, 4]]}>")
        }
    }

    @kotlin.test.Test
    public fun demangleConstTuple() {
        run {
            check((demangle("_RIC0KTEE")).toString() == "::<{()}>")
        }
        run {
            check((demangle("_RIC0KTj0_EE")).toString() == "::<{(0,)}>")
        }
        run {
            check((demangle("_RIC0KTh1_b0_EE")).toString() == "::<{(1, false)}>")
        }
        run {
            check((demangle("_RIC0KTRe616263_c78_RAh1_h2_h3_EEE")).toString() == "::<{(\"abc\", 'x', &[1, 2, 3])}>")
        }
    }

    @kotlin.test.Test
    public fun demangleConstAdt() {
        run {
            check((demangle("_RIC0KVNvINtNtC4core6option6OptionjE4NoneUE")).toString() == "::<{core::option::Option::<usize>::None}>")
        }
        run {
            check((demangle("_RIC0KVNvINtNtC4core6option6OptionjE4SomeTj0_EE")).toString() == "::<{core::option::Option::<usize>::Some(0)}>")
        }
        run {
            check((demangle("_RIC0KVNtC3foo3BarS1sRe616263_2chc78_5sliceRAh1_h2_h3_EEE")).toString() == "::<{foo::Bar { s: \"abc\", ch: 'x', slice: &[1, 2, 3] }}>")
        }
    }

    @kotlin.test.Test
    public fun demangleExponentialExplosion() {
        run {
            check((demangle("_RMC0TTTTTTpB8_EB7_EB6_EB5_EB4_EB3_E")).toString() == "<((((((_, _), (_, _)), ((_, _), (_, _))), (((_, _), (_, _)), ((_, _), (_, _)))), ((((_, _), (_, _)), ((_, _), (_, _))), (((_, _), (_, _)), ((_, _), (_, _))))), (((((_, _), (_, _)), ((_, _), (_, _))), (((_, _), (_, _)), ((_, _), (_, _)))), ((((_, _), (_, _)), ((_, _), (_, _))), (((_, _), (_, _)), ((_, _), (_, _))))))>")
        }
    }

    @kotlin.test.Test
    public fun demangleThinlto() {
        run {
            check((demangle("_RC3foo.llvm.9D1C9369")).toString() == "foo")
        }
        run {
            check((demangle("_RC3foo.llvm.9D1C9369@@16")).toString() == "foo")
        }
        run {
            check((demangle("_RNvC9backtrace3foo.llvm.A5310EB9")).toString() == "backtrace::foo")
        }
    }

    @kotlin.test.Test
    public fun demangleExtraSuffix() {
        run {
            check((demangle("_RNvNtNtNtNtCs92dm3009vxr_4rand4rngs7adapter9reseeding4fork23FORK_HANDLER_REGISTERED.0.0")).toString() == "rand::rngs::adapter::reseeding::fork::FORK_HANDLER_REGISTERED.0.0")
        }
    }

    @kotlin.test.Test
    public fun demanglingLimits() {
        // Stress tests found via fuzzing.
        for (sym in includeStr("v0-large-test-symbols/early-recursion-limit").lines().filter({ line -> !line.isEmpty() && !line.startsWith('#') })) {
            check((io.github.kotlinmania.rustlang.demangle(sym).map({ Unit })).rustEquals(Result.Err(io.github.kotlinmania.rustlang.v0.ParseError.RecursedTooDeep)))
        }
        assertContains(demangle("RIC20tRYIMYNRYFG05_EB5_B_B6_RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRB_E").toString(), "{recursion limit reached}")
    }

    @kotlin.test.Test
    public fun recursionLimitLeaks() {
        // NOTE(eddyb) this test checks that both paths and types support the
        // recursion limit correctly, i.e. matching `pushDepth` and `popDepth`,
        // and don't leak "recursion levels" and trip the limit.
        // The test inputs are generated on the fly, using a repeated pattern,
        // as hardcoding the actual strings would be too verbose.
        // Also, `MAX_DEPTH` can be directly used, instead of assuming its value.
        for ((symLeaf, expectedLeaf) in listOf(Pair("p", "_"), Pair("Rp", "&_"), Pair("C1x", "x"))) {
            val sym = "_RIC0p"
            val expected = "::<_"
            for (unused in 0 until (MAX_DEPTH * 2u)) {
                sym.pushStr(symLeaf)
                expected.pushStr(", ")
                expected.pushStr(expectedLeaf)
            }
            sym.push('E')
            expected.push('>')
            run {
                check("${demangle(sym)}" == expected)
            }
        }
    }

    @kotlin.test.Test
    public fun recursionLimitBackrefFreeBypass() {
        // NOTE(eddyb) this test checks that long symbols cannot bypass the
        // recursion limit by not using backrefs, and cause a stack overflow.
        // This value was chosen to be high enough that stack overflows were
        // observed even with `cargo test - - release`.
        val depth = 100000
        // In order to hide the long mangling from the initial "shallow" parse,
        // it's nested in an identifier (crate name), preceding its use.
        val sym = "_RIC$depth"
        val backrefStart = sym.len() - 2
        for (unused in 0 until depth) {
            sym.push('R')
        }
        // Write a backref to just after the length of the identifier.
        sym.push('B')
        sym.push(Char.fromDigit((backrefStart - 1).toUInt(), 36).unwrap())
        sym.push('_')
        // Close the `I` at the start.
        sym.push('E')
        assertContains(demangle(sym).toString(), "{recursion limit reached}")
    }
}

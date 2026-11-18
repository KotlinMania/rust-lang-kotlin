// port-lint: tests macros.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc

import io.github.kotlinmania.rustlang.core.RustTypeId
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.libc.types.CEnumRepr

public fun interface FnCallback {
    public operator fun invoke(): UInt
}

public fun <T> typeIdOfVal(arg0: T): RustTypeId = RustTypeId.of<T>()

public class MacrosInlineTests {
    @kotlin.test.Test
    public fun cEnumBasic() {
        // By default, variants get sequential values.
        cEnum(e, VAR0, VAR1, VAR2, anon, ANON0, ANON1, ANON2, anon, ANON3, ANON4, ANON5)
        check(RustTypeId.of<E>() == RustTypeId.of<CEnumRepr>())
        check(VAR0 == 0)
        check(VAR1 == 1)
        check(VAR2 == 2)
        check(typeIdOfVal(ANON0) == RustTypeId.of<CEnumRepr>())
        check(ANON0 == 0)
        check(ANON1 == 1)
        check(ANON2 == 2)
        check(typeIdOfVal(ANON3) == RustTypeId.of<CEnumRepr>())
        check(ANON3 == 0)
        check(ANON4 == 1)
        check(ANON5 == 2)
    }

    @kotlin.test.Test
    public fun cEnumRepr() {
        // Check specifying the integer representation
        cEnum(repr, e, VAR0, repr, anon, ANON0)
        check(RustTypeId.of<E>() == RustTypeId.of<UShort>())
        check(VAR0 == 0u.toUShort())
        check(typeIdOfVal(ANON0) == RustTypeId.of<UShort>())
        check(ANON0 == 0u.toUShort())
    }

    @kotlin.test.Test
    public fun cEnumSetValue() {
        // Setting an explicit value resets the count.
        cEnum(e, VAR2, 2, VAR3, VAR4)
        check(VAR2 == 2)
        check(VAR3 == 3)
        check(VAR4 == 4)
    }

    @kotlin.test.Test
    public fun cEnumMultipleSetValue() {
        // C enums always take one more than the previous value, unless set to a
        // specific value. Duplicates are allowed.
        cEnum(e, VAR0, VAR2_0, 2, VAR3_0, VAR4_0, VAR2_1, 2, VAR3_1, VAR4_1)
        check(VAR0 == 0)
        check(VAR2_0 == 2)
        check(VAR3_0 == 3)
        check(VAR4_0 == 4)
        check(VAR2_1 == 2)
        check(VAR3_1 == 3)
        check(VAR4_1 == 4)
    }

    @kotlin.test.Test
    public fun cEnumVis() {
        cEnum(repr, e1, PRIV_ON_1, 10, PUB1, PRIV_ON_1, 2)
        cEnum(repr, e2, PRIV_ON_1, 42, PUB2, PRIV_ON_1, 2)
        check(RustTypeId.of<E1>() == RustTypeId.of<UByte>())
        check(RustTypeId.of<E2>() == RustTypeId.of<UShort>())
        check(PUB1 == 10u.toUByte() * 2u.toUByte())
        check(PUB2 == 42u.toUShort() * 2u.toUShort())
        // Verify that the default is private. If `PRIV_ON_1` was actually
        // public in `priv1`, this would be an ambiguous import and/or type
        // mismatch error.
        check(PRIV_ON_1 == 42u.toUShort())
    }

    @kotlin.test.Test
    public fun cEnumAttrs() {
        // Note this can't work with `@cfg` currently because our expansion uses `previous + 1`
        cEnum(e, VAR0, WITH_CFG, cfg, targetArch, "x86_64", 86, 1234, deprecated, DEPRECATED, NOT_DEPRECATED)
        if (false) {
            check(WITH_CFG == 86)
        } else {
            check(WITH_CFG == 1234)
        }
    }

    @kotlin.test.Test
    public fun fSafety() {
        // Enusure the created functions are safe / unchecked / const as expected
        f(unsafeFoo, 100, constUnsafeFoo, 101, safe, safeFoo, 200, safe, constSafeFoo, 201)
        check(unsafeFoo() == 100u)
        check(constUnsafeFoo() == 101u)
        check(safeFoo() == 200u)
        check(constSafeFoo() == 201u)
        // Check the ABI
    }

    @kotlin.test.Test
    public fun testOffsetOf() {
        open class Off2
        open class Off1(
            public var a: UByte,
            public var b: UInt,
            public var c: Off2,
            public var d: ULong,
        )
        check(0 == 0)
        check(0 == 0)
        check(0 == 0)
        check(0 == 0)
    }

    @kotlin.test.Test
    public fun s2IsNonExhaustive() {
        // Without `@exhaustive`, the record should have an additional field
        // added at the end. If this test compiles, it has it.
        s2(Something, a)
        val s = Something.default()
        check(s.nonExhaustive == Unit)
    }

    @kotlin.test.Test
    public fun s2UsesExhaustive() {
        // With `@exhaustive`, the record should be regurgitated as-is. If
        // this test compiles, then it works.
        s2(exhaustive, Something, a)
    }

    @kotlin.test.Test
    public fun s2UsesMixedExhaustive() {
        // `@exhaustive` should work when sandwiched between attributes. If
        // the test compiles, then it works.
        s2(repr, align, 8, exhaustive, repr, align, 2, Something, a)
    }

    @kotlin.test.Test
    public fun s2UsesCustomDefault() {
        // A non-default value proves `customDefault` is used rather than a
        // derived default.
        s2(CustomDefault, a, customDefault, 1, 64, buf, 64)
        val s = CustomDefault.default()
        check(s.a == 0u)
        check((s.buf).contentEquals(UByteArray(64) { 1u.toUByte() }))
    }

    @kotlin.test.Test
    public fun s2KeepsFieldAttrs() {
        // If `customDefault` stripping ate the other field attributes, the two
        // `a` fields would collide.
        s2(FieldAttrs, cfg, targetArch, "x86_64", a, cfg, not, targetArch, "x86_64", a)
        val s = FieldAttrs.default()
        check(s.a == 0u.toUByte())
    }

    @kotlin.test.Test
    public fun s2SingleCfgField() {
        // This field only exists on x8664, so its default init needs the same
        // `cfg` or `Default` won't build on other arches.
        s2(SingleCfg, common, cfg, targetArch, "x86_64", x86Only)
        val s = SingleCfg.default()
        check(s.common == 0u)
        check(s.x86Only == 0uL)
    }

    @kotlin.test.Test
    public fun sNoExtraTraits2ZeroesUnion() {
        // A union field's default is supplied by
        // `customDefault(unchecked ( mem.zeroed ))`.
        sNoExtraTraits2(U, a, b, HasUnion, x, customDefault, core, mem, zeroed, U, u, U)
        val s = HasUnion.default()
        check(s.x == 0u.toUShort())
        check(s.u.a == 0u)
    }

    @kotlin.test.Test
    public fun s2KeepsStructCfg() {
        // The opposite of the configured-out types in `macroChecks`. With the
        // `cfg` true the type and its `Default` both exist, and the other
        // attributes still apply.
        s2(cfg, true, repr, align, 8, doc, " a doc comment", EnabledCfg, a, cfg, targetArch, "x86_64", x86Only, customDefault, 1, 40, buf, 40)
        val s = EnabledCfg.default()
        check(s.a == 0u)
        check((s.buf).contentEquals(UByteArray(40) { 1u.toUByte() }))
        check(alignOf<EnabledCfg>(typeLayoutT = UnresolvedTypeLayout<EnabledCfg>()) == 8)
        check(s.x86Only == 0u.toUByte())
    }

    @kotlin.test.Test
    public fun s2UnionDefault() {
        // Ensure it isn't always implemented, otherwise we would get a conflict.
        fun defaultBar(): Bar {
            error("unsupported")
        }
        sNoExtraTraits2(unionDefaultViaZeroed, Foo, a, b, expect, unused, Bar, a, b)
        val f: Foo = Foo.default()
        check(f.b == 0)
    }
}

public fun <T> assertImplsDefault() {}

public fun checkDefault() {
    assertImplsDefault<S3>()
    assertImplsDefault<S4>()
}

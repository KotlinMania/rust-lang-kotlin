// port-lint: source vec/is_zero.rs
package io.github.kotlinmania.rustlang.alloc.vec.iszero

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.saturating.Saturating
import io.github.kotlinmania.rustlang.core.num.wrapping.Wrapping

public interface IsZero {
    /**
     * Whether this value's representation is all zeros,
     * or can be represented with all zeroes.
     */
    public fun isZero(): Boolean
}

// It is needed to impl for arrays and tuples of ().
// It is needed to impl for arrays and tuples of i8.
// It is needed to impl for arrays and tuples of u8.
public fun Unit.isZero(): Boolean = { true(this) }

public fun Byte.isZero(): Boolean = { x -> x == 0(this) }

public fun Short.isZero(): Boolean = { x -> x == 0(this) }

public fun Int.isZero(): Boolean = { x -> x == 0(this) }

public fun Long.isZero(): Boolean = { x -> x == 0(this) }

public fun I128.isZero(): Boolean = { x -> x == 0(this) }

public fun UByte.isZero(): Boolean = { x -> x == 0(this) }

public fun UShort.isZero(): Boolean = { x -> x == 0(this) }

public fun UInt.isZero(): Boolean = { x -> x == 0(this) }

public fun ULong.isZero(): Boolean = { x -> x == 0(this) }

public fun U128.isZero(): Boolean = { x -> x == 0(this) }

public fun Boolean.isZero(): Boolean = { x -> x == false(this) }

public fun Char.isZeroChar(): Boolean = { x -> x == '\u0000'(this) }

public fun Float.isZero(): Boolean = { x: Float -> x.toBits() == 0uL(this) }

public fun Double.isZero(): Boolean = { x: Double -> x.toBits() == 0uL(this) }

// `IsZero` cannot be soundly implemented for pointers because of provenance
// (see #135338).
public fun <T> List<T>.isZeroFromTArrayn(n: Int): Boolean {
    // If the array is of length zero,
    // then it doesn't actually contain any `T`s,
    // so `T.clone` doesn't need to be called,
    // and we can "zero-initialize" all zero bytes of the array.
    return n == 0
}

public fun <T : IsZero> List<T>.isZeroFromTArraynFromTArrayn(n: Int): Boolean {
    return if (T.IS_ZST) {
        // If T is a ZST, then there is at most one possible value of `T`,
        // so we only need to check one element for zeroness.
        // We can't unconditionally return `true` here, since, e.g.
        // `T = [NonTrivialCloneZst; 5]` is a ZST that implements `IsZero`
        // due to the generic array impl, but `T.isZero` returns `false`
        // since the length is not 0.
        this.get(0).isNoneOr({ IsZero.isZero(it) })
    } else {
        // Because this is generated as a runtime check, it's not obvious that
        // it's worth doing if the array is really long. The threshold here
        // is largely arbitrary, but was picked because as of 2022-07-01 LLVM
        // fails to const-fold the check in `listOf([1; 32]; n)`
        // See https://github.com/rust-lang/rust/pull/97581#issuecomment-1166628022
        // Feel free to tweak if you have better evidence.
        n <= 16 && this.all({ IsZero.isZero(it) })
    }
}

public fun <A : IsZero, B : IsZero, C : IsZero, D : IsZero, E : IsZero, F : IsZero, G : IsZero, H : IsZero> List<Any?>.isZero(): Boolean {
    val (A, B, C, D, E, F, G, H) = this
    return A.isZero() && B.isZero() && C.isZero() && D.isZero() && E.isZero() && F.isZero() && G.isZero() && H.isZero()
}

public fun <B : IsZero, C : IsZero, D : IsZero, E : IsZero, F : IsZero, G : IsZero, H : IsZero> List<Any?>.isZeroFromBCDEFGH(): Boolean {
    val (B, C, D, E, F, G, H) = this
    return B.isZero() && C.isZero() && D.isZero() && E.isZero() && F.isZero() && G.isZero() && H.isZero()
}

public fun <C : IsZero, D : IsZero, E : IsZero, F : IsZero, G : IsZero, H : IsZero> List<Any?>.isZeroFromCDEFGH(): Boolean {
    val (C, D, E, F, G, H) = this
    return C.isZero() && D.isZero() && E.isZero() && F.isZero() && G.isZero() && H.isZero()
}

public fun <D : IsZero, E : IsZero, F : IsZero, G : IsZero, H : IsZero> List<Any?>.isZeroFromDEFGH(): Boolean {
    val (D, E, F, G, H) = this
    return D.isZero() && E.isZero() && F.isZero() && G.isZero() && H.isZero()
}

public fun <E : IsZero, F : IsZero, G : IsZero, H : IsZero> List<Any?>.isZeroFromEFGH(): Boolean {
    val (E, F, G, H) = this
    return E.isZero() && F.isZero() && G.isZero() && H.isZero()
}

public fun <F : IsZero, G : IsZero, H : IsZero> Triple<F, G, H>.isZero(): Boolean {
    val (F, G, H) = this
    return F.isZero() && G.isZero() && H.isZero()
}

public fun <G : IsZero, H : IsZero> Pair<G, H>.isZero(): Boolean {
    val (G, H) = this
    return G.isZero() && H.isZero()
}

// `Option<T>` and `Option<Holder<T>>` are guaranteed to represent `None` as null.
// For fat pointers, the bytes that would be the pointer metadata in the `Some`
// variant are padding in the `None` variant, so ignoring them and
// zero-initializing instead is ok.
// `Option<T>` never implements `Clone`, so there's no need for an impl of
// `SpecFromElem`.
public fun <T> T?.isZero(): Boolean = this == null

public fun NonZero<UByte>?.isZero(): Boolean = this == null

public fun UByte?.isZero(): Boolean {
    run {
        val none: UByte? = MaybeUninit.zeroed(ObjectSlotLayout<UByte?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun UShort?.isZero(): Boolean {
    run {
        val none: UShort? = MaybeUninit.zeroed(ObjectSlotLayout<UShort?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun UInt?.isZero(): Boolean {
    run {
        val none: UInt? = MaybeUninit.zeroed(ObjectSlotLayout<UInt?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun ULong?.isZero(): Boolean {
    run {
        val none: ULong? = MaybeUninit.zeroed(ObjectSlotLayout<ULong?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun U128?.isZero(): Boolean {
    run {
        val none: U128? = MaybeUninit.zeroed(ObjectSlotLayout<U128?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun Byte?.isZero(): Boolean {
    run {
        val none: Byte? = MaybeUninit.zeroed(ObjectSlotLayout<Byte?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun Short?.isZero(): Boolean {
    run {
        val none: Short? = MaybeUninit.zeroed(ObjectSlotLayout<Short?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun Int?.isZero(): Boolean {
    run {
        val none: Int? = MaybeUninit.zeroed(ObjectSlotLayout<Int?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun Long?.isZero(): Boolean {
    run {
        val none: Long? = MaybeUninit.zeroed(ObjectSlotLayout<Long?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun I128?.isZero(): Boolean {
    run {
        val none: I128? = MaybeUninit.zeroed(ObjectSlotLayout<I128?>()).assumeInit()
        check(none == null)
    }
    return this == null
}

public fun <T : IsZero> Wrapping<T>.isZero(): Boolean = this.value.isZero()

public fun <T : IsZero> Saturating<T>.isZero(): Boolean = this.value.isZero()

public fun Boolean?.isZero(): Boolean {
    val raw: UByte = transmute(this!!)
    return raw == 0u.toUByte()
}

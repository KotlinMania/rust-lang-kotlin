// port-lint: source array/equality.rs
package io.github.kotlinmania.rustlang.core.array.equality

import io.github.kotlinmania.rustlang.cmp.bytewise.BytewiseEq
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.intrinsics.rawEq

public fun <T, U> List<T>.eqUArraynFromTArrayn(other: List<U>, n: Int): Boolean = SpecArrayEq.specEq(this, other)

public fun <T, U> List<T>.neUArraynFromTArrayn(other: List<U>, n: Int): Boolean = SpecArrayEq.specNe(this, other)

public fun <T, U> List<T>.eqUSliceFromTArrayn(other: List<U>, n: Int): Boolean =
    when (val matchVal = other.asArray()) {
        null -> false
        else -> {
            val b = matchVal
            this == b
        }
    }

public fun <T, U> List<T>.neUSliceFromTArrayn(other: List<U>, n: Int): Boolean =
    when (val matchVal = other.asArray()) {
        null -> true
        else -> {
            val b = matchVal
            this != b
        }
    }

public fun <T, U> List<T>.eq(other: List<U>, n: Int): Boolean =
    when (val matchVal = this.asArray()) {
        null -> false
        else -> {
            val b = matchVal
            b == other
        }
    }

public fun <T, U> List<T>.ne(other: List<U>, n: Int): Boolean =
    when (val matchVal = this.asArray()) {
        null -> true
        else -> {
            val b = matchVal
            b != other
        }
    }

public fun <T, U> List<T>.eqUSliceFromTArraynUSliceFromTArrayn(other: List<U>, n: Int): Boolean = this == other

public fun <T, U> List<T>.neUSliceFromTArraynUSliceFromTArrayn(other: List<U>, n: Int): Boolean = this != other

public fun <T, U> List<T>.eqUArrayn(other: List<U>, n: Int): Boolean = this == other

public fun <T, U> List<T>.neUArrayn(other: List<U>, n: Int): Boolean = this != other

public fun <T, U> List<T>.eqUSliceFromTArrayn(other: MutableList<U>, n: Int): Boolean = this == other

public fun <T, U> List<T>.neUSliceFromTArrayn(other: MutableList<U>, n: Int): Boolean = this != other

public fun <T, U> MutableList<T>.eq(other: List<U>, n: Int): Boolean = this == other

public fun <T, U> MutableList<T>.ne(other: List<U>, n: Int): Boolean = this != other

// NOTE: some less important impls are omitted to reduce code bloat
// implSliceEq2 { [A; $N], &b [B; $N] }
// implSliceEq2 { [A; $N], &b mut [B; $N] }

public interface SpecArrayEq<Other, T> {
    public fun specEq(a: List<T>, b: List<Other>): Boolean

    public fun specNe(a: List<T>, b: List<Other>): Boolean
}

public fun <T, Other> specArrayEqSpecEqT(a: List<T>, b: List<Other>, n: Int): Boolean = a == b

public fun <T, Other> specArrayEqSpecNeT(a: List<T>, b: List<Other>, n: Int): Boolean = a != b

public fun <T : BytewiseEq<U>, U> specArrayEqSpecEqT(a: List<T>, b: List<U>, n: Int, typeLayoutT: TypeLayout<T>): Boolean {
    // SAFETY: Arrays are compared element-wise, and don't add any padding
    // between elements, so when the elements are `BytewiseEq`, we can
    // compare the entire array at once.
    return rawEq(a, transmute(b))
}

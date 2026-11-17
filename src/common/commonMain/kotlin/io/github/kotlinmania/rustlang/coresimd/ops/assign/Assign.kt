// port-lint: source ops/assign.rs
package io.github.kotlinmania.rustlang.coresimd.ops.assign

import io.github.kotlinmania.rustlang.coresimd.Simd

// Assignment operators

public fun <Mask, T, U> Simd<T>.addAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.add(rhs)
}

public fun <Mask, T, U> Simd<T>.mulAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.mul(rhs)
}

public fun <Mask, T, U> Simd<T>.subAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.sub(rhs)
}

public fun <Mask, T, U> Simd<T>.divAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.div(rhs)
}

public fun <Mask, T, U> Simd<T>.remAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.rem(rhs)
}

public fun <Mask, T, U> Simd<T>.bitandAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.bitand(rhs)
}

public fun <Mask, T, U> Simd<T>.bitorAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.bitor(rhs)
}

public fun <Mask, T, U> Simd<T>.bitxorAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.bitxor(rhs)
}

public fun <Mask, T, U> Simd<T>.shlAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.shl(rhs)
}

public fun <Mask, T, U> Simd<T>.shrAssign(rhs: U, n: Int) {
    var selfMut = this
    selfMut = selfMut.shr(rhs)
}

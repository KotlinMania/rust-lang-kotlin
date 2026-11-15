// port-lint: source ops/assign.rs
package io.github.kotlinmania.rustlang.coresimd.ops.assign

import io.github.kotlinmania.rustlang.Simd

// Assignment operators

public fun <Mask, T, U> Simd<T, N>.addAssign(rhs: U, n: Int) {
    this = this.add(rhs)
}

public fun <Mask, T, U> Simd<T, N>.mulAssign(rhs: U, n: Int) {
    this = this.mul(rhs)
}

public fun <Mask, T, U> Simd<T, N>.subAssign(rhs: U, n: Int) {
    this = this.sub(rhs)
}

public fun <Mask, T, U> Simd<T, N>.divAssign(rhs: U, n: Int) {
    this = this.div(rhs)
}

public fun <Mask, T, U> Simd<T, N>.remAssign(rhs: U, n: Int) {
    this = this.rem(rhs)
}

public fun <Mask, T, U> Simd<T, N>.bitandAssign(rhs: U, n: Int) {
    this = this.bitand(rhs)
}

public fun <Mask, T, U> Simd<T, N>.bitorAssign(rhs: U, n: Int) {
    this = this.bitor(rhs)
}

public fun <Mask, T, U> Simd<T, N>.bitxorAssign(rhs: U, n: Int) {
    this = this.bitxor(rhs)
}

public fun <Mask, T, U> Simd<T, N>.shlAssign(rhs: U, n: Int) {
    this = this.shl(rhs)
}

public fun <Mask, T, U> Simd<T, N>.shrAssign(rhs: U, n: Int) {
    this = this.shr(rhs)
}

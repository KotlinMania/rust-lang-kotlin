// port-lint: source ops/assign.rs
package io.github.kotlinmania.rustlang.coresimd.ops.assign

import io.github.kotlinmania.rustlang.coresimd.Simd

// Assignment operators

public fun <Mask, T, U> Simd<T>.addAssign(rhs: U, n: Int) {
    this = this.add(rhs)
}

public fun <Mask, T, U> Simd<T>.mulAssign(rhs: U, n: Int) {
    this = this.mul(rhs)
}

public fun <Mask, T, U> Simd<T>.subAssign(rhs: U, n: Int) {
    this = this.sub(rhs)
}

public fun <Mask, T, U> Simd<T>.divAssign(rhs: U, n: Int) {
    this = this.div(rhs)
}

public fun <Mask, T, U> Simd<T>.remAssign(rhs: U, n: Int) {
    this = this.rem(rhs)
}

public fun <Mask, T, U> Simd<T>.bitandAssign(rhs: U, n: Int) {
    this = this.bitand(rhs)
}

public fun <Mask, T, U> Simd<T>.bitorAssign(rhs: U, n: Int) {
    this = this.bitor(rhs)
}

public fun <Mask, T, U> Simd<T>.bitxorAssign(rhs: U, n: Int) {
    this = this.bitxor(rhs)
}

public fun <Mask, T, U> Simd<T>.shlAssign(rhs: U, n: Int) {
    this = this.shl(rhs)
}

public fun <Mask, T, U> Simd<T>.shrAssign(rhs: U, n: Int) {
    this = this.shr(rhs)
}

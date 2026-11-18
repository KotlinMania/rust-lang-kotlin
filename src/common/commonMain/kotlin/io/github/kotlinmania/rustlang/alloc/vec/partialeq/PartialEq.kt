// port-lint: source vec/partial_eq.rs
package io.github.kotlinmania.rustlang.alloc.vec.partialeq

import io.github.kotlinmania.rustlang.core.alloc.Allocator

public fun <T, U, A1 : Allocator, A2 : Allocator> List<T>.eq(other: List<U>): Boolean = this == other

public fun <T, U, A1 : Allocator, A2 : Allocator> List<T>.ne(other: List<U>): Boolean = this != other

public fun <T, U, A : Allocator> List<T>.eqUSlice(other: List<U>): Boolean = this == other

public fun <T, U, A : Allocator> List<T>.neUSlice(other: List<U>): Boolean = this != other

public fun <T, U, A : Allocator> List<T>.eq(other: MutableList<U>): Boolean = this == other

public fun <T, U, A : Allocator> List<T>.ne(other: MutableList<U>): Boolean = this != other

public fun <T, U, A : Allocator> List<T>.eqVecUA(other: List<U>): Boolean = this == other

public fun <T, U, A : Allocator> List<T>.neVecUA(other: List<U>): Boolean = this != other

public fun <T, U, A : Allocator> MutableList<T>.eq(other: List<U>): Boolean = this == other

public fun <T, U, A : Allocator> MutableList<T>.ne(other: List<U>): Boolean = this != other

public fun <T, U> List<T>.eqUSlice(other: MutableList<U>): Boolean = this == other

public fun <T, U> List<T>.neUSlice(other: MutableList<U>): Boolean = this != other

public fun <T, U, A : Allocator> List<T>.eqCowUSlice(other: List<U>): Boolean = this == other

public fun <T, U, A : Allocator> List<T>.neCowUSlice(other: List<U>): Boolean = this != other

public fun <T, U> MutableList<T>.eqCowUSlice(other: List<U>): Boolean = this == other

public fun <T, U> MutableList<T>.neCowUSlice(other: List<U>): Boolean = this != other

public fun <T, U, A : Allocator> List<T>.eq(other: List<U>, n: Int = 0): Boolean = this == other

public fun <T, U, A : Allocator> List<T>.ne(other: List<U>, n: Int = 0): Boolean = this != other

public fun <T, U, A : Allocator> List<T>.eqUArrayn(other: List<U>, n: Int = 0): Boolean = this == other

public fun <T, U, A : Allocator> List<T>.neUArrayn(other: List<U>, n: Int = 0): Boolean = this != other

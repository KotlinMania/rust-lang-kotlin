// port-lint: source vec/cow.rs
package io.github.kotlinmania.rustlang.alloc.vec.cow

/**
 * Creates a [Borrowed] variant of [Cow]
 * from a slice.
 *
 * This conversion does not allocate or clone the data.
 */
public fun <T> fromCowTSlice(s: List<T>): List<T> = s

/**
 * Creates a [Borrowed] variant of [Cow]
 * from a reference to an array.
 *
 * This conversion does not allocate or clone the data.
 */
public fun <T> fromCowTSlice(s: List<T>, n: Int): List<T> = ((s as Any?) as List<Any?>)

public fun <T, I : Iterable<T>> fromIteratorFromIterCowTSlice(it: I): List<T> = FromIterator.fromIter(it)

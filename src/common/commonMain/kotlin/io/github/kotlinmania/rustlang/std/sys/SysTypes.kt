package io.github.kotlinmania.rustlang.std.sys

import io.github.kotlinmania.rustlang.std.backtrace.Inner

/**
 * A trait for viewing representations from std types.
 */
internal interface AsInner<Inner> {
    public fun asInner(): Inner
}

/**
 * A trait for viewing representations from std types.
 */
internal interface AsInnerMut<Inner> {
    public fun asInnerMut(): Inner
}

/**
 * A trait for extracting representations from std types.
 */
internal interface IntoInner<Inner> {
    public fun intoInner(): Inner
}

/**
 * A trait for creating std types from internal representations.
 */
internal interface FromInner<Inner, T> {
    public fun fromInner(inner: Inner): T
}

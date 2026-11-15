package io.github.kotlinmania.rustlang.std.sys

import io.github.kotlinmania.rustlang.backtrace.Inner

/**
 * A trait for viewing representations from std types.
 */
public interface AsInner<Inner> {
    public fun asInner(): Inner
}

/**
 * A trait for viewing representations from std types.
 */
public interface AsInnerMut<Inner> {
    public fun asInnerMut(): Inner
}

/**
 * A trait for extracting representations from std types.
 */
public interface IntoInner<Inner> {
    public fun intoInner(): Inner
}

/**
 * A trait for creating std types from internal representations.
 */
public interface FromInner<Inner, T> {
    public fun fromInner(inner: Inner): T
}

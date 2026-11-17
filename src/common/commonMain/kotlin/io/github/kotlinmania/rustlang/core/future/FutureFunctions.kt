package io.github.kotlinmania.rustlang.core.future

import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.task.wake.Context

public fun getContext(cx: ResumeTy): Context {
    // SAFETY: the caller must guarantee that `cx.0` is a valid pointer
    // that fulfills all the requirements for a mutable reference.
    return cx.value.asPtr().cast()
}

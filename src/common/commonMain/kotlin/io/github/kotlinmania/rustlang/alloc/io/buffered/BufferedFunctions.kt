package io.github.kotlinmania.rustlang.alloc.io.buffered

import io.github.kotlinmania.rustlang.core.io.error.Error

public fun <W> fromError(iie: IntoInnerError<W>): Error = iie.field1

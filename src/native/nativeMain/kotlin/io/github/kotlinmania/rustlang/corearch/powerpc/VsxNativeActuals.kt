@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.powerpc.VectorSignedInt
import io.github.kotlinmania.rustlang.powerpc.VectorUnsignedChar
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun vperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt =
    VectorSignedInt()

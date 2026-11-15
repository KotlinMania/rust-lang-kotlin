@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import kotlinx.cinterop.ExperimentalForeignApi

public actual fun aesenc256(a: M256i, roundKey: M256i): M256i =
    M256i()

public actual fun aesenclast256(a: M256i, roundKey: M256i): M256i =
    M256i()

public actual fun aesdec256(a: M256i, roundKey: M256i): M256i =
    M256i()

public actual fun aesdeclast256(a: M256i, roundKey: M256i): M256i =
    M256i()

public actual fun aesenc512(a: M512i, roundKey: M512i): M512i =
    M512i()

public actual fun aesenclast512(a: M512i, roundKey: M512i): M512i =
    M512i()

public actual fun aesdec512(a: M512i, roundKey: M512i): M512i =
    M512i()

public actual fun aesdeclast512(a: M512i, roundKey: M512i): M512i =
    M512i()

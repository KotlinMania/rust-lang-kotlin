@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

public actual fun bcstnebf162ps128(a: ConstPtr): M128 =
    M128()

public actual fun bcstnebf162ps256(a: ConstPtr): M256 =
    M256()

public actual fun bcstnesh2ps128(a: ConstPtr): M128 =
    M128()

public actual fun bcstnesh2ps256(a: ConstPtr): M256 =
    M256()

public actual fun cvtneebf162ps128(a: ConstPtr): M128 =
    M128()

public actual fun cvtneebf162ps256(a: ConstPtr): M256 =
    M256()

public actual fun cvtneeph2ps128(a: ConstPtr): M128 =
    M128()

public actual fun cvtneeph2ps256(a: ConstPtr): M256 =
    M256()

public actual fun cvtneobf162ps128(a: ConstPtr): M128 =
    M128()

public actual fun cvtneobf162ps256(a: ConstPtr): M256 =
    M256()

public actual fun cvtneoph2ps128(a: ConstPtr): M128 =
    M128()

public actual fun cvtneoph2ps256(a: ConstPtr): M256 =
    M256()

public actual fun vcvtneps2bf16128(a: M128): M128bh =
    M128bh()

public actual fun vcvtneps2bf16256(a: M256): M128bh =
    M128bh()

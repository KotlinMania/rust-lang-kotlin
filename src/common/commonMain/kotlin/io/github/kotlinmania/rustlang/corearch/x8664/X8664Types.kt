package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.simd.Simd

// Any 1024-byte vector should work
public typealias Tile = Simd<UByte>

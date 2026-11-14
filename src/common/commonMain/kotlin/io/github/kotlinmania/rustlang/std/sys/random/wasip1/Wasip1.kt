// port-lint: source sys/random/wasip1.rs
package io.github.kotlinmania.rustlang.std.sys.random.wasip1

import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.wasip1.randomGet

public fun fillBytes(bytes: MutableList<UByte>) {
    randomGet(bytes.asMutPtr(), bytes.size).expect("failed to generate random data")
}

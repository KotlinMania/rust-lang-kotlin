// port-lint: source sys/random/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.random.unsupported

public fun fillBytes(arg0: MutableList<UByte>) {
    error("this target does not support random data generation")
}

public fun hashmapRandomKeys(): Pair<ULong, ULong> {
    // Use allocation addresses for a bit of randomness. This isn't
    // particularly secure, but there isn't really an alternative.
    val stack = 0u.toUByte()
    val heap = 0u.toUByte()
    val k1 = stack.addr().toULong()
    val k2 = heap.addr().toULong()
    return Pair(k1, k2)
}

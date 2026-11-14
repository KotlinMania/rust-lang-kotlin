// port-lint: source sys/random/wasi.rs
package io.github.kotlinmania.rustlang.std.sys.random.wasi

import io.github.kotlinmania.rustlang.std.wasip2.random.random.getRandomBytes
import io.github.kotlinmania.wasip3.random.insecureseed.getInsecureSeed

public fun fillBytes(bytes: MutableList<UByte>) {
    run { val dstTmp0 = bytes; val srcTmp0 = getRandomBytes(ULong.tryFrom(bytes.size).unwrap()); for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
}

public fun hashmapRandomKeys(): Pair<ULong, ULong> = getInsecureSeed()

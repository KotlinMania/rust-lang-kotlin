// port-lint: source sys/random/unix_legacy.rs
package io.github.kotlinmania.rustlang.std.sys.random.unixlegacy

import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.fs.File
import io.github.kotlinmania.rustlang.sync.oncelock.OnceLock

// Random data from `/ dev / urandom`
//
// Before `getentropy` was standardized in 2024, UNIX didn't have a standardized
// way of getting random data, so systems just followed the precedent set by
// Linux and exposed random devices at `/ dev / random` and `/ dev / urandom`. Thus,
// for the few systems that support neither `arc4randomBuf` nor `getentropy`
// yet, we just read from the file.

public var DEVICE: OnceLock<File> = OnceLock.new()

public fun fillBytes(bytes: MutableList<UByte>) {
    DEVICE.getOrTryInit({ File.open("/dev/urandom") }).andThen({ dev -> dev.readExact(bytes) }).expect("failed to generate random data")
}

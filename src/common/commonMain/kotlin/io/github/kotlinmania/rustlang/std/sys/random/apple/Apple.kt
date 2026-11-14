// port-lint: source sys/random/apple.rs
package io.github.kotlinmania.rustlang.std.sys.random.apple

import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.libc.CCRandomGenerateBytes
import io.github.kotlinmania.rustlang.libc.kCCSuccess

// Random data on Apple platforms.
//
// `CCRandomGenerateBytes` calls into `CCRandomCopyBytes` with `kCCRandomDefault`.
// `CCRandomCopyBytes` manages a CSPRNG which is seeded from the kernel's CSPRNG.
// We use `CCRandomGenerateBytes` instead of `SecCopyBytes` because it is accessible via
// `libSystem` (libc) while the other needs to link to `Security.framework`.
//
// Note that technically, `arc4randomBuf` is available as well, but that calls
// into the same system service anyway, and `CCRandomGenerateBytes` has been
// proven to be App Store-compatible.

public fun fillBytes(bytes: MutableList<UByte>) {
    val ret = CCRandomGenerateBytes(bytes.asMutPtr(), bytes.size)
    check(ret == kCCSuccess)
}

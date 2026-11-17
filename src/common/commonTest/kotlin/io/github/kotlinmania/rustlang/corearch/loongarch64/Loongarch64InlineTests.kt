package io.github.kotlinmania.rustlang.corearch.loongarch64

// `LoongArch64` intrinsics

public class Loongarch64InlineTests {
    @kotlin.test.Test
    public fun crc32() {
        check(crcWBW((-1).toByte(), -1) == 16777215)
        check(crcWHW((-1).toShort(), -1) == 65535)
        check(crcWWW(-1, -1) == 0)
        check(crcWDW(-1L, -1) == 3736805603u.castSigned())
        check(crccWBW((-1).toByte(), -1) == 16777215)
        check(crccWHW((-1).toShort(), -1) == 65535)
        check(crccWWW(-1, -1) == 0)
        check(crccWDW(-1L, -1) == 3080238136u.castSigned())
    }
}

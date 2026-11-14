// port-lint: tests x86_64/bt.rs
package io.github.kotlinmania.rustlang.corearch.x8664

public class BtInlineTests {
    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittest64() {
        run {
            val a = 0x50L
            check(bittest64(((a as Any?) as Unit), 4L) == 1u.toUByte())
            check(bittest64(((a as Any?) as Unit), 5L) == 0u.toUByte())
        }
    }

    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittestandset64() {
        run {
            val a = 0x50L
            check(bittestandset64(((a as Any?) as Unit), 4L) == 1u.toUByte())
            check(bittestandset64(((a as Any?) as Unit), 4L) == 1u.toUByte())
            check(bittestandset64(((a as Any?) as Unit), 5L) == 0u.toUByte())
            check(bittestandset64(((a as Any?) as Unit), 5L) == 1u.toUByte())
        }
    }

    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittestandreset64() {
        run {
            val a = 0x50L
            check(bittestandreset64(((a as Any?) as Unit), 4L) == 1u.toUByte())
            check(bittestandreset64(((a as Any?) as Unit), 4L) == 0u.toUByte())
            check(bittestandreset64(((a as Any?) as Unit), 5L) == 0u.toUByte())
            check(bittestandreset64(((a as Any?) as Unit), 5L) == 0u.toUByte())
        }
    }

    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittestandcomplement64() {
        run {
            val a = 0x50L
            check(bittestandcomplement64(((a as Any?) as Unit), 4L) == 1u.toUByte())
            check(bittestandcomplement64(((a as Any?) as Unit), 4L) == 0u.toUByte())
            check(bittestandcomplement64(((a as Any?) as Unit), 4L) == 1u.toUByte())
            check(bittestandcomplement64(((a as Any?) as Unit), 5L) == 0u.toUByte())
            check(bittestandcomplement64(((a as Any?) as Unit), 5L) == 1u.toUByte())
        }
    }
}

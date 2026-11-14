// port-lint: tests x86/bt.rs
package io.github.kotlinmania.rustlang.corearch.x86

public class BtInlineTests {
    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittest() {
        run {
            val a = 0x50
            check(bittest(((a as Any?) as Unit), 4) == 1u.toUByte())
            check(bittest(((a as Any?) as Unit), 5) == 0u.toUByte())
        }
    }

    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittestandset() {
        run {
            val a = 0x50
            check(bittestandset(((a as Any?) as Unit), 4) == 1u.toUByte())
            check(bittestandset(((a as Any?) as Unit), 4) == 1u.toUByte())
            check(bittestandset(((a as Any?) as Unit), 5) == 0u.toUByte())
            check(bittestandset(((a as Any?) as Unit), 5) == 1u.toUByte())
        }
    }

    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittestandreset() {
        run {
            val a = 0x50
            check(bittestandreset(((a as Any?) as Unit), 4) == 1u.toUByte())
            check(bittestandreset(((a as Any?) as Unit), 4) == 0u.toUByte())
            check(bittestandreset(((a as Any?) as Unit), 5) == 0u.toUByte())
            check(bittestandreset(((a as Any?) as Unit), 5) == 0u.toUByte())
        }
    }

    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittestandcomplement() {
        run {
            val a = 0x50
            check(bittestandcomplement(((a as Any?) as Unit), 4) == 1u.toUByte())
            check(bittestandcomplement(((a as Any?) as Unit), 4) == 0u.toUByte())
            check(bittestandcomplement(((a as Any?) as Unit), 4) == 1u.toUByte())
            check(bittestandcomplement(((a as Any?) as Unit), 5) == 0u.toUByte())
            check(bittestandcomplement(((a as Any?) as Unit), 5) == 1u.toUByte())
        }
    }
}

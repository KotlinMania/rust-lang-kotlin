// port-lint: tests x86/bt.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public class BtInlineTests {
    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittest() {
        run {
            val a = 0x50
            check(bittest(ConstPtr(a.toLong()), 4) == 1u.toUByte())
            check(bittest(ConstPtr(a.toLong()), 5) == 0u.toUByte())
        }
    }

    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittestandset() {
        run {
            val a = 0x50
            check(bittestandset(MutPtr(a.toLong()), 4) == 1u.toUByte())
            check(bittestandset(MutPtr(a.toLong()), 4) == 1u.toUByte())
            check(bittestandset(MutPtr(a.toLong()), 5) == 0u.toUByte())
            check(bittestandset(MutPtr(a.toLong()), 5) == 1u.toUByte())
        }
    }

    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittestandreset() {
        run {
            val a = 0x50
            check(bittestandreset(MutPtr(a.toLong()), 4) == 1u.toUByte())
            check(bittestandreset(MutPtr(a.toLong()), 4) == 0u.toUByte())
            check(bittestandreset(MutPtr(a.toLong()), 5) == 0u.toUByte())
            check(bittestandreset(MutPtr(a.toLong()), 5) == 0u.toUByte())
        }
    }

    // Uses inline assembly
    @kotlin.test.Test
    public fun testBittestandcomplement() {
        run {
            val a = 0x50
            check(bittestandcomplement(MutPtr(a.toLong()), 4) == 1u.toUByte())
            check(bittestandcomplement(MutPtr(a.toLong()), 4) == 0u.toUByte())
            check(bittestandcomplement(MutPtr(a.toLong()), 4) == 1u.toUByte())
            check(bittestandcomplement(MutPtr(a.toLong()), 5) == 0u.toUByte())
            check(bittestandcomplement(MutPtr(a.toLong()), 5) == 1u.toUByte())
        }
    }
}

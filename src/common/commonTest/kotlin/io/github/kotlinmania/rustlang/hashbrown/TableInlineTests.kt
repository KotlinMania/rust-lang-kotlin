// port-lint: tests table.rs
package io.github.kotlinmania.rustlang.hashbrown

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.sizeOf

public class TableInlineTests {
    @kotlin.test.Test
    public fun testAllocationInfo() {
        check(HashTable.new().allocationSize() == 0)
        check(HashTable.new().allocationSize() == 0)
        check(HashTable.withCapacity(1).allocationSize() > sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32))
    }
}

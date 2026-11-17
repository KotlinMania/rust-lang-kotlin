// port-lint: source int/udiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.udiv

import io.github.kotlinmania.rustlang.compilerbuiltins.int.specializeddivrem.u128DivideSparc
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write

public fun udivsi3(n: UInt, d: UInt): UInt {
    fun udivsi3(n1: UInt, d1: UInt): UInt = 0u
    return udivsi3(n, d)
}

public fun umodsi3(n: UInt, d: UInt): UInt {
    fun umodsi3(n1: UInt, d1: UInt): UInt = 0u
    return umodsi3(n, d)
}

public fun udivmodsi4(n: UInt, d: UInt, rem: RustMutableReference<UInt>?): UInt {
    fun udivmodsi4(n1: UInt, d1: UInt, rem1: RustMutableReference<UInt>?): UInt = 0u
    return udivmodsi4(n, d, rem)
}

public fun udivdi3(n: ULong, d: ULong): ULong {
    fun udivdi3(n1: ULong, d1: ULong): ULong = 0uL
    return udivdi3(n, d)
}

public fun umoddi3(n: ULong, d: ULong): ULong {
    fun umoddi3(n1: ULong, d1: ULong): ULong = 0uL
    return umoddi3(n, d)
}

public fun udivmoddi4(n: ULong, d: ULong, rem: RustMutableReference<ULong>?): ULong {
    fun udivmoddi4(n1: ULong, d1: ULong, rem1: RustMutableReference<ULong>?): ULong = 0uL
    return udivmoddi4(n, d, rem)
}

/**
 * Returns `n / d`
 */
public fun udivti3(n: U128, d: U128): U128 = u128DivideSparc(n, d, RustMutableReference(allocateNativeCell(0, PrimitiveLayouts.I32), PrimitiveLayouts.I32))

/**
 * Returns `n % d`
 */
public fun umodti3(n: U128, d: U128): U128 =
    run {
        val localStorageL175C12 = AddressableLocal(0, PrimitiveLayouts.I32)
        val rem by localStorageL175C12
        u128DivideSparc(n, d, RustMutableReference(localStorageL175C12.asMutPtr(), localStorageL175C12.layout))
        rem
    }

/**
 * Returns `n / d` and sets `* rem = n % d`
 */
public fun udivmodti4(n: U128, d: U128, rem: RustMutableReference<U128>?): U128 {
    val rem1 = rem
    return run {
        val localStorageL191C12 = AddressableLocal(0, PrimitiveLayouts.I32)
        val tmp by localStorageL191C12
        val quo = u128DivideSparc(n, d, RustMutableReference(localStorageL191C12.asMutPtr(), localStorageL191C12.layout))
        if (rem1 != null) {
            val rem2 = rem1
            (rem2).write(tmp)
        }
        quo
    }
}

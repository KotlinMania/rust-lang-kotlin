// port-lint: source int/addsub.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.addsub

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.DInt
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

public interface UAddSub<T> : DInt, Int {
    public fun uadd(other: T): T

    public fun uaddOne(): T

    public fun usub(other: T): T {
        val uneg = (!other).uaddOne()
        return this.uadd(uneg)
    }
}

public interface AddSub<T> : Int {
    public fun add(other: T): T

    public fun sub(other: T): T
}

public interface Addo<T> : AddSub<T> {
    public fun addo(other: T): Pair<T, Boolean> {
        val sum = this.add(other)
        return Pair(sum, ((kmCompareValues(other, T.ZERO) < 0)) != ((kmCompareValues(sum, this) < 0)))
    }
}

public interface Subo<T> : AddSub<T> {
    public fun subo(other: T): Pair<T, Boolean> {
        val sum = this.sub(other)
        return Pair(sum, ((kmCompareValues(other, T.ZERO) < 0)) != ((kmCompareValues(this, sum) < 0)))
    }
}

public fun rustI128Add(a: I128, b: I128): I128 = a.add(b)

public fun rustI128Addo(a: I128, b: I128, oflow: RustMutableReference<Int>): I128 {
    val oflow1 = oflow
    val (add, o) = a.addo(b)
    (oflow1).write(o.into())
    return add
}

public fun rustU128Add(a: U128, b: U128): U128 = a.add(b)

public fun rustU128Addo(a: U128, b: U128, oflow: RustMutableReference<Int>): U128 {
    val oflow1 = oflow
    val (add, o) = a.addo(b)
    (oflow1).write(o.into())
    return add
}

public fun rustI128Sub(a: I128, b: I128): I128 = a.sub(b)

public fun rustI128Subo(a: I128, b: I128, oflow: RustMutableReference<Int>): I128 {
    val oflow1 = oflow
    val (sub, o) = a.subo(b)
    (oflow1).write(o.into())
    return sub
}

public fun rustU128Sub(a: U128, b: U128): U128 = a.sub(b)

public fun rustU128Subo(a: U128, b: U128, oflow: RustMutableReference<Int>): U128 {
    val oflow1 = oflow
    val (sub, o) = a.subo(b)
    (oflow1).write(o.into())
    return sub
}

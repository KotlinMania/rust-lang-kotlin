package io.github.kotlinmania.rustlang.core.iter.adapters

import io.github.kotlinmania.rustlang.core.ops.trytrait.ChangeOutputType
import io.github.kotlinmania.rustlang.core.ops.trytrait.FromResidual
import io.github.kotlinmania.rustlang.core.ops.trytrait.Residual
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try

/**
 * Process the given iterator as if it yielded the item's `Try.Output`
 * type instead. Any `Try.Residual`s encountered will stop the inner iterator
 * and be propagated back to the overall result.
 */
public fun <TryType, I, T, R, U> tryProcess(iter: I, f: (GenericShunt<I, R>) -> U): ChangeOutputType<Item, U> where R : Residual<U, TryType> {
    // Note(#11084): we might be able to get rid of GenericShunt in favor of
    // Iterator.scan, as performance should be comparable
    var residual = null
    val shunt = GenericShunt(iter = iter, residual = residual)
    val value = f(shunt)
    return when (residual) {
        null -> Try.fromOutput(value)
        else -> {
            val r = residual
            FromResidual.FromResidual(r)
        }
    }
}

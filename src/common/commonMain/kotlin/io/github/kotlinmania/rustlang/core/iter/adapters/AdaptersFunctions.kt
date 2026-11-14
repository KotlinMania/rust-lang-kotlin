package io.github.kotlinmania.rustlang.core.iter.adapters

import io.github.kotlinmania.rustlang.core.ops.ChangeOutputType
import io.github.kotlinmania.rustlang.core.ops.FromResidual
import io.github.kotlinmania.rustlang.core.ops.Residual

/**
 * Process the given iterator as if it yielded the item's `Try.Output`
 * type instead. Any `Try.Residual`s encountered will stop the inner iterator
 * and be propagated back to the overall result.
 */
internal fun <I, T, R, U> tryProcess(iter: I, f: (GenericShunt<I, R>) -> U): ChangeOutputType<Item, U> where R : Residual<U, Any?> {
    // Note(#11084): we might be able to get rid of GenericShunt in favor of
    // Iterator.scan, as performance should be comparable
    var residual = null
    val shunt = GenericShunt(iter = iter, residual = residual)
    val value = f(shunt)
    return when (residual) {
        null -> Try.fromOutput(value)
        else -> {
            val r = residual
            FromResidual.fromResidual(r)
        }
    }
}

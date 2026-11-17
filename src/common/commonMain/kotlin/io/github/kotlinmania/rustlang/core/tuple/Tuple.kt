// port-lint: source tuple.rs
package io.github.kotlinmania.rustlang.core.tuple

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.*
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.mem.component10
import io.github.kotlinmania.rustlang.core.mem.component11
import io.github.kotlinmania.rustlang.core.mem.component12
import io.github.kotlinmania.rustlang.core.mem.component6
import io.github.kotlinmania.rustlang.core.mem.component7
import io.github.kotlinmania.rustlang.core.mem.component8
import io.github.kotlinmania.rustlang.core.mem.component9
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.kmPartialCompareValues
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue

/**
 * This trait is implemented for tuples up to twelve items long.
 */

/**
 * This trait is implemented for tuples up to twelve items long.
 */
public fun <T> fromT(array: List<T>): T {
    val T = array
    return T
}

/**
 * This trait is implemented for tuples up to twelve items long.
 */
public fun <T> fromTArray1(tuple: T): List<T> {
    val T = tuple
    return listOf(T)
}

public fun <U, T> Pair<U, T>.partialCmp(other: Pair<U, T>): Ordering? =
    when (val matchVal = kmPartialCompareValues((this.first), other.first)?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> kmPartialCompareValues((this.second), other.second)?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
        else -> {
            val ordering = matchVal
            ordering
        }
    }

public fun <U, T> Pair<U, T>.lt(other: Pair<U, T>): Boolean =
    when (val matchVal = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            (kmPartialCompareValues(this.second, other.second)?.let { cmpSign -> cmpSign < 0 } == true)
        }
    }

public fun <U, T> Pair<U, T>.le(other: Pair<U, T>): Boolean =
    when (val matchVal = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            (kmPartialCompareValues(this.second, other.second)?.let { cmpSign -> cmpSign <= 0 } == true)
        }
    }

public fun <U, T> Pair<U, T>.ge(other: Pair<U, T>): Boolean =
    when (val matchVal = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            (kmPartialCompareValues(this.second, other.second)?.let { cmpSign -> cmpSign >= 0 } == true)
        }
    }

public fun <U, T> Pair<U, T>.gt(other: Pair<U, T>): Boolean =
    when (val matchVal = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            (kmPartialCompareValues(this.second, other.second)?.let { cmpSign -> cmpSign > 0 } == true)
        }
    }

public fun <U, T> Pair<U, T>.chainingLess(other: Pair<U, T>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        Continue(Unit)
    }
}

public fun <U, T> Pair<U, T>.chainingGreater(other: Pair<U, T>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        Continue(Unit)
    }
}

public fun <U, T> Pair<U, T>.cmp(other: Pair<U, T>): Ordering =
    when (val matchVal = kmCompareValues((this.first), other.first).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> kmCompareValues((this.second), other.second).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
        else -> {
            val ordering = matchVal
            ordering
        }
    }

public fun <U, T> defaultUT(defaultUFn: () -> U, defaultTFn: () -> T): Pair<U, T> =
    Pair(run {
        val x: U = defaultUFn()
        x
    }, run {
        val x1: T = defaultTFn()
        x1
    })

public fun <T> fromTT(array: List<T>): Pair<T, T> {
    val (U, T) = array
    return Pair(U, T)
}

public fun <T> fromTArray2(tuple: Pair<T, T>): List<T> {
    val (U, T) = tuple
    return listOf(U, T)
}

public fun <V, U, T> Triple<V, U, T>.partialCmp(other: Triple<V, U, T>): Ordering? =
    when (val matchVal = kmPartialCompareValues((this.first), other.first)?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues((this.second), other.second)?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> kmPartialCompareValues((this.third), other.third)?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
            else -> {
                val ordering = matchVal1
                ordering
            }
        }
        else -> {
            val ordering1 = matchVal
            ordering1
        }
    }

public fun <V, U, T> Triple<V, U, T>.lt(other: Triple<V, U, T>): Boolean =
    when (val matchVal = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    (kmPartialCompareValues(this.third, other.third)?.let { cmpSign -> cmpSign < 0 } == true)
                }
            }
        }
    }

public fun <V, U, T> Triple<V, U, T>.le(other: Triple<V, U, T>): Boolean =
    when (val matchVal = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    (kmPartialCompareValues(this.third, other.third)?.let { cmpSign -> cmpSign <= 0 } == true)
                }
            }
        }
    }

public fun <V, U, T> Triple<V, U, T>.ge(other: Triple<V, U, T>): Boolean =
    when (val matchVal = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    (kmPartialCompareValues(this.third, other.third)?.let { cmpSign -> cmpSign >= 0 } == true)
                }
            }
        }
    }

public fun <V, U, T> Triple<V, U, T>.gt(other: Triple<V, U, T>): Boolean =
    when (val matchVal = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    (kmPartialCompareValues(this.third, other.third)?.let { cmpSign -> cmpSign > 0 } == true)
                }
            }
        }
    }

public fun <V, U, T> Triple<V, U, T>.chainingLess(other: Triple<V, U, T>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues(this.third, other.third).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues(this.third, other.third).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        Continue(Unit)
    }
}

public fun <V, U, T> Triple<V, U, T>.chainingGreater(other: Triple<V, U, T>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues(this.first, other.first).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues(this.second, other.second).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues(this.third, other.third).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues(this.third, other.third).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        Continue(Unit)
    }
}

public fun <V, U, T> Triple<V, U, T>.cmp(other: Triple<V, U, T>): Ordering =
    when (val matchVal = kmCompareValues((this.first), other.first).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues((this.second), other.second).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> kmCompareValues((this.third), other.third).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
            else -> {
                val ordering = matchVal1
                ordering
            }
        }
        else -> {
            val ordering1 = matchVal
            ordering1
        }
    }

public fun <V, U, T> defaultVUT(defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): Triple<V, U, T> =
    Triple(run {
        val x: V = defaultVFn()
        x
    }, run {
        val x1: U = defaultUFn()
        x1
    }, run {
        val x2: T = defaultTFn()
        x2
    })

public fun <T> fromTTT(array: List<T>): Triple<T, T, T> {
    val (V, U, T) = array
    return Triple(V, U, T)
}

public fun <T> fromTArray3(tuple: Triple<T, T, T>): List<T> {
    val (V, U, T) = tuple
    return listOf(V, U, T)
}

public fun <W, V, U, T> List<Any?>.partialCmp(other: List<Any?>): Ordering? =
    when (val matchVal = kmPartialCompareValues(((this[0] as W)), (other[0] as W))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues(((this[1] as V)), (other[1] as V))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmPartialCompareValues(((this[2] as U)), (other[2] as U))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> kmPartialCompareValues(((this[3] as T)), (other[3] as T))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                else -> {
                    val ordering = matchVal2
                    ordering
                }
            }
            else -> {
                val ordering1 = matchVal1
                ordering1
            }
        }
        else -> {
            val ordering2 = matchVal
            ordering2
        }
    }

public fun <W, V, U, T> List<Any?>.lt(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as W), (other[0] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as V), (other[1] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as U), (other[2] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            (kmPartialCompareValues((this[3] as T), (other[3] as T))?.let { cmpSign -> cmpSign < 0 } == true)
                        }
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.le(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as W), (other[0] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as V), (other[1] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as U), (other[2] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            (kmPartialCompareValues((this[3] as T), (other[3] as T))?.let { cmpSign -> cmpSign <= 0 } == true)
                        }
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.ge(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as W), (other[0] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as V), (other[1] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as U), (other[2] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            (kmPartialCompareValues((this[3] as T), (other[3] as T))?.let { cmpSign -> cmpSign >= 0 } == true)
                        }
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.gt(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as W), (other[0] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as V), (other[1] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as U), (other[2] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            (kmPartialCompareValues((this[3] as T), (other[3] as T))?.let { cmpSign -> cmpSign > 0 } == true)
                        }
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.chainingLess(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as W), (other[0] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as W), (other[0] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as V), (other[1] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as V), (other[1] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as U), (other[2] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as U), (other[2] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as T), (other[3] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as T), (other[3] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r3
        }
        Continue(Unit)
    }
}

public fun <W, V, U, T> List<Any?>.chainingGreater(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as W), (other[0] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as W), (other[0] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as V), (other[1] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as V), (other[1] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as U), (other[2] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as U), (other[2] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as T), (other[3] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as T), (other[3] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r3
        }
        Continue(Unit)
    }
}

public fun <W, V, U, T> List<Any?>.cmp(other: List<Any?>): Ordering =
    when (val matchVal = kmCompareValues(((this[0] as W)), (other[0] as W)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues(((this[1] as V)), (other[1] as V)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmCompareValues(((this[2] as U)), (other[2] as U)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> kmCompareValues(((this[3] as T)), (other[3] as T)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                else -> {
                    val ordering = matchVal2
                    ordering
                }
            }
            else -> {
                val ordering1 = matchVal1
                ordering1
            }
        }
        else -> {
            val ordering2 = matchVal
            ordering2
        }
    }

public fun <W, V, U, T> defaultWVUT(defaultWFn: () -> W, defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): List<Any?> =
    listOf(run {
        val x: W = defaultWFn()
        x
    }, run {
        val x1: V = defaultVFn()
        x1
    }, run {
        val x2: U = defaultUFn()
        x2
    }, run {
        val x3: T = defaultTFn()
        x3
    })

public fun <T> fromTTTT(array: List<T>): List<Any?> {
    val (W, V, U, T) = array
    return listOf(W, V, U, T)
}

public fun <T> fromTArray4(tuple: List<Any?>): List<T> {
    val (W, V, U, T) = tuple
    return listOf(W, V, U, T)
}

public fun <X, W, V, U, T> List<Any?>.partialCmpFromXWVUT(other: List<Any?>): Ordering? =
    when (val matchVal = kmPartialCompareValues(((this[0] as X)), (other[0] as X))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues(((this[1] as W)), (other[1] as W))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmPartialCompareValues(((this[2] as V)), (other[2] as V))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmPartialCompareValues(((this[3] as U)), (other[3] as U))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> kmPartialCompareValues(((this[4] as T)), (other[4] as T))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                    else -> {
                        val ordering = matchVal3
                        ordering
                    }
                }
                else -> {
                    val ordering1 = matchVal2
                    ordering1
                }
            }
            else -> {
                val ordering2 = matchVal1
                ordering2
            }
        }
        else -> {
            val ordering3 = matchVal
            ordering3
        }
    }

public fun <X, W, V, U, T> List<Any?>.ltFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as X), (other[0] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as W), (other[1] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as V), (other[2] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as U), (other[3] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    (kmPartialCompareValues((this[4] as T), (other[4] as T))?.let { cmpSign -> cmpSign < 0 } == true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.leFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as X), (other[0] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as W), (other[1] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as V), (other[2] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as U), (other[3] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    (kmPartialCompareValues((this[4] as T), (other[4] as T))?.let { cmpSign -> cmpSign <= 0 } == true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.geFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as X), (other[0] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as W), (other[1] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as V), (other[2] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as U), (other[3] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    (kmPartialCompareValues((this[4] as T), (other[4] as T))?.let { cmpSign -> cmpSign >= 0 } == true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.gtFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as X), (other[0] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as W), (other[1] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as V), (other[2] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as U), (other[3] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    (kmPartialCompareValues((this[4] as T), (other[4] as T))?.let { cmpSign -> cmpSign > 0 } == true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.chainingLessFromXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as X), (other[0] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as X), (other[0] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as W), (other[1] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as W), (other[1] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as V), (other[2] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as V), (other[2] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as U), (other[3] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as U), (other[3] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as T), (other[4] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as T), (other[4] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r4
        }
        Continue(Unit)
    }
}

public fun <X, W, V, U, T> List<Any?>.chainingGreaterFromXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as X), (other[0] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as X), (other[0] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as W), (other[1] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as W), (other[1] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as V), (other[2] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as V), (other[2] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as U), (other[3] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as U), (other[3] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as T), (other[4] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as T), (other[4] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r4
        }
        Continue(Unit)
    }
}

public fun <X, W, V, U, T> List<Any?>.cmpFromXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = kmCompareValues(((this[0] as X)), (other[0] as X)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues(((this[1] as W)), (other[1] as W)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmCompareValues(((this[2] as V)), (other[2] as V)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmCompareValues(((this[3] as U)), (other[3] as U)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> kmCompareValues(((this[4] as T)), (other[4] as T)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                    else -> {
                        val ordering = matchVal3
                        ordering
                    }
                }
                else -> {
                    val ordering1 = matchVal2
                    ordering1
                }
            }
            else -> {
                val ordering2 = matchVal1
                ordering2
            }
        }
        else -> {
            val ordering3 = matchVal
            ordering3
        }
    }

public fun <X, W, V, U, T> defaultXWVUT(defaultXFn: () -> X, defaultWFn: () -> W, defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): List<Any?> =
    listOf(run {
        val x: X = defaultXFn()
        x
    }, run {
        val x1: W = defaultWFn()
        x1
    }, run {
        val x2: V = defaultVFn()
        x2
    }, run {
        val x3: U = defaultUFn()
        x3
    }, run {
        val x4: T = defaultTFn()
        x4
    })

public fun <T> fromTTTTT(array: List<T>): List<Any?> {
    val (X, W, V, U, T) = array
    return listOf(X, W, V, U, T)
}

public fun <T> fromTArray5(tuple: List<Any?>): List<T> {
    val (X, W, V, U, T) = tuple
    return listOf(X, W, V, U, T)
}

public fun <Y, X, W, V, U, T> List<Any?>.partialCmpFromYXWVUT(other: List<Any?>): Ordering? =
    when (val matchVal = kmPartialCompareValues(((this[0] as Y)), (other[0] as Y))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues(((this[1] as X)), (other[1] as X))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmPartialCompareValues(((this[2] as W)), (other[2] as W))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmPartialCompareValues(((this[3] as V)), (other[3] as V))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmPartialCompareValues(((this[4] as U)), (other[4] as U))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> kmPartialCompareValues(((this[5] as T)), (other[5] as T))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                        else -> {
                            val ordering = matchVal4
                            ordering
                        }
                    }
                    else -> {
                        val ordering1 = matchVal3
                        ordering1
                    }
                }
                else -> {
                    val ordering2 = matchVal2
                    ordering2
                }
            }
            else -> {
                val ordering3 = matchVal1
                ordering3
            }
        }
        else -> {
            val ordering4 = matchVal
            ordering4
        }
    }

public fun <Y, X, W, V, U, T> List<Any?>.ltFromYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as Y), (other[0] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as X), (other[1] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as W), (other[2] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as V), (other[3] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as U), (other[4] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            (kmPartialCompareValues((this[5] as T), (other[5] as T))?.let { cmpSign -> cmpSign < 0 } == true)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <Y, X, W, V, U, T> List<Any?>.leFromYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as Y), (other[0] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as X), (other[1] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as W), (other[2] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as V), (other[3] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as U), (other[4] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            (kmPartialCompareValues((this[5] as T), (other[5] as T))?.let { cmpSign -> cmpSign <= 0 } == true)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <Y, X, W, V, U, T> List<Any?>.geFromYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as Y), (other[0] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as X), (other[1] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as W), (other[2] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as V), (other[3] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as U), (other[4] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            (kmPartialCompareValues((this[5] as T), (other[5] as T))?.let { cmpSign -> cmpSign >= 0 } == true)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <Y, X, W, V, U, T> List<Any?>.gtFromYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as Y), (other[0] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as X), (other[1] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as W), (other[2] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as V), (other[3] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as U), (other[4] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            (kmPartialCompareValues((this[5] as T), (other[5] as T))?.let { cmpSign -> cmpSign > 0 } == true)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <Y, X, W, V, U, T> List<Any?>.chainingLessFromYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as Y), (other[0] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as Y), (other[0] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as X), (other[1] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as X), (other[1] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as W), (other[2] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as W), (other[2] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as V), (other[3] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as V), (other[3] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as U), (other[4] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as U), (other[4] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as T), (other[5] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as T), (other[5] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r5
        }
        Continue(Unit)
    }
}

public fun <Y, X, W, V, U, T> List<Any?>.chainingGreaterFromYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as Y), (other[0] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as Y), (other[0] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as X), (other[1] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as X), (other[1] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as W), (other[2] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as W), (other[2] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as V), (other[3] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as V), (other[3] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as U), (other[4] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as U), (other[4] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as T), (other[5] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as T), (other[5] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r5
        }
        Continue(Unit)
    }
}

public fun <Y, X, W, V, U, T> List<Any?>.cmpFromYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = kmCompareValues(((this[0] as Y)), (other[0] as Y)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues(((this[1] as X)), (other[1] as X)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmCompareValues(((this[2] as W)), (other[2] as W)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmCompareValues(((this[3] as V)), (other[3] as V)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmCompareValues(((this[4] as U)), (other[4] as U)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> kmCompareValues(((this[5] as T)), (other[5] as T)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                        else -> {
                            val ordering = matchVal4
                            ordering
                        }
                    }
                    else -> {
                        val ordering1 = matchVal3
                        ordering1
                    }
                }
                else -> {
                    val ordering2 = matchVal2
                    ordering2
                }
            }
            else -> {
                val ordering3 = matchVal1
                ordering3
            }
        }
        else -> {
            val ordering4 = matchVal
            ordering4
        }
    }

public fun <Y, X, W, V, U, T> defaultYXWVUT(defaultYFn: () -> Y, defaultXFn: () -> X, defaultWFn: () -> W, defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): List<Any?> =
    listOf(run {
        val x: Y = defaultYFn()
        x
    }, run {
        val x1: X = defaultXFn()
        x1
    }, run {
        val x2: W = defaultWFn()
        x2
    }, run {
        val x3: V = defaultVFn()
        x3
    }, run {
        val x4: U = defaultUFn()
        x4
    }, run {
        val x5: T = defaultTFn()
        x5
    })

public fun <T> fromTTTTTT(array: List<T>): List<Any?> {
    val (Y, X, W, V, U, T) = array
    return listOf(Y, X, W, V, U, T)
}

public fun <T> fromTArray6(tuple: List<Any?>): List<T> {
    val (Y, X, W, V, U, T) = tuple
    return listOf(Y, X, W, V, U, T)
}

public fun <Z, Y, X, W, V, U, T> List<Any?>.partialCmpFromZYXWVUT(other: List<Any?>): Ordering? =
    when (val matchVal = kmPartialCompareValues(((this[0] as Z)), (other[0] as Z))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues(((this[1] as Y)), (other[1] as Y))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmPartialCompareValues(((this[2] as X)), (other[2] as X))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmPartialCompareValues(((this[3] as W)), (other[3] as W))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmPartialCompareValues(((this[4] as V)), (other[4] as V))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmPartialCompareValues(((this[5] as U)), (other[5] as U))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> kmPartialCompareValues(((this[6] as T)), (other[6] as T))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                            else -> {
                                val ordering = matchVal5
                                ordering
                            }
                        }
                        else -> {
                            val ordering1 = matchVal4
                            ordering1
                        }
                    }
                    else -> {
                        val ordering2 = matchVal3
                        ordering2
                    }
                }
                else -> {
                    val ordering3 = matchVal2
                    ordering3
                }
            }
            else -> {
                val ordering4 = matchVal1
                ordering4
            }
        }
        else -> {
            val ordering5 = matchVal
            ordering5
        }
    }

public fun <Z, Y, X, W, V, U, T> List<Any?>.ltFromZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as Z), (other[0] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as Y), (other[1] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as X), (other[2] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as W), (other[3] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as V), (other[4] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as U), (other[5] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    (kmPartialCompareValues((this[6] as T), (other[6] as T))?.let { cmpSign -> cmpSign < 0 } == true)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <Z, Y, X, W, V, U, T> List<Any?>.leFromZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as Z), (other[0] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as Y), (other[1] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as X), (other[2] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as W), (other[3] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as V), (other[4] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as U), (other[5] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    (kmPartialCompareValues((this[6] as T), (other[6] as T))?.let { cmpSign -> cmpSign <= 0 } == true)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <Z, Y, X, W, V, U, T> List<Any?>.geFromZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as Z), (other[0] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as Y), (other[1] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as X), (other[2] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as W), (other[3] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as V), (other[4] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as U), (other[5] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    (kmPartialCompareValues((this[6] as T), (other[6] as T))?.let { cmpSign -> cmpSign >= 0 } == true)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <Z, Y, X, W, V, U, T> List<Any?>.gtFromZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as Z), (other[0] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as Y), (other[1] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as X), (other[2] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as W), (other[3] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as V), (other[4] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as U), (other[5] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    (kmPartialCompareValues((this[6] as T), (other[6] as T))?.let { cmpSign -> cmpSign > 0 } == true)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as Z), (other[0] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as Z), (other[0] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as Y), (other[1] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as Y), (other[1] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as X), (other[2] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as X), (other[2] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as W), (other[3] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as W), (other[3] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as V), (other[4] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as V), (other[4] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as U), (other[5] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as U), (other[5] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as T), (other[6] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as T), (other[6] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r6
        }
        Continue(Unit)
    }
}

public fun <Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as Z), (other[0] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as Z), (other[0] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as Y), (other[1] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as Y), (other[1] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as X), (other[2] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as X), (other[2] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as W), (other[3] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as W), (other[3] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as V), (other[4] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as V), (other[4] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as U), (other[5] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as U), (other[5] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as T), (other[6] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as T), (other[6] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r6
        }
        Continue(Unit)
    }
}

public fun <Z, Y, X, W, V, U, T> List<Any?>.cmpFromZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = kmCompareValues(((this[0] as Z)), (other[0] as Z)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues(((this[1] as Y)), (other[1] as Y)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmCompareValues(((this[2] as X)), (other[2] as X)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmCompareValues(((this[3] as W)), (other[3] as W)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmCompareValues(((this[4] as V)), (other[4] as V)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmCompareValues(((this[5] as U)), (other[5] as U)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> kmCompareValues(((this[6] as T)), (other[6] as T)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                            else -> {
                                val ordering = matchVal5
                                ordering
                            }
                        }
                        else -> {
                            val ordering1 = matchVal4
                            ordering1
                        }
                    }
                    else -> {
                        val ordering2 = matchVal3
                        ordering2
                    }
                }
                else -> {
                    val ordering3 = matchVal2
                    ordering3
                }
            }
            else -> {
                val ordering4 = matchVal1
                ordering4
            }
        }
        else -> {
            val ordering5 = matchVal
            ordering5
        }
    }

public fun <Z, Y, X, W, V, U, T> defaultZYXWVUT(defaultZFn: () -> Z, defaultYFn: () -> Y, defaultXFn: () -> X, defaultWFn: () -> W, defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): List<Any?> =
    listOf(run {
        val x: Z = defaultZFn()
        x
    }, run {
        val x1: Y = defaultYFn()
        x1
    }, run {
        val x2: X = defaultXFn()
        x2
    }, run {
        val x3: W = defaultWFn()
        x3
    }, run {
        val x4: V = defaultVFn()
        x4
    }, run {
        val x5: U = defaultUFn()
        x5
    }, run {
        val x6: T = defaultTFn()
        x6
    })

public fun <T> fromTTTTTTT(array: List<T>): List<Any?> {
    val (Z, Y, X, W, V, U, T) = array
    return listOf(Z, Y, X, W, V, U, T)
}

public fun <T> fromTArray7(tuple: List<Any?>): List<T> {
    val (Z, Y, X, W, V, U, T) = tuple
    return listOf(Z, Y, X, W, V, U, T)
}

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.partialCmpFromAZYXWVUT(other: List<Any?>): Ordering? =
    when (val matchVal = kmPartialCompareValues(((this[0] as A)), (other[0] as A))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues(((this[1] as Z)), (other[1] as Z))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmPartialCompareValues(((this[2] as Y)), (other[2] as Y))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmPartialCompareValues(((this[3] as X)), (other[3] as X))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmPartialCompareValues(((this[4] as W)), (other[4] as W))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmPartialCompareValues(((this[5] as V)), (other[5] as V))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmPartialCompareValues(((this[6] as U)), (other[6] as U))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> kmPartialCompareValues(((this[7] as T)), (other[7] as T))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                else -> {
                                    val ordering = matchVal6
                                    ordering
                                }
                            }
                            else -> {
                                val ordering1 = matchVal5
                                ordering1
                            }
                        }
                        else -> {
                            val ordering2 = matchVal4
                            ordering2
                        }
                    }
                    else -> {
                        val ordering3 = matchVal3
                        ordering3
                    }
                }
                else -> {
                    val ordering4 = matchVal2
                    ordering4
                }
            }
            else -> {
                val ordering5 = matchVal1
                ordering5
            }
        }
        else -> {
            val ordering6 = matchVal
            ordering6
        }
    }

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.ltFromAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as A), (other[0] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as Z), (other[1] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as Y), (other[2] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as X), (other[3] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as W), (other[4] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as V), (other[5] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as U), (other[6] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            (kmPartialCompareValues((this[7] as T), (other[7] as T))?.let { cmpSign -> cmpSign < 0 } == true)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.leFromAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as A), (other[0] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as Z), (other[1] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as Y), (other[2] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as X), (other[3] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as W), (other[4] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as V), (other[5] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as U), (other[6] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            (kmPartialCompareValues((this[7] as T), (other[7] as T))?.let { cmpSign -> cmpSign <= 0 } == true)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.geFromAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as A), (other[0] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as Z), (other[1] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as Y), (other[2] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as X), (other[3] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as W), (other[4] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as V), (other[5] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as U), (other[6] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            (kmPartialCompareValues((this[7] as T), (other[7] as T))?.let { cmpSign -> cmpSign >= 0 } == true)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.gtFromAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as A), (other[0] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as Z), (other[1] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as Y), (other[2] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as X), (other[3] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as W), (other[4] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as V), (other[5] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as U), (other[6] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            (kmPartialCompareValues((this[7] as T), (other[7] as T))?.let { cmpSign -> cmpSign > 0 } == true)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as A), (other[0] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as A), (other[0] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as Z), (other[1] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as Z), (other[1] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as Y), (other[2] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as Y), (other[2] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as X), (other[3] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as X), (other[3] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as W), (other[4] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as W), (other[4] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as V), (other[5] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as V), (other[5] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as U), (other[6] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as U), (other[6] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as T), (other[7] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as T), (other[7] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r7
        }
        Continue(Unit)
    }
}

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as A), (other[0] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as A), (other[0] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as Z), (other[1] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as Z), (other[1] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as Y), (other[2] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as Y), (other[2] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as X), (other[3] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as X), (other[3] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as W), (other[4] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as W), (other[4] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as V), (other[5] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as V), (other[5] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as U), (other[6] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as U), (other[6] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as T), (other[7] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as T), (other[7] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r7
        }
        Continue(Unit)
    }
}

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = kmCompareValues(((this[0] as A)), (other[0] as A)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues(((this[1] as Z)), (other[1] as Z)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmCompareValues(((this[2] as Y)), (other[2] as Y)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmCompareValues(((this[3] as X)), (other[3] as X)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmCompareValues(((this[4] as W)), (other[4] as W)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmCompareValues(((this[5] as V)), (other[5] as V)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmCompareValues(((this[6] as U)), (other[6] as U)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> kmCompareValues(((this[7] as T)), (other[7] as T)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                else -> {
                                    val ordering = matchVal6
                                    ordering
                                }
                            }
                            else -> {
                                val ordering1 = matchVal5
                                ordering1
                            }
                        }
                        else -> {
                            val ordering2 = matchVal4
                            ordering2
                        }
                    }
                    else -> {
                        val ordering3 = matchVal3
                        ordering3
                    }
                }
                else -> {
                    val ordering4 = matchVal2
                    ordering4
                }
            }
            else -> {
                val ordering5 = matchVal1
                ordering5
            }
        }
        else -> {
            val ordering6 = matchVal
            ordering6
        }
    }

public fun <A, Z, Y, X, W, V, U, T> defaultAZYXWVUT(defaultAFn: () -> A, defaultZFn: () -> Z, defaultYFn: () -> Y, defaultXFn: () -> X, defaultWFn: () -> W, defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): List<Any?> =
    listOf(run {
        val x: A = defaultAFn()
        x
    }, run {
        val x1: Z = defaultZFn()
        x1
    }, run {
        val x2: Y = defaultYFn()
        x2
    }, run {
        val x3: X = defaultXFn()
        x3
    }, run {
        val x4: W = defaultWFn()
        x4
    }, run {
        val x5: V = defaultVFn()
        x5
    }, run {
        val x6: U = defaultUFn()
        x6
    }, run {
        val x7: T = defaultTFn()
        x7
    })

public fun <T> fromTTTTTTTT(array: List<T>): List<Any?> {
    val (A, Z, Y, X, W, V, U, T) = array
    return listOf(A, Z, Y, X, W, V, U, T)
}

public fun <T> fromTArray8(tuple: List<Any?>): List<T> {
    val (A, Z, Y, X, W, V, U, T) = tuple
    return listOf(A, Z, Y, X, W, V, U, T)
}

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.partialCmpFromBAZYXWVUT(other: List<Any?>): Ordering? =
    when (val matchVal = kmPartialCompareValues(((this[0] as B)), (other[0] as B))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues(((this[1] as A)), (other[1] as A))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmPartialCompareValues(((this[2] as Z)), (other[2] as Z))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmPartialCompareValues(((this[3] as Y)), (other[3] as Y))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmPartialCompareValues(((this[4] as X)), (other[4] as X))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmPartialCompareValues(((this[5] as W)), (other[5] as W))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmPartialCompareValues(((this[6] as V)), (other[6] as V))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> when (val matchVal7 = kmPartialCompareValues(((this[7] as U)), (other[7] as U))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                    Equal -> kmPartialCompareValues(((this[8] as T)), (other[8] as T))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                    else -> {
                                        val ordering = matchVal7
                                        ordering
                                    }
                                }
                                else -> {
                                    val ordering1 = matchVal6
                                    ordering1
                                }
                            }
                            else -> {
                                val ordering2 = matchVal5
                                ordering2
                            }
                        }
                        else -> {
                            val ordering3 = matchVal4
                            ordering3
                        }
                    }
                    else -> {
                        val ordering4 = matchVal3
                        ordering4
                    }
                }
                else -> {
                    val ordering5 = matchVal2
                    ordering5
                }
            }
            else -> {
                val ordering6 = matchVal1
                ordering6
            }
        }
        else -> {
            val ordering7 = matchVal
            ordering7
        }
    }

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.ltFromBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as B), (other[0] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as A), (other[1] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as Z), (other[2] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as Y), (other[3] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as X), (other[4] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as W), (other[5] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as V), (other[6] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as U), (other[7] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    (kmPartialCompareValues((this[8] as T), (other[8] as T))?.let { cmpSign -> cmpSign < 0 } == true)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.leFromBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as B), (other[0] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as A), (other[1] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as Z), (other[2] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as Y), (other[3] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as X), (other[4] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as W), (other[5] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as V), (other[6] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as U), (other[7] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    (kmPartialCompareValues((this[8] as T), (other[8] as T))?.let { cmpSign -> cmpSign <= 0 } == true)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.geFromBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as B), (other[0] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as A), (other[1] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as Z), (other[2] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as Y), (other[3] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as X), (other[4] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as W), (other[5] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as V), (other[6] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as U), (other[7] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    (kmPartialCompareValues((this[8] as T), (other[8] as T))?.let { cmpSign -> cmpSign >= 0 } == true)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.gtFromBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as B), (other[0] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as A), (other[1] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as Z), (other[2] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as Y), (other[3] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as X), (other[4] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as W), (other[5] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as V), (other[6] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as U), (other[7] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    (kmPartialCompareValues((this[8] as T), (other[8] as T))?.let { cmpSign -> cmpSign > 0 } == true)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as B), (other[0] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as B), (other[0] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as A), (other[1] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as A), (other[1] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as Z), (other[2] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as Z), (other[2] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as Y), (other[3] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as Y), (other[3] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as X), (other[4] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as X), (other[4] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as W), (other[5] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as W), (other[5] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as V), (other[6] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as V), (other[6] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as U), (other[7] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as U), (other[7] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r7
        }
        if (kmPartialCompareValues((this[8] as T), (other[8] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r8 = kmPartialCompareValues((this[8] as T), (other[8] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r8
        }
        Continue(Unit)
    }
}

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as B), (other[0] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as B), (other[0] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as A), (other[1] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as A), (other[1] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as Z), (other[2] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as Z), (other[2] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as Y), (other[3] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as Y), (other[3] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as X), (other[4] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as X), (other[4] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as W), (other[5] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as W), (other[5] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as V), (other[6] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as V), (other[6] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as U), (other[7] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as U), (other[7] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r7
        }
        if (kmPartialCompareValues((this[8] as T), (other[8] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r8 = kmPartialCompareValues((this[8] as T), (other[8] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r8
        }
        Continue(Unit)
    }
}

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = kmCompareValues(((this[0] as B)), (other[0] as B)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues(((this[1] as A)), (other[1] as A)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmCompareValues(((this[2] as Z)), (other[2] as Z)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmCompareValues(((this[3] as Y)), (other[3] as Y)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmCompareValues(((this[4] as X)), (other[4] as X)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmCompareValues(((this[5] as W)), (other[5] as W)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmCompareValues(((this[6] as V)), (other[6] as V)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> when (val matchVal7 = kmCompareValues(((this[7] as U)), (other[7] as U)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                    Equal -> kmCompareValues(((this[8] as T)), (other[8] as T)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                    else -> {
                                        val ordering = matchVal7
                                        ordering
                                    }
                                }
                                else -> {
                                    val ordering1 = matchVal6
                                    ordering1
                                }
                            }
                            else -> {
                                val ordering2 = matchVal5
                                ordering2
                            }
                        }
                        else -> {
                            val ordering3 = matchVal4
                            ordering3
                        }
                    }
                    else -> {
                        val ordering4 = matchVal3
                        ordering4
                    }
                }
                else -> {
                    val ordering5 = matchVal2
                    ordering5
                }
            }
            else -> {
                val ordering6 = matchVal1
                ordering6
            }
        }
        else -> {
            val ordering7 = matchVal
            ordering7
        }
    }

public fun <B, A, Z, Y, X, W, V, U, T> defaultBAZYXWVUT(defaultBFn: () -> B, defaultAFn: () -> A, defaultZFn: () -> Z, defaultYFn: () -> Y, defaultXFn: () -> X, defaultWFn: () -> W, defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): List<Any?> =
    listOf(run {
        val x: B = defaultBFn()
        x
    }, run {
        val x1: A = defaultAFn()
        x1
    }, run {
        val x2: Z = defaultZFn()
        x2
    }, run {
        val x3: Y = defaultYFn()
        x3
    }, run {
        val x4: X = defaultXFn()
        x4
    }, run {
        val x5: W = defaultWFn()
        x5
    }, run {
        val x6: V = defaultVFn()
        x6
    }, run {
        val x7: U = defaultUFn()
        x7
    }, run {
        val x8: T = defaultTFn()
        x8
    })

public fun <T> fromTTTTTTTTT(array: List<T>): List<Any?> {
    val (B, A, Z, Y, X, W, V, U, T) = array
    return listOf(B, A, Z, Y, X, W, V, U, T)
}

public fun <T> fromTArray9(tuple: List<Any?>): List<T> {
    val (B, A, Z, Y, X, W, V, U, T) = tuple
    return listOf(B, A, Z, Y, X, W, V, U, T)
}

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.partialCmpFromCBAZYXWVUT(other: List<Any?>): Ordering? =
    when (val matchVal = kmPartialCompareValues(((this[0] as C)), (other[0] as C))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues(((this[1] as B)), (other[1] as B))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmPartialCompareValues(((this[2] as A)), (other[2] as A))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmPartialCompareValues(((this[3] as Z)), (other[3] as Z))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmPartialCompareValues(((this[4] as Y)), (other[4] as Y))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmPartialCompareValues(((this[5] as X)), (other[5] as X))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmPartialCompareValues(((this[6] as W)), (other[6] as W))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> when (val matchVal7 = kmPartialCompareValues(((this[7] as V)), (other[7] as V))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                    Equal -> when (val matchVal8 = kmPartialCompareValues(((this[8] as U)), (other[8] as U))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                        Equal -> kmPartialCompareValues(((this[9] as T)), (other[9] as T))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                        else -> {
                                            val ordering = matchVal8
                                            ordering
                                        }
                                    }
                                    else -> {
                                        val ordering1 = matchVal7
                                        ordering1
                                    }
                                }
                                else -> {
                                    val ordering2 = matchVal6
                                    ordering2
                                }
                            }
                            else -> {
                                val ordering3 = matchVal5
                                ordering3
                            }
                        }
                        else -> {
                            val ordering4 = matchVal4
                            ordering4
                        }
                    }
                    else -> {
                        val ordering5 = matchVal3
                        ordering5
                    }
                }
                else -> {
                    val ordering6 = matchVal2
                    ordering6
                }
            }
            else -> {
                val ordering7 = matchVal1
                ordering7
            }
        }
        else -> {
            val ordering8 = matchVal
            ordering8
        }
    }

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.ltFromCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as C), (other[0] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as B), (other[1] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as A), (other[2] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as Z), (other[3] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as Y), (other[4] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as X), (other[5] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as W), (other[6] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as V), (other[7] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as U), (other[8] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            (kmPartialCompareValues((this[9] as T), (other[9] as T))?.let { cmpSign -> cmpSign < 0 } == true)
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.leFromCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as C), (other[0] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as B), (other[1] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as A), (other[2] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as Z), (other[3] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as Y), (other[4] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as X), (other[5] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as W), (other[6] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as V), (other[7] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as U), (other[8] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            (kmPartialCompareValues((this[9] as T), (other[9] as T))?.let { cmpSign -> cmpSign <= 0 } == true)
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.geFromCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as C), (other[0] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as B), (other[1] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as A), (other[2] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as Z), (other[3] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as Y), (other[4] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as X), (other[5] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as W), (other[6] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as V), (other[7] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as U), (other[8] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            (kmPartialCompareValues((this[9] as T), (other[9] as T))?.let { cmpSign -> cmpSign >= 0 } == true)
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.gtFromCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as C), (other[0] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as B), (other[1] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as A), (other[2] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as Z), (other[3] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as Y), (other[4] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as X), (other[5] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as W), (other[6] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as V), (other[7] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as U), (other[8] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            (kmPartialCompareValues((this[9] as T), (other[9] as T))?.let { cmpSign -> cmpSign > 0 } == true)
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as C), (other[0] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as C), (other[0] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as B), (other[1] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as B), (other[1] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as A), (other[2] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as A), (other[2] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as Z), (other[3] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as Z), (other[3] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as Y), (other[4] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as Y), (other[4] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as X), (other[5] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as X), (other[5] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as W), (other[6] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as W), (other[6] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as V), (other[7] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as V), (other[7] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r7
        }
        if (kmPartialCompareValues((this[8] as U), (other[8] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r8 = kmPartialCompareValues((this[8] as U), (other[8] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r8
        }
        if (kmPartialCompareValues((this[9] as T), (other[9] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r9 = kmPartialCompareValues((this[9] as T), (other[9] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r9
        }
        Continue(Unit)
    }
}

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as C), (other[0] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as C), (other[0] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as B), (other[1] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as B), (other[1] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as A), (other[2] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as A), (other[2] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as Z), (other[3] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as Z), (other[3] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as Y), (other[4] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as Y), (other[4] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as X), (other[5] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as X), (other[5] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as W), (other[6] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as W), (other[6] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as V), (other[7] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as V), (other[7] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r7
        }
        if (kmPartialCompareValues((this[8] as U), (other[8] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r8 = kmPartialCompareValues((this[8] as U), (other[8] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r8
        }
        if (kmPartialCompareValues((this[9] as T), (other[9] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r9 = kmPartialCompareValues((this[9] as T), (other[9] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r9
        }
        Continue(Unit)
    }
}

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromCBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = kmCompareValues(((this[0] as C)), (other[0] as C)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues(((this[1] as B)), (other[1] as B)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmCompareValues(((this[2] as A)), (other[2] as A)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmCompareValues(((this[3] as Z)), (other[3] as Z)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmCompareValues(((this[4] as Y)), (other[4] as Y)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmCompareValues(((this[5] as X)), (other[5] as X)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmCompareValues(((this[6] as W)), (other[6] as W)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> when (val matchVal7 = kmCompareValues(((this[7] as V)), (other[7] as V)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                    Equal -> when (val matchVal8 = kmCompareValues(((this[8] as U)), (other[8] as U)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                        Equal -> kmCompareValues(((this[9] as T)), (other[9] as T)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                        else -> {
                                            val ordering = matchVal8
                                            ordering
                                        }
                                    }
                                    else -> {
                                        val ordering1 = matchVal7
                                        ordering1
                                    }
                                }
                                else -> {
                                    val ordering2 = matchVal6
                                    ordering2
                                }
                            }
                            else -> {
                                val ordering3 = matchVal5
                                ordering3
                            }
                        }
                        else -> {
                            val ordering4 = matchVal4
                            ordering4
                        }
                    }
                    else -> {
                        val ordering5 = matchVal3
                        ordering5
                    }
                }
                else -> {
                    val ordering6 = matchVal2
                    ordering6
                }
            }
            else -> {
                val ordering7 = matchVal1
                ordering7
            }
        }
        else -> {
            val ordering8 = matchVal
            ordering8
        }
    }

public fun <C, B, A, Z, Y, X, W, V, U, T> defaultCBAZYXWVUT(defaultCFn: () -> C, defaultBFn: () -> B, defaultAFn: () -> A, defaultZFn: () -> Z, defaultYFn: () -> Y, defaultXFn: () -> X, defaultWFn: () -> W, defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): List<Any?> =
    listOf(run {
        val x: C = defaultCFn()
        x
    }, run {
        val x1: B = defaultBFn()
        x1
    }, run {
        val x2: A = defaultAFn()
        x2
    }, run {
        val x3: Z = defaultZFn()
        x3
    }, run {
        val x4: Y = defaultYFn()
        x4
    }, run {
        val x5: X = defaultXFn()
        x5
    }, run {
        val x6: W = defaultWFn()
        x6
    }, run {
        val x7: V = defaultVFn()
        x7
    }, run {
        val x8: U = defaultUFn()
        x8
    }, run {
        val x9: T = defaultTFn()
        x9
    })

public fun <T> fromTTTTTTTTTT(array: List<T>): List<Any?> {
    val (C, B, A, Z, Y, X, W, V, U, T) = array
    return listOf(C, B, A, Z, Y, X, W, V, U, T)
}

public fun <T> fromTArray10(tuple: List<Any?>): List<T> {
    val (C, B, A, Z, Y, X, W, V, U, T) = tuple
    return listOf(C, B, A, Z, Y, X, W, V, U, T)
}

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.partialCmpFromDCBAZYXWVUT(other: List<Any?>): Ordering? =
    when (val matchVal = kmPartialCompareValues(((this[0] as D)), (other[0] as D))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues(((this[1] as C)), (other[1] as C))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmPartialCompareValues(((this[2] as B)), (other[2] as B))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmPartialCompareValues(((this[3] as A)), (other[3] as A))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmPartialCompareValues(((this[4] as Z)), (other[4] as Z))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmPartialCompareValues(((this[5] as Y)), (other[5] as Y))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmPartialCompareValues(((this[6] as X)), (other[6] as X))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> when (val matchVal7 = kmPartialCompareValues(((this[7] as W)), (other[7] as W))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                    Equal -> when (val matchVal8 = kmPartialCompareValues(((this[8] as V)), (other[8] as V))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                        Equal -> when (val matchVal9 = kmPartialCompareValues(((this[9] as U)), (other[9] as U))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                            Equal -> kmPartialCompareValues(((this[10] as T)), (other[10] as T))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                            else -> {
                                                val ordering = matchVal9
                                                ordering
                                            }
                                        }
                                        else -> {
                                            val ordering1 = matchVal8
                                            ordering1
                                        }
                                    }
                                    else -> {
                                        val ordering2 = matchVal7
                                        ordering2
                                    }
                                }
                                else -> {
                                    val ordering3 = matchVal6
                                    ordering3
                                }
                            }
                            else -> {
                                val ordering4 = matchVal5
                                ordering4
                            }
                        }
                        else -> {
                            val ordering5 = matchVal4
                            ordering5
                        }
                    }
                    else -> {
                        val ordering6 = matchVal3
                        ordering6
                    }
                }
                else -> {
                    val ordering7 = matchVal2
                    ordering7
                }
            }
            else -> {
                val ordering8 = matchVal1
                ordering8
            }
        }
        else -> {
            val ordering9 = matchVal
            ordering9
        }
    }

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.ltFromDCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as D), (other[0] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as C), (other[1] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as B), (other[2] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as A), (other[3] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as Z), (other[4] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as Y), (other[5] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as X), (other[6] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as W), (other[7] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as V), (other[8] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            when (val matchVal9 = kmPartialCompareValues((this[9] as U), (other[9] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                                is ControlFlow.Break<*, *> -> {
                                                                                    val value9 = matchVal9.cfBreakValue()
                                                                                    value9
                                                                                }
                                                                                is ControlFlow.Continue<*, *> -> {
                                                                                    val cfContinueValueVal9 = matchVal9.cfContinueValue()
                                                                                    (kmPartialCompareValues((this[10] as T), (other[10] as T))?.let { cmpSign -> cmpSign < 0 } == true)
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.leFromDCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as D), (other[0] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as C), (other[1] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as B), (other[2] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as A), (other[3] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as Z), (other[4] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as Y), (other[5] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as X), (other[6] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as W), (other[7] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as V), (other[8] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            when (val matchVal9 = kmPartialCompareValues((this[9] as U), (other[9] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                                is ControlFlow.Break<*, *> -> {
                                                                                    val value9 = matchVal9.cfBreakValue()
                                                                                    value9
                                                                                }
                                                                                is ControlFlow.Continue<*, *> -> {
                                                                                    val cfContinueValueVal9 = matchVal9.cfContinueValue()
                                                                                    (kmPartialCompareValues((this[10] as T), (other[10] as T))?.let { cmpSign -> cmpSign <= 0 } == true)
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.geFromDCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as D), (other[0] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as C), (other[1] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as B), (other[2] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as A), (other[3] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as Z), (other[4] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as Y), (other[5] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as X), (other[6] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as W), (other[7] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as V), (other[8] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            when (val matchVal9 = kmPartialCompareValues((this[9] as U), (other[9] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                                is ControlFlow.Break<*, *> -> {
                                                                                    val value9 = matchVal9.cfBreakValue()
                                                                                    value9
                                                                                }
                                                                                is ControlFlow.Continue<*, *> -> {
                                                                                    val cfContinueValueVal9 = matchVal9.cfContinueValue()
                                                                                    (kmPartialCompareValues((this[10] as T), (other[10] as T))?.let { cmpSign -> cmpSign >= 0 } == true)
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.gtFromDCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as D), (other[0] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as C), (other[1] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as B), (other[2] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as A), (other[3] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as Z), (other[4] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as Y), (other[5] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as X), (other[6] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as W), (other[7] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as V), (other[8] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            when (val matchVal9 = kmPartialCompareValues((this[9] as U), (other[9] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                                is ControlFlow.Break<*, *> -> {
                                                                                    val value9 = matchVal9.cfBreakValue()
                                                                                    value9
                                                                                }
                                                                                is ControlFlow.Continue<*, *> -> {
                                                                                    val cfContinueValueVal9 = matchVal9.cfContinueValue()
                                                                                    (kmPartialCompareValues((this[10] as T), (other[10] as T))?.let { cmpSign -> cmpSign > 0 } == true)
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as D), (other[0] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as D), (other[0] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as C), (other[1] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as C), (other[1] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as B), (other[2] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as B), (other[2] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as A), (other[3] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as A), (other[3] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as Z), (other[4] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as Z), (other[4] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as Y), (other[5] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as Y), (other[5] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as X), (other[6] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as X), (other[6] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as W), (other[7] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as W), (other[7] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r7
        }
        if (kmPartialCompareValues((this[8] as V), (other[8] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r8 = kmPartialCompareValues((this[8] as V), (other[8] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r8
        }
        if (kmPartialCompareValues((this[9] as U), (other[9] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r9 = kmPartialCompareValues((this[9] as U), (other[9] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r9
        }
        if (kmPartialCompareValues((this[10] as T), (other[10] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r10 = kmPartialCompareValues((this[10] as T), (other[10] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r10
        }
        Continue(Unit)
    }
}

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as D), (other[0] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as D), (other[0] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as C), (other[1] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as C), (other[1] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as B), (other[2] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as B), (other[2] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as A), (other[3] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as A), (other[3] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as Z), (other[4] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as Z), (other[4] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as Y), (other[5] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as Y), (other[5] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as X), (other[6] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as X), (other[6] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as W), (other[7] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as W), (other[7] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r7
        }
        if (kmPartialCompareValues((this[8] as V), (other[8] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r8 = kmPartialCompareValues((this[8] as V), (other[8] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r8
        }
        if (kmPartialCompareValues((this[9] as U), (other[9] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r9 = kmPartialCompareValues((this[9] as U), (other[9] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r9
        }
        if (kmPartialCompareValues((this[10] as T), (other[10] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r10 = kmPartialCompareValues((this[10] as T), (other[10] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r10
        }
        Continue(Unit)
    }
}

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromDCBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = kmCompareValues(((this[0] as D)), (other[0] as D)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues(((this[1] as C)), (other[1] as C)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmCompareValues(((this[2] as B)), (other[2] as B)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmCompareValues(((this[3] as A)), (other[3] as A)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmCompareValues(((this[4] as Z)), (other[4] as Z)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmCompareValues(((this[5] as Y)), (other[5] as Y)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmCompareValues(((this[6] as X)), (other[6] as X)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> when (val matchVal7 = kmCompareValues(((this[7] as W)), (other[7] as W)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                    Equal -> when (val matchVal8 = kmCompareValues(((this[8] as V)), (other[8] as V)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                        Equal -> when (val matchVal9 = kmCompareValues(((this[9] as U)), (other[9] as U)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                            Equal -> kmCompareValues(((this[10] as T)), (other[10] as T)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                            else -> {
                                                val ordering = matchVal9
                                                ordering
                                            }
                                        }
                                        else -> {
                                            val ordering1 = matchVal8
                                            ordering1
                                        }
                                    }
                                    else -> {
                                        val ordering2 = matchVal7
                                        ordering2
                                    }
                                }
                                else -> {
                                    val ordering3 = matchVal6
                                    ordering3
                                }
                            }
                            else -> {
                                val ordering4 = matchVal5
                                ordering4
                            }
                        }
                        else -> {
                            val ordering5 = matchVal4
                            ordering5
                        }
                    }
                    else -> {
                        val ordering6 = matchVal3
                        ordering6
                    }
                }
                else -> {
                    val ordering7 = matchVal2
                    ordering7
                }
            }
            else -> {
                val ordering8 = matchVal1
                ordering8
            }
        }
        else -> {
            val ordering9 = matchVal
            ordering9
        }
    }

public fun <D, C, B, A, Z, Y, X, W, V, U, T> defaultDCBAZYXWVUT(defaultDFn: () -> D, defaultCFn: () -> C, defaultBFn: () -> B, defaultAFn: () -> A, defaultZFn: () -> Z, defaultYFn: () -> Y, defaultXFn: () -> X, defaultWFn: () -> W, defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): List<Any?> =
    listOf(run {
        val x: D = defaultDFn()
        x
    }, run {
        val x1: C = defaultCFn()
        x1
    }, run {
        val x2: B = defaultBFn()
        x2
    }, run {
        val x3: A = defaultAFn()
        x3
    }, run {
        val x4: Z = defaultZFn()
        x4
    }, run {
        val x5: Y = defaultYFn()
        x5
    }, run {
        val x6: X = defaultXFn()
        x6
    }, run {
        val x7: W = defaultWFn()
        x7
    }, run {
        val x8: V = defaultVFn()
        x8
    }, run {
        val x9: U = defaultUFn()
        x9
    }, run {
        val x10: T = defaultTFn()
        x10
    })

public fun <T> fromTTTTTTTTTTT(array: List<T>): List<Any?> {
    val (D, C, B, A, Z, Y, X, W, V, U, T) = array
    return listOf(D, C, B, A, Z, Y, X, W, V, U, T)
}

public fun <T> fromTArray11(tuple: List<Any?>): List<T> {
    val (D, C, B, A, Z, Y, X, W, V, U, T) = tuple
    return listOf(D, C, B, A, Z, Y, X, W, V, U, T)
}

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.partialCmpFromEDCBAZYXWVUT(other: List<Any?>): Ordering? =
    when (val matchVal = kmPartialCompareValues(((this[0] as E)), (other[0] as E))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmPartialCompareValues(((this[1] as D)), (other[1] as D))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmPartialCompareValues(((this[2] as C)), (other[2] as C))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmPartialCompareValues(((this[3] as B)), (other[3] as B))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmPartialCompareValues(((this[4] as A)), (other[4] as A))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmPartialCompareValues(((this[5] as Z)), (other[5] as Z))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmPartialCompareValues(((this[6] as Y)), (other[6] as Y))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> when (val matchVal7 = kmPartialCompareValues(((this[7] as X)), (other[7] as X))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                    Equal -> when (val matchVal8 = kmPartialCompareValues(((this[8] as W)), (other[8] as W))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                        Equal -> when (val matchVal9 = kmPartialCompareValues(((this[9] as V)), (other[9] as V))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                            Equal -> when (val matchVal10 = kmPartialCompareValues(((this[10] as U)), (other[10] as U))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                                Equal -> kmPartialCompareValues(((this[11] as T)), (other[11] as T))?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                                else -> {
                                                    val ordering = matchVal10
                                                    ordering
                                                }
                                            }
                                            else -> {
                                                val ordering1 = matchVal9
                                                ordering1
                                            }
                                        }
                                        else -> {
                                            val ordering2 = matchVal8
                                            ordering2
                                        }
                                    }
                                    else -> {
                                        val ordering3 = matchVal7
                                        ordering3
                                    }
                                }
                                else -> {
                                    val ordering4 = matchVal6
                                    ordering4
                                }
                            }
                            else -> {
                                val ordering5 = matchVal5
                                ordering5
                            }
                        }
                        else -> {
                            val ordering6 = matchVal4
                            ordering6
                        }
                    }
                    else -> {
                        val ordering7 = matchVal3
                        ordering7
                    }
                }
                else -> {
                    val ordering8 = matchVal2
                    ordering8
                }
            }
            else -> {
                val ordering9 = matchVal1
                ordering9
            }
        }
        else -> {
            val ordering10 = matchVal
            ordering10
        }
    }

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.ltFromEDCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as E), (other[0] as E)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as D), (other[1] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as C), (other[2] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as B), (other[3] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as A), (other[4] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as Z), (other[5] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as Y), (other[6] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as X), (other[7] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as W), (other[8] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            when (val matchVal9 = kmPartialCompareValues((this[9] as V), (other[9] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                                is ControlFlow.Break<*, *> -> {
                                                                                    val value9 = matchVal9.cfBreakValue()
                                                                                    value9
                                                                                }
                                                                                is ControlFlow.Continue<*, *> -> {
                                                                                    val cfContinueValueVal9 = matchVal9.cfContinueValue()
                                                                                    when (val matchVal10 = kmPartialCompareValues((this[10] as U), (other[10] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                                        is ControlFlow.Break<*, *> -> {
                                                                                            val value10 = matchVal10.cfBreakValue()
                                                                                            value10
                                                                                        }
                                                                                        is ControlFlow.Continue<*, *> -> {
                                                                                            val cfContinueValueVal10 = matchVal10.cfContinueValue()
                                                                                            (kmPartialCompareValues((this[11] as T), (other[11] as T))?.let { cmpSign -> cmpSign < 0 } == true)
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.leFromEDCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as E), (other[0] as E)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as D), (other[1] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as C), (other[2] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as B), (other[3] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as A), (other[4] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as Z), (other[5] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as Y), (other[6] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as X), (other[7] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as W), (other[8] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            when (val matchVal9 = kmPartialCompareValues((this[9] as V), (other[9] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                                is ControlFlow.Break<*, *> -> {
                                                                                    val value9 = matchVal9.cfBreakValue()
                                                                                    value9
                                                                                }
                                                                                is ControlFlow.Continue<*, *> -> {
                                                                                    val cfContinueValueVal9 = matchVal9.cfContinueValue()
                                                                                    when (val matchVal10 = kmPartialCompareValues((this[10] as U), (other[10] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }) {
                                                                                        is ControlFlow.Break<*, *> -> {
                                                                                            val value10 = matchVal10.cfBreakValue()
                                                                                            value10
                                                                                        }
                                                                                        is ControlFlow.Continue<*, *> -> {
                                                                                            val cfContinueValueVal10 = matchVal10.cfContinueValue()
                                                                                            (kmPartialCompareValues((this[11] as T), (other[11] as T))?.let { cmpSign -> cmpSign <= 0 } == true)
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.geFromEDCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as E), (other[0] as E)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as D), (other[1] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as C), (other[2] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as B), (other[3] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as A), (other[4] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as Z), (other[5] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as Y), (other[6] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as X), (other[7] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as W), (other[8] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            when (val matchVal9 = kmPartialCompareValues((this[9] as V), (other[9] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                                is ControlFlow.Break<*, *> -> {
                                                                                    val value9 = matchVal9.cfBreakValue()
                                                                                    value9
                                                                                }
                                                                                is ControlFlow.Continue<*, *> -> {
                                                                                    val cfContinueValueVal9 = matchVal9.cfContinueValue()
                                                                                    when (val matchVal10 = kmPartialCompareValues((this[10] as U), (other[10] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                                        is ControlFlow.Break<*, *> -> {
                                                                                            val value10 = matchVal10.cfBreakValue()
                                                                                            value10
                                                                                        }
                                                                                        is ControlFlow.Continue<*, *> -> {
                                                                                            val cfContinueValueVal10 = matchVal10.cfContinueValue()
                                                                                            (kmPartialCompareValues((this[11] as T), (other[11] as T))?.let { cmpSign -> cmpSign >= 0 } == true)
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.gtFromEDCBAZYXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = kmPartialCompareValues((this[0] as E), (other[0] as E)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
        is ControlFlow.Break<*, *> -> {
            val value = matchVal.cfBreakValue()
            value
        }
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            when (val matchVal1 = kmPartialCompareValues((this[1] as D), (other[1] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                is ControlFlow.Break<*, *> -> {
                    val value1 = matchVal1.cfBreakValue()
                    value1
                }
                is ControlFlow.Continue<*, *> -> {
                    val cfContinueValueVal1 = matchVal1.cfContinueValue()
                    when (val matchVal2 = kmPartialCompareValues((this[2] as C), (other[2] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                        is ControlFlow.Break<*, *> -> {
                            val value2 = matchVal2.cfBreakValue()
                            value2
                        }
                        is ControlFlow.Continue<*, *> -> {
                            val cfContinueValueVal2 = matchVal2.cfContinueValue()
                            when (val matchVal3 = kmPartialCompareValues((this[3] as B), (other[3] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                is ControlFlow.Break<*, *> -> {
                                    val value3 = matchVal3.cfBreakValue()
                                    value3
                                }
                                is ControlFlow.Continue<*, *> -> {
                                    val cfContinueValueVal3 = matchVal3.cfContinueValue()
                                    when (val matchVal4 = kmPartialCompareValues((this[4] as A), (other[4] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                        is ControlFlow.Break<*, *> -> {
                                            val value4 = matchVal4.cfBreakValue()
                                            value4
                                        }
                                        is ControlFlow.Continue<*, *> -> {
                                            val cfContinueValueVal4 = matchVal4.cfContinueValue()
                                            when (val matchVal5 = kmPartialCompareValues((this[5] as Z), (other[5] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                is ControlFlow.Break<*, *> -> {
                                                    val value5 = matchVal5.cfBreakValue()
                                                    value5
                                                }
                                                is ControlFlow.Continue<*, *> -> {
                                                    val cfContinueValueVal5 = matchVal5.cfContinueValue()
                                                    when (val matchVal6 = kmPartialCompareValues((this[6] as Y), (other[6] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                        is ControlFlow.Break<*, *> -> {
                                                            val value6 = matchVal6.cfBreakValue()
                                                            value6
                                                        }
                                                        is ControlFlow.Continue<*, *> -> {
                                                            val cfContinueValueVal6 = matchVal6.cfContinueValue()
                                                            when (val matchVal7 = kmPartialCompareValues((this[7] as X), (other[7] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                is ControlFlow.Break<*, *> -> {
                                                                    val value7 = matchVal7.cfBreakValue()
                                                                    value7
                                                                }
                                                                is ControlFlow.Continue<*, *> -> {
                                                                    val cfContinueValueVal7 = matchVal7.cfContinueValue()
                                                                    when (val matchVal8 = kmPartialCompareValues((this[8] as W), (other[8] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                        is ControlFlow.Break<*, *> -> {
                                                                            val value8 = matchVal8.cfBreakValue()
                                                                            value8
                                                                        }
                                                                        is ControlFlow.Continue<*, *> -> {
                                                                            val cfContinueValueVal8 = matchVal8.cfContinueValue()
                                                                            when (val matchVal9 = kmPartialCompareValues((this[9] as V), (other[9] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                                is ControlFlow.Break<*, *> -> {
                                                                                    val value9 = matchVal9.cfBreakValue()
                                                                                    value9
                                                                                }
                                                                                is ControlFlow.Continue<*, *> -> {
                                                                                    val cfContinueValueVal9 = matchVal9.cfContinueValue()
                                                                                    when (val matchVal10 = kmPartialCompareValues((this[10] as U), (other[10] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }) {
                                                                                        is ControlFlow.Break<*, *> -> {
                                                                                            val value10 = matchVal10.cfBreakValue()
                                                                                            value10
                                                                                        }
                                                                                        is ControlFlow.Continue<*, *> -> {
                                                                                            val cfContinueValueVal10 = matchVal10.cfContinueValue()
                                                                                            (kmPartialCompareValues((this[11] as T), (other[11] as T))?.let { cmpSign -> cmpSign > 0 } == true)
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromEDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as E), (other[0] as E)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as E), (other[0] as E)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as D), (other[1] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as D), (other[1] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as C), (other[2] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as C), (other[2] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as B), (other[3] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as B), (other[3] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as A), (other[4] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as A), (other[4] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as Z), (other[5] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as Z), (other[5] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as Y), (other[6] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as Y), (other[6] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as X), (other[7] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as X), (other[7] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r7
        }
        if (kmPartialCompareValues((this[8] as W), (other[8] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r8 = kmPartialCompareValues((this[8] as W), (other[8] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r8
        }
        if (kmPartialCompareValues((this[9] as V), (other[9] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r9 = kmPartialCompareValues((this[9] as V), (other[9] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r9
        }
        if (kmPartialCompareValues((this[10] as U), (other[10] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r10 = kmPartialCompareValues((this[10] as U), (other[10] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r10
        }
        if (kmPartialCompareValues((this[11] as T), (other[11] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) } is ControlFlow.Break<*, *>) {
            val r11 = kmPartialCompareValues((this[11] as T), (other[11] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign < 0) }
            return r11
        }
        Continue(Unit)
    }
}

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromEDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if (kmPartialCompareValues((this[0] as E), (other[0] as E)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r = kmPartialCompareValues((this[0] as E), (other[0] as E)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r
        }
        if (kmPartialCompareValues((this[1] as D), (other[1] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r1 = kmPartialCompareValues((this[1] as D), (other[1] as D)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r1
        }
        if (kmPartialCompareValues((this[2] as C), (other[2] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r2 = kmPartialCompareValues((this[2] as C), (other[2] as C)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r2
        }
        if (kmPartialCompareValues((this[3] as B), (other[3] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r3 = kmPartialCompareValues((this[3] as B), (other[3] as B)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r3
        }
        if (kmPartialCompareValues((this[4] as A), (other[4] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r4 = kmPartialCompareValues((this[4] as A), (other[4] as A)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r4
        }
        if (kmPartialCompareValues((this[5] as Z), (other[5] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r5 = kmPartialCompareValues((this[5] as Z), (other[5] as Z)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r5
        }
        if (kmPartialCompareValues((this[6] as Y), (other[6] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r6 = kmPartialCompareValues((this[6] as Y), (other[6] as Y)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r6
        }
        if (kmPartialCompareValues((this[7] as X), (other[7] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r7 = kmPartialCompareValues((this[7] as X), (other[7] as X)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r7
        }
        if (kmPartialCompareValues((this[8] as W), (other[8] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r8 = kmPartialCompareValues((this[8] as W), (other[8] as W)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r8
        }
        if (kmPartialCompareValues((this[9] as V), (other[9] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r9 = kmPartialCompareValues((this[9] as V), (other[9] as V)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r9
        }
        if (kmPartialCompareValues((this[10] as U), (other[10] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r10 = kmPartialCompareValues((this[10] as U), (other[10] as U)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r10
        }
        if (kmPartialCompareValues((this[11] as T), (other[11] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) } is ControlFlow.Break<*, *>) {
            val r11 = kmPartialCompareValues((this[11] as T), (other[11] as T)).let { cmpSign -> if (cmpSign == 0) ControlFlow.Continue<Boolean, Unit>(Unit) else ControlFlow.Break<Boolean, Unit>(cmpSign != null && cmpSign > 0) }
            return r11
        }
        Continue(Unit)
    }
}

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromEDCBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = kmCompareValues(((this[0] as E)), (other[0] as E)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
        Equal -> when (val matchVal1 = kmCompareValues(((this[1] as D)), (other[1] as D)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Equal -> when (val matchVal2 = kmCompareValues(((this[2] as C)), (other[2] as C)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                Equal -> when (val matchVal3 = kmCompareValues(((this[3] as B)), (other[3] as B)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                    Equal -> when (val matchVal4 = kmCompareValues(((this[4] as A)), (other[4] as A)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                        Equal -> when (val matchVal5 = kmCompareValues(((this[5] as Z)), (other[5] as Z)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                            Equal -> when (val matchVal6 = kmCompareValues(((this[6] as Y)), (other[6] as Y)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                Equal -> when (val matchVal7 = kmCompareValues(((this[7] as X)), (other[7] as X)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                    Equal -> when (val matchVal8 = kmCompareValues(((this[8] as W)), (other[8] as W)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                        Equal -> when (val matchVal9 = kmCompareValues(((this[9] as V)), (other[9] as V)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                            Equal -> when (val matchVal10 = kmCompareValues(((this[10] as U)), (other[10] as U)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
                                                Equal -> kmCompareValues(((this[11] as T)), (other[11] as T)).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }
                                                else -> {
                                                    val ordering = matchVal10
                                                    ordering
                                                }
                                            }
                                            else -> {
                                                val ordering1 = matchVal9
                                                ordering1
                                            }
                                        }
                                        else -> {
                                            val ordering2 = matchVal8
                                            ordering2
                                        }
                                    }
                                    else -> {
                                        val ordering3 = matchVal7
                                        ordering3
                                    }
                                }
                                else -> {
                                    val ordering4 = matchVal6
                                    ordering4
                                }
                            }
                            else -> {
                                val ordering5 = matchVal5
                                ordering5
                            }
                        }
                        else -> {
                            val ordering6 = matchVal4
                            ordering6
                        }
                    }
                    else -> {
                        val ordering7 = matchVal3
                        ordering7
                    }
                }
                else -> {
                    val ordering8 = matchVal2
                    ordering8
                }
            }
            else -> {
                val ordering9 = matchVal1
                ordering9
            }
        }
        else -> {
            val ordering10 = matchVal
            ordering10
        }
    }

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> defaultEDCBAZYXWVUT(defaultEFn: () -> E, defaultDFn: () -> D, defaultCFn: () -> C, defaultBFn: () -> B, defaultAFn: () -> A, defaultZFn: () -> Z, defaultYFn: () -> Y, defaultXFn: () -> X, defaultWFn: () -> W, defaultVFn: () -> V, defaultUFn: () -> U, defaultTFn: () -> T): List<Any?> =
    listOf(run {
        val x: E = defaultEFn()
        x
    }, run {
        val x1: D = defaultDFn()
        x1
    }, run {
        val x2: C = defaultCFn()
        x2
    }, run {
        val x3: B = defaultBFn()
        x3
    }, run {
        val x4: A = defaultAFn()
        x4
    }, run {
        val x5: Z = defaultZFn()
        x5
    }, run {
        val x6: Y = defaultYFn()
        x6
    }, run {
        val x7: X = defaultXFn()
        x7
    }, run {
        val x8: W = defaultWFn()
        x8
    }, run {
        val x9: V = defaultVFn()
        x9
    }, run {
        val x10: U = defaultUFn()
        x10
    }, run {
        val x11: T = defaultTFn()
        x11
    })

public fun <T> fromTTTTTTTTTTTT(array: List<T>): List<Any?> {
    val (E, D, C, B, A, Z, Y, X, W, V, U, T) = array
    return listOf(E, D, C, B, A, Z, Y, X, W, V, U, T)
}

public fun <T> fromTArray12(tuple: List<Any?>): List<T> {
    val (E, D, C, B, A, Z, Y, X, W, V, U, T) = tuple
    return floatArrayOf(E, D, C, B, A, Z, Y, X, W, V, U, T)
}

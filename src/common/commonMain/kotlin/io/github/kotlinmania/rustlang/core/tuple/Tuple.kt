// port-lint: source tuple.rs
package io.github.kotlinmania.rustlang.core.tuple

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.*
import io.github.kotlinmania.rustlang.core.ops.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow

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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> (this.field1).partialCmp(other.second)
        else -> {
            val ordering = matchVal
            ordering
        }
    }

public fun <U, T> Pair<U, T>.lt(other: Pair<U, T>): Boolean =
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> this.field1.lt(other.second)
    }

public fun <U, T> Pair<U, T>.le(other: Pair<U, T>): Boolean =
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> this.field1.le(other.second)
    }

public fun <U, T> Pair<U, T>.ge(other: Pair<U, T>): Boolean =
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> this.field1.ge(other.second)
    }

public fun <U, T> Pair<U, T>.gt(other: Pair<U, T>): Boolean =
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> this.field1.gt(other.second)
    }

public fun <U, T> Pair<U, T>.chainingLess(other: Pair<U, T>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        Continue()
    }
}

public fun <U, T> Pair<U, T>.chainingGreater(other: Pair<U, T>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        Continue()
    }
}

public fun <U, T> Pair<U, T>.cmp(other: Pair<U, T>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> (this.field1).cmp(other.second)
        else -> {
            val ordering = matchVal
            ordering
        }
    }

public fun <U, T> defaultUT(): Pair<U, T> =
    Pair(run {
        val x: U = Default.default()
        x
    }, run {
        val x1: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> (this.field2).partialCmp(other.third)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> this.field2.lt(other.third)
            }
        }
    }

public fun <V, U, T> Triple<V, U, T>.le(other: Triple<V, U, T>): Boolean =
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> this.field2.le(other.third)
            }
        }
    }

public fun <V, U, T> Triple<V, U, T>.ge(other: Triple<V, U, T>): Boolean =
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> this.field2.ge(other.third)
            }
        }
    }

public fun <V, U, T> Triple<V, U, T>.gt(other: Triple<V, U, T>): Boolean =
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> this.field2.gt(other.third)
            }
        }
    }

public fun <V, U, T> Triple<V, U, T>.chainingLess(other: Triple<V, U, T>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        Continue()
    }
}

public fun <V, U, T> Triple<V, U, T>.chainingGreater(other: Triple<V, U, T>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        Continue()
    }
}

public fun <V, U, T> Triple<V, U, T>.cmp(other: Triple<V, U, T>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> (this.field2).cmp(other.third)
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

public fun <V, U, T> defaultVUT(): Triple<V, U, T> =
    Triple(run {
        val x: V = Default.default()
        x
    }, run {
        val x1: U = Default.default()
        x1
    }, run {
        val x2: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).partialCmp(other.third)) {
                Equal -> (this.field3).partialCmp(other.field3)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> this.field3.lt(other.field3)
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.le(other: List<Any?>): Boolean =
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> this.field3.le(other.field3)
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.ge(other: List<Any?>): Boolean =
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> this.field3.ge(other.field3)
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.gt(other: List<Any?>): Boolean =
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> this.field3.gt(other.field3)
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.chainingLess(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        if (this.field3.chainingLess(other.field3) is Break) {
            val r3 = this.field3.chainingLess(other.field3)
            return r3
        }
        Continue()
    }
}

public fun <W, V, U, T> List<Any?>.chainingGreater(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        if (this.field3.chainingGreater(other.field3) is Break) {
            val r3 = this.field3.chainingGreater(other.field3)
            return r3
        }
        Continue()
    }
}

public fun <W, V, U, T> List<Any?>.cmp(other: List<Any?>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).cmp(other.third)) {
                Equal -> (this.field3).cmp(other.field3)
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

public fun <W, V, U, T> defaultWVUT(): List<Any?> =
    listOf(run {
        val x: W = Default.default()
        x
    }, run {
        val x1: V = Default.default()
        x1
    }, run {
        val x2: U = Default.default()
        x2
    }, run {
        val x3: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).partialCmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).partialCmp(other.field3)) {
                    Equal -> (this.field4).partialCmp(other.field4)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> this.field4.lt(other.field4)
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.leFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> this.field4.le(other.field4)
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.geFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> this.field4.ge(other.field4)
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.gtFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> this.field4.gt(other.field4)
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.chainingLessFromXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        if (this.field3.chainingLess(other.field3) is Break) {
            val r3 = this.field3.chainingLess(other.field3)
            return r3
        }
        if (this.field4.chainingLess(other.field4) is Break) {
            val r4 = this.field4.chainingLess(other.field4)
            return r4
        }
        Continue()
    }
}

public fun <X, W, V, U, T> List<Any?>.chainingGreaterFromXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        if (this.field3.chainingGreater(other.field3) is Break) {
            val r3 = this.field3.chainingGreater(other.field3)
            return r3
        }
        if (this.field4.chainingGreater(other.field4) is Break) {
            val r4 = this.field4.chainingGreater(other.field4)
            return r4
        }
        Continue()
    }
}

public fun <X, W, V, U, T> List<Any?>.cmpFromXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).cmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).cmp(other.field3)) {
                    Equal -> (this.field4).cmp(other.field4)
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

public fun <X, W, V, U, T> defaultXWVUT(): List<Any?> =
    listOf(run {
        val x: X = Default.default()
        x
    }, run {
        val x1: W = Default.default()
        x1
    }, run {
        val x2: V = Default.default()
        x2
    }, run {
        val x3: U = Default.default()
        x3
    }, run {
        val x4: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).partialCmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).partialCmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).partialCmp(other.field4)) {
                        Equal -> (this.field5).partialCmp(other.field5)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> this.field5.lt(other.field5)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> this.field5.le(other.field5)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> this.field5.ge(other.field5)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> this.field5.gt(other.field5)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

public fun <Y, X, W, V, U, T> List<Any?>.chainingLessFromYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        if (this.field3.chainingLess(other.field3) is Break) {
            val r3 = this.field3.chainingLess(other.field3)
            return r3
        }
        if (this.field4.chainingLess(other.field4) is Break) {
            val r4 = this.field4.chainingLess(other.field4)
            return r4
        }
        if (this.field5.chainingLess(other.field5) is Break) {
            val r5 = this.field5.chainingLess(other.field5)
            return r5
        }
        Continue()
    }
}

public fun <Y, X, W, V, U, T> List<Any?>.chainingGreaterFromYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        if (this.field3.chainingGreater(other.field3) is Break) {
            val r3 = this.field3.chainingGreater(other.field3)
            return r3
        }
        if (this.field4.chainingGreater(other.field4) is Break) {
            val r4 = this.field4.chainingGreater(other.field4)
            return r4
        }
        if (this.field5.chainingGreater(other.field5) is Break) {
            val r5 = this.field5.chainingGreater(other.field5)
            return r5
        }
        Continue()
    }
}

public fun <Y, X, W, V, U, T> List<Any?>.cmpFromYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).cmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).cmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).cmp(other.field4)) {
                        Equal -> (this.field5).cmp(other.field5)
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

public fun <Y, X, W, V, U, T> defaultYXWVUT(): List<Any?> =
    listOf(run {
        val x: Y = Default.default()
        x
    }, run {
        val x1: X = Default.default()
        x1
    }, run {
        val x2: W = Default.default()
        x2
    }, run {
        val x3: V = Default.default()
        x3
    }, run {
        val x4: U = Default.default()
        x4
    }, run {
        val x5: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).partialCmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).partialCmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).partialCmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).partialCmp(other.field5)) {
                            Equal -> (this.field6).partialCmp(other.field6)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> this.field6.lt(other.field6)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> this.field6.le(other.field6)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> this.field6.ge(other.field6)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> this.field6.gt(other.field6)
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

public fun <Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        if (this.field3.chainingLess(other.field3) is Break) {
            val r3 = this.field3.chainingLess(other.field3)
            return r3
        }
        if (this.field4.chainingLess(other.field4) is Break) {
            val r4 = this.field4.chainingLess(other.field4)
            return r4
        }
        if (this.field5.chainingLess(other.field5) is Break) {
            val r5 = this.field5.chainingLess(other.field5)
            return r5
        }
        if (this.field6.chainingLess(other.field6) is Break) {
            val r6 = this.field6.chainingLess(other.field6)
            return r6
        }
        Continue()
    }
}

public fun <Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        if (this.field3.chainingGreater(other.field3) is Break) {
            val r3 = this.field3.chainingGreater(other.field3)
            return r3
        }
        if (this.field4.chainingGreater(other.field4) is Break) {
            val r4 = this.field4.chainingGreater(other.field4)
            return r4
        }
        if (this.field5.chainingGreater(other.field5) is Break) {
            val r5 = this.field5.chainingGreater(other.field5)
            return r5
        }
        if (this.field6.chainingGreater(other.field6) is Break) {
            val r6 = this.field6.chainingGreater(other.field6)
            return r6
        }
        Continue()
    }
}

public fun <Z, Y, X, W, V, U, T> List<Any?>.cmpFromZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).cmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).cmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).cmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).cmp(other.field5)) {
                            Equal -> (this.field6).cmp(other.field6)
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

public fun <Z, Y, X, W, V, U, T> defaultZYXWVUT(): List<Any?> =
    listOf(run {
        val x: Z = Default.default()
        x
    }, run {
        val x1: Y = Default.default()
        x1
    }, run {
        val x2: X = Default.default()
        x2
    }, run {
        val x3: W = Default.default()
        x3
    }, run {
        val x4: V = Default.default()
        x4
    }, run {
        val x5: U = Default.default()
        x5
    }, run {
        val x6: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).partialCmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).partialCmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).partialCmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).partialCmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).partialCmp(other.field6)) {
                                Equal -> (this.field7).partialCmp(other.field7)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> this.field7.lt(other.field7)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> this.field7.le(other.field7)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> this.field7.ge(other.field7)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> this.field7.gt(other.field7)
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

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        if (this.field3.chainingLess(other.field3) is Break) {
            val r3 = this.field3.chainingLess(other.field3)
            return r3
        }
        if (this.field4.chainingLess(other.field4) is Break) {
            val r4 = this.field4.chainingLess(other.field4)
            return r4
        }
        if (this.field5.chainingLess(other.field5) is Break) {
            val r5 = this.field5.chainingLess(other.field5)
            return r5
        }
        if (this.field6.chainingLess(other.field6) is Break) {
            val r6 = this.field6.chainingLess(other.field6)
            return r6
        }
        if (this.field7.chainingLess(other.field7) is Break) {
            val r7 = this.field7.chainingLess(other.field7)
            return r7
        }
        Continue()
    }
}

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        if (this.field3.chainingGreater(other.field3) is Break) {
            val r3 = this.field3.chainingGreater(other.field3)
            return r3
        }
        if (this.field4.chainingGreater(other.field4) is Break) {
            val r4 = this.field4.chainingGreater(other.field4)
            return r4
        }
        if (this.field5.chainingGreater(other.field5) is Break) {
            val r5 = this.field5.chainingGreater(other.field5)
            return r5
        }
        if (this.field6.chainingGreater(other.field6) is Break) {
            val r6 = this.field6.chainingGreater(other.field6)
            return r6
        }
        if (this.field7.chainingGreater(other.field7) is Break) {
            val r7 = this.field7.chainingGreater(other.field7)
            return r7
        }
        Continue()
    }
}

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).cmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).cmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).cmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).cmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).cmp(other.field6)) {
                                Equal -> (this.field7).cmp(other.field7)
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

public fun <A, Z, Y, X, W, V, U, T> defaultAZYXWVUT(): List<Any?> =
    listOf(run {
        val x: A = Default.default()
        x
    }, run {
        val x1: Z = Default.default()
        x1
    }, run {
        val x2: Y = Default.default()
        x2
    }, run {
        val x3: X = Default.default()
        x3
    }, run {
        val x4: W = Default.default()
        x4
    }, run {
        val x5: V = Default.default()
        x5
    }, run {
        val x6: U = Default.default()
        x6
    }, run {
        val x7: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).partialCmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).partialCmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).partialCmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).partialCmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).partialCmp(other.field6)) {
                                Equal -> when (val matchVal7 = (this.field7).partialCmp(other.field7)) {
                                    Equal -> (this.field8).partialCmp(other.field8)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingLess(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> this.field8.lt(other.field8)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingLess(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> this.field8.le(other.field8)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingGreater(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> this.field8.ge(other.field8)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingGreater(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> this.field8.gt(other.field8)
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

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        if (this.field3.chainingLess(other.field3) is Break) {
            val r3 = this.field3.chainingLess(other.field3)
            return r3
        }
        if (this.field4.chainingLess(other.field4) is Break) {
            val r4 = this.field4.chainingLess(other.field4)
            return r4
        }
        if (this.field5.chainingLess(other.field5) is Break) {
            val r5 = this.field5.chainingLess(other.field5)
            return r5
        }
        if (this.field6.chainingLess(other.field6) is Break) {
            val r6 = this.field6.chainingLess(other.field6)
            return r6
        }
        if (this.field7.chainingLess(other.field7) is Break) {
            val r7 = this.field7.chainingLess(other.field7)
            return r7
        }
        if (this.field8.chainingLess(other.field8) is Break) {
            val r8 = this.field8.chainingLess(other.field8)
            return r8
        }
        Continue()
    }
}

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        if (this.field3.chainingGreater(other.field3) is Break) {
            val r3 = this.field3.chainingGreater(other.field3)
            return r3
        }
        if (this.field4.chainingGreater(other.field4) is Break) {
            val r4 = this.field4.chainingGreater(other.field4)
            return r4
        }
        if (this.field5.chainingGreater(other.field5) is Break) {
            val r5 = this.field5.chainingGreater(other.field5)
            return r5
        }
        if (this.field6.chainingGreater(other.field6) is Break) {
            val r6 = this.field6.chainingGreater(other.field6)
            return r6
        }
        if (this.field7.chainingGreater(other.field7) is Break) {
            val r7 = this.field7.chainingGreater(other.field7)
            return r7
        }
        if (this.field8.chainingGreater(other.field8) is Break) {
            val r8 = this.field8.chainingGreater(other.field8)
            return r8
        }
        Continue()
    }
}

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).cmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).cmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).cmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).cmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).cmp(other.field6)) {
                                Equal -> when (val matchVal7 = (this.field7).cmp(other.field7)) {
                                    Equal -> (this.field8).cmp(other.field8)
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

public fun <B, A, Z, Y, X, W, V, U, T> defaultBAZYXWVUT(): List<Any?> =
    listOf(run {
        val x: B = Default.default()
        x
    }, run {
        val x1: A = Default.default()
        x1
    }, run {
        val x2: Z = Default.default()
        x2
    }, run {
        val x3: Y = Default.default()
        x3
    }, run {
        val x4: X = Default.default()
        x4
    }, run {
        val x5: W = Default.default()
        x5
    }, run {
        val x6: V = Default.default()
        x6
    }, run {
        val x7: U = Default.default()
        x7
    }, run {
        val x8: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).partialCmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).partialCmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).partialCmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).partialCmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).partialCmp(other.field6)) {
                                Equal -> when (val matchVal7 = (this.field7).partialCmp(other.field7)) {
                                    Equal -> when (val matchVal8 = (this.field8).partialCmp(other.field8)) {
                                        Equal -> (this.field9).partialCmp(other.field9)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingLess(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingLess(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> this.field9.lt(other.field9)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingLess(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingLess(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> this.field9.le(other.field9)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingGreater(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingGreater(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> this.field9.ge(other.field9)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingGreater(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingGreater(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> this.field9.gt(other.field9)
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

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        if (this.field3.chainingLess(other.field3) is Break) {
            val r3 = this.field3.chainingLess(other.field3)
            return r3
        }
        if (this.field4.chainingLess(other.field4) is Break) {
            val r4 = this.field4.chainingLess(other.field4)
            return r4
        }
        if (this.field5.chainingLess(other.field5) is Break) {
            val r5 = this.field5.chainingLess(other.field5)
            return r5
        }
        if (this.field6.chainingLess(other.field6) is Break) {
            val r6 = this.field6.chainingLess(other.field6)
            return r6
        }
        if (this.field7.chainingLess(other.field7) is Break) {
            val r7 = this.field7.chainingLess(other.field7)
            return r7
        }
        if (this.field8.chainingLess(other.field8) is Break) {
            val r8 = this.field8.chainingLess(other.field8)
            return r8
        }
        if (this.field9.chainingLess(other.field9) is Break) {
            val r9 = this.field9.chainingLess(other.field9)
            return r9
        }
        Continue()
    }
}

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        if (this.field3.chainingGreater(other.field3) is Break) {
            val r3 = this.field3.chainingGreater(other.field3)
            return r3
        }
        if (this.field4.chainingGreater(other.field4) is Break) {
            val r4 = this.field4.chainingGreater(other.field4)
            return r4
        }
        if (this.field5.chainingGreater(other.field5) is Break) {
            val r5 = this.field5.chainingGreater(other.field5)
            return r5
        }
        if (this.field6.chainingGreater(other.field6) is Break) {
            val r6 = this.field6.chainingGreater(other.field6)
            return r6
        }
        if (this.field7.chainingGreater(other.field7) is Break) {
            val r7 = this.field7.chainingGreater(other.field7)
            return r7
        }
        if (this.field8.chainingGreater(other.field8) is Break) {
            val r8 = this.field8.chainingGreater(other.field8)
            return r8
        }
        if (this.field9.chainingGreater(other.field9) is Break) {
            val r9 = this.field9.chainingGreater(other.field9)
            return r9
        }
        Continue()
    }
}

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromCBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).cmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).cmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).cmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).cmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).cmp(other.field6)) {
                                Equal -> when (val matchVal7 = (this.field7).cmp(other.field7)) {
                                    Equal -> when (val matchVal8 = (this.field8).cmp(other.field8)) {
                                        Equal -> (this.field9).cmp(other.field9)
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

public fun <C, B, A, Z, Y, X, W, V, U, T> defaultCBAZYXWVUT(): List<Any?> =
    listOf(run {
        val x: C = Default.default()
        x
    }, run {
        val x1: B = Default.default()
        x1
    }, run {
        val x2: A = Default.default()
        x2
    }, run {
        val x3: Z = Default.default()
        x3
    }, run {
        val x4: Y = Default.default()
        x4
    }, run {
        val x5: X = Default.default()
        x5
    }, run {
        val x6: W = Default.default()
        x6
    }, run {
        val x7: V = Default.default()
        x7
    }, run {
        val x8: U = Default.default()
        x8
    }, run {
        val x9: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).partialCmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).partialCmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).partialCmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).partialCmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).partialCmp(other.field6)) {
                                Equal -> when (val matchVal7 = (this.field7).partialCmp(other.field7)) {
                                    Equal -> when (val matchVal8 = (this.field8).partialCmp(other.field8)) {
                                        Equal -> when (val matchVal9 = (this.field9).partialCmp(other.field9)) {
                                            Equal -> (this.field10).partialCmp(other.field10)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingLess(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingLess(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = this.field9.chainingLess(other.field9)) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> this.field10.lt(other.field10)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingLess(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingLess(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = this.field9.chainingLess(other.field9)) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> this.field10.le(other.field10)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingGreater(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingGreater(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = this.field9.chainingGreater(other.field9)) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> this.field10.ge(other.field10)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingGreater(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingGreater(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = this.field9.chainingGreater(other.field9)) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> this.field10.gt(other.field10)
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

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        if (this.field3.chainingLess(other.field3) is Break) {
            val r3 = this.field3.chainingLess(other.field3)
            return r3
        }
        if (this.field4.chainingLess(other.field4) is Break) {
            val r4 = this.field4.chainingLess(other.field4)
            return r4
        }
        if (this.field5.chainingLess(other.field5) is Break) {
            val r5 = this.field5.chainingLess(other.field5)
            return r5
        }
        if (this.field6.chainingLess(other.field6) is Break) {
            val r6 = this.field6.chainingLess(other.field6)
            return r6
        }
        if (this.field7.chainingLess(other.field7) is Break) {
            val r7 = this.field7.chainingLess(other.field7)
            return r7
        }
        if (this.field8.chainingLess(other.field8) is Break) {
            val r8 = this.field8.chainingLess(other.field8)
            return r8
        }
        if (this.field9.chainingLess(other.field9) is Break) {
            val r9 = this.field9.chainingLess(other.field9)
            return r9
        }
        if (this.field10.chainingLess(other.field10) is Break) {
            val r10 = this.field10.chainingLess(other.field10)
            return r10
        }
        Continue()
    }
}

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        if (this.field3.chainingGreater(other.field3) is Break) {
            val r3 = this.field3.chainingGreater(other.field3)
            return r3
        }
        if (this.field4.chainingGreater(other.field4) is Break) {
            val r4 = this.field4.chainingGreater(other.field4)
            return r4
        }
        if (this.field5.chainingGreater(other.field5) is Break) {
            val r5 = this.field5.chainingGreater(other.field5)
            return r5
        }
        if (this.field6.chainingGreater(other.field6) is Break) {
            val r6 = this.field6.chainingGreater(other.field6)
            return r6
        }
        if (this.field7.chainingGreater(other.field7) is Break) {
            val r7 = this.field7.chainingGreater(other.field7)
            return r7
        }
        if (this.field8.chainingGreater(other.field8) is Break) {
            val r8 = this.field8.chainingGreater(other.field8)
            return r8
        }
        if (this.field9.chainingGreater(other.field9) is Break) {
            val r9 = this.field9.chainingGreater(other.field9)
            return r9
        }
        if (this.field10.chainingGreater(other.field10) is Break) {
            val r10 = this.field10.chainingGreater(other.field10)
            return r10
        }
        Continue()
    }
}

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromDCBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).cmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).cmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).cmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).cmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).cmp(other.field6)) {
                                Equal -> when (val matchVal7 = (this.field7).cmp(other.field7)) {
                                    Equal -> when (val matchVal8 = (this.field8).cmp(other.field8)) {
                                        Equal -> when (val matchVal9 = (this.field9).cmp(other.field9)) {
                                            Equal -> (this.field10).cmp(other.field10)
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

public fun <D, C, B, A, Z, Y, X, W, V, U, T> defaultDCBAZYXWVUT(): List<Any?> =
    listOf(run {
        val x: D = Default.default()
        x
    }, run {
        val x1: C = Default.default()
        x1
    }, run {
        val x2: B = Default.default()
        x2
    }, run {
        val x3: A = Default.default()
        x3
    }, run {
        val x4: Z = Default.default()
        x4
    }, run {
        val x5: Y = Default.default()
        x5
    }, run {
        val x6: X = Default.default()
        x6
    }, run {
        val x7: W = Default.default()
        x7
    }, run {
        val x8: V = Default.default()
        x8
    }, run {
        val x9: U = Default.default()
        x9
    }, run {
        val x10: T = Default.default()
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
    when (val matchVal = (this.value).partialCmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).partialCmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).partialCmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).partialCmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).partialCmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).partialCmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).partialCmp(other.field6)) {
                                Equal -> when (val matchVal7 = (this.field7).partialCmp(other.field7)) {
                                    Equal -> when (val matchVal8 = (this.field8).partialCmp(other.field8)) {
                                        Equal -> when (val matchVal9 = (this.field9).partialCmp(other.field9)) {
                                            Equal -> when (val matchVal10 = (this.field10).partialCmp(other.field10)) {
                                                Equal -> (this.field11).partialCmp(other.field11)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingLess(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingLess(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = this.field9.chainingLess(other.field9)) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> {
                                                                                    when (val matchVal10 = this.field10.chainingLess(other.field10)) {
                                                                                        is Break -> {
                                                                                            val (value10) = matchVal10
                                                                                            value10
                                                                                        }
                                                                                        is Continue -> this.field11.lt(other.field11)
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
    when (val matchVal = this.value.chainingLess(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingLess(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingLess(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingLess(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingLess(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingLess(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingLess(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingLess(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingLess(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = this.field9.chainingLess(other.field9)) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> {
                                                                                    when (val matchVal10 = this.field10.chainingLess(other.field10)) {
                                                                                        is Break -> {
                                                                                            val (value10) = matchVal10
                                                                                            value10
                                                                                        }
                                                                                        is Continue -> this.field11.le(other.field11)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingGreater(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingGreater(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = this.field9.chainingGreater(other.field9)) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> {
                                                                                    when (val matchVal10 = this.field10.chainingGreater(other.field10)) {
                                                                                        is Break -> {
                                                                                            val (value10) = matchVal10
                                                                                            value10
                                                                                        }
                                                                                        is Continue -> this.field11.ge(other.field11)
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
    when (val matchVal = this.value.chainingGreater(other.first)) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = this.field1.chainingGreater(other.second)) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = this.field2.chainingGreater(other.third)) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = this.field3.chainingGreater(other.field3)) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = this.field4.chainingGreater(other.field4)) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = this.field5.chainingGreater(other.field5)) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = this.field6.chainingGreater(other.field6)) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = this.field7.chainingGreater(other.field7)) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = this.field8.chainingGreater(other.field8)) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = this.field9.chainingGreater(other.field9)) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> {
                                                                                    when (val matchVal10 = this.field10.chainingGreater(other.field10)) {
                                                                                        is Break -> {
                                                                                            val (value10) = matchVal10
                                                                                            value10
                                                                                        }
                                                                                        is Continue -> this.field11.gt(other.field11)
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

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingLessFromEDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingLess(other.first) is Break) {
            val r = this.value.chainingLess(other.first)
            return r
        }
        if (this.field1.chainingLess(other.second) is Break) {
            val r1 = this.field1.chainingLess(other.second)
            return r1
        }
        if (this.field2.chainingLess(other.third) is Break) {
            val r2 = this.field2.chainingLess(other.third)
            return r2
        }
        if (this.field3.chainingLess(other.field3) is Break) {
            val r3 = this.field3.chainingLess(other.field3)
            return r3
        }
        if (this.field4.chainingLess(other.field4) is Break) {
            val r4 = this.field4.chainingLess(other.field4)
            return r4
        }
        if (this.field5.chainingLess(other.field5) is Break) {
            val r5 = this.field5.chainingLess(other.field5)
            return r5
        }
        if (this.field6.chainingLess(other.field6) is Break) {
            val r6 = this.field6.chainingLess(other.field6)
            return r6
        }
        if (this.field7.chainingLess(other.field7) is Break) {
            val r7 = this.field7.chainingLess(other.field7)
            return r7
        }
        if (this.field8.chainingLess(other.field8) is Break) {
            val r8 = this.field8.chainingLess(other.field8)
            return r8
        }
        if (this.field9.chainingLess(other.field9) is Break) {
            val r9 = this.field9.chainingLess(other.field9)
            return r9
        }
        if (this.field10.chainingLess(other.field10) is Break) {
            val r10 = this.field10.chainingLess(other.field10)
            return r10
        }
        if (this.field11.chainingLess(other.field11) is Break) {
            val r11 = this.field11.chainingLess(other.field11)
            return r11
        }
        Continue()
    }
}

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromEDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean> {
    return run {
        if (this.value.chainingGreater(other.first) is Break) {
            val r = this.value.chainingGreater(other.first)
            return r
        }
        if (this.field1.chainingGreater(other.second) is Break) {
            val r1 = this.field1.chainingGreater(other.second)
            return r1
        }
        if (this.field2.chainingGreater(other.third) is Break) {
            val r2 = this.field2.chainingGreater(other.third)
            return r2
        }
        if (this.field3.chainingGreater(other.field3) is Break) {
            val r3 = this.field3.chainingGreater(other.field3)
            return r3
        }
        if (this.field4.chainingGreater(other.field4) is Break) {
            val r4 = this.field4.chainingGreater(other.field4)
            return r4
        }
        if (this.field5.chainingGreater(other.field5) is Break) {
            val r5 = this.field5.chainingGreater(other.field5)
            return r5
        }
        if (this.field6.chainingGreater(other.field6) is Break) {
            val r6 = this.field6.chainingGreater(other.field6)
            return r6
        }
        if (this.field7.chainingGreater(other.field7) is Break) {
            val r7 = this.field7.chainingGreater(other.field7)
            return r7
        }
        if (this.field8.chainingGreater(other.field8) is Break) {
            val r8 = this.field8.chainingGreater(other.field8)
            return r8
        }
        if (this.field9.chainingGreater(other.field9) is Break) {
            val r9 = this.field9.chainingGreater(other.field9)
            return r9
        }
        if (this.field10.chainingGreater(other.field10) is Break) {
            val r10 = this.field10.chainingGreater(other.field10)
            return r10
        }
        if (this.field11.chainingGreater(other.field11) is Break) {
            val r11 = this.field11.chainingGreater(other.field11)
            return r11
        }
        Continue()
    }
}

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromEDCBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this.value).cmp(other.first)) {
        Equal -> when (val matchVal1 = (this.field1).cmp(other.second)) {
            Equal -> when (val matchVal2 = (this.field2).cmp(other.third)) {
                Equal -> when (val matchVal3 = (this.field3).cmp(other.field3)) {
                    Equal -> when (val matchVal4 = (this.field4).cmp(other.field4)) {
                        Equal -> when (val matchVal5 = (this.field5).cmp(other.field5)) {
                            Equal -> when (val matchVal6 = (this.field6).cmp(other.field6)) {
                                Equal -> when (val matchVal7 = (this.field7).cmp(other.field7)) {
                                    Equal -> when (val matchVal8 = (this.field8).cmp(other.field8)) {
                                        Equal -> when (val matchVal9 = (this.field9).cmp(other.field9)) {
                                            Equal -> when (val matchVal10 = (this.field10).cmp(other.field10)) {
                                                Equal -> (this.field11).cmp(other.field11)
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

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> defaultEDCBAZYXWVUT(): List<Any?> =
    listOf(run {
        val x: E = Default.default()
        x
    }, run {
        val x1: D = Default.default()
        x1
    }, run {
        val x2: C = Default.default()
        x2
    }, run {
        val x3: B = Default.default()
        x3
    }, run {
        val x4: A = Default.default()
        x4
    }, run {
        val x5: Z = Default.default()
        x5
    }, run {
        val x6: Y = Default.default()
        x6
    }, run {
        val x7: X = Default.default()
        x7
    }, run {
        val x8: W = Default.default()
        x8
    }, run {
        val x9: V = Default.default()
        x9
    }, run {
        val x10: U = Default.default()
        x10
    }, run {
        val x11: T = Default.default()
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

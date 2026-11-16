// port-lint: source tuple.rs
package io.github.kotlinmania.rustlang.core.tuple

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.*
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
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

public fun <U, T> Pair<U, T>.chainingLess(other: Pair<U, T>): ControlFlow<Boolean, Unit> {
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

public fun <U, T> Pair<U, T>.chainingGreater(other: Pair<U, T>): ControlFlow<Boolean, Unit> {
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

public fun <V, U, T> Triple<V, U, T>.chainingLess(other: Triple<V, U, T>): ControlFlow<Boolean, Unit> {
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

public fun <V, U, T> Triple<V, U, T>.chainingGreater(other: Triple<V, U, T>): ControlFlow<Boolean, Unit> {
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
    when (val matchVal = (this[0]).partialCmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).partialCmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).partialCmp(other[2])) {
                Equal -> (this[3]).partialCmp(other[3])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> (this[3]).lt(other[3])
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.le(other: List<Any?>): Boolean =
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> (this[3]).le(other[3])
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.ge(other: List<Any?>): Boolean =
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> (this[3]).ge(other[3])
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.gt(other: List<Any?>): Boolean =
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> (this[3]).gt(other[3])
                    }
                }
            }
        }
    }

public fun <W, V, U, T> List<Any?>.chainingLess(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingLess(other[0]) is Break) {
            val r = (this[0]).chainingLess(other[0])
            return r
        }
        if ((this[1]).chainingLess(other[1]) is Break) {
            val r1 = (this[1]).chainingLess(other[1])
            return r1
        }
        if ((this[2]).chainingLess(other[2]) is Break) {
            val r2 = (this[2]).chainingLess(other[2])
            return r2
        }
        if ((this[3]).chainingLess(other[3]) is Break) {
            val r3 = (this[3]).chainingLess(other[3])
            return r3
        }
        Continue()
    }
}

public fun <W, V, U, T> List<Any?>.chainingGreater(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingGreater(other[0]) is Break) {
            val r = (this[0]).chainingGreater(other[0])
            return r
        }
        if ((this[1]).chainingGreater(other[1]) is Break) {
            val r1 = (this[1]).chainingGreater(other[1])
            return r1
        }
        if ((this[2]).chainingGreater(other[2]) is Break) {
            val r2 = (this[2]).chainingGreater(other[2])
            return r2
        }
        if ((this[3]).chainingGreater(other[3]) is Break) {
            val r3 = (this[3]).chainingGreater(other[3])
            return r3
        }
        Continue()
    }
}

public fun <W, V, U, T> List<Any?>.cmp(other: List<Any?>): Ordering =
    when (val matchVal = (this[0]).cmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).cmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).cmp(other[2])) {
                Equal -> (this[3]).cmp(other[3])
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
    when (val matchVal = (this[0]).partialCmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).partialCmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).partialCmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).partialCmp(other[3])) {
                    Equal -> (this[4]).partialCmp(other[4])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> (this[4]).lt(other[4])
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.leFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> (this[4]).le(other[4])
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.geFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> (this[4]).ge(other[4])
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.gtFromXWVUT(other: List<Any?>): Boolean =
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> (this[4]).gt(other[4])
                            }
                        }
                    }
                }
            }
        }
    }

public fun <X, W, V, U, T> List<Any?>.chainingLessFromXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingLess(other[0]) is Break) {
            val r = (this[0]).chainingLess(other[0])
            return r
        }
        if ((this[1]).chainingLess(other[1]) is Break) {
            val r1 = (this[1]).chainingLess(other[1])
            return r1
        }
        if ((this[2]).chainingLess(other[2]) is Break) {
            val r2 = (this[2]).chainingLess(other[2])
            return r2
        }
        if ((this[3]).chainingLess(other[3]) is Break) {
            val r3 = (this[3]).chainingLess(other[3])
            return r3
        }
        if ((this[4]).chainingLess(other[4]) is Break) {
            val r4 = (this[4]).chainingLess(other[4])
            return r4
        }
        Continue()
    }
}

public fun <X, W, V, U, T> List<Any?>.chainingGreaterFromXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingGreater(other[0]) is Break) {
            val r = (this[0]).chainingGreater(other[0])
            return r
        }
        if ((this[1]).chainingGreater(other[1]) is Break) {
            val r1 = (this[1]).chainingGreater(other[1])
            return r1
        }
        if ((this[2]).chainingGreater(other[2]) is Break) {
            val r2 = (this[2]).chainingGreater(other[2])
            return r2
        }
        if ((this[3]).chainingGreater(other[3]) is Break) {
            val r3 = (this[3]).chainingGreater(other[3])
            return r3
        }
        if ((this[4]).chainingGreater(other[4]) is Break) {
            val r4 = (this[4]).chainingGreater(other[4])
            return r4
        }
        Continue()
    }
}

public fun <X, W, V, U, T> List<Any?>.cmpFromXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this[0]).cmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).cmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).cmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).cmp(other[3])) {
                    Equal -> (this[4]).cmp(other[4])
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
    when (val matchVal = (this[0]).partialCmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).partialCmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).partialCmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).partialCmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).partialCmp(other[4])) {
                        Equal -> (this[5]).partialCmp(other[5])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> (this[5]).lt(other[5])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> (this[5]).le(other[5])
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> (this[5]).ge(other[5])
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> (this[5]).gt(other[5])
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
        if ((this[0]).chainingLess(other[0]) is Break) {
            val r = (this[0]).chainingLess(other[0])
            return r
        }
        if ((this[1]).chainingLess(other[1]) is Break) {
            val r1 = (this[1]).chainingLess(other[1])
            return r1
        }
        if ((this[2]).chainingLess(other[2]) is Break) {
            val r2 = (this[2]).chainingLess(other[2])
            return r2
        }
        if ((this[3]).chainingLess(other[3]) is Break) {
            val r3 = (this[3]).chainingLess(other[3])
            return r3
        }
        if ((this[4]).chainingLess(other[4]) is Break) {
            val r4 = (this[4]).chainingLess(other[4])
            return r4
        }
        if ((this[5]).chainingLess(other[5]) is Break) {
            val r5 = (this[5]).chainingLess(other[5])
            return r5
        }
        Continue()
    }
}

public fun <Y, X, W, V, U, T> List<Any?>.chainingGreaterFromYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingGreater(other[0]) is Break) {
            val r = (this[0]).chainingGreater(other[0])
            return r
        }
        if ((this[1]).chainingGreater(other[1]) is Break) {
            val r1 = (this[1]).chainingGreater(other[1])
            return r1
        }
        if ((this[2]).chainingGreater(other[2]) is Break) {
            val r2 = (this[2]).chainingGreater(other[2])
            return r2
        }
        if ((this[3]).chainingGreater(other[3]) is Break) {
            val r3 = (this[3]).chainingGreater(other[3])
            return r3
        }
        if ((this[4]).chainingGreater(other[4]) is Break) {
            val r4 = (this[4]).chainingGreater(other[4])
            return r4
        }
        if ((this[5]).chainingGreater(other[5]) is Break) {
            val r5 = (this[5]).chainingGreater(other[5])
            return r5
        }
        Continue()
    }
}

public fun <Y, X, W, V, U, T> List<Any?>.cmpFromYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this[0]).cmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).cmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).cmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).cmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).cmp(other[4])) {
                        Equal -> (this[5]).cmp(other[5])
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
    when (val matchVal = (this[0]).partialCmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).partialCmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).partialCmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).partialCmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).partialCmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).partialCmp(other[5])) {
                            Equal -> (this[6]).partialCmp(other[6])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> (this[6]).lt(other[6])
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> (this[6]).le(other[6])
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> (this[6]).ge(other[6])
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> (this[6]).gt(other[6])
                                            }
                                        }
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
        if ((this[0]).chainingLess(other[0]) is Break) {
            val r = (this[0]).chainingLess(other[0])
            return r
        }
        if ((this[1]).chainingLess(other[1]) is Break) {
            val r1 = (this[1]).chainingLess(other[1])
            return r1
        }
        if ((this[2]).chainingLess(other[2]) is Break) {
            val r2 = (this[2]).chainingLess(other[2])
            return r2
        }
        if ((this[3]).chainingLess(other[3]) is Break) {
            val r3 = (this[3]).chainingLess(other[3])
            return r3
        }
        if ((this[4]).chainingLess(other[4]) is Break) {
            val r4 = (this[4]).chainingLess(other[4])
            return r4
        }
        if ((this[5]).chainingLess(other[5]) is Break) {
            val r5 = (this[5]).chainingLess(other[5])
            return r5
        }
        if ((this[6]).chainingLess(other[6]) is Break) {
            val r6 = (this[6]).chainingLess(other[6])
            return r6
        }
        Continue()
    }
}

public fun <Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingGreater(other[0]) is Break) {
            val r = (this[0]).chainingGreater(other[0])
            return r
        }
        if ((this[1]).chainingGreater(other[1]) is Break) {
            val r1 = (this[1]).chainingGreater(other[1])
            return r1
        }
        if ((this[2]).chainingGreater(other[2]) is Break) {
            val r2 = (this[2]).chainingGreater(other[2])
            return r2
        }
        if ((this[3]).chainingGreater(other[3]) is Break) {
            val r3 = (this[3]).chainingGreater(other[3])
            return r3
        }
        if ((this[4]).chainingGreater(other[4]) is Break) {
            val r4 = (this[4]).chainingGreater(other[4])
            return r4
        }
        if ((this[5]).chainingGreater(other[5]) is Break) {
            val r5 = (this[5]).chainingGreater(other[5])
            return r5
        }
        if ((this[6]).chainingGreater(other[6]) is Break) {
            val r6 = (this[6]).chainingGreater(other[6])
            return r6
        }
        Continue()
    }
}

public fun <Z, Y, X, W, V, U, T> List<Any?>.cmpFromZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this[0]).cmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).cmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).cmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).cmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).cmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).cmp(other[5])) {
                            Equal -> (this[6]).cmp(other[6])
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
    when (val matchVal = (this[0]).partialCmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).partialCmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).partialCmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).partialCmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).partialCmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).partialCmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).partialCmp(other[6])) {
                                Equal -> (this[7]).partialCmp(other[7])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> (this[7]).lt(other[7])
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> (this[7]).le(other[7])
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> (this[7]).ge(other[7])
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> (this[7]).gt(other[7])
                                                    }
                                                }
                                            }
                                        }
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
        if ((this[0]).chainingLess(other[0]) is Break) {
            val r = (this[0]).chainingLess(other[0])
            return r
        }
        if ((this[1]).chainingLess(other[1]) is Break) {
            val r1 = (this[1]).chainingLess(other[1])
            return r1
        }
        if ((this[2]).chainingLess(other[2]) is Break) {
            val r2 = (this[2]).chainingLess(other[2])
            return r2
        }
        if ((this[3]).chainingLess(other[3]) is Break) {
            val r3 = (this[3]).chainingLess(other[3])
            return r3
        }
        if ((this[4]).chainingLess(other[4]) is Break) {
            val r4 = (this[4]).chainingLess(other[4])
            return r4
        }
        if ((this[5]).chainingLess(other[5]) is Break) {
            val r5 = (this[5]).chainingLess(other[5])
            return r5
        }
        if ((this[6]).chainingLess(other[6]) is Break) {
            val r6 = (this[6]).chainingLess(other[6])
            return r6
        }
        if ((this[7]).chainingLess(other[7]) is Break) {
            val r7 = (this[7]).chainingLess(other[7])
            return r7
        }
        Continue()
    }
}

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingGreater(other[0]) is Break) {
            val r = (this[0]).chainingGreater(other[0])
            return r
        }
        if ((this[1]).chainingGreater(other[1]) is Break) {
            val r1 = (this[1]).chainingGreater(other[1])
            return r1
        }
        if ((this[2]).chainingGreater(other[2]) is Break) {
            val r2 = (this[2]).chainingGreater(other[2])
            return r2
        }
        if ((this[3]).chainingGreater(other[3]) is Break) {
            val r3 = (this[3]).chainingGreater(other[3])
            return r3
        }
        if ((this[4]).chainingGreater(other[4]) is Break) {
            val r4 = (this[4]).chainingGreater(other[4])
            return r4
        }
        if ((this[5]).chainingGreater(other[5]) is Break) {
            val r5 = (this[5]).chainingGreater(other[5])
            return r5
        }
        if ((this[6]).chainingGreater(other[6]) is Break) {
            val r6 = (this[6]).chainingGreater(other[6])
            return r6
        }
        if ((this[7]).chainingGreater(other[7]) is Break) {
            val r7 = (this[7]).chainingGreater(other[7])
            return r7
        }
        Continue()
    }
}

public fun <A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this[0]).cmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).cmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).cmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).cmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).cmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).cmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).cmp(other[6])) {
                                Equal -> (this[7]).cmp(other[7])
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
    when (val matchVal = (this[0]).partialCmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).partialCmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).partialCmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).partialCmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).partialCmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).partialCmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).partialCmp(other[6])) {
                                Equal -> when (val matchVal7 = (this[7]).partialCmp(other[7])) {
                                    Equal -> (this[8]).partialCmp(other[8])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingLess(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> (this[8]).lt(other[8])
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingLess(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> (this[8]).le(other[8])
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingGreater(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> (this[8]).ge(other[8])
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingGreater(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> (this[8]).gt(other[8])
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
        if ((this[0]).chainingLess(other[0]) is Break) {
            val r = (this[0]).chainingLess(other[0])
            return r
        }
        if ((this[1]).chainingLess(other[1]) is Break) {
            val r1 = (this[1]).chainingLess(other[1])
            return r1
        }
        if ((this[2]).chainingLess(other[2]) is Break) {
            val r2 = (this[2]).chainingLess(other[2])
            return r2
        }
        if ((this[3]).chainingLess(other[3]) is Break) {
            val r3 = (this[3]).chainingLess(other[3])
            return r3
        }
        if ((this[4]).chainingLess(other[4]) is Break) {
            val r4 = (this[4]).chainingLess(other[4])
            return r4
        }
        if ((this[5]).chainingLess(other[5]) is Break) {
            val r5 = (this[5]).chainingLess(other[5])
            return r5
        }
        if ((this[6]).chainingLess(other[6]) is Break) {
            val r6 = (this[6]).chainingLess(other[6])
            return r6
        }
        if ((this[7]).chainingLess(other[7]) is Break) {
            val r7 = (this[7]).chainingLess(other[7])
            return r7
        }
        if ((this[8]).chainingLess(other[8]) is Break) {
            val r8 = (this[8]).chainingLess(other[8])
            return r8
        }
        Continue()
    }
}

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingGreater(other[0]) is Break) {
            val r = (this[0]).chainingGreater(other[0])
            return r
        }
        if ((this[1]).chainingGreater(other[1]) is Break) {
            val r1 = (this[1]).chainingGreater(other[1])
            return r1
        }
        if ((this[2]).chainingGreater(other[2]) is Break) {
            val r2 = (this[2]).chainingGreater(other[2])
            return r2
        }
        if ((this[3]).chainingGreater(other[3]) is Break) {
            val r3 = (this[3]).chainingGreater(other[3])
            return r3
        }
        if ((this[4]).chainingGreater(other[4]) is Break) {
            val r4 = (this[4]).chainingGreater(other[4])
            return r4
        }
        if ((this[5]).chainingGreater(other[5]) is Break) {
            val r5 = (this[5]).chainingGreater(other[5])
            return r5
        }
        if ((this[6]).chainingGreater(other[6]) is Break) {
            val r6 = (this[6]).chainingGreater(other[6])
            return r6
        }
        if ((this[7]).chainingGreater(other[7]) is Break) {
            val r7 = (this[7]).chainingGreater(other[7])
            return r7
        }
        if ((this[8]).chainingGreater(other[8]) is Break) {
            val r8 = (this[8]).chainingGreater(other[8])
            return r8
        }
        Continue()
    }
}

public fun <B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this[0]).cmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).cmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).cmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).cmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).cmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).cmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).cmp(other[6])) {
                                Equal -> when (val matchVal7 = (this[7]).cmp(other[7])) {
                                    Equal -> (this[8]).cmp(other[8])
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
    when (val matchVal = (this[0]).partialCmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).partialCmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).partialCmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).partialCmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).partialCmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).partialCmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).partialCmp(other[6])) {
                                Equal -> when (val matchVal7 = (this[7]).partialCmp(other[7])) {
                                    Equal -> when (val matchVal8 = (this[8]).partialCmp(other[8])) {
                                        Equal -> (this[9]).partialCmp(other[9])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingLess(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingLess(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> (this[9]).lt(other[9])
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingLess(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingLess(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> (this[9]).le(other[9])
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingGreater(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingGreater(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> (this[9]).ge(other[9])
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingGreater(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingGreater(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> (this[9]).gt(other[9])
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
        if ((this[0]).chainingLess(other[0]) is Break) {
            val r = (this[0]).chainingLess(other[0])
            return r
        }
        if ((this[1]).chainingLess(other[1]) is Break) {
            val r1 = (this[1]).chainingLess(other[1])
            return r1
        }
        if ((this[2]).chainingLess(other[2]) is Break) {
            val r2 = (this[2]).chainingLess(other[2])
            return r2
        }
        if ((this[3]).chainingLess(other[3]) is Break) {
            val r3 = (this[3]).chainingLess(other[3])
            return r3
        }
        if ((this[4]).chainingLess(other[4]) is Break) {
            val r4 = (this[4]).chainingLess(other[4])
            return r4
        }
        if ((this[5]).chainingLess(other[5]) is Break) {
            val r5 = (this[5]).chainingLess(other[5])
            return r5
        }
        if ((this[6]).chainingLess(other[6]) is Break) {
            val r6 = (this[6]).chainingLess(other[6])
            return r6
        }
        if ((this[7]).chainingLess(other[7]) is Break) {
            val r7 = (this[7]).chainingLess(other[7])
            return r7
        }
        if ((this[8]).chainingLess(other[8]) is Break) {
            val r8 = (this[8]).chainingLess(other[8])
            return r8
        }
        if ((this[9]).chainingLess(other[9]) is Break) {
            val r9 = (this[9]).chainingLess(other[9])
            return r9
        }
        Continue()
    }
}

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingGreater(other[0]) is Break) {
            val r = (this[0]).chainingGreater(other[0])
            return r
        }
        if ((this[1]).chainingGreater(other[1]) is Break) {
            val r1 = (this[1]).chainingGreater(other[1])
            return r1
        }
        if ((this[2]).chainingGreater(other[2]) is Break) {
            val r2 = (this[2]).chainingGreater(other[2])
            return r2
        }
        if ((this[3]).chainingGreater(other[3]) is Break) {
            val r3 = (this[3]).chainingGreater(other[3])
            return r3
        }
        if ((this[4]).chainingGreater(other[4]) is Break) {
            val r4 = (this[4]).chainingGreater(other[4])
            return r4
        }
        if ((this[5]).chainingGreater(other[5]) is Break) {
            val r5 = (this[5]).chainingGreater(other[5])
            return r5
        }
        if ((this[6]).chainingGreater(other[6]) is Break) {
            val r6 = (this[6]).chainingGreater(other[6])
            return r6
        }
        if ((this[7]).chainingGreater(other[7]) is Break) {
            val r7 = (this[7]).chainingGreater(other[7])
            return r7
        }
        if ((this[8]).chainingGreater(other[8]) is Break) {
            val r8 = (this[8]).chainingGreater(other[8])
            return r8
        }
        if ((this[9]).chainingGreater(other[9]) is Break) {
            val r9 = (this[9]).chainingGreater(other[9])
            return r9
        }
        Continue()
    }
}

public fun <C, B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromCBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this[0]).cmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).cmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).cmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).cmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).cmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).cmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).cmp(other[6])) {
                                Equal -> when (val matchVal7 = (this[7]).cmp(other[7])) {
                                    Equal -> when (val matchVal8 = (this[8]).cmp(other[8])) {
                                        Equal -> (this[9]).cmp(other[9])
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
    when (val matchVal = (this[0]).partialCmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).partialCmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).partialCmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).partialCmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).partialCmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).partialCmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).partialCmp(other[6])) {
                                Equal -> when (val matchVal7 = (this[7]).partialCmp(other[7])) {
                                    Equal -> when (val matchVal8 = (this[8]).partialCmp(other[8])) {
                                        Equal -> when (val matchVal9 = (this[9]).partialCmp(other[9])) {
                                            Equal -> (this[10]).partialCmp(other[10])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingLess(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingLess(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = (this[9]).chainingLess(other[9])) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> (this[10]).lt(other[10])
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingLess(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingLess(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = (this[9]).chainingLess(other[9])) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> (this[10]).le(other[10])
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingGreater(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingGreater(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = (this[9]).chainingGreater(other[9])) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> (this[10]).ge(other[10])
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingGreater(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingGreater(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = (this[9]).chainingGreater(other[9])) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> (this[10]).gt(other[10])
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
        if ((this[0]).chainingLess(other[0]) is Break) {
            val r = (this[0]).chainingLess(other[0])
            return r
        }
        if ((this[1]).chainingLess(other[1]) is Break) {
            val r1 = (this[1]).chainingLess(other[1])
            return r1
        }
        if ((this[2]).chainingLess(other[2]) is Break) {
            val r2 = (this[2]).chainingLess(other[2])
            return r2
        }
        if ((this[3]).chainingLess(other[3]) is Break) {
            val r3 = (this[3]).chainingLess(other[3])
            return r3
        }
        if ((this[4]).chainingLess(other[4]) is Break) {
            val r4 = (this[4]).chainingLess(other[4])
            return r4
        }
        if ((this[5]).chainingLess(other[5]) is Break) {
            val r5 = (this[5]).chainingLess(other[5])
            return r5
        }
        if ((this[6]).chainingLess(other[6]) is Break) {
            val r6 = (this[6]).chainingLess(other[6])
            return r6
        }
        if ((this[7]).chainingLess(other[7]) is Break) {
            val r7 = (this[7]).chainingLess(other[7])
            return r7
        }
        if ((this[8]).chainingLess(other[8]) is Break) {
            val r8 = (this[8]).chainingLess(other[8])
            return r8
        }
        if ((this[9]).chainingLess(other[9]) is Break) {
            val r9 = (this[9]).chainingLess(other[9])
            return r9
        }
        if ((this[10]).chainingLess(other[10]) is Break) {
            val r10 = (this[10]).chainingLess(other[10])
            return r10
        }
        Continue()
    }
}

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingGreater(other[0]) is Break) {
            val r = (this[0]).chainingGreater(other[0])
            return r
        }
        if ((this[1]).chainingGreater(other[1]) is Break) {
            val r1 = (this[1]).chainingGreater(other[1])
            return r1
        }
        if ((this[2]).chainingGreater(other[2]) is Break) {
            val r2 = (this[2]).chainingGreater(other[2])
            return r2
        }
        if ((this[3]).chainingGreater(other[3]) is Break) {
            val r3 = (this[3]).chainingGreater(other[3])
            return r3
        }
        if ((this[4]).chainingGreater(other[4]) is Break) {
            val r4 = (this[4]).chainingGreater(other[4])
            return r4
        }
        if ((this[5]).chainingGreater(other[5]) is Break) {
            val r5 = (this[5]).chainingGreater(other[5])
            return r5
        }
        if ((this[6]).chainingGreater(other[6]) is Break) {
            val r6 = (this[6]).chainingGreater(other[6])
            return r6
        }
        if ((this[7]).chainingGreater(other[7]) is Break) {
            val r7 = (this[7]).chainingGreater(other[7])
            return r7
        }
        if ((this[8]).chainingGreater(other[8]) is Break) {
            val r8 = (this[8]).chainingGreater(other[8])
            return r8
        }
        if ((this[9]).chainingGreater(other[9]) is Break) {
            val r9 = (this[9]).chainingGreater(other[9])
            return r9
        }
        if ((this[10]).chainingGreater(other[10]) is Break) {
            val r10 = (this[10]).chainingGreater(other[10])
            return r10
        }
        Continue()
    }
}

public fun <D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromDCBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this[0]).cmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).cmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).cmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).cmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).cmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).cmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).cmp(other[6])) {
                                Equal -> when (val matchVal7 = (this[7]).cmp(other[7])) {
                                    Equal -> when (val matchVal8 = (this[8]).cmp(other[8])) {
                                        Equal -> when (val matchVal9 = (this[9]).cmp(other[9])) {
                                            Equal -> (this[10]).cmp(other[10])
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
    when (val matchVal = (this[0]).partialCmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).partialCmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).partialCmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).partialCmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).partialCmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).partialCmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).partialCmp(other[6])) {
                                Equal -> when (val matchVal7 = (this[7]).partialCmp(other[7])) {
                                    Equal -> when (val matchVal8 = (this[8]).partialCmp(other[8])) {
                                        Equal -> when (val matchVal9 = (this[9]).partialCmp(other[9])) {
                                            Equal -> when (val matchVal10 = (this[10]).partialCmp(other[10])) {
                                                Equal -> (this[11]).partialCmp(other[11])
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingLess(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingLess(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = (this[9]).chainingLess(other[9])) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> {
                                                                                    when (val matchVal10 = (this[10]).chainingLess(other[10])) {
                                                                                        is Break -> {
                                                                                            val (value10) = matchVal10
                                                                                            value10
                                                                                        }
                                                                                        is Continue -> (this[11]).lt(other[11])
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingLess(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingLess(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingLess(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingLess(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingLess(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingLess(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingLess(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingLess(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingLess(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = (this[9]).chainingLess(other[9])) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> {
                                                                                    when (val matchVal10 = (this[10]).chainingLess(other[10])) {
                                                                                        is Break -> {
                                                                                            val (value10) = matchVal10
                                                                                            value10
                                                                                        }
                                                                                        is Continue -> (this[11]).le(other[11])
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingGreater(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingGreater(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = (this[9]).chainingGreater(other[9])) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> {
                                                                                    when (val matchVal10 = (this[10]).chainingGreater(other[10])) {
                                                                                        is Break -> {
                                                                                            val (value10) = matchVal10
                                                                                            value10
                                                                                        }
                                                                                        is Continue -> (this[11]).ge(other[11])
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    when (val matchVal = (this[0]).chainingGreater(other[0])) {
        is Break -> {
            val (value) = matchVal
            value
        }
        is Continue -> {
            when (val matchVal1 = (this[1]).chainingGreater(other[1])) {
                is Break -> {
                    val (value1) = matchVal1
                    value1
                }
                is Continue -> {
                    when (val matchVal2 = (this[2]).chainingGreater(other[2])) {
                        is Break -> {
                            val (value2) = matchVal2
                            value2
                        }
                        is Continue -> {
                            when (val matchVal3 = (this[3]).chainingGreater(other[3])) {
                                is Break -> {
                                    val (value3) = matchVal3
                                    value3
                                }
                                is Continue -> {
                                    when (val matchVal4 = (this[4]).chainingGreater(other[4])) {
                                        is Break -> {
                                            val (value4) = matchVal4
                                            value4
                                        }
                                        is Continue -> {
                                            when (val matchVal5 = (this[5]).chainingGreater(other[5])) {
                                                is Break -> {
                                                    val (value5) = matchVal5
                                                    value5
                                                }
                                                is Continue -> {
                                                    when (val matchVal6 = (this[6]).chainingGreater(other[6])) {
                                                        is Break -> {
                                                            val (value6) = matchVal6
                                                            value6
                                                        }
                                                        is Continue -> {
                                                            when (val matchVal7 = (this[7]).chainingGreater(other[7])) {
                                                                is Break -> {
                                                                    val (value7) = matchVal7
                                                                    value7
                                                                }
                                                                is Continue -> {
                                                                    when (val matchVal8 = (this[8]).chainingGreater(other[8])) {
                                                                        is Break -> {
                                                                            val (value8) = matchVal8
                                                                            value8
                                                                        }
                                                                        is Continue -> {
                                                                            when (val matchVal9 = (this[9]).chainingGreater(other[9])) {
                                                                                is Break -> {
                                                                                    val (value9) = matchVal9
                                                                                    value9
                                                                                }
                                                                                is Continue -> {
                                                                                    when (val matchVal10 = (this[10]).chainingGreater(other[10])) {
                                                                                        is Break -> {
                                                                                            val (value10) = matchVal10
                                                                                            value10
                                                                                        }
                                                                                        is Continue -> (this[11]).gt(other[11])
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
        if ((this[0]).chainingLess(other[0]) is Break) {
            val r = (this[0]).chainingLess(other[0])
            return r
        }
        if ((this[1]).chainingLess(other[1]) is Break) {
            val r1 = (this[1]).chainingLess(other[1])
            return r1
        }
        if ((this[2]).chainingLess(other[2]) is Break) {
            val r2 = (this[2]).chainingLess(other[2])
            return r2
        }
        if ((this[3]).chainingLess(other[3]) is Break) {
            val r3 = (this[3]).chainingLess(other[3])
            return r3
        }
        if ((this[4]).chainingLess(other[4]) is Break) {
            val r4 = (this[4]).chainingLess(other[4])
            return r4
        }
        if ((this[5]).chainingLess(other[5]) is Break) {
            val r5 = (this[5]).chainingLess(other[5])
            return r5
        }
        if ((this[6]).chainingLess(other[6]) is Break) {
            val r6 = (this[6]).chainingLess(other[6])
            return r6
        }
        if ((this[7]).chainingLess(other[7]) is Break) {
            val r7 = (this[7]).chainingLess(other[7])
            return r7
        }
        if ((this[8]).chainingLess(other[8]) is Break) {
            val r8 = (this[8]).chainingLess(other[8])
            return r8
        }
        if ((this[9]).chainingLess(other[9]) is Break) {
            val r9 = (this[9]).chainingLess(other[9])
            return r9
        }
        if ((this[10]).chainingLess(other[10]) is Break) {
            val r10 = (this[10]).chainingLess(other[10])
            return r10
        }
        if ((this[11]).chainingLess(other[11]) is Break) {
            val r11 = (this[11]).chainingLess(other[11])
            return r11
        }
        Continue()
    }
}

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.chainingGreaterFromEDCBAZYXWVUT(other: List<Any?>): ControlFlow<Boolean, Unit> {
    return run {
        if ((this[0]).chainingGreater(other[0]) is Break) {
            val r = (this[0]).chainingGreater(other[0])
            return r
        }
        if ((this[1]).chainingGreater(other[1]) is Break) {
            val r1 = (this[1]).chainingGreater(other[1])
            return r1
        }
        if ((this[2]).chainingGreater(other[2]) is Break) {
            val r2 = (this[2]).chainingGreater(other[2])
            return r2
        }
        if ((this[3]).chainingGreater(other[3]) is Break) {
            val r3 = (this[3]).chainingGreater(other[3])
            return r3
        }
        if ((this[4]).chainingGreater(other[4]) is Break) {
            val r4 = (this[4]).chainingGreater(other[4])
            return r4
        }
        if ((this[5]).chainingGreater(other[5]) is Break) {
            val r5 = (this[5]).chainingGreater(other[5])
            return r5
        }
        if ((this[6]).chainingGreater(other[6]) is Break) {
            val r6 = (this[6]).chainingGreater(other[6])
            return r6
        }
        if ((this[7]).chainingGreater(other[7]) is Break) {
            val r7 = (this[7]).chainingGreater(other[7])
            return r7
        }
        if ((this[8]).chainingGreater(other[8]) is Break) {
            val r8 = (this[8]).chainingGreater(other[8])
            return r8
        }
        if ((this[9]).chainingGreater(other[9]) is Break) {
            val r9 = (this[9]).chainingGreater(other[9])
            return r9
        }
        if ((this[10]).chainingGreater(other[10]) is Break) {
            val r10 = (this[10]).chainingGreater(other[10])
            return r10
        }
        if ((this[11]).chainingGreater(other[11]) is Break) {
            val r11 = (this[11]).chainingGreater(other[11])
            return r11
        }
        Continue()
    }
}

public fun <E, D, C, B, A, Z, Y, X, W, V, U, T> List<Any?>.cmpFromEDCBAZYXWVUT(other: List<Any?>): Ordering =
    when (val matchVal = (this[0]).cmp(other[0])) {
        Equal -> when (val matchVal1 = (this[1]).cmp(other[1])) {
            Equal -> when (val matchVal2 = (this[2]).cmp(other[2])) {
                Equal -> when (val matchVal3 = (this[3]).cmp(other[3])) {
                    Equal -> when (val matchVal4 = (this[4]).cmp(other[4])) {
                        Equal -> when (val matchVal5 = (this[5]).cmp(other[5])) {
                            Equal -> when (val matchVal6 = (this[6]).cmp(other[6])) {
                                Equal -> when (val matchVal7 = (this[7]).cmp(other[7])) {
                                    Equal -> when (val matchVal8 = (this[8]).cmp(other[8])) {
                                        Equal -> when (val matchVal9 = (this[9]).cmp(other[9])) {
                                            Equal -> when (val matchVal10 = (this[10]).cmp(other[10])) {
                                                Equal -> (this[11]).cmp(other[11])
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

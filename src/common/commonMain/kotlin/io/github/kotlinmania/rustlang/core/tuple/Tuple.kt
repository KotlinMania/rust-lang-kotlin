// port-lint: source tuple.rs
package io.github.kotlinmania.rustlang.core.tuple

import io.github.kotlinmania.rustlang.core.num.f128.consts.E
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.*

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

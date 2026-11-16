package io.github.kotlinmania.rustlang.procmacro.bridge

import io.github.kotlinmania.rustlang.alloc.collections.btree.map.Range
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.procmacro.Delimiter
import io.github.kotlinmania.rustlang.procmacro.bridge.buffer.Buffer
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.Decode
import io.github.kotlinmania.rustlang.procmacro.bridge.rpc.Encode
import io.github.kotlinmania.rustlang.procmacro.diagnostic.Level

public fun <Unmarked, T> markVecT(unmarked: List<Unmarked>, markTFn: (List<Unmarked>) -> T): List<T> {
    // Should be a no-op due to std's in-place collect optimizations.
    return unmarked.map(markTFn).toMutableList()
}

public fun <Unmarked, T> List<T>.unmark(unmarkTFn: (T) -> List<Unmarked>): List<Unmarked> {
    // Should be a no-op due to std's in-place collect optimizations.
    return this.map(unmarkTFn).toMutableList()
}

public fun mark(unmarked: Unit) {
    unmarked
}

public fun Unit.unmark() {
    this
}

public fun markBoolean(unmarked: Boolean): Boolean = unmarked

public fun Boolean.unmark(): Boolean = this

public fun markString(unmarked: String): String = unmarked

public fun String.unmark(): String = this

public fun markUByte(unmarked: UByte): UByte = unmarked

public fun UByte.unmark(): UByte = this

public fun markInt(unmarked: Int): Int = unmarked

public fun Int.unmark(): Int = this

public fun markDelimiter(unmarked: Delimiter): Delimiter = unmarked

public fun Delimiter.unmark(): Delimiter = this

public fun markLevel(unmarked: Level): Level = unmarked

public fun Level.unmark(): Level = this

public fun markBoundInt(unmarked: Bound<Int>): Bound<Int> = unmarked

public fun Bound<Int>.unmark(): Bound<Int> = this

public fun markRangeInt(unmarked: IntRange): IntRange = unmarked

public fun IntRange.unmark(): IntRange = this

public fun <Unmarked, T> markOptionT(unmarked: Unmarked?): T? =
    when (unmarked) {
        null -> {
            null
        }
        else -> {
            val t = unmarked
            Option.Some(Mark.Mark(t))
        }
    }

public fun <Unmarked, T> T?.unmark(): Unmarked? =
    when (this) {
        null -> {
            null
        }
        else -> {
            val t = this
            Option.Some(t.unmark())
        }
    }

public fun <Unmarked, T, E> markResultTE(unmarked: Result<Unmarked, Unmarked>): Result<T, E> =
    when (unmarked) { is Result.Ok -> {
        val t = unmarked.value
        Result.Ok<T, E>(Mark.Mark(t))
    }; is Result.Err -> {
        val e = unmarked.error
        Result.Err<T, E>(Mark.Mark(e))
    } }

public fun <Unmarked, T, E> Result<T, E>.unmark(): Result<Unmarked, Unmarked> =
    when (this) { is Result.Ok -> {
        val t = this.value
        Result.Ok<Unmarked, Unmarked>(t.unmark())
    }; is Result.Err -> {
        val e = this.error
        Result.Err<Unmarked, Unmarked>(e.unmark())
    } }

public fun <S, T : Encode<S>> Range<T>.encode(w: Buffer, s: S) {
    this.start.encode(w, s)
    this.end.encode(w, s)
}

public fun <S, T : Decode<S, T>> decodeRangeT(r: List<UByte>, s: S): Range<T> = Range(start = Decode.decode(r, s), end = Decode.decode(r, s))

// port-lint: source cmp/clamp.rs
package io.github.kotlinmania.rustlang.core.cmp.clamp

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.cmp.clamp
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.intrinsics.kmMaxnumf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmMaxnumf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmMinnumf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmMinnumf64
import io.github.kotlinmania.rustlang.core.ops.range.RangeFrom
import io.github.kotlinmania.rustlang.core.ops.range.RangeFull
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.ops.range.RangeToInclusive

/**
 * Trait for ranges supported by [Ord.clampTo].
 */
public interface ClampBounds<T> {
    /**
     * The implementation of [Ord.clampTo].
     */
    public fun clamp(value: T): T
}

public fun <T> RangeFrom<T>.clamp(value: T): T = value.max(this.start)

public fun <T> RangeToInclusive<T>.clamp(value: T): T = value.min(this.end)

public fun <T> RangeInclusive<T>.clamp(value: T): T {
    val (start, end) = this.intoInner()
    return value.clamp(start, end)
}

public fun <T> RangeFull.clamp(value: T): T = value

// @unstable(feature = "f16", issue = "116909")
// @unstable(feature = "f128", issue = "116909")
public fun RangeFrom<F16>.clamp(value: F16): F16 {
    check(!this.start.isNan()) { "start was NaN" }
    return F16.fromFloat(kmMaxnumf32(value.toFloat(), (F16.fromDouble(this.start.toDouble())).toFloat()))
}

public fun RangeToInclusive<F16>.clamp(value: F16): F16 {
    check(!this.end.isNan()) { "end was NaN" }
    return F16.fromFloat(kmMinnumf32(value.toFloat(), (F16.fromDouble(this.end.toDouble())).toFloat()))
}

public fun RangeInclusive<F16>.clamp(value: F16): F16 {
    val (start, end) = this.intoInner()
    check(start <= end) { "start > end, or either was NaN" }
    return value.clamp(start, end)
}

public fun RangeFrom<kotlin.Float>.clamp(value: kotlin.Float): kotlin.Float {
    check(!this.start.isNan()) { "start was NaN" }
    return kmMaxnumf32(value, this.start.toFloat())
}

public fun RangeToInclusive<kotlin.Float>.clamp(value: kotlin.Float): kotlin.Float {
    check(!this.end.isNan()) { "end was NaN" }
    return kmMinnumf32(value, this.end.toFloat())
}

public fun RangeInclusive<kotlin.Float>.clamp(value: kotlin.Float): kotlin.Float {
    val (start, end) = this.intoInner()
    check(start <= end) { "start > end, or either was NaN" }
    return value.clamp(start, end)
}

public fun RangeFrom<Double>.clamp(value: Double): Double {
    check(!this.start.isNan()) { "start was NaN" }
    return kmMaxnumf64(value, this.start.toDouble())
}

public fun RangeToInclusive<Double>.clamp(value: Double): Double {
    check(!this.end.isNan()) { "end was NaN" }
    return kmMinnumf64(value, this.end.toDouble())
}

public fun RangeInclusive<Double>.clamp(value: Double): Double {
    val (start, end) = this.intoInner()
    check(start <= end) { "start > end, or either was NaN" }
    return value.clamp(start, end)
}

public fun RangeFrom<F128>.clamp(value: F128): F128 {
    check(!this.start.isNan()) { "start was NaN" }
    return F128.fromDouble(kmMaxnumf64(value.toDouble(), (F128.fromDouble(this.start.toDouble())).toDouble()))
}

public fun RangeToInclusive<F128>.clamp(value: F128): F128 {
    check(!this.end.isNan()) { "end was NaN" }
    return F128.fromDouble(kmMinnumf64(value.toDouble(), (F128.fromDouble(this.end.toDouble())).toDouble()))
}

public fun RangeInclusive<F128>.clamp(value: F128): F128 {
    val (start, end) = this.intoInner()
    check(start <= end) { "start > end, or either was NaN" }
    return value.clamp(start, end)
}

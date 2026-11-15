package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort
import io.github.kotlinmania.rustlang.core.intrinsics.kmCeilf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmCeilf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloorf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloorf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundTiesEvenf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundTiesEvenf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmSqrtf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmSqrtf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmTruncf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmTruncf64

/**
 * Generates the [unreachable] instruction, which causes an unconditional [trap].
 *
 * This function is safe to call and immediately aborts the execution.
 *
 * [unreachable]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-control
 * [trap]: https://webassembly.github.io/spec/core/intro/overview.html#trap
 */
public fun unreachable(): Nothing = kmAbort()

/**
 * Generates the [f32.ceil] instruction, returning the smallest integer greater than or equal to `a`.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f32.ceil].
 *
 * [std.f32.ceil]: https://doc.rust-lang.org/std/primitive.f32.html#method.ceil
 * [f32.ceil]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f32Ceil(a: Float): Float = kmCeilf32(a)

/**
 * Generates the [f32.floor] instruction, returning the largest integer less than or equal to `a`.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f32.floor].
 *
 * [std.f32.floor]: https://doc.rust-lang.org/std/primitive.f32.html#method.floor
 * [f32.floor]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f32Floor(a: Float): Float = kmFloorf32(a)

/**
 * Generates the [f32.trunc] instruction, roundinging to the nearest integer towards zero.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f32.trunc].
 *
 * [std.f32.trunc]: https://doc.rust-lang.org/std/primitive.f32.html#method.trunc
 * [f32.trunc]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f32Trunc(a: Float): Float = kmTruncf32(a)

/**
 * Generates the [f32.nearest] instruction, roundinging to the nearest integer. Rounds half-way
 * cases to the number with an even least significant digit.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f32.roundTiesEven].
 *
 * [std.f32.roundTiesEven]: https://doc.rust-lang.org/std/primitive.f32.html#method.roundTiesEven
 * [f32.nearest]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f32Nearest(a: Float): Float = kmRoundTiesEvenf32(a)

/**
 * Generates the [f32.sqrt] instruction, returning the square root of the number `a`.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f32.sqrt].
 *
 * [std.f32.sqrt]: https://doc.rust-lang.org/std/primitive.f32.html#method.sqrt
 * [f32.sqrt]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f32Sqrt(a: Float): Float = kmSqrtf32(a)

/**
 * Generates the [f64.ceil] instruction, returning the smallest integer greater than or equal to `a`.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f64.ceil].
 *
 * [std.f64.ceil]: https://doc.rust-lang.org/std/primitive.f64.html#method.ceil
 * [f64.ceil]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f64Ceil(a: Double): Double = kmCeilf64(a)

/**
 * Generates the [f64.floor] instruction, returning the largest integer less than or equal to `a`.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f64.floor].
 *
 * [std.f64.floor]: https://doc.rust-lang.org/std/primitive.f64.html#method.floor
 * [f64.floor]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f64Floor(a: Double): Double = kmFloorf64(a)

/**
 * Generates the [f64.trunc] instruction, roundinging to the nearest integer towards zero.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f64.trunc].
 *
 * [std.f64.trunc]: https://doc.rust-lang.org/std/primitive.f64.html#method.trunc
 * [f64.trunc]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f64Trunc(a: Double): Double = kmTruncf64(a)

/**
 * Generates the [f64.nearest] instruction, roundinging to the nearest integer. Rounds half-way
 * cases to the number with an even least significant digit.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f64.roundTiesEven].
 *
 * [std.f64.roundTiesEven]: https://doc.rust-lang.org/std/primitive.f64.html#method.roundTiesEven
 * [f64.nearest]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f64Nearest(a: Double): Double = kmRoundTiesEvenf64(a)

/**
 * Generates the [f64.sqrt] instruction, returning the square root of the number `a`.
 *
 * This method is useful when targeting `noStd` and is equivalent to [std.f64.sqrt].
 *
 * [std.f64.sqrt]: https://doc.rust-lang.org/std/primitive.f64.html#method.sqrt
 * [f64.sqrt]: https://webassembly.github.io/spec/core/syntax/instructions.html#syntax-instr-numeric
 */
public fun f64Sqrt(a: Double): Double = kmSqrtf64(a)

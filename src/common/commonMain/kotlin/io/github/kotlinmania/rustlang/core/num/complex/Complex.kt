// port-lint: source num/complex.rs
package io.github.kotlinmania.rustlang.core.num.complex

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.hint.coldPath
import io.github.kotlinmania.rustlang.num.imp.libm.complex.rustDivhc3
import io.github.kotlinmania.rustlang.num.imp.libm.complex.rustMulhc3
import io.github.kotlinmania.rustlang.ops.arith.Add
import io.github.kotlinmania.rustlang.ops.arith.Neg
import io.github.kotlinmania.rustlang.ops.arith.Sub

/**
 * A [complex number], represented in rectangular form.
 *
 * # Basic operations
 *
 * A `Complex<T>` supports addition, subtraction, and negation when `T` does. The conjugation
 * requires that `T` supports negation.
 *
 * ```kotlin
 * import std.num.Complex
 *
 * @feature(complexNumbers)
 * public fun doctestEntry() {
 *     val x = Complex(re = 5.0, im = 9.0)
 *     val y = Complex.new(2.0, 3.0)
 *     // Scalar addition and subtraction operate on the real component.
 *     check(x + 1.0 == Complex.new(6.0, 9.0))
 *     check(x - 1.0 == Complex.new(4.0, 9.0))
 *     // Complex addition and subtraction operate on each component separately.
 *     check(x + y == Complex.new(7.0, 12.0))
 *     check(x - y == Complex.new(3.0, 6.0))
 *     // Negation occurs on both components.
 *     // Geometrically this is a rotation by 180 degrees.
 *     check(-x == Complex.new(-5.0, -9.0))
 *     // The conjugate negates only the imaginary component.
 *     // Geometrically this is a reflection across the real axis.
 *     check(x.conjugate() == Complex.new(5.0, -9.0))
 * }
 * ```
 *
 * # Complex multiplication and division of floating points
 *
 * Multiplication and division follow the textbook formulas, except that they attempt to
 * correct for overflow of intermediate steps of the calculation.
 *
 * This behavior mirrors the C standard, although it does not specify exactly when the
 * output should be corrected. Hence, the exact output may vary from platform to platform.
 *
 * ```kotlin
 * import std.num.Complex
 *
 * @feature(complexNumbers)
 * public fun doctestEntry() {
 *     check(Complex.new(1.0, 2.0) * Complex.new(3.0, 4.0) == Complex.new(-5.0, 10.0))
 *     check(Complex.new(2.0, 11.0) / Complex.new(2.0, 1.0) == Complex.new(3.0, 4.0))
 *     // The textbook algorithm would return NaN + NaNi for these inputs.
 *     check(Complex.new(1.0, 0.0) * Complex.new(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY) == Complex.new(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
 *     check(Complex.new(1.0, 1.0) / Complex.new(Double.POSITIVE_INFINITY, 0.0) == Complex.new(0.0, 0.0))
 * }
 * ```
 *
 * # FFI compatibility
 *
 * The following types are guaranteed to fully match the platform's `_Complex` in terms of layout and ABI:
 *
 * - `Complex<`[cFloat]`>`
 * - `Complex<`[cDouble]`>`
 *
 * For any other `Complex<T>` ABI compatibility is not guaranteed.
 *
 * [complex number]: https://en.wikipedia.org/wiki/ComplexNumber
 */
public open class Complex<T>(
    /**
     * The real component.
     */
    public var re: T,
    /**
     * The imaginary component.
     */
    public var im: T,
) : Neg<Complex<Output>>, Add<Complex<T>, Complex<Output>>, Sub<Complex<T>, Complex<Output>> {
    public open fun clone(): Complex<T> = Complex(re, im)

    /**
     * The complex conjugate of a complex number.
     *
     * The conjugate of `a + bi` is `a - bi`: the imaginary component is negated.
     * Geometrically, this is a reflection across the real axis.
     *
     * ```kotlin
     * import std.num.Complex
     *
     * @feature(complexNumbers)
     * public fun doctestEntry() {
     *     check(Complex.new(3.0, 2.0).conjugate() == Complex.new(3.0, -2.0))
     *     check(Complex.new(-3.0, -2.0).conjugate() == Complex.new(-3.0, 2.0))
     * }
     * ```
     */
    public open fun conjugate(): Complex<T> = Complex(re = this.re, im = -this.im)

    /**
     * Negate a complex number.
     *
     * The negation of `a + bi` is `- a - bi`: both components are negated.
     * Geometrically this is a rotation of 180°.
     *
     * ```kotlin
     * import std.num.Complex
     *
     * @feature(complexNumbers)
     * public fun doctestEntry() {
     *     check(-Complex.new(3.0, 2.0) == Complex.new(-3.0, -2.0))
     * }
     * ```
     */
    public override fun neg(): Complex<Output> = Complex.new(-this.re, -this.im)

    /**
     * Complex addition operates on each component separately.
     *
     * ```kotlin
     * import std.num.Complex
     *
     * @feature(complexNumbers)
     * public fun doctestEntry() {
     *     check(Complex.new(2.0, 3.0) + Complex.new(5.0, 9.0) == Complex.new(7.0, 12.0))
     * }
     * ```
     */
    public override fun add(rhs: Complex<T>): Complex<Output> = Complex.new(this.re + rhs.re, this.im + rhs.im)

    /**
     * Scalar addition operates only on the real component.
     *
     * ```kotlin
     * import std.num.Complex
     *
     * @feature(complexNumbers)
     * public fun doctestEntry() {
     *     check(Complex.new(2.0, 3.0) + 10.0 == Complex.new(12.0, 3.0))
     * }
     * ```
     */
    public override fun add(rhs: T): Complex<Output> = Complex.new(this.re + rhs, this.im)

    /**
     * Complex subtraction operates on each component separately.
     *
     * ```kotlin
     * import std.num.Complex
     *
     * @feature(complexNumbers)
     * public fun doctestEntry() {
     *     check(Complex.new(2.0, 3.0) - Complex.new(5.0, 9.0) == Complex.new(-3.0, -6.0))
     * }
     * ```
     */
    public override fun sub(rhs: Complex<T>): Complex<Output> = Complex.new(this.re - rhs.re, this.im - rhs.im)

    /**
     * Scalar subtraction operates only on the real component.
     *
     * ```kotlin
     * import std.num.Complex
     *
     * @feature(complexNumbers)
     * public fun doctestEntry() {
     *     check(Complex.new(2.0, 3.0) - 10.0 == Complex.new(-8.0, 3.0))
     * }
     * ```
     */
    public override fun sub(rhs: T): Complex<Output> = Complex.new(this.re - rhs, this.im)

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Complex<T>> =
            object : TypeLayout<Complex<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutT.sizeBytes, typeLayoutT.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutT.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Complex<T> =
                    Complex(
                        (typeLayoutT).readNative(addr),
                        (typeLayoutT).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Complex<T>) {
                    (typeLayoutT).writeNative(addr, value.re)
                    (typeLayoutT).writeNative(addr + off1, value.im)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Complex<T> =
                    Complex(
                        (typeLayoutT).readBytes(buf, offset),
                        (typeLayoutT).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Complex<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.re)
                    (typeLayoutT).writeBytes(buf, offset + off1.toInt(), value.im)
                }
            }

        /**
         * Create a new complex number from a real and imaginary component.
         */
        public fun <T> new(re: T, im: T): Complex<T> = Complex(re = re, im = im)

        public fun <T> default(defaultTFn: () -> T): Complex<T> = Complex(re = defaultTFn(), im = defaultTFn())
    }
}

public fun Complex<F16>.mul(rhs: Complex<F16>): Complex<F16> {
    val a = this.re
    val b = this.im
    val c = rhs.re
    val d = rhs.im
    val ac = a * c
    val bd = b * d
    val ad = a * d
    val bc = b * c
    val z = Complex.new(ac - bd, ad + bc)
    return if (z.re.isNan() && z.im.isNan()) {
        coldPath()
        rustMulhc3(a, b, c, d)
    } else {
        z
    }
}

public fun Complex<F16>.div(rhs: Complex<F16>): Complex<F16> = rustDivhc3(this.re, this.im, rhs.re, rhs.im)

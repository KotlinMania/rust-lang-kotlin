// port-lint: source ops/index.rs
package io.github.kotlinmania.rustlang.core.ops.index

/**
 * Used for indexing operations (`container[index]`) in immutable contexts.
 *
 * `container[index]` is actually syntactic sugar for `* container.index(index)`,
 * but only when used as an immutable value. If a mutable value is requested,
 * [IndexMut] is used instead. This allows nice things such as
 * `val value = v[index]` if the type of `value` implements [Copy].
 *
 * # Examples
 *
 * The following example implements `Index` on a read-only `NucleotideCount`
 * container, enabling individual counts to be retrieved with index syntax.
 *
 * ```kotlin
 * sealed class Nucleotide {
 *     public data object A : Nucleotide()
 *     public data object C : Nucleotide()
 *     public data object G : Nucleotide()
 *     public data object T : Nucleotide()
 *
 *     private fun kmDiscriminant(): Int =
 *         when (this) {
 *         A -> 0
 *         C -> 1
 *         G -> 2
 *         T -> 3
 *         }
 *
 *     public fun toByte(): Byte = kmDiscriminant().toByte()
 *
 *     public fun toUByte(): UByte = kmDiscriminant().toUByte()
 *
 *     public fun toShort(): Short = kmDiscriminant().toShort()
 *
 *     public fun toUShort(): UShort = kmDiscriminant().toUShort()
 *
 *     public fun toInt(): Int = kmDiscriminant()
 *
 *     public fun toUInt(): UInt = kmDiscriminant().toUInt()
 *
 *     public fun toLong(): Long = kmDiscriminant().toLong()
 *
 *     public fun toULong(): ULong = kmDiscriminant().toULong()
 * }
 * open class NucleotideCount(
 *     public var a: Int,
 *     public var c: Int,
 *     public var g: Int,
 *     public var t: Int,
 * ) {
 *     public open fun index(nucleotide: Nucleotide): Int {
 *         return when (nucleotide) {
 *             Nucleotide.A -> this.a
 *             Nucleotide.C -> this.c
 *             Nucleotide.G -> this.g
 *             Nucleotide.T -> this.t
 *         }
 *     }
 * }
 * val nucleotideCount = NucleotideCount(a = 14, c = 9, g = 10, t = 12)
 * check(nucleotideCount[Nucleotide.A] == 14)
 * check(nucleotideCount[Nucleotide.C] == 9)
 * check(nucleotideCount[Nucleotide.G] == 10)
 * check(nucleotideCount[Nucleotide.T] == 12)
 * ```
 */
public interface Index<Idx, out Output> {
    /**
     * Performs the indexing (`container[index]`) operation.
     *
     * # Panics
     *
     * May panic if the index is out of bounds.
     */
    public fun index(index: Idx): Output
}

/**
 * Used for indexing operations (`container[index]`) in mutable contexts.
 *
 * `container[index]` is actually syntactic sugar for
 * `* container.indexMut(index)`, but only when used as a mutable value. If
 * an immutable value is requested, the [Index] trait is used instead. This
 * allows nice things such as `v[index] = value`.
 *
 * # Examples
 *
 * A very simple implementation of a `Balance` struct that has two sides, where
 * each can be indexed mutably and immutably.
 *
 * ```kotlin
 * sealed class Side {
 *     public data object Left : Side()
 *     public data object Right : Side()
 *
 *     private fun kmDiscriminant(): Int =
 *         when (this) {
 *         Left -> 0
 *         Right -> 1
 *         }
 *
 *     public fun toByte(): Byte = kmDiscriminant().toByte()
 *
 *     public fun toUByte(): UByte = kmDiscriminant().toUByte()
 *
 *     public fun toShort(): Short = kmDiscriminant().toShort()
 *
 *     public fun toUShort(): UShort = kmDiscriminant().toUShort()
 *
 *     public fun toInt(): Int = kmDiscriminant()
 *
 *     public fun toUInt(): UInt = kmDiscriminant().toUInt()
 *
 *     public fun toLong(): Long = kmDiscriminant().toLong()
 *
 *     public fun toULong(): ULong = kmDiscriminant().toULong()
 * }
 * sealed class Weight {
 *     public data class Kilogram(public var value: Float) : Weight()
 *     public data class Pound(public var value: Float) : Weight()
 *
 *     public fun rustEquals(other: Weight): Boolean = this == other
 * }
 * open class Balance(public var left: Weight, public var right: Weight) {
 *     public open fun index(index: Side): Weight {
 *         println("Accessing $index-side of balance immutably")
 *         return when (index) {
 *             Side.Left -> this.left
 *             Side.Right -> this.right
 *         }
 *     }
 *
 *     public open fun indexMut(index: Side): Weight {
 *         println("Accessing $index-side of balance mutably")
 *         return when (index) {
 *             Side.Left -> this.left
 *             Side.Right -> this.right
 *         }
 *     }
 * }
 * var balance = Balance(right = Weight.Kilogram(2.5), left = Weight.Pound(1.5))
 * // In this case, `balance[Side.Right]` is sugar for
 * // `* balance.index(Side.Right)`, since we are only *reading*
 * // `balance[Side.Right]`, not writing it.
 * check(balance[Side.Right] == Weight.Kilogram(2.5))
 * // However, in this case `balance[Side.Left]` is sugar for
 * // `* balance.indexMut(Side.Left)`, since we are writing
 * // `balance[Side.Left]`.
 * balance[Side.Left] = Weight.Kilogram(3.0)
 * ```
 */
public interface IndexMut<Idx, Output> : Index<Idx, Output> {
    /**
     * Performs the mutable indexing (`container[index]`) operation.
     *
     * # Panics
     *
     * May panic if the index is out of bounds.
     */
    public fun indexMut(index: Idx): Output
}

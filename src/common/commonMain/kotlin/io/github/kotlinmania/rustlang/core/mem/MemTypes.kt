package io.github.kotlinmania.rustlang.core.mem

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.alignment.Alignment

/**
 * Provides associated constants for various useful properties of types,
 * to give them a canonical form in our code and make them easier to read.
 *
 * This is here only to simplify all the ZST checks we need in the library.
 * It's not on a stabilization track right now.
 */
public interface SizedTypeProperties {
    public val SIZE: Int
        get() = (UnresolvedTypeLayout<SizedTypeProperties>()).sizeBytes.toInt()

    public val ALIGN: Int
        get() = (UnresolvedTypeLayout<SizedTypeProperties>()).alignBytes.toInt()

    public val ALIGNMENT: Alignment
        get() = Alignment.new(SizedTypeProperties.ALIGN).unwrap()

    /**
     * `true` if this type requires no storage.
     * `false` if its [size](sizeOf) is greater than zero.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(sizedTypeProperties)
     * public fun doctestEntry() {
     *     fun <T> doSomethingWith() {
     *         if (T.IS_ZST) {
     *             // ... special approach ...
     *         } else {
     *             // ... the normal thing ...
     *         }
     *     }
     *     open class MyUnit
     *     check(MyUnit.IS_ZST)
     *     // For negative checks, consider using UFCS to emphasize the negation
     *     check(Int.IS_ZST.inv())
     *     // As it can sometimes hide in the type otherwise
     *     check(String.IS_ZST.inv())
     * }
     * ```
     */
    public val IS_ZST: Boolean
        get() = SizedTypeProperties.SIZE == 0

    public val LAYOUT: Layout
        get() = Layout.fromSizeAlignUnchecked(SizedTypeProperties.SIZE, SizedTypeProperties.ALIGN)

    /**
     * The largest safe length for a `[Self]`.
     *
     * Anything larger than this would make `sizeOfVal` overflow `Long.MAX`,
     * which is never allowed for a single object.
     */
    public val MAX_SLICE_LEN: Int
        get() =
            when (val matchVal = SizedTypeProperties.SIZE) {
                0 -> Int.MAX_VALUE
                else -> {
                    val n = matchVal
                    (Int.MAX_VALUE) / n
                }
            }
}

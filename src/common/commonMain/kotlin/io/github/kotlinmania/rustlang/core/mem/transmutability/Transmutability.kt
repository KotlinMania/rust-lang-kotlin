// port-lint: source mem/transmutability.rs
package io.github.kotlinmania.rustlang.core.mem.transmutability

import io.github.kotlinmania.rustlang.core.marker.ConstParamTy
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.manuallydrop.ManuallyDrop
import io.github.kotlinmania.rustlang.core.result.and

//

/**
 * Marks that `Src` is transmutable into `Self`.
 *
 * # Implementation
 *
 * This trait cannot be implemented explicitly. It is implemented on-the-fly by
 * the compiler for all types `Src` and `Self` such that, given a set of safety
 * obligations on the programmer (see [Assume]), the compiler has proved that
 * the bits of a value of type `Src` can be soundly reinterpreted as a `Self`.
 *
 * # Safety
 *
 * If `Dst: TransmuteFrom<Src, ASSUMPTIONS>`, the compiler guarantees that
 * `Src` is soundly *union-transmutable* into a value of type `Dst`, provided
 * that the programmer has guaranteed that the given [`Assumptions`](Assume)
 * are satisfied.
 *
 * A union-reinterpret is any bit-reinterpretation conversion in the form of:
 *
 * ```kotlin
 * fun <Src, Dst> transmuteViaUnion(src: Src): Dst {
 *     @repr(C)
 *     open class Transmute<Src, Dst>(public var src: Src? = null, public var dst: Dst? = null)
 *     val reinterpret = Transmute(src = src)
 *     val dst = reinterpret.dst
 *     return ManuallyDrop.intoInner(dst)
 * }
 * ```
 *
 * Note that this construction is more permissive than
 * [`mem.transmuteCopy`](transmuteCopy); union-transmutes permit
 * conversions that extend the bits of `Src` with trailing padding to fill
 * trailing uninitialized bytes of `Self`; e.g.:
 *
 * ```kotlin
 * import core.mem.Assume
 * import core.mem.TransmuteFrom
 * import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.TypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * @feature(transmutability)
 * public fun doctestEntry() {
 *     // size = 1
 *     @repr(C, align(2))
 *     open class Dst(public val fieldStorage: MutPtr, public val fieldLayout: TypeLayout<UByte>) {
 *         public var field0: UByte
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public var value: UByte
 *             get() = fieldLayout.readNative(fieldStorage.rawAddress)
 *             set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }
 *
 *         public constructor(value: UByte, fieldLayout: TypeLayout<UByte>) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 2L)), AlignedTypeLayout(fieldLayout, 2L))
 *
 *         public fun pointerLayout(): TypeLayout<Dst> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Dst(ptr, layout) }, { it.value })
 *     }
 *     val src = 42u.toUByte()
 *     // size = 2
 *     Dst.reinterpret(src)
 * }
 * ```
 *
 * # Caveats
 *
 * ## Portability
 *
 * Implementations of this trait do not provide any guarantee of portability
 * across toolchains, targets or compilations. This trait may be implemented
 * for certain combinations of `Src`, `Self` and `ASSUME` on some toolchains,
 * targets or compilations, but not others. For example, if the layouts of
 * `Src` or `Self` are non-deterministic, the presence or absence of an
 * implementation of this trait may also be non-deterministic. Even if `Src`
 * and `Self` have deterministic layouts (e.g., they are `repr(C)` structs),
 * Rust does not specify the alignments of its primitive integer types, and
 * layouts that involve these types may vary across toolchains, targets or
 * compilations.
 *
 * ## Stability
 *
 * Implementations of this trait do not provide any guarantee of SemVer
 * stability across the crate versions that define the `Src` and `Self` types.
 * If SemVer stability is crucial to your application, you must consult the
 * documentation of `Src` and `Self`s' defining crates. Note that the presence
 * of `repr(C)`, alone, does not carry a safety invariant of SemVer stability.
 * Furthermore, stability does not imply portability. For example, the size of
 * `Int` is stable, but not portable.
 */
public interface TransmuteFrom<Src, T> {
    /**
     * Transmutes a `Src` value into a `Self`.
     *
     * # Safety
     *
     * The safety obligations of the caller depend on the value of `ASSUME`:
     * - If [`Assume.alignment`](Assume.alignment), the caller must guarantee
     *   that the addresses of references in the returned `Self` satisfy the
     *   alignment requirements of their referent types.
     * - If [`Assume.lifetimes`](Assume.lifetimes), the caller must guarantee
     *   that references in the returned `Self` will not outlive their
     *   referents.
     * - If [`Assume.safety`](Assume.safety), the returned value might not
     *   satisfy the library safety invariants of `Self`, and the caller must
     *   guarantee that undefined behavior does not arise from uses of the
     *   returned value.
     * - If [`Assume.validity`](Assume.validity), the caller must guarantee
     *   that `src` is a bit-valid instance of `Self`.
     *
     * When satisfying the above obligations (if any), the caller must *not*
     * assume that this trait provides any inherent guarantee of layout
     * [portability](#portability) or [stability](#stability).
     */
    public fun transmute(src: Src): T {
        open class Transmute<Src, Dst>(
            public var src: Src? = null,
            public var dst: Dst? = null,
        )
        val transmute = Transmute(src = src)
        // SAFETY: It is safe to reinterpret the bits of `src` as a value of
        // type `Self`, because, by combination of invariant on this trait and
        // contract on the caller, `src` has been proven to satisfy both the
        // language and library invariants of `Self`. For all invariants not
        // `ASSUME`d by the caller, the safety obligation is supplied by the
        // compiler. Conversely, for all invariants `ASSUME`d by the caller,
        // the safety obligation is supplied by contract on the caller.
        val dst = transmute.dst
        return ManuallyDrop.intoInner(dst)
    }
}

/**
 * Configurable proof assumptions of [TransmuteFrom].
 *
 * When `false`, the respective proof obligation belongs to the compiler. When
 * `true`, the onus of the safety proof belongs to the programmer.
 */
public open class Assume(
    /**
     * When `false`, [TransmuteFrom] is not implemented for transmutations
     * that might violate the alignment requirements of references; e.g.:
     *
     * ```compile_fail,E0277
     * @file:feature(transmutability)
     * use core.mem.TransmuteFrom;
     *
     * assertEq(alignOf::<[u8; 2]>(), 1);
     * assertEq(alignOf::<u16>(), 2);
     *
     * let src: &[u8; 2] = &[0xFF, 0xFF];
     *
     * // SAFETY: No safety obligations.
     * let dst: &u16 = unchecked {
     *     <_ as TransmuteFrom<_>>::reinterpret(src)
     * };
     * ```
     *
     * When `true`, [TransmuteFrom] assumes that *you* have ensured
     * that references in the transmuted value satisfy the alignment
     * requirements of their referent types; e.g.:
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import core.mem.Assume
     * import core.mem.TransmuteFrom
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
     *
     * @feature(pointerIsAlignedTo, transmutability)
     * public fun doctestEntry() {
     *     val src: UByteArray = ubyteArrayOf(0xFFu, 0xFFu)
     *     val maybeDst: UShort? = if (ConstPtr.isAlignedTo(src, alignOf<UShort>(typeLayoutT = PrimitiveLayouts.U16))) {
     *         // SAFETY: We have checked above that the address of `src` satisfies the
     *         // alignment requirements of `UShort`.
     *         Any?.reinterpret(src)
     *     } else {
     *         null
     *     }
     *     check(when (maybeDst) {
     *         UShort.MAX_VALUE -> true
     *         null -> true
     *         else -> false
     *     })
     * }
     * ```
     */
    public var alignment: Boolean,
    /**
     * When `false`, [TransmuteFrom] is not implemented for transmutations
     * that extend the scopes of references.
     *
     * When `true`, [TransmuteFrom] assumes that *you* have ensured that
     * references in the transmuted value do not outlive their referents.
     */
    public var lifetimes: Boolean,
    /**
     * When `false`, [TransmuteFrom] is not implemented for transmutations
     * that might violate the library safety invariants of the destination
     * type; e.g.:
     *
     * ```compile_fail,E0277
     * @file:feature(transmutability)
     * use core.mem.TransmuteFrom;
     *
     * let src: u8 = 3;
     *
     * struct EvenU8 {
     *     // SAFETY: `val` must be an even number.
     *     val: u8,
     * }
     *
     * // SAFETY: No safety obligations.
     * let dst: EvenU8 = unchecked {
     *     <_ as TransmuteFrom<_>>::reinterpret(src)
     * };
     * ```
     *
     * When `true`, [TransmuteFrom] assumes that *you* have ensured
     * that undefined behavior does not arise from using the transmuted value;
     * e.g.:
     *
     * ```kotlin
     * import core.mem.Assume
     * import core.mem.TransmuteFrom
     *
     * @feature(transmutability)
     * public fun doctestEntry() {
     *     open class EvenU8(
     *         // SAFETY: `val` must be an even number.
     *         public var value: UByte,
     *     )
     *     val src: UByte = 42u
     *     val maybeDst: EvenU8? = if (src % 2u == 0u.toUByte()) {
     *         // SAFETY: We have checked above that the value of `src` is even.
     *         Any?.reinterpret(src)
     *     } else {
     *         null
     *     }
     *     check(when {
     *         maybeDst != null && maybeDst.value == 42 -> true
     *         else -> false
     *     })
     * }
     * ```
     */
    public var safety: Boolean,
    /**
     * When `false`, [TransmuteFrom] is not implemented for transmutations
     * that might violate the language-level bit-validity invariant of the
     * destination type; e.g.:
     *
     * ```compile_fail,E0277
     * @file:feature(transmutability)
     * use core.mem.TransmuteFrom;
     *
     * let src: u8 = 3;
     *
     * // SAFETY: No safety obligations.
     * let dst: bool = unchecked {
     *     <_ as TransmuteFrom<_>>::reinterpret(src)
     * };
     * ```
     *
     * When `true`, [TransmuteFrom] assumes that *you* have ensured
     * that the value being transmuted is a bit-valid instance of the
     * transmuted value; e.g.:
     *
     * ```kotlin
     * import core.mem.Assume
     * import core.mem.TransmuteFrom
     *
     * @feature(transmutability)
     * public fun doctestEntry() {
     *     val src: UByte = 1u
     *     val maybeDst: Boolean? = if (src == 0u.toUByte() || src == 1u.toUByte()) {
     *         // SAFETY: We have checked above that the value of `src` is a bit-valid
     *         // instance of `Boolean`.
     *         Any?.reinterpret(src)
     *     } else {
     *         null
     *     }
     *     check(maybeDst == true)
     * }
     * ```
     */
    public var validity: Boolean,
) : ConstParamTy, Add<Assume, Any?, Assume>, Sub<Assume, Any?, Assume> {
    public open fun clone(): Assume = Assume(alignment, lifetimes, safety, validity)

    /**
     * Combine the assumptions of `self` and `otherAssumptions`.
     *
     * This is especially useful for extending [Assume] in generic contexts;
     * e.g.:
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import core.mem.Assume
     * import core.mem.TransmuteFrom
     * import io.github.kotlinmania.rustlang.core.mem.TypeLayout
     * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
     *
     * @feature(adtConstParams, genericConstExprs, pointerIsAlignedTo, transmutability,)
     * public fun doctestEntry() {
     *     / **
     *      * Attempts to reinterpret `src` to `Dst`.
     *      *
     *      * Returns `None` if `src` violates the alignment requirements of `Dst`.
     *      *
     *      * # Safety
     *      *
     *      * The caller guarantees that the obligations required by `ASSUME`, except
     *      * alignment, are satisfied.
     *      * /
     *     fun <Src, Dst> tryTransmuteRef(src: Src, assume: Assume, typeLayoutDst: TypeLayout<Dst>): Dst? {
     *         return if (ConstPtr.isAlignedTo(src, alignOf<Dst>(typeLayoutT = typeLayoutDst))) {
     *             // SAFETY: By the above dynamic check, we have ensured that the address
     *             // of `src` satisfies the alignment requirements of `Dst`. By contract
     *             // on the caller, the safety obligations required by `ASSUME` have also
     *             // been satisfied.
     *             Any?.reinterpret(src)
     *         } else {
     *             null
     *         }
     *     }
     *     val src: UByteArray = ubyteArrayOf(0xFFu, 0xFFu)
     *     // SAFETY: No safety obligations.
     *     val maybeDst: UShort? = tryTransmuteRef(src, Assume.NOTHING)
     * }
     * ```
     */
    public open fun and(otherAssumptions: Assume): Assume = Assume(alignment = this.alignment || otherAssumptions.alignment, lifetimes = this.lifetimes || otherAssumptions.lifetimes, safety = this.safety || otherAssumptions.safety, validity = this.validity || otherAssumptions.validity)

    /**
     * Remove `otherAssumptions` the obligations of `self`; e.g.:
     *
     * ```kotlin
     * import core.mem.Assume
     *
     * @feature(transmutability)
     * public fun doctestEntry() {
     *     val assumptions = Assume.ALIGNMENT.and(Assume.SAFETY)
     *     val toBeRemoved = Assume.SAFETY.and(Assume.VALIDITY)
     *     check(assumptions.butNot(toBeRemoved) == Assume.ALIGNMENT)
     * }
     * ```
     */
    public open fun butNot(otherAssumptions: Assume): Assume = Assume(alignment = this.alignment && !otherAssumptions.alignment, lifetimes = this.lifetimes && !otherAssumptions.lifetimes, safety = this.safety && !otherAssumptions.safety, validity = this.validity && !otherAssumptions.validity)

    // Note(jswrenn): This const op is not actually usable. Why?
    // https://github.com/rust-lang/rust/pull/100726#issuecomment-1219928926
    public override fun add(otherAssumptions: Assume): Assume = this.and(otherAssumptions)

    // Note(jswrenn): This const op is not actually usable. Why?
    // https://github.com/rust-lang/rust/pull/100726#issuecomment-1219928926
    public override fun sub(otherAssumptions: Assume): Assume = this.butNot(otherAssumptions)

    public companion object {
        public fun typeLayout(): TypeLayout<Assume> =
            object : TypeLayout<Assume> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Assume =
                    Assume(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 2L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 3L),
                    )
                override fun writeNative(addr: Long, value: Assume) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.alignment)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.lifetimes)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.safety)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 3L, value.validity)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Assume =
                    Assume(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 3),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Assume) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.alignment)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.lifetimes)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.safety)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 3, value.validity)
                }
            }

        /**
         * With this, [TransmuteFrom] does not assume you have ensured any safety
         * obligations are met, and relies only upon its own analysis to (dis)prove
         * transmutability.
         */
        public val NOTHING: Assume = Assume(alignment = false, lifetimes = false, safety = false, validity = false)

        /**
         * With this, [TransmuteFrom] assumes only that you have ensured that
         * references in the transmuted value satisfy the alignment requirements of
         * their referent types. See [Assume.alignment] for examples.
         */
        public val ALIGNMENT: Assume = Assume.NOTHING.copy(alignment = true)

        /**
         * With this, [TransmuteFrom] assumes only that you have ensured that
         * references in the transmuted value do not outlive their referents. See
         * [Assume.lifetimes] for examples.
         */
        public val LIFETIMES: Assume = Assume.NOTHING.copy(lifetimes = true)

        /**
         * With this, [TransmuteFrom] assumes only that you have ensured that
         * undefined behavior does not arise from using the transmuted value. See
         * [Assume.safety] for examples.
         */
        public val SAFETY: Assume = Assume.NOTHING.copy(safety = true)

        /**
         * With this, [TransmuteFrom] assumes only that you have ensured that the
         * value being transmuted is a bit-valid instance of the transmuted value.
         * See [Assume.validity] for examples.
         */
        public val VALIDITY: Assume = Assume.NOTHING.copy(validity = true)
    }
}

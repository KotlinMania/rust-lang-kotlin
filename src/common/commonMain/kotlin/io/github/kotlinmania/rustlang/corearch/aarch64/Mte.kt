// port-lint: source aarch64/mte.rs
package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// AArch64 Memory tagging intrinsics
//
// [ACLE documentation](https://arm-software.github.io/acle/main/acle.html#markdown-toc-mte-intrinsics)

public expect fun irg(ptr: ConstPtr, exclude: Long): ConstPtr

public expect fun gmi(ptr: ConstPtr, exclude: Long): Long

public expect fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr

public expect fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr)

public expect fun addg(ptr: ConstPtr, value: Long): ConstPtr

public expect fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long

/**
 * Return a pointer containing a randomly generated logical address tag.
 *
 * `src`: A pointer containing an address.
 * `mask`: A mask where each of the lower 16 bits specifies logical
 *         tags which must be excluded from consideration. Zero excludes no
 *         tags.
 *
 * The returned pointer contains a copy of the `src` address, but with a
 * randomly generated logical tag, excluding any specified by `mask`.
 *
 * SAFETY: The pointer provided by this intrinsic will be invalid until the memory
 * has been appropriately tagged with `__arm_mte_set_tag`. If using that intrinsic
 * on the provided pointer is itself invalid, then it will be permanently invalid
 * and Undefined Behavior to dereference it.
 */
public fun armMteCreateRandomTag(src: ConstPtr, mask: ULong): ConstPtr = (irg(ConstPtr(src.rawAddress, src.rawMeta), mask.toLong())).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

/**
 * Return a pointer with the logical address tag offset by a value.
 *
 * `src`: A pointer containing an address and a logical tag.
 * `OFFSET`: A compile-time constant value in the range [0, 15].
 *
 * Adds offset to the logical address tag in `src`, wrapping if the result is
 * outside of the valid 16 tags.
 *
 * SAFETY: See `__arm_mte_create_random_tag`.
 */
public fun armMteIncrementTag(src: ConstPtr, offset: Long): ConstPtr = (addg(ConstPtr(src.rawAddress, src.rawMeta), offset)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

/**
 * Add a logical tag to the set of excluded logical tags.
 *
 * `src`: A pointer containing an address and a logical tag.
 * `excluded`: A mask where the lower 16 bits each specify currently-excluded
 *             logical tags.
 *
 * Adds the logical tag stored in `src` to the set in `excluded`, and returns
 * the result.
 */
public fun armMteExcludeTag(src: ConstPtr, excluded: ULong): ULong = gmi(ConstPtr(src.rawAddress, src.rawMeta), excluded.toLong()).toULong()

/**
 * Store an allocation tag for the 16-byte granule of memory.
 *
 * `tagAddress`: A pointer containing an address and a logical tag, which
 *                must be 16-byte aligned.
 *
 * SAFETY: `tagAddress` must be 16-byte aligned. The tag will apply to the
 * entire 16-byte memory granule.
 */
public fun armMteSetTag(tagAddress: ConstPtr) {
    stg(ConstPtr(tagAddress.rawAddress, tagAddress.rawMeta), ConstPtr(tagAddress.rawAddress, tagAddress.rawMeta))
}

/**
 * Load an allocation tag from memory, returning a new pointer with the
 * corresponding logical tag.
 *
 * `address`: A pointer containing an address from which allocation tag memory
 *            is read. This does not need to be 16-byte aligned.
 */
public fun armMteGetTag(address: ConstPtr): ConstPtr = (ldg(ConstPtr(address.rawAddress, address.rawMeta), ConstPtr(address.rawAddress, address.rawMeta))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

/**
 * Calculate the difference between the address parts of two pointers, ignoring
 * the tags, and sign-extending the result.
 */
public fun armMtePtrdiff(a: ConstPtr, b: ConstPtr): Long = subp(ConstPtr(a.rawAddress, a.rawMeta), ConstPtr(b.rawAddress, b.rawMeta))

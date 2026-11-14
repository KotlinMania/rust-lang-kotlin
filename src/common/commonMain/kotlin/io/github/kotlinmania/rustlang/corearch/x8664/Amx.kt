// port-lint: source x86_64/amx.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.F16x32
import io.github.kotlinmania.rustlang.corearch.simd.F32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x32
import io.github.kotlinmania.rustlang.corearch.x86.M512
import io.github.kotlinmania.rustlang.corearch.x86.M512bh
import io.github.kotlinmania.rustlang.corearch.x86.M512h
import io.github.kotlinmania.rustlang.corearch.x86.M512i

/**
 * Load tile configuration from a 64-byte memory location specified by `memAddr`.
 * The tile configuration format is specified below, and includes the tile type pallette,
 * the number of bytes per row, and the number of rows. If the specified palletteId is zero,
 * that signifies the init state for both the tile config and the tile data, and the tiles are zeroed.
 * Any invalid configurations will result in #GP fault.
 *
 * ```kotlin
 * 0: palette 1: startRow 2 - 15: reserved, must be zero 16 - 17: tile0.colsb 18 - 19: tile1.colsb 20 - 21: tile2.colsb ... 30 - 31: tile7.colsb 32 - 47: reserved, must be zero 48: tile0.rows 49: tile1.rows 50: tile2.rows ... 55: tile7.rows 56 - 63: reserved, must be zero
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_loadconfig&igExpand=6875)
 */
public fun tileLoadconfig(memAddr: ConstPtr) {
    ldtilecfg(memAddr)
}

/**
 * Stores the current tile configuration to a 64-byte memory location specified by `memAddr`.
 * The tile configuration format is as specified in [tileLoadconfig], and includes the tile type pallette,
 * the number of bytes per row, and the number of rows. If tiles are not configured, all zeroes will be stored to memory.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_storeconfig&igExpand=6879)
 */
public fun tileStoreconfig(memAddr: MutPtr) {
    sttilecfg(memAddr)
}

/**
 * Load tile rows from memory specified by base address and stride into destination tile dst using the tile configuration previously configured via [tileLoadconfig].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_loadd&igExpand=6877)
 */
public fun tileLoadd(base: ConstPtr, stride: Int, dst: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    tileloadd64(dst.toByte(), base, stride.toULong())
}

/**
 * Load tile rows from memory specified by base address and stride into destination tile dst. The shape
 * of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_loadd&igExpand=6877)
 */
public fun tileLoadd(dst: MutPtr, base: ConstPtr, stride: Int) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tileloadd64Internal((ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).rows, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).colsb, base, stride.toULong())
}

/**
 * Release the tile configuration to return to the init state, which releases all storage it currently holds.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_release&igExpand=6878)
 */
public fun tileRelease() {
    tilerelease()
}

/**
 * Store the tile specified by src to memory specified by base address and stride using the tile configuration previously configured via [tileLoadconfig].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_stored&igExpand=6881)
 */
public fun tileStored(base: MutPtr, stride: Int, dst: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    tilestored64(dst.toByte(), base, stride.toULong())
}

/**
 * Store the tile specified by src to memory specified by base address and stride. The shape of the tile
 * is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_stored&igExpand=6881)
 */
public fun tileStored(base: MutPtr, stride: ULong, src: Tile1024i) {
    tilestored64Internal(src.rows, src.colsb, base, stride, src.tile)
}

/**
 * Load tile rows from memory specified by base address and stride into destination tile dst using the tile configuration
 * previously configured via [tileLoadconfig]. This intrinsic provides a hint to the implementation that the data will
 * likely not be reused in the near future and the data caching can be optimized accordingly.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_stream_loadd&igExpand=6883)
 */
public fun tileStreamLoadd(base: ConstPtr, stride: Int, dst: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    tileloaddt164(dst.toByte(), base, stride.toULong())
}

/**
 * Load tile rows from memory specified by base address and stride into destination tile dst. The shape
 * of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 * This intrinsic provides a hint to the implementation that the data will likely not be reused in the
 * near future and the data caching can be optimized accordingly.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_stream_loadd&igExpand=6883)
 */
public fun tileStreamLoadd(dst: MutPtr, base: ConstPtr, stride: Int) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tileloaddt164Internal((ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).rows, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).colsb, base, stride.toULong())
}

/**
 * Zero the tile specified by `tdest`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_zero&igExpand=6885)
 */
public fun tileZero(dst: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    tilezero(dst.toByte())
}

/**
 * Zero the tile specified by `dst`. The shape of the tile is specified in the struct of [tile1024i].
 * The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_zero&igExpand=6885)
 */
public fun tileZero(dst: MutPtr) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tilezeroInternal((ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).rows, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).colsb)
}

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in tiles a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in dst, and store the 32-bit result back to tile dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_dpbf16ps&igExpand=6864)
 */
public fun tileDpbf16ps(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdpbf16ps(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of FP16 (16-bit) floating-point pairs in tiles a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in dst, and store the 32-bit result back to tile dst. The shape of the tile
 * is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_dpbf16ps&igExpand=6864)
 */
public fun tileDpbf16ps(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdpbf16psInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Compute dot-product of bytes in tiles with a source/destination accumulator.
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding
 * signed 8-bit integers in b, producing 4 intermediate 32-bit results.
 * Sum these 4 results with the corresponding 32-bit integer in dst, and store the 32-bit result back to tile dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_dpbssd&igExpand=6866)
 */
public fun tileDpbssd(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdpbssd(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of bytes in tiles with a source/destination accumulator.
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding
 * signed 8-bit integers in b, producing 4 intermediate 32-bit results.
 * Sum these 4 results with the corresponding 32-bit integer in dst, and store the 32-bit result back to tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_dpbssd&igExpand=6866)
 */
public fun tileDpbssd(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdpbssdInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Compute dot-product of bytes in tiles with a source/destination accumulator.
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding
 * unsigned 8-bit integers in b, producing 4 intermediate 32-bit results.
 * Sum these 4 results with the corresponding 32-bit integer in dst, and store the 32-bit result back to tile dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_dpbsud&igExpand=6868)
 */
public fun tileDpbsud(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdpbsud(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of bytes in tiles with a source/destination accumulator.
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding
 * unsigned 8-bit integers in b, producing 4 intermediate 32-bit results.
 * Sum these 4 results with the corresponding 32-bit integer in dst, and store the 32-bit result back to tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_dpbsud&igExpand=6868)
 */
public fun tileDpbsud(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdpbsudInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Compute dot-product of bytes in tiles with a source/destination accumulator.
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding
 * signed 8-bit integers in b, producing 4 intermediate 32-bit results.
 * Sum these 4 results with the corresponding 32-bit integer in dst, and store the 32-bit result back to tile dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_dpbusd&igExpand=6870)
 */
public fun tileDpbusd(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdpbusd(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of bytes in tiles with a source/destination accumulator.
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding
 * signed 8-bit integers in b, producing 4 intermediate 32-bit results.
 * Sum these 4 results with the corresponding 32-bit integer in dst, and store the 32-bit result back to tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_dpbusd&igExpand=6870)
 */
public fun tileDpbusd(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdpbusdInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Compute dot-product of bytes in tiles with a source/destination accumulator.
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding
 * unsigned 8-bit integers in b, producing 4 intermediate 32-bit results.
 * Sum these 4 results with the corresponding 32-bit integer in dst, and store the 32-bit result back to tile dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_dpbuud&igExpand=6872)
 */
public fun tileDpbuud(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdpbuud(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of bytes in tiles with a source/destination accumulator.
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding
 * unsigned 8-bit integers in b, producing 4 intermediate 32-bit results.
 * Sum these 4 results with the corresponding 32-bit integer in dst, and store the 32-bit result back to tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_dpbuud&igExpand=6872)
 */
public fun tileDpbuud(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdpbuudInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Compute dot-product of FP16 (16-bit) floating-point pairs in tiles a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 *  with elements in dst, and store the 32-bit result back to tile dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_dpfp16ps&igExpand=6874)
 */
public fun tileDpfp16ps(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdpfp16ps(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of FP16 (16-bit) floating-point pairs in tiles a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 *  with elements in dst, and store the 32-bit result back to tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_dpfp16ps&igExpand=6874)
 */
public fun tileDpfp16ps(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdpfp16psInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Perform matrix multiplication of two tiles containing complex elements and accumulate the results into a packed single precision tile.
 * Each dword element in input tiles a and b is interpreted as a complex number with FP16 real part and FP16 imaginary part.
 * Calculates the imaginary part of the result. For each possible combination of (row of a, column of b),
 * it performs a set of multiplication and accumulations on all corresponding complex numbers (one from a and one from b).
 * The imaginary part of the a element is multiplied with the real part of the corresponding b element, and the real part of
 * the a element is multiplied with the imaginary part of the corresponding b elements. The two accumulated results are added,
 * and then accumulated into the corresponding row and column of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_cmmimfp16ps&igExpand=6860)
 */
public fun tileCmmimfp16ps(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tcmmimfp16ps(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Perform matrix multiplication of two tiles containing complex elements and accumulate the results into a packed single precision tile.
 * Each dword element in input tiles a and b is interpreted as a complex number with FP16 real part and FP16 imaginary part.
 * Calculates the imaginary part of the result. For each possible combination of (row of a, column of b),
 * it performs a set of multiplication and accumulations on all corresponding complex numbers (one from a and one from b).
 * The imaginary part of the a element is multiplied with the real part of the corresponding b element, and the real part of
 * the a element is multiplied with the imaginary part of the corresponding b elements. The two accumulated results are added,
 * and then accumulated into the corresponding row and column of dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_cmmimfp16ps&igExpand=6860)
 */
public fun tileCmmimfp16ps(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tcmmimfp16psInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Perform matrix multiplication of two tiles containing complex elements and accumulate the results into a packed single precision tile.
 * Each dword element in input tiles a and b is interpreted as a complex number with FP16 real part and FP16 imaginary part.
 * Calculates the real part of the result. For each possible combination of (row of a, column of b),
 * it performs a set of multiplication and accumulations on all corresponding complex numbers (one from a and one from b).
 * The real part of the a element is multiplied with the real part of the corresponding b element, and the negated imaginary part of
 * the a element is multiplied with the imaginary part of the corresponding b elements.
 * The two accumulated results are added, and then accumulated into the corresponding row and column of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tile_cmmrlfp16ps&igExpand=6862)
 */
public fun tileCmmrlfp16ps(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tcmmrlfp16ps(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Perform matrix multiplication of two tiles containing complex elements and accumulate the results into a packed single precision tile.
 * Each dword element in input tiles a and b is interpreted as a complex number with FP16 real part and FP16 imaginary part.
 * Calculates the real part of the result. For each possible combination of (row of a, column of b),
 * it performs a set of multiplication and accumulations on all corresponding complex numbers (one from a and one from b).
 * The real part of the a element is multiplied with the real part of the corresponding b element, and the negated imaginary part of
 * the a element is multiplied with the imaginary part of the corresponding b elements.
 * The two accumulated results are added, and then accumulated into the corresponding row and column of dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=__tile_cmmrlfp16ps&igExpand=6862)
 */
public fun tileCmmrlfp16ps(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tcmmrlfp16psInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Compute dot-product of BF8 (8-bit E5M2) floating-point elements in tile a and BF8 (8-bit E5M2)
 * floating-point elements in tile b, accumulating the intermediate single-precision
 * (32-bit) floating-point elements with elements in dst, and store the 32-bit result
 * back to tile dst.
 */
public fun tileDpbf8ps(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdpbf8ps(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of BF8 (8-bit E5M2) floating-point elements in tile a and BF8 (8-bit E5M2)
 * floating-point elements in tile b, accumulating the intermediate single-precision
 * (32-bit) floating-point elements with elements in dst, and store the 32-bit result
 * back to tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileDpbf8ps(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdpbf8psInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Compute dot-product of BF8 (8-bit E5M2) floating-point elements in tile a and HF8
 * (8-bit E4M3) floating-point elements in tile b, accumulating the intermediate single-precision
 * (32-bit) floating-point elements with elements in dst, and store the 32-bit result
 * back to tile dst.
 */
public fun tileDpbhf8ps(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdpbhf8ps(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of BF8 (8-bit E5M2) floating-point elements in tile a and HF8
 * (8-bit E4M3) floating-point elements in tile b, accumulating the intermediate single-precision
 * (32-bit) floating-point elements with elements in dst, and store the 32-bit result
 * back to tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileDpbhf8ps(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdpbhf8psInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Compute dot-product of HF8 (8-bit E4M3) floating-point elements in tile a and BF8
 * (8-bit E5M2) floating-point elements in tile b, accumulating the intermediate single-precision
 * (32-bit) floating-point elements with elements in dst, and store the 32-bit result
 * back to tile dst.
 */
public fun tileDphbf8ps(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdphbf8ps(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of HF8 (8-bit E4M3) floating-point elements in tile a and BF8
 * (8-bit E5M2) floating-point elements in tile b, accumulating the intermediate single-precision
 * (32-bit) floating-point elements with elements in dst, and store the 32-bit result
 * back to tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileDphbf8ps(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdphbf8psInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Compute dot-product of HF8 (8-bit E4M3) floating-point elements in tile a and HF8 (8-bit E4M3)
 * floating-point elements in tile b, accumulating the intermediate single-precision
 * (32-bit) floating-point elements with elements in dst, and store the 32-bit result
 * back to tile dst.
 */
public fun tileDphf8ps(dst: Int, a: Int, b: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= a && a < (1 shl 3)) { "A doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= b && b < (1 shl 3)) { "B doesn't fit in 3 bits (unsigned)" }
    }
    tdphf8ps(dst.toByte(), a.toByte(), b.toByte())
}

/**
 * Compute dot-product of HF8 (8-bit E4M3) floating-point elements in tile a and HF8 (8-bit E4M3)
 * floating-point elements in tile b, accumulating the intermediate single-precision
 * (32-bit) floating-point elements with elements in dst, and store the 32-bit result
 * back to tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileDphf8ps(dst: MutPtr, a: Tile1024i, b: Tile1024i) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tdphf8psInternal(a.rows, b.colsb, a.colsb, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile, a.tile, b.tile)
}

/**
 * Load tile rows from memory specified by base address and stride into destination tile dst
 * using the tile configuration previously configured via [tileLoadconfig].
 * Additionally, this intrinsic indicates the source memory location is likely to become
 * read-shared by multiple processors, i.e., read in the future by at least one other processor
 * before it is written, assuming it is ever written in the future.
 */
public fun tileLoaddrs(base: ConstPtr, stride: Int, dst: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    tileloaddrs64(dst.toByte(), base, stride.toULong())
}

/**
 * Load tile rows from memory specified by base address and stride into destination tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 * Additionally, this intrinsic indicates the source memory location is likely to become
 * read-shared by multiple processors, i.e., read in the future by at least one other processor
 * before it is written, assuming it is ever written in the future.
 */
public fun tileLoaddrs(dst: MutPtr, base: ConstPtr, stride: Int) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tileloaddrs64Internal((ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).rows, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).colsb, base, stride.toULong())
}

/**
 * Load tile rows from memory specified by base address and stride into destination tile dst
 * using the tile configuration previously configured via [tileLoadconfig].
 * Provides a hint to the implementation that the data would be reused but does not need
 * to be resident in the nearest cache levels.
 * Additionally, this intrinsic indicates the source memory location is likely to become
 * read-shared by multiple processors, i.e., read in the future by at least one other processor
 * before it is written, assuming it is ever written in the future.
 */
public fun tileStreamLoaddrs(base: ConstPtr, stride: Int, dst: Int) {
    run {
        check(0 <= dst && dst < (1 shl 3)) { "DST doesn't fit in 3 bits (unsigned)" }
    }
    tileloaddrst164(dst.toByte(), base, stride.toULong())
}

/**
 * Load tile rows from memory specified by base address and stride into destination tile dst.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 * Provides a hint to the implementation that the data would be reused but does not need
 * to be resident in the nearest cache levels.
 * Additionally, this intrinsic indicates the source memory location is likely to become
 * read-shared by multiple processors, i.e., read in the future by at least one other processor
 * before it is written, assuming it is ever written in the future.
 */
public fun tileStreamLoaddrs(dst: MutPtr, base: ConstPtr, stride: Int) {
    (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).tile = tileloaddrst164Internal((ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).rows, (ObjectSlotLayout<Tile1024i>()).readNative((dst).rawAddress).colsb, base, stride.toULong())
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed 32-bit signed integer
 * elements to packed single-precision (32-bit) floating-point elements.
 */
public fun tileCvtrowd2ps(row: UInt, tile: Int): M512 {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    return tcvtrowd2ps(tile.toByte(), row).asM512()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed 32-bit signed integer
 * elements to packed single-precision (32-bit) floating-point elements.
 */
public fun tileCvtrowd2psi(tile: Int, row: Int): M512 {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= row && row < (1 shl 6)) { "ROW doesn't fit in 6 bits (unsigned)" }
    }
    return tcvtrowd2psi(tile.toByte(), row.toUInt()).asM512()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed 32-bit signed integer
 * elements to packed single-precision (32-bit) floating-point elements.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileCvtrowd2ps(src: Tile1024i, row: UInt): M512 = tcvtrowd2psInternal(src.rows, src.colsb, src.tile, row).asM512()

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed half-precision (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the high 16-bits within each 32-bit element of the returned vector.
 */
public fun tileCvtrowps2phh(row: UInt, tile: Int): M512h {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    return tcvtrowps2phh(tile.toByte(), row).asM512h()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed half-precision (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the high 16-bits within each 32-bit element of the returned vector.
 */
public fun tileCvtrowps2phhi(tile: Int, row: Int): M512h {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= row && row < (1 shl 6)) { "ROW doesn't fit in 6 bits (unsigned)" }
    }
    return tcvtrowps2phhi(tile.toByte(), row.toUInt()).asM512h()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed half-precision (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the high 16-bits within each 32-bit element of the returned vector.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileCvtrowps2phh(src: Tile1024i, row: UInt): M512h = tcvtrowps2phhInternal(src.rows, src.colsb, src.tile, row).asM512h()

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed half-precision (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the low 16-bits within each 32-bit element of the returned vector.
 */
public fun tileCvtrowps2phl(row: UInt, tile: Int): M512h {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    return tcvtrowps2phl(tile.toByte(), row).asM512h()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed half-precision (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the low 16-bits within each 32-bit element of the returned vector.
 */
public fun tileCvtrowps2phli(tile: Int, row: Int): M512h {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= row && row < (1 shl 6)) { "ROW doesn't fit in 6 bits (unsigned)" }
    }
    return tcvtrowps2phli(tile.toByte(), row.toUInt()).asM512h()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed half-precision (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the low 16-bits within each 32-bit element of the returned vector.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileCvtrowps2phl(src: Tile1024i, row: UInt): M512h = tcvtrowps2phlInternal(src.rows, src.colsb, src.tile, row).asM512h()

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed BF16 (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the high 16-bits within each 32-bit element of the returned vector.
 */
public fun tileCvtrowps2bf16h(row: UInt, tile: Int): M512bh {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    return tcvtrowps2bf16h(tile.toByte(), row).asM512bh()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed BF16 (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the high 16-bits within each 32-bit element of the returned vector.
 */
public fun tileCvtrowps2bf16hi(tile: Int, row: Int): M512bh {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= row && row < (1 shl 6)) { "ROW doesn't fit in 6 bits (unsigned)" }
    }
    return tcvtrowps2bf16hi(tile.toByte(), row.toUInt()).asM512bh()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed BF16 (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the high 16-bits within each 32-bit element of the returned vector.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileCvtrowps2bf16h(src: Tile1024i, row: UInt): M512bh = tcvtrowps2bf16hInternal(src.rows, src.colsb, src.tile, row).asM512bh()

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed BF16 (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the low 16-bits within each 32-bit element of the returned vector.
 */
public fun tileCvtrowps2bf16l(row: UInt, tile: Int): M512bh {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    return tcvtrowps2bf16l(tile.toByte(), row).asM512bh()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed BF16 (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the low 16-bits within each 32-bit element of the returned vector.
 */
public fun tileCvtrowps2bf16li(tile: Int, row: Int): M512bh {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= row && row < (1 shl 6)) { "ROW doesn't fit in 6 bits (unsigned)" }
    }
    return tcvtrowps2bf16li(tile.toByte(), row.toUInt()).asM512bh()
}

/**
 * Moves a row from a tile register to a zmm register, converting the packed single-precision (32-bit)
 * floating-point elements to packed BF16 (16-bit) floating-point elements. The resulting
 * 16-bit elements are placed in the low 16-bits within each 32-bit element of the returned vector.
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileCvtrowps2bf16l(src: Tile1024i, row: UInt): M512bh = tcvtrowps2bf16lInternal(src.rows, src.colsb, src.tile, row).asM512bh()

/**
 * Moves one row of tile data into a zmm vector register
 */
public fun tileMovrow(row: UInt, tile: Int): M512i {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    return tilemovrow(tile.toByte(), row).asM512i()
}

/**
 * Moves one row of tile data into a zmm vector register
 */
public fun tileMovrowi(tile: Int, row: Int): M512i {
    run {
        check(0 <= tile && tile < (1 shl 3)) { "TILE doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= row && row < (1 shl 6)) { "ROW doesn't fit in 6 bits (unsigned)" }
    }
    return tilemovrowi(tile.toByte(), row.toUInt()).asM512i()
}

/**
 * Moves one row of tile data into a zmm vector register
 * The shape of the tile is specified in the struct of [tile1024i]. The register of the tile is allocated by the compiler.
 */
public fun tileMovrow(src: Tile1024i, row: UInt): M512i = tilemovrowInternal(src.rows, src.colsb, src.tile, row).asM512i()

public expect fun ldtilecfg(memAddr: ConstPtr)

public expect fun sttilecfg(memAddr: MutPtr)

public expect fun tileloadd64(dst: Byte, base: ConstPtr, stride: ULong)

public expect fun tileloadd64Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile

public expect fun tileloaddt164(dst: Byte, base: ConstPtr, stride: ULong)

public expect fun tileloaddt164Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile

public expect fun tilerelease()

public expect fun tilestored64(dst: Byte, base: MutPtr, stride: ULong)

public expect fun tilestored64Internal(rows: UShort, colsb: UShort, base: MutPtr, stride: ULong, src: Tile)

public expect fun tilezero(dst: Byte)

public expect fun tilezeroInternal(rows: UShort, colsb: UShort): Tile

public expect fun tdpbf16ps(dst: Byte, a: Byte, b: Byte)

public expect fun tdpbf16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tdpbuud(dst: Byte, a: Byte, b: Byte)

public expect fun tdpbuudInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tdpbusd(dst: Byte, a: Byte, b: Byte)

public expect fun tdpbusdInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tdpbsud(dst: Byte, a: Byte, b: Byte)

public expect fun tdpbsudInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tdpbssd(dst: Byte, a: Byte, b: Byte)

public expect fun tdpbssdInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tdpfp16ps(dst: Byte, a: Byte, b: Byte)

public expect fun tdpfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tcmmimfp16ps(dst: Byte, a: Byte, b: Byte)

public expect fun tcmmimfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tcmmrlfp16ps(dst: Byte, a: Byte, b: Byte)

public expect fun tcmmrlfp16psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tdpbf8ps(dst: Byte, a: Byte, b: Byte)

public expect fun tdpbf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tdpbhf8ps(dst: Byte, a: Byte, b: Byte)

public expect fun tdpbhf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tdphbf8ps(dst: Byte, a: Byte, b: Byte)

public expect fun tdphbf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tdphf8ps(dst: Byte, a: Byte, b: Byte)

public expect fun tdphf8psInternal(m: UShort, n: UShort, k: UShort, dst: Tile, a: Tile, b: Tile): Tile

public expect fun tileloaddrs64(dst: Byte, base: ConstPtr, stride: ULong)

public expect fun tileloaddrs64Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile

public expect fun tileloaddrst164(dst: Byte, base: ConstPtr, stride: ULong)

public expect fun tileloaddrst164Internal(rows: UShort, colsb: UShort, base: ConstPtr, stride: ULong): Tile

public expect fun tcvtrowd2ps(tile: Byte, row: UInt): F32x16

public expect fun tcvtrowd2psi(tile: Byte, row: UInt): F32x16

public expect fun tcvtrowd2psInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F32x16

public expect fun tcvtrowps2phh(tile: Byte, row: UInt): F16x32

public expect fun tcvtrowps2phhi(tile: Byte, row: UInt): F16x32

public expect fun tcvtrowps2phhInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F16x32

public expect fun tcvtrowps2phl(tile: Byte, row: UInt): F16x32

public expect fun tcvtrowps2phli(tile: Byte, row: UInt): F16x32

public expect fun tcvtrowps2phlInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): F16x32

public expect fun tcvtrowps2bf16h(tile: Byte, row: UInt): U16x32

public expect fun tcvtrowps2bf16hi(tile: Byte, row: UInt): U16x32

public expect fun tcvtrowps2bf16hInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): U16x32

public expect fun tcvtrowps2bf16l(tile: Byte, row: UInt): U16x32

public expect fun tcvtrowps2bf16li(tile: Byte, row: UInt): U16x32

public expect fun tcvtrowps2bf16lInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): U16x32

public expect fun tilemovrow(tile: Byte, row: UInt): I32x16

public expect fun tilemovrowi(tile: Byte, row: UInt): I32x16

public expect fun tilemovrowInternal(rows: UShort, colsb: UShort, src: Tile, row: UInt): I32x16

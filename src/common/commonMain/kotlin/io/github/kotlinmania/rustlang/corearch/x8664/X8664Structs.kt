package io.github.kotlinmania.rustlang.corearch.x8664

/**
 * A tile register, used by AMX instructions.
 *
 * This type is the same as the `__tile1024i` type defined by Intel, representing a 1024-byte tile register.
 * Usage of this type typically corresponds to the `amx - tile` and up target features for x8664.
 *
 * This struct contains the tile configuration information as well as the tile itself.
 * The tile configuration information consists of the row count and the size of each column in bytes,
 * with `row * colsb` never exceeding 1024.
 *
 * The typical usage pattern looks like
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
 *
 * val tile = MaybeUninit.uninit(PrimitiveLayouts.U8)
 * val tilePtr = tile.asMutPtr()
 * tilePtr.rows = rows
 * tilePtr.colsb = colsb
 * tileZero(tilePtr)
 * val tile1 = tile.assumeInit()
 * ```
 * Most intrinsics using `__tile1024i` (except for the store intrinsics) have a destination parameter
 * of type `* __tile1024i`, and it expects the `rows` and `colsb` fields of the destination
 * to be initialized. After the function call, the whole struct can be assumed to be initialized.
 * Moreover, for dot-product intrinsics, it is UB if the shape of two operands are not compatible
 * as a matrix product or if the shape of the destination doesn't match the expected shape.
 */
public open class Tile1024i(
    public var rows: UShort,
    public var colsb: UShort,
    public var tile: Tile,
)

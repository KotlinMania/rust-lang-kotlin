// port-lint: source x86_64/movrs.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// Read-shared Move instructions

public expect fun movrsqi(src: ConstPtr): Byte

public expect fun movrshi(src: ConstPtr): Short

public expect fun movrssi(src: ConstPtr): Int

public expect fun movrsdi(src: ConstPtr): Long

/**
 * Moves a byte from the source to the destination, with an indication that the source memory
 * location is likely to become read-shared by multiple processors, i.e., read in the future by at
 * least one other processor before it is written, assuming it is ever written in the future.
 */
public fun movrsI8(src: ConstPtr): Byte = movrsqi(src)

/**
 * Moves a 16-bit word from the source to the destination, with an indication that the source memory
 * location is likely to become read-shared by multiple processors, i.e., read in the future by at
 * least one other processor before it is written, assuming it is ever written in the future.
 */
public fun movrsI16(src: ConstPtr): Short = movrshi(src)

/**
 * Moves a 32-bit doubleword from the source to the destination, with an indication that the source
 * memory location is likely to become read-shared by multiple processors, i.e., read in the future
 * by at least one other processor before it is written, assuming it is ever written in the future.
 */
public fun movrsI32(src: ConstPtr): Int = movrssi(src)

/**
 * Moves a 64-bit quadword from the source to the destination, with an indication that the source
 * memory location is likely to become read-shared by multiple processors, i.e., read in the future
 * by at least one other processor before it is written, assuming it is ever written in the future.
 */
public fun movrsI64(src: ConstPtr): Long = movrsdi(src)

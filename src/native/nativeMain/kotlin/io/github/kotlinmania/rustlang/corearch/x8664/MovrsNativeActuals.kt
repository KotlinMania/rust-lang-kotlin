@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664MovrsMovrsdi
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664MovrsMovrshi
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664MovrsMovrsqi
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664MovrsMovrssi
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun movrsqi(src: ConstPtr): Byte =
    rustlangCorearchX8664MovrsMovrsqi(src.rawAddress.toCPointer<ByteVar>())

public actual fun movrshi(src: ConstPtr): Short =
    rustlangCorearchX8664MovrsMovrshi(src.rawAddress.toCPointer<ByteVar>())

public actual fun movrssi(src: ConstPtr): Int =
    rustlangCorearchX8664MovrsMovrssi(src.rawAddress.toCPointer<ByteVar>())

public actual fun movrsdi(src: ConstPtr): Long =
    rustlangCorearchX8664MovrsMovrsdi(src.rawAddress.toCPointer<ByteVar>())

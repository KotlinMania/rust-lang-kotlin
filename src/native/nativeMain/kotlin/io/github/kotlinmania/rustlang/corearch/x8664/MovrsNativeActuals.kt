@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664MovrsMovrsdi
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664MovrsMovrshi
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664MovrsMovrsqi
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664MovrsMovrssi
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun movrsqi(src: ConstPtr): Byte =
    corearchX8664MovrsMovrsqi(src.rawAddress.toCPointer<ByteVar>())

public actual fun movrshi(src: ConstPtr): Short =
    corearchX8664MovrsMovrshi(src.rawAddress.toCPointer<ByteVar>())

public actual fun movrssi(src: ConstPtr): Int =
    corearchX8664MovrsMovrssi(src.rawAddress.toCPointer<ByteVar>())

public actual fun movrsdi(src: ConstPtr): Long =
    corearchX8664MovrsMovrsdi(src.rawAddress.toCPointer<ByteVar>())

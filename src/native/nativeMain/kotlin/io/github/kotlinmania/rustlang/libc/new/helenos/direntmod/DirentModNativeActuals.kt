@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.direntmod

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.helenos.direntmod.cinterop.libcNewHelenosDirentmodDirentModClosedir
import io.github.kotlinmania.rustlang.libc.new.helenos.direntmod.cinterop.libcNewHelenosDirentmodDirentModOpendir
import io.github.kotlinmania.rustlang.libc.new.helenos.direntmod.cinterop.libcNewHelenosDirentmodDirentModReaddir
import io.github.kotlinmania.rustlang.libc.new.helenos.direntmod.cinterop.libcNewHelenosDirentmodDirentModRewinddir
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun opendir(name: ConstPtr): MutPtr =
    MutPtr(libcNewHelenosDirentmodDirentModOpendir(name.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun readdir(dir: MutPtr): MutPtr =
    MutPtr(libcNewHelenosDirentmodDirentModReaddir(dir.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun closedir(dir: MutPtr): CInt =
    libcNewHelenosDirentmodDirentModClosedir(dir.rawAddress.toCPointer<ByteVar>())

public actual fun rewinddir(dir: MutPtr) {
    libcNewHelenosDirentmodDirentModRewinddir(dir.rawAddress.toCPointer<ByteVar>())
}

@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.direntmod

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

private object NewHelenosDirentmodDirentModJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewHelenosDirentmodDirentModOpendir(name: Long): Long

    @JvmStatic
    external fun libcNewHelenosDirentmodDirentModReaddir(dir: Long): Long

    @JvmStatic
    external fun libcNewHelenosDirentmodDirentModClosedir(dir: Long): Int

    @JvmStatic
    external fun libcNewHelenosDirentmodDirentModRewinddir(dir: Long)
}

public actual fun opendir(name: ConstPtr): MutPtr =
    MutPtr(NewHelenosDirentmodDirentModJniBridge.libcNewHelenosDirentmodDirentModOpendir(name.rawAddress))

public actual fun readdir(dir: MutPtr): MutPtr =
    MutPtr(NewHelenosDirentmodDirentModJniBridge.libcNewHelenosDirentmodDirentModReaddir(dir.rawAddress))

public actual fun closedir(dir: MutPtr): CInt =
    NewHelenosDirentmodDirentModJniBridge.libcNewHelenosDirentmodDirentModClosedir(dir.rawAddress)

public actual fun rewinddir(dir: MutPtr) {
    NewHelenosDirentmodDirentModJniBridge.libcNewHelenosDirentmodDirentModRewinddir(dir.rawAddress)
}

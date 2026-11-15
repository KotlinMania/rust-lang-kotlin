@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

private object CorearchLoongarch32JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchLoongarch32Cacop(a: Int, b: Int, c: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarch32Csrrd(a: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch32Csrwr(a: Int, b: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch32Csrxchg(a: Int, b: Int, c: Int): Int
}

public actual fun cacop(a: Int, b: Int, c: Int) {
    CorearchLoongarch32JniBridge.rustlangCorearchLoongarch32Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Int =
    CorearchLoongarch32JniBridge.rustlangCorearchLoongarch32Csrrd(a)

public actual fun csrwr(a: Int, b: Int): Int =
    CorearchLoongarch32JniBridge.rustlangCorearchLoongarch32Csrwr(a, b)

public actual fun csrxchg(a: Int, b: Int, c: Int): Int =
    CorearchLoongarch32JniBridge.rustlangCorearchLoongarch32Csrxchg(a, b, c)

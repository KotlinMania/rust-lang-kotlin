@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

private object Loongarch32JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchLoongarch32ExternCacop(a: Int, b: Int, c: Int)

    @JvmStatic
    external fun corearchLoongarch32ExternCsrrd(a: Int): Int

    @JvmStatic
    external fun corearchLoongarch32ExternCsrwr(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch32ExternCsrxchg(a: Int, b: Int, c: Int): Int
}

public actual fun externCacop(a: Int, b: Int, c: Int) {
    Loongarch32JniBridge.corearchLoongarch32ExternCacop(a, b, c)
}

public actual fun externCsrrd(a: Int): Int =
    Loongarch32JniBridge.corearchLoongarch32ExternCsrrd(a)

public actual fun externCsrwr(a: Int, b: Int): Int =
    Loongarch32JniBridge.corearchLoongarch32ExternCsrwr(a, b)

public actual fun externCsrxchg(a: Int, b: Int, c: Int): Int =
    Loongarch32JniBridge.corearchLoongarch32ExternCsrxchg(a, b, c)

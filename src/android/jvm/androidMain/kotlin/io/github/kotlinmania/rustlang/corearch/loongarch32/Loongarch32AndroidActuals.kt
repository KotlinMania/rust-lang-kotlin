@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

private object Loongarch32JniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchLoongarch32Cacop(a: Int, b: Int, c: Int)

    @JvmStatic
    external fun corearchLoongarch32Csrrd(a: Int): Int

    @JvmStatic
    external fun corearchLoongarch32Csrwr(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch32Csrxchg(a: Int, b: Int, c: Int): Int
}

public actual fun cacop(a: Int, b: Int, c: Int) {
    Loongarch32JniBridge.corearchLoongarch32Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Int =
    Loongarch32JniBridge.corearchLoongarch32Csrrd(a)

public actual fun csrwr(a: Int, b: Int): Int =
    Loongarch32JniBridge.corearchLoongarch32Csrwr(a, b)

public actual fun csrxchg(a: Int, b: Int, c: Int): Int =
    Loongarch32JniBridge.corearchLoongarch32Csrxchg(a, b, c)

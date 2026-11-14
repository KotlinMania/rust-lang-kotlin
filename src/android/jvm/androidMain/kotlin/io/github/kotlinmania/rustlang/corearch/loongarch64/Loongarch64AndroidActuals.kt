@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

private object Loongarch64JniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchLoongarch64CrcWBW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64CrcWHW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64CrcWWW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64CrcWDW(a: Long, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64CrccWBW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64CrccWHW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64CrccWWW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64CrccWDW(a: Long, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64Cacop(a: Long, b: Long, c: Long)

    @JvmStatic
    external fun corearchLoongarch64Csrrd(a: Int): Long

    @JvmStatic
    external fun corearchLoongarch64Csrwr(a: Long, b: Int): Long

    @JvmStatic
    external fun corearchLoongarch64Csrxchg(a: Long, b: Long, c: Int): Long

    @JvmStatic
    external fun corearchLoongarch64IocsrrdD(a: Int): Long

    @JvmStatic
    external fun corearchLoongarch64IocsrwrD(a: Long, b: Int)

    @JvmStatic
    external fun corearchLoongarch64Asrtle(a: Long, b: Long)

    @JvmStatic
    external fun corearchLoongarch64Asrtgt(a: Long, b: Long)

    @JvmStatic
    external fun corearchLoongarch64Lddir(a: Long, b: Long): Long

    @JvmStatic
    external fun corearchLoongarch64Ldpte(a: Long, b: Long)
}

public actual fun crcWBW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64CrcWBW(a, b)

public actual fun crcWHW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64CrcWHW(a, b)

public actual fun crcWWW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64CrcWWW(a, b)

public actual fun crcWDW(a: Long, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64CrcWDW(a, b)

public actual fun crccWBW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64CrccWBW(a, b)

public actual fun crccWHW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64CrccWHW(a, b)

public actual fun crccWWW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64CrccWWW(a, b)

public actual fun crccWDW(a: Long, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64CrccWDW(a, b)

public actual fun cacop(a: Long, b: Long, c: Long) {
    Loongarch64JniBridge.corearchLoongarch64Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Long =
    Loongarch64JniBridge.corearchLoongarch64Csrrd(a)

public actual fun csrwr(a: Long, b: Int): Long =
    Loongarch64JniBridge.corearchLoongarch64Csrwr(a, b)

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    Loongarch64JniBridge.corearchLoongarch64Csrxchg(a, b, c)

public actual fun iocsrrdD(a: Int): Long =
    Loongarch64JniBridge.corearchLoongarch64IocsrrdD(a)

public actual fun iocsrwrD(a: Long, b: Int) {
    Loongarch64JniBridge.corearchLoongarch64IocsrwrD(a, b)
}

public actual fun asrtle(a: Long, b: Long) {
    Loongarch64JniBridge.corearchLoongarch64Asrtle(a, b)
}

public actual fun asrtgt(a: Long, b: Long) {
    Loongarch64JniBridge.corearchLoongarch64Asrtgt(a, b)
}

public actual fun lddir(a: Long, b: Long): Long =
    Loongarch64JniBridge.corearchLoongarch64Lddir(a, b)

public actual fun ldpte(a: Long, b: Long) {
    Loongarch64JniBridge.corearchLoongarch64Ldpte(a, b)
}

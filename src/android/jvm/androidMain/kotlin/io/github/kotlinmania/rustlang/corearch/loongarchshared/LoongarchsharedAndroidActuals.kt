@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

private object LoongarchsharedJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchLoongarchsharedDbar(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedIbar(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedMovgr2fcsr(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedMovfcsr2gr(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedIocsrrdB(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedIocsrrdH(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedIocsrrdW(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedIocsrwrB(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedIocsrwrH(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedIocsrwrW(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedBreakTarget(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedCpucfg(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedSyscall(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedFrecipeS(a: Float): Float

    @JvmStatic
    external fun corearchLoongarchsharedFrecipeD(a: Double): Double

    @JvmStatic
    external fun corearchLoongarchsharedFrsqrteS(a: Float): Float

    @JvmStatic
    external fun corearchLoongarchsharedFrsqrteD(a: Double): Double
}

public actual fun dbar(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedDbar(a)
}

public actual fun ibar(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedIbar(a)
}

public actual fun movgr2fcsr(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedMovgr2fcsr(a, b)
}

public actual fun movfcsr2gr(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedMovfcsr2gr(a)

public actual fun iocsrrdB(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedIocsrrdB(a)

public actual fun iocsrrdH(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedIocsrrdH(a)

public actual fun iocsrrdW(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedIocsrrdW(a)

public actual fun iocsrwrB(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedIocsrwrB(a, b)
}

public actual fun iocsrwrH(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedIocsrwrH(a, b)
}

public actual fun iocsrwrW(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedIocsrwrW(a, b)
}

public actual fun breakTarget(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedBreakTarget(a)
}

public actual fun cpucfg(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedCpucfg(a)

public actual fun syscall(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedSyscall(a)
}

public actual fun frecipeS(a: Float): Float =
    LoongarchsharedJniBridge.corearchLoongarchsharedFrecipeS(a)

public actual fun frecipeD(a: Double): Double =
    LoongarchsharedJniBridge.corearchLoongarchsharedFrecipeD(a)

public actual fun frsqrteS(a: Float): Float =
    LoongarchsharedJniBridge.corearchLoongarchsharedFrsqrteS(a)

public actual fun frsqrteD(a: Double): Double =
    LoongarchsharedJniBridge.corearchLoongarchsharedFrsqrteD(a)

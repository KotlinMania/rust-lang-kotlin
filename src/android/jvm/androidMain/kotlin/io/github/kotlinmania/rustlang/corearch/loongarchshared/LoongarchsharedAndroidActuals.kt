@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

private object CorearchLoongarchsharedJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedDbar(a: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedIbar(a: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedMovgr2fcsr(a: Int, b: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedMovfcsr2gr(a: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedIocsrrdB(a: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedIocsrrdH(a: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedIocsrrdW(a: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedIocsrwrB(a: Int, b: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedIocsrwrH(a: Int, b: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedIocsrwrW(a: Int, b: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedBreakTarget(a: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedCpucfg(a: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedSyscall(a: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedFrecipeS(a: Float): Float

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedFrecipeD(a: Double): Double

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedFrsqrteS(a: Float): Float

    @JvmStatic
    external fun rustlangCorearchLoongarchsharedFrsqrteD(a: Double): Double
}

public actual fun dbar(a: Int) {
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedDbar(a)
}

public actual fun ibar(a: Int) {
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedIbar(a)
}

public actual fun movgr2fcsr(a: Int, b: Int) {
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedMovgr2fcsr(a, b)
}

public actual fun movfcsr2gr(a: Int): Int =
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedMovfcsr2gr(a)

public actual fun iocsrrdB(a: Int): Int =
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedIocsrrdB(a)

public actual fun iocsrrdH(a: Int): Int =
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedIocsrrdH(a)

public actual fun iocsrrdW(a: Int): Int =
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedIocsrrdW(a)

public actual fun iocsrwrB(a: Int, b: Int) {
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedIocsrwrB(a, b)
}

public actual fun iocsrwrH(a: Int, b: Int) {
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedIocsrwrH(a, b)
}

public actual fun iocsrwrW(a: Int, b: Int) {
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedIocsrwrW(a, b)
}

public actual fun breakTarget(a: Int) {
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedBreakTarget(a)
}

public actual fun cpucfg(a: Int): Int =
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedCpucfg(a)

public actual fun syscall(a: Int) {
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedSyscall(a)
}

public actual fun frecipeS(a: Float): Float =
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedFrecipeS(a)

public actual fun frecipeD(a: Double): Double =
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedFrecipeD(a)

public actual fun frsqrteS(a: Float): Float =
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedFrsqrteS(a)

public actual fun frsqrteD(a: Double): Double =
    CorearchLoongarchsharedJniBridge.rustlangCorearchLoongarchsharedFrsqrteD(a)

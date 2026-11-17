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
    external fun corearchLoongarchsharedExternIocsrrdB(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrrdH(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrrdW(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrwrB(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrwrH(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrwrW(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedBreakTarget(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternCpucfg(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedSyscall(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternFrecipeS(a: Float): Float

    @JvmStatic
    external fun corearchLoongarchsharedExternFrecipeD(a: Double): Double

    @JvmStatic
    external fun corearchLoongarchsharedExternFrsqrteS(a: Float): Float

    @JvmStatic
    external fun corearchLoongarchsharedExternFrsqrteD(a: Double): Double
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

public actual fun externIocsrrdB(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrrdB(a)

public actual fun externIocsrrdH(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrrdH(a)

public actual fun externIocsrrdW(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrrdW(a)

public actual fun externIocsrwrB(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrwrB(a, b)
}

public actual fun externIocsrwrH(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrwrH(a, b)
}

public actual fun externIocsrwrW(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrwrW(a, b)
}

public actual fun breakTarget(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedBreakTarget(a)
}

public actual fun externCpucfg(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternCpucfg(a)

public actual fun syscall(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedSyscall(a)
}

public actual fun externFrecipeS(a: Float): Float =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternFrecipeS(a)

public actual fun externFrecipeD(a: Double): Double =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternFrecipeD(a)

public actual fun externFrsqrteS(a: Float): Float =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternFrsqrteS(a)

public actual fun externFrsqrteD(a: Double): Double =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternFrsqrteD(a)

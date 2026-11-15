@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared

private object CorearchArmsharedHintsJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchArmsharedHintsHint(arg0: Int)
}

public actual fun hint(arg0: Int) {
    CorearchArmsharedHintsJniBridge.rustlangCorearchArmsharedHintsHint(arg0)
}

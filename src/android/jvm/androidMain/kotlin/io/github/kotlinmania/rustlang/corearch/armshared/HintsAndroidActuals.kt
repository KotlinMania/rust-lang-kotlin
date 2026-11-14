@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared

private object ArmsharedHintsJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchArmsharedHintsHint(arg0: Int)
}

public actual fun hint(arg0: Int) {
    ArmsharedHintsJniBridge.corearchArmsharedHintsHint(arg0)
}

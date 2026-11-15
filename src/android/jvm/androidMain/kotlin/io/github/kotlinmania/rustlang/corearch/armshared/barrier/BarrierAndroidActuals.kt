@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

private object CorearchArmsharedBarrierJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchArmsharedBarrierDmb(arg0: Int)

    @JvmStatic
    external fun rustlangCorearchArmsharedBarrierDsb(arg0: Int)

    @JvmStatic
    external fun rustlangCorearchArmsharedBarrierIsb(arg0: Int)
}

public actual fun dmb(arg0: Int) {
    CorearchArmsharedBarrierJniBridge.rustlangCorearchArmsharedBarrierDmb(arg0)
}

public actual fun dsb(arg0: Int) {
    CorearchArmsharedBarrierJniBridge.rustlangCorearchArmsharedBarrierDsb(arg0)
}

public actual fun isb(arg0: Int) {
    CorearchArmsharedBarrierJniBridge.rustlangCorearchArmsharedBarrierIsb(arg0)
}

@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

private object ArmsharedBarrierJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchArmsharedBarrierDmb(arg0: Int)

    @JvmStatic
    external fun corearchArmsharedBarrierDsb(arg0: Int)

    @JvmStatic
    external fun corearchArmsharedBarrierIsb(arg0: Int)
}

public actual fun dmb(arg0: Int) {
    ArmsharedBarrierJniBridge.corearchArmsharedBarrierDmb(arg0)
}

public actual fun dsb(arg0: Int) {
    ArmsharedBarrierJniBridge.corearchArmsharedBarrierDsb(arg0)
}

public actual fun isb(arg0: Int) {
    ArmsharedBarrierJniBridge.corearchArmsharedBarrierIsb(arg0)
}

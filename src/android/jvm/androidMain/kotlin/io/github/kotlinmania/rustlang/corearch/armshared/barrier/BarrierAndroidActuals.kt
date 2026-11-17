@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

private object ArmsharedBarrierJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchArmsharedBarrierExternDmb(arg0: Int)

    @JvmStatic
    external fun corearchArmsharedBarrierExternDsb(arg0: Int)

    @JvmStatic
    external fun corearchArmsharedBarrierExternIsb(arg0: Int)
}

public actual fun externDmb(arg0: Int) {
    ArmsharedBarrierJniBridge.corearchArmsharedBarrierExternDmb(arg0)
}

public actual fun externDsb(arg0: Int) {
    ArmsharedBarrierJniBridge.corearchArmsharedBarrierExternDsb(arg0)
}

public actual fun externIsb(arg0: Int) {
    ArmsharedBarrierJniBridge.corearchArmsharedBarrierExternIsb(arg0)
}

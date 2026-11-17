@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternDbar")
private external fun wasiCorearchLoongarchsharedExternDbar(a: Int): Unit

public actual fun externDbar(a: Int): Unit =
    wasiCorearchLoongarchsharedExternDbar(a)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternIbar")
private external fun wasiCorearchLoongarchsharedExternIbar(a: Int): Unit

public actual fun externIbar(a: Int): Unit =
    wasiCorearchLoongarchsharedExternIbar(a)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternMovgr2fcsr")
private external fun wasiCorearchLoongarchsharedExternMovgr2fcsr(a: Int, b: Int): Unit

public actual fun externMovgr2fcsr(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedExternMovgr2fcsr(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternMovfcsr2gr")
private external fun wasiCorearchLoongarchsharedExternMovfcsr2gr(a: Int): Int

public actual fun externMovfcsr2gr(a: Int): Int =
    wasiCorearchLoongarchsharedExternMovfcsr2gr(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternIocsrrdB")
private external fun wasiCorearchLoongarchsharedExternIocsrrdB(a: Int): Int

public actual fun externIocsrrdB(a: Int): Int =
    wasiCorearchLoongarchsharedExternIocsrrdB(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternIocsrrdH")
private external fun wasiCorearchLoongarchsharedExternIocsrrdH(a: Int): Int

public actual fun externIocsrrdH(a: Int): Int =
    wasiCorearchLoongarchsharedExternIocsrrdH(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternIocsrrdW")
private external fun wasiCorearchLoongarchsharedExternIocsrrdW(a: Int): Int

public actual fun externIocsrrdW(a: Int): Int =
    wasiCorearchLoongarchsharedExternIocsrrdW(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternIocsrwrB")
private external fun wasiCorearchLoongarchsharedExternIocsrwrB(a: Int, b: Int): Unit

public actual fun externIocsrwrB(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedExternIocsrwrB(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternIocsrwrH")
private external fun wasiCorearchLoongarchsharedExternIocsrwrH(a: Int, b: Int): Unit

public actual fun externIocsrwrH(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedExternIocsrwrH(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternIocsrwrW")
private external fun wasiCorearchLoongarchsharedExternIocsrwrW(a: Int, b: Int): Unit

public actual fun externIocsrwrW(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedExternIocsrwrW(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedBreakTarget")
private external fun wasiCorearchLoongarchsharedBreakTarget(a: Int): Unit

public actual fun breakTarget(a: Int): Unit =
    wasiCorearchLoongarchsharedBreakTarget(a)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternCpucfg")
private external fun wasiCorearchLoongarchsharedExternCpucfg(a: Int): Int

public actual fun externCpucfg(a: Int): Int =
    wasiCorearchLoongarchsharedExternCpucfg(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternSyscall")
private external fun wasiCorearchLoongarchsharedExternSyscall(a: Int): Unit

public actual fun externSyscall(a: Int): Unit =
    wasiCorearchLoongarchsharedExternSyscall(a)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternFrecipeS")
private external fun wasiCorearchLoongarchsharedExternFrecipeS(a: Float): Float

public actual fun externFrecipeS(a: Float): Float =
    wasiCorearchLoongarchsharedExternFrecipeS(a).toFloat()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternFrecipeD")
private external fun wasiCorearchLoongarchsharedExternFrecipeD(a: Double): Double

public actual fun externFrecipeD(a: Double): Double =
    wasiCorearchLoongarchsharedExternFrecipeD(a).toDouble()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternFrsqrteS")
private external fun wasiCorearchLoongarchsharedExternFrsqrteS(a: Float): Float

public actual fun externFrsqrteS(a: Float): Float =
    wasiCorearchLoongarchsharedExternFrsqrteS(a).toFloat()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedExternFrsqrteD")
private external fun wasiCorearchLoongarchsharedExternFrsqrteD(a: Double): Double

public actual fun externFrsqrteD(a: Double): Double =
    wasiCorearchLoongarchsharedExternFrsqrteD(a).toDouble()

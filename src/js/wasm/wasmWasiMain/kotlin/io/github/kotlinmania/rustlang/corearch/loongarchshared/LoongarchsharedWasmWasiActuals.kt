@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedDbar")
private external fun wasiCorearchLoongarchsharedDbar(a: Int): Unit

public actual fun dbar(a: Int): Unit =
    wasiCorearchLoongarchsharedDbar(a)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedIbar")
private external fun wasiCorearchLoongarchsharedIbar(a: Int): Unit

public actual fun ibar(a: Int): Unit =
    wasiCorearchLoongarchsharedIbar(a)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedMovgr2fcsr")
private external fun wasiCorearchLoongarchsharedMovgr2fcsr(a: Int, b: Int): Unit

public actual fun movgr2fcsr(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedMovgr2fcsr(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedMovfcsr2gr")
private external fun wasiCorearchLoongarchsharedMovfcsr2gr(a: Int): Int

public actual fun movfcsr2gr(a: Int): Int =
    wasiCorearchLoongarchsharedMovfcsr2gr(a).toInt()

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

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedSyscall")
private external fun wasiCorearchLoongarchsharedSyscall(a: Int): Unit

public actual fun syscall(a: Int): Unit =
    wasiCorearchLoongarchsharedSyscall(a)

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

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

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedIocsrrdB")
private external fun wasiCorearchLoongarchsharedIocsrrdB(a: Int): Int

public actual fun iocsrrdB(a: Int): Int =
    wasiCorearchLoongarchsharedIocsrrdB(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedIocsrrdH")
private external fun wasiCorearchLoongarchsharedIocsrrdH(a: Int): Int

public actual fun iocsrrdH(a: Int): Int =
    wasiCorearchLoongarchsharedIocsrrdH(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedIocsrrdW")
private external fun wasiCorearchLoongarchsharedIocsrrdW(a: Int): Int

public actual fun iocsrrdW(a: Int): Int =
    wasiCorearchLoongarchsharedIocsrrdW(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedIocsrwrB")
private external fun wasiCorearchLoongarchsharedIocsrwrB(a: Int, b: Int): Unit

public actual fun iocsrwrB(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedIocsrwrB(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedIocsrwrH")
private external fun wasiCorearchLoongarchsharedIocsrwrH(a: Int, b: Int): Unit

public actual fun iocsrwrH(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedIocsrwrH(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedIocsrwrW")
private external fun wasiCorearchLoongarchsharedIocsrwrW(a: Int, b: Int): Unit

public actual fun iocsrwrW(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedIocsrwrW(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedBreakTarget")
private external fun wasiCorearchLoongarchsharedBreakTarget(a: Int): Unit

public actual fun breakTarget(a: Int): Unit =
    wasiCorearchLoongarchsharedBreakTarget(a)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedCpucfg")
private external fun wasiCorearchLoongarchsharedCpucfg(a: Int): Int

public actual fun cpucfg(a: Int): Int =
    wasiCorearchLoongarchsharedCpucfg(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedSyscall")
private external fun wasiCorearchLoongarchsharedSyscall(a: Int): Unit

public actual fun syscall(a: Int): Unit =
    wasiCorearchLoongarchsharedSyscall(a)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedFrecipeS")
private external fun wasiCorearchLoongarchsharedFrecipeS(a: Float): Float

public actual fun frecipeS(a: Float): Float =
    wasiCorearchLoongarchsharedFrecipeS(a).toFloat()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedFrecipeD")
private external fun wasiCorearchLoongarchsharedFrecipeD(a: Double): Double

public actual fun frecipeD(a: Double): Double =
    wasiCorearchLoongarchsharedFrecipeD(a).toDouble()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedFrsqrteS")
private external fun wasiCorearchLoongarchsharedFrsqrteS(a: Float): Float

public actual fun frsqrteS(a: Float): Float =
    wasiCorearchLoongarchsharedFrsqrteS(a).toFloat()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarchsharedFrsqrteD")
private external fun wasiCorearchLoongarchsharedFrsqrteD(a: Double): Double

public actual fun frsqrteD(a: Double): Double =
    wasiCorearchLoongarchsharedFrsqrteD(a).toDouble()

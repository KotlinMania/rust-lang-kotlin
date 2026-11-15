@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedDbar")
private external fun wasiRustlangCorearchLoongarchsharedDbar(a: Int): Unit

public actual fun dbar(a: Int): Unit =
    wasiRustlangCorearchLoongarchsharedDbar(a)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedIbar")
private external fun wasiRustlangCorearchLoongarchsharedIbar(a: Int): Unit

public actual fun ibar(a: Int): Unit =
    wasiRustlangCorearchLoongarchsharedIbar(a)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedMovgr2fcsr")
private external fun wasiRustlangCorearchLoongarchsharedMovgr2fcsr(a: Int, b: Int): Unit

public actual fun movgr2fcsr(a: Int, b: Int): Unit =
    wasiRustlangCorearchLoongarchsharedMovgr2fcsr(a, b)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedMovfcsr2gr")
private external fun wasiRustlangCorearchLoongarchsharedMovfcsr2gr(a: Int): Int

public actual fun movfcsr2gr(a: Int): Int =
    wasiRustlangCorearchLoongarchsharedMovfcsr2gr(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedIocsrrdB")
private external fun wasiRustlangCorearchLoongarchsharedIocsrrdB(a: Int): Int

public actual fun iocsrrdB(a: Int): Int =
    wasiRustlangCorearchLoongarchsharedIocsrrdB(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedIocsrrdH")
private external fun wasiRustlangCorearchLoongarchsharedIocsrrdH(a: Int): Int

public actual fun iocsrrdH(a: Int): Int =
    wasiRustlangCorearchLoongarchsharedIocsrrdH(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedIocsrrdW")
private external fun wasiRustlangCorearchLoongarchsharedIocsrrdW(a: Int): Int

public actual fun iocsrrdW(a: Int): Int =
    wasiRustlangCorearchLoongarchsharedIocsrrdW(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedIocsrwrB")
private external fun wasiRustlangCorearchLoongarchsharedIocsrwrB(a: Int, b: Int): Unit

public actual fun iocsrwrB(a: Int, b: Int): Unit =
    wasiRustlangCorearchLoongarchsharedIocsrwrB(a, b)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedIocsrwrH")
private external fun wasiRustlangCorearchLoongarchsharedIocsrwrH(a: Int, b: Int): Unit

public actual fun iocsrwrH(a: Int, b: Int): Unit =
    wasiRustlangCorearchLoongarchsharedIocsrwrH(a, b)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedIocsrwrW")
private external fun wasiRustlangCorearchLoongarchsharedIocsrwrW(a: Int, b: Int): Unit

public actual fun iocsrwrW(a: Int, b: Int): Unit =
    wasiRustlangCorearchLoongarchsharedIocsrwrW(a, b)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedBreakTarget")
private external fun wasiRustlangCorearchLoongarchsharedBreakTarget(a: Int): Unit

public actual fun breakTarget(a: Int): Unit =
    wasiRustlangCorearchLoongarchsharedBreakTarget(a)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedCpucfg")
private external fun wasiRustlangCorearchLoongarchsharedCpucfg(a: Int): Int

public actual fun cpucfg(a: Int): Int =
    wasiRustlangCorearchLoongarchsharedCpucfg(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedSyscall")
private external fun wasiRustlangCorearchLoongarchsharedSyscall(a: Int): Unit

public actual fun syscall(a: Int): Unit =
    wasiRustlangCorearchLoongarchsharedSyscall(a)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedFrecipeS")
private external fun wasiRustlangCorearchLoongarchsharedFrecipeS(a: Float): Float

public actual fun frecipeS(a: Float): Float =
    wasiRustlangCorearchLoongarchsharedFrecipeS(a).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedFrecipeD")
private external fun wasiRustlangCorearchLoongarchsharedFrecipeD(a: Double): Double

public actual fun frecipeD(a: Double): Double =
    wasiRustlangCorearchLoongarchsharedFrecipeD(a).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedFrsqrteS")
private external fun wasiRustlangCorearchLoongarchsharedFrsqrteS(a: Float): Float

public actual fun frsqrteS(a: Float): Float =
    wasiRustlangCorearchLoongarchsharedFrsqrteS(a).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarchsharedFrsqrteD")
private external fun wasiRustlangCorearchLoongarchsharedFrsqrteD(a: Double): Double

public actual fun frsqrteD(a: Double): Double =
    wasiRustlangCorearchLoongarchsharedFrsqrteD(a).toDouble()

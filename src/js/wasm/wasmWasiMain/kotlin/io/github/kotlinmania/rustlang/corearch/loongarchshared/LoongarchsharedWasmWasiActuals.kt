@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedDbar")
private external fun wasiCorearchLoongarchsharedDbar(a: Int): Unit

public actual fun dbar(a: Int): Unit =
    wasiCorearchLoongarchsharedDbar(a)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedIbar")
private external fun wasiCorearchLoongarchsharedIbar(a: Int): Unit

public actual fun ibar(a: Int): Unit =
    wasiCorearchLoongarchsharedIbar(a)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedMovgr2fcsr")
private external fun wasiCorearchLoongarchsharedMovgr2fcsr(a: Int, b: Int): Unit

public actual fun movgr2fcsr(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedMovgr2fcsr(a, b)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedMovfcsr2gr")
private external fun wasiCorearchLoongarchsharedMovfcsr2gr(a: Int): Int

public actual fun movfcsr2gr(a: Int): Int =
    wasiCorearchLoongarchsharedMovfcsr2gr(a).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedIocsrrdB")
private external fun wasiCorearchLoongarchsharedIocsrrdB(a: Int): Int

public actual fun iocsrrdB(a: Int): Int =
    wasiCorearchLoongarchsharedIocsrrdB(a).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedIocsrrdH")
private external fun wasiCorearchLoongarchsharedIocsrrdH(a: Int): Int

public actual fun iocsrrdH(a: Int): Int =
    wasiCorearchLoongarchsharedIocsrrdH(a).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedIocsrrdW")
private external fun wasiCorearchLoongarchsharedIocsrrdW(a: Int): Int

public actual fun iocsrrdW(a: Int): Int =
    wasiCorearchLoongarchsharedIocsrrdW(a).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedIocsrwrB")
private external fun wasiCorearchLoongarchsharedIocsrwrB(a: Int, b: Int): Unit

public actual fun iocsrwrB(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedIocsrwrB(a, b)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedIocsrwrH")
private external fun wasiCorearchLoongarchsharedIocsrwrH(a: Int, b: Int): Unit

public actual fun iocsrwrH(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedIocsrwrH(a, b)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedIocsrwrW")
private external fun wasiCorearchLoongarchsharedIocsrwrW(a: Int, b: Int): Unit

public actual fun iocsrwrW(a: Int, b: Int): Unit =
    wasiCorearchLoongarchsharedIocsrwrW(a, b)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedBreakTarget")
private external fun wasiCorearchLoongarchsharedBreakTarget(a: Int): Unit

public actual fun breakTarget(a: Int): Unit =
    wasiCorearchLoongarchsharedBreakTarget(a)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedCpucfg")
private external fun wasiCorearchLoongarchsharedCpucfg(a: Int): Int

public actual fun cpucfg(a: Int): Int =
    wasiCorearchLoongarchsharedCpucfg(a).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedSyscall")
private external fun wasiCorearchLoongarchsharedSyscall(a: Int): Unit

public actual fun syscall(a: Int): Unit =
    wasiCorearchLoongarchsharedSyscall(a)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedFrecipeS")
private external fun wasiCorearchLoongarchsharedFrecipeS(a: Float): Float

public actual fun frecipeS(a: Float): Float =
    wasiCorearchLoongarchsharedFrecipeS(a).toFloat()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedFrecipeD")
private external fun wasiCorearchLoongarchsharedFrecipeD(a: Double): Double

public actual fun frecipeD(a: Double): Double =
    wasiCorearchLoongarchsharedFrecipeD(a).toDouble()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedFrsqrteS")
private external fun wasiCorearchLoongarchsharedFrsqrteS(a: Float): Float

public actual fun frsqrteS(a: Float): Float =
    wasiCorearchLoongarchsharedFrsqrteS(a).toFloat()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarchsharedFrsqrteD")
private external fun wasiCorearchLoongarchsharedFrsqrteD(a: Double): Double

public actual fun frsqrteD(a: Double): Double =
    wasiCorearchLoongarchsharedFrsqrteD(a).toDouble()

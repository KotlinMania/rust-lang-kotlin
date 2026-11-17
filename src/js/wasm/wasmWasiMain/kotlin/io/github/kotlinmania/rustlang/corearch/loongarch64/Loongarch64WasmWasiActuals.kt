@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCrcWBW")
private external fun wasiCorearchLoongarch64ExternCrcWBW(a: Int, b: Int): Int

public actual fun externCrcWBW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64ExternCrcWBW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCrcWHW")
private external fun wasiCorearchLoongarch64ExternCrcWHW(a: Int, b: Int): Int

public actual fun externCrcWHW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64ExternCrcWHW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCrcWWW")
private external fun wasiCorearchLoongarch64ExternCrcWWW(a: Int, b: Int): Int

public actual fun externCrcWWW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64ExternCrcWWW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCrcWDW")
private external fun wasiCorearchLoongarch64ExternCrcWDW(a: Long, b: Int): Int

public actual fun externCrcWDW(a: Long, b: Int): Int =
    wasiCorearchLoongarch64ExternCrcWDW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCrccWBW")
private external fun wasiCorearchLoongarch64ExternCrccWBW(a: Int, b: Int): Int

public actual fun externCrccWBW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64ExternCrccWBW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCrccWHW")
private external fun wasiCorearchLoongarch64ExternCrccWHW(a: Int, b: Int): Int

public actual fun externCrccWHW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64ExternCrccWHW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCrccWWW")
private external fun wasiCorearchLoongarch64ExternCrccWWW(a: Int, b: Int): Int

public actual fun externCrccWWW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64ExternCrccWWW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCrccWDW")
private external fun wasiCorearchLoongarch64ExternCrccWDW(a: Long, b: Int): Int

public actual fun externCrccWDW(a: Long, b: Int): Int =
    wasiCorearchLoongarch64ExternCrccWDW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCacop")
private external fun wasiCorearchLoongarch64ExternCacop(a: Long, b: Long, c: Long): Unit

public actual fun externCacop(a: Long, b: Long, c: Long): Unit =
    wasiCorearchLoongarch64ExternCacop(a, b, c)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCsrrd")
private external fun wasiCorearchLoongarch64ExternCsrrd(a: Int): Long

public actual fun externCsrrd(a: Int): Long =
    wasiCorearchLoongarch64ExternCsrrd(a).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCsrwr")
private external fun wasiCorearchLoongarch64ExternCsrwr(a: Long, b: Int): Long

public actual fun externCsrwr(a: Long, b: Int): Long =
    wasiCorearchLoongarch64ExternCsrwr(a, b).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternCsrxchg")
private external fun wasiCorearchLoongarch64ExternCsrxchg(a: Long, b: Long, c: Int): Long

public actual fun externCsrxchg(a: Long, b: Long, c: Int): Long =
    wasiCorearchLoongarch64ExternCsrxchg(a, b, c).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternIocsrrdD")
private external fun wasiCorearchLoongarch64ExternIocsrrdD(a: Int): Long

public actual fun externIocsrrdD(a: Int): Long =
    wasiCorearchLoongarch64ExternIocsrrdD(a).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternIocsrwrD")
private external fun wasiCorearchLoongarch64ExternIocsrwrD(a: Long, b: Int): Unit

public actual fun externIocsrwrD(a: Long, b: Int): Unit =
    wasiCorearchLoongarch64ExternIocsrwrD(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternAsrtle")
private external fun wasiCorearchLoongarch64ExternAsrtle(a: Long, b: Long): Unit

public actual fun externAsrtle(a: Long, b: Long): Unit =
    wasiCorearchLoongarch64ExternAsrtle(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternAsrtgt")
private external fun wasiCorearchLoongarch64ExternAsrtgt(a: Long, b: Long): Unit

public actual fun externAsrtgt(a: Long, b: Long): Unit =
    wasiCorearchLoongarch64ExternAsrtgt(a, b)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternLddir")
private external fun wasiCorearchLoongarch64ExternLddir(a: Long, b: Long): Long

public actual fun externLddir(a: Long, b: Long): Long =
    wasiCorearchLoongarch64ExternLddir(a, b).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64ExternLdpte")
private external fun wasiCorearchLoongarch64ExternLdpte(a: Long, b: Long): Unit

public actual fun externLdpte(a: Long, b: Long): Unit =
    wasiCorearchLoongarch64ExternLdpte(a, b)

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

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64Cacop")
private external fun wasiCorearchLoongarch64Cacop(a: Long, b: Long, c: Long): Unit

public actual fun cacop(a: Long, b: Long, c: Long): Unit =
    wasiCorearchLoongarch64Cacop(a, b, c)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64Csrrd")
private external fun wasiCorearchLoongarch64Csrrd(a: Int): Long

public actual fun csrrd(a: Int): Long =
    wasiCorearchLoongarch64Csrrd(a).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64Csrwr")
private external fun wasiCorearchLoongarch64Csrwr(a: Long, b: Int): Long

public actual fun csrwr(a: Long, b: Int): Long =
    wasiCorearchLoongarch64Csrwr(a, b).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64Csrxchg")
private external fun wasiCorearchLoongarch64Csrxchg(a: Long, b: Long, c: Int): Long

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    wasiCorearchLoongarch64Csrxchg(a, b, c).toLong()

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

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64Lddir")
private external fun wasiCorearchLoongarch64Lddir(a: Long, b: Long): Long

public actual fun lddir(a: Long, b: Long): Long =
    wasiCorearchLoongarch64Lddir(a, b).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch64Ldpte")
private external fun wasiCorearchLoongarch64Ldpte(a: Long, b: Long): Unit

public actual fun ldpte(a: Long, b: Long): Unit =
    wasiCorearchLoongarch64Ldpte(a, b)

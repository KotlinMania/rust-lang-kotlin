@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.libc.fuchsia.UintptrT
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_backtrace !== 'undefined') return globalThis.__km_napi_backtrace !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_backtrace = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_backtrace = null; return false; } try { globalThis.__km_napi_backtrace = req('@kotlinmania/backtrace-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['backtrace_native.node', 'build/node-ffi/backtrace_native.node', 'build/node-ffi/release/backtrace_native.node', 'build/node-ffi/debug/backtrace_native.node', 'src/js/node-backtrace/build/Release/backtrace_native.node', 'src/js/node-backtrace/build/Debug/backtrace_native.node', 'src/js/node-backtrace/dist/build/Release/backtrace_native.node', 'src/js/node-backtrace/dist/build/Debug/backtrace_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_backtrace = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_backtrace = null; return false; }")
private external fun initBacktraceLibunwindNapiAddon(): Boolean

private val backtraceLibunwindNapiAvailable: Boolean = initBacktraceLibunwindNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_backtrace.backtraceBacktraceLibunwindUnwindGetIp(p0)")
private external fun wasmBacktraceBacktraceLibunwindUnwindGetIp(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_backtrace.backtraceBacktraceLibunwindUnwindFindEnclosingFunction(p0)")
private external fun wasmBacktraceBacktraceLibunwindUnwindFindEnclosingFunction(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_backtrace.backtraceBacktraceLibunwindGetSp(p0)")
private external fun wasmBacktraceBacktraceLibunwindGetSp(p0: String): String

public actual fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetIp(ctx: MutPtr): UintptrT =
    if (backtraceLibunwindNapiAvailable) wasmBacktraceBacktraceLibunwindUnwindGetIp(ctx.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr =
    if (backtraceLibunwindNapiAvailable) MutPtr(wasmBacktraceBacktraceLibunwindUnwindFindEnclosingFunction(pc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSp(ctx: MutPtr): UintptrT =
    if (backtraceLibunwindNapiAvailable) wasmBacktraceBacktraceLibunwindGetSp(ctx.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

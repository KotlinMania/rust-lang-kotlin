@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.libc.fuchsia.UintptrT
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val backtraceLibunwindNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_backtrace !== 'undefined') return globalThis.__km_napi_backtrace; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/backtrace-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_backtrace = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['backtrace_native.node', 'build/node-ffi/backtrace_native.node', 'build/node-ffi/release/backtrace_native.node', 'build/node-ffi/debug/backtrace_native.node', 'src/js/node-backtrace/build/Release/backtrace_native.node', 'src/js/node-backtrace/build/Debug/backtrace_native.node', 'src/js/node-backtrace/dist/build/Release/backtrace_native.node', 'src/js/node-backtrace/dist/build/Debug/backtrace_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_backtrace = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_backtrace = null; return null; })()")

public actual fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindGetIp(ctx: MutPtr): UintptrT =
    if (backtraceLibunwindNapiAddon != null) (backtraceLibunwindNapiAddon.backtraceBacktraceLibunwindUnwindGetIp(ctx.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr =
    if (backtraceLibunwindNapiAddon != null) MutPtr((backtraceLibunwindNapiAddon.backtraceBacktraceLibunwindUnwindFindEnclosingFunction(pc.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSp(ctx: MutPtr): UintptrT =
    if (backtraceLibunwindNapiAddon != null) (backtraceLibunwindNapiAddon.backtraceBacktraceLibunwindGetSp(ctx.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

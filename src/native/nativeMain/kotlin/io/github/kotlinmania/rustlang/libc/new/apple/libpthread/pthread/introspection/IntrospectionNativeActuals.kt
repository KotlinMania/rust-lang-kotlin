@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.introspection

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.introspection.cinterop.rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionGetspecificNp
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.introspection.cinterop.rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionSetspecificNp
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.pthread.pthreadtypes.PthreadKeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun pthreadIntrospectionHookInstall(hook: PthreadIntrospectionHookT): PthreadIntrospectionHookT =
    PthreadIntrospectionHookT { _, _, _, _ -> }

public actual fun pthreadIntrospectionSetspecificNp(thread: PthreadT, key: PthreadKeyT, value: ConstPtr): CInt =
    rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionSetspecificNp(thread.toLong().toCPointer<ByteVar>(), key, value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadIntrospectionGetspecificNp(thread: PthreadT, key: PthreadKeyT): MutPtr =
    MutPtr(rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionGetspecificNp(thread.toLong().toCPointer<ByteVar>(), key)?.toLong() ?: 0L)

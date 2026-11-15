@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.introspection

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.pthread.pthreadtypes.PthreadKeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

private object LibcNewAppleLibpthreadPthreadIntrospectionJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionHookInstall(hook: Long): Long

    @JvmStatic
    external fun rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionSetspecificNp(thread: Long, key: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionGetspecificNp(thread: Long, key: Long): Long
}

public actual fun pthreadIntrospectionHookInstall(hook: PthreadIntrospectionHookT): PthreadIntrospectionHookT =
    PthreadIntrospectionHookT { _, _, _, _ -> }

public actual fun pthreadIntrospectionSetspecificNp(thread: PthreadT, key: PthreadKeyT, value: ConstPtr): CInt =
    LibcNewAppleLibpthreadPthreadIntrospectionJniBridge.rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionSetspecificNp(thread.toLong(), key.toLong(), value.rawAddress)

public actual fun pthreadIntrospectionGetspecificNp(thread: PthreadT, key: PthreadKeyT): MutPtr =
    MutPtr(LibcNewAppleLibpthreadPthreadIntrospectionJniBridge.rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionGetspecificNp(thread.toLong(), key.toLong()))

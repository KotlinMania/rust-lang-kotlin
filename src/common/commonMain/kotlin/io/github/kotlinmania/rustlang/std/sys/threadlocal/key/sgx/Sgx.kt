// port-lint: source sys/thread_local/key/sgx.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.sgx

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.sgx.Key as AbiKey

public fun interface CreateDtorCallback {
    public operator fun invoke(arg0: MutPtr)
}

public typealias Key = ULong

public fun create(dtor: CreateDtorCallback?): Key = Tls.create(dtor).asUsize()

public fun set(key: Key, value: MutPtr) {
    Tls.set(AbiKey.fromUsize(key), value)
}

public fun get(key: Key): MutPtr = Tls.get(AbiKey.fromUsize(key))

public fun destroy(key: Key) {
    Tls.destroy(AbiKey.fromUsize(key))
}

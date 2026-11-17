// port-lint: tests sys/thread_local/key/tests.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.tests

import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.withoutProvenanceMut
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.windows.KEY
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.racy.LazyKey
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.get
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.set
import io.github.kotlinmania.rustlang.std.thread.functions.spawn

public class TestsInlineTests {
    @kotlin.test.Test
    public fun smoke() {
        val K1: LazyKey  = LazyKey.new(null)
        val K2: LazyKey  = LazyKey.new(null)
        val k1 = K1.force()
        val k2 = K2.force()
        check(k1 != k2)
        check(K1.force() == k1)
        check(K2.force() == k2)
        run {
            check(((get(k1)).rawAddress == 0L))
            check(((get(k2)).rawAddress == 0L))
            set(k1, ptr.withoutProvenanceMut(1))
            set(k2, ptr.withoutProvenanceMut(2))
            check((get(k1)).rawAddress.toInt() == 1)
            check((get(k2)).rawAddress.toInt() == 2)
        }
    }

    @kotlin.test.Test
    public fun destructors() {
        fun destruct(ptr: MutPtr) {
            (Arc.fromRaw(ConstPtr(ptr.rawAddress, ptr.rawMeta))).let { }
        }
        val KEY: LazyKey  = LazyKey.new(::destruct)
        val shared1 = Arc.new(Unit)
        val shared2 = shared1
        val key = KEY.force()
        run {
            check(((get(key)).rawAddress == 0L))
            set(key, mutPtrFromRustCast(Arc.intoRaw(shared1)))
        }
        spawn({ run {
            val key1 = KEY.force()
            check(((get(key1)).rawAddress == 0L))
            set(key1, mutPtrFromRustCast(Arc.intoRaw(shared2)))
        } }).join().unwrap()
        // Leak the Arc, let the TLS destructor clean it up.
        val shared11 = Arc.fromRaw((get(key)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
        check(Arc.strongCount(shared11) == 1)
    }
}

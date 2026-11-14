// port-lint: source new/helenos/fibril_synch.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.ListT
import io.github.kotlinmania.rustlang.libc.helenos.listInitialize
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.fibril.FibrilOwnerInfoT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

// HelenOS fibril synchronization primitives
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/c/include/fibrilSynch.h>

public data class FibrilMutexT(
    public var oi: FibrilOwnerInfoT = FibrilOwnerInfoT(),
    public var counter: CInt = 0,
    public var waiters: ListT = ListT(),
)

public data class FibrilRwlockT(
    public var oi: FibrilOwnerInfoT = FibrilOwnerInfoT(),
    public var writers: CUint = 0u,
    public var readers: CUint = 0u,
    public var waiters: ListT = ListT(),
)

public data class FibrilCondvarT(
    public var waiters: ListT = ListT(),
)

public fun fibrilMutexInitialize(fm: FibrilMutexT) {
    fm.oi.ownedBy = MutPtr(0L)
    fm.counter = 1
    listInitialize(fm.waiters)
}

public expect fun fibrilMutexLock(mutex: MutPtr)

public expect fun fibrilMutexUnlock(mutex: MutPtr)

public expect fun fibrilMutexTrylock(mutex: MutPtr): Boolean

public expect fun fibrilMutexIsLocked(mutex: MutPtr): Boolean

public expect fun fibrilRwlockInitialize(rwlock: MutPtr)

public expect fun fibrilRwlockReadLock(rwlock: MutPtr)

public expect fun fibrilRwlockWriteLock(rwlock: MutPtr)

public expect fun fibrilRwlockReadUnlock(rwlock: MutPtr)

public expect fun fibrilRwlockWriteUnlock(rwlock: MutPtr)

public expect fun fibrilRwlockIsReadLocked(rwlock: MutPtr): Boolean

public expect fun fibrilRwlockIsWriteLocked(rwlock: MutPtr): Boolean

public expect fun fibrilRwlockIsLocked(rwlock: MutPtr): Boolean

public expect fun fibrilCondvarInitialize(condvar: MutPtr)

public expect fun fibrilCondvarWait(condvar: MutPtr, mutex: MutPtr)

public expect fun fibrilCondvarWaitTimeout(condvar: MutPtr, mutex: MutPtr, timeout: UsecT): ErrnoT

public expect fun fibrilCondvarSignal(condvar: MutPtr)

public expect fun fibrilCondvarBroadcast(condvar: MutPtr)

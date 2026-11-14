// port-lint: source wasm32/atomic.rs
package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public expect fun llvmAtomicWaitI32(ptr: MutPtr, exp: Int, timeout: Long): Int

public expect fun llvmAtomicWaitI64(ptr: MutPtr, exp: Long, timeout: Long): Int

public expect fun llvmAtomicNotify(ptr: MutPtr, cnt: Int): Int

/**
 * Corresponding intrinsic to wasm's [`memory.atomic.wait32` instruction][instr]
 *
 * This function, when called, will block the current thread if the memory
 * pointed to by `ptr` is equal to `expression` (performing this action
 * atomically).
 *
 * The argument `timeoutNs` is a maximum number of nanoseconds the calling
 * thread will be blocked for, if it blocks. If the timeout is negative then
 * the calling thread will be blocked forever.
 *
 * The calling thread can only be woken up with a call to the `wake` intrinsic
 * once it has been blocked. Changing the memory behind `ptr` will not wake
 * the thread once it's blocked.
 *
 * # Return value
 *
 * * 0 - indicates that the thread blocked and then was woken up
 * * 1 - the loaded value from `ptr` didn't match `expression`, the thread
 *   didn't block
 * * 2 - the thread blocked, but the timeout expired.
 *
 * [instr]: https://webassembly.github.io/threads/core/syntax/instructions.html#syntax-instr-atomic-memory
 */
public fun memoryAtomicWait32(ptr: MutPtr, expression: Int, timeoutNs: Long): Int = llvmAtomicWaitI32(ptr, expression, timeoutNs)

/**
 * Corresponding intrinsic to wasm's [`memory.atomic.wait64` instruction][instr]
 *
 * This function, when called, will block the current thread if the memory
 * pointed to by `ptr` is equal to `expression` (performing this action
 * atomically).
 *
 * The argument `timeoutNs` is a maximum number of nanoseconds the calling
 * thread will be blocked for, if it blocks. If the timeout is negative then
 * the calling thread will be blocked forever.
 *
 * The calling thread can only be woken up with a call to the `wake` intrinsic
 * once it has been blocked. Changing the memory behind `ptr` will not wake
 * the thread once it's blocked.
 *
 * # Return value
 *
 * * 0 - indicates that the thread blocked and then was woken up
 * * 1 - the loaded value from `ptr` didn't match `expression`, the thread
 *   didn't block
 * * 2 - the thread blocked, but the timeout expired.
 *
 * [instr]: https://webassembly.github.io/threads/core/syntax/instructions.html#syntax-instr-atomic-memory
 */
public fun memoryAtomicWait64(ptr: MutPtr, expression: Long, timeoutNs: Long): Int = llvmAtomicWaitI64(ptr, expression, timeoutNs)

/**
 * Corresponding intrinsic to wasm's [`memory.atomic.notify` instruction][instr]
 *
 * This function will notify a number of threads blocked on the address
 * indicated by `ptr`. Threads previously blocked with the `i32AtomicWait`
 * and `i64AtomicWait` functions above will be woken up.
 *
 * The `waiters` argument indicates how many waiters should be woken up (a
 * maximum). If the value is zero no waiters are woken up.
 *
 * # Return value
 *
 * Returns the number of waiters which were actually notified.
 *
 * [instr]: https://webassembly.github.io/threads/core/syntax/instructions.html#syntax-instr-atomic-memory
 */
public fun memoryAtomicNotify(ptr: MutPtr, waiters: UInt): UInt = llvmAtomicNotify(ptr, waiters.toInt()).toUInt()

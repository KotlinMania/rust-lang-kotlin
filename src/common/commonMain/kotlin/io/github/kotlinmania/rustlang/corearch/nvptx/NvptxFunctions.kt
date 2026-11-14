package io.github.kotlinmania.rustlang.corearch.nvptx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.Intrinsics

public expect fun syncthreads(a: UInt)

public expect fun blockDimX(): UInt

public expect fun blockDimY(): UInt

public expect fun blockDimZ(): UInt

public expect fun blockIdxX(): UInt

public expect fun blockIdxY(): UInt

public expect fun blockIdxZ(): UInt

public expect fun gridDimX(): UInt

public expect fun gridDimY(): UInt

public expect fun gridDimZ(): UInt

public expect fun threadIdxX(): UInt

public expect fun threadIdxY(): UInt

public expect fun threadIdxZ(): UInt

/**
 * Synchronizes all threads in the block.
 */
public fun syncthreads() {
    syncthreads(0u)
}

/**
 * x-th thread-block dimension.
 */
public fun blockDimX(): UInt = blockDimX()

/**
 * y-th thread-block dimension.
 */
public fun blockDimY(): UInt = blockDimY()

/**
 * z-th thread-block dimension.
 */
public fun blockDimZ(): UInt = blockDimZ()

/**
 * x-th thread-block index.
 */
public fun blockIdxX(): UInt = blockIdxX()

/**
 * y-th thread-block index.
 */
public fun blockIdxY(): UInt = blockIdxY()

/**
 * z-th thread-block index.
 */
public fun blockIdxZ(): UInt = blockIdxZ()

/**
 * x-th block-grid dimension.
 */
public fun gridDimX(): UInt = gridDimX()

/**
 * y-th block-grid dimension.
 */
public fun gridDimY(): UInt = gridDimY()

/**
 * z-th block-grid dimension.
 */
public fun gridDimZ(): UInt = gridDimZ()

/**
 * x-th thread index.
 */
public fun threadIdxX(): UInt = threadIdxX()

/**
 * y-th thread index.
 */
public fun threadIdxY(): UInt = threadIdxY()

/**
 * z-th thread index.
 */
public fun threadIdxZ(): UInt = threadIdxZ()

/**
 * Generates the trap instruction `TRAP`
 */
public fun trap(): Nothing = Intrinsics.abortImmediate()

// Basic CUDA syscall declarations.

/**
 * Print formatted output from a kernel to a host-side output stream.
 *
 * Syscall arguments:
 * * `status`: The status value that is returned by `vprintf`.
 * * `format`: A pointer to the format specifier input (uses common `printf` format).
 * * `valist`: A pointer to the valist input.
 *
 * ```ignore (available only for nvptx)
 * # use std.mem.transmute;
 * @repr(C)
 * struct PrintArgs(f32, f32, f32, i32);
 *
 * let a = 0.1f32;
 * let b = 0.2f32;
 * vprintf(
 *     "int(%f + %f) = int(%f) = %d\n".asPtr(),
 *     reinterpret(&PrintArgs(a, b, a + b, (a + b) as i32)),
 * );
 * ```
 *
 * Sources:
 * [Programming Guide](https://docs.nvidia.com/cuda/cuda-c-programming-guide/index.html#formatted-output),
 * [PTX Interoperability](https://docs.nvidia.com/cuda/ptx-writers-guide-to-interoperability/index.html#system-calls).
 */
public expect fun vprintf(format: ConstPtr, valist: ConstPtr): Int

// Note(denzp): assign `malloc` and `nothrow` attributes.

/**
 * Allocate memory dynamically from a fixed-size heap in global memory.
 *
 * The CUDA in-kernel `malloc()` function allocates at least `size` bytes
 * from the device heap and returns a pointer to the allocated memory
 * or `NULL` if insufficient memory exists to fulfill the request.
 *
 * The returned pointer is guaranteed to be aligned to a 16-byte boundary.
 *
 * The memory allocated by a given CUDA thread via `malloc()` remains allocated
 * for the scope of the CUDA context, or until it is explicitly released
 * by a call to `free()`. It can be used by any other CUDA threads
 * even from subsequent kernel launches.
 *
 * Sources:
 * [Programming Guide](https://docs.nvidia.com/cuda/cuda-c-programming-guide/index.html#dynamic-global-memory-allocation-and-operations),
 * [PTX Interoperability](https://docs.nvidia.com/cuda/ptx-writers-guide-to-interoperability/index.html#system-calls).
 */
public expect fun malloc(size: ULong): MutPtr

// Note(denzp): assign `nothrow` attribute.

/**
 * Free previously dynamically allocated memory.
 *
 * The CUDA in-kernel `free()` function deallocates the memory pointed to by `ptr`,
 * which must have been returned by a previous call to `malloc()`. If `ptr` is NULL,
 * the call to `free()` is ignored.
 *
 * Any CUDA thread may free memory allocated by another thread, but care should be taken
 * to ensure that the same pointer is not freed more than once. Repeated calls to `free()`
 * with the same `ptr` has undefined behavior.
 *
 * Sources:
 * [Programming Guide](https://docs.nvidia.com/cuda/cuda-c-programming-guide/index.html#dynamic-global-memory-allocation-and-operations),
 * [PTX Interoperability](https://docs.nvidia.com/cuda/ptx-writers-guide-to-interoperability/index.html#system-calls).
 */
public expect fun free(ptr: MutPtr)

// Internal declaration of the syscall. Exported variant has
// the `charSize` parameter set to `1` (single char size in bytes).
public expect fun assertfail(message: ConstPtr, file: ConstPtr, line: UInt, function: ConstPtr, charSize: ULong)

/**
 * Syscall to be used whenever the *assert expression produces a `false` value*.
 *
 * Syscall arguments:
 * * `message`: The pointer to the string that should be output.
 * * `file`: The pointer to the file name string associated with the assert.
 * * `line`: The line number associated with the assert.
 * * `function`: The pointer to the function name string associated with the assert.
 *
 * Source:
 * [PTX Interoperability](https://docs.nvidia.com/cuda/ptx-writers-guide-to-interoperability/index.html#system-calls).
 */
public fun assertFail(message: ConstPtr, file: ConstPtr, line: UInt, function: ConstPtr) {
    assertfail(message, file, line, function, 1uL)
}

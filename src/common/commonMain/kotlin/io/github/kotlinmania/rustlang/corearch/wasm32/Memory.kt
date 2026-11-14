// port-lint: source wasm32/memory.rs
package io.github.kotlinmania.rustlang.corearch.wasm32

public expect fun llvmMemoryGrow(mem: UInt, pages: ULong): ULong

public expect fun llvmMemorySize(mem: UInt): ULong

/**
 * Corresponding intrinsic to wasm's [`memory.size` instruction][instr]
 *
 * This function, when called, will return the current memory size in units of
 * pages. The current WebAssembly page size is 65536 bytes (64 KB).
 *
 * The argument `MEM` is the numerical index of which memory to return the
 * size of. Note that currently the WebAssembly specification only supports one
 * memory, so it is required that zero is passed in. The argument is present to
 * be forward-compatible with future WebAssembly revisions. If a nonzero
 * argument is passed to this function it will currently unconditionally abort.
 *
 * [instr]: http://webassembly.github.io/spec/core/exec/instructions.html#exec-memory-size
 */
public fun memorySize(mem: UInt): Int {
    run {
        check(mem == 0u)
    }
    return (llvmMemorySize(mem)).toInt()
}

/**
 * Corresponding intrinsic to wasm's [`memory.grow` instruction][instr]
 *
 * This function, when called, will attempt to grow the default linear memory
 * by the specified `delta` of pages. The current WebAssembly page size is
 * 65536 bytes (64 KB). If memory is successfully grown then the previous size
 * of memory, in pages, is returned. If memory cannot be grown then
 * `Int.MAX` is returned.
 *
 * The argument `MEM` is the numerical index of which memory to return the
 * size of. Note that currently the WebAssembly specification only supports one
 * memory, so it is required that zero is passed in. The argument is present to
 * be forward-compatible with future WebAssembly revisions. If a nonzero
 * argument is passed to this function it will currently unconditionally abort.
 *
 * [instr]: http://webassembly.github.io/spec/core/exec/instructions.html#exec-memory-grow
 */
public fun memoryGrow(delta: Int, mem: UInt): Int =
    (run {
        run {
            check(mem == 0u)
        }
        llvmMemoryGrow(mem, delta.toULong())
    }).toInt()

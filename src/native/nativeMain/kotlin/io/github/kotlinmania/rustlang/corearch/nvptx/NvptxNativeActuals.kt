@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.nvptx

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxAssertfail
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxBlockDimX
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxBlockDimY
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxBlockDimZ
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxBlockIdxX
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxBlockIdxY
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxBlockIdxZ
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxFree
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxGridDimX
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxGridDimY
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxGridDimZ
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxMalloc
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxSyncthreads
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxThreadIdxX
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxThreadIdxY
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxThreadIdxZ
import io.github.kotlinmania.rustlang.corearch.nvptx.cinterop.corearchNvptxVprintf
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun syncthreads(a: UInt) {
    corearchNvptxSyncthreads(a)
}

public actual fun blockDimX(): UInt =
    corearchNvptxBlockDimX()

public actual fun blockDimY(): UInt =
    corearchNvptxBlockDimY()

public actual fun blockDimZ(): UInt =
    corearchNvptxBlockDimZ()

public actual fun blockIdxX(): UInt =
    corearchNvptxBlockIdxX()

public actual fun blockIdxY(): UInt =
    corearchNvptxBlockIdxY()

public actual fun blockIdxZ(): UInt =
    corearchNvptxBlockIdxZ()

public actual fun gridDimX(): UInt =
    corearchNvptxGridDimX()

public actual fun gridDimY(): UInt =
    corearchNvptxGridDimY()

public actual fun gridDimZ(): UInt =
    corearchNvptxGridDimZ()

public actual fun threadIdxX(): UInt =
    corearchNvptxThreadIdxX()

public actual fun threadIdxY(): UInt =
    corearchNvptxThreadIdxY()

public actual fun threadIdxZ(): UInt =
    corearchNvptxThreadIdxZ()

public actual fun vprintf(format: ConstPtr, valist: ConstPtr): Int =
    corearchNvptxVprintf(format.rawAddress.toCPointer<ByteVar>(), valist.rawAddress.toCPointer<ByteVar>())

public actual fun malloc(size: ULong): MutPtr =
    MutPtr(corearchNvptxMalloc(size)?.toLong() ?: 0L)

public actual fun free(ptr: MutPtr) {
    corearchNvptxFree(ptr.rawAddress.toCPointer<ByteVar>())
}

public actual fun assertfail(message: ConstPtr, file: ConstPtr, line: UInt, function: ConstPtr, charSize: ULong) {
    corearchNvptxAssertfail(message.rawAddress.toCPointer<ByteVar>(), file.rawAddress.toCPointer<ByteVar>(), line, function.rawAddress.toCPointer<ByteVar>(), charSize)
}

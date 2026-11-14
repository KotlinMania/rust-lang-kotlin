package io.github.kotlinmania.rustlang.alloc.collections.binaryheap

import io.github.kotlinmania.rustlang.core.alloc.Allocator

/**
 * Converts a `BinaryHeap<T>` into a `List<T>`.
 *
 * This conversion requires no data movement or allocation, and has
 * constant time complexity.
 */
public fun <T, A : Allocator> fromVecTA(heap: BinaryHeap<T, A>): List<T> = heap.data

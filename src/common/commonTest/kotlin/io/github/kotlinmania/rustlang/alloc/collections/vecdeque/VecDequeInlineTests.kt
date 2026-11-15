package io.github.kotlinmania.rustlang.alloc.collections.vecdeque

// A double-ended queue (deque) implemented with a growable ring buffer.
//
// This queue has *O*(1) amortized inserts and removals from both ends of the
// container. It also has *O*(1) indexing like a vector. The contained elements
// are not required to be copyable, and the queue will be sendable if the
// contained type is sendable.

package io.github.kotlinmania.rustlang.core.iter.adapters

/**
 * This trait provides transitive access to source-stage in an iterator-adapter pipeline
 * under the conditions that
 * * the iterator source `S` itself implements `SourceIter<Source = S>`
 * * there is a delegating implementation of this trait for each adapter in the pipeline between
 *   the source and the pipeline consumer.
 *
 * When the source is an owning iterator struct (commonly called `IntoIter`) then
 * this can be useful for specializing [FromIterator] implementations or recovering the
 * remaining elements after an iterator has been partially exhausted.
 *
 * Note that implementations do not necessarily have to provide access to the innermost
 * source of a pipeline. A stateful intermediate adapter might eagerly evaluate a part
 * of the pipeline and expose its internal storage as source.
 *
 * The trait is unchecked because implementers must uphold additional safety properties.
 * See [asInner] for details.
 *
 * The primary use of this trait is in-place iteration. Refer to the [vec.inPlaceCollect]
 * module documentation for more information.
 *
 * [vec.inPlaceCollect]: ../../../../alloc/vec/inPlaceCollect/index.html
 *
 * # Examples
 *
 * Retrieving a partially consumed source:
 *
 * ```kotlin
 * var iter = mutableListOf(9, 9, 9).intoIter().map({ i -> i * i })
 * iter.next()
 * var remainder = replace(iter.asInner(), ArrayDeque((mutableListOf()).toList()))
 * println("n = ${remainder.len()} elements remaining")
 * ```
 *
 * [asInner]: SourceIter.asInner
 */
public interface SourceIter<Source> {
    /**
     * Retrieve the source of an iterator pipeline.
     *
     * # Safety
     *
     * Implementations must return the same mutable reference for their scope, unless
     * replaced by a caller.
     *
     * Callers may only replace the reference when they stopped iteration and drop the
     * iterator pipeline after extracting the source.
     *
     * This means iterator adapters can rely on the source not changing during
     * iteration but they cannot rely on it in their Drop implementations.
     *
     * Implementing this method means adapters relinquish private-only access to their
     * source and can only rely on guarantees made based on method receiver types.
     * The lack of restricted access also requires that adapters must uphold the source's
     * public API even when they have access to its internals.
     *
     * Callers in turn must expect the source to be in any state that is consistent with
     * its public API since adapters sitting between it and the source have the same
     * access. In particular an adapter may have consumed more elements than strictly necessary.
     *
     * The overall goal of these requirements is to let the consumer of a pipeline use
     * * whatever remains in the source after iteration has stopped
     * * the memory that has become unused by advancing a consuming iterator
     *
     * [next]: Iterator.next()
     */
    public fun asInner(): Source
}

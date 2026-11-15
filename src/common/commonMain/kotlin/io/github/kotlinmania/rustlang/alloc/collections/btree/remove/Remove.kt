// port-lint: source collections/btree/remove.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.remove

import io.github.kotlinmania.rustlang.alloc.collections.btree.map.MIN_LEN
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.ForceResult.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Handle
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.LeftOrRight.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.NodeRef
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.Edge
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.Internal
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.KV
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.Leaf
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.LeafOrInternal
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.Mut
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Removes a key-value pair from the tree, and returns that pair, as well as
 * the leaf edge corresponding to that former pair. It's possible this empties
 * a root node that is internal, which the caller should pop from the map
 * holding the tree. The caller should also decrement the map's length.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, LeafOrInternal>, KV>.removeKvTracking(handleEmptiedInternalRoot: () -> Unit, alloc: A): Pair<Pair<K, V>, Handle<NodeRef<Mut, K, V, Leaf>, Edge>> =
    when (val matchVal = this.force()) {
        is io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf -> {
            val (node) = matchVal
            node.removeLeafKv(handleEmptiedInternalRoot, alloc)
        }
        is io.github.kotlinmania.rustlang.alloc.collections.btree.node.Internal -> {
            val (node1) = matchVal
            node1.removeInternalKv(handleEmptiedInternalRoot, alloc)
        }
    }

public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, Leaf>, KV>.removeLeafKv(handleEmptiedInternalRoot: () -> Unit, alloc: A): Pair<Pair<K, V>, Handle<NodeRef<Mut, K, V, Leaf>, Edge>> {
    var (oldKv, pos) = this.remove()
    val len = pos.reborrow().intoNode().len()
    if (len < MIN_LEN) {
        val idx = pos.idx()
        // We have to temporarily forget the child type, because there is no
        // distinct node type for the immediate parents of a leaf.
        val newPos =
            run {
                val matchVal = pos.intoNode().forgetType().chooseParentKv()
                when {
                    matchVal is Result.Ok<*, *> && matchVal.value is Left -> {
                        check(leftParentKv.rightChildLen() == MIN_LEN - 1)
                        if (leftParentKv.canMerge()) {
                            leftParentKv.mergeTrackingChildEdge(Right(idx), alloc.clone())
                        } else {
                            check(leftParentKv.leftChildLen() > MIN_LEN)
                            leftParentKv.stealLeft(idx)
                        }
                    }
                    matchVal is Result.Ok<*, *> && matchVal.value is Right -> {
                        check(rightParentKv.leftChildLen() == MIN_LEN - 1)
                        if (rightParentKv.canMerge()) {
                            rightParentKv.mergeTrackingChildEdge(Left(idx), alloc.clone())
                        } else {
                            check(rightParentKv.rightChildLen() > MIN_LEN)
                            rightParentKv.stealRight(idx)
                        }
                    }
                    // ignore-tidy-undocumented-unchecked
                    else -> {
                        val pos1 = (matchVal as Result.Err<*, *>).error
                        Handle.newEdge(pos1, idx)
                    }
                }
            }
        // SAFETY: `newPos` is the leaf we started from or a sibling.
        pos = newPos.castToLeafUnchecked()
        // Only if we merged, the parent (if any) has shrunk, but skipping
        // the following step otherwise does not pay off in benchmarks.
        //
        // SAFETY: We won't destroy or rearrange the leaf where `pos` is at
        // by handling its parent recursively; at worst we will destroy or
        // rearrange the parent through the grandparent, thus change the
        // link to the parent inside the leaf.
        when (val matchVal1 = pos.reborrowMut().intoNode().ascend()) {
            is Ok -> {
                val parent = matchVal1.value
                if (!parent.intoNode().forgetType().fixNodeAndAffectedAncestors(alloc)) {
                    handleEmptiedInternalRoot()
                }
            }
            else -> {}
        }
    }
    return Pair(oldKv, pos)
}

public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, Internal>, KV>.removeInternalKv(handleEmptiedInternalRoot: () -> Unit, alloc: A): Pair<Pair<K, V>, Handle<NodeRef<Mut, K, V, Leaf>, Edge>> {
    // Remove an adjacent KV from its leaf and then put it back in place of
    // the element we were asked to remove. Prefer the left adjacent KV,
    // for the reasons listed in `chooseParentKv`.
    val leftLeafKv =
        this
            .leftEdge()
            .descend()
            .lastLeafEdge()
            .leftKv()
    // ignore-tidy-undocumented-unchecked
    val leftLeafKv1 = leftLeafKv.ok().unwrapUnchecked()
    val (leftKv, leftHole) = leftLeafKv1.removeLeafKv(handleEmptiedInternalRoot, alloc)
    // The internal node may have been stolen from or merged. Go back right
    // to find where the original KV ended up.
    // ignore-tidy-undocumented-unchecked
    val internal = leftHole.nextKv().ok().unwrapUnchecked()
    val oldKv = internal.replaceKv(leftKv.first, leftKv.second)
    val pos = internal.nextLeafEdge()
    return Pair(oldKv, pos)
}

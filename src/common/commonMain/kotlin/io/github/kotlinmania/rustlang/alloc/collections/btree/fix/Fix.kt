// port-lint: source collections/btree/fix.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.fix

import io.github.kotlinmania.rustlang.alloc.collections.btree.map.MIN_LEN
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.ForceResult.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Handle
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.LeftOrRight.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.NodeRef
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Root
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.Internal
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.KV
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.LeafOrInternal
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.marker.Mut
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.num.rustSaturatingSub
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Stocks up a possibly underfull node by merging with or stealing from a
 * sibling. If successful but at the cost of shrinking the parent node,
 * returns that shrunk parent node. Returns an `Err` if the node is
 * an empty root.
 */
public fun <K, V, A : AllocatorClone> NodeRef<Mut, K, V, LeafOrInternal>.fixNodeThroughParent(alloc: A): Result<NodeRef<Mut, K, V, Internal>?, NodeRef<Mut, K, V, LeafOrInternal>> {
    val len = this.len()
    return if (len >= MIN_LEN) {
        Result.Ok(null)
    } else {
        run {
            val matchVal = this.chooseParentKv()
            when {
                matchVal is Result.Ok<*> && matchVal.value is Left -> {
                    if (leftParentKv.canMerge()) {
                        val parent = leftParentKv.mergeTrackingParent(alloc)
                        Result.Ok(parent)
                    } else {
                        leftParentKv.bulkStealLeft(MIN_LEN - len)
                        Result.Ok(null)
                    }
                }
                matchVal is Result.Ok<*> && matchVal.value is Right -> {
                    if (rightParentKv.canMerge()) {
                        val parent1 = rightParentKv.mergeTrackingParent(alloc)
                        Result.Ok(parent1)
                    } else {
                        rightParentKv.bulkStealRight(MIN_LEN - len)
                        Result.Ok(null)
                    }
                }
                else -> {
                    val root = (matchVal as Result.Err<*>).error
                    if (len > 0) {
                        Result.Ok(null)
                    } else {
                        Result.Err(root)
                    }
                }
            }
        }
    }
}

/**
 * Stocks up a possibly underfull node, and if that causes its parent node
 * to shrink, stocks up the parent, recursively.
 * Returns `true` if it fixed the tree, `false` if it couldn't because the
 * root node became empty.
 *
 * This method does not expect ancestors to already be underfull upon entry
 * and panics if it encounters an empty ancestor.
 */
internal fun <K, V, A : AllocatorClone> NodeRef<Mut, K, V, LeafOrInternal>.fixNodeAndAffectedAncestors(alloc: A): Boolean {
    while (true) {
        run {
            val matchVal = this.fixNodeThroughParent(alloc.clone())
            when {
                matchVal is Result.Ok<*> && matchVal.value != null -> this = parent.forgetType()
                matchVal is Result.Ok<*> && matchVal.value == null -> {
                    val None = (matchVal as Result.Ok<*>).value
                    return true
                }
                else -> { return false }
            }
        }
    }
}

/**
 * Removes empty levels on the top, but keeps an empty leaf if the entire tree is empty.
 */
internal fun <K, V, A : AllocatorClone> Root<K, V>.fixTop(alloc: A) {
    while (this.height() > 0 && this.len() == 0) {
        this.popInternalLevel(alloc.clone())
    }
}

/**
 * Stocks up or merge away any underfull nodes on the right border of the
 * tree. The other nodes, those that are not the root nor a rightmost edge,
 * must already have at least MIN_LEN elements.
 */
internal fun <K, V, A : AllocatorClone> Root<K, V>.fixRightBorder(alloc: A) {
    this.fixTop(alloc.clone())
    if (this.len() > 0) {
        this.borrowMut().lastKv().fixRightBorderOfRightEdge(alloc.clone())
        this.fixTop(alloc)
    }
}

/**
 * The symmetric clone of `fixRightBorder`.
 */
internal fun <K, V, A : AllocatorClone> Root<K, V>.fixLeftBorder(alloc: A) {
    this.fixTop(alloc.clone())
    if (this.len() > 0) {
        this.borrowMut().firstKv().fixLeftBorderOfLeftEdge(alloc.clone())
        this.fixTop(alloc)
    }
}

/**
 * Stocks up any underfull nodes on the right border of the tree.
 * The other nodes, those that are neither the root nor a rightmost edge,
 * must be prepared to have up to MIN_LEN elements stolen.
 */
internal fun <K, V> Root<K, V>.fixRightBorderOfPlentiful() {
    var curNode = this.borrowMut()
    while (true) {
        val matchVal = curNode.force()
        if (matchVal !is Internal) break
        val (internal) = matchVal as Internal
        // Check if rightmost child is underfull.
        val lastKv = internal.lastKv().considerForBalancing()
        check(lastKv.leftChildLen() >= MIN_LEN * 2)
        val rightChildLen = lastKv.rightChildLen()
        if (rightChildLen < MIN_LEN) {
            // We need to steal.
            lastKv.bulkStealLeft(MIN_LEN - rightChildLen)
        }
        // Go further down.
        curNode = lastKv.intoRightChild()
    }
}

public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, LeafOrInternal>, KV>.fixLeftBorderOfLeftEdge(alloc: A) {
    while (true) {
        val matchVal = this.force()
        if (matchVal !is Internal) break
        val (internalKv) = matchVal as Internal
        this = internalKv.fixLeftChild(alloc.clone()).firstKv()
        check(this.reborrow().intoNode().len() > MIN_LEN)
    }
}

public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, LeafOrInternal>, KV>.fixRightBorderOfRightEdge(alloc: A) {
    while (true) {
        val matchVal = this.force()
        if (matchVal !is Internal) break
        val (internalKv) = matchVal as Internal
        this = internalKv.fixRightChild(alloc.clone()).lastKv()
        check(this.reborrow().intoNode().len() > MIN_LEN)
    }
}

/**
 * Stocks up the left child, assuming the right child isn't underfull, and
 * provisions an extra element to allow merging its children in turn
 * without becoming underfull.
 * Returns the left child.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, Internal>, KV>.fixLeftChild(alloc: A): NodeRef<Mut, K, V, LeafOrInternal> {
    val internalKv = this.considerForBalancing()
    val leftLen = internalKv.leftChildLen()
    check(internalKv.rightChildLen() >= MIN_LEN)
    return if (internalKv.canMerge()) {
        internalKv.mergeTrackingChild(alloc)
    } else {
        // `MIN_LEN + 1` to avoid readjust if merge happens on the next level.
        val count = rustSaturatingSub((MIN_LEN + 1), leftLen.toInt())
        if (count > 0) {
            internalKv.bulkStealRight(count)
        }
        internalKv.intoLeftChild()
    }
}

/**
 * Stocks up the right child, assuming the left child isn't underfull, and
 * provisions an extra element to allow merging its children in turn
 * without becoming underfull.
 * Returns wherever the right child ended up.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, Internal>, KV>.fixRightChild(alloc: A): NodeRef<Mut, K, V, LeafOrInternal> {
    val internalKv = this.considerForBalancing()
    val rightLen = internalKv.rightChildLen()
    check(internalKv.leftChildLen() >= MIN_LEN)
    return if (internalKv.canMerge()) {
        internalKv.mergeTrackingChild(alloc)
    } else {
        // `MIN_LEN + 1` to avoid readjust if merge happens on the next level.
        val count = rustSaturatingSub((MIN_LEN + 1), rightLen.toInt())
        if (count > 0) {
            internalKv.bulkStealLeft(count)
        }
        internalKv.intoRightChild()
    }
}

// port-lint: source collections/btree/split.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.split

import io.github.kotlinmania.rustlang.alloc.collections.btree.node.ForceResult.*
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Internal
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Leaf
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Root
import io.github.kotlinmania.rustlang.alloc.collections.btree.search.SearchResult.*
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort
import io.github.kotlinmania.rustlang.core.mem.dropguard.DropGuard

/**
 * Calculates the length of both trees that result from splitting up
 * a given number of distinct key-value pairs.
 */
public fun <K, V> Root.Companion.calcSplitLength(totalNum: Int, rootA: Root<K, V>, rootB: Root<K, V>): Pair<Int, Int> {
    val (lengthA, lengthB)
    if (rootA.height() < rootB.height()) {
        lengthA = rootA.reborrow().calcLength()
        lengthB = totalNum - lengthA
        check(lengthB == rootB.reborrow().calcLength())
    } else {
        lengthB = rootB.reborrow().calcLength()
        lengthA = totalNum - lengthB
        check(lengthA == rootA.reborrow().calcLength())
    }
    return Pair(lengthA, lengthB)
}

/**
 * Split off a tree with key-value pairs at and after the given key.
 * The result is meaningful only if the tree is ordered by key,
 * and if the ordering of `Q` corresponds to that of `K`.
 * If `self` respects all `BTreeMap` tree invariants, then both
 * `self` and the returned tree will respect those invariants.
 */
public fun <K, V, Q, A : AllocatorClone> Root<K, V>.splitOff(key: Q, alloc: A): Root<K, V> where K : Borrow<Q> {
    val leftRoot = this
    val rightRoot = Root.newPillar(leftRoot.height(), alloc.clone())
    var leftNode = leftRoot.borrowMut()
    var rightNode = rightRoot.borrowMut()
    // The first search runs before anything has moved, so a panic from the
    // caller's `Ord`/`Borrow` impl here can unwind safely: `self` is
    // untouched and the new right tree is still empty.
    var splitEdge =
        when (val matchVal = leftNode.searchNode(key)) {
            // key is going to the right tree
            is Found<*, *, *, *, *> -> {
                val (kv) = matchVal
                kv.leftEdge()
            }
            is GoDown<*, *, *, *, *> -> {
                val (edge) = matchVal
                edge
            }
        }
    // From the first `moveSuffix` on, `leftRoot` and `rightRoot` share
    // key-value pairs through two temporarily invalid tree structures, and
    // neither is independently droppable until `fixRightBorder` /
    // `fixLeftBorder` repair them and the caller recomputes both lengths.
    // A panic from a later `searchNode` comparison would unwind out of
    // that state and double-free the shared values (#158165), so abort
    // instead of exposing it.
    val guard = DropGuard.new(Unit, { kmAbort() })
    while (true) {
        splitEdge.moveSuffix(rightNode)
        run {
            val matchVal1 = Pair(splitEdge.force(), rightNode.force())
            when {
                matchVal1.first is Internal && matchVal1.second is Internal -> {
                    val (edge1) = matchVal1.first as Internal
                    val (node) = matchVal1.second as Internal
                    leftNode = edge1.descend()
                    rightNode = node.firstEdge().descend()
                }
                matchVal1.first is Leaf && matchVal1.second is Leaf -> { break }
                else -> error("unreachable")
            }
        }
        splitEdge = when (val matchVal2 = leftNode.searchNode(key)) {
            is Found<*, *, *, *, *> -> {
                val (kv1) = matchVal2
                kv1.leftEdge()
            }
            is GoDown<*, *, *, *, *> -> {
                val (edge2) = matchVal2
                edge2
            }
        }
    }
    leftRoot.fixRightBorder(alloc.clone())
    rightRoot.fixLeftBorder(alloc)
    DropGuard.dismiss(guard)
    return rightRoot
}

/**
 * Creates a tree consisting of empty nodes.
 */
public fun <K, V, A : AllocatorClone> Root.Companion.newPillar(height: Int, alloc: A): Root<K, V> {
    val root = Root.new(alloc.clone())
    for (unused in 0 until height) {
        root.pushInternalLevel(alloc.clone())
    }
    return root
}

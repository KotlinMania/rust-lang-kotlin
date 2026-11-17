// port-lint: source collections/btree/append.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.append

import io.github.kotlinmania.rustlang.alloc.collections.btree.node.CAPACITY
import io.github.kotlinmania.rustlang.alloc.collections.btree.node.Root
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Pushes all key-value pairs to the end of the tree, incrementing a
 * `length` variable along the way. The latter makes it easier for the
 * caller to avoid a leak when the iterator panicks.
 */
public fun <K, V, I, A : AllocatorClone> Root<K, V>.bulkPush(iter: I, length: RustMutableReference<Int>, alloc: A) where I : Iterator<Pair<K, V>> {
    val length1 = length
    var curNode = this.borrowMut().lastLeafEdge().intoNode()
    // Iterate through all key-value pairs, pushing them into nodes at the right level.
    for ((key, value) in iter) {
        // Try to push key-value pair into the current leaf node.
        if (curNode.len() < CAPACITY) {
            curNode.push(key, value)
        } else {
            // No space left, go up and push there.
            var openNode: Any?
            var testNode = curNode.forgetType()
            while (true) {
                run {
                    val matchVal = testNode.ascend()
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val parent = matchVal.okValue()
                        val parent1 = parent.intoNode()
                        if (parent1.len() < CAPACITY) {
                            // Found a node with space left, push here.
                            openNode = parent1
                            break
                        } else {
                            // Go up again.
                            testNode = parent1.forgetType()
                        }
                    }; is Result.Err<*, *> -> {
                        // We are at the top, create a new root node and push there.
                        openNode = this.pushInternalLevel(alloc.clone())
                        break
                    } }
                }
            }
            // Push key-value pair and new right subtree.
            val treeHeight = openNode.height() - 1
            val rightTree = Root.new(alloc.clone())
            for (unused in 0 until treeHeight) {
                rightTree.pushInternalLevel(alloc.clone())
            }
            openNode.push(key, value, rightTree)
            // Go down to the rightmost leaf again.
            curNode = openNode.forgetType().lastLeafEdge().intoNode()
        }
        // Increment length every iteration, to make sure the map drops
        // the appended elements even if advancing the iterator panicks.
        (length1).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
    }
    this.fixRightBorderOfPlentiful()
}

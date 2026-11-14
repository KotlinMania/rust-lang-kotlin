// port-lint: source collections/linked_list.rs
package io.github.kotlinmania.rustlang.alloc.collections.linkedlist

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.num.rustCheckedSub
import io.github.kotlinmania.rustlang.core.num.rustSaturatingSub
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.slice.asPtr

// A doubly-linked list with owned nodes.
//
// The `LinkedList` allows pushing and popping elements at either end
// in constant time.
//
// NOTE: It is almost always better to use [Vec] or [VecDeque] because
// array-based containers are generally faster,
// more memory efficient, and make better use of CPU cache.

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * A doubly-linked list with owned nodes.
 *
 * The `LinkedList` allows pushing and popping elements at either end
 * in constant time.
 *
 * A `LinkedList` with a known list of items can be initialized from an array:
 * ```kotlin
 * import std.collections.LinkedList
 *
 * val list = LinkedList.from(listOf(1, 2, 3))
 * ```
 *
 * NOTE: It is almost always better to use [Vec] or [VecDeque] because
 * array-based containers are generally faster,
 * more memory efficient, and make better use of CPU cache.
 */
public open class LinkedList<T, A : Allocator> internal constructor(
    headCtorArg: NonNull?,
    tailCtorArg: NonNull?,
    public var len: Int,
    public var alloc: A,
    public var marker: Unit,
) : Iterable<T> {
    public var head: NonNull? = headCtorArg
        internal set

    public var tail: NonNull? = tailCtorArg
        internal set

    override fun iterator(): kotlin.collections.Iterator<T> = this.intoIter().iterator()

    // private methods

    /**
     * Adds the given node to the front of the list.
     *
     * # Safety
     *
     * `node` must point to a valid owned node in the list's allocator.
     */
    public open fun pushFrontNode(node: NonNull) {
        // SAFETY: This method takes care not to create mutable references to
        // whole nodes, to maintain validity of aliasing pointers into `element`.
        run {
            node.asPtr().next = this.head
            node.asPtr().prev = null
            val node1 = node
            when (val matchVal = this.head) {
                null -> this.tail = node1
                // Not creating new mutable (unique!) references overlapping `element`.
                else -> {
                    val head = matchVal
                    head.asPtr().prev = node1
                }
            }
            this.head = node1
            this.len += 1
        }
    }

    /**
     * Removes and returns the node at the front of the list.
     */
    public open fun popFrontNode(): Node<T>? {
        // SAFETY: This method takes care not to create mutable references to
        // whole nodes, to maintain validity of aliasing pointers into `element`.
        return this.head?.let({ node -> run {
            val node1 = Box.fromRawIn(node.asPtr(), this.alloc)
            this.head = node1.next
            when (val matchVal = this.head) {
                null -> this.tail = null
                // Not creating new mutable (unique!) references overlapping `element`.
                else -> {
                    val head = matchVal
                    head.asPtr().prev = null
                }
            }
            this.len -= 1
            node1
        } })
    }

    /**
     * Adds the given node to the back of the list.
     *
     * # Safety
     *
     * `node` must point to a valid owned node in the list's allocator.
     */
    public open fun pushBackNode(node: NonNull) {
        // SAFETY: This method takes care not to create mutable references to
        // whole nodes, to maintain validity of aliasing pointers into `element`.
        run {
            node.asPtr().next = null
            node.asPtr().prev = this.tail
            val node1 = node
            when (val matchVal = this.tail) {
                null -> this.head = node1
                // Not creating new mutable (unique!) references overlapping `element`.
                else -> {
                    val tail = matchVal
                    tail.asPtr().next = node1
                }
            }
            this.tail = node1
            this.len += 1
        }
    }

    /**
     * Removes and returns the node at the back of the list.
     */
    public open fun popBackNode(): Node<T>? {
        // SAFETY: This method takes care not to create mutable references to
        // whole nodes, to maintain validity of aliasing pointers into `element`.
        return this.tail?.let({ node -> run {
            val node1 = Box.fromRawIn(node.asPtr(), this.alloc)
            this.tail = node1.prev
            when (val matchVal = this.tail) {
                null -> this.head = null
                // Not creating new mutable (unique!) references overlapping `element`.
                else -> {
                    val tail = matchVal
                    tail.asPtr().next = null
                }
            }
            this.len -= 1
            node1
        } })
    }

    /**
     * Unlinks the specified node from the current list.
     *
     * This method takes care not to create mutable references to `element`, to
     * maintain validity of aliasing pointers.
     *
     * # Safety
     *
     * The provided node must belong to the current list.
     */
    public open fun unlinkNode(node: NonNull) {
        // SAFETY: This is ours now, we can create a &mut.
        val node1 = node.asMut()
        // Not creating new mutable (unique!) references overlapping `element`.
        when (val matchVal = node1.prev) {
            // this node is the head node
            null -> this.head = node1.next
            // SAFETY: `prev` is a live node in this list.
            else -> {
                val prev = matchVal
                prev.asPtr().next = node1.next
            }
        }
        when (val matchVal1 = node1.next) {
            // this node is the tail node
            null -> this.tail = node1.prev
            // SAFETY: `next` is a live node in this list.
            else -> {
                val next = matchVal1
                next.asPtr().prev = node1.prev
            }
        }
        this.len -= 1
    }

    /**
     * Splices a series of nodes between two existing nodes.
     *
     * # Safety
     *
     * * The provided nodes must belong to the two existing lists.
     * * `spliceLength` must be exactly the number of nodes between `spliceStart` and `spliceEnd`, inclusive.
     * * `spliceStart` and `spliceEnd` must be in the same list, and `spliceStart` must precede `spliceEnd`.
     * * If `existingPrev` is `None`, then `existingNext` must be the head node in `self`.
     *   If `existingNext` is `None`, then `existingPrev` must be the end node in `self`.
     *   In other case, `existingPrev` must be the predecessor of `existingNext` in `self`.
     */
    public open fun spliceNodes(existingPrev: NonNull?, existingNext: NonNull?, spliceStart: NonNull, spliceEnd: NonNull, spliceLength: Int) {
        // This method takes care not to create multiple mutable references to whole nodes at the same time,
        // to maintain validity of aliasing pointers into `element`.
        if (existingPrev != null) {
            val existingPrev1 = existingPrev
            // SAFETY: `existingPrev` points to a live node owned by `self`, which don't have any other mutable references.
            run {
                existingPrev1.asMut().next = spliceStart
            }
        } else {
            this.head = spliceStart
        }
        if (existingNext != null) {
            val existingNext1 = existingNext
            // SAFETY: `existingNext` points to a live node owned by `self`, which don't have any other mutable references.
            run {
                existingNext1.asMut().prev = spliceEnd
            }
        } else {
            this.tail = spliceEnd
        }
        // SAFETY: This method takes care not to create multiple mutable references to whole nodes at the same time,
        // to maintain validity of aliasing pointers into `element`.
        run {
            spliceStart.asMut().prev = existingPrev
            spliceEnd.asMut().next = existingNext
        }
        this.len += spliceLength
    }

    /**
     * Detaches all nodes from a linked list as a series of nodes.
     */
    public open fun detachAllNodes(): Triple<NonNull, NonNull, Int>? {
        val head = this.head.also { this.head = null }
        val tail = this.tail.also { this.tail = null }
        val len = mem.replace(this.len, 0)
        return if (head != null) {
            val head1 = head
            // SAFETY: In a LinkedList, either both the head and tail are None because
            // the list is empty, or both head and tail are Some because the list is populated.
            // Since we have verified the head is Some, we are sure the tail is Some too.
            val tail1 = tail.unwrapUnchecked()
            Triple(head1, tail1, len)
        } else {
            null
        }
    }

    /**
     * Splits this list before `splitNode`.
     *
     * `self` retains `splitNode` and all nodes after it, while the returned list contains all
     * nodes before `splitNode`. If `splitNode` is `None`, all nodes are moved into the returned
     * list and `self` is left empty.
     *
     * # Safety
     *
     * * If `splitNode` is `Some`, it must point to a node in `self`.
     * * If `splitNode` is `Some`, `at` must equal its zero-based index in `self`.
     */
    public open fun splitOffBeforeNode(splitNode: NonNull?, at: Int): LinkedList<T, A> {
        // The split node is the new head node of the second part
        return if (splitNode != null) {
            val splitNode1 = splitNode
            val firstPartHead: Any?
            val firstPartTail: Any?
            // SAFETY: `splitNode` points to a live node in `self`, which don't have any other mutable references.
            run {
                firstPartTail = splitNode1.asMut().prev.take()
            }
            if (firstPartTail != null) {
                val tail = firstPartTail
                // SAFETY: `tail` is the live predecessor of `splitNode`, which don't have any other mutable references.
                run {
                    tail.asMut().next = null
                }
                firstPartHead = this.head
            } else {
                firstPartHead = null
            }
            val firstPart = LinkedList(head = firstPartHead, tail = firstPartTail, len = at, alloc = this.alloc.clone(), marker = Unit)
            // Fix the head ptr of the second part
            this.head = splitNode1
            this.len -= at
            firstPart
        } else {
            mem.replace(this, LinkedList.newIn(this.alloc.clone()))
        }
    }

    /**
     * Splits this list after `splitNode`.
     *
     * `self` retains `splitNode` and all nodes before it, while the returned list contains all
     * nodes after `splitNode`. If `splitNode` is `None`, all nodes are moved into the returned
     * list and `self` is left empty.
     *
     * # Safety
     *
     * * If `splitNode` is `Some`, it must point to a node in `self`.
     * * If `splitNode` is `Some`, `at` must equal its zero-based index in `self` plus one.
     */
    public open fun splitOffAfterNode(splitNode: NonNull?, at: Int): LinkedList<T, A> {
        // The split node is the new tail node of the first part and owns
        // the head of the second part.
        return if (splitNode != null) {
            val splitNode1 = splitNode
            val secondPartHead: Any?
            val secondPartTail: Any?
            // SAFETY: `splitNode` points to a live node in `self`, which don't have any other mutable references.
            run {
                secondPartHead = splitNode1.asMut().next.take()
            }
            if (secondPartHead != null) {
                val head = secondPartHead
                // SAFETY: `head` is the live successor of `splitNode`, which don't have any other mutable references.
                run {
                    head.asMut().prev = null
                }
                secondPartTail = this.tail
            } else {
                secondPartTail = null
            }
            val secondPart = LinkedList(head = secondPartHead, tail = secondPartTail, len = this.len - at, alloc = this.alloc.clone(), marker = Unit)
            // Fix the tail ptr of the first part
            this.tail = splitNode1
            this.len = at
            secondPart
        } else {
            mem.replace(this, LinkedList.newIn(this.alloc.clone()))
        }
    }

    /**
     * Returns a reference to the underlying allocator.
     *
     * # Examples
     *
     * ```kotlin
     * import std.alloc.System
     * import std.collections.LinkedList
     *
     * @feature(allocatorExt)
     * public fun doctestEntry() {
     *     val list: LinkedList<Int, System> = LinkedList.newIn(System)
     *     val alloc: System = list.allocator()
     * }
     * ```
     */
    public open fun allocator(): A = this.alloc

    /**
     * Provides a forward iterator.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var list: LinkedList<UInt> = LinkedList.new()
     * list.addLast(0)
     * list.addLast(1)
     * list.addLast(2)
     * var iter = list.iter()
     * check(iter.next() == 0)
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * check(iter.next() == null)
     * ```
     */
    public open fun iter(): Iter<T> = Iter(head = this.head, tail = this.tail, len = this.len, marker = Unit)

    /**
     * Provides a forward iterator with mutable references.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var list: LinkedList<UInt> = LinkedList.new()
     * list.addLast(0)
     * list.addLast(1)
     * list.addLast(2)
     * for (idx in list.indices) {
     *     list[idx] += 10
     * }
     * var iter = list.iter()
     * check(iter.next() == 10)
     * check(iter.next() == 11)
     * check(iter.next() == 12)
     * check(iter.next() == null)
     * ```
     */
    public open fun iterMut(): IterMut<T> = IterMut(head = this.head, tail = this.tail, len = this.len, marker = Unit)

    /**
     * Provides a cursor at the front element.
     *
     * The cursor is pointing to the "ghost" non-element if the list is empty.
     */
    public open fun cursorFront(): Cursor<T, A> = Cursor(index = 0, current = this.head, list = this)

    /**
     * Provides a cursor with editing operations at the front element.
     *
     * The cursor is pointing to the "ghost" non-element if the list is empty.
     */
    public open fun cursorFrontMut(): CursorMut<T, A> = CursorMut(index = 0, current = this.head, list = this)

    /**
     * Provides a cursor at the back element.
     *
     * The cursor is pointing to the "ghost" non-element if the list is empty.
     */
    public open fun cursorBack(): Cursor<T, A> = Cursor(index = rustSaturatingSub(this.len, 1), current = this.tail, list = this)

    /**
     * Provides a cursor with editing operations at the back element.
     *
     * The cursor is pointing to the "ghost" non-element if the list is empty.
     */
    public open fun cursorBackMut(): CursorMut<T, A> = CursorMut(index = rustSaturatingSub(this.len, 1), current = this.tail, list = this)

    /**
     * Returns `true` if the `LinkedList` is empty.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.new()
     * check(dl.isEmpty())
     * dl.pushFront("foo")
     * check(!dl.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.head == null

    /**
     * Returns the length of the `LinkedList`.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.new()
     * dl.pushFront(2)
     * check(dl.len() == 1)
     * dl.pushFront(1)
     * check(dl.len() == 2)
     * dl.addLast(3)
     * check(dl.len() == 3)
     * ```
     */
    public open fun len(): Int = this.len

    /**
     * Removes all elements from the `LinkedList`.
     *
     * This operation should compute in *O*(*n*) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.new()
     * dl.pushFront(2)
     * dl.pushFront(1)
     * check(dl.len() == 2)
     * check(dl.front() == 1)
     * dl.clear()
     * check(dl.len() == 0)
     * check(dl.front() == null)
     * ```
     */
    public open fun clear() {
        // We need to drop the nodes while keeping self.alloc
        // We can do this by moving (head, tail, len) into a new list that borrows self.alloc
        drop(LinkedList(head = this.head.also { this.head = null }, tail = this.tail.also { this.tail = null }, len = mem.take(this.len), alloc = this.alloc, marker = Unit))
    }

    /**
     * Returns `true` if the `LinkedList` contains an element equal to the
     * given value.
     *
     * This operation should compute linearly in *O*(*n*) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var list: LinkedList<UInt> = LinkedList.new()
     * list.addLast(0)
     * list.addLast(1)
     * list.addLast(2)
     * check(list.contains(0) == true)
     * check(list.contains(10) == false)
     * ```
     */
    public open fun contains(x: T): Boolean = this.iter().any({ e -> e == x })

    /**
     * Provides a reference to the front element, or `None` if the list is
     * empty.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.new()
     * check(dl.front() == null)
     * dl.pushFront(1)
     * check(dl.front() == 1)
     * ```
     */
    public open fun front(): T? {
        // SAFETY: `self` is immutable borrowed, so the head node can be immutable borrowed as well.
        return this.head?.let({ node -> node.asRef().element })
    }

    /**
     * Provides a mutable reference to the front element, or `None` if the list
     * is empty.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.new()
     * check(dl.front() == null)
     * dl.pushFront(1)
     * check(dl.front() == 1)
     * when (val matchVal = dl.frontMut()) {
     *     null -> {}
     *     else -> {
     *         val x = matchVal
     *         x = 5
     *     }
     * }
     * check(dl.front() == 5)
     * ```
     */
    public open fun frontMut(): T? {
        // SAFETY: `self` is mutable borrowed, so the head node can be mutable borrowed as well.
        return this.head?.let({ node -> node.asMut().element })
    }

    /**
     * Provides a reference to the back element, or `None` if the list is
     * empty.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.new()
     * check(dl.back() == null)
     * dl.addLast(1)
     * check(dl.back() == 1)
     * ```
     */
    public open fun back(): T? {
        // SAFETY: `self` is immutable borrowed, so the tail node can be immutable borrowed as well.
        return this.tail?.let({ node -> node.asRef().element })
    }

    /**
     * Provides a mutable reference to the back element, or `None` if the list
     * is empty.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.new()
     * check(dl.back() == null)
     * dl.addLast(1)
     * check(dl.back() == 1)
     * when (val matchVal = dl.backMut()) {
     *     null -> {}
     *     else -> {
     *         val x = matchVal
     *         x = 5
     *     }
     * }
     * check(dl.back() == 5)
     * ```
     */
    public open fun backMut(): T? {
        // SAFETY: `self` is mutable borrowed, so the tail node can be mutable borrowed as well.
        return this.tail?.let({ node -> node.asMut().element })
    }

    /**
     * Adds an element to the front of the list.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.new()
     * dl.pushFront(2)
     * check(dl.front().unwrap() == 2)
     * dl.pushFront(1)
     * check(dl.front().unwrap() == 1)
     * ```
     */
    public open fun pushFront(elt: T) {
        this.pushFrontMut(elt)
    }

    /**
     * Adds an element to the front of the list, returning a reference to it.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.from(listOf(1, 2, 3))
     * val ptr = dl.pushFrontMut(2)
     * ptr += 4
     * check(dl.front().unwrap() == 6)
     * ```
     */
    public open fun pushFrontMut(elt: T): T {
        val node = Box.intoNonNullWithAllocator(Box.newIn(Node.new(elt), this.alloc)).first
        // SAFETY: node is a unique pointer to a node in self.alloc
        return run {
            this.pushFrontNode(node)
            node.asMut().element
        }
    }

    /**
     * Removes the first element and returns it, or `None` if the list is
     * empty.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var d = LinkedList.new()
     * check(d.removeFirstOrNull() == null)
     * d.pushFront(1)
     * d.pushFront(3)
     * check(d.removeFirstOrNull() == 3)
     * check(d.removeFirstOrNull() == 1)
     * check(d.removeFirstOrNull() == null)
     * ```
     */
    public open fun popFront(): T? = this.popFrontNode().map(Node::intoElement)

    /**
     * Adds an element to the back of the list.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var d = LinkedList.new()
     * d.addLast(1)
     * d.addLast(3)
     * check(3 == d.back().unwrap())
     * ```
     */
    public open fun pushBack(elt: T) {
        this.pushBackMut(elt)
    }

    /**
     * Adds an element to the back of the list, returning a reference to it.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var dl = LinkedList.from(listOf(1, 2, 3))
     * val ptr = dl.pushBackMut(2)
     * ptr += 4
     * check(dl.back().unwrap() == 6)
     * ```
     */
    public open fun pushBackMut(elt: T): T {
        val node = Box.intoNonNullWithAllocator(Box.newIn(Node.new(elt), this.alloc)).first
        // SAFETY: node is a unique pointer to a node in self.alloc
        return run {
            this.pushBackNode(node)
            node.asMut().element
        }
    }

    /**
     * Removes the last element from a list and returns it, or `None` if
     * it is empty.
     *
     * This operation should compute in *O*(1) time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var d = LinkedList.new()
     * check(d.popBack() == null)
     * d.addLast(1)
     * d.addLast(3)
     * check(d.popBack() == 3)
     * ```
     */
    public open fun popBack(): T? = this.popBackNode().map(Node::intoElement)

    /**
     * Splits the list into two at the given index. Returns everything after the given index,
     * including the index.
     *
     * This operation should compute in *O*(*n*) time.
     *
     * # Panics
     *
     * Panics if `at> len`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var d = LinkedList.new()
     * d.pushFront(1)
     * d.pushFront(2)
     * d.pushFront(3)
     * var split = d.splitOff(2)
     * check(split.removeFirstOrNull() == 1)
     * check(split.removeFirstOrNull() == null)
     * ```
     */
    public open fun splitOff(at: Int): LinkedList<T, A> {
        val len = this.len()
        check(at <= len) { "Cannot split off at a nonexistent index" }
        if (at == 0) {
            return mem.replace(this, LinkedList.newIn(this.alloc.clone()))
        } else if (at == len) {
            return LinkedList.newIn(this.alloc.clone())
        }
        // Below, we iterate towards the `i - 1`th node, either from the start or the end,
        // depending on which would be faster.
        val splitNode =
            if (at - 1 <= len - 1 - (at - 1)) {
                val iter = this.iterMut()
                // instead of skipping using .skip() (which creates a new struct),
                // we skip manually so we can access the head field without
                // depending on implementation details of Skip
                for (unused in 0 until at - 1) {
                    iter.next()
                }
                iter.head
            } else {
                // better off starting from the end
                val iter1 = this.iterMut()
                for (unused1 in 0 until len - 1 - (at - 1)) {
                    iter1.nextBack()
                }
                iter1.tail
            }
        // SAFETY: `splitNode` is a valid node in `self`, and `at` is `indexOf(splitNode) + 1`.
        return this.splitOffAfterNode(splitNode, at)
    }

    /**
     * Removes the element at the given index and returns it.
     *
     * This operation should compute in *O*(*n*) time.
     *
     * # Panics
     * Panics if at >= len
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * @feature(linkedListRemove)
     * public fun doctestEntry() {
     *     var d = LinkedList.new()
     *     d.pushFront(1)
     *     d.pushFront(2)
     *     d.pushFront(3)
     *     check(d.remove(1) == 2)
     *     check(d.remove(0) == 3)
     *     check(d.remove(0) == 1)
     * }
     * ```
     */
    public open fun remove(at: Int): T {
        val len = this.len()
        check(at < len) { "Cannot remove at an index outside of the list bounds" }
        // Below, we iterate towards the node at the given index, either from
        // the start or the end, depending on which would be faster.
        val offsetFromEnd = len - at - 1
        return if (at <= offsetFromEnd) {
            val cursor = this.cursorFrontMut()
            for (unused in 0 until at) {
                cursor.moveNext()
            }
            cursor.removeCurrent().unwrap()
        } else {
            val cursor1 = this.cursorBackMut()
            for (unused1 in 0 until offsetFromEnd) {
                cursor1.movePrev()
            }
            cursor1.removeCurrent().unwrap()
        }
    }

    /**
     * Retains only the elements specified by the predicate.
     *
     * In other words, remove all elements `e` for which `f(e)` returns false.
     * This method operates in place, visiting each element exactly once in the
     * original order, and preserves the order of the retained elements.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * @feature(linkedListRetain)
     * public fun doctestEntry() {
     *     var d = LinkedList.new()
     *     d.pushFront(1)
     *     d.pushFront(2)
     *     d.pushFront(3)
     *     d.retain({ x -> x % 2 == 0 })
     *     check(d.removeFirstOrNull() == 2)
     *     check(d.removeFirstOrNull() == null)
     * }
     * ```
     *
     * Because the elements are visited exactly once in the original order,
     * external state may be used to decide which elements to keep.
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * @feature(linkedListRetain)
     * public fun doctestEntry() {
     *     var d = LinkedList.new()
     *     d.pushFront(1)
     *     d.pushFront(2)
     *     d.pushFront(3)
     *     val keep = booleanArrayOf(false, true, false)
     *     var iter = keep.iter()
     *     d.retain({ iter.next()!! })
     *     check(d.removeFirstOrNull() == 2)
     *     check(d.removeFirstOrNull() == null)
     * }
     * ```
     */
    public open fun retain(f: (T) -> Boolean) {
        val cursor = this.cursorFrontMut()
        while (true) {
            val matchVal = cursor.current()
            if (matchVal == null) break
            val node = matchVal
            if (!f(node)) {
                cursor.removeCurrent().unwrap()
            } else {
                cursor.moveNext()
            }
        }
    }

    /**
     * Creates an iterator which uses a closure to determine if an element should be removed.
     *
     * If the closure returns `true`, the element is removed from the list and
     * yielded. If the closure returns `false`, or panics, the element remains
     * in the list and will not be yielded.
     *
     * If the returned `ExtractIf` is not exhausted, e.g. because it is dropped without iterating
     * or the iteration short-circuits, then the remaining elements will be retained.
     * Use `extractIf().forEach(drop)` if you do not need the returned iterator.
     *
     * The iterator also lets you mutate the value of each element in the
     * closure, regardless of whether you choose to keep or remove it.
     *
     * # Examples
     *
     * Splitting a list into even and odd values, reusing the original list:
     *
     * ```kotlin
     * import std.collections.LinkedList
     *
     * var numbers: LinkedList<UInt> = LinkedList.new()
     * numbers.extend(listOf(1, 2, 3, 4, 5, 6, 8, 9, 11, 13, 14, 15))
     * val evens = numbers.extractIf({ x -> x % 2 == 0 }).collect<LinkedList>()
     * val odds = numbers
     * check(evens.intoIter().collect<Vec>() == mutableListOf(2, 4, 6, 8, 14))
     * check(odds.intoIter().collect<Vec>() == mutableListOf(1, 3, 5, 9, 11, 13, 15))
     * ```
     */
    public open fun extractIf(filter: (T) -> Boolean): ExtractIf<T, (T) -> Boolean, A> {
        // avoid borrow issues.
        val it = this.head
        val oldLen = this.len
        return ExtractIf(list = this, it = it, pred = filter, idx = 0, oldLen = oldLen)
    }

    public open fun drop() {
        open class DropGuard<T, A : Allocator>(
            public var value: LinkedList<T, A>,
        ) {
            public var field0: LinkedList<T, A>
                get() = value
                set(v) {
                    value = v
                }

            public open fun drop() {
                // Continue the same loop we do below. This only runs when a destructor has
                // panicked. If another one panics this will abort.
                while (this.value.popFrontNode() != null) {}
            }
        }
        // Wrap self so that if a destructor panics, we can try to keep looping
        val guard = DropGuard(this)
        while (guard.first.popFrontNode() != null) {}
        mem.forget(guard)
    }

    /**
     * Consumes the list into an iterator yielding elements by value.
     */
    public open fun intoIter(): IntoIter<T, A> = IntoIter(list = this)

    public open fun <I : Iterable<T>> extend(iter: I) {
        LinkedList.specExtend(this, iter)
    }

    public open fun extendOne(elem: T) {
        this.addLast(elem)
    }

    public open fun <I : Iterable<T>> extendT(iter: I) {
        this.extend(iter.iterator().cloned())
    }

    public open fun extendOneT(elem: T) {
        this.addLast(elem)
    }

    public open fun eq(other: LinkedList<T, A>): Boolean = this.len() == other.len() && this.iter().eq(other)

    public open fun ne(other: LinkedList<T, A>): Boolean = this.len() != other.len() || this.iter().ne(other)

    public open fun partialCmp(other: LinkedList<T, A>): Ordering? = this.iter().partialCmp(other)

    public open fun cmp(other: LinkedList<T, A>): Ordering = this.iter().cmp(other)

    public open fun clone(): LinkedList<T, A> {
        val list = LinkedList.newIn(this.alloc.clone())
        list.extend(this.iter().cloned())
        return list
    }

    /**
     * Overwrites the contents of `self` with a clone of the contents of `source`.
     *
     * This method is preferred over simply assigning `source.clone()` to `self`,
     * as it avoids reallocation of the nodes of the linked list. Additionally,
     * if the element type `T` overrides `cloneFrom()`, this will reuse the
     * resources of `self`s elements as well.
     */
    public open fun cloneFrom(source: LinkedList<T, A>) {
        val sourceIter = source.iter()
        for (elem in this.iterMut()) {
            val sourceElem = sourceIter.next() ?: break
            elem.cloneFrom(sourceElem)
        }
        while (this.len() > source.len()) {
            this.popBack()
        }
        if (!sourceIter.isEmpty()) {
            this.extend(sourceIter.cloned())
        }
    }

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this).finish()
    }

    public override fun hashCode(): Int {
        var state = 0
        state.writeLengthPrefix(this.len())
        for (elt in this) {
            state = 31 * state + (elt).hashCode()
        }
        return state
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            head: NonNull?,
            tail: NonNull?,
            len: Int,
            alloc: A,
            marker: Unit,
        ): LinkedList<T, A> = LinkedList<T, A>(
            headCtorArg = head,
            tailCtorArg = tail,
            len = len,
            alloc = alloc,
            marker = marker,
        )

        /**
         * Constructs an empty `LinkedList<T, A>`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         * import std.collections.LinkedList
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val list: LinkedList<Int, System> = LinkedList.newIn(System)
         * }
         * ```
         */
        public fun <T, A : Allocator> newIn(alloc: A): LinkedList<T, A> = LinkedList(head = null, tail = null, len = 0, alloc = alloc, marker = Unit)
    }
}

public open class Node<T> internal constructor(
    nextCtorArg: NonNull?,
    prevCtorArg: NonNull?,
    public var element: T,
) {
    public var next: NonNull? = nextCtorArg
        internal set

    public var prev: NonNull? = prevCtorArg
        internal set

    public open fun <A : Allocator> intoElement(): T = this.element

    public companion object {
        public operator fun <T> invoke(
            next: NonNull?,
            prev: NonNull?,
            element: T,
        ): Node<T> = Node<T>(
            nextCtorArg = next,
            prevCtorArg = prev,
            element = element,
        )

        public fun <T> new(element: T): Node<T> = Node(next = null, prev = null, element = element)
    }
}

/**
 * An iterator over the elements of a `LinkedList`.
 *
 * This `class` is created by [LinkedList.iter]. See its
 * documentation for more.
 */
public open class Iter<T> internal constructor(
    headCtorArg: NonNull?,
    tailCtorArg: NonNull?,
    public var len: Int,
    public var marker: Unit,
) : TrustedLen, Iterable<T> {
    public var head: NonNull? = headCtorArg
        internal set

    public var tail: NonNull? = tailCtorArg
        internal set

    override fun iterator(): kotlin.collections.Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "Iter").field(mem.ManuallyDrop.new(LinkedList(head = this.head, tail = this.tail, len = this.len, alloc = Global, marker = Unit))).field(this.len).finish()
    }

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): Iter<T> = Iter(this.head, this.tail, this.len, this.marker).copy()

    public open fun next(): T? {
        if (this.len == 0) {
            return null
        }
        // SAFETY: When `len> 0`, `head` and `tail` are guaranteed to be `Some`.
        // The scope of the returned reference is bound to the scope of the iterator,
        // which is valid because the iterator holds a reference to the list.
        return run {
            // Need an unbound scope to get a
            val node = this.head.unwrapUnchecked().asPtr()
            this.len -= 1
            this.head = node.next
            node.element
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(this.len, this.len)

    public open fun last(): T? = this.nextBack()

    public open fun nextBack(): T? {
        if (this.len == 0) {
            return null
        }
        // SAFETY: When `len> 0`, `head` and `tail` are guaranteed to be `Some`.
        // The scope of the returned reference is bound to the scope of the iterator,
        // which is valid because the iterator holds a reference to the list.
        return run {
            // Need an unbound scope to get a
            val node = this.tail.unwrapUnchecked().asPtr()
            this.len -= 1
            this.tail = node.prev
            node.element
        }
    }

    public companion object {
        public operator fun <T> invoke(
            head: NonNull?,
            tail: NonNull?,
            len: Int,
            marker: Unit,
        ): Iter<T> = Iter<T>(
            headCtorArg = head,
            tailCtorArg = tail,
            len = len,
            marker = marker,
        )

        /**
         * Creates an empty `linkedList.Iter`.
         *
         * ```kotlin
         * import linkedlist.Iter
         *
         * val iter: Iter<UByte> = Default.default()
         * check(iter.len() == 0)
         * ```
         */
        public fun <T> default(): Iter<T> = Iter(head = null, tail = null, len = 0, marker = Default.default())
    }
}

/**
 * A mutable iterator over the elements of a `LinkedList`.
 *
 * This `class` is created by [LinkedList.iterMut]. See its
 * documentation for more.
 */
public open class IterMut<T> internal constructor(
    headCtorArg: NonNull?,
    tailCtorArg: NonNull?,
    public var len: Int,
    public var marker: Unit,
) : TrustedLen, Iterable<T> {
    public var head: NonNull? = headCtorArg
        internal set

    public var tail: NonNull? = tailCtorArg
        internal set

    override fun iterator(): kotlin.collections.Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IterMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "IterMut").field(mem.ManuallyDrop.new(LinkedList(head = this.head, tail = this.tail, len = this.len, alloc = Global, marker = Unit))).field(this.len).finish()
    }

    public open fun next(): T? {
        if (this.len == 0) {
            return null
        }
        // SAFETY: When `len> 0`, `head` and `tail` are guaranteed to be `Some`.
        // The scope of the returned reference is bound to the scope of the iterator,
        // which is valid because the iterator holds a reference to the list.
        return run {
            // Need an unbound scope to get a
            val node = this.head.unwrapUnchecked().asPtr()
            this.len -= 1
            this.head = node.next
            node.element
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(this.len, this.len)

    public open fun last(): T? = this.nextBack()

    public open fun nextBack(): T? {
        if (this.len == 0) {
            return null
        }
        // SAFETY: When `len> 0`, `head` and `tail` are guaranteed to be `Some`.
        // The scope of the returned reference is bound to the scope of the iterator,
        // which is valid because the iterator holds a reference to the list.
        return run {
            // Need an unbound scope to get a
            val node = this.tail.unwrapUnchecked().asPtr()
            this.len -= 1
            this.tail = node.prev
            node.element
        }
    }

    public companion object {
        public operator fun <T> invoke(
            head: NonNull?,
            tail: NonNull?,
            len: Int,
            marker: Unit,
        ): IterMut<T> = IterMut<T>(
            headCtorArg = head,
            tailCtorArg = tail,
            len = len,
            marker = marker,
        )

        public fun <T> default(): IterMut<T> = IterMut(head = null, tail = null, len = 0, marker = Default.default())
    }
}

/**
 * An owning iterator over the elements of a `LinkedList`.
 *
 * This `class` is created by the [intoIter] method on [LinkedList]
 * (provided by the [IntoIterator] trait). See its documentation for more.
 *
 * [intoIter]: LinkedList.intoIter
 */
public open class IntoIter<T, A : Allocator> internal constructor(
    listCtorArg: LinkedList<T, A>,
) : TrustedLen, Iterable<T> {
    public var list: LinkedList<T, A> = listCtorArg
        internal set

    override fun iterator(): kotlin.collections.Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IntoIter<T, A> = IntoIter(list)

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "IntoIter").field(this.list).finish()
    }

    public open fun next(): T? = this.list.removeFirstOrNull()

    public open fun sizeHint(): Pair<Int, Int?> = Pair(this.list.len, this.list.len)

    public open fun nextBack(): T? = this.list.popBack()

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            list: LinkedList<T, A>,
        ): IntoIter<T, A> = IntoIter<T, A>(
            listCtorArg = list,
        )
    }
}

/**
 * Creates an empty `LinkedList<T>`.
 */
public fun <T> defaultLinkedListT(): LinkedList<T> = LinkedList.new()

/**
 * Creates an empty `LinkedList`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.collections.LinkedList
 *
 * val list: LinkedList<UInt> = LinkedList.new()
 * ```
 */
public fun <T> LinkedList.Companion.new(): LinkedList<T> = LinkedList(head = null, tail = null, len = 0, alloc = Global, marker = Unit)

/**
 * Moves all elements from `other` to the end of the list.
 *
 * This reuses all the nodes from `other` and moves them into `self`. After
 * this operation, `other` becomes empty.
 *
 * This operation should compute in *O*(1) time and *O*(1) memory.
 *
 * # Examples
 *
 * ```kotlin
 * import std.collections.LinkedList
 *
 * var list1 = LinkedList.new()
 * list1.addLast('a')
 * var list2 = LinkedList.new()
 * list2.addLast('b')
 * list2.addLast('c')
 * list1.append(list2)
 * var iter = list1.iter()
 * check(iter.next() == 'a')
 * check(iter.next() == 'b')
 * check(iter.next() == 'c')
 * check(iter.next() == null)
 * check(list2.isEmpty())
 * ```
 */
public fun <T> LinkedList<T>.append(other: LinkedList<T>) {
    when (val matchVal = this.tail) {
        null -> mem.swap(this, other)
        else -> {
            val tail = matchVal
            when (val matchVal1 = other.head.also { other.head = null }) {
                null -> {}
                else -> {
                    val otherHead = matchVal1
                    // SAFETY: `asMut` is okay here because we have exclusive
                    // access to the entirety of both lists.
                    run {
                        tail.asMut().next = otherHead
                        otherHead.asMut().prev = tail
                    }
                    this.tail = other.tail.also { other.tail = null }
                    this.len += mem.replace(other.len, 0)
                }
            }
        }
    }
}

/**
 * A cursor over a `LinkedList`.
 *
 * A `Cursor` is like an iterator, except that it can freely seek back-and-forth.
 *
 * Cursors always rest between two elements in the list, and index in a logically circular way.
 * To accommodate this, there is a "ghost" non-element that yields `None` between the head and
 * tail of the list.
 *
 * When created, cursors start at the front of the list, or the "ghost" non-element if the list is empty.
 */
public open class Cursor<T, A : Allocator> internal constructor(
    public var index: Int,
    currentCtorArg: NonNull?,
    listCtorArg: LinkedList<T, A>,
) {
    public var current: NonNull? = currentCtorArg
        internal set

    public var list: LinkedList<T, A> = listCtorArg
        internal set

    public open fun clone(): Cursor<T, A> {
        val structVal = Cursor(this.index, this.current, this.list)
        val index = structVal.index
        val current = structVal.current
        val list = structVal.list
        return Cursor(index = index, current = current, list = list)
    }

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "Cursor").field(this.list).field(this.index()).finish()
    }

    /**
     * Returns the cursor position index within the `LinkedList`.
     *
     * This returns `None` if the cursor is currently pointing to the
     * "ghost" non-element.
     */
    public open fun index(): Int? {
        (this.current ?: return null)
        return this.index
    }

    /**
     * Moves the cursor to the next element of the `LinkedList`.
     *
     * If the cursor is pointing to the "ghost" non-element then this will move it to
     * the first element of the `LinkedList`. If it is pointing to the last
     * element of the `LinkedList` then this will move it to the "ghost" non-element.
     */
    public open fun moveNext() {
        when (val matchVal = this.current.also { this.current = null }) {
            // We had no current element; the cursor was sitting at the start position
            // Next element should be the head of the list
            null -> {
                this.current = this.list.head
                this.index = 0
            }
            // We had a previous element, so let's go to its next
            // SAFETY: `current` is a pointer to a live node in `list`, which don't have mutable reference.
            else -> {
                val current = matchVal
                run {
                    this.current = current.asRef().next
                    this.index += 1
                }
            }
        }
    }

    /**
     * Moves the cursor to the previous element of the `LinkedList`.
     *
     * If the cursor is pointing to the "ghost" non-element then this will move it to
     * the last element of the `LinkedList`. If it is pointing to the first
     * element of the `LinkedList` then this will move it to the "ghost" non-element.
     */
    public open fun movePrev() {
        when (val matchVal = this.current.also { this.current = null }) {
            // No current. We're at the start of the list. Yield None and jump to the end.
            null -> {
                this.current = this.list.tail
                this.index = rustSaturatingSub(this.list.len(), 1)
            }
            // Have a prev. Yield it and go to the previous element.
            // SAFETY: `current` is a pointer to a live node in `list`, which don't have mutable reference.
            else -> {
                val current = matchVal
                run {
                    this.current = current.asRef().prev
                    this.index = (rustCheckedSub(this.index, 1) ?: this.list.len())
                }
            }
        }
    }

    /**
     * Returns a reference to the element that the cursor is currently
     * pointing to.
     *
     * This returns `None` if the cursor is currently pointing to the
     * "ghost" non-element.
     */
    public open fun current(): T? {
        // SAFETY: If `self.current` is `Some`, then `current` is a pointer to a live node in `list`.
        return this.current?.let({ current -> current.asPtr().element })
    }

    /**
     * Returns a reference to the next element.
     *
     * If the cursor is pointing to the "ghost" non-element then this returns
     * the first element of the `LinkedList`. If it is pointing to the last
     * element of the `LinkedList` then this returns `None`.
     */
    public open fun peekNext(): T? {
        // SAFETY: If `self.current` is `Some`, then `current` is a pointer to a live node in `list`,
        // and `current` does not have mutable reference.
        // If `next` is `Some`, then we can get inner pointer which points at a live node in `list`.
        return run {
            val next =
                when (val matchVal = this.current) {
                    null -> this.list.head
                    else -> {
                        val current = matchVal
                        current.asRef().next
                    }
                }
            next.map({ next1 -> next1.asPtr().element })
        }
    }

    /**
     * Returns a reference to the previous element.
     *
     * If the cursor is pointing to the "ghost" non-element then this returns
     * the last element of the `LinkedList`. If it is pointing to the first
     * element of the `LinkedList` then this returns `None`.
     */
    public open fun peekPrev(): T? {
        // SAFETY: If `self.current` is `Some`, then `current` is a pointer to a live node in `list`,
        // and `current` does not have mutable reference.
        // If `prev` is `Some`, then we can get inner pointer which points at a live node in `list`.
        return run {
            val prev =
                when (val matchVal = this.current) {
                    null -> this.list.tail
                    else -> {
                        val current = matchVal
                        current.asRef().prev
                    }
                }
            prev.map({ prev1 -> prev1.asPtr().element })
        }
    }

    /**
     * Provides a reference to the front element of the cursor's parent list,
     * or None if the list is empty.
     */
    public open fun front(): T? = this.list.front()

    /**
     * Provides a reference to the back element of the cursor's parent list,
     * or None if the list is empty.
     */
    public open fun back(): T? = this.list.back()

    /**
     * Provides a reference to the cursor's parent list.
     */
    public open fun asList(): LinkedList<T, A> = this.list

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            index: Int,
            current: NonNull?,
            list: LinkedList<T, A>,
        ): Cursor<T, A> = Cursor<T, A>(
            index = index,
            currentCtorArg = current,
            listCtorArg = list,
        )
    }
}

/**
 * A cursor over a `LinkedList` with editing operations.
 *
 * A `Cursor` is like an iterator, except that it can freely seek back-and-forth, and can
 * safely mutate the list during iteration. This is because the scope of its yielded
 * references is tied to its own scope, instead of just the underlying list. This means
 * cursors cannot yield multiple elements at once.
 *
 * Cursors always rest between two elements in the list, and index in a logically circular way.
 * To accommodate this, there is a "ghost" non-element that yields `None` between the head and
 * tail of the list.
 */
public open class CursorMut<T, A : Allocator> internal constructor(
    public var index: Int,
    currentCtorArg: NonNull?,
    listCtorArg: LinkedList<T, A>,
) {
    public var current: NonNull? = currentCtorArg
        internal set

    public var list: LinkedList<T, A> = listCtorArg
        internal set

    public open fun fmt(f: StringBuilder) {
        DebugTuple(f, "CursorMut").field(this.list).field(this.index()).finish()
    }

    /**
     * Returns the cursor position index within the `LinkedList`.
     *
     * This returns `None` if the cursor is currently pointing to the
     * "ghost" non-element.
     */
    public open fun index(): Int? {
        (this.current ?: return null)
        return this.index
    }

    /**
     * Moves the cursor to the next element of the `LinkedList`.
     *
     * If the cursor is pointing to the "ghost" non-element then this will move it to
     * the first element of the `LinkedList`. If it is pointing to the last
     * element of the `LinkedList` then this will move it to the "ghost" non-element.
     */
    public open fun moveNext() {
        when (val matchVal = this.current.also { this.current = null }) {
            // We had no current element; the cursor was sitting at the start position
            // Next element should be the head of the list
            null -> {
                this.current = this.list.head
                this.index = 0
            }
            // We had a previous element, so let's go to its next
            // SAFETY: `current` is a pointer to a live node in `list`, which don't have mutable reference.
            else -> {
                val current = matchVal
                run {
                    this.current = current.asRef().next
                    this.index += 1
                }
            }
        }
    }

    /**
     * Moves the cursor to the previous element of the `LinkedList`.
     *
     * If the cursor is pointing to the "ghost" non-element then this will move it to
     * the last element of the `LinkedList`. If it is pointing to the first
     * element of the `LinkedList` then this will move it to the "ghost" non-element.
     */
    public open fun movePrev() {
        when (val matchVal = this.current.also { this.current = null }) {
            // No current. We're at the start of the list. Yield None and jump to the end.
            null -> {
                this.current = this.list.tail
                this.index = rustSaturatingSub(this.list.len(), 1)
            }
            // Have a prev. Yield it and go to the previous element.
            // SAFETY: `current` is a pointer to a live node in `list`, which don't have mutable reference.
            else -> {
                val current = matchVal
                run {
                    this.current = current.asRef().prev
                    this.index = (rustCheckedSub(this.index, 1) ?: this.list.len())
                }
            }
        }
    }

    /**
     * Returns a reference to the element that the cursor is currently
     * pointing to.
     *
     * This returns `None` if the cursor is currently pointing to the
     * "ghost" non-element.
     */
    public open fun current(): T? {
        // SAFETY: If `self.current` is `Some`, then `current` is a pointer to a live node in `list`.
        return this.current?.let({ current -> current.asPtr().element })
    }

    /**
     * Returns a reference to the next element.
     *
     * If the cursor is pointing to the "ghost" non-element then this returns
     * the first element of the `LinkedList`. If it is pointing to the last
     * element of the `LinkedList` then this returns `None`.
     */
    public open fun peekNext(): T? {
        // SAFETY: If `self.current` is `Some`, then `current` is a pointer to a live node in `list`,
        // and `current` does not have mutable reference.
        // If `next` is `Some`, then we can get inner pointer which points at a live node in `list`.
        return run {
            val next =
                when (val matchVal = this.current) {
                    null -> this.list.head
                    else -> {
                        val current = matchVal
                        current.asRef().next
                    }
                }
            next.map({ next1 -> next1.asPtr().element })
        }
    }

    /**
     * Returns a reference to the previous element.
     *
     * If the cursor is pointing to the "ghost" non-element then this returns
     * the last element of the `LinkedList`. If it is pointing to the first
     * element of the `LinkedList` then this returns `None`.
     */
    public open fun peekPrev(): T? {
        // SAFETY: If `self.current` is `Some`, then `current` is a pointer to a live node in `list`,
        // and `current` does not have mutable reference.
        // If `prev` is `Some`, then we can get inner pointer which points at a live node in `list`.
        return run {
            val prev =
                when (val matchVal = this.current) {
                    null -> this.list.tail
                    else -> {
                        val current = matchVal
                        current.asRef().prev
                    }
                }
            prev.map({ prev1 -> prev1.asPtr().element })
        }
    }

    /**
     * Returns a read-only cursor pointing to the current element.
     *
     * The scope of the returned `Cursor` is bound to that of the
     * `CursorMut`, which means it cannot outlive the `CursorMut` and that the
     * `CursorMut` is frozen for the scope of the `Cursor`.
     */
    public open fun asCursor(): Cursor<T, A> = Cursor(list = this.list, current = this.current, index = this.index)

    /**
     * Provides a read-only reference to the cursor's parent list.
     *
     * The scope of the returned reference is bound to that of the
     * `CursorMut`, which means it cannot outlive the `CursorMut` and that the
     * `CursorMut` is frozen for the scope of the reference.
     */
    public open fun asList(): LinkedList<T, A> = this.list

    /**
     * Inserts a new element into the `LinkedList` after the current one.
     *
     * If the cursor is pointing at the "ghost" non-element then the new element is
     * inserted at the front of the `LinkedList`.
     */
    public open fun insertAfter(item: T) {
        val splicedNode = Box.intoNonNullWithAllocator(Box.newIn(Node.new(item), this.list.alloc)).first
        // SAFETY:
        // * If `self.current` is `Some`, then `node` is a pointer to a live node in `list`
        // and don't have mutable reference.
        // * `self.current` (possible `None`) and `nodeNext` belong to `self.list`,
        // while `splicedNode` does not belong to any list.
        // * This method only inserts one node, so `splicedNode` is both the head and tail of the new nodes to be spliced in.
        // * The length of the new nodes to be spliced in is 1.
        // * If `self.current` is `None`, then `nodeNext` is the head of the list.
        // If `self.current` is `Some`, then `nodeNext` is the next node after `self.current`.
        run {
            val nodeNext =
                when (val matchVal = this.current) {
                    null -> this.list.head
                    else -> {
                        val node = matchVal
                        node.asRef().next
                    }
                }
            this.list.spliceNodes(this.current, nodeNext, splicedNode, splicedNode, 1)
        }
        if (this.current == null) {
            // The "ghost" non-element's index has changed.
            this.index = this.list.len
        }
    }

    /**
     * Inserts a new element into the `LinkedList` before the current one.
     *
     * If the cursor is pointing at the "ghost" non-element then the new element is
     * inserted at the end of the `LinkedList`.
     */
    public open fun insertBefore(item: T) {
        val splicedNode = Box.intoNonNullWithAllocator(Box.newIn(Node.new(item), this.list.alloc)).first
        // SAFETY:
        // * If `self.current` is `Some`, then `node` is a pointer to a live node in `list`
        // and don't have mutable reference.
        // * `self.current` (possible `None`) and `nodePrev` belong to `self.list`,
        // while `splicedNode` does not belong to any list.
        // * This method only inserts one node, so `splicedNode` is both the head and tail of the new nodes to be spliced in.
        // * The length of the new nodes to be spliced in is 1.
        // * If `self.current` is `None`, then `nodePrev` is the tail of the list.
        // If `self.current` is `Some`, then `nodePrev` is the previous node before `self.current`.
        run {
            val nodePrev =
                when (val matchVal = this.current) {
                    null -> this.list.tail
                    else -> {
                        val node = matchVal
                        node.asRef().prev
                    }
                }
            this.list.spliceNodes(nodePrev, this.current, splicedNode, splicedNode, 1)
        }
        this.index += 1
    }

    /**
     * Removes the current element from the `LinkedList`.
     *
     * The element that was removed is returned, and the cursor is
     * moved to point to the next element in the `LinkedList`.
     *
     * If the cursor is currently pointing to the "ghost" non-element then no element
     * is removed and `None` is returned.
     */
    public open fun removeCurrent(): T? {
        val unlinkedNode = (this.current ?: return null)
        // SAFETY: `unlinkedNode` is a pointer to a live node owned by `self.list`.
        // It is safe to unlink it and construct a `Holder` from it.
        return run {
            this.current = unlinkedNode.asRef().next
            this.list.unlinkNode(unlinkedNode)
            val unlinkedNode1 = Box.fromRawIn(unlinkedNode.asPtr(), this.list.alloc)
            unlinkedNode1.element
        }
    }

    /**
     * Removes the current element from the `LinkedList` without deallocating the list node.
     *
     * The node that was removed is returned as a new `LinkedList` containing only this node.
     * The cursor is moved to point to the next element in the current `LinkedList`.
     *
     * If the cursor is currently pointing to the "ghost" non-element then no element
     * is removed and `None` is returned.
     */
    public open fun removeCurrentAsList(): LinkedList<T, A>? {
        val unlinkedNode = (this.current ?: return null)
        // SAFETY: `unlinkedNode` is a pointer to a live node owned by `self.list`.
        // It is safe to unlink it. Also, `unlinkedNode` does not have any mutable reference.
        run {
            this.current = unlinkedNode.asRef().next
            this.list.unlinkNode(unlinkedNode)
            unlinkedNode.asMut().prev = null
            unlinkedNode.asMut().next = null
        }
        return LinkedList(head = unlinkedNode, tail = unlinkedNode, len = 1, alloc = this.list.alloc.clone(), marker = Unit)
    }

    /**
     * Splits the list into two after the current element. This will return a
     * new list consisting of everything after the cursor, with the original
     * list retaining everything before.
     *
     * If the cursor is pointing at the "ghost" non-element then the entire contents
     * of the `LinkedList` are moved.
     */
    public open fun splitAfter(): LinkedList<T, A> {
        val splitOffIdx =
            if (this.index == this.list.len) {
                0
            } else {
                this.index + 1
            }
        if (this.index == this.list.len) {
            // The "ghost" non-element's index has changed to 0.
            this.index = 0
        }
        // SAFETY: If `self.current` is `Some`, it contains a pointer to a live node in `list`,
        // and `splitOffIdx` is `self.index + 1`.
        return this.list.splitOffAfterNode(this.current, splitOffIdx)
    }

    /**
     * Splits the list into two before the current element. This will return a
     * new list consisting of everything before the cursor, with the original
     * list retaining everything after.
     *
     * If the cursor is pointing at the "ghost" non-element then the entire contents
     * of the `LinkedList` are moved.
     */
    public open fun splitBefore(): LinkedList<T, A> {
        val splitOffIdx = this.index
        this.index = 0
        // SAFETY: If `self.current` is `Some`, it contains a pointer to a live node in `list`,
        // and `splitOffIdx` is the index of `self.current`.
        return this.list.splitOffBeforeNode(this.current, splitOffIdx)
    }

    // `pushFront` continues to point to "ghost" when it adds a node to mimic
    // the behavior of `insertBefore` on an empty list.

    /**
     * Appends an element to the front of the cursor's parent list. The node
     * that the cursor points to is unchanged, even if it is the "ghost" node.
     *
     * This operation should compute in *O*(1) time.
     */
    public open fun pushFront(elt: T) {
        // Safety: We know that `pushFront` does not change the position in
        // memory of other nodes. This ensures that `self.current` remains
        // valid.
        this.list.pushFront(elt)
        this.index += 1
    }

    /**
     * Appends an element to the back of the cursor's parent list. The node
     * that the cursor points to is unchanged, even if it is the "ghost" node.
     *
     * This operation should compute in *O*(1) time.
     */
    public open fun pushBack(elt: T) {
        // Safety: We know that `pushBack` does not change the position in
        // memory of other nodes. This ensures that `self.current` remains
        // valid.
        this.list.addLast(elt)
        if (this.current() == null) {
            // The index of "ghost" is the length of the list, so we just need
            // to increment self.index to reflect the new length of the list.
            this.index += 1
        }
    }

    /**
     * Removes the first element from the cursor's parent list and returns it,
     * or None if the list is empty. The element the cursor points to remains
     * unchanged, unless it was pointing to the front element. In that case, it
     * points to the new front element.
     *
     * This operation should compute in *O*(1) time.
     */
    public open fun popFront(): T? {
        // We can't check if current is empty, we must check the list directly.
        // It is possible for `self.current == None` and the list to be
        // non-empty.
        return if (this.list.isEmpty()) {
            null
        } else {
            // We can't point to the node that we pop. Copying the behavior of
            // `removeCurrent`, we move on to the next node in the sequence.
            // If the list is of length 1 then we end pointing to the "ghost"
            // node at index 0, which is expected.
            if (this.list.head == this.current) {
                this.moveNext()
            }
            // An element was removed before (or at) our current position, so
            // the index must be decremented. `saturatingSub` handles the
            // ghost node case where index could be 0.
            this.index = rustSaturatingSub(this.index, 1)
            this.list.removeFirstOrNull()
        }
    }

    /**
     * Removes the last element from the cursor's parent list and returns it,
     * or None if the list is empty. The element the cursor points to remains
     * unchanged, unless it was pointing to the back element. In that case, it
     * points to the "ghost" element.
     *
     * This operation should compute in *O*(1) time.
     */
    public open fun popBack(): T? =
        if (this.list.isEmpty()) {
            null
        } else {
            if (this.list.tail == this.current) {
                // The index now reflects the length of the list. It was the
                // length of the list minus 1, but now the list is 1 smaller. No
                // change is needed for `index`.
                this.current = null
            } else if (this.current == null) {
                this.index = this.list.len - 1
            }
            this.list.popBack()
        }

    /**
     * Provides a reference to the front element of the cursor's parent list,
     * or None if the list is empty.
     */
    public open fun front(): T? = this.list.front()

    /**
     * Provides a mutable reference to the front element of the cursor's
     * parent list, or None if the list is empty.
     */
    public open fun frontMut(): T? = this.list.frontMut()

    /**
     * Provides a reference to the back element of the cursor's parent list,
     * or None if the list is empty.
     */
    public open fun back(): T? = this.list.back()

    /**
     * Provides a mutable reference to back element of the cursor's parent
     * list, or `None` if the list is empty.
     *
     * # Examples
     * Building and mutating a list with a cursor, then getting the back element:
     * ```kotlin
     * import std.collections.LinkedList
     *
     * @feature(linkedListCursors)
     * public fun doctestEntry() {
     *     var dl = LinkedList.new()
     *     dl.pushFront(3)
     *     dl.pushFront(2)
     *     dl.pushFront(1)
     *     var cursor = dl.cursorFrontMut()
     *     cursor.current().unwrap() = 99
     *     cursor.backMut().unwrap() = 0
     *     var contents = dl.intoIter()
     *     check(contents.next() == 99)
     *     check(contents.next() == 2)
     *     check(contents.next() == 0)
     *     check(contents.next() == null)
     * }
     * ```
     */
    public open fun backMut(): T? = this.list.backMut()

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            index: Int,
            current: NonNull?,
            list: LinkedList<T, A>,
        ): CursorMut<T, A> = CursorMut<T, A>(
            index = index,
            currentCtorArg = current,
            listCtorArg = list,
        )
    }
}

// Now the list editing operations

/**
 * Inserts the elements from the given `LinkedList` after the current one.
 *
 * If the cursor is pointing at the "ghost" non-element then the new elements are
 * inserted at the start of the `LinkedList`.
 */
public fun <T> CursorMut<T>.spliceAfter(list: LinkedList<T>) {
    val (spliceHead, spliceTail, spliceLen) = list.detachAllNodes() ?: return
    // SAFETY:
    // * If `self.current` is `Some`, then `node` is a pointer to a live node in `list`
    // and don't have mutable reference.
    // * `self.current` (possible `None`) and `nodeNext` belong to `self.list`,
    // while `spliceHead` and `spliceTail` belong to `list`.
    // * `spliceLen` is the length of the new nodes to be spliced in.
    // * `spliceHead` and `spliceTail` are the head and tail of the new nodes to be spliced in.
    // * If `self.current` is `None`, then `nodeNext` is the head of the list.
    // If `self.current` is `Some`, then `nodeNext` is the next node after `self.current`.
    run {
        val nodeNext =
            when (val matchVal = this.current) {
                null -> this.list.head
                else -> {
                    val node = matchVal
                    node.asRef().next
                }
            }
        this.list.spliceNodes(this.current, nodeNext, spliceHead, spliceTail, spliceLen)
    }
    if (this.current == null) {
        // The "ghost" non-element's index has changed.
        this.index = this.list.len
    }
}

/**
 * Inserts the elements from the given `LinkedList` before the current one.
 *
 * If the cursor is pointing at the "ghost" non-element then the new elements are
 * inserted at the end of the `LinkedList`.
 */
public fun <T> CursorMut<T>.spliceBefore(list: LinkedList<T>) {
    val (spliceHead, spliceTail, spliceLen) =
        run {
            val matchVal = list.detachAllNodes()
            when {
                matchVal != null -> {
                    val parts = matchVal
                    parts
                }
                else -> { return }
            }
        }
    // SAFETY:
    // * If `self.current` is `Some`, then `node` is a pointer to a live node in `list`
    // and don't have mutable reference.
    // * `self.current` (possible `None`) and `nodePrev` belong to `self.list`,
    // while `spliceHead` and `spliceTail` belong to `list`.
    // * `spliceLen` is the length of the new nodes to be spliced in.
    // * `spliceHead` and `spliceTail` are the head and tail of the new nodes to be spliced in.
    // * If `self.current` is `None`, then `nodePrev` is the tail of the list.
    // If `self.current` is `Some`, then `nodePrev` is the previous node before `self.current`.
    run {
        val nodePrev =
            when (val matchVal1 = this.current) {
                null -> this.list.tail
                else -> {
                    val node = matchVal1
                    node.asRef().prev
                }
            }
        this.list.spliceNodes(nodePrev, this.current, spliceHead, spliceTail, spliceLen)
    }
    this.index += spliceLen
}

/**
 * This `class` is created by the [extractIf] method on [LinkedList].
 *
 * [extractIf]: LinkedList.extractIf
 */
public open class ExtractIf<T, F, A : Allocator> internal constructor(
    listCtorArg: LinkedList<T, A>,
    itCtorArg: NonNull?,
    public var pred: F,
    public var idx: Int,
    public var oldLen: Int,
) : Iterable<T> {
    public var list: LinkedList<T, A> = listCtorArg
        internal set

    public var it: NonNull? = itCtorArg
        internal set

    override fun iterator(): kotlin.collections.Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@ExtractIf.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): T? {
        while (this.it != null) {
            val node = this.it
            // SAFETY: `node` is a pointer to a live node in `list`, which don't have mutable reference.
            run {
                this.it = node.asRef().next
                this.idx += 1
                if ((this.pred)(node.asMut().element)) {
                    // `unlinkNode` is okay with aliasing `element` references.
                    this.list.unlinkNode(node)
                    return Box.fromRawIn(node.asPtr(), this.list.alloc).element
                }
            }
        }
        return null
    }

    public open fun sizeHint(): Pair<Int, Int?> = Pair(0, this.oldLen - this.idx)

    public open fun fmt(f: StringBuilder) {
        // SAFETY: If `node` is `Some`, then `node` is a pointer to a live node in `list`.
        val peek = this.it?.let({ node -> node.asRef().element })
        DebugStruct(f, "ExtractIf").field("peek", peek).finishNonExhaustive()
    }

    public companion object {
        public operator fun <T, F, A : Allocator> invoke(
            list: LinkedList<T, A>,
            it: NonNull?,
            pred: F,
            idx: Int,
            oldLen: Int,
        ): ExtractIf<T, F, A> = ExtractIf<T, F, A>(
            listCtorArg = list,
            itCtorArg = it,
            pred = pred,
            idx = idx,
            oldLen = oldLen,
        )
    }
}

/**
 * Creates an empty `linkedList.IntoIter`.
 *
 * ```kotlin
 * import linkedlist.IntoIter
 *
 * val iter: IntoIter<UByte> = Default.default()
 * check(iter.len() == 0)
 * ```
 */
public fun <T> defaultIntoIterT(): IntoIter<T> = LinkedList.new().intoIter()

public fun <T, I : Iterable<T>> fromIteratorFromIterLinkedListT(iter: I): LinkedList<T> {
    val list: LinkedList<T>  = LinkedList.new()
    list.extend(iter)
    return list
}

public fun <I, A : Allocator> LinkedList<I.Item, A>.specExtend(iter: I) {
    iter.iterator().forEach({ elt -> this.addLast(elt) })
}

public fun <T> LinkedList<T>.specExtend(other: LinkedList<T>) {
    this.append(other)
}

/**
 * Converts a `[T; N]` into a `LinkedList<T>`.
 *
 * ```kotlin
 * import std.collections.LinkedList
 *
 * val list1 = LinkedList.from(listOf(1, 2, 3, 4))
 * val list2: LinkedList = listOf(1, 2, 3, 4).into()
 * check(list1 == list2)
 * ```
 */
public fun <T> fromLinkedListT(arr: List<T>, n: Int): LinkedList<T> = LinkedList.fromIter(arr)

// Ensure that `LinkedList` and its read-only iterators are covariant in their type parameters.
public fun assertCovariance() {
    fun a(x: LinkedList<String>): LinkedList<String> = x
    fun b(x: Iter<String>): Iter<String> = x
    fun c(x: IntoIter<String>): IntoIter<String> = x
}

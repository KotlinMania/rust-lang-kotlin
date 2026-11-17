// port-lint: source collections/btree/node.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.node

import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.collections.btree.mem.takeMut
import io.github.kotlinmania.rustlang.alloc.collections.linkedlist.Node
import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.Dropper
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.index.SliceIndex

public val B: Int = 6

public val CAPACITY: Int = 2 * B - 1

public val MIN_LEN_AFTER_SPLIT: Int = B - 1

public val KV_IDX_CENTER: Int = B - 1

public val EDGE_IDX_LEFT_OF_CENTER: Int = B - 1

public val EDGE_IDX_RIGHT_OF_CENTER: Int = B

/**
 * The underlying representation of leaf nodes and part of the representation of internal nodes.
 */
public open class LeafNode<K, V> internal constructor(
    parentCtorArg: NonNull?,
    parentIdxCtorArg: MaybeUninit<UShort>,
    /**
     * The number of keys and values this node stores.
     */
    public var len: UShort,
    /**
     * The arrays storing the actual data of the node. Only the first `len` elements of each
     * array are initialized and valid.
     */
    public var keys: List<MaybeUninit<K>>,
    public var vals: List<MaybeUninit<V>>,
) {
    /**
     * We want to be covariant in `K` and `V`.
     */
    public var parent: NonNull? = parentCtorArg
        internal set

    /**
     * This node's index into the parent node's `edges` array.
     * `* node.parent.edges[node.parentIdx]` should be the same thing as `node`.
     * This is only guaranteed to be initialized when `parent` is non-null.
     */
    public var parentIdx: MaybeUninit<UShort> = parentIdxCtorArg
        internal set

    public companion object {
        public operator fun <K, V> invoke(
            parent: NonNull?,
            parentIdx: MaybeUninit<UShort>,
            len: UShort,
            keys: List<MaybeUninit<K>>,
            vals: List<MaybeUninit<V>>,
        ): LeafNode<K, V> = LeafNode<K, V>(
            parentCtorArg = parent,
            parentIdxCtorArg = parentIdx,
            len = len,
            keys = keys,
            vals = vals,
        )

        /**
         * Initializes a new `LeafNode` in-place.
         *
         * # Safety
         *
         * The caller must ensure that `this` points to a (possibly uninitialized) `LeafNode`
         */
        public fun <K, V> init(thisRef: MutPtr) {
            // As a general policy, we leave fields uninitialized if they can be, as this should
            // be both slightly faster and easier to track in Valgrind.
            // ignore-tidy-undocumented-unchecked
            run {
                // parentIdx, keys, and vals are all MaybeUninit
                (UnresolvedTypeLayout<NonNull?>()).writeNative(((addrOfMut((UnresolvedTypeLayout<LeafNode<K, V>>()).readNative((thisRef).rawAddress).parent))).rawAddress, null)
                (PrimitiveLayouts.U16).writeNative(((addrOfMut((UnresolvedTypeLayout<LeafNode<K, V>>()).readNative((thisRef).rawAddress).len))).rawAddress, 0)
            }
        }

        /**
         * Creates a new boxed `LeafNode`.
         */
        public fun <K, V, A : AllocatorClone> new(alloc: A): LeafNode<K, V> {
            val leaf = Box.newUninitIn(alloc)
            // SAFETY: `leaf` points to a `LeafNode`.
            LeafNode.init(leaf.asMutPtr())
            // SAFETY: `leaf` was just initialized.
            return leaf.assumeInit()
        }
    }
}

// gdbProviders.py uses this type name for introspection.

/**
 * The underlying representation of internal nodes. As with `LeafNode`s, these should be hidden
 * behind `BoxedNode`s to prevent dropping uninitialized keys and values. Any pointer to an
 * `InternalNode` can be directly cast to a pointer to the underlying `LeafNode` portion of the
 * node, allowing code to act on leaf and internal nodes generically without having to even check
 * which of the two a pointer is pointing at. This property is enabled by the use of `repr(C)`.
 */
public open class InternalNode<K, V> internal constructor(
    public var data: LeafNode<K, V>,
    edgesCtorArg: List<MaybeUninit<BoxedNode<K, V>>>,
) {
    /**
     * The pointers to the children of this node. `len + 1` of these are considered
     * initialized and valid, except that near the end, while the tree is held
     * through borrow type `Dying`, some of these pointers are dangling.
     */
    public var edges: List<MaybeUninit<BoxedNode<K, V>>> = edgesCtorArg
        internal set

    public companion object {
        public operator fun <K, V> invoke(
            data: LeafNode<K, V>,
            edges: List<MaybeUninit<BoxedNode<K, V>>>,
        ): InternalNode<K, V> = InternalNode<K, V>(
            data = data,
            edgesCtorArg = edges,
        )

        /**
         * Creates a new boxed `InternalNode`.
         *
         * # Safety
         * An invariant of internal nodes is that they have at least one
         * initialized and valid edge. This function does not set up
         * such an edge.
         */
        public fun <K, V, A : AllocatorClone> new(alloc: A): InternalNode<K, V> {
            val node = Box.newUninitIn(alloc)
            // SAFETY: argument points to the `node.data` `LeafNode`.
            LeafNode.init(addrOfMut(node.asMutPtr().data))
            // SAFETY: `node.data` was just initialized and `node.edges` is MaybeUninit.
            return node.assumeInit()
        }
    }
}

/**
 * A managed, non-null pointer to a node. This is either an owned pointer to
 * `LeafNode<K, V>` or an owned pointer to `InternalNode<K, V>`.
 *
 * However, `BoxedNode` contains no information as to which of the two types
 * of nodes it actually contains, and, partially due to this lack of information,
 * is not a separate type and has no destructor.
 */
public typealias BoxedNode<K, V> = NonNull

// N.B. `NodeRef` is always covariant in `K` and `V`, even when the `BorrowType`
// is `Mut`. This is technically wrong, but cannot result in any unsafety due to
// internal use of `NodeRef` because we stay completely generic over `K` and `V`.
// However, whenever a public type wraps `NodeRef`, make sure that it has the
// correct variance.

/**
 * A reference to a node.
 *
 * This type has a number of parameters that control how it acts:
 * - `BorrowType`: A dummy type that describes the kind of borrow and carries a scope.
 *    - When this is `Immut<>`, the `NodeRef` acts roughly like `Node`.
 *    - When this is `ValMut<>`, the `NodeRef` acts roughly like `Node`
 *      with respect to keys and tree structure, but also allows many
 *      mutable references to values throughout the tree to coexist.
 *    - When this is `Mut<>`, the `NodeRef` acts roughly like `Node`,
 *      although insert methods allow a mutable pointer to a value to coexist.
 *    - When this is `Owned`, the `NodeRef` acts roughly like `Holder<Node>`,
 *      but does not have a destructor, and must be cleaned up manually.
 *    - When this is `Dying`, the `NodeRef` still acts roughly like `Holder<Node>`,
 *      but has methods to destroy the tree bit by bit, and ordinary methods,
 *      while not marked as unchecked to call, can invoke UB if called incorrectly.
 *   Since any `NodeRef` allows navigating through the tree, `BorrowType`
 *   effectively applies to the entire tree, not just to the node itself.
 * - `K` and `V`: These are the types of keys and values stored in the nodes.
 * - `Type`: This can be `Leaf`, `Internal`, or `LeafOrInternal`. When this is
 *   `Leaf`, the `NodeRef` points to a leaf node, when this is `Internal` the
 *   `NodeRef` points to an internal node, and when this is `LeafOrInternal` the
 *   `NodeRef` could be pointing to either type of node.
 *   `Type` is named `NodeType` when used outside `NodeRef`.
 *
 * Both `BorrowType` and `NodeType` restrict what methods we implement, to
 * exploit static type safety. There are limitations in the way we can apply
 * such restrictions:
 * - For each type parameter, we can only define a method either generically
 *   or for one particular type. For example, we cannot define a method like
 *   `intoKv` generically for all `BorrowType`, or once for all types that
 *   carry a scope, because we want it to return  references.
 *   Therefore, we define it only for the least powerful type `Immut<>`.
 * - We cannot get implicit coercion from say `Mut<>` to `Immut<>`.
 *   Therefore, we have to explicitly call `reborrow` on a more powerful
 *   `NodeRef` in order to reach a method like `intoKv`.
 *
 * All methods on `NodeRef` that return some kind of reference, either:
 * - Take `self` by value, and return the scope carried by `BorrowType`.
 *   Sometimes, to invoke such a method, we need to call `reborrowMut`.
 * - Take `self` by reference, and (implicitly) return that reference's
 *   scope, instead of the scope carried by `BorrowType`. That way,
 *   the borrow checker guarantees that the `NodeRef` remains borrowed as long
 *   as the returned reference is used.
 *   The methods supporting insert bend this rule by returning a raw pointer,
 *   i.e., a reference without any scope.
 */
public open class NodeRef<BorrowType, K, V, Type> internal constructor(
    /**
     * The number of levels that the node and the level of leaves are apart, a
     * constant of the node that cannot be entirely described by `Type`, and that
     * the node itself does not store. We only need to store the height of the root
     * node, and derive every other node's height from it.
     * Must be zero if `Type` is `Leaf` and non-zero if `Type` is `Internal`.
     */
    public var height: Int,
    nodeCtorArg: NonNull,
    public var marker: Unit,
) {
    /**
     * The pointer to the leaf or internal node. The definition of `InternalNode`
     * ensures that the pointer is valid either way.
     */
    public var node: NonNull = nodeCtorArg
        internal set

    /**
     * Finds the length of the node. This is the number of keys or values.
     * The number of edges is `len() + 1`.
     * Note that, despite being safe, calling this function can have the side effect
     * of invalidating mutable references that unchecked code has created.
     */
    public open fun len(): Int {
        // SAFETY: We only access the `len` field here. If BorrowType is marker.ValMut,
        // there might be outstanding mutable references to values that we must not invalidate.
        return Int.from((UnresolvedTypeLayout<LeafNode<K, V>>()).readNative((NodeRef.asLeafPtr(this)).rawAddress).len)
    }

    /**
     * Returns the number of levels that the node and leaves are apart. Zero
     * height means the node is a leaf itself. If you picture trees with the
     * root on top, the number says at which elevation the node appears.
     * If you picture trees with leaves on top, the number says how high
     * the tree extends above the node.
     */
    public open fun height(): Int = this.height

    /**
     * Temporarily takes out another, immutable reference to the same node.
     */
    public open fun reborrow(): NodeRef<Immut, K, V, Type> = NodeRef(height = this.height, node = this.node, marker = Unit)

    /**
     * Finds the parent of the current node. Returns `Ok(handle)` if the current
     * node actually has a parent, where `handle` points to the edge of the parent
     * that points to the current node. Returns `Err(self)` if the current node has
     * no parent, giving back the original `NodeRef`.
     *
     * The method name assumes you picture trees with the root node on top.
     *
     * `edge.descend().ascend().unwrap()` and `node.ascend().unwrap().descend()` should
     * both, upon success, do nothing.
     */
    public open fun ascend(): Result<Handle<NodeRef<BorrowType, K, V, Internal>, Edge>, NodeRef<BorrowType, K, V, Type>> {
        run {
            check(BorrowType.TRAVERSAL_PERMIT)
        }
        // We need to use raw pointers to nodes because, if BorrowType is marker.ValMut,
        // there might be outstanding mutable references to values that we must not invalidate.
        val leafPtr: ConstPtr = NodeRef.asLeafPtr(this)
        // ignore-tidy-undocumented-unchecked
        return ((UnresolvedTypeLayout<Any?>()).readNative((leafPtr).rawAddress))!!.parent.asRef().map({ parent -> Handle(node = NodeRef.fromInternal(parent, this.height + 1), idx = Int.from(((UnresolvedTypeLayout<Any?>()).readNative((leafPtr).rawAddress))!!.parentIdx.assumeInit()), marker = Unit) }).okOr(this)
    }

    public open fun firstEdge(): Handle<NodeRef<BorrowType, K, V, Type>, Edge> {
        // ignore-tidy-undocumented-unchecked
        return Handle.newEdge(this, 0)
    }

    public open fun lastEdge(): Handle<NodeRef<BorrowType, K, V, Type>, Edge> {
        val len = this.len()
        // ignore-tidy-undocumented-unchecked
        return Handle.newEdge(this, len)
    }

    /**
     * Note that `self` must be nonempty.
     */
    public open fun firstKv(): Handle<NodeRef<BorrowType, K, V, Type>, KV> {
        val len = this.len()
        check(len > 0)
        // ignore-tidy-undocumented-unchecked
        return Handle.newKv(this, 0)
    }

    /**
     * Note that `self` must be nonempty.
     */
    public open fun lastKv(): Handle<NodeRef<BorrowType, K, V, Type>, KV> {
        val len = this.len()
        check(len > 0)
        // ignore-tidy-undocumented-unchecked
        return Handle.newKv(this, len - 1)
    }

    /**
     * Could be a public implementation of PartialEq, but only used in this module.
     */
    public open fun eq(other: NodeRef<BorrowType, K, V, Type>): Boolean {
        val node = this.node
        val height = this.height
        val marker = this.marker
        return if (node.eq(other.node)) {
            check(height == other.height)
            true
        } else {
            false
        }
    }

    public companion object {
        public operator fun <BorrowType, K, V, Type> invoke(
            height: Int,
            node: NonNull,
            marker: Unit,
        ): NodeRef<BorrowType, K, V, Type> = NodeRef<BorrowType, K, V, Type>(
            height = height,
            nodeCtorArg = node,
            marker = marker,
        )

        /**
         * Exposes the leaf portion of any leaf or internal node.
         *
         * Returns a raw ptr to avoid invalidating other references to this node.
         */
        public fun <BorrowType, K, V, Type> asLeafPtr(thisRef: NodeRef<BorrowType, K, V, Type>): MutPtr {
            // The node must be valid for at least the LeafNode portion.
            // This is not a reference in the NodeRef type because we don't know if
            // it should be unique or shared.
            return thisRef.node.asPtr()
        }
    }
}

/**
 * The root node of an owned tree.
 *
 * Note that this does not have a destructor, and must be cleaned up manually.
 */
public typealias Root<K, V> = NodeRef<Owned, K, V, LeafOrInternal>

public fun <K, V, Type> NodeRef<Immut, K, V, Type>.clone(): NodeRef<Immut, K, V, Type> = NodeRef(this.height, this.node, this.marker)

public fun <K, V, A : AllocatorClone> NodeRef.Companion.newLeaf(alloc: A): NodeRef<Owned, K, V, Leaf> = NodeRef.fromNewLeaf(LeafNode.new(alloc))

public fun <K, V, A : AllocatorClone> NodeRef.Companion.fromNewLeaf(leaf: LeafNode<K, V>): NodeRef<Owned, K, V, Leaf> {
    // The allocator must be dropped, not leaked.  See also `BTreeMap.alloc`.
    val (node, alloc) = Box.intoNonNullWithAllocator(leaf)
    return NodeRef(height = 0, node = node, marker = Unit)
}

/**
 * Creates a new internal (height > 0) `NodeRef`
 */
public fun <K, V, A : AllocatorClone> NodeRef.Companion.newInternal(child: Root<K, V>, alloc: A): NodeRef<Owned, K, V, Internal> {
    // ignore-tidy-undocumented-unchecked
    val newNode = InternalNode.new(alloc)
    newNode.edges[0].write(child.node)
    return NodeRef.fromNewInternal(newNode, NonZero.new(child.height + 1)!!)
}

/**
 * Creates a new internal (height > 0) `NodeRef` from an existing internal node
 */
public fun <K, V, A : AllocatorClone> NodeRef.Companion.fromNewInternal(internal: InternalNode<K, V>, height: NonZero<Int, NonZeroUsizeInner>): NodeRef<Owned, K, V, Internal> {
    // The allocator must be dropped, not leaked.  See also `BTreeMap.alloc`.
    val (node, alloc) = Box.intoNonNullWithAllocator(internal)
    val thisRef = NodeRef(height = height.into(), node = node.cast(), marker = Unit)
    thisRef.borrowMut().correctAllChildrensParentLinks()
    return thisRef
}

/**
 * Unpack a node reference that was packed as `NodeRef.parent`.
 */
public fun <BorrowType, K, V> NodeRef.Companion.fromInternal(node: NonNull, height: Int): NodeRef<BorrowType, K, V, Internal> {
    check(height > 0)
    return NodeRef(height = height, node = node.cast(), marker = Unit)
}

/**
 * Exposes the data of an internal node.
 *
 * Returns a raw ptr to avoid invalidating other references to this node.
 */
public fun <BorrowType, K, V> NodeRef.Companion.asInternalPtr(thisRef: NodeRef<BorrowType, K, V, Internal>): MutPtr {
    // SAFETY: the static node type is `Internal`.
    return mutPtrFromRustCast(thisRef.node.asPtr())
}

/**
 * Borrows exclusive access to the data of an internal node.
 */
public fun <K, V> NodeRef<Mut, K, V, Internal>.asInternalMut(): InternalNode<K, V> {
    val ptr = NodeRef.asInternalPtr(this)
    // ignore-tidy-undocumented-unchecked
    return ptr
}

/**
 * Exposes the leaf portion of any leaf or internal node in an immutable tree.
 */
public fun <K, V, Type> NodeRef<Immut, K, V, Type>.intoLeaf(): LeafNode<K, V> {
    val ptr = NodeRef.asLeafPtr(this)
    // SAFETY: there can be no mutable references into this tree borrowed as `Immut`.
    return (UnresolvedTypeLayout<LeafNode<K, V>>()).readNative((ptr).rawAddress)
}

/**
 * Borrows a view into the keys stored in the node.
 */
public fun <K, V, Type> NodeRef<Immut, K, V, Type>.keys(): List<K> {
    val leaf = this.intoLeaf()
    // ignore-tidy-undocumented-unchecked
    return leaf.keys.getUnchecked(0 until Int.from(leaf.len)).assumeInitRef()
}

/**
 * Similar to `ascend`, gets a reference to a node's parent node, but also
 * deallocates the current node in the process. This is unchecked because the
 * current node will still be accessible despite being deallocated.
 */
public fun <K, V, A : AllocatorClone> NodeRef<Dying, K, V, LeafOrInternal>.deallocateAndAscend(alloc: A, typeLayoutK: TypeLayout<K> = UnresolvedTypeLayout(), typeLayoutV: TypeLayout<V> = UnresolvedTypeLayout()): Handle<NodeRef<Dying, K, V, Internal>, Edge>? {
    val height = this.height
    val node = this.node
    val ret = this.ascend().ok()
    // ignore-tidy-undocumented-unchecked
    run {
        alloc.deallocate(node.cast(), if (height > 0) {
            Layout.new<InternalNode<K, V>>(typeLayoutT = UnresolvedTypeLayout<InternalNode<K, V>>())
        } else {
            Layout.new<LeafNode<K, V>>(typeLayoutT = UnresolvedTypeLayout<LeafNode<K, V>>())
        })
    }
    return ret
}

/**
 * Drops all kvs in this node without deallocating the node itself
 *
 * # Safety
 *
 * - kvs in the node must be initialized and must not be accessed after this call.
 * - No kvs in the node can be accessed through any other path while this function is running.
 */
public fun <K, V> NodeRef<Dying, K, V, LeafOrInternal>.dropKvs() {
    open class DropGuard<K, V>(
        public var node: NodeRef<Dying, K, V, LeafOrInternal>,
        public var idx: Int,
    ) {
        public open fun drop() {
            val len = this.node.len()
            for (i in this.idx until len) {
                // SAFETY: i < len and the kv has been initialized and
                // it won't use the i-th kv more than once.
                run {
                    val kv = Handle.newKv(read(this.node, typeLayoutT = UnresolvedTypeLayout<NonNull>()), i)
                    kv.dropKeyVal()
                }
            }
        }
    }
    val len = this.len()
    for (idx in 0 until len) {
        // If `kv.dropKeyVal()` panics, the guard would drop
        // the rest and skip the panicked kv.
        val guard = DropGuard(node = this, idx = idx + 1)
        // SAFETY: idx < len and the kv has been initialized and
        // it won't touch the idx-th more than once
        run {
            val kv = Handle.newKv(read(this), idx)
            kv.dropKeyVal()
        }
        forget(guard)
    }
}

// Note(@gereeter) consider adding yet another type parameter to `NodeRef`
// that restricts the use of navigation methods on reborrowed pointers,
// preventing this unsafety.

/**
 * Temporarily takes out another mutable reference to the same node. Beware, as
 * this method is very dangerous, doubly so since it might not immediately appear
 * dangerous.
 *
 * Because mutable pointers can roam anywhere around the tree, the returned
 * pointer can easily be used to make the original pointer dangling, out of
 * bounds, or invalid under stacked borrow rules.
 */
public fun <K, V, Type> NodeRef<Mut, K, V, Type>.reborrowMut(): NodeRef<Mut, K, V, Type> = NodeRef(height = this.height, node = this.node, marker = Unit)

/**
 * Borrows exclusive access to the leaf portion of a leaf or internal node.
 */
public fun <K, V, Type> NodeRef<Mut, K, V, Type>.asLeafMut(): LeafNode<K, V> {
    val ptr = NodeRef.asLeafPtr(this)
    // SAFETY: we have exclusive access to the entire node.
    return RustMutableReference(MutPtr((ptr).rawAddress), UnresolvedTypeLayout<LeafNode<K, V>>())
}

/**
 * Offers exclusive access to the leaf portion of a leaf or internal node.
 */
public fun <K, V, Type> NodeRef<Mut, K, V, Type>.intoLeafMut(): LeafNode<K, V> {
    val ptr = NodeRef.asLeafPtr(this)
    // SAFETY: we have exclusive access to the entire node.
    return RustMutableReference(MutPtr((ptr).rawAddress), UnresolvedTypeLayout<LeafNode<K, V>>())
}

/**
 * Returns a dormant copy of this node with its scope erased which can
 * be reawakened later.
 */
public fun <K, V, Type> NodeRef<Mut, K, V, Type>.dormant(): NodeRef<DormantMut, K, V, Type> = NodeRef(height = this.height, node = this.node, marker = Unit)

/**
 * Revert to the unique borrow initially captured.
 *
 * # Safety
 *
 * The reborrow must have ended, i.e., the reference returned by `new` and
 * all pointers and references derived from it, must not be used anymore.
 */
public fun <K, V, Type> NodeRef<DormantMut, K, V, Type>.awaken(): NodeRef<Mut, K, V, Type> = NodeRef(height = this.height, node = this.node, marker = Unit)

/**
 * Borrows exclusive access to the leaf portion of a dying leaf or internal node.
 */
public fun <K, V, Type> NodeRef<Dying, K, V, Type>.asLeafDying(): LeafNode<K, V> {
    val ptr = NodeRef.asLeafPtr(this)
    // SAFETY: we have exclusive access to the entire node.
    return RustMutableReference(MutPtr((ptr).rawAddress), UnresolvedTypeLayout<LeafNode<K, V>>())
}

/**
 * Borrows exclusive access to an element of the key storage area.
 *
 * # Safety
 * `index` is in bounds of 0..CAPACITY
 */
public fun <K, V, Type, I, Output> NodeRef<Mut, K, V, Type>.keyAreaMut(index: I): Output where I : SliceIndex<List<MaybeUninit<K>>, Output> {
    // SAFETY: the caller will not be able to call further methods on self
    // until the key slice reference is dropped, as we have unique access
    // for the scope of the borrow.
    return this.asLeafMut().keys.asMutSlice().getUncheckedMut(index)
}

/**
 * Borrows exclusive access to an element or slice of the node's value storage area.
 *
 * # Safety
 * `index` is in bounds of 0..CAPACITY
 */
public fun <K, V, Type, I, Output> NodeRef<Mut, K, V, Type>.valAreaMut(index: I): Output where I : SliceIndex<List<MaybeUninit<V>>, Output> {
    // SAFETY: the caller will not be able to call further methods on self
    // until the value slice reference is dropped, as we have unique access
    // for the scope of the borrow.
    return this.asLeafMut().vals.asMutSlice().getUncheckedMut(index)
}

/**
 * Borrows exclusive access to an element or slice of the node's storage area for edge contents.
 *
 * # Safety
 * `index` is in bounds of 0..CAPACITY + 1
 */
public fun <K, V, I, Output> NodeRef<Mut, K, V, Internal>.edgeAreaMut(index: I): Output where I : SliceIndex<List<MaybeUninit<BoxedNode<K, V>>>, Output> {
    // SAFETY: the caller will not be able to call further methods on self
    // until the edge slice reference is dropped, as we have unique access
    // for the scope of the borrow.
    return this.asInternalMut().edges.asMutSlice().getUncheckedMut(index)
}

/**
 * # Safety
 * - The node has more than `idx` initialized elements.
 */
public fun <K, V, Type> NodeRef<ValMut, K, V, Type>.intoKeyValMutAt(idx: Int): Pair<K, V> {
    // We only create a reference to the one element we are interested in,
    // to avoid aliasing with outstanding references to other elements,
    // in particular, those returned to the caller in earlier iterations.
    val leaf = NodeRef.asLeafPtr(this)
    // ignore-tidy-undocumented-unchecked
    val keys = addrOf((UnresolvedTypeLayout<LeafNode<K, V>>()).readNative((leaf).rawAddress).keys)
    // ignore-tidy-undocumented-unchecked
    val vals = addrOfMut((UnresolvedTypeLayout<LeafNode<K, V>>()).readNative((leaf).rawAddress).vals)
    // We must coerce to unsized array pointers because of Rust issue #74679.
    val keys1: ConstPtr = keys
    val vals1: MutPtr = vals
    // ignore-tidy-undocumented-unchecked
    val key = (keys1.getUnchecked(idx)).assumeInitRef()
    // ignore-tidy-undocumented-unchecked
    val value = (vals1.getUncheckedMut(idx)).assumeInitMut()
    return Pair(key, value)
}

/**
 * Borrows exclusive access to the length of the node.
 */
public fun <K, V, Type> NodeRef<Mut, K, V, Type>.lenMut(): RustMutableReference<UShort> = this.asLeafMut().len

/**
 * # Safety
 * Every item returned by `range` is a valid edge index for the node.
 */
public fun <K, V, R : Iterator<Int>> NodeRef<Mut, K, V, Internal>.correctChildrensParentLinks(range: R) {
    for (i in range) {
        check(i <= this.len())
        // ignore-tidy-undocumented-unchecked
        Handle.newEdge(this.reborrowMut(), i).correctParentLink()
    }
}

public fun <K, V> NodeRef<Mut, K, V, Internal>.correctAllChildrensParentLinks() {
    val len = this.len()
    // ignore-tidy-undocumented-unchecked
    this.correctChildrensParentLinks(0..len)
}

/**
 * Sets the node's link to its parent edge,
 * without invalidating other references to the node.
 */
public fun <K, V> NodeRef<Mut, K, V, LeafOrInternal>.setParentLink(parent: NonNull, parentIdx: Int) {
    val leaf = NodeRef.asLeafPtr(this)
    // ignore-tidy-undocumented-unchecked
    (UnresolvedTypeLayout<LeafNode<K, V>>()).readNative((leaf).rawAddress).parent = parent
    // ignore-tidy-undocumented-unchecked
    (UnresolvedTypeLayout<LeafNode<K, V>>()).readNative((leaf).rawAddress).parentIdx.write(parentIdx.toUShort())
}

/**
 * Clears the root's link to its parent edge.
 */
public fun <K, V> NodeRef<Owned, K, V, LeafOrInternal>.clearParentLink() {
    val rootNode = this.borrowMut()
    val leaf = rootNode.asLeafMut()
    leaf.parent = null
}

/**
 * Returns a new owned tree, with its own root node that is initially empty.
 */
public fun <K, V, A : AllocatorClone> NodeRef.Companion.new(alloc: A): NodeRef<Owned, K, V, LeafOrInternal> = NodeRef.newLeaf(alloc).forgetType()

/**
 * Adds a new internal node with a single edge pointing to the previous root node,
 * make that new node the root node, and return it. This increases the height by 1
 * and is the opposite of `popInternalLevel`.
 */
public fun <K, V, A : AllocatorClone> NodeRef<Owned, K, V, LeafOrInternal>.pushInternalLevel(alloc: A): NodeRef<Mut, K, V, Internal> {
    takeMut(this, { oldRoot -> NodeRef.newInternal(oldRoot, alloc).forgetType() })
    // `self.borrowMut()`, except that we just forgot we're internal now:
    return NodeRef(height = this.height, node = this.node, marker = Unit)
}

/**
 * Removes the internal root node, using its first child as the new root node.
 * As it is intended only to be called when the root node has only one child,
 * no cleanup is done on any of the keys, values and other children.
 * This decreases the height by 1 and is the opposite of `pushInternalLevel`.
 *
 * Does not invalidate any handles or references pointing into the subtree
 * rooted at the first child of `self`.
 *
 * Panics if there is no internal level, i.e., if the root node is a leaf.
 */
public fun <K, V, A : AllocatorClone> NodeRef<Owned, K, V, LeafOrInternal>.popInternalLevel(alloc: A, typeLayoutK: TypeLayout<K> = UnresolvedTypeLayout(), typeLayoutV: TypeLayout<V> = UnresolvedTypeLayout()) {
    check(this.height > 0)
    val top = this.node
    // SAFETY: we asserted to be internal.
    val internalSelf = this.borrowMut().castToInternalUnchecked()
    val internalNode = internalSelf.asInternalMut()
    // SAFETY: the first edge is always initialized.
    this.node = internalNode.edges[0].assumeInitRead()
    this.height -= 1
    this.clearParentLink()
    // ignore-tidy-undocumented-unchecked
    run {
        alloc.deallocate(top.cast(), Layout.new<InternalNode<K, V>>(typeLayoutT = UnresolvedTypeLayout<InternalNode<K, V>>()))
    }
}

/**
 * Mutably borrows the owned root node. Unlike `reborrowMut`, this is safe
 * because the return value cannot be used to destroy the root, and there
 * cannot be other references to the tree.
 */
public fun <K, V, Type> NodeRef<Owned, K, V, Type>.borrowMut(): NodeRef<Mut, K, V, Type> = NodeRef(height = this.height, node = this.node, marker = Unit)

/**
 * Slightly mutably borrows the owned root node.
 */
public fun <K, V, Type> NodeRef<Owned, K, V, Type>.borrowValmut(): NodeRef<ValMut, K, V, Type> = NodeRef(height = this.height, node = this.node, marker = Unit)

/**
 * Irreversibly transitions to a reference that permits traversal and offers
 * destructive methods and little else.
 */
public fun <K, V, Type> NodeRef<Owned, K, V, Type>.intoDying(): NodeRef<Dying, K, V, Type> = NodeRef(height = this.height, node = this.node, marker = Unit)

/**
 * Adds a key-value pair to the end of the node, and returns
 * a handle to the inserted value.
 *
 * # Safety
 *
 * The returned handle has an unbound scope.
 */
public fun <K, V> NodeRef<Mut, K, V, Leaf>.pushWithHandle(key: K, value: V): Handle<NodeRef<Mut, K, V, Leaf>, KV> {
    val len = this.lenMut()
    val idx = Int.from((len).read())
    check(idx < CAPACITY)
    (len).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
    // ignore-tidy-undocumented-unchecked
    return run {
        this.keyAreaMut(idx).write(key)
        this.valAreaMut(idx).write(value)
        Handle.newKv(NodeRef(height = this.height, node = this.node, marker = Unit), idx)
    }
}

/**
 * Adds a key-value pair to the end of the node, and returns
 * the mutable reference of the inserted value.
 */
public fun <K, V> NodeRef<Mut, K, V, Leaf>.push(key: K, value: V): MutPtr {
    // SAFETY: The unbound handle is no longer accessible.
    return this.pushWithHandle(key, value).intoValMut()
}

/**
 * Adds a key-value pair, and an edge to go to the right of that pair,
 * to the end of the node.
 */
public fun <K, V> NodeRef<Mut, K, V, Internal>.push(key: K, value: V, edge: Root<K, V>) {
    check(edge.height == this.height - 1)
    val len = this.lenMut()
    val idx = Int.from((len).read())
    check(idx < CAPACITY)
    (len).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
    // ignore-tidy-undocumented-unchecked
    run {
        this.keyAreaMut(idx).write(key)
        this.valAreaMut(idx).write(value)
        this.edgeAreaMut(idx + 1).write(edge.node)
        Handle.newEdge(this.reborrowMut(), idx + 1).correctParentLink()
    }
}

/**
 * Removes any static information asserting that this node is a `Leaf` node.
 */
public fun <BorrowType, K, V> NodeRef<BorrowType, K, V, Leaf>.forgetType(): NodeRef<BorrowType, K, V, LeafOrInternal> = NodeRef(height = this.height, node = this.node, marker = Unit)

/**
 * Checks whether a node is an `Internal` node or a `Leaf` node.
 */
public fun <BorrowType, K, V> NodeRef<BorrowType, K, V, LeafOrInternal>.force(): ForceResult<NodeRef<BorrowType, K, V, Leaf>, NodeRef<BorrowType, K, V, Internal>> =
    if (this.height == 0) {
        ForceResult.Leaf(NodeRef(height = this.height, node = this.node, marker = Unit))
    } else {
        ForceResult.Internal(NodeRef(height = this.height, node = this.node, marker = Unit))
    }

/**
 * Unsafely asserts to the compiler the static information that this node is a `Leaf`.
 */
public fun <K, V> NodeRef<Mut, K, V, LeafOrInternal>.castToLeafUnchecked(): NodeRef<Mut, K, V, Leaf> {
    check(this.height == 0)
    return NodeRef(height = this.height, node = this.node, marker = Unit)
}

/**
 * Unsafely asserts to the compiler the static information that this node is an `Internal`.
 */
public fun <K, V> NodeRef<Mut, K, V, LeafOrInternal>.castToInternalUnchecked(): NodeRef<Mut, K, V, Internal> {
    check(this.height > 0)
    return NodeRef(height = this.height, node = this.node, marker = Unit)
}

/**
 * A reference to a specific key-value pair or edge within a node. The `Node` parameter
 * must be a `NodeRef`, while the `Type` can either be `KV` (signifying a handle on a key-value
 * pair) or `Edge` (signifying a handle on an edge).
 *
 * Note that even `Leaf` nodes can have `Edge` handles. Instead of representing a pointer to
 * a child node, these represent the spaces where child pointers would go between the key-value
 * pairs. For example, in a node with length 2, there would be 3 possible edge locations - one
 * to the left of the node, one between the two pairs, and one at the right of the node.
 */
public open class Handle<Node, Type>(
    public var node: Node,
    public var idx: Int,
    public var marker: Unit,
) {
    // We don't need the full generality of `@derive(Clone)`, as the only time `Node` will be
    // `Clone`able is when it is an immutable reference and therefore `Copy`.
    public open fun clone(): Handle<Node, Type> = Handle(this.node, this.idx, this.marker)

    /**
     * Retrieves the node that contains the edge or key-value pair this handle points to.
     */
    public open fun intoNode(): Node = this.node

    /**
     * Returns the position of this handle in the node.
     */
    public open fun idx(): Int = this.idx
}

/**
 * Creates a new handle to a key-value pair in `node`.
 * Unsafe because the caller must ensure that `idx<node.len()`.
 */
public fun <BorrowType, K, V, NodeType> Handle.Companion.newKv(node: NodeRef<BorrowType, K, V, NodeType>, idx: Int): Handle<NodeRef<BorrowType, K, V, NodeType>, KV> {
    check(idx < node.len())
    return Handle(node = node, idx = idx, marker = Unit)
}

public fun <BorrowType, K, V, NodeType> Handle<NodeRef<BorrowType, K, V, NodeType>, KV>.leftEdge(): Handle<NodeRef<BorrowType, K, V, NodeType>, Edge> {
    // ignore-tidy-undocumented-unchecked
    return Handle.newEdge(this.node, this.idx)
}

public fun <BorrowType, K, V, NodeType> Handle<NodeRef<BorrowType, K, V, NodeType>, KV>.rightEdge(): Handle<NodeRef<BorrowType, K, V, NodeType>, Edge> {
    // ignore-tidy-undocumented-unchecked
    return Handle.newEdge(this.node, this.idx + 1)
}

public fun <BorrowType, K, V, NodeType, HandleType> Handle<NodeRef<BorrowType, K, V, NodeType>, HandleType>.eq(other: Handle<NodeRef<BorrowType, K, V, NodeType>, HandleType>): Boolean {
    val node = this.node
    val idx = this.idx
    val marker = this.marker
    return node.eq(other.node) && idx == other.idx
}

/**
 * Temporarily takes out another immutable handle on the same location.
 */
public fun <BorrowType, K, V, NodeType, HandleType> Handle<NodeRef<BorrowType, K, V, NodeType>, HandleType>.reborrow(): Handle<NodeRef<Immut, K, V, NodeType>, HandleType> {
    // We can't use Handle.newKv or Handle.newEdge because we don't know our type
    return Handle(node = this.node.reborrow(), idx = this.idx, marker = Unit)
}

/**
 * Temporarily takes out another mutable handle on the same location. Beware, as
 * this method is very dangerous, doubly so since it might not immediately appear
 * dangerous.
 *
 * For details, see `NodeRef.reborrowMut`.
 */
public fun <K, V, NodeType, HandleType> Handle<NodeRef<Mut, K, V, NodeType>, HandleType>.reborrowMut(): Handle<NodeRef<Mut, K, V, NodeType>, HandleType> {
    // We can't use Handle.newKv or Handle.newEdge because we don't know our type
    // ignore-tidy-undocumented-unchecked
    return Handle(node = this.node.reborrowMut(), idx = this.idx, marker = Unit)
}

/**
 * Returns a dormant copy of this handle which can be reawakened later.
 *
 * See `DormantMutRef` for more details.
 */
public fun <K, V, NodeType, HandleType> Handle<NodeRef<Mut, K, V, NodeType>, HandleType>.dormant(): Handle<NodeRef<DormantMut, K, V, NodeType>, HandleType> = Handle(node = this.node.dormant(), idx = this.idx, marker = Unit)

/**
 * Revert to the unique borrow initially captured.
 *
 * # Safety
 *
 * The reborrow must have ended, i.e., the reference returned by `new` and
 * all pointers and references derived from it, must not be used anymore.
 */
public fun <K, V, NodeType, HandleType> Handle<NodeRef<DormantMut, K, V, NodeType>, HandleType>.awaken(): Handle<NodeRef<Mut, K, V, NodeType>, HandleType> {
    // ignore-tidy-undocumented-unchecked
    return Handle(node = this.node.awaken(), idx = this.idx, marker = Unit)
}

/**
 * Creates a new handle to an edge in `node`.
 * Unsafe because the caller must ensure that `idx <= node.len()`.
 */
public fun <BorrowType, K, V, NodeType> Handle.Companion.newEdge(node: NodeRef<BorrowType, K, V, NodeType>, idx: Int): Handle<NodeRef<BorrowType, K, V, NodeType>, Edge> {
    check(idx <= node.len())
    return Handle(node = node, idx = idx, marker = Unit)
}

public fun <BorrowType, K, V, NodeType> Handle<NodeRef<BorrowType, K, V, NodeType>, Edge>.leftKv(): Result<Handle<NodeRef<BorrowType, K, V, NodeType>, KV>, Handle<NodeRef<BorrowType, K, V, NodeType>, Edge>> =
    if (this.idx > 0) {
        // ignore-tidy-undocumented-unchecked
        Result.Ok<Handle<NodeRef<BorrowType, K, V, NodeType>, KV>, Handle<NodeRef<BorrowType, K, V, NodeType>, Edge>>(Handle.newKv(this.node, this.idx - 1))
    } else {
        Result.Err<Handle<NodeRef<BorrowType, K, V, NodeType>, KV>, Handle<NodeRef<BorrowType, K, V, NodeType>, Edge>>(this)
    }

public fun <BorrowType, K, V, NodeType> Handle<NodeRef<BorrowType, K, V, NodeType>, Edge>.rightKv(): Result<Handle<NodeRef<BorrowType, K, V, NodeType>, KV>, Handle<NodeRef<BorrowType, K, V, NodeType>, Edge>> =
    if (this.idx < this.node.len()) {
        // ignore-tidy-undocumented-unchecked
        Result.Ok<Handle<NodeRef<BorrowType, K, V, NodeType>, KV>, Handle<NodeRef<BorrowType, K, V, NodeType>, Edge>>(Handle.newKv(this.node, this.idx))
    } else {
        Result.Err<Handle<NodeRef<BorrowType, K, V, NodeType>, KV>, Handle<NodeRef<BorrowType, K, V, NodeType>, Edge>>(this)
    }

public sealed class LeftOrRight<T> {
    public data class Left<T>(public var value: T) : LeftOrRight<T>()
    public data class Right<T>(public var value: T) : LeftOrRight<T>()
}

/**
 * Given an edge index where we want to insert into a node filled to capacity,
 * computes a sensible KV index of a split point and where to perform the insertion.
 * The goal of the split point is for its key and value to end up in a parent node;
 * the keys, values and edges to the left of the split point become the left child;
 * the keys, values and edges to the right of the split point become the right child.
 */
public fun splitpoint(edgeIdx: Int): Pair<Int, LeftOrRight<Int>> {
    check(edgeIdx <= CAPACITY)
    // Rust issue #74834 tries to explain these symmetric rules.
    return when (edgeIdx) {
        in 0 until EDGE_IDX_LEFT_OF_CENTER -> Pair(KV_IDX_CENTER - 1, LeftOrRight.Left(edgeIdx))
        EDGE_IDX_LEFT_OF_CENTER -> Pair(KV_IDX_CENTER, LeftOrRight.Left(edgeIdx))
        EDGE_IDX_RIGHT_OF_CENTER -> Pair(KV_IDX_CENTER, LeftOrRight.Right(0))
        else -> Pair(KV_IDX_CENTER + 1, LeftOrRight.Right(edgeIdx - (KV_IDX_CENTER + 1 + 1)))
    }
}

/**
 * Inserts a new key-value pair between the key-value pairs to the right and left of
 * this edge. This method assumes that there is enough space in the node for the new
 * pair to fit.
 */
public fun <K, V> Handle<NodeRef<Mut, K, V, Leaf>, Edge>.insertFit(key: K, value: V): Handle<NodeRef<Mut, K, V, Leaf>, KV> {
    check(this.node.len() < CAPACITY)
    val newLen = this.node.len() + 1
    // ignore-tidy-undocumented-unchecked
    return run {
        sliceInsert(this.node.keyAreaMut(0 until newLen), this.idx, key)
        sliceInsert(this.node.valAreaMut(0 until newLen), this.idx, value)
        this.node.lenMut() = newLen.toUShort()
        Handle.newKv(this.node, this.idx)
    }
}

/**
 * Inserts a new key-value pair between the key-value pairs to the right and left of
 * this edge. This method splits the node if there isn't enough room.
 *
 * Returns a dormant handle to the inserted node which can be reawakened
 * once splitting is complete.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, Leaf>, Edge>.insert(key: K, value: V, alloc: A): Pair<SplitResult<K, V, Leaf>?, Handle<NodeRef<DormantMut, K, V, Leaf>, KV>> =
    if (this.node.len() < CAPACITY) {
        // SAFETY: There is enough space in the node for insertion.
        val handle = this.insertFit(key, value)
        Pair(null, handle.dormant())
    } else {
        val (middleKvIdx, insertion) = splitpoint(this.idx)
        // ignore-tidy-undocumented-unchecked
        val middle = Handle.newKv(this.node, middleKvIdx)
        val result = middle.split(alloc)
        val insertionEdge =
            when (insertion) {
                // ignore-tidy-undocumented-unchecked
                is LeftOrRight.Left<*> -> {
                    val (insertIdx) = insertion
                    Handle.newEdge(result.left.reborrowMut(), insertIdx)
                }
                // ignore-tidy-undocumented-unchecked
                is LeftOrRight.Right<*> -> {
                    val (insertIdx1) = insertion
                    Handle.newEdge(result.right.borrowMut(), insertIdx1)
                }
            }
        // SAFETY: We just split the node, so there is enough space for
        // insertion.
        val handle1 = insertionEdge.insertFit(key, value).dormant()
        Pair(result, handle1)
    }

/**
 * Fixes the parent pointer and index in the child node that this edge
 * links to. This is useful when the ordering of edges has been changed,
 */
public fun <K, V> Handle<NodeRef<Mut, K, V, Internal>, Edge>.correctParentLink() {
    // Create backpointer without invalidating other references to the node.
    // ignore-tidy-undocumented-unchecked
    val ptr = NonNull.newUnchecked(NodeRef.asInternalPtr(this.node))
    val idx = this.idx
    val child = this.descend()
    child.setParentLink(ptr, idx)
}

/**
 * Inserts a new key-value pair and an edge that will go to the right of that new pair
 * between this edge and the key-value pair to the right of this edge. This method assumes
 * that there is enough space in the node for the new pair to fit.
 */
public fun <K, V> Handle<NodeRef<Mut, K, V, Internal>, Edge>.insertFit(key: K, value: V, edge: Root<K, V>) {
    check(this.node.len() < CAPACITY)
    check(edge.height == this.node.height - 1)
    val newLen = this.node.len() + 1
    // ignore-tidy-undocumented-unchecked
    run {
        sliceInsert(this.node.keyAreaMut(0 until newLen), this.idx, key)
        sliceInsert(this.node.valAreaMut(0 until newLen), this.idx, value)
        sliceInsert(this.node.edgeAreaMut(0 until newLen + 1), this.idx + 1, edge.node)
        this.node.lenMut() = newLen.toUShort()
        this.node.correctChildrensParentLinks(this.idx + 1 until newLen + 1)
    }
}

/**
 * Inserts a new key-value pair and an edge that will go to the right of that new pair
 * between this edge and the key-value pair to the right of this edge. This method splits
 * the node if there isn't enough room.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, Internal>, Edge>.insert(key: K, value: V, edge: Root<K, V>, alloc: A): SplitResult<K, V, Internal>? {
    check(edge.height == this.node.height - 1)
    return if (this.node.len() < CAPACITY) {
        this.insertFit(key, value, edge)
        null
    } else {
        val (middleKvIdx, insertion) = splitpoint(this.idx)
        // ignore-tidy-undocumented-unchecked
        val middle = Handle.newKv(this.node, middleKvIdx)
        val result = middle.split(alloc)
        var insertionEdge =
            when (insertion) {
                // ignore-tidy-undocumented-unchecked
                is LeftOrRight.Left<*> -> {
                    val (insertIdx) = insertion
                    Handle.newEdge(result.left.reborrowMut(), insertIdx)
                }
                // ignore-tidy-undocumented-unchecked
                is LeftOrRight.Right<*> -> {
                    val (insertIdx1) = insertion
                    Handle.newEdge(result.right.borrowMut(), insertIdx1)
                }
            }
        insertionEdge.insertFit(key, value, edge)
        result
    }
}

/**
 * Inserts a new key-value pair between the key-value pairs to the right and left of
 * this edge. This method splits the node if there isn't enough room, and tries to
 * insert the split off portion into the parent node recursively, until the root is reached.
 *
 * If the returned result is some `SplitResult`, the `left` field will be the root node.
 * The returned pointer points to the inserted value, which in the case of `SplitResult`
 * is in the `left` or `right` tree.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, Leaf>, Edge>.insertRecursing(key: K, value: V, alloc: A, splitRoot: (SplitResult<K, V, LeafOrInternal>) -> Unit): Handle<NodeRef<Mut, K, V, Leaf>, KV> {
    var (split1, handle2) =
        run {
            val matchVal = this.insert(key, value, alloc.clone())
            when {
                // SAFETY: we have finished splitting and can now re-awaken the
                // handle to the inserted element.
                matchVal.first == null -> {
                    val handle = matchVal.second
                    return handle.awaken()
                }
                else -> {
                    val split = matchVal.first
                    val handle1 = matchVal.second
                    Pair(split.forgetNodeType(), handle1)
                }
            }
        }
    while (true) {
        split1 = run {
            val matchVal1 = split1.left.ascend()
            when (matchVal1) { is Result.Ok<*, *> -> {
                val parent = matchVal1.okValue()
                when (val matchVal2 = parent.insert(split1.kv.first, split1.kv.second, split1.right, alloc.clone())) {
                    // SAFETY: we have finished splitting and can now re-awaken the
                    // handle to the inserted element.
                    null -> { return handle2.awaken() }
                    else -> {
                        val split2 = matchVal2
                        split2.forgetNodeType()
                    }
                }
            }; is Result.Err<*, *> -> {
                val root = matchVal1.errValue()
                splitRoot(split1.copy(left = root))
                // SAFETY: we have finished splitting and can now re-awaken the
                // handle to the inserted element.
                return handle2.awaken()
            } }
        }
    }
}

/**
 * Finds the node pointed to by this edge.
 *
 * The method name assumes you picture trees with the root node on top.
 *
 * `edge.descend().ascend().unwrap()` and `node.ascend().unwrap().descend()` should
 * both, upon success, do nothing.
 */
public fun <BorrowType, K, V> Handle<NodeRef<BorrowType, K, V, Internal>, Edge>.descend(): NodeRef<BorrowType, K, V, LeafOrInternal> {
    run {
        check(BorrowType.TRAVERSAL_PERMIT)
    }
    // We need to use raw pointers to nodes because, if BorrowType is
    // marker.ValMut, there might be outstanding mutable references to
    // values that we must not invalidate. There's no worry accessing the
    // height field because that value is copied. Beware that, once the
    // node pointer is dereferenced, we access the edges array with a
    // reference (Rust issue #73987) and invalidate any other references
    // to or inside the array, should any be around.
    val parentPtr = NodeRef.asInternalPtr(this.node)
    // ignore-tidy-undocumented-unchecked
    val node = parentPtr.edges.getUnchecked(this.idx).assumeInitRead()
    return NodeRef(node = node, height = this.node.height - 1, marker = Unit)
}

public fun <K, V, NodeType> Handle<NodeRef<Immut, K, V, NodeType>, KV>.intoKv(): Pair<K, V> {
    check(this.idx < this.node.len())
    val leaf = this.node.intoLeaf()
    // ignore-tidy-undocumented-unchecked
    val k = leaf.keys.getUnchecked(this.idx).assumeInitRef()
    // ignore-tidy-undocumented-unchecked
    val v = leaf.vals.getUnchecked(this.idx).assumeInitRef()
    return Pair(k, v)
}

public fun <K, V, NodeType> Handle<NodeRef<Mut, K, V, NodeType>, KV>.keyMut(): K {
    // ignore-tidy-undocumented-unchecked
    return this.node.keyAreaMut(this.idx).assumeInitMut()
}

public fun <K, V, NodeType> Handle<NodeRef<Mut, K, V, NodeType>, KV>.intoValMut(): V {
    check(this.idx < this.node.len())
    val leaf = this.node.intoLeafMut()
    // ignore-tidy-undocumented-unchecked
    return leaf.vals.getUncheckedMut(this.idx).assumeInitMut()
}

public fun <K, V, NodeType> Handle<NodeRef<Mut, K, V, NodeType>, KV>.intoKvMut(): Pair<K, V> {
    check(this.idx < this.node.len())
    val leaf = this.node.intoLeafMut()
    // ignore-tidy-undocumented-unchecked
    val k = leaf.keys.getUncheckedMut(this.idx).assumeInitMut()
    // ignore-tidy-undocumented-unchecked
    val v = leaf.vals.getUncheckedMut(this.idx).assumeInitMut()
    return Pair(k, v)
}

public fun <K, V, NodeType> Handle<NodeRef<ValMut, K, V, NodeType>, KV>.intoKvValmut(): Pair<K, V> {
    // ignore-tidy-undocumented-unchecked
    return this.node.intoKeyValMutAt(this.idx)
}

public fun <K, V, NodeType> Handle<NodeRef<Mut, K, V, NodeType>, KV>.kvMut(): Pair<K, V> {
    check(this.idx < this.node.len())
    // We cannot call separate key and value methods, because calling the second one
    // invalidates the reference returned by the first.
    // ignore-tidy-undocumented-unchecked
    return run {
        val leaf = this.node.asLeafMut()
        val key = leaf.keys.getUncheckedMut(this.idx).assumeInitMut()
        val value = leaf.vals.getUncheckedMut(this.idx).assumeInitMut()
        Pair(key, value)
    }
}

/**
 * Replaces the key and value that the KV handle refers to.
 */
public fun <K, V, NodeType> Handle<NodeRef<Mut, K, V, NodeType>, KV>.replaceKv(k: K, v: V): Pair<K, V> {
    val (key, value) = this.kvMut()
    return Pair(replace(key, k), replace(value, v))
}

/**
 * Extracts the key and value that the KV handle refers to.
 * # Safety
 * The node that the handle refers to must not yet have been deallocated.
 */
public fun <K, V, NodeType> Handle<NodeRef<Dying, K, V, NodeType>, KV>.intoKeyVal(): Pair<K, V> {
    check(this.idx < this.node.len())
    val leaf = this.node.asLeafDying()
    // ignore-tidy-undocumented-unchecked
    return run {
        val key = leaf.keys.getUncheckedMut(this.idx).assumeInitRead()
        val value = leaf.vals.getUncheckedMut(this.idx).assumeInitRead()
        Pair(key, value)
    }
}

/**
 * Drops the key and value that the KV handle refers to.
 * # Safety
 * The node that the handle refers to must not yet have been deallocated.
 */
public fun <K, V, NodeType> Handle<NodeRef<Dying, K, V, NodeType>, KV>.dropKeyVal() {
    // Run the destructor of the value even if the destructor of the key panics.
    open class Dropper<T>(
        public var value: MaybeUninit<T>,
    ) {
        public var field0: MaybeUninit<T>
            get() = value
            set(v) {
                value = v
            }

        public open fun drop() {
            // ignore-tidy-undocumented-unchecked
            run {
                this.value.assumeInitDrop()
            }
        }
    }
    check(this.idx < this.node.len())
    val leaf = this.node.asLeafDying()
    // ignore-tidy-undocumented-unchecked
    run {
        val key = leaf.keys.getUncheckedMut(this.idx)
        val value = leaf.vals.getUncheckedMut(this.idx)
        val guard = Dropper(value)
        key.assumeInitDrop()
        // dropping the guard will drop the value
    }
}

/**
 * Helps implementations of `split` for a particular `NodeType`,
 * by taking care of leaf data.
 */
public fun <K, V, NodeType> Handle<NodeRef<Mut, K, V, NodeType>, KV>.splitLeafData(newNode: LeafNode<K, V>): Pair<K, V> {
    check(this.idx < this.node.len())
    val oldLen = this.node.len()
    val newLen = oldLen - this.idx - 1
    newNode.len = newLen.toUShort()
    // ignore-tidy-undocumented-unchecked
    return run {
        val k = this.node.keyAreaMut(this.idx).assumeInitRead()
        val v = this.node.valAreaMut(this.idx).assumeInitRead()
        moveToSlice(this.node.keyAreaMut(this.idx + 1 until oldLen), ((newNode.keys).subList(0, newLen)))
        moveToSlice(this.node.valAreaMut(this.idx + 1 until oldLen), ((newNode.vals).subList(0, newLen)))
        this.node.lenMut() = this.idx.toUShort()
        Pair(k, v)
    }
}

/**
 * Splits the underlying node into three parts:
 *
 * - The node is truncated to only contain the key-value pairs to the left of
 *   this handle.
 * - The key and value pointed to by this handle are extracted.
 * - All the key-value pairs to the right of this handle are put into a newly
 *   allocated node.
 */
public fun <K, V, A : AllocatorClone> Handle<NodeRef<Mut, K, V, Leaf>, KV>.split(alloc: A): SplitResult<K, V, Leaf> {
    val newNode = LeafNode.new(alloc)
    val kv = this.splitLeafData(newNode)
    val right = NodeRef.fromNewLeaf(newNode)
    return SplitResult(left = this.node, kv = kv, right = right)
}

/**
 * Removes the key-value pair pointed to by this handle and returns it, along with the edge
 * that the key-value pair collapsed into.
 */
public fun <K, V> Handle<NodeRef<Mut, K, V, Leaf>, KV>.remove(): Pair<Pair<K, V>, Handle<NodeRef<Mut, K, V, Leaf>, Edge>> {
    val oldLen = this.node.len()
    // ignore-tidy-undocumented-unchecked
    return run {
        val k = sliceRemove(this.node.keyAreaMut(0 until oldLen), this.idx)
        val v = sliceRemove(this.node.valAreaMut(0 until oldLen), this.idx)
        this.node.lenMut() = (oldLen - 1).toUShort()
        Pair(Pair(k, v), this.leftEdge())
    }
}

/**
 * Represents a session for evaluating and performing a balancing operation
 * around an internal key-value pair.
 */
public open class BalancingContext<K, V> internal constructor(
    parentCtorArg: Handle<NodeRef<Mut, K, V, Internal>, KV>,
    leftChildCtorArg: NodeRef<Mut, K, V, LeafOrInternal>,
    rightChildCtorArg: NodeRef<Mut, K, V, LeafOrInternal>,
) {
    public var parent: Handle<NodeRef<Mut, K, V, Internal>, KV> = parentCtorArg
        internal set

    public var leftChild: NodeRef<Mut, K, V, LeafOrInternal> = leftChildCtorArg
        internal set

    public var rightChild: NodeRef<Mut, K, V, LeafOrInternal> = rightChildCtorArg
        internal set

    public open fun leftChildLen(): Int = this.leftChild.len()

    public open fun rightChildLen(): Int = this.rightChild.len()

    public open fun intoLeftChild(): NodeRef<Mut, K, V, LeafOrInternal> = this.leftChild

    public open fun intoRightChild(): NodeRef<Mut, K, V, LeafOrInternal> = this.rightChild

    /**
     * Returns whether merging is possible, i.e., whether there is enough room
     * in a node to combine the central KV with both adjacent child nodes.
     */
    public open fun canMerge(): Boolean = this.leftChild.len() + 1 + this.rightChild.len() <= CAPACITY

    /**
     * Performs a merge and lets a closure decide what to return.
     */
    public open fun <R, A : Allocator> doMerge(result: (NodeRef<Mut, K, V, Internal>, NodeRef<Mut, K, V, LeafOrInternal>) -> R, alloc: A, typeLayoutK: TypeLayout<K> = UnresolvedTypeLayout(), typeLayoutV: TypeLayout<V> = UnresolvedTypeLayout()): R {
        var parentNode = this.parent.node
        val parentIdx = this.parent.idx
        val marker = this.parent.marker
        val oldParentLen = parentNode.len()
        val leftNode = this.leftChild
        val oldLeftLen = leftNode.len()
        val rightNode = this.rightChild
        val rightLen = rightNode.len()
        val newLeftLen = oldLeftLen + 1 + rightLen
        check(newLeftLen <= CAPACITY)
        // ignore-tidy-undocumented-unchecked
        run {
            leftNode.lenMut() = newLeftLen.toUShort()
            val parentKey = sliceRemove(parentNode.keyAreaMut(0 until oldParentLen), parentIdx)
            leftNode.keyAreaMut(oldLeftLen).write(parentKey)
            moveToSlice(rightNode.keyAreaMut(0 until rightLen), leftNode.keyAreaMut(oldLeftLen + 1 until newLeftLen))
            val parentVal = sliceRemove(parentNode.valAreaMut(0 until oldParentLen), parentIdx)
            leftNode.valAreaMut(oldLeftLen).write(parentVal)
            moveToSlice(rightNode.valAreaMut(0 until rightLen), leftNode.valAreaMut(oldLeftLen + 1 until newLeftLen))
            sliceRemove(parentNode.edgeAreaMut(0 until oldParentLen + 1), parentIdx + 1)
            parentNode.correctChildrensParentLinks(parentIdx + 1 until oldParentLen)
            parentNode.lenMut() -= 1
            if (parentNode.height > 1) {
                // SAFETY: the height of the nodes being merged is one below the height
                // of the node of this edge, thus above zero, so they are internal.
                val leftNode1 = leftNode.reborrowMut().castToInternalUnchecked()
                val rightNode1 = rightNode.castToInternalUnchecked()
                moveToSlice(rightNode1.edgeAreaMut(0 until rightLen + 1), leftNode1.edgeAreaMut(oldLeftLen + 1 until newLeftLen + 1))
                leftNode1.correctChildrensParentLinks(oldLeftLen + 1 until newLeftLen + 1)
                alloc.deallocate(rightNode1.node.cast(), Layout.new<InternalNode<K, V>>(typeLayoutT = UnresolvedTypeLayout<InternalNode<K, V>>()))
            } else {
                alloc.deallocate(rightNode.node.cast(), Layout.new<LeafNode<K, V>>(typeLayoutT = UnresolvedTypeLayout<LeafNode<K, V>>()))
            }
        }
        return result(parentNode, leftNode)
    }

    /**
     * Merges the parent's key-value pair and both adjacent child nodes into
     * the left child node and returns the shrunk parent node.
     *
     * Panics unless we `.canMerge()`.
     */
    public open fun <A : AllocatorClone> mergeTrackingParent(alloc: A): NodeRef<Mut, K, V, Internal> = this.doMerge({ parent, child -> parent }, alloc)

    /**
     * Merges the parent's key-value pair and both adjacent child nodes into
     * the left child node and returns that child node.
     *
     * Panics unless we `.canMerge()`.
     */
    public open fun <A : AllocatorClone> mergeTrackingChild(alloc: A): NodeRef<Mut, K, V, LeafOrInternal> = this.doMerge({ parent, child -> child }, alloc)

    /**
     * Merges the parent's key-value pair and both adjacent child nodes into
     * the left child node and returns the edge handle in that child node
     * where the tracked child edge ended up,
     *
     * Panics unless we `.canMerge()`.
     */
    public open fun <A : AllocatorClone> mergeTrackingChildEdge(trackEdgeIdx: LeftOrRight<Int>, alloc: A): Handle<NodeRef<Mut, K, V, LeafOrInternal>, Edge> {
        val oldLeftLen = this.leftChild.len()
        val rightLen = this.rightChild.len()
        check(when (trackEdgeIdx) {
            is LeftOrRight.Left<*> -> {
                val (idx) = trackEdgeIdx
                idx <= oldLeftLen
            }
            is LeftOrRight.Right<*> -> {
                val (idx1) = trackEdgeIdx
                idx1 <= rightLen
            }
        })
        val child = this.mergeTrackingChild(alloc)
        val newIdx =
            when (trackEdgeIdx) {
                is LeftOrRight.Left<*> -> {
                    val (idx2) = trackEdgeIdx
                    idx2
                }
                is LeftOrRight.Right<*> -> {
                    val (idx3) = trackEdgeIdx
                    oldLeftLen + 1 + idx3
                }
            }
        // ignore-tidy-undocumented-unchecked
        return Handle.newEdge(child, newIdx)
    }

    /**
     * Removes a key-value pair from the left child and places it in the key-value storage
     * of the parent, while pushing the old parent key-value pair into the right child.
     * Returns a handle to the edge in the right child corresponding to where the original
     * edge specified by `trackRightEdgeIdx` ended up.
     */
    public open fun stealLeft(trackRightEdgeIdx: Int): Handle<NodeRef<Mut, K, V, LeafOrInternal>, Edge> {
        this.bulkStealLeft(1)
        // ignore-tidy-undocumented-unchecked
        return Handle.newEdge(this.rightChild, 1 + trackRightEdgeIdx)
    }

    /**
     * Removes a key-value pair from the right child and places it in the key-value storage
     * of the parent, while pushing the old parent key-value pair onto the left child.
     * Returns a handle to the edge in the left child specified by `trackLeftEdgeIdx`,
     * which didn't move.
     */
    public open fun stealRight(trackLeftEdgeIdx: Int): Handle<NodeRef<Mut, K, V, LeafOrInternal>, Edge> {
        this.bulkStealRight(1)
        // ignore-tidy-undocumented-unchecked
        return Handle.newEdge(this.leftChild, trackLeftEdgeIdx)
    }

    /**
     * This does stealing similar to `stealLeft` but steals multiple elements at once.
     */
    public open fun bulkStealLeft(count: Int) {
        check(count > 0)
        // ignore-tidy-undocumented-unchecked
        run {
            val leftNode = this.leftChild
            val oldLeftLen = leftNode.len()
            val rightNode = this.rightChild
            val oldRightLen = rightNode.len()
            // Make sure that we may steal safely.
            check(oldRightLen + count <= CAPACITY)
            check(oldLeftLen >= count)
            val newLeftLen = oldLeftLen - count
            val newRightLen = oldRightLen + count
            leftNode.lenMut() = newLeftLen.toUShort()
            rightNode.lenMut() = newRightLen.toUShort()
            // Move leaf data.
            run {
                // Make room for stolen elements in the right child.
                sliceShr(rightNode.keyAreaMut(0 until newRightLen), count)
                sliceShr(rightNode.valAreaMut(0 until newRightLen), count)
                // Move elements from the left child to the right one.
                moveToSlice(leftNode.keyAreaMut(newLeftLen + 1 until oldLeftLen), rightNode.keyAreaMut(0 until count - 1))
                moveToSlice(leftNode.valAreaMut(newLeftLen + 1 until oldLeftLen), rightNode.valAreaMut(0 until count - 1))
                // Move the leftmost stolen pair to the parent.
                val k = leftNode.keyAreaMut(newLeftLen).assumeInitRead()
                val v = leftNode.valAreaMut(newLeftLen).assumeInitRead()
                val (k1, v1) = this.parent.replaceKv(k, v)
                // Move parent's key-value pair to the right child.
                rightNode.keyAreaMut(count - 1).write(k1)
                rightNode.valAreaMut(count - 1).write(v1)
            }
            run {
                val matchVal = Pair(leftNode.reborrowMut().force(), rightNode.reborrowMut().force())
                when {
                    matchVal.first is ForceResult.Internal<*, *> && matchVal.second is ForceResult.Internal<*, *> -> {
                        val (left) = matchVal.first as ForceResult.Internal<*, *>
                        val (right) = matchVal.second as ForceResult.Internal<*, *>
                        // Make room for stolen edges.
                        sliceShr(right.edgeAreaMut(0 until newRightLen + 1), count)
                        // Steal edges.
                        moveToSlice(left.edgeAreaMut(newLeftLen + 1 until oldLeftLen + 1), right.edgeAreaMut(0 until count))
                        right.correctChildrensParentLinks(0 until newRightLen + 1)
                    }
                    matchVal.first is ForceResult.Leaf<*, *> && matchVal.second is ForceResult.Leaf<*, *> -> {}
                    else -> error("unreachable")
                }
            }
        }
    }

    /**
     * The symmetric clone of `bulkStealLeft`.
     */
    public open fun bulkStealRight(count: Int) {
        check(count > 0)
        // ignore-tidy-undocumented-unchecked
        run {
            val leftNode = this.leftChild
            val oldLeftLen = leftNode.len()
            val rightNode = this.rightChild
            val oldRightLen = rightNode.len()
            // Make sure that we may steal safely.
            check(oldLeftLen + count <= CAPACITY)
            check(oldRightLen >= count)
            val newLeftLen = oldLeftLen + count
            val newRightLen = oldRightLen - count
            leftNode.lenMut() = newLeftLen.toUShort()
            rightNode.lenMut() = newRightLen.toUShort()
            // Move leaf data.
            run {
                // Move the rightmost stolen pair to the parent.
                val k = rightNode.keyAreaMut(count - 1).assumeInitRead()
                val v = rightNode.valAreaMut(count - 1).assumeInitRead()
                val (k1, v1) = this.parent.replaceKv(k, v)
                // Move parent's key-value pair to the left child.
                leftNode.keyAreaMut(oldLeftLen).write(k1)
                leftNode.valAreaMut(oldLeftLen).write(v1)
                // Move elements from the right child to the left one.
                moveToSlice(rightNode.keyAreaMut(0 until count - 1), leftNode.keyAreaMut(oldLeftLen + 1 until newLeftLen))
                moveToSlice(rightNode.valAreaMut(0 until count - 1), leftNode.valAreaMut(oldLeftLen + 1 until newLeftLen))
                // Fill gap where stolen elements used to be.
                sliceShl(rightNode.keyAreaMut(0 until oldRightLen), count)
                sliceShl(rightNode.valAreaMut(0 until oldRightLen), count)
            }
            run {
                val matchVal = Pair(leftNode.reborrowMut().force(), rightNode.reborrowMut().force())
                when {
                    matchVal.first is ForceResult.Internal<*, *> && matchVal.second is ForceResult.Internal<*, *> -> {
                        val (left) = matchVal.first as ForceResult.Internal<*, *>
                        val (right) = matchVal.second as ForceResult.Internal<*, *>
                        // Steal edges.
                        moveToSlice(right.edgeAreaMut(0 until count), left.edgeAreaMut(oldLeftLen + 1 until newLeftLen + 1))
                        // Fill gap where stolen edges used to be.
                        sliceShl(right.edgeAreaMut(0 until oldRightLen + 1), count)
                        left.correctChildrensParentLinks(oldLeftLen + 1 until newLeftLen + 1)
                        right.correctChildrensParentLinks(0 until newRightLen + 1)
                    }
                    matchVal.first is ForceResult.Leaf<*, *> && matchVal.second is ForceResult.Leaf<*, *> -> {}
                    else -> error("unreachable")
                }
            }
        }
    }

    public companion object {
        public operator fun <K, V> invoke(
            parent: Handle<NodeRef<Mut, K, V, Internal>, KV>,
            leftChild: NodeRef<Mut, K, V, LeafOrInternal>,
            rightChild: NodeRef<Mut, K, V, LeafOrInternal>,
        ): BalancingContext<K, V> = BalancingContext<K, V>(
            parentCtorArg = parent,
            leftChildCtorArg = leftChild,
            rightChildCtorArg = rightChild,
        )
    }
}

public fun <K, V> Handle<NodeRef<Mut, K, V, Internal>, KV>.considerForBalancing(): BalancingContext<K, V> {
    // ignore-tidy-undocumented-unchecked
    val self1 = read(this)
    // ignore-tidy-undocumented-unchecked
    val self2 = read(this)
    return BalancingContext(parent = this, leftChild = self1.leftEdge().descend(), rightChild = self2.rightEdge().descend())
}

/**
 * Chooses a balancing context involving the node as a child, thus between
 * the KV immediately to the left or to the right in the parent node.
 * Returns an `Err` if there is no parent.
 * Panics if the parent is empty.
 *
 * Prefers the left side, to be optimal if the given node is somehow
 * underfull, meaning here only that it has fewer elements than its left
 * sibling and than its right sibling, if they exist. In that case,
 * merging with the left sibling is faster, since we only need to move
 * the node's N elements, instead of shifting them to the right and moving
 * more than N elements in front. Stealing from the left sibling is also
 * typically faster, since we only need to shift the node's N elements to
 * the right, instead of shifting at least N of the sibling's elements to
 * the left.
 */
public fun <K, V> NodeRef<Mut, K, V, LeafOrInternal>.chooseParentKv(): Result<LeftOrRight<BalancingContext<K, V>>, NodeRef<Mut, K, V, LeafOrInternal>> {
    // ignore-tidy-undocumented-unchecked
    return run {
        val matchVal = read(this).ascend()
        when (matchVal) { is Result.Ok<*, *> -> {
            val parentEdge = matchVal.okValue()
            run {
                val matchVal1 = parentEdge.leftKv()
                when (matchVal1) { is Result.Ok<*, *> -> {
                    val leftParentKv = matchVal1.okValue()
                    Result.Ok<LeftOrRight<BalancingContext<K, V>>, NodeRef<Mut, K, V, LeafOrInternal>>(LeftOrRight.Left(BalancingContext(parent = read(leftParentKv), leftChild = leftParentKv.leftEdge().descend(), rightChild = this)))
                }; is Result.Err<*, *> -> {
                    val parentEdge1 = matchVal1.errValue()
                    run {
                        val matchVal2 = parentEdge1.rightKv()
                        when (matchVal2) { is Result.Ok<*, *> -> {
                            val rightParentKv = matchVal2.okValue()
                            Result.Ok<LeftOrRight<BalancingContext<K, V>>, NodeRef<Mut, K, V, LeafOrInternal>>(LeftOrRight.Right(BalancingContext(parent = read(rightParentKv), leftChild = this, rightChild = rightParentKv.rightEdge().descend())))
                        }; is Result.Err<*, *> -> {
                            error("empty internal node")
                        } }
                    }
                } }
            }
        }; is Result.Err<*, *> -> {
            val root = matchVal.errValue()
            Result.Err<LeftOrRight<BalancingContext<K, V>>, NodeRef<Mut, K, V, LeafOrInternal>>(root)
        } }
    }
}

public fun <BorrowType, K, V> Handle<NodeRef<BorrowType, K, V, Leaf>, Edge>.forgetNodeType(): Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, Edge> {
    // ignore-tidy-undocumented-unchecked
    return Handle.newEdge(this.node.forgetType(), this.idx)
}

/**
 * Checks whether the underlying node is an `Internal` node or a `Leaf` node.
 */
public fun <BorrowType, K, V, Type> Handle<NodeRef<BorrowType, K, V, LeafOrInternal>, Type>.force(): ForceResult<Handle<NodeRef<BorrowType, K, V, Leaf>, Type>, Handle<NodeRef<BorrowType, K, V, Internal>, Type>> =
    when (val matchVal = this.node.force()) {
        is ForceResult.Leaf<*, *> -> {
            val (node) = matchVal
            ForceResult.Leaf(Handle(node = node, idx = this.idx, marker = Unit))
        }
        is ForceResult.Internal<*, *> -> {
            val (node1) = matchVal
            ForceResult.Internal(Handle(node = node1, idx = this.idx, marker = Unit))
        }
    }

/**
 * Unsafely asserts to the compiler the static information that the handle's node is a `Leaf`.
 */
public fun <K, V, Type> Handle<NodeRef<Mut, K, V, LeafOrInternal>, Type>.castToLeafUnchecked(): Handle<NodeRef<Mut, K, V, Leaf>, Type> {
    // ignore-tidy-undocumented-unchecked
    val node = this.node.castToLeafUnchecked()
    return Handle(node = node, idx = this.idx, marker = Unit)
}

/**
 * Move the suffix after `self` from one node to another one. `right` must be empty.
 * The first edge of `right` remains unchanged.
 */
public fun <K, V> Handle<NodeRef<Mut, K, V, LeafOrInternal>, Edge>.moveSuffix(right: NodeRef<Mut, K, V, LeafOrInternal>) {
    // ignore-tidy-undocumented-unchecked
    run {
        val newLeftLen = this.idx
        val leftNode = this.reborrowMut().intoNode()
        val oldLeftLen = leftNode.len()
        val newRightLen = oldLeftLen - newLeftLen
        val rightNode = right.reborrowMut()
        check(rightNode.len() == 0)
        check(leftNode.height == rightNode.height)
        if (newRightLen > 0) {
            leftNode.lenMut() = newLeftLen.toUShort()
            rightNode.lenMut() = newRightLen.toUShort()
            moveToSlice(leftNode.keyAreaMut(newLeftLen until oldLeftLen), rightNode.keyAreaMut(0 until newRightLen))
            moveToSlice(leftNode.valAreaMut(newLeftLen until oldLeftLen), rightNode.valAreaMut(0 until newRightLen))
            run {
                val matchVal = Pair(leftNode.force(), rightNode.force())
                when {
                    matchVal.first is ForceResult.Internal<*, *> && matchVal.second is ForceResult.Internal<*, *> -> {
                        val (left) = matchVal.first as ForceResult.Internal<*, *>
                        val (right1) = matchVal.second as ForceResult.Internal<*, *>
                        moveToSlice(left.edgeAreaMut(newLeftLen + 1 until oldLeftLen + 1), right1.edgeAreaMut(1 until newRightLen + 1))
                        right1.correctChildrensParentLinks(1 until newRightLen + 1)
                    }
                    matchVal.first is ForceResult.Leaf<*, *> && matchVal.second is ForceResult.Leaf<*, *> -> {}
                    else -> error("unreachable")
                }
            }
        }
    }
}

public sealed class ForceResult<Leaf, Internal> {
    public data class Leaf<Leaf, Internal>(public var value: Leaf) : ForceResult<Leaf, Internal>()
    public data class Internal<Leaf, Internal>(public var value: Internal) : ForceResult<Leaf, Internal>()
}

/**
 * Result of insertion, when a node needed to expand beyond its capacity.
 */
public open class SplitResult<K, V, NodeType> internal constructor(
    leftCtorArg: NodeRef<Mut, K, V, NodeType>,
    // Some key and value that existed before and were split off, to be inserted elsewhere.
    public var kv: Pair<K, V>,
    rightCtorArg: NodeRef<Owned, K, V, NodeType>,
) {
    // Altered node in existing tree with elements and edges that belong to the left of `kv`.
    public var left: NodeRef<Mut, K, V, NodeType> = leftCtorArg
        internal set

    // Owned, unattached, new node with elements and edges that belong to the right of `kv`.
    public var right: NodeRef<Owned, K, V, NodeType> = rightCtorArg
        internal set

    public companion object {
        public operator fun <K, V, NodeType> invoke(
            left: NodeRef<Mut, K, V, NodeType>,
            kv: Pair<K, V>,
            right: NodeRef<Owned, K, V, NodeType>,
        ): SplitResult<K, V, NodeType> = SplitResult<K, V, NodeType>(
            leftCtorArg = left,
            kv = kv,
            rightCtorArg = right,
        )
    }
}

public fun <K, V> SplitResult<K, V, Leaf>.forgetNodeType(): SplitResult<K, V, LeafOrInternal> = SplitResult(left = this.left.forgetType(), kv = this.kv, right = this.right.forgetType())

public open class Leaf(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

public open class Internal(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

public open class LeafOrInternal(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

public object Owned : BorrowType {
    /**
     * Reject traversal, because it isn't needed. Instead traversal
     * happens using the result of `borrowMut`.
     * By disabling traversal, and only creating new references to roots,
     * we know that every reference of the `Owned` type is to a root node.
     */
    public val TRAVERSAL_PERMIT: Boolean = false
}

public object Dying : BorrowType

public object DormantMut : BorrowType

public open class Immut(
    public var value: Unit,
) : BorrowType {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }
}

public open class Mut(
    public var value: Unit,
) : BorrowType {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }
}

public open class ValMut(
    public var value: Unit,
) : BorrowType {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }
}

public interface BorrowType {
    /**
     * If node references of this borrow type allow traversing to other
     * nodes in the tree, this constant is set to `true`. It can be used
     * for a compile-time assertion.
     */
    public val TRAVERSAL_PERMIT: Boolean
        get() = true
}

public open class KV(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

public open class Edge(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

/**
 * Inserts a value into a slice of initialized elements followed by one uninitialized element.
 *
 * # Safety
 * The slice has more than `idx` elements.
 */
public fun <T> sliceInsert(slice: MutableList<MaybeUninit<T>>, idx: Int, value: T) {
    val len = slice.size
    check(len > idx)
    val slicePtr = slice.asMutPtr()
    // ignore-tidy-undocumented-unchecked
    run {
        if (len > idx + 1) {
            copy((MutPtr((slicePtr).rawAddress + ((idx).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).asConstPtr(), MutPtr((slicePtr).rawAddress + ((idx + 1).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes)), len - idx - 1, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
        }
        (UnresolvedTypeLayout<MaybeUninit<T>>()).readNative((MutPtr((slicePtr).rawAddress + ((idx).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).rawAddress).write(value)
    }
}

/**
 * Removes and returns a value from a slice of all initialized elements, leaving behind one
 * trailing uninitialized element.
 *
 * # Safety
 * The slice has more than `idx` elements.
 */
public fun <T> sliceRemove(slice: MutableList<MaybeUninit<T>>, idx: Int): T {
    val len = slice.size
    check(idx < len)
    val slicePtr = slice.asMutPtr()
    // ignore-tidy-undocumented-unchecked
    return run {
        val ret = (UnresolvedTypeLayout<MaybeUninit<T>>()).readNative((MutPtr((slicePtr).rawAddress + ((idx).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).rawAddress).assumeInitRead()
        copy((MutPtr((slicePtr).rawAddress + ((idx + 1).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).asConstPtr(), MutPtr((slicePtr).rawAddress + ((idx).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes)), len - idx - 1, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
        ret
    }
}

/**
 * Shifts the elements in a slice `distance` positions to the left.
 *
 * # Safety
 * The slice has at least `distance` elements.
 */
public fun <T> sliceShl(slice: MutableList<MaybeUninit<T>>, distance: Int) {
    val slicePtr = slice.asMutPtr()
    // ignore-tidy-undocumented-unchecked
    run {
        copy((MutPtr((slicePtr).rawAddress + ((distance).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).asConstPtr(), slicePtr, slice.size - distance, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
    }
}

/**
 * Shifts the elements in a slice `distance` positions to the right.
 *
 * # Safety
 * The slice has at least `distance` elements.
 */
public fun <T> sliceShr(slice: MutableList<MaybeUninit<T>>, distance: Int) {
    val slicePtr = slice.asMutPtr()
    // ignore-tidy-undocumented-unchecked
    run {
        copy((slicePtr).asConstPtr(), MutPtr((slicePtr).rawAddress + ((distance).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes)), slice.size - distance, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
    }
}

/**
 * Moves all values from a slice of initialized elements to a slice
 * of uninitialized elements, leaving behind `src` as all uninitialized.
 * Works like `dst.copyFromSlice(src)` but does not require `T` to be `Copy`.
 */
public fun <T> moveToSlice(src: MutableList<MaybeUninit<T>>, dst: MutableList<MaybeUninit<T>>) {
    check(src.size == dst.size)
    // ignore-tidy-undocumented-unchecked
    run {
        copyNonoverlapping(src.asPtr(), dst.asMutPtr(), src.size, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
    }
}

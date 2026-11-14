// port-lint: source ptr/metadata.rs
package io.github.kotlinmania.rustlang.core.ptr.metadata

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.cmp.Eq
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.hash.Hash
import io.github.kotlinmania.rustlang.core.intrinsics.aggregateRawPtr
import io.github.kotlinmania.rustlang.core.intrinsics.ptrMetadata
import io.github.kotlinmania.rustlang.core.intrinsics.transmute
import io.github.kotlinmania.rustlang.core.intrinsics.vtableAlign
import io.github.kotlinmania.rustlang.core.intrinsics.vtableSize
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

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
 * Provides the pointer metadata type of any pointed-to type.
 *
 * # Pointer metadata
 *
 * Raw pointer types and reference types in Rust can be thought of as made of two parts:
 * a data pointer that contains the memory address of the value, and some metadata.
 *
 * For statically-sized types (that implement the `Sized` traits)
 * as well as for `extern` types,
 * pointers are said to be “thin”: metadata is zero-sized and its type is `()`.
 *
 * Pointers to [dynamically-sized types][dst] are said to be “wide” or “fat”,
 * they have non-zero-sized metadata:
 *
 * * For structs whose last field is a DST, metadata is the metadata for the last field
 * * For the `String` type, metadata is the length in bytes as `Int`
 * * For slice types like `[T]`, metadata is the length in items as `Int`
 * * For trait objects like `dynamic SomeTrait`, metadata is [`DynMetadata.Self`][DynMetadata]
 *   (e.g. `DynMetadata<dynamic SomeTrait>`)
 *
 * In the future, the Rust language may gain new kinds of types
 * that have different pointer metadata.
 *
 * [dst]: https://doc.rust-lang.org/nomicon/exotic-sizes.html#dynamically-sized-types-dsts
 *
 * # The `Pointee` trait
 *
 * The point of this trait is its `Metadata` associated type,
 * which is `()` or `Int` or `DynMetadata<_>` as described above.
 * It is automatically implemented for every type.
 * It can be assumed to be implemented in a generic context, even without a corresponding bound.
 *
 * # Usage
 *
 * Raw pointers can be decomposed into the data pointer and metadata components
 * with their [toRawParts] method.
 *
 * Alternatively, metadata alone can be extracted with the [metadata] function.
 * A reference can be passed to [metadata] and implicitly coerced.
 *
 * A (possibly-wide) pointer can be put back together from its data pointer and metadata
 * with [fromRawParts] or [fromRawPartsMut].
 *
 * [toRawParts]: *const.toRawParts
 */
public interface Pointee<out Metadata>

// NOTE: don’t stabilize this before trait aliases are stable in the language?

/**
 * Pointers to types implementing this trait alias are “thin”.
 *
 * This includes statically-`Sized` types and `extern` types.
 *
 * # Example
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 *
 * @feature(ptrMetadata)
 * public fun doctestEntry() {
 *     fun <T : Thin> thisNeverPanics() {
 *         check(sizeOf<T>(typeLayoutT = ObjectSlotLayout<T>()) == sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))
 *     }
 * }
 * ```
 */
public interface Thin : Pointee<Unit>

/**
 * Extracts the metadata component of a pointer.
 *
 * Values of type `* T`, `T`, or `T` can be passed directly to this function
 * as they implicitly coerce to `* const T`.
 *
 * # Example
 *
 * ```kotlin
 * @feature(ptrMetadata)
 * public fun doctestEntry() {
 *     check(metadata("foo") == 3)
 * }
 * ```
 */
public fun metadata(ptr: ConstPtr): Metadata = ptrMetadata(ptr)

/**
 * Forms a (possibly-wide) raw pointer from a data pointer and metadata.
 *
 * This function is safe but the returned pointer is not necessarily safe to dereference.
 * For slices, see the documentation of [slice.fromRawParts] for safety requirements.
 * For trait objects, the metadata must come from a pointer to the same underlying erased type.
 *
 * If you are attempting to deconstruct a DST in a generic context to be reconstructed later,
 * a thin pointer can always be obtained by casting `* const T` to `* const()`.
 */
public fun fromRawParts(dataPointer: ConstPtr, metadata: Metadata): ConstPtr = aggregateRawPtr(dataPointer, metadata)

/**
 * Performs the same functionality as [fromRawParts], except that a
 * raw `*` pointer is returned, as opposed to a raw `* const` pointer.
 *
 * See the documentation of [fromRawParts] for more details.
 */
public fun fromRawPartsMut(dataPointer: MutPtr, metadata: Metadata): MutPtr = aggregateRawPtr(dataPointer, metadata)

/**
 * The metadata for a `Dyn = dynamic SomeTrait` trait object type.
 *
 * It is a pointer to a vtable (virtual call table)
 * that represents all the necessary information
 * to manipulate the concrete type stored inside a trait object.
 * The vtable notably contains:
 *
 * * type size
 * * type alignment
 * * a pointer to the type’s `dropInPlace` impl (may be a no-op for plain-old-data)
 * * pointers to all the methods for the type’s implementation of the trait
 *
 * Note that the first three are special because they’re necessary to allocate, drop,
 * and deallocate any trait object.
 *
 * It is possible to name this struct with a type parameter that is not a `dynamic` trait object
 * (for example `DynMetadata<ULong>`) but not to obtain a meaningful value of that struct.
 *
 * Note that while this type implements `PartialEq`, comparing vtable pointers is unreliable:
 * pointers to vtables of the same type for the same trait can compare inequal (because vtables are
 * duplicated in multiple codegen units), and pointers to vtables of *different* types/traits can
 * compare equal (since identical vtables can be deduplicated within a codegen unit).
 */
public open class DynMetadata<Dyn> internal constructor(
    vtablePtrCtorArg: NonNull,
    public var phantom: Unit,
) : TrivialClone<DynMetadata<Dyn>> {
    public var vtablePtr: NonNull = vtablePtrCtorArg
        internal set

    /**
     * When `DynMetadata` appears as the metadata field of a wide pointer, the rustcMiddle layout
     * computation does magic and the resulting layout is *not* a `FieldsShape.Aggregate`, instead
     * it is a `FieldsShape.Primitive`. This means that the same type can have different layout
     * depending on whether it appears as the metadata field of a wide pointer or as a stand-alone
     * type, which understandably confuses codegen and leads to ICEs when trying to project to a
     * field of `DynMetadata`. To work around that issue, we use `reinterpret` instead of using a
     * field projection.
     */
    public open fun vtablePtr(): ConstPtr {
        // SAFETY: this layout assumption is hard-coded into the compiler.
        // If it's somehow not a size match, the reinterpret will error.
        return transmute<DynMetadata<Dyn>, ConstPtr>(this)
    }

    /**
     * Returns the size of the type associated with this vtable.
     */
    public open fun sizeOf(): Int {
        // Note that "size stored in vtable" is *not* the same as "result of sizeOfValRaw".
        // Consider a reference like `(Int, dynamic Send)`: the vtable will only store the size of the
        // `Send` part!
        // SAFETY: DynMetadata always contains a valid vtable pointer
        return vtableSize(constPtrFromRustCast(this.vtablePtr()))
    }

    /**
     * Returns the alignment of the type associated with this vtable.
     */
    public open fun alignOf(): Int {
        // SAFETY: DynMetadata always contains a valid vtable pointer
        return vtableAlign(constPtrFromRustCast(this.vtablePtr()))
    }

    /**
     * Returns the size and alignment together as a `Layout`
     */
    public open fun layout(): Layout {
        // SAFETY: the compiler emitted this vtable for a concrete Rust type which
        // is known to have a valid layout. Same rationale as in `Layout.forValue`.
        return Layout.fromSizeAlignUnchecked(this.sizeOf(), this.alignOf())
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "DynMetadata").field(this.vtablePtr()).finish()

    // Manual impls needed to avoid `Dyn: $ Trait` bounds.

    public override fun clone(): DynMetadata<Dyn> = DynMetadata(this.vtablePtr, this.phantom)

    public open fun eq(other: DynMetadata<Dyn>): Boolean = Eq<VTable>(this.vtablePtr(), other.vtablePtr())

    public open fun cmp(other: DynMetadata<Dyn>): Ordering = ConstPtr.cmp(this.vtablePtr(), other.vtablePtr())

    public open fun partialCmp(other: DynMetadata<Dyn>): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var hasher = 0
        Hash<VTable>(this.vtablePtr(), hasher)
        return hasher
    }

    public companion object {
        public operator fun <Dyn> invoke(
            vtablePtr: NonNull,
            phantom: Unit,
        ): DynMetadata<Dyn> = DynMetadata<Dyn>(
            vtablePtrCtorArg = vtablePtr,
            phantom = phantom,
        )
    }
}

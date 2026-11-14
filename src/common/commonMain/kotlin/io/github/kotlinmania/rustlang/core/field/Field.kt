// port-lint: source field.rs
package io.github.kotlinmania.rustlang.core.field

import io.github.kotlinmania.rustlang.core.intrinsics.fieldOffset
import io.github.kotlinmania.rustlang.core.intrinsics.typeName
import io.github.kotlinmania.rustlang.core.mem.typeinfo.Type
import io.github.kotlinmania.rustlang.core.mem.typeinfo.TypeKind
import io.github.kotlinmania.rustlang.core.mem.typeinfo.of
import io.github.kotlinmania.rustlang.core.ptr.hash
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

// Field Reflection

/**
 * Field Representing Type
 */
public open class FieldRepresentingType<T>(
    // We want this type to be invariant over `T`, because otherwise `fieldOf(Struct<short>,
    // field)`is a subtype of`fieldOf(Struct<long>, field)`. This subtype relationship does not
    // have an immediately obvious meaning and we want to prevent people from relying on it.
    public var phantom: Unit,
)

public fun <T> FieldRepresentingType<T, Variant, Field>.fmt(f: StringBuilder, variant: UInt, field: UInt): Result<Unit, Unit> {
    sealed class Member {
        public data class Name(public var value: String) : Member()
        public data class Index(public var value: UInt) : Member()

        public open fun fmt(f1: StringBuilder): Result<Unit, Unit> =
            when (this) {
                is Name -> {
                    val (name) = this
                    run { f1.append((name).toString()); Result.Ok(Unit) }
                }
                is Index -> {
                    val (idx) = this
                    run { f1.append((idx).toString()); Result.Ok(Unit) }
                }
            }
    }
    val (variant, field) =
        run {
            val typeId = of<T>()
            when (Type.of<T>().kind) {
                TypeKind.Struct -> Pair(null, Member.Name(typeId.field(0, field.toInt()).name()))
                TypeKind.Tuple -> Pair(null, Member.Index(field))
                TypeKind.Enum -> {
                    val variantName = typeId.variant(variant.toInt()).name()
                    val fieldName = typeId.field(variant.toInt(), field.toInt()).name()
                    Pair(variantName, Member.Name(fieldName))
                }
                TypeKind.Union -> Pair(null, Member.Name(typeId.field(0, field.toInt()).name()))
                else -> error("unreachable")
            }
        }
    val typeName = "FieldRepresentingType"
    return when (variant) {
        null -> f.writeFmt("field_of!($typeName, $field)")
        else -> {
            val variant1 = variant
            f.writeFmt("field_of!($typeName, $variant.$field)")
        }
    }
    // NOTE: if there are changes in the reflection work and the above no
    // longer compiles, then the following debug impl could also work in
    // the meantime:
    // `rust
    // let typeName = const { typeName::<T>() };
    // write(f, "fieldOf({typeName}, {VARIANT}.{FIELD})")
    // `
}

public fun <T> FieldRepresentingType<T, Variant, Field>.clone(variant: UInt, field: UInt): FieldRepresentingType<T, Variant, Field> = FieldRepresentingType(this.phantom)

public fun <T> defaultFieldRepresentingTypeTVariantField(variant: UInt, field: UInt): FieldRepresentingType<T, Variant, Field> = FieldRepresentingType(phantom = Unit)

public fun <T, H> FieldRepresentingType<T, Variant, Field>.hash(state: H, variant: UInt, field: UInt) {
    this.phantom.hash(state)
}

public fun <T> FieldRepresentingType<T, Variant, Field>.eq(other: FieldRepresentingType<T, Variant, Field>, variant: UInt, field: UInt): Boolean = this.phantom == other.phantom

public fun <T> FieldRepresentingType<T, Variant, Field>.partialCmp(other: FieldRepresentingType<T, Variant, Field>, variant: UInt, field: UInt): Ordering? = this.phantom.partialCmp(other.phantom)

public fun <T> FieldRepresentingType<T, Variant, Field>.cmp(other: FieldRepresentingType<T, Variant, Field>, variant: UInt, field: UInt): Ordering = this.phantom.cmp(other.phantom)

// NOTE: the compiler provides the impl of `Field` for `FieldRepresentingType` when it can guarantee
// the safety requirements of this trait. It also has to manually add the correct trait bounds on
// associated types (and the `Self` type). Thus any changes to the bounds here must be reflected in
// the old and new trait solver:
// - `fun assembleCandidatesForFieldTrait` in
// `compiler / rustcTraitSelection / src / traits / select / candidateAssembly.rs`, and
// - `fun considerBuiltinFieldCandidate` in
// `compiler / rustcNextTraitSolver / src / solve / traitGoals.rs`.

/**
 * Type representing a field of a `class`, `union`, `sealed class` variant or tuple.
 *
 * # Safety
 *
 * Given a valid value of type `Self.Base`, there exists a valid value of type `Self.Type` at
 * byte offset `OFFSET`
 */
public interface Field<T, out Base, out Type> {
    /**
     * The offset of the field in bytes.
     */
    public val OFFSET: Int
        get() = fieldOffset<T>()
}

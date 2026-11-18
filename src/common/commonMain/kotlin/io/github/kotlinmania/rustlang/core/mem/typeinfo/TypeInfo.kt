// port-lint: source mem/type_info.rs
package io.github.kotlinmania.rustlang.core.mem.typeinfo

import io.github.kotlinmania.rustlang.core.RustTypeId
import io.github.kotlinmania.rustlang.core.intrinsics.fieldRepresentingTypeActualTypeId
import io.github.kotlinmania.rustlang.core.intrinsics.fieldRepresentingTypeName
import io.github.kotlinmania.rustlang.core.intrinsics.fieldRepresentingTypeOffset
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeId
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdArrayLen
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdElementTy
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdFieldRepresentingType
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdFields
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdFunctionPtr
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdGenerics
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdIsSigned
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdNonExhaustive
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdPointsMutably
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdPointsTo
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdReflectedAttrs
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdSizeOf
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdTypeOf
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdVariantName
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdVariantNonExhaustive
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdVariantReflectedAttrs
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdVariants
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.metadata.DynMetadata
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

// MVP for exposing compile-time information about types in a
// runtime or const-eval processable way.

/**
 * Compile-time type information.
 */
public open class Type(
    /**
     * Per-type information
     */
    public var kind: TypeKind,
) {
    public companion object {
        /**
         * Returns the type information of the generic type parameter.
         *
         * Note: Unlike `TypeId`s obtained via `TypeId.of`, the `Type`
         * struct and its fields contain `TypeId`s that are not necessarily
         * derived from types that outlive `static`. This means that using
         * the `TypeId`s (transitively) obtained from this function will
         * be able to break invariants that other `TypeId` consuming crates
         * may have assumed to hold.
         */
        public fun <T> of(): Type = typeId<T>().info()
    }
}

/**
 * Info of a trait implementation, you can retrieve the vtable with [Self.getVtable]
 */
public open class TraitImpl<T>(
    public var vtable: DynMetadata<T>,
) {
    /**
     * Gets the raw vtable for type reflection mapping
     */
    public open fun getVtable(): DynMetadata<T> = this.vtable
}

/**
 * Compute the type information of a concrete type.
 * It can only be called at compile time.
 */
public fun RustTypeId.info(): Type = typeIdTypeOf(this)

// Note(reflection): get rid of the static scope bound on TypeId and remove this function.

/**
 * Returns the [TypeId] of the generic type parameter.
 *
 * This is identical to [TypeId.of] but without the static scope bound. It will be removed
 * in the future.
 */
public fun <T> of(): RustTypeId = typeId<T>()

/**
 * Compile-time type information.
 */
public sealed class TypeKind {
    /**
     * Tuples.
     */
    public data object Tuple : TypeKind()
    /**
     * Arrays.
     */
    public data object Array : TypeKind()
    /**
     * Slices.
     */
    public data object Slice : TypeKind()
    /**
     * Dynamic Traits.
     */
    public data class DynTrait(public var value: io.github.kotlinmania.rustlang.core.mem.typeinfo.DynTrait) : TypeKind()
    /**
     * Structs.
     */
    public data object Struct : TypeKind()
    /**
     * Enums.
     */
    public data object Enum : TypeKind()
    /**
     * Unions.
     */
    public data object Union : TypeKind()
    /**
     * Primitive boolean type.
     */
    public data object Bool : TypeKind()
    /**
     * Primitive character type.
     */
    public data object Char : TypeKind()
    /**
     * Primitive signed and unsigned integer type.
     */
    public data object Int : TypeKind()
    /**
     * Primitive floating-point type.
     */
    public data object Float : TypeKind()
    /**
     * String slice type.
     */
    public data class Str(public var value: io.github.kotlinmania.rustlang.core.mem.typeinfo.Str) : TypeKind()
    /**
     * References.
     */
    public data object Reference : TypeKind()
    /**
     * Pointers.
     */
    public data object Pointer : TypeKind()
    /**
     * Function pointers.
     */
    public data object FnPtr : TypeKind()
    /**
     * Note(#146922): add all the common types
     * non exhaustive list:
     * - Never
     */
    public data object Other : TypeKind()
}

/**
 * Compile-time type information about dynamic traits.
 * Note(#146922): Add super traits and generics
 */
public open class DynTrait(
    /**
     * The predicates of  a dynamic trait.
     */
    public var predicates: List<DynTraitPredicate>,
)

/**
 * Compile-time type information about a dynamic trait predicate.
 */
public open class DynTraitPredicate(
    /**
     * The type of the trait as a dynamic trait type.
     */
    public var traitTy: Trait,
)

/**
 * Compile-time type information about a trait.
 */
public open class Trait(
    /**
     * The TypeId of the trait as a dynamic type
     */
    public var ty: RustTypeId,
    /**
     * Whether the trait is an auto trait
     */
    public var isAuto: Boolean,
)

/**
 * Compile-time type information about instantiated generics of structs, enum and union variants.
 */
public sealed class Generic {
    /**
     * Scopes.
     */
    public data class Lifetime(public var value: io.github.kotlinmania.rustlang.core.mem.typeinfo.Lifetime) : Generic()
    /**
     * Types.
     */
    public data class Type(public var value: GenericType) : Generic()
    /**
     * Const parameters.
     */
    public data class Const(public var value: io.github.kotlinmania.rustlang.core.mem.typeinfo.Const) : Generic()
}

/**
 * Compile-time type information about generic scopes.
 */
public open class Lifetime {
    public companion object {
        public fun typeLayout(): TypeLayout<Lifetime> =
            object : TypeLayout<Lifetime> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Lifetime =
                    Lifetime()
                override fun writeNative(addr: Long, value: Lifetime) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Lifetime =
                    Lifetime()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Lifetime) {
                }
            }
    }
}

/**
 * Compile-time type information about instantiated generic types.
 */
public open class GenericType(
    /**
     * The type itself.
     */
    public var ty: RustTypeId,
)

/**
 * Compile-time type information about generic const parameters.
 */
public open class Const(
    /**
     * The const's type.
     */
    public var ty: RustTypeId,
)

/**
 * Compile-time type information about string slice types.
 */
public open class Str {
    public companion object {
        public fun typeLayout(): TypeLayout<Str> =
            object : TypeLayout<Str> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Str =
                    Str()
                override fun writeNative(addr: Long, value: Str) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Str =
                    Str()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Str) {
                }
            }
    }
}

/**
 * Function pointer, e.g. fn(u8),
 */
public open class FnPtr(
    public var isUnsafe: Boolean,
    public var abi: Abi,
    public var inputs: List<RustTypeId>,
    public var output: RustTypeId,
    public var variadic: Boolean,
    // Note(splat): should these fields be private, or merged into an Option<u8/u16>?
    /**
     * Is any function argument splatted?
     */
    public var isSplatted: Boolean,
    public var splattedIndex: UByte,
) {
    /**
     * Returns the splatted function argument index, or `None` if no argument is splatted.
     *
     * e.g. in `fun overload(a: UByte, @rustcSplat b: (Float, Int))` the index is 1,
     * and it can be called as `overload(a, 1.0, 2)`.
     */
    public open fun splatted(): UByte? =
        if (this.isSplatted) {
            this.splattedIndex
        } else {
            null
        }

    /**
     * Whether this function is variadic, e.g. extern "C" fn add(n: Int, mut args: ...);
     */
    public open fun isVariadic(): Boolean = this.variadic

    /**
     * whether this refers to an unchecked function.
     */
    public open fun isUnsafe(): Boolean = this.isUnsafe

    /**
     * Returns the application binary interface. For example extern "C".
     */
    public open fun abi(): Abi = this.abi

    /**
     * The types of the functions parameters
     */
    public open fun inputs(): List<RustTypeId> = this.inputs

    /**
     * List of the types returned by the function. For a function with no output
     * specified this returns `TypeId.of<Unit>`.
     */
    public open fun output(): RustTypeId = this.output
}

/**
 * Abi of [FnPtr]
 */
public sealed class Abi {
    /**
     * Named abi, e.g. extern "custom", "stdcall" etc.
     */
    public data class Named(public var value: String) : Abi()
    /**
     * Default
     */
    public data object ExternRust : Abi()
    /**
     * C-calling convention
     */
    public data object ExternC : Abi()

    public fun rustEquals(other: Abi): Boolean = this == other
}

/**
 * Returns `true` if the type represented by this `TypeId` is an signed integer.
 *
 * For everything else this returns false.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     check(RustTypeId.of<Int>().isSigned() == true)
 *     check(RustTypeId.of<UByte>().isSigned() == false)
 *     check(RustTypeId.of<Boolean>().isSigned() == false)
 * }
 * ```
 */
public fun RustTypeId.isSigned(): Boolean = typeIdIsSigned(this)

/**
 * When called on a `TypeId` representing an array or slice this returns the type of each
 * element otherwise this returns `None`.
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     check(RustTypeId.of<UIntArray>().elementTy() == RustTypeId.of<UInt>())
 *     check(RustTypeId.of<UByte>().elementTy() == null)
 *     // not an array or slice
 * }
 * ```
 */
public fun RustTypeId.elementTy(): RustTypeId? = typeIdElementTy(this)

/**
 * When called on a `TypeId` representing an array this returns the length of the array in
 * all other cases this returns zero.
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     check(RustTypeId.of<UIntArray>().arrayLen() == 16)
 *     check(RustTypeId.of<UByte>().arrayLen() == 0)
 *     // not an array
 * }
 * ```
 */
public fun RustTypeId.arrayLen(): Int = typeIdArrayLen(this)

/**
 * Returns the size of the type represented by this `TypeId`. `None` if it is unsized.
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     check(RustTypeId.of<UInt>().size() == 4)
 *     check(RustTypeId.of<UByteArray>().size() == 16)
 * }
 * ```
 */
public fun RustTypeId.size(): Int? = typeIdSizeOf(this)

/**
 * Returns the number of variants of the type represented by this `TypeId`.
 *
 * For enums, this is the number of variants. For structs and unions, this is always 1.
 *
 * ```kotlin
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     open class Unit
 *     open class Point(public var x: UInt, public var y: UInt)
 *     check(RustTypeId.of<Unit?>().variants() == 2)
 *     check(RustTypeId.of<Unit>().variants() == 1)
 *     check(RustTypeId.of<Point>().variants() == 1)
 *     check(RustTypeId.of<Pair<Float, Float>>().variants() == 1)
 * }
 * ```
 */
public fun RustTypeId.variants(): Int = typeIdVariants(this)

// Note(reflection): make the errors nicer. This is a wider problem,
// TypeId.fields has nice errors in the docs but those are not the ones shown
// by rustc.

/**
 * Returns the variant representing type at the given index of the type represented by this `TypeId`. Use it to
 * get the name of an enum variant or check whether it is nonExhaustive.
 *
 * ```kotlin
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     sealed class Enum {
 *         public data object Unit : Enum()
 *         public data class Tuple(public var field0: UInt, public var field1: ULong) : Enum()
 *         public data class Struct(public var x: UInt, public var y: UInt, public var z: String) : Enum()
 *     }
 *     check(RustTypeId.of<Enum>().variant(1).name() == "Tuple")
 *     check(RustTypeId.of<Enum>().variant(2).name() == "Struct")
 *     check(RustTypeId.of<Enum>().variant(1).nonExhaustive() == false)
 *     check(RustTypeId.of<Enum>().variant(2).nonExhaustive() == true)
 * }
 * ```
 *
 * The variant index refer to the source order index of a variant in a type.
 *
 * Variant indexes are always `0 .. variantCount`, regardless of any custom discriminants that may have been defined.
 *
 * ```kotlin
 * sealed class Enum {
 *     public data object Foo : Enum()
 *     // variant index == 0
 *     public data object Bar : Enum()
 *
 *     private fun kmDiscriminant(): Int =
 *         when (this) {
 *         Foo -> 0
 *         Bar -> 1
 *         }
 *
 *     public fun toByte(): Byte = kmDiscriminant().toByte()
 *
 *     public fun toUByte(): UByte = kmDiscriminant().toUByte()
 *
 *     public fun toShort(): Short = kmDiscriminant().toShort()
 *
 *     public fun toUShort(): UShort = kmDiscriminant().toUShort()
 *
 *     public fun toInt(): Int = kmDiscriminant()
 *
 *     public fun toUInt(): UInt = kmDiscriminant().toUInt()
 *
 *     public fun toLong(): Long = kmDiscriminant().toLong()
 *
 *     public fun toULong(): ULong = kmDiscriminant().toULong()
 * }
 * ```
 *
 * Calling variant on the TypeId for a struct will be treated as a compile-time error. The same
 * is true for out-of-bounds indexing on an enum.
 *
 * ```compile_fail,E0080
 * # @file:feature(typeInfo)
 * # use std.any.TypeId;
 * #
 * # struct Point {
 * #     x: u32,
 * #     y: u32,
 * # }
 * # enum Enum {
 * #     Unit,
 * #     Tuple(u32, u64),
 * #     Struct { x: u32, y: u32, z: String },
 * # }
 * const {
 *     _ = TypeId.of::<Point>().variant(0); // error: cannot get the variant of a struct
 *     _ = TypeId.of::<Enum>().variant(10); // error: indexing out of bounds: the len is 3 but the index is 10
 * }
 * ```
 */
public fun RustTypeId.variant(variantIndex: Int): VariantId {
    typeIdFields(this, variantIndex)
    return VariantId(base = this, variant = variantIndex)
}

// Note(typeInfo): Add enum variant pattern types and use them to represent individual variants
// Then add a `variant` method to get a wrapper around such a pattern type (similar to the FRT
// type we have) and add methods on that. It's the only way to really sensibly represent
// things like `nonExhaustive` which can be applied to variants as well.

/**
 * Returns the number of fields at the given `variantIndex` of the type represented by this `TypeId`.
 *
 * ```kotlin
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     open class Point(public var x: UInt, public var y: UInt)
 *     sealed class Enum {
 *         public data object Unit : Enum()
 *         public data class Tuple(public var field0: UInt, public var field1: ULong) : Enum()
 *         public data class Struct(public var x: UInt, public var y: UInt, public var z: String) : Enum()
 *     }
 *     check(RustTypeId.of<UInt>().fields(0) == 0)
 *     check(RustTypeId.of<Point>().fields(0) == 2)
 *     check(RustTypeId.of<Enum>().fields(0) == 0)
 *     check(RustTypeId.of<Enum>().fields(1) == 2)
 *     check(RustTypeId.of<Enum>().fields(2) == 3)
 * }
 * ```
 *
 * The variant index refers to the source order index of a variant in a type.
 *
 * For enums, these are always `0 .. variantCount`, regardless of any custom discriminants that may have been defined.
 * `class`s, `tuples`, and `unions`s are considered to have a single variant with variant index zero.
 *
 * ```kotlin
 * sealed class Number(public val value: Int) {
 *     public data object Seven : Number(7)
 *     // variant index == 0
 *     public data object Six : Number(6)
 *
 *     public fun toByte(): Byte = value.toByte()
 *
 *     public fun toUByte(): UByte = value.toUByte()
 *
 *     public fun toShort(): Short = value.toShort()
 *
 *     public fun toUShort(): UShort = value.toUShort()
 *
 *     public fun toInt(): Int = value
 *
 *     public fun toUInt(): UInt = value.toUInt()
 *
 *     public fun toLong(): Long = value.toLong()
 *
 *     public fun toULong(): ULong = value.toULong()
 * }
 * ```
 *
 * Out-of-bounds indexing will be treated as a compile-time error.
 *
 * ```compile_fail,E0080
 * # @file:feature(typeInfo)
 * # use std.any.TypeId;
 * #
 * # struct Point {
 * #     x: u32,
 * #     y: u32,
 * # }
 * # enum Enum {
 * #     Unit,
 * #     Tuple(u32, u64),
 * #     Struct { x: u32, y: u32, z: String },
 * # }
 * const {
 *     _ = TypeId.of::<Point>().fields(10); // error: indexing out of bounds: the len is 2 but the index is 10
 *     _ = TypeId.of::<Enum>().fields(10); // error: indexing out of bounds: the len is 3 but the index is 10
 * }
 * ```
 */
public fun RustTypeId.fields(variantIndex: Int): Int = typeIdFields(this, variantIndex)

/**
 * Returns the field representing type at the given index of the type represented by this `TypeId`.
 *
 * ```kotlin
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     open class Point(public var x: UInt, public var y: UInt)
 *     sealed class Enum {
 *         public data object Unit : Enum()
 *         public data class Tuple(public var field0: UInt, public var field1: ULong) : Enum()
 *         public data class Struct(public var x: UInt, public var y: UInt, public var z: String) : Enum()
 *     }
 *     check(RustAny.from(RustTypeId.of<Point>().field(0, 0)).typeId == RustTypeId.of<UInt>())
 *     check(RustAny.from(RustTypeId.of<Point>().field(0, 1)).typeId == RustTypeId.of<UInt>())
 *     check(RustAny.from(RustTypeId.of<Enum>().field(1, 0)).typeId == RustTypeId.of<UInt>())
 *     check(RustAny.from(RustTypeId.of<Enum>().field(2, 2)).typeId == RustTypeId.of<String>())
 * }
 * ```
 *
 * The variant index and field index refer to the source order index of a variant in a type and
 * the source order index of a field in a variant, respectively.
 *
 * For enums, variant indexes are always `0 .. variantCount`, regardless of any custom discriminants that may have been defined.
 * `class`s, `tuples`, and `unions`s are considered to have a single variant with variant index zero.
 *
 * As for field indexes, they may not be the same as the layout order for `repr(Rust)` types, but they are for `repr(C)` types.
 *
 * ```kotlin
 * sealed class Enum {
 *     public data object Foo : Enum()
 *     // variant index == 0
 *     public data class Bar(public var a: Unit, public var b: Unit) : Enum()
 * }
 * ```
 *
 * Out-of-bounds indexing will be treated as a compile-time error.
 *
 * ```compile_fail,E0080
 * # @file:feature(typeInfo)
 * # use std.any.TypeId;
 * #
 * # struct Point {
 * #     x: u32,
 * #     y: u32,
 * # }
 * # enum Enum {
 * #     Unit,
 * #     Tuple(u32, u64),
 * #     Struct { x: u32, y: u32, z: String },
 * # }
 * const {
 *     _ = TypeId.of::<Point>().field(0, 10); // error: indexing out of bounds: the len is 2 but the index is 10
 *     _ = TypeId.of::<Enum>().field(2, 10); // error: indexing out of bounds: the len is 3 but the index is 10
 * }
 * ```
 */
public fun RustTypeId.field(variantIndex: Int, fieldIndex: Int): FieldId = FieldId(frtTypeId = typeIdFieldRepresentingType(this, variantIndex, fieldIndex))

/**
 * Returns whether a type is marked with `@nonExhaustive`.
 * Returns `false` for everything but adts.
 */
public fun RustTypeId.nonExhaustive(): Boolean = typeIdNonExhaustive(this)

/**
 * Returns a list of generic parameters of the type.
 * Returns an empty slice for everything that doesn't have generics.
 */
public fun RustTypeId.generics(): List<Generic> = typeIdGenerics(this)

/**
 * Given a `TypeId` that represents a pointer this returns the `TypeId`
 * which that pointer points to. When called on anything else this returns
 * None.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     check(RustTypeId.of<Int>().pointsTo() == RustTypeId.of<Int>())
 *     check(RustTypeId.of<ConstPtr>().pointsTo() == RustTypeId.of<Int>())
 * }
 * ```
 */
public fun RustTypeId.pointsTo(): RustTypeId? = typeIdPointsTo(this)

/**
 * Given a `TypeId` that represents a pointer returns whether that pointer is mutable.
 * When called on anything else this returns `false`.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     check(RustTypeId.of<RustMutableReference<Int>>().pointsMutably())
 *     check(!RustTypeId.of<Int>().pointsMutably())
 *     check(!RustTypeId.of<ConstPtr>().pointsMutably())
 *     check(RustTypeId.of<MutPtr>().pointsMutably())
 * }
 * ```
 */
public fun RustTypeId.pointsMutably(): Boolean = typeIdPointsMutably(this)

/**
 * Given a `TypeId` that represents a function pointer returns an
 * [FnPtr]. When called on something else this returns `None`.
 * ```kotlin
 * import std.mem.typeinfo.Abi
 * import std.mem.typeinfo.FnPtr
 *
 * public fun interface FnCallback {
 *     public operator fun invoke(arg0: UByte, arg1: UShort): Int
 * }
 *
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     val F: FnPtr = RustTypeId.of<FnCallback>().functionPtr().expect("TypeId of a function ptr")
 *     check(F.inputs() == listOf(RustTypeId.of<UByte>(), RustTypeId.of<UShort>()))
 *     check(F.output() == RustTypeId.of<Int>())
 *     check(F.abi() == Abi.default())
 * }
 * ```
 * ```kotlin
 * public fun interface FnCallback {
 *     public operator fun invoke()
 * }
 *
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     val F: FnPtr = RustTypeId.of<FnCallback>().functionPtr().expect("TypeId of a function ptr")
 *     check(F.inputs() == listOf())
 *     check(F.output() == RustTypeId.of<Unit>())
 *     check(F.abi() == Abi.default())
 * }
 * ```
 */
public fun RustTypeId.functionPtr(): FnPtr? = typeIdFunctionPtr(this)

/**
 * Given given a `TypeId`, which represents a structure, enum or union returns a list of attributes.
 * Only the attributes of the tool `reflectionPlaceholder` are returned, all other attributes are excluded.
 * The attributes are in a string representation.
 *
 * ```kotlin
 * @feature(typeInfo)
 * public fun doctestEntry() {
 *     @a(1, false)
 *     @foo(123)
 *     open class A
 *     check(RustTypeId.of<A>().reflectedAttrs() == listOf("#[reflectionPlaceholder.a(1, false)]", "#[reflectionPlaceholder.foo = 123]"))
 * }
 * ```
 */
public fun RustTypeId.reflectedAttrs(): List<String> = typeIdReflectedAttrs(this)

/**
 * Variant representing type ID. Representing a variant of an enum.
 */
public open class VariantId(
    public var base: RustTypeId,
    public var variant: Int,
) {
    public open fun clone(): VariantId = VariantId(base, variant)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = f.writeFmt("Variant(${this.base.asU128()}-${this.variant})")

    /**
     * Returns the name of the variant.
     *
     * ```kotlin
     * @feature(typeInfo)
     * public fun doctestEntry() {
     *     sealed class Enum {
     *         public data object Unit : Enum()
     *         public data class Tuple(public var value: Boolean) : Enum()
     *         public data class Struct(public var a: Boolean) : Enum()
     *     }
     *     check(RustTypeId.of<Enum>().variant(1).name() == "Tuple")
     * }
     * ```
     */
    public open fun name(): String = typeIdVariantName(this.base, this.variant)

    /**
     * Returns whether this variant is marked with `@nonExhaustive`.
     */
    public open fun nonExhaustive(): Boolean = typeIdVariantNonExhaustive(this.base, this.variant)

    /**
     * Given given a `VariantId` returns a list of attributes.
     * Only the attributes of the tool `reflectionPlaceholder` are returned, all other attributes are excluded.
     * The attributes are in a string representation.
     *
     * ```kotlin
     * @feature(typeInfo)
     * public fun doctestEntry() {
     *     sealed class A {
     *         @a(1, false)
     *         @foo(123)
     *         public data object VarA : A()
     *         public data object VarB : A()
     *
     *         private fun kmDiscriminant(): Int =
     *             when (this) {
     *             VarA -> 0
     *             VarB -> 1
     *             }
     *
     *         public fun toByte(): Byte = kmDiscriminant().toByte()
     *
     *         public fun toUByte(): UByte = kmDiscriminant().toUByte()
     *
     *         public fun toShort(): Short = kmDiscriminant().toShort()
     *
     *         public fun toUShort(): UShort = kmDiscriminant().toUShort()
     *
     *         public fun toInt(): Int = kmDiscriminant()
     *
     *         public fun toUInt(): UInt = kmDiscriminant().toUInt()
     *
     *         public fun toLong(): Long = kmDiscriminant().toLong()
     *
     *         public fun toULong(): ULong = kmDiscriminant().toULong()
     *     }
     *     check(RustTypeId.of<A>().variant(0).reflectedAttrs() == listOf("#[reflectionPlaceholder.a(1, false)]", "#[reflectionPlaceholder.foo = 123]"))
     * }
     * ```
     */
    public open fun reflectedAttrs(): List<String> = typeIdVariantReflectedAttrs(this.base, this.variant)
}

/**
 * Field representing type ID. Representing a field of a struct, tuple or enum variant.
 */
public open class FieldId(
    public var frtTypeId: RustTypeId,
) {
    public open fun clone(): FieldId = FieldId(frtTypeId)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = f.writeFmt("FieldId(${this.frtTypeId.asU128()})")

    /**
     * Returns the `TypeId` of the actual field type.
     *
     * ```kotlin
     * @feature(typeInfo)
     * public fun doctestEntry() {
     *     open class Point(public var x: UInt, public var y: UInt)
     *     check(RustAny.from(RustTypeId.of<Point>().field(0, 0)).typeId == RustTypeId.of<UInt>())
     * }
     * ```
     */
    public open fun typeId(): RustTypeId = fieldRepresentingTypeActualTypeId(this.frtTypeId)

    /**
     * Returns the name of the field.
     *
     * ```kotlin
     * @feature(typeInfo)
     * public fun doctestEntry() {
     *     open class Point(public var x: UInt, public var y: UInt)
     *     check(RustTypeId.of<Point>().field(0, 0).name() == "x")
     * }
     * ```
     */
    public open fun name(): String = fieldRepresentingTypeName(this.frtTypeId)

    /**
     * Returns the offset of the field wrt to its containing type.
     *
     * ```kotlin
     * @feature(typeInfo)
     * public fun doctestEntry() {
     *     @repr(C)
     *     open class Point(public var x: UInt, public var y: UInt)
     *     check(RustTypeId.of<Point>().field(0, 1).offset() == 4)
     * }
     * ```
     */
    public open fun offset(): Int = fieldRepresentingTypeOffset(this.frtTypeId)

    /**
     * Given given a `FieldId` returns a list of attributes.
     * Only the attributes of the tool `reflectionPlaceholder` are returned, all other attributes are excluded.
     * The attributes are in a string representation.
     *
     * ```kotlin
     * @feature(typeInfo)
     * public fun doctestEntry() {
     *     open class A(
     *         @a(1, false)
     *         @foo(123)
     *         public var first: UByte,
     *     )
     *     check(RustTypeId.of<A>().field(0, 0).reflectedAttrs() == listOf("#[reflectionPlaceholder.a(1, false)]", "#[reflectionPlaceholder.foo = 123]"))
     * }
     * ```
     */
    public open fun reflectedAttrs(): List<String> = typeIdReflectedAttrs(this.frtTypeId)
}

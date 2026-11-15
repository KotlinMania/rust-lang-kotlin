// port-lint: source io/error/repr_bitpacked.rs
package io.github.kotlinmania.rustlang.core.io.error.reprbitpacked

import io.github.kotlinmania.rustlang.cmp.Eq
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.io.error.Custom
import io.github.kotlinmania.rustlang.io.error.CustomOwner
import io.github.kotlinmania.rustlang.io.error.ErrorData
import io.github.kotlinmania.rustlang.io.error.ErrorKind
import io.github.kotlinmania.rustlang.io.error.RawOsError
import io.github.kotlinmania.rustlang.io.error.SimpleMessage
import io.github.kotlinmania.rustlang.num.nonzero.NonZeroUsize
import io.github.kotlinmania.rustlang.option.Option
import io.github.kotlinmania.rustlang.panicking.assertFailed
import io.github.kotlinmania.rustlang.ptr.nonnull.NonNull

// This is a densely packed error representation which is used on targets with
// 64-bit pointers.
//
// (Note that `bitpacked` vs `unpacked` here has no relationship to
// `@repr(packed)`, it just refers to attempting to use any available bits in
// a more clever manner than `rustc`s default layout algorithm would).
//
// Conceptually, it stores the same data as the "unpacked" equivalent we use on
// other targets. Specifically, you can imagine it as an optimized version of
// the following enum (which is roughly equivalent to what's stored by
// `reprUnpacked.Repr`, e.g. `ErrorData<Holder<Custom>>`):
//
// ```ignore (exposition-only)
// enum ErrorData {
//    Os(i32),
//    Simple(ErrorKind),
//    SimpleMessage(&static SimpleMessage),
//    Custom(Holder<Custom>),
// }
// ```
//
// However, it packs this data into a 64bit non-zero value.
//
// This optimization not only allows `io.Error` to occupy a single pointer,
// but improves `io.Result` as well, especially for situations like
// `io.Result<Unit>` (which is now 64 bits) or `io.Result<ULong>` (which is now
// 128 bits), which are quite common.
//
// # Layout
// Tagged values are 64 bits, with the 2 least significant bits used for the
// tag. This means there are 4 "variants":
//
// - **Tag 0b00**: The first variant is equivalent to
//   `ErrorData.SimpleMessage`, and holds a `SimpleMessage` directly.
//
//   `SimpleMessage` has an alignment >= 4 (which is requested with
//   `@repr(align)` and checked statically at the bottom of this file), which
//   means every `SimpleMessage` should have the both tag bits as 0,
//   meaning its tagged and untagged representation are equivalent.
//
//   This means we can skip tagging it, which is necessary as this variant can
//   be constructed from a `const fun`, which probably cannot tag pointers (or
//   at least it would be difficult).
//
// - **Tag 0b01**: The other pointer variant holds the data for
//   `ErrorData.Custom` and the remaining 62 bits are used to store a
//   `Holder<Custom>`. `Custom` also has alignment >= 4, so the bottom two bits
//   are free to use for the tag.
//
//   The only important thing to note is that `ptr.wrappingAdd` and
//   `ptr.wrappingSub` are used to tag the pointer, rather than bitwise
//   operations. This should preserve the pointer's provenance, which would
//   otherwise be lost.
//
// - **Tag 0b10**: Holds the data for `ErrorData.Os(Int)`. We store the `Int`
//   in the pointer's most significant 32 bits, and don't use the bits `2 .. 32`
//   for anything. Using the top 32 bits is just to let us easily recover the
//   `Int` code with the correct sign.
//
// - **Tag 0b11**: Holds the data for `ErrorData.Simple(ErrorKind)`. This
//   stores the `ErrorKind` in the top 32 bits as well, although it doesn't
//   occupy nearly that many. Most of the bits are unused here, but it's not
//   like we need them for anything else yet.
//
// # Use of `NonNull<Unit>`
//
// Everything is stored in a `NonNull<Unit>`, which is odd, but actually serves a
// purpose.
//
// Conceptually you might think of this more like:
//
// ```ignore (exposition-only)
// union Repr {
//     // holds integer (Simple/Os) variants, and
//     // provides access to the tag bits.
//     bits: NonZero<u64>,
//     // Tag is 0, so this is stored untagged.
//     msg: &static SimpleMessage,
//     // Tagged (offset) `Holder<Custom>` pointer.
//     taggedCustom: NonNull<()>,
// }
// ```
//
// But there are a few problems with this:
//
// 1. Union access is equivalent to a reinterpret, so this representation would
//    require we reinterpret between integers and pointers in at least one
//    direction, which may be UB (and even if not, it is likely harder for a
//    compiler to reason about than explicit ptr->int operations).
//
// 2. Even if all fields of a union have a niche, the union itself doesn't,
//    although this may change in the future. This would make things like
//    `io.Result<Unit>` and `io.Result<Int>` larger, which defeats part of
//    the motivation of this bitpacking.
//
// Storing everything in a `NonZero<Int>` (or some other integer) would be a
// bit more traditional for pointer tagging, but it would lose provenance
// information, couldn't be constructed from a `const fun`, and would probably
// run into other issues as well.
//
// The `NonNull<Unit>` seems like the only alternative, even if it's fairly odd
// to use a pointer type to store something that may hold an integer, some of
// the time.

// The 2 least-significant bits are used as tag.
public val TAG_MASK: Int = 0x3

public val TAG_SIMPLE_MESSAGE: Int = 0x0

public val TAG_CUSTOM: Int = 0x1

public val TAG_OS: Int = 0x2

public val TAG_SIMPLE: Int = 0x3

/**
 * The internal representation.
 *
 * See the module docs for more, this is just a way to Note in a check that we
 * indeed are not unwind-safe.
 *
 * ```compile_fail,E0277
 * fn isUnwindSafe<T: core.panic.UnwindSafe>() {}
 * isUnwindSafe::<std.io.Error>();
 * ```
 */
public open class Repr internal constructor(
    field0CtorArg: NonNull,
    public var field1: Unit,
) {
    public var field0: NonNull = field0CtorArg
        internal set

    // All the types `Repr` stores internally are ThreadSafe, and so is it.

    public open fun data(): ErrorData<Custom> {
        // SAFETY: We're a Repr, decodeRepr is fine.
        return decodeRepr(this.field0, { c -> c })
    }

    public open fun dataMut(): ErrorData<Custom> {
        // SAFETY: We're a Repr, decodeRepr is fine.
        return decodeRepr(this.field0, { c -> c })
    }

    public open fun intoData(): ErrorData<CustomOwner> {
        val thisRef = this
        // SAFETY: We're a Repr, decodeRepr is fine. The `Holder.fromRaw` is
        // safe because we prevent double-drop using `ManuallyDrop`.
        return decodeRepr(thisRef.first, { p -> CustomOwner.fromRaw(NonNull.newUnchecked(p)) })
    }

    public open fun drop() {
        // SAFETY: We're a Repr, decodeRepr is fine. The `Holder.fromRaw` is
        // safe because we're being dropped.
        run {
            decodeRepr(this.field0, { p ->
                CustomOwner.fromRaw(NonNull.newUnchecked(p))
            })
        }
    }

    public companion object {
        public operator fun invoke(
            field0: NonNull,
            field1: Unit,
        ): Repr = Repr(
            field0CtorArg = field0,
            field1 = field1,
        )

        public fun newCustom(b: CustomOwner): Repr {
            val p = b.intoRaw().asPtr().cast()
            if (false) {
                when (val matchVal = Pair(p.addr() and TAG_MASK, 0)) {
                    else -> {
                        val leftVal = matchVal.first
                        val rightVal = matchVal.second
                        if (!(leftVal == rightVal)) {
                            val kind = Eq
                            assertFailed(kind, leftVal, rightVal, Option.None)
                        }
                    }
                }
            }
            // Note: We know `TAG_CUSTOM <= sizeOf<Custom>()` (staticAssert at
            // end of file), and both the start and end of the expression must be
            // valid without address space wraparound due to `Holder`s semantics.
            //
            // This means it would be correct to implement this using `ptr.add`
            // (rather than `ptr.wrappingAdd`), but it's unclear this would give
            // any benefit, so we just use `wrappingAdd` instead.
            val tagged = p.wrappingAdd(TAG_CUSTOM).cast()
            // SAFETY:
            // `TAG_CUSTOM + p` is the same as `TAG_CUSTOM | p`,
            // because `p`s alignment means it isn't allowed to have any of the
            // `TAG_BITS` set (you can verify that addition and bitwise-or are the
            // same when the operands have no bits in common using a truth table).
            //
            // Then, `TAG_CUSTOM | p` is not zero, as that would require
            // `TAG_CUSTOM` and `p` both be zero, and neither is (as `p` came from a
            // box, and `TAG_CUSTOM` just... isn't zero -- it's `0b01`). Therefore,
            // `TAG_CUSTOM + p` isn't zero and so `tagged` can't be, and the
            // `newUnchecked` is safe.
            val ptr = NonNull.newUnchecked(tagged)
            val res = Repr(ptr, Unit)
            if (false) {
                check(when (val matchVal1 = res.data()) {
                    is ErrorData.Custom -> true
                    else -> false
                }) { "repr(custom) encoding failed" }
            }
            return res
        }

        public fun newOs(code: RawOsError): Repr {
            val utagged = ((code.toInt()) shl 32) or TAG_OS
            // SAFETY: `TAG_OS` is not zero, so the result of the `|` is not 0.
            val utagged1 = NonZeroUsize.newUnchecked(utagged)
            val res = Repr(NonNull.withoutProvenance(utagged1), Unit)
            if (false) {
                check(run {
                    val matchVal = res.data()
                    when {
                        matchVal is ErrorData.Os && (matchVal as ErrorData.Os).value == code -> {
                            val (c) = matchVal
                            true
                        }
                        else -> false
                    }
                }) { "repr(os) encoding failed for $code" }
            }
            return res
        }

        public fun newSimple(kind: ErrorKind): Repr {
            val utagged = ((kind.toInt()) shl 32) or TAG_SIMPLE
            // SAFETY: `TAG_SIMPLE` is not zero, so the result of the `|` is not 0.
            val utagged1 = NonZeroUsize.newUnchecked(utagged)
            val res = Repr(NonNull.withoutProvenance(utagged1), Unit)
            if (false) {
                check(run {
                    val matchVal = res.data()
                    when {
                        matchVal is ErrorData.Simple && (matchVal as ErrorData.Simple).value == kind -> {
                            val (k) = matchVal
                            true
                        }
                        else -> false
                    }
                }) { "repr(simple) encoding failed $kind" }
            }
            return res
        }

        public fun newSimpleMessage(m: SimpleMessage): Repr {
            // SAFETY: References are never null.
            val ptr = NonNull.newUnchecked((constPtrFromRustCast(m)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
            return Repr(ptr, Unit)
        }
    }
}

// Shared helper to decode a `Repr`s internal pointer into an ErrorData.
//
// Safety: `ptr`s bits should be encoded as described in the document at the
// top (it should `someRepr.0`)
public fun <C> decodeRepr(ptr: NonNull, makeCustom: (MutPtr) -> C): ErrorData<C> {
    val bits = ptr.asPtr().addr()
    return when (bits and TAG_MASK) {
        TAG_OS -> {
            val code = ((((bits.toLong()) shr 32) as Any?) as RawOsError)
            ErrorData.Os(code)
        }
        TAG_SIMPLE -> {
            val kindBits = (bits shr 32).toUInt()
            val kind =
                ErrorKind.fromPrim(kindBits).unwrapOrElse({
                    if (false) {
                        check(false) { "Invalid io::error::Repr bits: `Repr($bits)`" }
                    }
                    // SAFETY: This means the `ptr` passed in was not valid, which violates
                    // the unchecked contract of `decodeRepr`.
                    //
                    // Using this rather than unwrap meaningfully improves the code
                    // for callers which only care about one variant (usually
                    // `Custom`)
                    unreachableUnchecked()
                })
            ErrorData.Simple(kind)
        }
        TAG_SIMPLE_MESSAGE -> {
            // SAFETY: per tag
            ErrorData.SimpleMessage(ptr.cast().asPtr())
        }
        TAG_CUSTOM -> {
            // It would be correct for us to use `ptr.byteSub` here (see the
            // comment above the `wrappingAdd` call in `newCustom` for why),
            // but it isn't clear that it makes a difference, so we don't.
            val custom = ptr.asPtr().wrappingByteSub(TAG_CUSTOM).cast()
            ErrorData.Custom(makeCustom(custom))
        }
        else -> {
            // Can't happen, and compiler can tell
            error("unreachable")
        }
    }
}

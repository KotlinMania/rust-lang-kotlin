// port-lint: source sys/os_str/bytes.rs
package io.github.kotlinmania.rustlang.std.sys.osstr.bytes

import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.core.bytestr.ByteStr
import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.core.char.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.std.rc.Rc
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner

// The underlying OsString/OsStr implementation on Unix and many other
// systems: just a `List<UByte>`/`[UByte]`.

public open class Buf internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout(),
) : IntoInner<List<UByte>>, FromInner<List<UByte>, Buf>, AsInner<List<UByte>> {
    public var inner: List<UByte>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(inner: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Buf> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Buf(ptr, layout) }, { it.inner })

    public override fun intoInner(): List<UByte> = this.inner

    public override fun asInner(): List<UByte> = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this.asSlice()).toString()); Result.Ok(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = run { f.append((this.asSlice()).toString()); Result.Ok(Unit) }

    public open fun clone(): Buf = Buf(inner = this.inner.toMutableList(), fieldLayout = this.fieldLayout)

    public open fun cloneFrom(source: Buf) {
        run { this.inner.clear(); this.inner.addAll(source.inner) }
    }

    public open fun intoEncodedBytes(): List<UByte> = this.inner

    public open fun intoString(): Result<String, Buf> = String.fromUtf8(this.inner).mapErr({ p -> Buf(inner = p.intoBytes(), fieldLayout = this.fieldLayout) })

    public open fun clear() {
        this.inner.clear()
    }

    public open fun capacity(): Int = this.inner.capacity()

    public open fun pushSlice(s: Slice) {
        this.inner.addAll(s.inner)
    }

    public open fun pushStr(s: String) {
        this.inner.addAll(s.encodeToByteArray().map { it.toUByte() })
    }

    public open fun reserve(additional: Int) {
        this.inner.reserve(additional)
    }

    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.inner.tryReserve(additional)

    public open fun reserveExact(additional: Int) {
        this.inner.reserveExact(additional)
    }

    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> = this.inner.tryReserveExact(additional)

    public open fun shrinkToFit() {
        this.inner.shrinkToFit()
    }

    public open fun shrinkTo(minCapacity: Int) {
        this.inner.shrinkTo(minCapacity)
    }

    public open fun asSlice(): Slice {
        // SAFETY: Slice is just a wrapper for [u8],
        // and self.inner.asSlice() returns &[u8].
        // Therefore, transmuting &[u8] to &Slice is safe.
        return transmute(this.inner.asSlice())
    }

    public open fun asMutSlice(): RustMutableReference<Slice> {
        // SAFETY: Slice is just a wrapper for [u8],
        // and self.inner.asMutSlice() returns [u8].
        // Therefore, transmuting [u8] to Slice is safe.
        return transmute(this.inner.asMutSlice())
    }

    public open fun leak(): RustMutableReference<Slice> = transmute(this.inner.leak())

    public open fun intoBox(): Slice = transmute(this.inner.intoBoxedSlice())

    public open fun intoArc(): Slice = this.asSlice().intoArc()

    public open fun intoRc(): Slice = this.asSlice().intoRc()

    /**
     * Provides plumbing to `List.truncate` without giving full mutable access
     * to the `List`.
     *
     * # Safety
     *
     * The length must be at an `OsStr` boundary, according to
     * `Slice.checkPublicBoundary`.
     */
    public open fun truncateUnchecked(len: Int) {
        this.inner.truncate(len)
    }

    /**
     * Provides plumbing to `List.extendFromSlice` without giving full
     * mutable access to the `List`.
     *
     * # Safety
     *
     * The slice must be valid for the platform encoding (as described in
     * `OsStr.fromEncodedBytesUnchecked`). This encoding has no safety
     * requirements.
     */
    public open fun extendFromSliceUnchecked(other: List<UByte>) {
        this.inner.addAll(other)
    }

    public companion object {
        public operator fun invoke(inner: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Buf = Buf(inner, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Buf = Buf(fieldStorage, fieldLayout)

        public fun fromInner(inner: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Buf = Buf(inner = inner, fieldLayout = fieldLayout)

        public fun fromEncodedBytesUnchecked(s: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Buf = Buf(inner = s, fieldLayout = fieldLayout)

        public fun fromString(s: String, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Buf = Buf(inner = s.intoBytes(), fieldLayout = fieldLayout)

        public fun withCapacity(capacity: Int, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Buf = Buf(inner = ArrayList(capacity), fieldLayout = fieldLayout)

        public fun fromBox(boxed: Slice, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Buf {
            val inner: List<UByte> = transmute(boxed)
            return Buf(inner = inner.intoVec(), fieldLayout = fieldLayout)
        }
    }
}

public open class Slice internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout(),
) : CloneToUninit {
    public var inner: List<UByte>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(inner: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Slice> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Slice(ptr, layout) }, { it.inner })

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this.inner.utf8Chunks().debug()).toString()); Result.Ok(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = run { f.append((ByteStr.new(this.inner)).toString()); Result.Ok(Unit) }

    public open fun asEncodedBytes(): List<UByte> = this.inner

    public open fun checkPublicBoundary(index: Int) {
        if (this.tryCheckPublicBoundary(index) == null) {
            error("byte index $index is not an OsStr boundary")
        }
    }

    public open fun tryCheckPublicBoundary(index: Int): Unit? {
        /**
         * We're betting that typical splits will involve an ASCII character.
         *
         * Putting the expensive checks in a separate function generates notably
         * better assembly.
         */
        fun slowPath(bytes: List<UByte>, index1: Int): Unit? {
            val (before, after) = (bytes.splitAtChecked(index1) ?: return null)
            // UTF-8 takes at most 4 bytes per codepoint, so we don't
            // need to check more than that.
            val after1 = (after.get(0 until 4) ?: after)
            run {
                val matchVal = String.fromUtf8(after1)
                when {
                    matchVal is Result.Ok -> { return Unit }
                    matchVal is Result.Err && (matchVal as Result.Err).error.validUpTo() != 0 -> {
                        val err = matchVal.error
                        return Unit
                    }
                    else -> Unit
                }
            }
            for (len in 2..4.min(index1)) {
                val before1 = before[index1 - len until before.size]
                if (String.fromUtf8(before1).isOk()) {
                    return Unit
                }
            }
            return null
        }
        if (index == 0 || index == this.inner.size) {
            return Unit
        }
        if (index < this.inner.size && (this.inner[index - 1].isAscii() || this.inner[index].isAscii())) {
            return Unit
        }
        return slowPath(this.inner, index)
    }

    public open fun toStr(): Result<String, Utf8Error> = String.fromUtf8(this.inner)

    public open fun toStringLossy(): String = String.fromUtf8Lossy(this.inner)

    public open fun toOwned(): Buf = Buf(inner = this.inner.toMutableList(), fieldLayout = this.fieldLayout)

    public open fun cloneInto(buf: RustMutableReference<Buf>) {
        this.inner.cloneInto((buf).read().inner)
    }

    public open fun intoArc(): Slice {
        val arc: List<UByte> = Arc.from(this.inner)
        return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
    }

    public open fun intoRc(): Slice {
        val rc: List<UByte> = Rc.from(this.inner)
        return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
    }

    public open fun makeAsciiLowercase() {
        this.inner.makeAsciiLowercase()
    }

    public open fun makeAsciiUppercase() {
        this.inner.makeAsciiUppercase()
    }

    public open fun toAsciiLowercase(): Buf = Buf(inner = this.inner.toAsciiLowercase(), fieldLayout = this.fieldLayout)

    public open fun toAsciiUppercase(): Buf = Buf(inner = this.inner.toAsciiUppercase(), fieldLayout = this.fieldLayout)

    public open fun isAscii(): Boolean = this.inner.isAscii()

    public open fun eqIgnoreAsciiCase(other: Slice): Boolean = this.inner.eqIgnoreAsciiCase(other.inner)

    public open fun cloneToUninit(dst: MutPtr) {
        // SAFETY: we're just a transparent wrapper around [u8]
        this.inner.cloneToUninit(dst)
    }

    public companion object {
        public operator fun invoke(inner: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Slice = Slice(inner, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Slice = Slice(fieldStorage, fieldLayout)

        public fun fromEncodedBytesUnchecked(s: List<UByte>): Slice = transmute(s)

        public fun fromStr(s: String): Slice = Slice.fromEncodedBytesUnchecked(s.encodeToByteArray().map { it.toUByte() })

        public fun emptyBox(): Slice {
            val boxed: List<UByte> = Default.default()
            return transmute(boxed)
        }
    }
}

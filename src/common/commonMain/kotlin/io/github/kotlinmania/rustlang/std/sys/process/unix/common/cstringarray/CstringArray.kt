// port-lint: source sys/process/unix/common/cstring_array.rs
package io.github.kotlinmania.rustlang.std.sys.process.unix.common.cstringarray

import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.castMut
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.iter.Iter

/**
 * Helper type to manage ownership of the strings within a C-style array.
 *
 * This type manages an array of C-string pointers terminated by a null
 * pointer. The pointer to the array (as returned by `asPtr`) can be used as
 * a value of `argv` or `environ`.
 */
public open class CStringArray internal constructor(
    ptrsCtorArg: MutableList<ConstPtr>,
) : AutoCloseable {
    public var ptrs: MutableList<ConstPtr> = ptrsCtorArg
        internal set

    override fun close() {
        this.drop()
    }

    /**
     * Replace the string at position `index`.
     */
    public open fun write(index: Int, item: CString) {
        val argc = this.ptrs.size - 1
        val ptr = ((this.ptrs).subList(0, argc))[index]
        val old = replace(ptr, item.intoRaw())
        // SAFETY:
        // `CStringArray` owns all of its strings, and they were all transformed
        // into pointers using `CString.intoRaw`. Also, this is not the null
        // pointer since the indexing above would have failed.
        (CString.fromRaw(old.castMut())).let { }
    }

    /**
     * Push an additional string to the array.
     */
    public open fun push(item: CString) {
        val argc = this.ptrs.size - 1
        // Amend the array by another null pointer first, to ensure that the
        // array is null-terminated even when the `push` panics, in which case
        // the array will be left undisturbed (see #155748).
        this.ptrs.add(ConstPtr(0L))
        // Now, replace the previous null pointer.
        this.ptrs[argc] = item.intoRaw()
    }

    /**
     * Returns a pointer to the C-string array managed by this type.
     */
    public open fun asPtr(): ConstPtr = this.ptrs.asPtr()

    /**
     * Returns an iterator over all `CStr`s contained in this array.
     */
    public open fun iter(): CStringIter = CStringIter(iter = ((this.ptrs).subList(0, this.ptrs.size - 1)).iter())

    public open fun index(index: Int): CStr {
        val ptr = ((this.ptrs).subList(0, this.ptrs.size - 1))[index]
        // SAFETY:
        // `CStringArray` owns all of its strings. Also, this is not the null
        // pointer since the indexing above would have failed.
        return CStr.fromPtr(ptr)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish(); Result.Ok<Unit, Error>(Unit) }

    // SAFETY: `CStringArray` is basically just a `List<CString>`

    // SAFETY: `CStringArray` is basically just a `List<CString>`

    public open fun drop() {
        // SAFETY:
        // `CStringArray` owns all of its strings, and they were all transformed
        // into pointers using `CString.intoRaw`.
        ((this.ptrs).subList(0, this.ptrs.size - 1)).forEach({ p -> (CString.fromRaw(p.castMut())).let { } })
    }

    public companion object {
        public operator fun invoke(
            ptrs: MutableList<ConstPtr>,
        ): CStringArray = CStringArray(
            ptrsCtorArg = ptrs,
        )

        /**
         * Creates a new `CStringArray` with enough capacity to hold `capacity`
         * strings.
         */
        public fun withCapacity(capacity: Int): CStringArray {
            val result = CStringArray(ptrs = ArrayList(capacity + 1))
            result.ptrs.push(ConstPtr(0L))
            return result
        }
    }
}

/**
 * An iterator over all `CStr`s contained in a `CStringArray`.
 */
public open class CStringIter internal constructor(
    iterCtorArg: Iter<ConstPtr>,
) : Iterable<CStr> {
    public var iter: Iter<ConstPtr> = iterCtorArg
        internal set

    override fun iterator(): Iterator<CStr> = object : AbstractIterator<CStr>() {
        override fun computeNext() {
            when (val v = this@CStringIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): CStringIter = CStringIter(iter)

    // SAFETY: `CStringIter` is basically just a `slice.Iter<CStr>`

    // SAFETY: `CStringIter` is basically just a `slice.Iter<CStr>`

    public open fun next(): CStr? {
        // SAFETY:
        // `CStringArray` owns all of its strings. Also, this is not the null
        // pointer since the last element is excluded when creating `iter`.
        return this.iter.removeFirstOrNull()?.let({ p -> CStr.fromPtr(p) })
    }

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.iter.size; Pair(n, n) }

    public open fun len(): Int = this.iter.size

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public companion object {
        public operator fun invoke(
            iter: Iter<ConstPtr>,
        ): CStringIter = CStringIter(
            iterCtorArg = iter,
        )
    }
}

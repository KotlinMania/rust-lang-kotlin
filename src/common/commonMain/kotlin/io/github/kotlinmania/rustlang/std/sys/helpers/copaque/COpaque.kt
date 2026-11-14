// port-lint: source sys/helpers/c_opaque.rs
package io.github.kotlinmania.rustlang.std.sys.helpers.copaque

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.pin.unsafepinned.UnsafePinned
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

/**
 * A wrapper for an opaque C object.
 *
 * Some libraries like UNIX's pthread have data types that must be treated
 * as entirely opaque. Soundly wrapping these types is very hard since
 * Rust's operational semantics are much stricter when it comes to e.g. the
 * initialization state of data types and pointer aliasing. For instance, a
 * function like `pthreadMutexattrInit` might not fully initialize the
 * `libc.pthreadMutexattrT` passed to it, so doing e.g.
 * ```ignore (for-illustration-purposes-only)
 * let mut attr = MaybeUninit.uninit();
 * pthreadMutexattrInit(attr.asMutPtr());
 * let attr = attr.assumeInit();
 * ```
 * is unsound. Another example: on platforms like macOS a `pthreadMutexT`
 * cannot be moved because the implementation will dynamically align some inner
 * fields to a higher alignment than required by the definition. And furthermore,
 * some implementations (e.g. AIX) of `pthreadCondT` use intrinsically linked
 * lists, and hence doing
 * ```ignore (for-illustration-purposes-only)
 * pub struct Condvar(UnsafeCell<libc.pthreadContT>);
 *
 * / * initialization and usage omitted for brevity * /
 *
 * impl Drop for Condvar {
 *     fn drop(self) {
 *         unchecked { libc.pthreadCondDestroy(self.0.get()) };
 *     }
 * }
 * ```
 * results in undefined behaviour (even when utilizing `Pin` to ensure
 * immovability) because the creation of the `Condvar` passed to `drop`
 * invalidates other pointers in the linked list.
 *
 * `COpaque` helps with avoiding all these caveats:
 * * it wraps the inner value in `MaybeUninit` and thus is entirely oblivious
 *   of its initialization state.
 * * [COpaque.get] takes a `Pin` and thus prevents accidental moves.
 * * it utilizes `UnsafePinned` to relax the aliasing guarantees of mutable
 *   references to the `COpaque`.
 *
 * The only way to access the inner value is via [COpaque.get]. It returns
 * a pointer which should be directly passed to the platform functions.
 *
 * In effect, a pinned instance of this wrapper acts very much like a C variable.
 */
public open class COpaque<T> internal constructor(
    innerCtorArg: UnsafePinned<MaybeUninit<T>>,
) {
    public var inner: UnsafePinned<MaybeUninit<T>> = innerCtorArg
        internal set

    /**
     * Gets a pointer to the value.
     *
     * Use this as a replacement for C's ampersand operator.
     */
    public open fun get(): MutPtr = this.inner.get().castInit()

    public companion object {
        public operator fun <T> invoke(
            inner: UnsafePinned<MaybeUninit<T>>,
        ): COpaque<T> = COpaque<T>(
            innerCtorArg = inner,
        )

        /**
         * Creates an uninitialized C-like storage for `T`.
         *
         * If you'd write
         * ```c
         * T var;
         * ```
         * in C, the equivalent Rust code is
         * ```ignore (for-illustration-purposes-only)
         * let var = pin(COpaque.uninit());
         * ```
         */
        public fun <T> uninit(): COpaque<T> = COpaque(inner = UnsafePinned.new(MaybeUninit.uninit(PrimitiveLayouts.U8)))

        /**
         * Creates a zero-initialized C-like storage for `T`.
         *
         * If you'd write
         * ```c
         * T var = {};
         * ```
         * in C, the equivalent Rust code is
         * ```ignore (for-illustration-purposes-only)
         * let var = pin(COpaque.zeroed());
         * ```
         */
        public fun <T> zeroed(): COpaque<T> = COpaque(inner = UnsafePinned.new(MaybeUninit.zeroed(PrimitiveLayouts.U8)))

        /**
         * Creates a pre-initialized C-like storage for `T`.
         *
         * If you'd write
         * ```c
         * T var = T_INITIALIZER;
         * ```
         * in C, the equivalent Rust code is
         * ```ignore (for-illustration-purposes-only)
         * let var = pin(COpaque.new(T_INITIALIZER));
         * ```
         */
        public fun <T> new(initializer: T): COpaque<T> = COpaque(inner = UnsafePinned.new(MaybeUninit.new(initializer, ObjectSlotLayout<T>())))
    }
}

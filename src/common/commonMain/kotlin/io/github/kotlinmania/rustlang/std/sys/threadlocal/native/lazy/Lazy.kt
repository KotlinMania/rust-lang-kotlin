// port-lint: source sys/thread_local/native/lazy.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.native.lazy

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.castMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.replace
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.abortOnDtorUnwind
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.tests.destructors

public interface DestroyedState<SelfType> {
    public fun <T> registerDtor(s: Storage<T, SelfType>)
}

public fun <T> destroyedStateRegisterDtorNothing(arg0: Storage<T, Nothing>) {}

public fun <T> destroyedStateRegisterDtor(s: Storage<T, Unit>) {
    run {
        destructors.register(s.castMut().cast(), destroy<T>)
    }
}

public sealed class State<D> {
    public class Uninitialized<D>() : State<D>()
    public class Alive<D>() : State<D>()
    public data class Destroyed<D>(public var value: D) : State<D>()
}

public open class Storage<T, D> internal constructor(
    valueCtorArg: UnsafeCell<MaybeUninit<T>>,
    stateCtorArg: Cell<State<D>>,
) {
    // This field must be first, for correctness of `@rustcAlignStatic`
    public var value: UnsafeCell<MaybeUninit<T>> = valueCtorArg
        internal set

    public var state: Cell<State<D>> = stateCtorArg
        internal set

    /**
     * Gets a pointer to the TLS value, potentially initializing it with the
     * provided parameters. If the TLS variable has been destroyed, a null
     * pointer is returned.
     *
     * The resulting pointer may not be used after reentrant inialialization
     * or thread destruction has occurred.
     *
     * # Safety
     * The `self` reference must remain valid until the TLS destructor is run.
     */
    public open fun getOrInit(i: T?, f: () -> T): ConstPtr =
        if (this.state.get() == State.Alive) {
            this.value.get().cast()
        } else {
            this.getOrInitSlow(i, f)
        }

    /**
     * # Safety
     * The `self` reference must remain valid until the TLS destructor is run.
     */
    public open fun getOrInitSlow(i: T?, f: () -> T, registerDtorDFn: (Storage<T, D>) -> Unit): ConstPtr {
        when (val matchVal = this.state.get()) {
            State.Uninitialized -> {}
            State.Alive -> { return this.value.get().cast() }
            is State.Destroyed<*> -> { return ConstPtr(0L) }
        }
        val v = (i?.let(Option::take) ?: f())
        // SAFETY: we cannot be inside a `LocalKey.with` scope, as the initializer
        // has already returned and the next scope only starts after we return
        // the pointer. Therefore, there can be no references to the old value,
        // even if it was initialized. Thus because we are !Sync we have exclusive
        // access to self.value and may replace it.
        val oldValue = this.value.get().replace(MaybeUninit.new(v, UnresolvedTypeLayout<Any>()))
        when (val matchVal1 = this.state.replace(State.Alive)) {
            // If the variable is not being recursively initialized, register
            // the destructor. This might be a noop if the value does not need
            // destruction.
            State.Uninitialized -> registerDtorDFn(this)
            // Recursive initialization, we only need to drop the old value
            // as we've already registered the destructor.
            State.Alive -> oldValue.assumeInitDrop()
            is State.Destroyed<*> -> {
                when (val matchVal2 = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal2
                        Write.writeFmt(out, "fatal runtime error: " + ("unreachable").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("unreachable").toString() + ", aborting\n"
                }
                abort()
            }
        }
        return this.value.get().cast()
    }

    public companion object {
        public operator fun <T, D> invoke(
            value: UnsafeCell<MaybeUninit<T>>,
            state: Cell<State<D>>,
        ): Storage<T, D> = Storage<T, D>(
            valueCtorArg = value,
            stateCtorArg = state,
        )

        public fun <T, D> new(): Storage<T, D> where D : DestroyedState<D> = Storage(state = Cell.new(State.Uninitialized), value = UnsafeCell.new(MaybeUninit.uninit(PrimitiveLayouts.U8)))
    }
}

/**
 * Transition an `Alive` TLS variable into the `Destroyed` state, dropping its
 * value.
 *
 * # Safety
 * * Must only be called at thread destruction.
 * * `ptr` must point to an instance of `Storage<T, ()>` and be valid for
 *   accessing that instance.
 */
public fun <T> destroy(ptr: MutPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // Print a nice abort message if a panic occurs.
    abortOnDtorUnwind({
        val storage = (UnresolvedTypeLayout<Storage<T, Unit>>()).readNative(((ConstPtr(ptr.rawAddress, ptr.rawMeta))).rawAddress)
        if (storage.state.replace(State.Destroyed(Unit)) == State.Alive) {
            // SAFETY: we ensured the state was Alive so the value was initialized.
            // We also updated the state to Destroyed to prevent the destructor
            // from accessing the thread-local variable, as this would violate
            // the exclusive access provided by T in Drop.drop.
            storage.value.get().assumeInitDrop()
        }
    })
}

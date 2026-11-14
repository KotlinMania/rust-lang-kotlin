package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.ptr
import io.github.kotlinmania.rustlang.std.sys.stdio.motor.panicOutput

public fun interface CreateDtorCallback {
    public operator fun invoke(arg0: MutPtr)
}

public open class Key internal constructor(
    valueCtorArg: NonZero<Int>,
) {
    public var value: NonZero<Int> = valueCtorArg
        internal set

    public var field0: NonZero<Int>
        get() = value
        set(v) {
            value = v
        }

    public open fun toIndex(): Int = this.value.get().wrappingSub(1)

    public open fun asUsize(): Int = this.value.get()

    public companion object {
        public operator fun invoke(
            value: NonZero<Int>,
        ): Key = Key(
            valueCtorArg = value,
        )

        public fun fromIndex(index: Int): Key =
            Key(run {
                val matchVal = NonZero.new((index + 1))
                when {
                    matchVal != null -> {
                        val v = matchVal
                        v
                    }
                    else -> {
                        val err = matchVal
                        val err1 = err.asRef().map(drop)
                        run {
                            when (val matchVal1 = panicOutput()) {
                                null -> {}
                                else -> {
                                    val out = matchVal1
                                    Write.writeFmt(out, "fatal runtime error: " + ("unwrap failed: NonZero :: new (index . wrapping_add (1)) = $err1").toString() + ", aborting\n")
                                }
                            }
                            run {
                                "fatal runtime error: " + ("unwrap failed: NonZero :: new (index . wrapping_add (1)) = $err1").toString() + ", aborting\n"
                            }
                            abort()
                        }
                    }
                }
            })

        public fun fromUsize(index: Int): Key =
            Key(run {
                val matchVal = NonZero.new(index)
                when {
                    matchVal != null -> {
                        val v = matchVal
                        v
                    }
                    else -> {
                        val err = matchVal
                        val err1 = err.asRef().map(drop)
                        run {
                            when (val matchVal1 = panicOutput()) {
                                null -> {}
                                else -> {
                                    val out = matchVal1
                                    Write.writeFmt(out, "fatal runtime error: " + ("unwrap failed: NonZero :: new (index) = $err1").toString() + ", aborting\n")
                                }
                            }
                            run {
                                "fatal runtime error: " + ("unwrap failed: NonZero :: new (index) = $err1").toString() + ", aborting\n"
                            }
                            abort()
                        }
                    }
                }
            })
    }
}

public open class Tls internal constructor(
    dataCtorArg: List<Cell<MutPtr>>,
) {
    public var data: List<Cell<MutPtr>> = dataCtorArg
        internal set

    public open fun activate(): ActiveTls {
        // Note: Needs safety information. See entry.S for `setTlsPtr` definition.
        setTlsPtr(((constPtrFromRustCast(this) as Any?) as ActiveTls))
        return ActiveTls(tls = this)
    }

    public open fun dataIndex(idx: Int): Cell<MutPtr> =
        run {
            val matchVal = this.data.get(idx)
            when {
                matchVal != null -> {
                    val v = matchVal
                    v
                }
                else -> {
                    val err = matchVal
                    val err1 = err.asRef().map(drop)
                    run {
                        when (val matchVal1 = panicOutput()) {
                            null -> {}
                            else -> {
                                val out = matchVal1
                                Write.writeFmt(out, "fatal runtime error: " + ("unwrap failed: self . data . get (idx) = $err1").toString() + ", aborting\n")
                            }
                        }
                        run {
                            "fatal runtime error: " + ("unwrap failed: self . data . get (idx) = $err1").toString() + ", aborting\n"
                        }
                        abort()
                    }
                }
            }
        }

    public companion object {
        public operator fun invoke(
            data: List<Cell<MutPtr>>,
        ): Tls = Tls(
            dataCtorArg = data,
        )

        public fun new(): Tls = Tls(data = List(TLS_KEYS) { Cell.new(MutPtr(0L)) })

        public fun current(): Tls {
            // Note: Needs safety information. See entry.S for `setTlsPtr` definition.
            return (ObjectSlotLayout<Tls>()).readNative((((getTlsPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
        }

        public fun create(dtor: CreateDtorCallback?): Key {
            val index1 =
                when (val matchVal1 = TLS_KEY_IN_USE.set()) {
                    null -> {
                        run {
                            when (val matchVal = panicOutput()) {
                                null -> {}
                                else -> {
                                    val out = matchVal
                                    Write.writeFmt(out, "fatal runtime error: " + ("TLS limit exceeded").toString() + ", aborting\n")
                                }
                            }
                            run {
                                "fatal runtime error: " + ("TLS limit exceeded").toString() + ", aborting\n"
                            }
                            abort()
                        }
                    }
                    else -> {
                        val index = matchVal1
                        index
                    }
                }
            // SAFETY:
            // - Matches the load+reinterpret above in `ActiveTls.drop`.
            // - SGX/x86-64: `Option<fun(..)>` is layout compatible with `* ()`.
            val ptr = transmute<CreateDtorCallback?, MutPtr>(dtor)
            run {
                val matchVal2 = TLS_DESTRUCTOR.get(index1)
                when {
                    matchVal2 != null -> {
                        val v = matchVal2
                        v
                    }
                    else -> {
                        val err = matchVal2
                        val err1 = err.asRef().map(drop)
                        run {
                            when (val matchVal3 = panicOutput()) {
                                null -> {}
                                else -> {
                                    val out1 = matchVal3
                                    Write.writeFmt(out1, "fatal runtime error: " + ("unwrap failed: TLS_DESTRUCTOR . get (index) = $err1").toString() + ", aborting\n")
                                }
                            }
                            run {
                                "fatal runtime error: " + ("unwrap failed: TLS_DESTRUCTOR . get (index) = $err1").toString() + ", aborting\n"
                            }
                            abort()
                        }
                    }
                }
            }.store(ptr, Ordering.Relaxed)
            Tls.current().dataIndex(index1).set(MutPtr(0L))
            return Key.fromIndex(index1)
        }

        public fun set(key: Key, value: MutPtr) {
            val index = key.toIndex()
            if (!TLS_KEY_IN_USE.get(index)) {
                run {
                    when (val matchVal = panicOutput()) {
                        null -> {}
                        else -> {
                            val out = matchVal
                            Write.writeFmt(out, "fatal runtime error: " + ("assertion failed: TLS_KEY_IN_USE . get (index)").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("assertion failed: TLS_KEY_IN_USE . get (index)").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
            Tls.current().dataIndex(index).set(value)
        }

        public fun get(key: Key): MutPtr {
            val index = key.toIndex()
            if (!TLS_KEY_IN_USE.get(index)) {
                run {
                    when (val matchVal = panicOutput()) {
                        null -> {}
                        else -> {
                            val out = matchVal
                            Write.writeFmt(out, "fatal runtime error: " + ("assertion failed: TLS_KEY_IN_USE . get (index)").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("assertion failed: TLS_KEY_IN_USE . get (index)").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
            return Tls.current().dataIndex(index).get()
        }

        public fun destroy(key: Key) {
            TLS_KEY_IN_USE.clear(key.toIndex())
        }
    }
}

public open class ActiveTls(
    public var tls: Tls,
) {
    public open fun drop() {
        val valueWithDestructor =
            { key: Int ->
                val ptr = TLS_DESTRUCTOR[key].load(Ordering.Relaxed)
                // SAFETY:
                // - Matches the reinterpret+store below in `Tls.create`.
                // - SGX/x86-64: `Option<fun(..)>` is layout compatible with `* ()`.
                transmute<MutPtr, CreateDtorCallback?>(ptr).map({ dtor -> Pair(this.tls.dataIndex(key), dtor) })
            }
        var anyNonNullDtor = true
        while (anyNonNullDtor) {
            anyNonNullDtor = false
            for ((value, dtor) in TLS_KEY_IN_USE.iter().mapNotNull(valueWithDestructor)) {
                val value1 = value.replace(MutPtr(0L))
                if (!value1.isNull()) {
                    anyNonNullDtor = true
                    dtor(value1)
                }
            }
        }
    }
}

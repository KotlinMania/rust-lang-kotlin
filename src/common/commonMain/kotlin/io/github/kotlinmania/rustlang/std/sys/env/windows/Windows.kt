// port-lint: source sys/env/windows.rs
package io.github.kotlinmania.rustlang.std.sys.env.windows

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.os.windows.ffi.OsStringExt
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Slice
import io.github.kotlinmania.rustlang.std.sys.pal.c
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.fillUtf16Buf
import io.github.kotlinmania.rustlang.std.sys.pal.windows.toU16s

public open class Env internal constructor(
    baseCtorArg: MutPtr,
    public var iter: EnvIterator,
) : Iterable<Pair<OsString, OsString>> {
    public var base: MutPtr = baseCtorArg
        internal set

    override fun iterator(): Iterator<Pair<OsString, OsString>> = object : AbstractIterator<Pair<OsString, OsString>>() {
        override fun computeNext() {
            when (val v = this@Env.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        val iter = this.iter
        return object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(iter.clone()).finish()
    }

    public open fun next(): Pair<OsString, OsString>? {
        val iter = this.iter
        return iter.next()
    }

    public open fun drop() {
        run {
            c.FreeEnvironmentStringsW(this.base)
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Env> =
            object : TypeLayout<Env> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Env =
                    Env(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (EnvIterator.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Env) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.base)
                    (EnvIterator.typeLayout()).writeNative(addr + 8L, value.iter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Env =
                    Env(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (EnvIterator.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Env) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.base)
                    (EnvIterator.typeLayout()).writeBytes(buf, offset + 8, value.iter)
                }
            }

        public operator fun invoke(
            base: MutPtr,
            iter: EnvIterator,
        ): Env = Env(
            baseCtorArg = base,
            iter = iter,
        )
    }
}

public open class EnvIterator internal constructor(
    valueCtorArg: MutPtr,
) : Iterable<Pair<OsString, OsString>> {
    public var value: MutPtr = valueCtorArg
        internal set

    public var field0: MutPtr
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Pair<OsString, OsString>> = object : AbstractIterator<Pair<OsString, OsString>>() {
        override fun computeNext() {
            when (val v = this@EnvIterator.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EnvIterator = EnvIterator(value)

    public open fun next(): Pair<OsString, OsString>? {
        var cur by run {
            fun <V> place(read: () -> V, write: (V) -> Unit): kotlin.properties.ReadWriteProperty<Any?, V> =
                object : kotlin.properties.ReadWriteProperty<Any?, V> {
                    override fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): V = read()
                    override fun setValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>, value: V) = write(value)
                }
            place({ this.value }, { this.value = it })
        }
        while (true) {
            run {
                if ((UnresolvedTypeLayout<c.Wchar>()).readNative((cur).rawAddress) == 0u.toUShort()) {
                    return null
                }
                val p = ConstPtr(((UnresolvedTypeLayout<c.Wchar>()).readNative((cur).rawAddress)).toLong())
                var len = 0
                while ((PrimitiveLayouts.U16).readNative((ConstPtr((p).rawAddress + ((len).toLong() * (PrimitiveLayouts.U16).strideBytes))).rawAddress) != 0u.toUShort()) {
                    len += 1
                }
                val s = Slice.fromRawParts(p, len)
                (UnresolvedTypeLayout<c.Wchar>()).writeNative((cur).rawAddress, MutPtr((cur).rawAddress + ((len + 1).toLong() * (UnresolvedTypeLayout<c.Wchar>()).strideBytes)))
                // Windows allows environment variables to start with an equals
                // symbol (in any other position, this is the separator between
                // variable name and value). Since`s` has at least length 1 at
                // this point (because the empty string terminates the array of
                // environment variables), we can safely slice.
                val pos =
                    when (val matchVal = (((s).subList(1, (s).size)).indexOfFirst({ u -> u == 61u.toUShort() }).takeIf { it >= 0 })?.let({ p1 -> p1 + 1 })) {
                        null -> { continue }
                        else -> {
                            val p1 = matchVal
                            p1
                        }
                    }
                return Pair(OsStringExt.fromWide(((s).subList(0, pos))), OsStringExt.fromWide(((s).subList(pos + 1, (s).size))))
            }
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<EnvIterator> =
            object : TypeLayout<EnvIterator> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): EnvIterator =
                    EnvIterator(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: EnvIterator) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EnvIterator =
                    EnvIterator(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EnvIterator) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
                }
            }

        public operator fun invoke(
            value: MutPtr,
        ): EnvIterator = EnvIterator(
            valueCtorArg = value,
        )
    }
}

public fun env(): Env =
    run {
        val ch = c.GetEnvironmentStringsW()
        if (ch.isNull()) {
            error("failure getting env string from OS: ${io.github.kotlinmania.rustlang.core.io.error.Error.lastOsError()}")
        }
        Env(base = ch, iter = EnvIterator(ch))
    }

public fun getenv(k: OsStr): OsString? {
    val k1 = (toU16s(k).ok() ?: return null)
    return fillUtf16Buf({ buf, sz -> c.GetEnvironmentVariableW(k1.asPtr(), buf, sz) }, { OsStringExt.fromWide(it) }).ok()
}

public fun setenv(k: OsStr, v: OsStr): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> {
    // SAFETY: We ensure that k and v are null-terminated wide strings.
    return run {
        val k1 = when (val tryResult = toU16s(k)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        val v1 = when (val tryResult = toU16s(v)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        cvt(c.SetEnvironmentVariableW(k1.asPtr(), v1.asPtr())).map({ Unit })
    }
}

public fun unsetenv(n: OsStr): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> {
    // SAFETY: We ensure that v is a null-terminated wide strings.
    return run {
        val v = when (val tryResult = toU16s(n)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        cvt(c.SetEnvironmentVariableW(v.asPtr(), ConstPtr(0L))).map({ Unit })
    }
}

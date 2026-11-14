// port-lint: source sys/process/env.rs
package io.github.kotlinmania.rustlang.std.sys.process.env

import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.path.Iter
import io.github.kotlinmania.rustlang.std.sync.mpmc.IntoIter
import io.github.kotlinmania.rustlang.std.sys.process.windows.EnvKey

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * Stores a set of changes to an environment
 */
public open class CommandEnv(
    public var clear: Boolean,
    public var sawPath: Boolean,
    public var vars: MutableMap<EnvKey, OsString?>,
) {
    public open fun clone(): CommandEnv = CommandEnv(clear, sawPath, vars)

    public open fun fmt(f: StringBuilder) {
        val debugCommandEnv = DebugStruct(f, "CommandEnv")
        debugCommandEnv.field("clear", this.clear).field("vars", this.vars)
        debugCommandEnv.finish()
    }

    // Capture the current environment with these changes applied
    public open fun capture(): MutableMap<EnvKey, OsString> {
        val result = mutableMapOf()
        if (!this.clear) {
            for ((k, v) in env.varsOs()) {
                result.put(k.into(), v)
            }
        }
        for ((k1, maybeV) in this.vars) {
            if (maybeV != null) {
                val v1 = maybeV
                result.put(k1.clone(), v1.clone())
            } else {
                result.remove(k1)
            }
        }
        return result
    }

    public open fun isUnchanged(): Boolean = (!this.clear && this.vars.isEmpty()).toUByte()

    public open fun captureIfChanged(): MutableMap<EnvKey, OsString>? =
        if (this.isUnchanged()) {
            null
        } else {
            this.capture()
        }

    // The following functions build up changes
    public open fun set(key: OsStr, value: OsStr) {
        val key1 = EnvKey.from(key)
        this.maybeSawPath(key1)
        this.vars.put(key1, value.toOwned())
    }

    public open fun remove(key: OsStr) {
        val key1 = EnvKey.from(key)
        this.maybeSawPath(key1)
        if (this.clear) {
            this.vars.remove(key1)
        } else {
            this.vars.put(key1, null)
        }
    }

    public open fun clear() {
        this.clear = true
        this.vars.clear()
    }

    public open fun doesClear(): Boolean = this.clear

    public open fun haveChangedPath(): Boolean = (this.sawPath || this.clear).toUByte()

    public open fun maybeSawPath(key: EnvKey) {
        if (!this.sawPath && key == "PATH") {
            this.sawPath = true
        }
    }

    public open fun iter(): CommandEnvs {
        val iter = this.vars.iter()
        return CommandEnvs(iter = iter)
    }
}

public open class CommandEnvs internal constructor(
    iterCtorArg: Iter<EnvKey, OsString?>,
) : Iterable<Pair<OsStr, OsStr?>> {
    public var iter: Iter<EnvKey, OsString?> = iterCtorArg
        internal set

    override fun iterator(): Iterator<Pair<OsStr, OsStr?>> = object : AbstractIterator<Pair<OsStr, OsStr?>>() {
        override fun computeNext() {
            when (val v = this@CommandEnvs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Pair<OsStr, OsStr?>? = this.iter.next()?.let({ (key, value) -> Pair(key.asRef(), value.asDeref()) })

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun len(): Int = this.iter.len()

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public companion object {
        public operator fun invoke(
            iter: Iter<EnvKey, OsString?>,
        ): CommandEnvs = CommandEnvs(
            iterCtorArg = iter,
        )
    }
}

/**
 * An iterator over the fully resolved environment variables.
 *
 * This struct is created by
 * [`Command.getResolvedEnvs`][process.Command.getResolvedEnvs]. See its
 * documentation for more.
 */
public open class CommandResolvedEnvs internal constructor(
    innerCtorArg: IntoIter<EnvKey, OsString>,
) : Iterable<Pair<OsString, OsString>> {
    public var inner: IntoIter<EnvKey, OsString> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Pair<OsString, OsString>> = object : AbstractIterator<Pair<OsString, OsString>>() {
        override fun computeNext() {
            when (val v = this@CommandResolvedEnvs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Pair<OsString, OsString>? = this.inner.next()?.let({ (key, value) -> Pair(key.into(), value) })

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public companion object {
        public operator fun invoke(
            inner: IntoIter<EnvKey, OsString>,
        ): CommandResolvedEnvs = CommandResolvedEnvs(
            innerCtorArg = inner,
        )

        internal fun new(map: MutableMap<EnvKey, OsString>): CommandResolvedEnvs = CommandResolvedEnvs(inner = map.intoIter())
    }
}

// port-lint: source bridge/panic_message.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.panicmessage

import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.procmacro.bridge.Decode
import io.github.kotlinmania.rustlang.procmacro.bridge.Encode
import io.github.kotlinmania.rustlang.procmacro.bridge.buffer.Buffer
import io.github.kotlinmania.rustlang.procmacro.bridge.decode
import io.github.kotlinmania.rustlang.procmacro.bridge.encode

/**
 * Simplified version of panic payloads, ignoring
 * types other than `String` and `String`.
 */
public sealed class PanicMessage : Encode<S>, Decode<S, PanicMessage> {
    public data class StaticStr(public var value: String) : PanicMessage()
    public data class String(public var value: String) : PanicMessage()
    public data object Unknown : PanicMessage()

    public open fun asStr(): String? =
        when (this) {
            is PanicMessage.StaticStr -> {
                val (s) = this as PanicMessage.StaticStr
                s
            }
            is PanicMessage.String -> {
                val (s1) = this as PanicMessage.String
                s1
            }
            PanicMessage.Unknown -> null
        }

    public open fun intoString(): String? =
        when (this) {
            is PanicMessage.StaticStr -> {
                val (s) = this as PanicMessage.StaticStr
                s.into()
            }
            is PanicMessage.String -> {
                val (s1) = this as PanicMessage.String
                s1
            }
            PanicMessage.Unknown -> null
        }

    public override fun encode(w: Buffer, s: S) {
        this.asStr().encode(w, s)
    }

    public companion object {
        public fun from(payload: RustAny): PanicMessage {
            when (val matchVal = payload.downcastRef<String>()) {
                null -> {}
                else -> {
                    val s = matchVal
                    return PanicMessage.StaticStr(s)
                }
            }
            when (val matchVal1 = payload.downcast<String>()) {
                is Result.Ok<*, *> -> {
                    val s1 = matchVal1.value
                    return PanicMessage.String(s1)
                }
                else -> {}
            }
            return PanicMessage.Unknown
        }

        public fun <S> decode(r: List<UByte>, s: S): PanicMessage =
            when (val matchVal = Option.decode(r, s)) {
                null -> PanicMessage.Unknown
                else -> {
                    val s1 = matchVal
                    PanicMessage.String(s1)
                }
            }
    }
}

public fun fromBoxRustAny(value: PanicMessage): RustAny =
    when (value) {
        is PanicMessage.StaticStr -> {
            val (s) = value
            s
        }
        is PanicMessage.String -> {
            val (s1) = value
            s1
        }
        PanicMessage.Unknown -> {
            open class UnknownPanicMessage
            UnknownPanicMessage()
        }
    }

package io.github.kotlinmania.rustlang.backtrace.backtrace

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind.Frame as FrameImp

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
 * A trait representing one frame of a backtrace, yielded to the `trace`
 * function of this crate.
 *
 * The tracing function's closure will be yielded frames, and the frame is
 * virtually dispatched as the underlying implementation is not always known
 * until runtime.
 */
public open class Frame(
    public var inner: FrameImp,
) {
    public open fun clone(): Frame = Frame(inner)

    /**
     * Returns the current instruction pointer of this frame.
     *
     * This is normally the next instruction to execute in the frame, but not
     * all implementations list this with 100% accuracy (but it's generally
     * pretty close).
     *
     * It is recommended to pass this value to `backtrace.resolve` to turn it
     * into a symbol name.
     */
    public open fun ip(): MutPtr = this.inner.ip()

    /**
     * Returns the current stack pointer of this frame.
     *
     * In the case that a backend cannot recover the stack pointer for this
     * frame, a null pointer is returned.
     */
    public open fun sp(): MutPtr = this.inner.sp()

    /**
     * Returns the starting symbol address of the frame of this function.
     *
     * This will attempt to rewind the instruction pointer returned by `ip` to
     * the start of the function, returning that value. In some cases, however,
     * backends will just return `ip` from this function.
     *
     * The returned value can sometimes be used if `backtrace.resolve` failed
     * on the `ip` given above.
     */
    public open fun symbolAddress(): MutPtr = this.inner.symbolAddress()

    /**
     * Returns the base address of the module to which the frame belongs.
     */
    public open fun moduleBaseAddress(): MutPtr? = this.inner.moduleBaseAddress()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Frame").field("ip", this.ip()).field("symbol_address", this.symbolAddress()).finish(); Result.Ok<Unit, Error>(Unit) }
}

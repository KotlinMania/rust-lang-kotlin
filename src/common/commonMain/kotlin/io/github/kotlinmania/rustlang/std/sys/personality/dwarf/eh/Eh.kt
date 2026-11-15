// port-lint: source sys/personality/dwarf/eh.rs
package io.github.kotlinmania.rustlang.std.sys.personality.dwarf.eh

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.withExposedProvenance
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sys.personality.dwarf.DwarfReader

// Parsing of GCC-style Language-Specific Data Area (LSDA)
// For details see:
//  * <https://refspecs.linuxfoundation.org/LSB_3.0.0/LSB-PDA/LSB-PDA/ehframechpt.html>
//  * <https://refspecs.linuxfoundation.org/LSB_5.0.0/LSB-Core-generic/LSB-Core-generic/dwarfext.html>
//  * <https://itanium-cxx-abi.github.io/cxx-abi/exceptions.pdf>
//  * <https://www.airs.com/blog/archives/460>
//  * <https://www.airs.com/blog/archives/464>
//
// A reference implementation may be found in the GCC source tree
// (`<root> / libgcc / unwind - c.c` as of this writing).

public val DW_EH_PE_OMIT: UByte = 0xFFu

public val DW_EH_PE_ABSPTR: UByte = 0x00u

public val DW_EH_PE_ULEB128: UByte = 0x01u

public val DW_EH_PE_UDATA2: UByte = 0x02u

public val DW_EH_PE_UDATA4: UByte = 0x03u

public val DW_EH_PE_UDATA8: UByte = 0x04u

public val DW_EH_PE_SLEB128: UByte = 0x09u

public val DW_EH_PE_SDATA2: UByte = 0x0Au

public val DW_EH_PE_SDATA4: UByte = 0x0Bu

public val DW_EH_PE_SDATA8: UByte = 0x0Cu

public val DW_EH_PE_PCREL: UByte = 0x10u

public val DW_EH_PE_TEXTREL: UByte = 0x20u

public val DW_EH_PE_DATAREL: UByte = 0x30u

public val DW_EH_PE_FUNCREL: UByte = 0x40u

public val DW_EH_PE_ALIGNED: UByte = 0x50u

public val DW_EH_PE_INDIRECT: UByte = 0x80u

public open class EHContext internal constructor(
    ipCtorArg: ConstPtr,
    funcStartCtorArg: ConstPtr,
    getTextStartCtorArg: () -> ConstPtr,
    getDataStartCtorArg: () -> ConstPtr,
) {
    public var ip: ConstPtr = ipCtorArg
        internal set

    // Current instruction pointer
    public var funcStart: ConstPtr = funcStartCtorArg
        internal set

    // Pointer to the current function
    public var getTextStart: () -> ConstPtr = getTextStartCtorArg
        internal set

    // Get pointer to the code section
    public var getDataStart: () -> ConstPtr = getDataStartCtorArg
        internal set

    public open fun clone(): EHContext = EHContext(ip, funcStart, getTextStart, getDataStart)

    public companion object {
        public operator fun invoke(
            ip: ConstPtr,
            funcStart: ConstPtr,
            getTextStart: () -> ConstPtr,
            getDataStart: () -> ConstPtr,
        ): EHContext = EHContext(
            ipCtorArg = ip,
            funcStartCtorArg = funcStart,
            getTextStartCtorArg = getTextStart,
            getDataStartCtorArg = getDataStart,
        )
    }
}

/**
 * Landing pad.
 */
public typealias LPad = ConstPtr

public sealed class EHAction {
    public data object None : EHAction()
    /**
     * Destructors should be executed when stack unwinds.
     */
    public class Cleanup internal constructor(valueCtorArg: LPad) : EHAction() {
        public var value: LPad = valueCtorArg
            internal set

        public operator fun component1(): LPad = this.value

        public fun copy(value: LPad = this.value): Cleanup = Cleanup(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Cleanup) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: LPad,
            ): Cleanup = Cleanup(
                valueCtorArg = value,
            )
        }
    }
    /**
     * Stack unwind should be stopped as the exception is going to be caught by `catchUnwind`.
     */
    public class Catch internal constructor(valueCtorArg: LPad) : EHAction() {
        public var value: LPad = valueCtorArg
            internal set

        public operator fun component1(): LPad = this.value

        public fun copy(value: LPad = this.value): Catch = Catch(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Catch) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: LPad,
            ): Catch = Catch(
                valueCtorArg = value,
            )
        }
    }
    /**
     * Stack unwind should be stopped for termination (`UnwindAction.Terminate`).
     *
     * Note that due to inlining the landing pad can execute destructors before terminating. So
     * this is different from `Terminate`.
     *
     * Handling of this is mostly identical to `Catch`; except that Rust frames that have no
     * destructors but only `UnwindAction.Terminate` is considered as plain-old-frame (POF) and
     * forced unwind is allowed to unwind past it; so this is treated as `None` during forced unwind.
     */
    public class Filter internal constructor(valueCtorArg: LPad) : EHAction() {
        public var value: LPad = valueCtorArg
            internal set

        public operator fun component1(): LPad = this.value

        public fun copy(value: LPad = this.value): Filter = Filter(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Filter) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: LPad,
            ): Filter = Filter(
                valueCtorArg = value,
            )
        }
    }
    /**
     * Process should be terminated as the call site does not permit unwinding.
     */
    public data object Terminate : EHAction()
}

/**
 * 32-bit ARM Darwin platforms uses SjLj exceptions.
 *
 * The exception is watchOS armv7k (specifically that subarchitecture), which
 * instead uses DWARF Call Frame Information (CFI) unwinding.
 *
 * <https://github.com/llvm/llvm-project/blob/llvmorg-18.1.4/clang/lib/Driver/ToolChains/Darwin.cpp#L3107-L3119>
 */
public val USING_SJLJ_EXCEPTIONS: Boolean = false

public fun findEhAction(lsda: ConstPtr, context: EHContext): Result<EHAction, Unit> {
    if (((lsda).rawAddress == 0L)) {
        return Result.Ok(EHAction.None)
    }
    val funcStart = context.funcStart
    val reader = DwarfReader.new(lsda)
    val lpadBase =
        run {
            val startEncoding = reader.read<UByte>()
            // base address for landing pad offsets
            if (startEncoding != DW_EH_PE_OMIT) {
                when (val tryResult = readEncodedPointer(reader, context, startEncoding)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            } else {
                funcStart
            }
        }
    val callSiteEncoding =
        run {
            val ttypeEncoding = reader.read<UByte>()
            if (ttypeEncoding != DW_EH_PE_OMIT) {
                // Rust doesn't analyze exception types, so we don't care about the type table
                reader.readUleb128()
            }
            reader.read<UByte>()
        }
    val actionTable =
        run {
            val callSiteTableLength = reader.readUleb128()
            ConstPtr((reader.ptr).rawAddress + ((callSiteTableLength.toInt()).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    val ip = context.ip
    return if (USING_SJLJ_EXCEPTIONS.inv()) {
        // read the callsite table
        while (((reader.ptr).rawAddress.toULong() < (actionTable).rawAddress.toULong())) {
            run {
                // these are offsets rather than pointers;
                val csStart = when (val tryResult = readEncodedOffset(reader, callSiteEncoding)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                val csLen = when (val tryResult = readEncodedOffset(reader, callSiteEncoding)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                val csLpad = when (val tryResult = readEncodedOffset(reader, callSiteEncoding)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                val csActionEntry = reader.readUleb128()
                // Callsite table is sorted by csStart, so if we've passed the ip, we
                // may stop searching.
                if (((ip).rawAddress.toULong() < (ConstPtr((funcStart).rawAddress + ((csStart).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress.toULong())) {
                    break
                }
                if (((ip).rawAddress.toULong() < (ConstPtr((funcStart).rawAddress + ((csStart + csLen).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress.toULong())) {
                    if (csLpad == 0) {
                        return Result.Ok(EHAction.None)
                    } else {
                        val lpad = ConstPtr((lpadBase).rawAddress + ((csLpad).toLong() * (PrimitiveLayouts.U8).strideBytes))
                        return Result.Ok(interpretCsAction(actionTable, csActionEntry, lpad))
                    }
                }
            }
        }
        // Ip is not present in the table. This indicates a nounwind call.
        Result.Ok(EHAction.Terminate)
    } else {
        // SjLj version:
        // The "IP" is an index into the call-site table, with two exceptions:
        // -1 means no-action', and 0 means 'terminate'.
        when ((ip).rawAddress.toInt()) {
            -1 -> { return Result.Ok(EHAction.None) }
            0 -> { return Result.Ok(EHAction.Terminate) }
            else -> Unit
        }
        var idx = (ip).rawAddress.toInt()
        while (true) {
            val csLpad1 = reader.readUleb128()
            val csActionEntry1 = reader.readUleb128()
            idx -= 1
            if (idx == 0) {
                // Can never have null landing pad for sjlj -- that would have
                // been indicated by a -1 call site index.
                // Note(strict provenance)
                val lpad1 = withExposedProvenance((csLpad1 + 1uL).toInt())
                return Result.Ok(interpretCsAction(actionTable, csActionEntry1, lpad1))
            }
        }
    }
}

public fun interpretCsAction(actionTable: ConstPtr, csActionEntry: ULong, lpad: LPad): EHAction =
    if (csActionEntry == 0uL) {
        // If csActionEntry is 0 then this is a cleanup (Drop.drop). We run these
        // for both Rust panics and foreign exceptions.
        EHAction.Cleanup(lpad)
    } else {
        // If lpad != 0 and csActionEntry != 0, we have to check ttypeIndex.
        // If ttypeIndex == 0 under the condition, we take cleanup action.
        val actionRecord = ConstPtr((actionTable).rawAddress + ((csActionEntry.toInt() - 1).toLong() * (PrimitiveLayouts.U8).strideBytes))
        val actionReader = DwarfReader.new(actionRecord)
        val ttypeIndex = actionReader.readSleb128()
        val nextAction = actionReader.readSleb128()
        if (nextAction != 0L) {
            // We observed multiple actions. Action records contain no duplicates (at least that is
            // true for both LLVM/GCC), and as Rust does not have exception specification, this
            // indicates that we have at least 2 of "cleanup", "catch" and "filter", so we should
            // catch all exceptions.
            //
            // Note that even for the case of "cleanup" + "filter", decoding them as "catch" is
            // fine: "filter" behaves identically to "catch" except for forced unwind; in case of
            // forced unwind, hitting a "cleanup" landing pad is UB as it indicates that we're
            // unwinding past a non-POF Rust frame.
            EHAction.Catch(lpad)
        } else if (ttypeIndex == 0L) {
            EHAction.Cleanup(lpad)
        } else if (ttypeIndex > 0L) {
            // Stop unwinding Rust panics at catchUnwind.
            EHAction.Catch(lpad)
        } else {
            EHAction.Filter(lpad)
        }
    }

public fun roundUp(unrounded: Int, align: Int): Result<Int, Unit> =
    if (align.isPowerOfTwo()) {
        Result.Ok((unrounded + align - 1) and (align - 1).inv())
    } else {
        Result.Err(Unit)
    }

/**
 * Reads an offset (`Int`) from `reader` whose encoding is described by `encoding`.
 *
 * `encoding` must be a [DWARF Exception Header Encoding as described by the LSB spec][LSB-dwarf-ext].
 * In addition the upper ("application") part must be zero.
 *
 * # Errors
 * Returns `Err` if `encoding`
 * * is not a valid DWARF Exception Header Encoding,
 * * is `DwEhPeOmit`, or
 * * has a non-zero application part.
 *
 * [LSB-dwarf-ext]: https://refspecs.linuxfoundation.org/LSB_5.0.0/LSB-Core-generic/LSB-Core-generic/dwarfext.html
 */
public fun readEncodedOffset(reader: DwarfReader, encoding: UByte): Result<Int, Unit> {
    if (encoding == DW_EH_PE_OMIT || encoding and 0xF0u.toUByte() != 0u.toUByte()) {
        return Result.Err(Unit)
    }
    val result =
        when (encoding and 0x0Fu.toUByte()) {
            // despite the name, LLVM also uses absptr for offsets instead of pointers
            DW_EH_PE_ABSPTR -> reader.read<Int>()
            DW_EH_PE_ULEB128 -> reader.readUleb128().toInt()
            DW_EH_PE_UDATA2 -> reader.read<UShort>().toInt()
            DW_EH_PE_UDATA4 -> reader.read<UInt>().toInt()
            DW_EH_PE_UDATA8 -> reader.read<ULong>().toInt()
            DW_EH_PE_SLEB128 -> reader.readSleb128().toInt()
            DW_EH_PE_SDATA2 -> reader.read<Short>().toInt()
            DW_EH_PE_SDATA4 -> reader.read<Int>().toInt()
            DW_EH_PE_SDATA8 -> reader.read<Long>().toInt()
            else -> { return Result.Err(Unit) }
        }
    return Result.Ok(result)
}

/**
 * Reads a pointer from `reader` whose encoding is described by `encoding`.
 *
 * `encoding` must be a [DWARF Exception Header Encoding as described by the LSB spec][LSB-dwarf-ext].
 *
 * # Errors
 * Returns `Err` if `encoding`
 * * is not a valid DWARF Exception Header Encoding,
 * * is `DwEhPeOmit`, or
 * * combines `DwEhPeAbsptr` or `DwEhPeAligned` application part with an integer encoding
 *   (not `DwEhPeAbsptr`) in the value format part.
 *
 * [LSB-dwarf-ext]: https://refspecs.linuxfoundation.org/LSB_5.0.0/LSB-Core-generic/LSB-Core-generic/dwarfext.html
 */
public fun readEncodedPointer(reader: DwarfReader, context: EHContext, encoding: UByte): Result<ConstPtr, Unit> {
    if (encoding == DW_EH_PE_OMIT) {
        return Result.Err(Unit)
    }
    val basePtr =
        when (encoding and 0x70u.toUByte()) {
            DW_EH_PE_ABSPTR -> ConstPtr(0L)
            // relative to address of the encoded value, despite the name
            DW_EH_PE_PCREL -> reader.ptr
            DW_EH_PE_FUNCREL -> {
                if (((context.funcStart).rawAddress == 0L)) {
                    return Result.Err(Unit)
                }
                context.funcStart
            }
            DW_EH_PE_TEXTREL -> context.getTextStart()
            DW_EH_PE_DATAREL -> context.getDataStart()
            // aligned means the value is aligned to the size of a pointer
            DW_EH_PE_ALIGNED -> {
                reader.ptr = ConstPtr((when (val tryResult = roundUp((reader.ptr).rawAddress.toInt(), sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L)))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }).toLong())
                ConstPtr(0L)
            }
            else -> { return Result.Err(Unit) }
        }
    var ptr =
        if (basePtr.isNull()) {
            // any value encoding other than absptr would be nonsensical here;
            // there would be no source of pointer provenance
            if (encoding and 0x0Fu.toUByte() != DW_EH_PE_ABSPTR) {
                return Result.Err(Unit)
            }
            reader.read<ConstPtr>()
        } else {
            val offset = when (val tryResult = readEncodedOffset(reader, encoding and 0x0Fu.toUByte())) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            basePtr.wrappingAdd(offset)
        }
    if (encoding and DW_EH_PE_INDIRECT != 0u.toUByte()) {
        ptr = (ptr.cast())
    }
    return Result.Ok(ptr)
}

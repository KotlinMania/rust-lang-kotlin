// port-lint: source windows_sys.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

public expect fun enumerateLoadedModulesW64(hprocess: Handle, enumloadedmodulescallback: PenumloadedModulesCallbackw64, usercontext: ConstPtr): Bool

public expect fun stackWalk64(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64): Bool

public expect fun stackWalkEx(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64, flags: UInt): Bool

public expect fun symAddrIncludeInlineTrace(hprocess: Handle, address: ULong): UInt

public expect fun symFromAddrW(hprocess: Handle, address: ULong, displacement: MutPtr, symbol: MutPtr): Bool

public expect fun symFromInlineContextW(hprocess: Handle, address: ULong, inlinecontext: UInt, displacement: MutPtr, symbol: MutPtr): Bool

public expect fun symFunctionTableAccess64(hprocess: Handle, addrbase: ULong): MutPtr

public expect fun symGetLineFromAddrW64(hprocess: Handle, dwaddr: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool

public expect fun symGetLineFromInlineContextW(hprocess: Handle, dwaddr: ULong, inlinecontext: UInt, qwmodulebaseaddress: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool

public expect fun symGetModuleBase64(hprocess: Handle, qwaddr: ULong): ULong

public expect fun symGetOptions(): UInt

public expect fun symGetSearchPathW(hprocess: Handle, searchpatha: Pwstr, searchpathlength: UInt): Bool

public expect fun symInitializeW(hprocess: Handle, usersearchpath: Pcwstr, finvadeprocess: Bool): Bool

public expect fun symQueryInlineTrace(hprocess: Handle, startaddress: ULong, startcontext: UInt, startretaddress: ULong, curaddress: ULong, curcontext: MutPtr, curframeindex: MutPtr): Bool

public expect fun symSetOptions(symoptions: UInt): UInt

public expect fun symSetSearchPathW(hprocess: Handle, searchpatha: Pcwstr): Bool

public expect fun closeHandle(hobject: Handle): Bool

public expect fun createFileMappingA(hfile: Handle, lpfilemappingattributes: ConstPtr, flprotect: PageProtectionFlags, dwmaximumsizehigh: UInt, dwmaximumsizelow: UInt, lpname: Pcstr): Handle

public expect fun createMutexA(lpmutexattributes: ConstPtr, binitialowner: Bool, lpname: Pcstr): Handle

public expect fun createToolhelp32Snapshot(dwflags: CreateToolhelpSnapshotFlags, th32processid: UInt): Handle

public expect fun getCurrentProcess(): Handle

public expect fun getCurrentProcessId(): UInt

public expect fun getCurrentThread(): Handle

public expect fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc

public expect fun loadLibraryA(lplibfilename: Pcstr): Hmodule

public expect fun mapViewOfFile(hfilemappingobject: Handle, dwdesiredaccess: FileMap, dwfileoffsethigh: UInt, dwfileoffsetlow: UInt, dwnumberofbytestomap: ULong): MemoryMappedViewAddress

public expect fun module32FirstW(hsnapshot: Handle, lpme: MutPtr): Bool

public expect fun module32NextW(hsnapshot: Handle, lpme: MutPtr): Bool

public expect fun releaseMutex(hmutex: Handle): Bool

public expect fun rtlCaptureContext(contextrecord: MutPtr)

public expect fun rtlLookupFunctionEntry(controlpc: ULong, imagebase: MutPtr, historytable: MutPtr): MutPtr

public expect fun rtlVirtualUnwind(handlertype: RtlVirtualUnwindHandlerType, imagebase: ULong, controlpc: ULong, functionentry: ConstPtr, contextrecord: MutPtr, handlerdata: MutPtr, establisherframe: MutPtr, contextpointers: MutPtr): ExceptionRoutine

public expect fun unmapViewOfFile(lpbaseaddress: MemoryMappedViewAddress): Bool

public expect fun waitForSingleObjectEx(hhandle: Handle, dwmilliseconds: UInt, balertable: Bool): WaitEvent

public expect fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: Pcwstr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: Pcstr, lpuseddefaultchar: MutPtr): Int

public expect fun lstrlenW(lpstring: Pcwstr): Int

public open class Address64(
    public var offset: ULong,
    public var segment: UShort,
    public var mode: AddressMode,
) {
    public open fun clone(): Address64 = Address64(offset, segment, mode)

    public companion object {
        public fun typeLayout(): TypeLayout<Address64> =
            object : TypeLayout<Address64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Address64 =
                    Address64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Address64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.offset)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.segment)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Address64 =
                    Address64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Address64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.offset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.segment)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.mode)
                }
            }
    }
}

public typealias AddressMode = Int

public open class Arm64NtNeon128(
    public var anonymous: Arm64NtNeon1280? = null,
    public var d: DoubleArray? = null,
    public var s: FloatArray? = null,
    public var h: UShortArray? = null,
    public var b: UByteArray? = null,
)

public open class Arm64NtNeon1280(
    public var low: ULong,
    public var high: Long,
) {
    public open fun clone(): Arm64NtNeon1280 = Arm64NtNeon1280(low, high)

    public companion object {
        public fun typeLayout(): TypeLayout<Arm64NtNeon1280> =
            object : TypeLayout<Arm64NtNeon1280> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Arm64NtNeon1280 =
                    Arm64NtNeon1280(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Arm64NtNeon1280) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.low)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.high)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Arm64NtNeon1280 =
                    Arm64NtNeon1280(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Arm64NtNeon1280) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.low)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.high)
                }
            }
    }
}

public val ADDR_MODE_FLAT: AddressMode = 3

public typealias Bool = Int

public open class Context(
    public var contextFlags: ContextFlags,
    public var cpsr: UInt,
    public var anonymous: Context0,
    public var sp: ULong,
    public var pc: ULong,
    public var v: List<Arm64NtNeon128>,
    public var fpcr: UInt,
    public var fpsr: UInt,
    public var bcr: UIntArray,
    public var bvr: ULongArray,
    public var wcr: UIntArray,
    public var wvr: ULongArray,
) {
    public open fun clone(): Context = Context(contextFlags, cpsr, anonymous, sp, pc, v, fpcr, fpsr, bcr, bvr, wcr, wvr)
}

public open class Context0(
    public var anonymous: Context00? = null,
    public var x: ULongArray? = null,
)

public open class Context00(
    public var x0: ULong,
    public var x1: ULong,
    public var x2: ULong,
    public var x3: ULong,
    public var x4: ULong,
    public var x5: ULong,
    public var x6: ULong,
    public var x7: ULong,
    public var x8: ULong,
    public var x9: ULong,
    public var x10: ULong,
    public var x11: ULong,
    public var x12: ULong,
    public var x13: ULong,
    public var x14: ULong,
    public var x15: ULong,
    public var x16: ULong,
    public var x17: ULong,
    public var x18: ULong,
    public var x19: ULong,
    public var x20: ULong,
    public var x21: ULong,
    public var x22: ULong,
    public var x23: ULong,
    public var x24: ULong,
    public var x25: ULong,
    public var x26: ULong,
    public var x27: ULong,
    public var x28: ULong,
    public var fp: ULong,
    public var lr: ULong,
) {
    public open fun clone(): Context00 = Context00(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20, x21, x22, x23, x24, x25, x26, x27, x28, fp, lr)

    public companion object {
        public fun typeLayout(): TypeLayout<Context00> =
            object : TypeLayout<Context00> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 248L
                override fun readNative(addr: Long): Context00 =
                    Context00(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U64).readNative(addr + 168L),
                        (PrimitiveLayouts.U64).readNative(addr + 176L),
                        (PrimitiveLayouts.U64).readNative(addr + 184L),
                        (PrimitiveLayouts.U64).readNative(addr + 192L),
                        (PrimitiveLayouts.U64).readNative(addr + 200L),
                        (PrimitiveLayouts.U64).readNative(addr + 208L),
                        (PrimitiveLayouts.U64).readNative(addr + 216L),
                        (PrimitiveLayouts.U64).readNative(addr + 224L),
                        (PrimitiveLayouts.U64).readNative(addr + 232L),
                        (PrimitiveLayouts.U64).readNative(addr + 240L),
                    )
                override fun writeNative(addr: Long, value: Context00) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.x0)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.x1)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.x2)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.x3)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.x4)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.x5)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.x6)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.x7)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.x8)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.x9)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.x10)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.x11)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.x12)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.x13)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.x14)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.x15)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.x16)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.x17)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.x18)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.x19)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.x20)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.x21)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.x22)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.x23)
                    (PrimitiveLayouts.U64).writeNative(addr + 192L, value.x24)
                    (PrimitiveLayouts.U64).writeNative(addr + 200L, value.x25)
                    (PrimitiveLayouts.U64).writeNative(addr + 208L, value.x26)
                    (PrimitiveLayouts.U64).writeNative(addr + 216L, value.x27)
                    (PrimitiveLayouts.U64).writeNative(addr + 224L, value.x28)
                    (PrimitiveLayouts.U64).writeNative(addr + 232L, value.fp)
                    (PrimitiveLayouts.U64).writeNative(addr + 240L, value.lr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Context00 =
                    Context00(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 176),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 184),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 192),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 200),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 208),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 216),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 224),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 232),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 240),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Context00) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.x0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.x1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.x2)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.x3)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.x4)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.x5)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.x6)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.x7)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.x8)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.x9)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.x10)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.x11)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.x12)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.x13)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.x14)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.x15)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.x16)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.x17)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.x18)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.x19)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.x20)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.x21)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.x22)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.x23)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.x24)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.x25)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.x26)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 216, value.x27)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 224, value.x28)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 232, value.fp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 240, value.lr)
                }
            }
    }
}

public typealias ContextFlags = UInt

public val CP_UTF8: UInt = 65001u

public typealias CreateToolhelpSnapshotFlags = UInt

public typealias ExceptionDisposition = Int

public open class ExceptionRecord internal constructor(
    public var exceptionCode: Ntstatus,
    public var exceptionFlags: UInt,
    exceptionRecordCtorArg: MutPtr,
    exceptionAddressCtorArg: MutPtr,
    public var numberParameters: UInt,
    public var exceptionInformation: IntArray,
) {
    public var exceptionRecord: MutPtr = exceptionRecordCtorArg
        internal set

    public var exceptionAddress: MutPtr = exceptionAddressCtorArg
        internal set

    public open fun clone(): ExceptionRecord = ExceptionRecord(exceptionCode, exceptionFlags, exceptionRecord, exceptionAddress, numberParameters, exceptionInformation)

    public companion object {
        public fun typeLayout(): TypeLayout<ExceptionRecord> =
            object : TypeLayout<ExceptionRecord> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 152L
                override fun readNative(addr: Long): ExceptionRecord =
                    ExceptionRecord(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveArrayLayouts.usize64Array(15)).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: ExceptionRecord) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.exceptionCode)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.exceptionFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.exceptionRecord)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.exceptionAddress)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.numberParameters)
                    (PrimitiveArrayLayouts.usize64Array(15)).writeNative(addr + 32L, value.exceptionInformation)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExceptionRecord =
                    ExceptionRecord(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveArrayLayouts.usize64Array(15)).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExceptionRecord) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.exceptionCode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.exceptionFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.exceptionRecord)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.exceptionAddress)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.numberParameters)
                    (PrimitiveArrayLayouts.usize64Array(15)).writeBytes(buf, offset + 32, value.exceptionInformation)
                }
            }

        public operator fun invoke(
            exceptionCode: Ntstatus,
            exceptionFlags: UInt,
            exceptionRecord: MutPtr,
            exceptionAddress: MutPtr,
            numberParameters: UInt,
            exceptionInformation: IntArray,
        ): ExceptionRecord = ExceptionRecord(
            exceptionCode = exceptionCode,
            exceptionFlags = exceptionFlags,
            exceptionRecordCtorArg = exceptionRecord,
            exceptionAddressCtorArg = exceptionAddress,
            numberParameters = numberParameters,
            exceptionInformation = exceptionInformation,
        )
    }
}

public fun interface ExceptionRoutineFn {
    public operator fun invoke(exceptionrecord: MutPtr, establisherframe: ConstPtr, contextrecord: MutPtr, dispatchercontext: ConstPtr): ExceptionDisposition
}

public typealias ExceptionRoutine = ExceptionRoutineFn?

public val FALSE: Bool = 0

public fun interface FarprocFn {
    public operator fun invoke(): Long
}

public typealias Farproc = FarprocFn?

public typealias FileMap = UInt

public val FILE_MAP_READ: FileMap = 4u

public open class FloatingSaveArea(
    public var controlWord: UInt,
    public var statusWord: UInt,
    public var tagWord: UInt,
    public var errorOffset: UInt,
    public var errorSelector: UInt,
    public var dataOffset: UInt,
    public var dataSelector: UInt,
    public var registerArea: UByteArray,
    public var cr0NpxState: UInt,
) {
    public open fun clone(): FloatingSaveArea = FloatingSaveArea(controlWord, statusWord, tagWord, errorOffset, errorSelector, dataOffset, dataSelector, registerArea, cr0NpxState)

    public companion object {
        public fun typeLayout(): TypeLayout<FloatingSaveArea> =
            object : TypeLayout<FloatingSaveArea> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): FloatingSaveArea =
                    FloatingSaveArea(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveArrayLayouts.uByteArray(80)).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 108L),
                    )
                override fun writeNative(addr: Long, value: FloatingSaveArea) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.controlWord)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.statusWord)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tagWord)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.errorOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.errorSelector)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.dataOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.dataSelector)
                    (PrimitiveArrayLayouts.uByteArray(80)).writeNative(addr + 28L, value.registerArea)
                    (PrimitiveLayouts.U32).writeNative(addr + 108L, value.cr0NpxState)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FloatingSaveArea =
                    FloatingSaveArea(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveArrayLayouts.uByteArray(80)).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 108),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FloatingSaveArea) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.controlWord)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.statusWord)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tagWord)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.errorOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.errorSelector)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.dataOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.dataSelector)
                    (PrimitiveArrayLayouts.uByteArray(80)).writeBytes(buf, offset + 28, value.registerArea)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 108, value.cr0NpxState)
                }
            }
    }
}

public typealias Handle = MutPtr

public typealias Hinstance = MutPtr

public typealias Hmodule = MutPtr

public open class ImagehlpLinew64 internal constructor(
    public var sizeOfStruct: UInt,
    keyCtorArg: MutPtr,
    public var lineNumber: UInt,
    fileNameCtorArg: Pwstr,
    public var address: ULong,
) {
    public var key: MutPtr = keyCtorArg
        internal set

    public var fileName: Pwstr = fileNameCtorArg
        internal set

    public open fun clone(): ImagehlpLinew64 = ImagehlpLinew64(sizeOfStruct, key, lineNumber, fileName, address)

    public companion object {
        public fun typeLayout(): TypeLayout<ImagehlpLinew64> =
            object : TypeLayout<ImagehlpLinew64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): ImagehlpLinew64 =
                    ImagehlpLinew64(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: ImagehlpLinew64) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.sizeOfStruct)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.key)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.lineNumber)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.fileName)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.address)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ImagehlpLinew64 =
                    ImagehlpLinew64(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ImagehlpLinew64) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.sizeOfStruct)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.key)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.lineNumber)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.fileName)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.address)
                }
            }

        public operator fun invoke(
            sizeOfStruct: UInt,
            key: MutPtr,
            lineNumber: UInt,
            fileName: Pwstr,
            address: ULong,
        ): ImagehlpLinew64 = ImagehlpLinew64(
            sizeOfStruct = sizeOfStruct,
            keyCtorArg = key,
            lineNumber = lineNumber,
            fileNameCtorArg = fileName,
            address = address,
        )
    }
}

public open class ImageArm64RuntimeFunctionEntry(
    public var beginAddress: UInt,
    public var anonymous: ImageArm64RuntimeFunctionEntry0,
) {
    public open fun clone(): ImageArm64RuntimeFunctionEntry = ImageArm64RuntimeFunctionEntry(beginAddress, anonymous)
}

public open class ImageArm64RuntimeFunctionEntry0(
    public var unwindData: UInt? = null,
    public var anonymous: ImageArm64RuntimeFunctionEntry00? = null,
)

public open class ImageArm64RuntimeFunctionEntry00(
    public var bitfield: UInt,
) {
    public open fun clone(): ImageArm64RuntimeFunctionEntry00 = ImageArm64RuntimeFunctionEntry00(bitfield)

    public companion object {
        public fun typeLayout(): TypeLayout<ImageArm64RuntimeFunctionEntry00> =
            object : TypeLayout<ImageArm64RuntimeFunctionEntry00> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ImageArm64RuntimeFunctionEntry00 =
                    ImageArm64RuntimeFunctionEntry00(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ImageArm64RuntimeFunctionEntry00) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.bitfield)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ImageArm64RuntimeFunctionEntry00 =
                    ImageArm64RuntimeFunctionEntry00(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ImageArm64RuntimeFunctionEntry00) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.bitfield)
                }
            }
    }
}

public typealias ImageFileMachine = UShort

public val IMAGE_FILE_MACHINE_I386: ImageFileMachine = 332u.toUShort()

public open class ImageRuntimeFunctionEntry(
    public var beginAddress: UInt,
    public var endAddress: UInt,
    public var anonymous: ImageRuntimeFunctionEntry0,
) {
    public open fun clone(): ImageRuntimeFunctionEntry = ImageRuntimeFunctionEntry(beginAddress, endAddress, anonymous)
}

public open class ImageRuntimeFunctionEntry0(
    public var unwindInfoAddress: UInt? = null,
    public var unwindData: UInt? = null,
)

public val INFINITE: UInt = 4294967295u

public val INVALID_HANDLE_VALUE: Handle = MutPtr((-1).toLong())

public open class Kdhelp64(
    public var thread: ULong,
    public var thCallbackStack: UInt,
    public var thCallbackBStore: UInt,
    public var nextCallback: UInt,
    public var framePointer: UInt,
    public var kiCallUserMode: ULong,
    public var keUserCallbackDispatcher: ULong,
    public var systemRangeStart: ULong,
    public var kiUserExceptionDispatcher: ULong,
    public var stackBase: ULong,
    public var stackLimit: ULong,
    public var buildVersion: UInt,
    public var retpolineStubFunctionTableSize: UInt,
    public var retpolineStubFunctionTable: ULong,
    public var retpolineStubOffset: UInt,
    public var retpolineStubSize: UInt,
    public var reserved0: ULongArray,
) {
    public open fun clone(): Kdhelp64 = Kdhelp64(thread, thCallbackStack, thCallbackBStore, nextCallback, framePointer, kiCallUserMode, keUserCallbackDispatcher, systemRangeStart, kiUserExceptionDispatcher, stackBase, stackLimit, buildVersion, retpolineStubFunctionTableSize, retpolineStubFunctionTable, retpolineStubOffset, retpolineStubSize, reserved0)

    public companion object {
        public fun typeLayout(): TypeLayout<Kdhelp64> =
            object : TypeLayout<Kdhelp64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Kdhelp64 =
                    Kdhelp64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U32).readNative(addr + 72L),
                        (PrimitiveLayouts.U32).readNative(addr + 76L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveLayouts.U32).readNative(addr + 88L),
                        (PrimitiveLayouts.U32).readNative(addr + 92L),
                        (PrimitiveArrayLayouts.uLongArray(2)).readNative(addr + 96L),
                    )
                override fun writeNative(addr: Long, value: Kdhelp64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.thread)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.thCallbackStack)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.thCallbackBStore)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.nextCallback)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.framePointer)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.kiCallUserMode)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.keUserCallbackDispatcher)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.systemRangeStart)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.kiUserExceptionDispatcher)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.stackBase)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.stackLimit)
                    (PrimitiveLayouts.U32).writeNative(addr + 72L, value.buildVersion)
                    (PrimitiveLayouts.U32).writeNative(addr + 76L, value.retpolineStubFunctionTableSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.retpolineStubFunctionTable)
                    (PrimitiveLayouts.U32).writeNative(addr + 88L, value.retpolineStubOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 92L, value.retpolineStubSize)
                    (PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr + 96L, value.reserved0)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Kdhelp64 =
                    Kdhelp64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 76),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 92),
                        (PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset + 96),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Kdhelp64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.thread)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.thCallbackStack)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.thCallbackBStore)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.nextCallback)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.framePointer)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.kiCallUserMode)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.keUserCallbackDispatcher)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.systemRangeStart)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.kiUserExceptionDispatcher)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.stackBase)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.stackLimit)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 72, value.buildVersion)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 76, value.retpolineStubFunctionTableSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.retpolineStubFunctionTable)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 88, value.retpolineStubOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 92, value.retpolineStubSize)
                    (PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset + 96, value.reserved0)
                }
            }
    }
}

public open class KnonvolatileContextPointers internal constructor(
    x19CtorArg: MutPtr,
    x20CtorArg: MutPtr,
    x21CtorArg: MutPtr,
    x22CtorArg: MutPtr,
    x23CtorArg: MutPtr,
    x24CtorArg: MutPtr,
    x25CtorArg: MutPtr,
    x26CtorArg: MutPtr,
    x27CtorArg: MutPtr,
    x28CtorArg: MutPtr,
    fpCtorArg: MutPtr,
    lrCtorArg: MutPtr,
    d8CtorArg: MutPtr,
    d9CtorArg: MutPtr,
    d10CtorArg: MutPtr,
    d11CtorArg: MutPtr,
    d12CtorArg: MutPtr,
    d13CtorArg: MutPtr,
    d14CtorArg: MutPtr,
    d15CtorArg: MutPtr,
) {
    public var x19: MutPtr = x19CtorArg
        internal set

    public var x20: MutPtr = x20CtorArg
        internal set

    public var x21: MutPtr = x21CtorArg
        internal set

    public var x22: MutPtr = x22CtorArg
        internal set

    public var x23: MutPtr = x23CtorArg
        internal set

    public var x24: MutPtr = x24CtorArg
        internal set

    public var x25: MutPtr = x25CtorArg
        internal set

    public var x26: MutPtr = x26CtorArg
        internal set

    public var x27: MutPtr = x27CtorArg
        internal set

    public var x28: MutPtr = x28CtorArg
        internal set

    public var fp: MutPtr = fpCtorArg
        internal set

    public var lr: MutPtr = lrCtorArg
        internal set

    public var d8: MutPtr = d8CtorArg
        internal set

    public var d9: MutPtr = d9CtorArg
        internal set

    public var d10: MutPtr = d10CtorArg
        internal set

    public var d11: MutPtr = d11CtorArg
        internal set

    public var d12: MutPtr = d12CtorArg
        internal set

    public var d13: MutPtr = d13CtorArg
        internal set

    public var d14: MutPtr = d14CtorArg
        internal set

    public var d15: MutPtr = d15CtorArg
        internal set

    public open fun clone(): KnonvolatileContextPointers = KnonvolatileContextPointers(x19, x20, x21, x22, x23, x24, x25, x26, x27, x28, fp, lr, d8, d9, d10, d11, d12, d13, d14, d15)

    public companion object {
        public fun typeLayout(): TypeLayout<KnonvolatileContextPointers> =
            object : TypeLayout<KnonvolatileContextPointers> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 160L
                override fun readNative(addr: Long): KnonvolatileContextPointers =
                    KnonvolatileContextPointers(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 88L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 96L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 104L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 112L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 120L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 128L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 136L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 144L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 152L),
                    )
                override fun writeNative(addr: Long, value: KnonvolatileContextPointers) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.x19)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.x20)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.x21)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.x22)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.x23)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.x24)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.x25)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.x26)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.x27)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.x28)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.fp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 88L, value.lr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 96L, value.d8)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 104L, value.d9)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 112L, value.d10)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 120L, value.d11)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 128L, value.d12)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 136L, value.d13)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 144L, value.d14)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 152L, value.d15)
                }
                override fun readBytes(buf: ByteArray, offset: Int): KnonvolatileContextPointers =
                    KnonvolatileContextPointers(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 152),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: KnonvolatileContextPointers) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.x19)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.x20)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.x21)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.x22)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.x23)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.x24)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.x25)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.x26)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.x27)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.x28)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.fp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 88, value.lr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 96, value.d8)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 104, value.d9)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 112, value.d10)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 120, value.d11)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 128, value.d12)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 136, value.d13)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 144, value.d14)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 152, value.d15)
                }
            }

        public operator fun invoke(
            x19: MutPtr,
            x20: MutPtr,
            x21: MutPtr,
            x22: MutPtr,
            x23: MutPtr,
            x24: MutPtr,
            x25: MutPtr,
            x26: MutPtr,
            x27: MutPtr,
            x28: MutPtr,
            fp: MutPtr,
            lr: MutPtr,
            d8: MutPtr,
            d9: MutPtr,
            d10: MutPtr,
            d11: MutPtr,
            d12: MutPtr,
            d13: MutPtr,
            d14: MutPtr,
            d15: MutPtr,
        ): KnonvolatileContextPointers = KnonvolatileContextPointers(
            x19CtorArg = x19,
            x20CtorArg = x20,
            x21CtorArg = x21,
            x22CtorArg = x22,
            x23CtorArg = x23,
            x24CtorArg = x24,
            x25CtorArg = x25,
            x26CtorArg = x26,
            x27CtorArg = x27,
            x28CtorArg = x28,
            fpCtorArg = fp,
            lrCtorArg = lr,
            d8CtorArg = d8,
            d9CtorArg = d9,
            d10CtorArg = d10,
            d11CtorArg = d11,
            d12CtorArg = d12,
            d13CtorArg = d13,
            d14CtorArg = d14,
            d15CtorArg = d15,
        )
    }
}

public class KnonvolatileContextPointers0 internal constructor(
    floatingContextCtorArg: List<MutPtr>?,
    public var anonymous: KnonvolatileContextPointers00?,
) {
    public var floatingContext: List<MutPtr>? = floatingContextCtorArg
        internal set

    public constructor() : this(
        floatingContextCtorArg = null,
        anonymous = null,
    )

    public companion object {
        public operator fun invoke(
            floatingContext: List<MutPtr>? = null,
            anonymous: KnonvolatileContextPointers00? = null,
        ): KnonvolatileContextPointers0 = KnonvolatileContextPointers0(
            floatingContextCtorArg = floatingContext,
            anonymous = anonymous,
        )
    }
}

public open class KnonvolatileContextPointers00 internal constructor(
    xmm0CtorArg: MutPtr,
    xmm1CtorArg: MutPtr,
    xmm2CtorArg: MutPtr,
    xmm3CtorArg: MutPtr,
    xmm4CtorArg: MutPtr,
    xmm5CtorArg: MutPtr,
    xmm6CtorArg: MutPtr,
    xmm7CtorArg: MutPtr,
    xmm8CtorArg: MutPtr,
    xmm9CtorArg: MutPtr,
    xmm10CtorArg: MutPtr,
    xmm11CtorArg: MutPtr,
    xmm12CtorArg: MutPtr,
    xmm13CtorArg: MutPtr,
    xmm14CtorArg: MutPtr,
    xmm15CtorArg: MutPtr,
) {
    public var xmm0: MutPtr = xmm0CtorArg
        internal set

    public var xmm1: MutPtr = xmm1CtorArg
        internal set

    public var xmm2: MutPtr = xmm2CtorArg
        internal set

    public var xmm3: MutPtr = xmm3CtorArg
        internal set

    public var xmm4: MutPtr = xmm4CtorArg
        internal set

    public var xmm5: MutPtr = xmm5CtorArg
        internal set

    public var xmm6: MutPtr = xmm6CtorArg
        internal set

    public var xmm7: MutPtr = xmm7CtorArg
        internal set

    public var xmm8: MutPtr = xmm8CtorArg
        internal set

    public var xmm9: MutPtr = xmm9CtorArg
        internal set

    public var xmm10: MutPtr = xmm10CtorArg
        internal set

    public var xmm11: MutPtr = xmm11CtorArg
        internal set

    public var xmm12: MutPtr = xmm12CtorArg
        internal set

    public var xmm13: MutPtr = xmm13CtorArg
        internal set

    public var xmm14: MutPtr = xmm14CtorArg
        internal set

    public var xmm15: MutPtr = xmm15CtorArg
        internal set

    public open fun clone(): KnonvolatileContextPointers00 = KnonvolatileContextPointers00(xmm0, xmm1, xmm2, xmm3, xmm4, xmm5, xmm6, xmm7, xmm8, xmm9, xmm10, xmm11, xmm12, xmm13, xmm14, xmm15)

    public companion object {
        public fun typeLayout(): TypeLayout<KnonvolatileContextPointers00> =
            object : TypeLayout<KnonvolatileContextPointers00> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): KnonvolatileContextPointers00 =
                    KnonvolatileContextPointers00(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 88L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 96L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 104L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 112L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: KnonvolatileContextPointers00) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.xmm0)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.xmm1)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.xmm2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.xmm3)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.xmm4)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.xmm5)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.xmm6)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.xmm7)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.xmm8)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.xmm9)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.xmm10)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 88L, value.xmm11)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 96L, value.xmm12)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 104L, value.xmm13)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 112L, value.xmm14)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 120L, value.xmm15)
                }
                override fun readBytes(buf: ByteArray, offset: Int): KnonvolatileContextPointers00 =
                    KnonvolatileContextPointers00(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: KnonvolatileContextPointers00) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.xmm0)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.xmm1)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.xmm2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.xmm3)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.xmm4)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.xmm5)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.xmm6)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.xmm7)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.xmm8)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.xmm9)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.xmm10)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 88, value.xmm11)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 96, value.xmm12)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 104, value.xmm13)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 112, value.xmm14)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 120, value.xmm15)
                }
            }

        public operator fun invoke(
            xmm0: MutPtr,
            xmm1: MutPtr,
            xmm2: MutPtr,
            xmm3: MutPtr,
            xmm4: MutPtr,
            xmm5: MutPtr,
            xmm6: MutPtr,
            xmm7: MutPtr,
            xmm8: MutPtr,
            xmm9: MutPtr,
            xmm10: MutPtr,
            xmm11: MutPtr,
            xmm12: MutPtr,
            xmm13: MutPtr,
            xmm14: MutPtr,
            xmm15: MutPtr,
        ): KnonvolatileContextPointers00 = KnonvolatileContextPointers00(
            xmm0CtorArg = xmm0,
            xmm1CtorArg = xmm1,
            xmm2CtorArg = xmm2,
            xmm3CtorArg = xmm3,
            xmm4CtorArg = xmm4,
            xmm5CtorArg = xmm5,
            xmm6CtorArg = xmm6,
            xmm7CtorArg = xmm7,
            xmm8CtorArg = xmm8,
            xmm9CtorArg = xmm9,
            xmm10CtorArg = xmm10,
            xmm11CtorArg = xmm11,
            xmm12CtorArg = xmm12,
            xmm13CtorArg = xmm13,
            xmm14CtorArg = xmm14,
            xmm15CtorArg = xmm15,
        )
    }
}

public class KnonvolatileContextPointers1 internal constructor(
    integerContextCtorArg: List<MutPtr>?,
    public var anonymous: KnonvolatileContextPointers10?,
) {
    public var integerContext: List<MutPtr>? = integerContextCtorArg
        internal set

    public constructor() : this(
        integerContextCtorArg = null,
        anonymous = null,
    )

    public companion object {
        public operator fun invoke(
            integerContext: List<MutPtr>? = null,
            anonymous: KnonvolatileContextPointers10? = null,
        ): KnonvolatileContextPointers1 = KnonvolatileContextPointers1(
            integerContextCtorArg = integerContext,
            anonymous = anonymous,
        )
    }
}

public open class KnonvolatileContextPointers10 internal constructor(
    raxCtorArg: MutPtr,
    rcxCtorArg: MutPtr,
    rdxCtorArg: MutPtr,
    rbxCtorArg: MutPtr,
    rspCtorArg: MutPtr,
    rbpCtorArg: MutPtr,
    rsiCtorArg: MutPtr,
    rdiCtorArg: MutPtr,
    r8CtorArg: MutPtr,
    r9CtorArg: MutPtr,
    r10CtorArg: MutPtr,
    r11CtorArg: MutPtr,
    r12CtorArg: MutPtr,
    r13CtorArg: MutPtr,
    r14CtorArg: MutPtr,
    r15CtorArg: MutPtr,
) {
    public var rax: MutPtr = raxCtorArg
        internal set

    public var rcx: MutPtr = rcxCtorArg
        internal set

    public var rdx: MutPtr = rdxCtorArg
        internal set

    public var rbx: MutPtr = rbxCtorArg
        internal set

    public var rsp: MutPtr = rspCtorArg
        internal set

    public var rbp: MutPtr = rbpCtorArg
        internal set

    public var rsi: MutPtr = rsiCtorArg
        internal set

    public var rdi: MutPtr = rdiCtorArg
        internal set

    public var r8: MutPtr = r8CtorArg
        internal set

    public var r9: MutPtr = r9CtorArg
        internal set

    public var r10: MutPtr = r10CtorArg
        internal set

    public var r11: MutPtr = r11CtorArg
        internal set

    public var r12: MutPtr = r12CtorArg
        internal set

    public var r13: MutPtr = r13CtorArg
        internal set

    public var r14: MutPtr = r14CtorArg
        internal set

    public var r15: MutPtr = r15CtorArg
        internal set

    public open fun clone(): KnonvolatileContextPointers10 = KnonvolatileContextPointers10(rax, rcx, rdx, rbx, rsp, rbp, rsi, rdi, r8, r9, r10, r11, r12, r13, r14, r15)

    public companion object {
        public fun typeLayout(): TypeLayout<KnonvolatileContextPointers10> =
            object : TypeLayout<KnonvolatileContextPointers10> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): KnonvolatileContextPointers10 =
                    KnonvolatileContextPointers10(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 88L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 96L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 104L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 112L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: KnonvolatileContextPointers10) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.rax)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.rcx)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.rdx)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.rbx)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.rsp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.rbp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.rsi)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.rdi)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.r8)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.r9)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.r10)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 88L, value.r11)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 96L, value.r12)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 104L, value.r13)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 112L, value.r14)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 120L, value.r15)
                }
                override fun readBytes(buf: ByteArray, offset: Int): KnonvolatileContextPointers10 =
                    KnonvolatileContextPointers10(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: KnonvolatileContextPointers10) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.rax)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.rcx)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.rdx)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.rbx)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.rsp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.rbp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.rsi)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.rdi)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.r8)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.r9)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.r10)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 88, value.r11)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 96, value.r12)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 104, value.r13)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 112, value.r14)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 120, value.r15)
                }
            }

        public operator fun invoke(
            rax: MutPtr,
            rcx: MutPtr,
            rdx: MutPtr,
            rbx: MutPtr,
            rsp: MutPtr,
            rbp: MutPtr,
            rsi: MutPtr,
            rdi: MutPtr,
            r8: MutPtr,
            r9: MutPtr,
            r10: MutPtr,
            r11: MutPtr,
            r12: MutPtr,
            r13: MutPtr,
            r14: MutPtr,
            r15: MutPtr,
        ): KnonvolatileContextPointers10 = KnonvolatileContextPointers10(
            raxCtorArg = rax,
            rcxCtorArg = rcx,
            rdxCtorArg = rdx,
            rbxCtorArg = rbx,
            rspCtorArg = rsp,
            rbpCtorArg = rbp,
            rsiCtorArg = rsi,
            rdiCtorArg = rdi,
            r8CtorArg = r8,
            r9CtorArg = r9,
            r10CtorArg = r10,
            r11CtorArg = r11,
            r12CtorArg = r12,
            r13CtorArg = r13,
            r14CtorArg = r14,
            r15CtorArg = r15,
        )
    }
}

public open class M128a(
    public var low: ULong,
    public var high: Long,
) {
    public open fun clone(): M128a = M128a(low, high)

    public companion object {
        public fun typeLayout(): TypeLayout<M128a> =
            object : TypeLayout<M128a> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): M128a =
                    M128a(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: M128a) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.low)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.high)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M128a =
                    M128a(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M128a) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.low)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.high)
                }
            }
    }
}

public val MAX_SYM_NAME: UInt = 2000u

public open class MemoryMappedViewAddress internal constructor(
    valueCtorArg: MutPtr,
) {
    public var value: MutPtr = valueCtorArg
        internal set

    public open fun clone(): MemoryMappedViewAddress = MemoryMappedViewAddress(value)

    public companion object {
        public fun typeLayout(): TypeLayout<MemoryMappedViewAddress> =
            object : TypeLayout<MemoryMappedViewAddress> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): MemoryMappedViewAddress =
                    MemoryMappedViewAddress(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MemoryMappedViewAddress) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MemoryMappedViewAddress =
                    MemoryMappedViewAddress(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MemoryMappedViewAddress) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
                }
            }

        public operator fun invoke(
            value: MutPtr,
        ): MemoryMappedViewAddress = MemoryMappedViewAddress(
            valueCtorArg = value,
        )
    }
}

public open class Moduleentry32w internal constructor(
    public var dwSize: UInt,
    public var th32ModuleID: UInt,
    public var th32ProcessID: UInt,
    public var glblcntUsage: UInt,
    public var proccntUsage: UInt,
    modBaseAddrCtorArg: MutPtr,
    public var modBaseSize: UInt,
    hModuleCtorArg: Hmodule,
    public var szModule: UShortArray,
    public var szExePath: UShortArray,
) {
    public var modBaseAddr: MutPtr = modBaseAddrCtorArg
        internal set

    public var hModule: Hmodule = hModuleCtorArg
        internal set

    public open fun clone(): Moduleentry32w = Moduleentry32w(dwSize, th32ModuleID, th32ProcessID, glblcntUsage, proccntUsage, modBaseAddr, modBaseSize, hModule, szModule, szExePath)

    public companion object {
        public fun typeLayout(): TypeLayout<Moduleentry32w> =
            object : TypeLayout<Moduleentry32w> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 1080L
                override fun readNative(addr: Long): Moduleentry32w =
                    Moduleentry32w(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveArrayLayouts.uShortArray(256)).readNative(addr + 48L),
                        (PrimitiveArrayLayouts.uShortArray(260)).readNative(addr + 560L),
                    )
                override fun writeNative(addr: Long, value: Moduleentry32w) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.dwSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.th32ModuleID)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.th32ProcessID)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.glblcntUsage)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.proccntUsage)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.modBaseAddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.modBaseSize)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.hModule)
                    (PrimitiveArrayLayouts.uShortArray(256)).writeNative(addr + 48L, value.szModule)
                    (PrimitiveArrayLayouts.uShortArray(260)).writeNative(addr + 560L, value.szExePath)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Moduleentry32w =
                    Moduleentry32w(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveArrayLayouts.uShortArray(256)).readBytes(buf, offset + 48),
                        (PrimitiveArrayLayouts.uShortArray(260)).readBytes(buf, offset + 560),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Moduleentry32w) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.dwSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.th32ModuleID)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.th32ProcessID)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.glblcntUsage)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.proccntUsage)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.modBaseAddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.modBaseSize)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.hModule)
                    (PrimitiveArrayLayouts.uShortArray(256)).writeBytes(buf, offset + 48, value.szModule)
                    (PrimitiveArrayLayouts.uShortArray(260)).writeBytes(buf, offset + 560, value.szExePath)
                }
            }

        public operator fun invoke(
            dwSize: UInt,
            th32ModuleID: UInt,
            th32ProcessID: UInt,
            glblcntUsage: UInt,
            proccntUsage: UInt,
            modBaseAddr: MutPtr,
            modBaseSize: UInt,
            hModule: Hmodule,
            szModule: UShortArray,
            szExePath: UShortArray,
        ): Moduleentry32w = Moduleentry32w(
            dwSize = dwSize,
            th32ModuleID = th32ModuleID,
            th32ProcessID = th32ProcessID,
            glblcntUsage = glblcntUsage,
            proccntUsage = proccntUsage,
            modBaseAddrCtorArg = modBaseAddr,
            modBaseSize = modBaseSize,
            hModuleCtorArg = hModule,
            szModule = szModule,
            szExePath = szExePath,
        )
    }
}

public typealias Ntstatus = Int

public typealias PageProtectionFlags = UInt

public val PAGE_READONLY: PageProtectionFlags = 2u

public typealias Pcstr = ConstPtr

public typealias Pcwstr = ConstPtr

public fun interface PenumloadedModulesCallbackw64Fn {
    public operator fun invoke(modulename: Pcwstr, modulebase: ULong, modulesize: UInt, usercontext: ConstPtr): Bool
}

public typealias PenumloadedModulesCallbackw64 = PenumloadedModulesCallbackw64Fn?

public fun interface PfunctionTableAccessRoutine64Fn {
    public operator fun invoke(ahprocess: Handle, addrbase: ULong): MutPtr
}

public typealias PfunctionTableAccessRoutine64 = PfunctionTableAccessRoutine64Fn?

public fun interface PgetModuleBaseRoutine64Fn {
    public operator fun invoke(hprocess: Handle, address: ULong): ULong
}

public typealias PgetModuleBaseRoutine64 = PgetModuleBaseRoutine64Fn?

public fun interface PreadProcessMemoryRoutine64Fn {
    public operator fun invoke(hprocess: Handle, qwbaseaddress: ULong, lpbuffer: MutPtr, nsize: UInt, lpnumberofbytesread: MutPtr): Bool
}

public typealias PreadProcessMemoryRoutine64 = PreadProcessMemoryRoutine64Fn?

public typealias Pstr = MutPtr

public fun interface PtranslateAddressRoutine64Fn {
    public operator fun invoke(hprocess: Handle, hthread: Handle, lpaddr: ConstPtr): ULong
}

public typealias PtranslateAddressRoutine64 = PtranslateAddressRoutine64Fn?

public typealias Pwstr = MutPtr

public typealias RtlVirtualUnwindHandlerType = UInt

public open class SecurityAttributes internal constructor(
    public var nLength: UInt,
    lpSecurityDescriptorCtorArg: MutPtr,
    public var bInheritHandle: Bool,
) {
    public var lpSecurityDescriptor: MutPtr = lpSecurityDescriptorCtorArg
        internal set

    public open fun clone(): SecurityAttributes = SecurityAttributes(nLength, lpSecurityDescriptor, bInheritHandle)

    public companion object {
        public fun typeLayout(): TypeLayout<SecurityAttributes> =
            object : TypeLayout<SecurityAttributes> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): SecurityAttributes =
                    SecurityAttributes(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SecurityAttributes) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nLength)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.lpSecurityDescriptor)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.bInheritHandle)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SecurityAttributes =
                    SecurityAttributes(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SecurityAttributes) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nLength)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.lpSecurityDescriptor)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.bInheritHandle)
                }
            }

        public operator fun invoke(
            nLength: UInt,
            lpSecurityDescriptor: MutPtr,
            bInheritHandle: Bool,
        ): SecurityAttributes = SecurityAttributes(
            nLength = nLength,
            lpSecurityDescriptorCtorArg = lpSecurityDescriptor,
            bInheritHandle = bInheritHandle,
        )
    }
}

public open class Stackframe64 internal constructor(
    public var addrPC: Address64,
    public var addrReturn: Address64,
    public var addrFrame: Address64,
    public var addrStack: Address64,
    public var addrBStore: Address64,
    funcTableEntryCtorArg: MutPtr,
    public var params: ULongArray,
    public var far: Bool,
    public var virtual: Bool,
    public var reserved: ULongArray,
    public var kdHelp: Kdhelp64,
) {
    public var funcTableEntry: MutPtr = funcTableEntryCtorArg
        internal set

    public open fun clone(): Stackframe64 = Stackframe64(addrPC, addrReturn, addrFrame, addrStack, addrBStore, funcTableEntry, params, far, virtual, reserved, kdHelp)

    public companion object {
        public fun typeLayout(): TypeLayout<Stackframe64> =
            object : TypeLayout<Stackframe64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 264L
                override fun readNative(addr: Long): Stackframe64 =
                    Stackframe64(
                        (Address64.typeLayout()).readNative(addr),
                        (Address64.typeLayout()).readNative(addr + 16L),
                        (Address64.typeLayout()).readNative(addr + 32L),
                        (Address64.typeLayout()).readNative(addr + 48L),
                        (Address64.typeLayout()).readNative(addr + 64L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (PrimitiveArrayLayouts.uLongArray(4)).readNative(addr + 88L),
                        (PrimitiveLayouts.I32).readNative(addr + 120L),
                        (PrimitiveLayouts.I32).readNative(addr + 124L),
                        (PrimitiveArrayLayouts.uLongArray(3)).readNative(addr + 128L),
                        (Kdhelp64.typeLayout()).readNative(addr + 152L),
                    )
                override fun writeNative(addr: Long, value: Stackframe64) {
                    (Address64.typeLayout()).writeNative(addr, value.addrPC)
                    (Address64.typeLayout()).writeNative(addr + 16L, value.addrReturn)
                    (Address64.typeLayout()).writeNative(addr + 32L, value.addrFrame)
                    (Address64.typeLayout()).writeNative(addr + 48L, value.addrStack)
                    (Address64.typeLayout()).writeNative(addr + 64L, value.addrBStore)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.funcTableEntry)
                    (PrimitiveArrayLayouts.uLongArray(4)).writeNative(addr + 88L, value.params)
                    (PrimitiveLayouts.I32).writeNative(addr + 120L, value.far)
                    (PrimitiveLayouts.I32).writeNative(addr + 124L, value.virtual)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeNative(addr + 128L, value.reserved)
                    (Kdhelp64.typeLayout()).writeNative(addr + 152L, value.kdHelp)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stackframe64 =
                    Stackframe64(
                        (Address64.typeLayout()).readBytes(buf, offset),
                        (Address64.typeLayout()).readBytes(buf, offset + 16),
                        (Address64.typeLayout()).readBytes(buf, offset + 32),
                        (Address64.typeLayout()).readBytes(buf, offset + 48),
                        (Address64.typeLayout()).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (PrimitiveArrayLayouts.uLongArray(4)).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 124),
                        (PrimitiveArrayLayouts.uLongArray(3)).readBytes(buf, offset + 128),
                        (Kdhelp64.typeLayout()).readBytes(buf, offset + 152),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stackframe64) {
                    (Address64.typeLayout()).writeBytes(buf, offset, value.addrPC)
                    (Address64.typeLayout()).writeBytes(buf, offset + 16, value.addrReturn)
                    (Address64.typeLayout()).writeBytes(buf, offset + 32, value.addrFrame)
                    (Address64.typeLayout()).writeBytes(buf, offset + 48, value.addrStack)
                    (Address64.typeLayout()).writeBytes(buf, offset + 64, value.addrBStore)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.funcTableEntry)
                    (PrimitiveArrayLayouts.uLongArray(4)).writeBytes(buf, offset + 88, value.params)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 120, value.far)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 124, value.virtual)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeBytes(buf, offset + 128, value.reserved)
                    (Kdhelp64.typeLayout()).writeBytes(buf, offset + 152, value.kdHelp)
                }
            }

        public operator fun invoke(
            addrPC: Address64,
            addrReturn: Address64,
            addrFrame: Address64,
            addrStack: Address64,
            addrBStore: Address64,
            funcTableEntry: MutPtr,
            params: ULongArray,
            far: Bool,
            virtual: Bool,
            reserved: ULongArray,
            kdHelp: Kdhelp64,
        ): Stackframe64 = Stackframe64(
            addrPC = addrPC,
            addrReturn = addrReturn,
            addrFrame = addrFrame,
            addrStack = addrStack,
            addrBStore = addrBStore,
            funcTableEntryCtorArg = funcTableEntry,
            params = params,
            far = far,
            virtual = virtual,
            reserved = reserved,
            kdHelp = kdHelp,
        )
    }
}

public open class StackframeEx internal constructor(
    public var addrPC: Address64,
    public var addrReturn: Address64,
    public var addrFrame: Address64,
    public var addrStack: Address64,
    public var addrBStore: Address64,
    funcTableEntryCtorArg: MutPtr,
    public var params: ULongArray,
    public var far: Bool,
    public var virtual: Bool,
    public var reserved: ULongArray,
    public var kdHelp: Kdhelp64,
    public var stackFrameSize: UInt,
    public var inlineFrameContext: UInt,
) {
    public var funcTableEntry: MutPtr = funcTableEntryCtorArg
        internal set

    public open fun clone(): StackframeEx = StackframeEx(addrPC, addrReturn, addrFrame, addrStack, addrBStore, funcTableEntry, params, far, virtual, reserved, kdHelp, stackFrameSize, inlineFrameContext)

    public companion object {
        public fun typeLayout(): TypeLayout<StackframeEx> =
            object : TypeLayout<StackframeEx> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): StackframeEx =
                    StackframeEx(
                        (Address64.typeLayout()).readNative(addr),
                        (Address64.typeLayout()).readNative(addr + 16L),
                        (Address64.typeLayout()).readNative(addr + 32L),
                        (Address64.typeLayout()).readNative(addr + 48L),
                        (Address64.typeLayout()).readNative(addr + 64L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (PrimitiveArrayLayouts.uLongArray(4)).readNative(addr + 88L),
                        (PrimitiveLayouts.I32).readNative(addr + 120L),
                        (PrimitiveLayouts.I32).readNative(addr + 124L),
                        (PrimitiveArrayLayouts.uLongArray(3)).readNative(addr + 128L),
                        (Kdhelp64.typeLayout()).readNative(addr + 152L),
                        (PrimitiveLayouts.U32).readNative(addr + 264L),
                        (PrimitiveLayouts.U32).readNative(addr + 268L),
                    )
                override fun writeNative(addr: Long, value: StackframeEx) {
                    (Address64.typeLayout()).writeNative(addr, value.addrPC)
                    (Address64.typeLayout()).writeNative(addr + 16L, value.addrReturn)
                    (Address64.typeLayout()).writeNative(addr + 32L, value.addrFrame)
                    (Address64.typeLayout()).writeNative(addr + 48L, value.addrStack)
                    (Address64.typeLayout()).writeNative(addr + 64L, value.addrBStore)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.funcTableEntry)
                    (PrimitiveArrayLayouts.uLongArray(4)).writeNative(addr + 88L, value.params)
                    (PrimitiveLayouts.I32).writeNative(addr + 120L, value.far)
                    (PrimitiveLayouts.I32).writeNative(addr + 124L, value.virtual)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeNative(addr + 128L, value.reserved)
                    (Kdhelp64.typeLayout()).writeNative(addr + 152L, value.kdHelp)
                    (PrimitiveLayouts.U32).writeNative(addr + 264L, value.stackFrameSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 268L, value.inlineFrameContext)
                }
                override fun readBytes(buf: ByteArray, offset: Int): StackframeEx =
                    StackframeEx(
                        (Address64.typeLayout()).readBytes(buf, offset),
                        (Address64.typeLayout()).readBytes(buf, offset + 16),
                        (Address64.typeLayout()).readBytes(buf, offset + 32),
                        (Address64.typeLayout()).readBytes(buf, offset + 48),
                        (Address64.typeLayout()).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (PrimitiveArrayLayouts.uLongArray(4)).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 124),
                        (PrimitiveArrayLayouts.uLongArray(3)).readBytes(buf, offset + 128),
                        (Kdhelp64.typeLayout()).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 264),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 268),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: StackframeEx) {
                    (Address64.typeLayout()).writeBytes(buf, offset, value.addrPC)
                    (Address64.typeLayout()).writeBytes(buf, offset + 16, value.addrReturn)
                    (Address64.typeLayout()).writeBytes(buf, offset + 32, value.addrFrame)
                    (Address64.typeLayout()).writeBytes(buf, offset + 48, value.addrStack)
                    (Address64.typeLayout()).writeBytes(buf, offset + 64, value.addrBStore)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.funcTableEntry)
                    (PrimitiveArrayLayouts.uLongArray(4)).writeBytes(buf, offset + 88, value.params)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 120, value.far)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 124, value.virtual)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeBytes(buf, offset + 128, value.reserved)
                    (Kdhelp64.typeLayout()).writeBytes(buf, offset + 152, value.kdHelp)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 264, value.stackFrameSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 268, value.inlineFrameContext)
                }
            }

        public operator fun invoke(
            addrPC: Address64,
            addrReturn: Address64,
            addrFrame: Address64,
            addrStack: Address64,
            addrBStore: Address64,
            funcTableEntry: MutPtr,
            params: ULongArray,
            far: Bool,
            virtual: Bool,
            reserved: ULongArray,
            kdHelp: Kdhelp64,
            stackFrameSize: UInt,
            inlineFrameContext: UInt,
        ): StackframeEx = StackframeEx(
            addrPC = addrPC,
            addrReturn = addrReturn,
            addrFrame = addrFrame,
            addrStack = addrStack,
            addrBStore = addrBStore,
            funcTableEntryCtorArg = funcTableEntry,
            params = params,
            far = far,
            virtual = virtual,
            reserved = reserved,
            kdHelp = kdHelp,
            stackFrameSize = stackFrameSize,
            inlineFrameContext = inlineFrameContext,
        )
    }
}

public open class SymbolInfow(
    public var sizeOfStruct: UInt,
    public var typeIndex: UInt,
    public var reserved: ULongArray,
    public var index: UInt,
    public var size: UInt,
    public var modBase: ULong,
    public var flags: SymbolInfoFlags,
    public var value: ULong,
    public var address: ULong,
    public var register: UInt,
    public var scope: UInt,
    public var tag: UInt,
    public var nameLen: UInt,
    public var maxNameLen: UInt,
    public var name: UShortArray,
) {
    public open fun clone(): SymbolInfow = SymbolInfow(sizeOfStruct, typeIndex, reserved, index, size, modBase, flags, value, address, register, scope, tag, nameLen, maxNameLen, name)

    public companion object {
        public fun typeLayout(): TypeLayout<SymbolInfow> =
            object : TypeLayout<SymbolInfow> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): SymbolInfow =
                    SymbolInfow(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uLongArray(2)).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U32).readNative(addr + 64L),
                        (PrimitiveLayouts.U32).readNative(addr + 68L),
                        (PrimitiveLayouts.U32).readNative(addr + 72L),
                        (PrimitiveLayouts.U32).readNative(addr + 76L),
                        (PrimitiveLayouts.U32).readNative(addr + 80L),
                        (PrimitiveArrayLayouts.uShortArray(1)).readNative(addr + 84L),
                    )
                override fun writeNative(addr: Long, value: SymbolInfow) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.sizeOfStruct)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.typeIndex)
                    (PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr + 8L, value.reserved)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.index)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.size)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.modBase)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.flags)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.value)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.address)
                    (PrimitiveLayouts.U32).writeNative(addr + 64L, value.register)
                    (PrimitiveLayouts.U32).writeNative(addr + 68L, value.scope)
                    (PrimitiveLayouts.U32).writeNative(addr + 72L, value.tag)
                    (PrimitiveLayouts.U32).writeNative(addr + 76L, value.nameLen)
                    (PrimitiveLayouts.U32).writeNative(addr + 80L, value.maxNameLen)
                    (PrimitiveArrayLayouts.uShortArray(1)).writeNative(addr + 84L, value.name)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SymbolInfow =
                    SymbolInfow(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 68),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 76),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 80),
                        (PrimitiveArrayLayouts.uShortArray(1)).readBytes(buf, offset + 84),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SymbolInfow) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.sizeOfStruct)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.typeIndex)
                    (PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset + 8, value.reserved)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.index)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.size)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.modBase)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.flags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.value)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.address)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.register)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 68, value.scope)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 72, value.tag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 76, value.nameLen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 80, value.maxNameLen)
                    (PrimitiveArrayLayouts.uShortArray(1)).writeBytes(buf, offset + 84, value.name)
                }
            }
    }
}

public typealias SymbolInfoFlags = UInt

public val SYMOPT_DEFERRED_LOADS: UInt = 4u

public val TH32CS_SNAPMODULE: CreateToolhelpSnapshotFlags = 8u

public val TRUE: Bool = 1

public open class UnwindHistoryTable(
    public var count: UInt,
    public var localHint: UByte,
    public var globalHint: UByte,
    public var search: UByte,
    public var once: UByte,
    public var lowAddress: Int,
    public var highAddress: Int,
    public var entry: List<UnwindHistoryTableEntry>,
) {
    public open fun clone(): UnwindHistoryTable = UnwindHistoryTable(count, localHint, globalHint, search, once, lowAddress, highAddress, entry)
}

public open class UnwindHistoryTableEntry internal constructor(
    public var imageBase: Int,
    functionEntryCtorArg: MutPtr,
) {
    public var functionEntry: MutPtr = functionEntryCtorArg
        internal set

    public open fun clone(): UnwindHistoryTableEntry = UnwindHistoryTableEntry(imageBase, functionEntry)

    public companion object {
        public fun typeLayout(): TypeLayout<UnwindHistoryTableEntry> =
            object : TypeLayout<UnwindHistoryTableEntry> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): UnwindHistoryTableEntry =
                    UnwindHistoryTableEntry(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: UnwindHistoryTableEntry) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.imageBase)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.functionEntry)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnwindHistoryTableEntry =
                    UnwindHistoryTableEntry(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnwindHistoryTableEntry) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.imageBase)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.functionEntry)
                }
            }

        public operator fun invoke(
            imageBase: Int,
            functionEntry: MutPtr,
        ): UnwindHistoryTableEntry = UnwindHistoryTableEntry(
            imageBase = imageBase,
            functionEntryCtorArg = functionEntry,
        )
    }
}

public typealias WaitEvent = UInt

public open class XsaveFormat(
    public var controlWord: UShort,
    public var statusWord: UShort,
    public var tagWord: UByte,
    public var reserved1: UByte,
    public var errorOpcode: UShort,
    public var errorOffset: UInt,
    public var errorSelector: UShort,
    public var reserved2: UShort,
    public var dataOffset: UInt,
    public var dataSelector: UShort,
    public var reserved3: UShort,
    public var mxCsr: UInt,
    public var mxCsrMask: UInt,
    public var floatRegisters: List<M128a>,
    public var xmmRegisters: List<M128a>,
    public var reserved4: UByteArray,
) {
    public open fun clone(): XsaveFormat = XsaveFormat(controlWord, statusWord, tagWord, reserved1, errorOpcode, errorOffset, errorSelector, reserved2, dataOffset, dataSelector, reserved3, mxCsr, mxCsrMask, floatRegisters, xmmRegisters, reserved4)

    public companion object {
        public fun typeLayout(): TypeLayout<XsaveFormat> =
            object : TypeLayout<XsaveFormat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 512L
                override fun readNative(addr: Long): XsaveFormat =
                    XsaveFormat(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 12L),
                        (PrimitiveLayouts.U16).readNative(addr + 14L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 20L),
                        (PrimitiveLayouts.U16).readNative(addr + 22L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (fixedListTypeLayout(M128a.typeLayout(), 8)).readNative(addr + 32L),
                        (fixedListTypeLayout(M128a.typeLayout(), 16)).readNative(addr + 160L),
                        (PrimitiveArrayLayouts.uByteArray(96)).readNative(addr + 416L),
                    )
                override fun writeNative(addr: Long, value: XsaveFormat) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.controlWord)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.statusWord)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.tagWord)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.reserved1)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.errorOpcode)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.errorOffset)
                    (PrimitiveLayouts.U16).writeNative(addr + 12L, value.errorSelector)
                    (PrimitiveLayouts.U16).writeNative(addr + 14L, value.reserved2)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.dataOffset)
                    (PrimitiveLayouts.U16).writeNative(addr + 20L, value.dataSelector)
                    (PrimitiveLayouts.U16).writeNative(addr + 22L, value.reserved3)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.mxCsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.mxCsrMask)
                    (fixedListTypeLayout(M128a.typeLayout(), 8)).writeNative(addr + 32L, value.floatRegisters)
                    (fixedListTypeLayout(M128a.typeLayout(), 16)).writeNative(addr + 160L, value.xmmRegisters)
                    (PrimitiveArrayLayouts.uByteArray(96)).writeNative(addr + 416L, value.reserved4)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XsaveFormat =
                    XsaveFormat(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 14),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 22),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (fixedListTypeLayout(M128a.typeLayout(), 8)).readBytes(buf, offset + 32),
                        (fixedListTypeLayout(M128a.typeLayout(), 16)).readBytes(buf, offset + 160),
                        (PrimitiveArrayLayouts.uByteArray(96)).readBytes(buf, offset + 416),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XsaveFormat) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.controlWord)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.statusWord)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.tagWord)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.reserved1)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.errorOpcode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.errorOffset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.errorSelector)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 14, value.reserved2)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.dataOffset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 20, value.dataSelector)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 22, value.reserved3)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.mxCsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.mxCsrMask)
                    (fixedListTypeLayout(M128a.typeLayout(), 8)).writeBytes(buf, offset + 32, value.floatRegisters)
                    (fixedListTypeLayout(M128a.typeLayout(), 16)).writeBytes(buf, offset + 160, value.xmmRegisters)
                    (PrimitiveArrayLayouts.uByteArray(96)).writeBytes(buf, offset + 416, value.reserved4)
                }
            }
    }
}

// port-lint: source windows_sys.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Address64> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Address64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Address64 =
                    Address64(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Address64) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.offset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr + 8L, value.segment)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 12L, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Address64 =
                    Address64(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Address64) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.offset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.segment)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.mode)
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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Arm64NtNeon1280> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Arm64NtNeon1280> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Arm64NtNeon1280 =
                    Arm64NtNeon1280(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Arm64NtNeon1280) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.low)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 8L, value.high)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Arm64NtNeon1280 =
                    Arm64NtNeon1280(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Arm64NtNeon1280) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.low)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.high)
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
)

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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Context00> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Context00> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 248L
                override fun readNative(addr: Long): Context00 =
                    Context00(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 56L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 72L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 88L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 96L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 104L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 112L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 120L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 128L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 136L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 144L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 152L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 160L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 168L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 176L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 184L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 192L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 200L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 208L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 216L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 224L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 232L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 240L),
                    )
                override fun writeNative(addr: Long, value: Context00) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.x0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 8L, value.x1)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 16L, value.x2)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 24L, value.x3)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 32L, value.x4)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 40L, value.x5)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 48L, value.x6)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 56L, value.x7)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 64L, value.x8)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 72L, value.x9)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 80L, value.x10)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 88L, value.x11)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 96L, value.x12)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 104L, value.x13)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 112L, value.x14)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 120L, value.x15)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 128L, value.x16)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 136L, value.x17)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 144L, value.x18)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 152L, value.x19)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 160L, value.x20)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 168L, value.x21)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 176L, value.x22)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 184L, value.x23)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 192L, value.x24)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 200L, value.x25)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 208L, value.x26)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 216L, value.x27)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 224L, value.x28)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 232L, value.fp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 240L, value.lr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Context00 =
                    Context00(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 176),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 184),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 192),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 200),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 208),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 216),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 224),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 232),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 240),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Context00) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.x0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.x1)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.x2)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.x3)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.x4)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.x5)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.x6)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.x7)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.x8)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.x9)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.x10)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.x11)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.x12)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.x13)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.x14)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.x15)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.x16)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.x17)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.x18)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.x19)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.x20)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.x21)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.x22)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.x23)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.x24)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.x25)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.x26)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 216, value.x27)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 224, value.x28)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 232, value.fp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 240, value.lr)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ExceptionRecord> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ExceptionRecord> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 152L
                override fun readNative(addr: Long): ExceptionRecord =
                    ExceptionRecord(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 4L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.usize64Array(15)).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: ExceptionRecord) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr, value.exceptionCode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 4L, value.exceptionFlags)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.exceptionRecord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.exceptionAddress)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 24L, value.numberParameters)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.usize64Array(15)).writeNative(addr + 32L, value.exceptionInformation)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExceptionRecord =
                    ExceptionRecord(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.usize64Array(15)).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExceptionRecord) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset, value.exceptionCode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.exceptionFlags)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.exceptionRecord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.exceptionAddress)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.numberParameters)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.usize64Array(15)).writeBytes(buf, offset + 32, value.exceptionInformation)
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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FloatingSaveArea> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FloatingSaveArea> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): FloatingSaveArea =
                    FloatingSaveArea(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 4L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 12L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 20L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uByteArray(80)).readNative(addr + 28L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 108L),
                    )
                override fun writeNative(addr: Long, value: FloatingSaveArea) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.controlWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 4L, value.statusWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 8L, value.tagWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 12L, value.errorOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 16L, value.errorSelector)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 20L, value.dataOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 24L, value.dataSelector)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uByteArray(80)).writeNative(addr + 28L, value.registerArea)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 108L, value.cr0NpxState)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FloatingSaveArea =
                    FloatingSaveArea(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uByteArray(80)).readBytes(buf, offset + 28),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 108),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FloatingSaveArea) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.controlWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.statusWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tagWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.errorOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.errorSelector)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.dataOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.dataSelector)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uByteArray(80)).writeBytes(buf, offset + 28, value.registerArea)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 108, value.cr0NpxState)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ImagehlpLinew64> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ImagehlpLinew64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): ImagehlpLinew64 =
                    ImagehlpLinew64(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: ImagehlpLinew64) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.sizeOfStruct)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.key)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 16L, value.lineNumber)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.fileName)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 32L, value.address)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ImagehlpLinew64 =
                    ImagehlpLinew64(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ImagehlpLinew64) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.sizeOfStruct)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.key)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.lineNumber)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.fileName)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.address)
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
)

public open class ImageArm64RuntimeFunctionEntry0(
    public var unwindData: UInt? = null,
    public var anonymous: ImageArm64RuntimeFunctionEntry00? = null,
)

public open class ImageArm64RuntimeFunctionEntry00(
    public var bitfield: UInt,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ImageArm64RuntimeFunctionEntry00> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ImageArm64RuntimeFunctionEntry00> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ImageArm64RuntimeFunctionEntry00 =
                    ImageArm64RuntimeFunctionEntry00(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ImageArm64RuntimeFunctionEntry00) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.bitfield)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ImageArm64RuntimeFunctionEntry00 =
                    ImageArm64RuntimeFunctionEntry00(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ImageArm64RuntimeFunctionEntry00) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.bitfield)
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
)

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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Kdhelp64> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Kdhelp64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Kdhelp64 =
                    Kdhelp64(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 12L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 20L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 56L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 72L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 76L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 88L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 92L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(2)).readNative(addr + 96L),
                    )
                override fun writeNative(addr: Long, value: Kdhelp64) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.thread)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 8L, value.thCallbackStack)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 12L, value.thCallbackBStore)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 16L, value.nextCallback)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 20L, value.framePointer)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 24L, value.kiCallUserMode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 32L, value.keUserCallbackDispatcher)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 40L, value.systemRangeStart)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 48L, value.kiUserExceptionDispatcher)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 56L, value.stackBase)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 64L, value.stackLimit)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 72L, value.buildVersion)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 76L, value.retpolineStubFunctionTableSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 80L, value.retpolineStubFunctionTable)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 88L, value.retpolineStubOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 92L, value.retpolineStubSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr + 96L, value.reserved0)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Kdhelp64 =
                    Kdhelp64(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 72),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 76),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 88),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 92),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset + 96),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Kdhelp64) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.thread)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.thCallbackStack)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.thCallbackBStore)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.nextCallback)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.framePointer)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.kiCallUserMode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.keUserCallbackDispatcher)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.systemRangeStart)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.kiUserExceptionDispatcher)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.stackBase)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.stackLimit)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 72, value.buildVersion)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 76, value.retpolineStubFunctionTableSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.retpolineStubFunctionTable)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 88, value.retpolineStubOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 92, value.retpolineStubSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset + 96, value.reserved0)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<KnonvolatileContextPointers> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<KnonvolatileContextPointers> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 160L
                override fun readNative(addr: Long): KnonvolatileContextPointers =
                    KnonvolatileContextPointers(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 88L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 96L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 104L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 112L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 120L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 128L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 136L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 144L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 152L),
                    )
                override fun writeNative(addr: Long, value: KnonvolatileContextPointers) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.x19)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.x20)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.x21)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.x22)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.x23)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.x24)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.x25)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.x26)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.x27)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.x28)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.fp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 88L, value.lr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 96L, value.d8)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 104L, value.d9)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 112L, value.d10)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 120L, value.d11)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 128L, value.d12)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 136L, value.d13)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 144L, value.d14)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 152L, value.d15)
                }
                override fun readBytes(buf: ByteArray, offset: Int): KnonvolatileContextPointers =
                    KnonvolatileContextPointers(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 88),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 96),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 104),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 112),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 120),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 128),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 136),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 144),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 152),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: KnonvolatileContextPointers) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.x19)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.x20)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.x21)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.x22)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.x23)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.x24)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.x25)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.x26)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.x27)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.x28)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.fp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 88, value.lr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 96, value.d8)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 104, value.d9)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 112, value.d10)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 120, value.d11)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 128, value.d12)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 136, value.d13)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 144, value.d14)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 152, value.d15)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<KnonvolatileContextPointers00> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<KnonvolatileContextPointers00> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): KnonvolatileContextPointers00 =
                    KnonvolatileContextPointers00(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 88L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 96L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 104L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 112L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: KnonvolatileContextPointers00) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.xmm0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.xmm1)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.xmm2)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.xmm3)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.xmm4)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.xmm5)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.xmm6)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.xmm7)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.xmm8)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.xmm9)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.xmm10)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 88L, value.xmm11)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 96L, value.xmm12)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 104L, value.xmm13)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 112L, value.xmm14)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 120L, value.xmm15)
                }
                override fun readBytes(buf: ByteArray, offset: Int): KnonvolatileContextPointers00 =
                    KnonvolatileContextPointers00(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 88),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 96),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 104),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 112),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: KnonvolatileContextPointers00) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.xmm0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.xmm1)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.xmm2)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.xmm3)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.xmm4)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.xmm5)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.xmm6)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.xmm7)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.xmm8)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.xmm9)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.xmm10)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 88, value.xmm11)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 96, value.xmm12)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 104, value.xmm13)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 112, value.xmm14)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 120, value.xmm15)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<KnonvolatileContextPointers10> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<KnonvolatileContextPointers10> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): KnonvolatileContextPointers10 =
                    KnonvolatileContextPointers10(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 88L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 96L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 104L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 112L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: KnonvolatileContextPointers10) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.rax)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.rcx)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.rdx)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.rbx)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.rsp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.rbp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.rsi)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.rdi)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.r8)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.r9)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.r10)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 88L, value.r11)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 96L, value.r12)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 104L, value.r13)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 112L, value.r14)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 120L, value.r15)
                }
                override fun readBytes(buf: ByteArray, offset: Int): KnonvolatileContextPointers10 =
                    KnonvolatileContextPointers10(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 88),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 96),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 104),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 112),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: KnonvolatileContextPointers10) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.rax)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.rcx)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.rdx)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.rbx)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.rsp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.rbp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.rsi)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.rdi)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.r8)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.r9)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.r10)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 88, value.r11)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 96, value.r12)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 104, value.r13)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 112, value.r14)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 120, value.r15)
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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<M128a> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<M128a> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): M128a =
                    M128a(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: M128a) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.low)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 8L, value.high)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M128a =
                    M128a(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M128a) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.low)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.high)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<MemoryMappedViewAddress> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<MemoryMappedViewAddress> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): MemoryMappedViewAddress =
                    MemoryMappedViewAddress(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MemoryMappedViewAddress) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MemoryMappedViewAddress =
                    MemoryMappedViewAddress(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MemoryMappedViewAddress) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Moduleentry32w> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Moduleentry32w> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 1080L
                override fun readNative(addr: Long): Moduleentry32w =
                    Moduleentry32w(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 4L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 12L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(256)).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(260)).readNative(addr + 560L),
                    )
                override fun writeNative(addr: Long, value: Moduleentry32w) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.dwSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 4L, value.th32ModuleID)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 8L, value.th32ProcessID)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 12L, value.glblcntUsage)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 16L, value.proccntUsage)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.modBaseAddr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 32L, value.modBaseSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.hModule)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(256)).writeNative(addr + 48L, value.szModule)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(260)).writeNative(addr + 560L, value.szExePath)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Moduleentry32w =
                    Moduleentry32w(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(256)).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(260)).readBytes(buf, offset + 560),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Moduleentry32w) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.dwSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.th32ModuleID)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.th32ProcessID)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.glblcntUsage)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.proccntUsage)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.modBaseAddr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.modBaseSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.hModule)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(256)).writeBytes(buf, offset + 48, value.szModule)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(260)).writeBytes(buf, offset + 560, value.szExePath)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<SecurityAttributes> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<SecurityAttributes> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): SecurityAttributes =
                    SecurityAttributes(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SecurityAttributes) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.nLength)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.lpSecurityDescriptor)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 16L, value.bInheritHandle)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SecurityAttributes =
                    SecurityAttributes(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SecurityAttributes) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.nLength)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.lpSecurityDescriptor)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.bInheritHandle)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Stackframe64> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Stackframe64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 264L
                override fun readNative(addr: Long): Stackframe64 =
                    Stackframe64(
                        (Address64.typeLayout()).readNative(addr),
                        (Address64.typeLayout()).readNative(addr + 16L),
                        (Address64.typeLayout()).readNative(addr + 32L),
                        (Address64.typeLayout()).readNative(addr + 48L),
                        (Address64.typeLayout()).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(4)).readNative(addr + 88L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 120L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 124L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(3)).readNative(addr + 128L),
                        (Kdhelp64.typeLayout()).readNative(addr + 152L),
                    )
                override fun writeNative(addr: Long, value: Stackframe64) {
                    (Address64.typeLayout()).writeNative(addr, value.addrPC)
                    (Address64.typeLayout()).writeNative(addr + 16L, value.addrReturn)
                    (Address64.typeLayout()).writeNative(addr + 32L, value.addrFrame)
                    (Address64.typeLayout()).writeNative(addr + 48L, value.addrStack)
                    (Address64.typeLayout()).writeNative(addr + 64L, value.addrBStore)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.funcTableEntry)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(4)).writeNative(addr + 88L, value.params)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 120L, value.far)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 124L, value.virtual)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(3)).writeNative(addr + 128L, value.reserved)
                    (Kdhelp64.typeLayout()).writeNative(addr + 152L, value.kdHelp)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stackframe64 =
                    Stackframe64(
                        (Address64.typeLayout()).readBytes(buf, offset),
                        (Address64.typeLayout()).readBytes(buf, offset + 16),
                        (Address64.typeLayout()).readBytes(buf, offset + 32),
                        (Address64.typeLayout()).readBytes(buf, offset + 48),
                        (Address64.typeLayout()).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(4)).readBytes(buf, offset + 88),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 120),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 124),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(3)).readBytes(buf, offset + 128),
                        (Kdhelp64.typeLayout()).readBytes(buf, offset + 152),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stackframe64) {
                    (Address64.typeLayout()).writeBytes(buf, offset, value.addrPC)
                    (Address64.typeLayout()).writeBytes(buf, offset + 16, value.addrReturn)
                    (Address64.typeLayout()).writeBytes(buf, offset + 32, value.addrFrame)
                    (Address64.typeLayout()).writeBytes(buf, offset + 48, value.addrStack)
                    (Address64.typeLayout()).writeBytes(buf, offset + 64, value.addrBStore)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.funcTableEntry)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(4)).writeBytes(buf, offset + 88, value.params)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 120, value.far)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 124, value.virtual)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(3)).writeBytes(buf, offset + 128, value.reserved)
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<StackframeEx> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<StackframeEx> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): StackframeEx =
                    StackframeEx(
                        (Address64.typeLayout()).readNative(addr),
                        (Address64.typeLayout()).readNative(addr + 16L),
                        (Address64.typeLayout()).readNative(addr + 32L),
                        (Address64.typeLayout()).readNative(addr + 48L),
                        (Address64.typeLayout()).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(4)).readNative(addr + 88L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 120L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 124L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(3)).readNative(addr + 128L),
                        (Kdhelp64.typeLayout()).readNative(addr + 152L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 264L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 268L),
                    )
                override fun writeNative(addr: Long, value: StackframeEx) {
                    (Address64.typeLayout()).writeNative(addr, value.addrPC)
                    (Address64.typeLayout()).writeNative(addr + 16L, value.addrReturn)
                    (Address64.typeLayout()).writeNative(addr + 32L, value.addrFrame)
                    (Address64.typeLayout()).writeNative(addr + 48L, value.addrStack)
                    (Address64.typeLayout()).writeNative(addr + 64L, value.addrBStore)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.funcTableEntry)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(4)).writeNative(addr + 88L, value.params)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 120L, value.far)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 124L, value.virtual)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(3)).writeNative(addr + 128L, value.reserved)
                    (Kdhelp64.typeLayout()).writeNative(addr + 152L, value.kdHelp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 264L, value.stackFrameSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 268L, value.inlineFrameContext)
                }
                override fun readBytes(buf: ByteArray, offset: Int): StackframeEx =
                    StackframeEx(
                        (Address64.typeLayout()).readBytes(buf, offset),
                        (Address64.typeLayout()).readBytes(buf, offset + 16),
                        (Address64.typeLayout()).readBytes(buf, offset + 32),
                        (Address64.typeLayout()).readBytes(buf, offset + 48),
                        (Address64.typeLayout()).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(4)).readBytes(buf, offset + 88),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 120),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 124),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(3)).readBytes(buf, offset + 128),
                        (Kdhelp64.typeLayout()).readBytes(buf, offset + 152),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 264),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 268),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: StackframeEx) {
                    (Address64.typeLayout()).writeBytes(buf, offset, value.addrPC)
                    (Address64.typeLayout()).writeBytes(buf, offset + 16, value.addrReturn)
                    (Address64.typeLayout()).writeBytes(buf, offset + 32, value.addrFrame)
                    (Address64.typeLayout()).writeBytes(buf, offset + 48, value.addrStack)
                    (Address64.typeLayout()).writeBytes(buf, offset + 64, value.addrBStore)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.funcTableEntry)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(4)).writeBytes(buf, offset + 88, value.params)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 120, value.far)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 124, value.virtual)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(3)).writeBytes(buf, offset + 128, value.reserved)
                    (Kdhelp64.typeLayout()).writeBytes(buf, offset + 152, value.kdHelp)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 264, value.stackFrameSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 268, value.inlineFrameContext)
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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<SymbolInfow> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<SymbolInfow> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): SymbolInfow =
                    SymbolInfow(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 4L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(2)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 28L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 56L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 68L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 72L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 76L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(1)).readNative(addr + 84L),
                    )
                override fun writeNative(addr: Long, value: SymbolInfow) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.sizeOfStruct)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 4L, value.typeIndex)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr + 8L, value.reserved)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 24L, value.index)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 28L, value.size)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 32L, value.modBase)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 40L, value.flags)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 48L, value.value)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 56L, value.address)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 64L, value.register)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 68L, value.scope)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 72L, value.tag)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 76L, value.nameLen)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 80L, value.maxNameLen)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(1)).writeNative(addr + 84L, value.name)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SymbolInfow =
                    SymbolInfow(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 68),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 72),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 76),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(1)).readBytes(buf, offset + 84),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SymbolInfow) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.sizeOfStruct)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.typeIndex)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset + 8, value.reserved)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.index)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.size)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.modBase)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.flags)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.value)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.address)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.register)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 68, value.scope)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 72, value.tag)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 76, value.nameLen)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 80, value.maxNameLen)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uShortArray(1)).writeBytes(buf, offset + 84, value.name)
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
)

public open class UnwindHistoryTableEntry internal constructor(
    public var imageBase: Int,
    functionEntryCtorArg: MutPtr,
) {
    public var functionEntry: MutPtr = functionEntryCtorArg
        internal set

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<UnwindHistoryTableEntry> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<UnwindHistoryTableEntry> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): UnwindHistoryTableEntry =
                    UnwindHistoryTableEntry(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: UnwindHistoryTableEntry) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.imageBase)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.functionEntry)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnwindHistoryTableEntry =
                    UnwindHistoryTableEntry(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnwindHistoryTableEntry) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.imageBase)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.functionEntry)
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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<XsaveFormat> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<XsaveFormat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 512L
                override fun readNative(addr: Long): XsaveFormat =
                    XsaveFormat(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr + 2L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr + 4L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr + 5L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr + 6L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr + 12L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr + 14L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr + 20L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr + 22L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 28L),
                        (io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout(M128a.typeLayout(), 8)).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout(M128a.typeLayout(), 16)).readNative(addr + 160L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uByteArray(96)).readNative(addr + 416L),
                    )
                override fun writeNative(addr: Long, value: XsaveFormat) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr, value.controlWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr + 2L, value.statusWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr + 4L, value.tagWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr + 5L, value.reserved1)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr + 6L, value.errorOpcode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 8L, value.errorOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr + 12L, value.errorSelector)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr + 14L, value.reserved2)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 16L, value.dataOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr + 20L, value.dataSelector)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr + 22L, value.reserved3)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 24L, value.mxCsr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 28L, value.mxCsrMask)
                    (io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout(M128a.typeLayout(), 8)).writeNative(addr + 32L, value.floatRegisters)
                    (io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout(M128a.typeLayout(), 16)).writeNative(addr + 160L, value.xmmRegisters)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uByteArray(96)).writeNative(addr + 416L, value.reserved4)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XsaveFormat =
                    XsaveFormat(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset + 14),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset + 20),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset + 22),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout(M128a.typeLayout(), 8)).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout(M128a.typeLayout(), 16)).readBytes(buf, offset + 160),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uByteArray(96)).readBytes(buf, offset + 416),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XsaveFormat) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset, value.controlWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.statusWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.tagWord)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.reserved1)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.errorOpcode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.errorOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.errorSelector)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset + 14, value.reserved2)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.dataOffset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset + 20, value.dataSelector)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset + 22, value.reserved3)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.mxCsr)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.mxCsrMask)
                    (io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout(M128a.typeLayout(), 8)).writeBytes(buf, offset + 32, value.floatRegisters)
                    (io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout(M128a.typeLayout(), 16)).writeBytes(buf, offset + 160, value.xmmRegisters)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.uByteArray(96)).writeBytes(buf, offset + 416, value.reserved4)
                }
            }
    }
}

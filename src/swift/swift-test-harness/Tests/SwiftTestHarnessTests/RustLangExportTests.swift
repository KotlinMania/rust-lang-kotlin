import Testing
import RustLang

@Suite("RustLang Swift Export Behavioral Suite")
struct RustLangExportTests {
    @Test("Inline C memory intrinsics and AArch64 assembly 128-bit helpers work across Swift")
    func testInlineCAndAssemblyIntrinsics() throws {
        typealias Intrinsics = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.intrinsics

        let addr = Intrinsics.kmAlloc(size: 16, align: 16)
        #expect(addr != 0)
        defer {
            Intrinsics.kmDealloc(ptr: addr, size: 16, align: 16)
        }

        Intrinsics.kmStoreU64(addr: addr, value: 0x0102030405060708)
        #expect(Intrinsics.kmLoadU64(addr: addr) == 0x0102030405060708)

        let hi = Intrinsics.kmUmulhU64(a: UInt64.max, b: 2)
        #expect(hi == 1)

        Intrinsics.kmStoreU64(addr: addr, value: 11)
        Intrinsics.kmStoreU64(addr: addr + 8, value: 22)
        let cas = Intrinsics.kmAtomicCxchgU128Pair(
            addr: addr,
            expLo: 11,
            expHi: 22,
            desLo: 33,
            desHi: 44
        )
        #expect(cas.succeeded == true)
        #expect(Intrinsics.kmLoadU64(addr: addr) == 33)
        #expect(Intrinsics.kmLoadU64(addr: addr + 8) == 44)
    }

    @Test("Exported U128, F16, Layout, AtomicU64, and RustString behave faithfully from Swift")
    func testExportedCoreAndAllocTypes() throws {
        typealias Intrinsics = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.intrinsics
        typealias CorePkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core
        typealias AllocLayoutPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.alloc
        typealias AtomicPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.sync.atomic
        typealias StringPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.alloc.string

        let u128Max64 = CorePkg.U128(high: 0, low: UInt64.max)
        let u128Two = CorePkg.U128.Companion.shared.fromULong(v: 2)
        let u128Prod = u128Max64 * u128Two
        #expect(u128Prod.high == 1)
        #expect(u128Prod.low == UInt64.max - 1)

        let half = CorePkg.F16.Companion.shared.fromFloat(value: 1.5)
        #expect(half.toFloat() == 1.5)

        let layout = AllocLayoutPkg.Layout.Companion.shared.fromSizeAlignUnchecked(size: 32, align: 16)
        #expect(layout.size() == 32)
        #expect(layout.align() == 16)

        let atomic = AtomicPkg.AtomicU64.Companion.shared.new(v: 100)
        defer { Intrinsics.kmDealloc(ptr: atomic.rawAddress, size: 8, align: 8) }
        let prev = atomic.fetchAdd(value: 25, order: AtomicPkg.Ordering.SeqCst)
        #expect(prev == 100)
        #expect(atomic.load(order: AtomicPkg.Ordering.SeqCst) == 125)

        let rustStr = StringPkg.RustString.Companion.shared.from(text: "swift-core")
        defer { rustStr.deallocate() }
        rustStr.pushStr(string: "-alloc")
        #expect(rustStr.len() == 16)
        #expect(rustStr.asStr() == "swift-core-alloc")
        #expect(rustStr.asPtr().isNull() == false)
    }

    @Test("Concrete ConstPtr, MutPtr, AtomicPtr, CString, and FFI memory functions export cleanly to Swift")
    func testConcretePointersAndFfiSurface() throws {
        typealias Intrinsics = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.intrinsics
        typealias PtrPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.ptr
        typealias FfiPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.ffi
        typealias AllocFfiPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.alloc.ffi
        typealias AtomicPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.sync.atomic

        let cstr = AllocFfiPkg.CString.Companion.shared.fromStringUnchecked(text: "swift-ptr-abi")
        defer { cstr.deallocate() }
        let ptr: PtrPkg.ConstPtr = cstr.asPtr()
        #expect(ptr.isNull() == false)
        #expect(FfiPkg.strlen(s: ptr) == 13)
        #expect(FfiPkg.CStr.Companion.shared.fromPtr(ptr: ptr).countBytes() == 13)

        let bufA = PtrPkg.MutPtr(rawAddress: Intrinsics.kmAlloc(size: 8, align: 8), rawMeta: 0)
        let bufB = PtrPkg.MutPtr(rawAddress: Intrinsics.kmAlloc(size: 8, align: 8), rawMeta: 0)
        defer {
            Intrinsics.kmDealloc(ptr: bufA.rawAddress, size: 8, align: 8)
            Intrinsics.kmDealloc(ptr: bufB.rawAddress, size: 8, align: 8)
        }
        _ = FfiPkg.memset(s: bufA, c: 0x5A, n: 8)
        _ = FfiPkg.memcpy(dest: bufB, src: bufA.castConst(), n: 8)
        #expect(FfiPkg.memcmp(s1: bufA.castConst(), s2: bufB.castConst(), n: 8) == 0)

        let atomicPtr = AtomicPkg.AtomicPtr.Companion.shared.new(p: bufA)
        defer { Intrinsics.kmDealloc(ptr: atomicPtr.rawAddress, size: 8, align: 8) }
        let swapped = atomicPtr.swap(ptr: bufB, order: AtomicPkg.Ordering.SeqCst)
        #expect(swapped.rawAddress == bufA.rawAddress)
        #expect(atomicPtr.load(order: AtomicPkg.Ordering.SeqCst).rawAddress == bufB.rawAddress)
    }

    @Test("Slice.fromIntList and Vec.fromIntList bridge cleanly to Swift")
    func testSliceAndVecSwiftBridge() throws {
        typealias SlicePkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.slice
        typealias VecPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.alloc.vec
        typealias AllocPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.alloc.alloc

        let ints: [Int32] = [10, 20, 30, 40]
        let slice = SlicePkg.Slice.Companion.shared.fromIntList(values: ints)
        #expect(slice.len() == 4)
        #expect(slice[2] as? Int32 == 30)

        let vec = VecPkg.Vec.Companion.shared.fromIntList(
            values: [7],
            allocator: AllocPkg.Global.shared
        )
        defer { vec.deallocate() }
        vec.push(value: 14 as Int32)
        #expect(vec.len() == 2)
        #expect(vec[0] as? Int32 == 7)
        #expect(vec.pop() as? Int32 == 14)
    }

    @Test("FpCategory, Wrapping, and U128/I128 numeric helpers export cleanly to Swift")
    func testNumModuleAndInt128Extensions() throws {
        typealias CorePkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core
        typealias NumPkg = ExportedKotlinPackages.io.github.kotlinmania.rustlang.core.num

        #expect(NumPkg.rustClassifyDouble(v: Double.nan) == NumPkg.FpCategory.Nan)
        #expect(NumPkg.rustClassifyDouble(v: -0.0) == NumPkg.FpCategory.Zero)
        #expect(NumPkg.rustIsSignNegative(v: -0.0) == true)

        let wrapping = NumPkg.Wrapping(value: 42 as Int32)
        #expect(wrapping.field0 as? Int32 == 42)

        let maxU128 = CorePkg.U128.Companion.shared.MAX
        #expect(maxU128.toFloat().isInfinite)
        #expect(maxU128.countOnes() == 128)
        let two = CorePkg.I128.Companion.shared.fromInt(v: 2)
        #expect(two.pow(exp: 10).toInt() == 1024)
    }
}

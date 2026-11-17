// port-lint: tests x86_64/amx.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.array.fromFn
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.corearch.x86.mmCvtnessSbh

public open class Tilecfg(
    /**
     * 0 `or` 1
     */
    public var palette: UByte,
    public var startRow: UByte,
    /**
     * reserved, must be zero
     */
    public var reservedA0: UByteArray,
    /**
     * number of bytes of one row in each tile
     */
    public var colsb: UShortArray,
    /**
     * reserved, must be zero
     */
    public var reservedB0: UShortArray,
    /**
     * number of rows in each tile
     */
    public var rows: UByteArray,
    /**
     * reserved, must be zero
     */
    public var reservedC0: UByteArray,
) {
    public open fun clone(): Tilecfg = Tilecfg(palette, startRow, reservedA0, colsb, reservedB0, rows, reservedC0)

    public open fun asPtr(): ConstPtr = (constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

    public open fun asMutPtr(): MutPtr = (mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

    public companion object {
        public fun typeLayout(): TypeLayout<Tilecfg> =
            object : TypeLayout<Tilecfg> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Tilecfg =
                    Tilecfg(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveArrayLayouts.uByteArray(14)).readNative(addr + 2L),
                        (PrimitiveArrayLayouts.uShortArray(8)).readNative(addr + 16L),
                        (PrimitiveArrayLayouts.uShortArray(8)).readNative(addr + 32L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 48L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: Tilecfg) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.palette)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.startRow)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeNative(addr + 2L, value.reservedA0)
                    (PrimitiveArrayLayouts.uShortArray(8)).writeNative(addr + 16L, value.colsb)
                    (PrimitiveArrayLayouts.uShortArray(8)).writeNative(addr + 32L, value.reservedB0)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 48L, value.rows)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 56L, value.reservedC0)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tilecfg =
                    Tilecfg(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveArrayLayouts.uByteArray(14)).readBytes(buf, offset + 2),
                        (PrimitiveArrayLayouts.uShortArray(8)).readBytes(buf, offset + 16),
                        (PrimitiveArrayLayouts.uShortArray(8)).readBytes(buf, offset + 32),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 48),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tilecfg) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.palette)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.startRow)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeBytes(buf, offset + 2, value.reservedA0)
                    (PrimitiveArrayLayouts.uShortArray(8)).writeBytes(buf, offset + 16, value.colsb)
                    (PrimitiveArrayLayouts.uShortArray(8)).writeBytes(buf, offset + 32, value.reservedB0)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 48, value.rows)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 56, value.reservedC0)
                }
            }

        public fun new(palette: UByte, startRow: UByte, colsb: UShortArray, rows: UByteArray): Tilecfg = Tilecfg(palette = palette, startRow = startRow, reservedA0 = UByteArray(14) { 0u.toUByte() }, colsb = colsb, reservedB0 = UShortArray(8) { 0u.toUShort() }, rows = rows, reservedC0 = UByteArray(8) { 0u.toUByte() })
    }
}

public fun initAmx() {}

public fun Tile1024i.Companion.zeroed(rows: UShort, colsb: UShort): Tile1024i = Tile1024i(rows = rows, colsb = colsb, tile = tilezeroInternal(rows, colsb))

public fun testTileLoadconfig() {
    run {
        val config = Tilecfg.default()
        tileLoadconfig(config.asPtr())
        tileRelease()
    }
}

public fun testTileStoreconfig() {
    run {
        val config = Tilecfg.new(1, 0, List(8) { 32 }, List(8) { 8 })
        tileLoadconfig(config.asPtr())
        val config = Tilecfg.default()
        tileStoreconfig(config.asMutPtr())
        tileRelease()
        check(config == config)
    }
}

public fun testTileZero() {
    run {
        initAmx()
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileZero(0)
        val localStorageL1166C12 = AddressableArrayLocal.fromList(List(16) { ByteArray(64) { 1 } }, PrimitiveArrayLayouts.byteArray((64).toInt()))
        var out by localStorageL1166C12
        tileStored(localStorageL1166C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(out == List(16) { ByteArray(64) { 0.toByte() } })
    }
}

public fun testTileStored() {
    run {
        initAmx()
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileZero(0)
        val localStorageL1197C12 = AddressableArrayLocal.fromList(List(16) { ByteArray(64) { 1 } }, PrimitiveArrayLayouts.byteArray((64).toInt()))
        var out by localStorageL1197C12
        tileStored(localStorageL1197C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(out == List(16) { ByteArray(64) { 0.toByte() } })
    }
}

public fun testTileLoadd() {
    run {
        initAmx()
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileZero(0)
        val localStorageL1228C12 = AddressableArrayLocal.fromByteArray(ByteArray(1024) { 1 })
        tileLoadd(localStorageL1228C12.elementConstPtr(0), 64, 0)
        val localStorageL1230C12 = AddressableArrayLocal.fromList(List(16) { ByteArray(64) { 0 } }, PrimitiveArrayLayouts.byteArray((64).toInt()))
        var out by localStorageL1230C12
        tileStored(localStorageL1230C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(out == List(16) { ByteArray(64) { 1.toByte() } })
    }
}

public fun testTileStreamLoadd() {
    run {
        initAmx()
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileZero(0)
        val localStorageL1263C12 = AddressableArrayLocal.fromByteArray(ByteArray(1024) { 1 })
        tileStreamLoadd(localStorageL1263C12.elementConstPtr(0), 64, 0)
        val localStorageL1265C12 = AddressableArrayLocal.fromList(List(16) { ByteArray(64) { 0 } }, PrimitiveArrayLayouts.byteArray((64).toInt()))
        var out by localStorageL1265C12
        tileStored(localStorageL1265C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(out == List(16) { ByteArray(64) { 1.toByte() } })
    }
}

public fun testTileRelease() {
    run {
        tileRelease()
    }
}

public val BF16_1: UShort = 0x3f80u

public val BF16_2: UShort = 0x4000u

public fun testTileDpbf16ps() {
    run {
        initAmx()
        val ones = UShortArray(512) { BF16_1 }
        val twos = UShortArray(512) { BF16_2 }
        val res = MutableList(16) { List(16) { 0.0f } }
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr(), 64, 1)
        tileLoadd(twos.asPtr(), 64, 2)
        tileDpbf16ps(0, 1, 2)
        tileStored(res.asMutPtr().cast(), 64, 0)
        tileRelease()
        check(res == List(16) { List(16) { 64.0f } })
    }
}

public fun testTileDpbssd() {
    run {
        initAmx()
        val ones = ByteArray(1024) { (-1).toByte() }
        val twos = ByteArray(1024) { (-2).toByte() }
        val localStorageL1349C12 = AddressableArrayLocal.fromList(List(16) { IntArray(16) { 0 } }, PrimitiveArrayLayouts.intArray((16).toInt()))
        var res by localStorageL1349C12
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr(), 64, 1)
        tileLoadd(twos.asPtr(), 64, 2)
        tileDpbssd(0, 1, 2)
        tileStored(localStorageL1349C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(res == List(16) { IntArray(16) { 128 } })
    }
}

public fun testTileDpbsud() {
    run {
        initAmx()
        val ones = ByteArray(1024) { (-1).toByte() }
        val localStorageL1393C12 = AddressableArrayLocal.fromUByteArray(UByteArray(1024) { 2u.toUByte() })
        val localStorageL1394C12 = AddressableArrayLocal.fromList(List(16) { IntArray(16) { 0 } }, PrimitiveArrayLayouts.intArray((16).toInt()))
        var res by localStorageL1394C12
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr(), 64, 1)
        tileLoadd(localStorageL1393C12.elementConstPtr(0), 64, 2)
        tileDpbsud(0, 1, 2)
        tileStored(localStorageL1394C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(res == List(16) { IntArray(16) { -128 } })
    }
}

public fun testTileDpbusd() {
    run {
        initAmx()
        val localStorageL1437C12 = AddressableArrayLocal.fromUByteArray(UByteArray(1024) { 1u.toUByte() })
        val twos = ByteArray(1024) { (-2).toByte() }
        val localStorageL1439C12 = AddressableArrayLocal.fromList(List(16) { IntArray(16) { 0 } }, PrimitiveArrayLayouts.intArray((16).toInt()))
        var res by localStorageL1439C12
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(localStorageL1437C12.elementConstPtr(0), 64, 1)
        tileLoadd(twos.asPtr(), 64, 2)
        tileDpbusd(0, 1, 2)
        tileStored(localStorageL1439C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(res == List(16) { IntArray(16) { -128 } })
    }
}

public fun testTileDpbuud() {
    run {
        initAmx()
        val localStorageL1482C12 = AddressableArrayLocal.fromUByteArray(UByteArray(1024) { 1u.toUByte() })
        val localStorageL1483C12 = AddressableArrayLocal.fromUByteArray(UByteArray(1024) { 2u.toUByte() })
        val localStorageL1484C12 = AddressableArrayLocal.fromList(List(16) { IntArray(16) { 0 } }, PrimitiveArrayLayouts.intArray((16).toInt()))
        var res by localStorageL1484C12
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(localStorageL1482C12.elementConstPtr(0), 64, 1)
        tileLoadd(localStorageL1483C12.elementConstPtr(0), 64, 2)
        tileDpbuud(0, 1, 2)
        tileStored(localStorageL1484C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(res == List(16) { IntArray(16) { 128 } })
    }
}

public fun testTileDpfp16ps() {
    run {
        initAmx()
        val ones = List(512) { F16.fromFloat(1.0f) }
        val twos = List(512) { F16.fromFloat(2.0f) }
        val res = MutableList(16) { List(16) { 0.0f } }
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr().cast(), 64, 1)
        tileLoadd(twos.asPtr().cast(), 64, 2)
        tileDpfp16ps(0, 1, 2)
        tileStored(res.asMutPtr().cast(), 64, 0)
        tileRelease()
        check(res == List(16) { List(16) { 64.0f } })
    }
}

public fun testTileCmmimfp16ps() {
    run {
        initAmx()
        val ones = List(512) { F16.fromFloat(1.0f) }
        val twos = List(512) { F16.fromFloat(2.0f) }
        val res = MutableList(16) { List(16) { 0.0f } }
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr().cast(), 64, 1)
        tileLoadd(twos.asPtr().cast(), 64, 2)
        tileCmmimfp16ps(0, 1, 2)
        tileStored(res.asMutPtr().cast(), 64, 0)
        tileRelease()
        check(res == List(16) { List(16) { 64.0f } })
    }
}

public fun testTileCmmrlfp16ps() {
    run {
        initAmx()
        val ones = List(512) { F16.fromFloat(1.0f) }
        val twos = List(512) { F16.fromFloat(2.0f) }
        val res = MutableList(16) { List(16) { 0.0f } }
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr().cast(), 64, 1)
        tileLoadd(twos.asPtr().cast(), 64, 2)
        tileCmmrlfp16ps(0, 1, 2)
        tileStored(res.asMutPtr().cast(), 64, 0)
        tileRelease()
        check(res == List(16) { List(16) { 0.0f } })
    }
}

public val BF8_ONE: UByte = 0x3cu

public val BF8_TWO: UByte = 0x40u

public val HF8_ONE: UByte = 0x38u

public val HF8_TWO: UByte = 0x40u

public fun testTileDpbf8ps() {
    run {
        initAmx()
        val ones = UByteArray(1024) { BF8_ONE }
        val twos = UByteArray(1024) { BF8_TWO }
        val res = MutableList(16) { FloatArray(16) { 0.0f } }
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr(), 64, 1)
        tileLoadd(twos.asPtr(), 64, 2)
        tileDpbf8ps(0, 1, 2)
        tileStored(res.asMutPtr(), 64, 0)
        tileRelease()
        check(res == MutableList(16) { FloatArray(16) { 128.0f } })
    }
}

public fun testTileDpbhf8ps() {
    run {
        initAmx()
        val ones = UByteArray(1024) { BF8_ONE }
        val twos = UByteArray(1024) { HF8_TWO }
        val res = MutableList(16) { FloatArray(16) { 0.0f } }
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr(), 64, 1)
        tileLoadd(twos.asPtr(), 64, 2)
        tileDpbhf8ps(0, 1, 2)
        tileStored(res.asMutPtr(), 64, 0)
        tileRelease()
        check(res == MutableList(16) { FloatArray(16) { 128.0f } })
    }
}

public fun testTileDphbf8ps() {
    run {
        initAmx()
        val ones = UByteArray(1024) { HF8_ONE }
        val twos = UByteArray(1024) { BF8_TWO }
        val res = MutableList(16) { FloatArray(16) { 0.0f } }
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr(), 64, 1)
        tileLoadd(twos.asPtr(), 64, 2)
        tileDphbf8ps(0, 1, 2)
        tileStored(res.asMutPtr(), 64, 0)
        tileRelease()
        check(res == MutableList(16) { FloatArray(16) { 128.0f } })
    }
}

public fun testTileDphf8ps() {
    run {
        initAmx()
        val ones = UByteArray(1024) { HF8_ONE }
        val twos = UByteArray(1024) { HF8_TWO }
        val res = MutableList(16) { FloatArray(16) { 0.0f } }
        val config = Tilecfg.default()
        config.palette = 1
        (0..2).forEach({ i ->
            config.colsb[i] = 64
            config.rows[i] = 16
        })
        tileLoadconfig(config.asPtr())
        tileZero(0)
        tileLoadd(ones.asPtr(), 64, 1)
        tileLoadd(twos.asPtr(), 64, 2)
        tileDphf8ps(0, 1, 2)
        tileStored(res.asMutPtr(), 64, 0)
        tileRelease()
        check(res == MutableList(16) { FloatArray(16) { 128.0f } })
    }
}

public fun testTileLoaddrs() {
    run {
        initAmx()
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileZero(0)
        val localStorageL1853C12 = AddressableArrayLocal.fromByteArray(ByteArray(1024) { 1 })
        tileLoaddrs(localStorageL1853C12.elementConstPtr(0), 64, 0)
        val localStorageL1855C12 = AddressableArrayLocal.fromList(List(16) { ByteArray(64) { 0 } }, PrimitiveArrayLayouts.byteArray((64).toInt()))
        var out by localStorageL1855C12
        tileStored(localStorageL1855C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(out == List(16) { ByteArray(64) { 1.toByte() } })
    }
}

public fun testTileStreamLoaddrs() {
    run {
        initAmx()
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileZero(0)
        val localStorageL1888C12 = AddressableArrayLocal.fromByteArray(ByteArray(1024) { 1 })
        tileStreamLoaddrs(localStorageL1888C12.elementConstPtr(0), 64, 0)
        val localStorageL1890C12 = AddressableArrayLocal.fromList(List(16) { ByteArray(64) { 0 } }, PrimitiveArrayLayouts.byteArray((64).toInt()))
        var out by localStorageL1890C12
        tileStored(localStorageL1890C12.elementMutPtr(0), 64, 0)
        tileRelease()
        check(out == List(16) { ByteArray(64) { 1.toByte() } })
    }
}

public fun testTileMovrow() {
    run {
        initAmx()
        val localStorageL1917C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.uByteArray((64).toInt()))
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL1917C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row = tileMovrow(i, 0)
            check(row.asU8x64().asArray() == List(_) { i })
        }
    }
}

public fun testTileMovrowi() {
    run {
        initAmx()
        val localStorageL1960C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.uByteArray((64).toInt()))
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL1960C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row =
                when (i) {
                    0 -> tileMovrowi(0, 0)
                    1 -> tileMovrowi(0, 1)
                    2 -> tileMovrowi(0, 2)
                    3 -> tileMovrowi(0, 3)
                    4 -> tileMovrowi(0, 4)
                    5 -> tileMovrowi(0, 5)
                    6 -> tileMovrowi(0, 6)
                    7 -> tileMovrowi(0, 7)
                    8 -> tileMovrowi(0, 8)
                    9 -> tileMovrowi(0, 9)
                    10 -> tileMovrowi(0, 10)
                    11 -> tileMovrowi(0, 11)
                    12 -> tileMovrowi(0, 12)
                    13 -> tileMovrowi(0, 13)
                    14 -> tileMovrowi(0, 14)
                    15 -> tileMovrowi(0, 15)
                    else -> error("row index out of range")
                }
            check(row.asU8x64().asArray() == List(_) { i })
        }
    }
}

public fun testTileCvtrowd2ps() {
    run {
        initAmx()
        val localStorageL1996C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.uIntArray((16).toInt()))
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL1996C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row = tileCvtrowd2ps(i, 0)
            check(row.asF32x16().asArray() == List(_) { i })
        }
    }
}

public fun testTileCvtrowd2psi() {
    run {
        initAmx()
        val localStorageL2015C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.uIntArray((16).toInt()))
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL2015C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row =
                when (i) {
                    0 -> tileCvtrowd2psi(0, 0)
                    1 -> tileCvtrowd2psi(0, 1)
                    2 -> tileCvtrowd2psi(0, 2)
                    3 -> tileCvtrowd2psi(0, 3)
                    4 -> tileCvtrowd2psi(0, 4)
                    5 -> tileCvtrowd2psi(0, 5)
                    6 -> tileCvtrowd2psi(0, 6)
                    7 -> tileCvtrowd2psi(0, 7)
                    8 -> tileCvtrowd2psi(0, 8)
                    9 -> tileCvtrowd2psi(0, 9)
                    10 -> tileCvtrowd2psi(0, 10)
                    11 -> tileCvtrowd2psi(0, 11)
                    12 -> tileCvtrowd2psi(0, 12)
                    13 -> tileCvtrowd2psi(0, 13)
                    14 -> tileCvtrowd2psi(0, 14)
                    15 -> tileCvtrowd2psi(0, 15)
                    else -> error("row index out of range")
                }
            check(row.asF32x16().asArray() == List(_) { i })
        }
    }
}

public fun testTileCvtrowps2phh() {
    run {
        initAmx()
        val localStorageL2051C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.floatArray((16).toInt()))
        var array by localStorageL2051C12
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL2051C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row = tileCvtrowps2phh(i, 0)
            check(row.asF16x32().asArray() == fromFn({ j -> if (j and 1 == 0) {
                0.0
            } else {
                i
            } }))
        }
    }
}

public fun testTileCvtrowps2phhi() {
    run {
        initAmx()
        val localStorageL2073C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.floatArray((16).toInt()))
        var array by localStorageL2073C12
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL2073C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row =
                when (i) {
                    0 -> tileCvtrowps2phhi(0, 0)
                    1 -> tileCvtrowps2phhi(0, 1)
                    2 -> tileCvtrowps2phhi(0, 2)
                    3 -> tileCvtrowps2phhi(0, 3)
                    4 -> tileCvtrowps2phhi(0, 4)
                    5 -> tileCvtrowps2phhi(0, 5)
                    6 -> tileCvtrowps2phhi(0, 6)
                    7 -> tileCvtrowps2phhi(0, 7)
                    8 -> tileCvtrowps2phhi(0, 8)
                    9 -> tileCvtrowps2phhi(0, 9)
                    10 -> tileCvtrowps2phhi(0, 10)
                    11 -> tileCvtrowps2phhi(0, 11)
                    12 -> tileCvtrowps2phhi(0, 12)
                    13 -> tileCvtrowps2phhi(0, 13)
                    14 -> tileCvtrowps2phhi(0, 14)
                    15 -> tileCvtrowps2phhi(0, 15)
                    else -> error("row index out of range")
                }
            check(row.asF16x32().asArray() == fromFn({ j -> if (j and 1 == 0) {
                0.0
            } else {
                i
            } }))
        }
    }
}

public fun testTileCvtrowps2phl() {
    run {
        initAmx()
        val localStorageL2114C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.floatArray((16).toInt()))
        var array by localStorageL2114C12
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL2114C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row = tileCvtrowps2phl(i, 0)
            check(row.asF16x32().asArray() == fromFn({ j -> if (j and 1 == 0) {
                i
            } else {
                0.0
            } }))
        }
    }
}

public fun testTileCvtrowps2phli() {
    run {
        initAmx()
        val localStorageL2136C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.floatArray((16).toInt()))
        var array by localStorageL2136C12
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL2136C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row =
                when (i) {
                    0 -> tileCvtrowps2phli(0, 0)
                    1 -> tileCvtrowps2phli(0, 1)
                    2 -> tileCvtrowps2phli(0, 2)
                    3 -> tileCvtrowps2phli(0, 3)
                    4 -> tileCvtrowps2phli(0, 4)
                    5 -> tileCvtrowps2phli(0, 5)
                    6 -> tileCvtrowps2phli(0, 6)
                    7 -> tileCvtrowps2phli(0, 7)
                    8 -> tileCvtrowps2phli(0, 8)
                    9 -> tileCvtrowps2phli(0, 9)
                    10 -> tileCvtrowps2phli(0, 10)
                    11 -> tileCvtrowps2phli(0, 11)
                    12 -> tileCvtrowps2phli(0, 12)
                    13 -> tileCvtrowps2phli(0, 13)
                    14 -> tileCvtrowps2phli(0, 14)
                    15 -> tileCvtrowps2phli(0, 15)
                    else -> error("row index out of range")
                }
            check(row.asF16x32().asArray() == fromFn({ j -> if (j and 1 == 0) {
                i
            } else {
                0.0
            } }))
        }
    }
}

public fun testTileCvtrowps2bf16h() {
    run {
        initAmx()
        val localStorageL2177C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.floatArray((16).toInt()))
        var array by localStorageL2177C12
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL2177C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row = tileCvtrowps2bf16h(i, 0)
            check(row.asU16x32().asArray() == fromFn({ j -> if (j and 1 == 0) {
                0
            } else {
                mmCvtnessSbh(i.toFloat()).toBits()
            } }))
        }
    }
}

public fun testTileCvtrowps2bf16hi() {
    run {
        initAmx()
        val localStorageL2203C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.floatArray((16).toInt()))
        var array by localStorageL2203C12
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL2203C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row =
                when (i) {
                    0 -> tileCvtrowps2bf16hi(0, 0)
                    1 -> tileCvtrowps2bf16hi(0, 1)
                    2 -> tileCvtrowps2bf16hi(0, 2)
                    3 -> tileCvtrowps2bf16hi(0, 3)
                    4 -> tileCvtrowps2bf16hi(0, 4)
                    5 -> tileCvtrowps2bf16hi(0, 5)
                    6 -> tileCvtrowps2bf16hi(0, 6)
                    7 -> tileCvtrowps2bf16hi(0, 7)
                    8 -> tileCvtrowps2bf16hi(0, 8)
                    9 -> tileCvtrowps2bf16hi(0, 9)
                    10 -> tileCvtrowps2bf16hi(0, 10)
                    11 -> tileCvtrowps2bf16hi(0, 11)
                    12 -> tileCvtrowps2bf16hi(0, 12)
                    13 -> tileCvtrowps2bf16hi(0, 13)
                    14 -> tileCvtrowps2bf16hi(0, 14)
                    15 -> tileCvtrowps2bf16hi(0, 15)
                    else -> error("row index out of range")
                }
            check(row.asU16x32().asArray() == fromFn({ j -> if (j and 1 == 0) {
                0
            } else {
                mmCvtnessSbh(i.toFloat()).toBits()
            } }))
        }
    }
}

public fun testTileCvtrowps2bf16l() {
    run {
        initAmx()
        val localStorageL2252C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.floatArray((16).toInt()))
        var array by localStorageL2252C12
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL2252C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row = tileCvtrowps2bf16l(i, 0)
            check(row.asU16x32().asArray() == fromFn({ j -> if (j and 1 == 0) {
                mmCvtnessSbh(i.toFloat()).toBits()
            } else {
                0
            } }))
        }
    }
}

public fun testTileCvtrowps2bf16li() {
    run {
        initAmx()
        val localStorageL2278C12 = AddressableArrayLocal.fromList(fromFn({ i -> List(_) { i } }), PrimitiveArrayLayouts.floatArray((16).toInt()))
        var array by localStorageL2278C12
        val config = Tilecfg.default()
        config.palette = 1
        config.colsb[0] = 64
        config.rows[0] = 16
        tileLoadconfig(config.asPtr())
        tileLoadd(localStorageL2278C12.elementConstPtr(0), 64, 0)
        for (i in 0 until 16) {
            val row =
                when (i) {
                    0 -> tileCvtrowps2bf16li(0, 0)
                    1 -> tileCvtrowps2bf16li(0, 1)
                    2 -> tileCvtrowps2bf16li(0, 2)
                    3 -> tileCvtrowps2bf16li(0, 3)
                    4 -> tileCvtrowps2bf16li(0, 4)
                    5 -> tileCvtrowps2bf16li(0, 5)
                    6 -> tileCvtrowps2bf16li(0, 6)
                    7 -> tileCvtrowps2bf16li(0, 7)
                    8 -> tileCvtrowps2bf16li(0, 8)
                    9 -> tileCvtrowps2bf16li(0, 9)
                    10 -> tileCvtrowps2bf16li(0, 10)
                    11 -> tileCvtrowps2bf16li(0, 11)
                    12 -> tileCvtrowps2bf16li(0, 12)
                    13 -> tileCvtrowps2bf16li(0, 13)
                    14 -> tileCvtrowps2bf16li(0, 14)
                    15 -> tileCvtrowps2bf16li(0, 15)
                    else -> error("row index out of range")
                }
            check(row.asU16x32().asArray() == fromFn({ j -> if (j and 1 == 0) {
                mmCvtnessSbh(i.toFloat()).toBits()
            } else {
                0
            } }))
        }
    }
}

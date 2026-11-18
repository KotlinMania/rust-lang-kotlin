// port-lint: source new/linux_uapi/linux/tls.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.tls

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U8

// Header: `uapi / linux / tls.h`

public val TLS_TX: CInt = 1

public val TLS_RX: CInt = 2

public val TLS_TX_ZEROCOPY_RO: CInt = 3

public val TLS_RX_EXPECT_NO_PAD: CInt = 4

public val TLS_1_2_VERSION_MAJOR: U8 = 0x3u.toUByte()

public val TLS_1_2_VERSION_MINOR: U8 = 0x3u.toUByte()

public val TLS_1_2_VERSION: U16 = (((TLS_1_2_VERSION_MAJOR.toUShort()).toUInt() shl 8).toUShort()) or (TLS_1_2_VERSION_MINOR.toUShort())

public val TLS_1_3_VERSION_MAJOR: U8 = 0x3u.toUByte()

public val TLS_1_3_VERSION_MINOR: U8 = 0x4u.toUByte()

public val TLS_1_3_VERSION: U16 = (((TLS_1_3_VERSION_MAJOR.toUShort()).toUInt() shl 8).toUShort()) or (TLS_1_3_VERSION_MINOR.toUShort())

public val TLS_CIPHER_AES_GCM_128: U16 = 51u.toUShort()

public val TLS_CIPHER_AES_GCM_128_IV_SIZE: Int = 8

public val TLS_CIPHER_AES_GCM_128_KEY_SIZE: Int = 16

public val TLS_CIPHER_AES_GCM_128_SALT_SIZE: Int = 4

public val TLS_CIPHER_AES_GCM_128_TAG_SIZE: Int = 16

public val TLS_CIPHER_AES_GCM_128_REC_SEQ_SIZE: Int = 8

public val TLS_CIPHER_AES_GCM_256: U16 = 52u.toUShort()

public val TLS_CIPHER_AES_GCM_256_IV_SIZE: Int = 8

public val TLS_CIPHER_AES_GCM_256_KEY_SIZE: Int = 32

public val TLS_CIPHER_AES_GCM_256_SALT_SIZE: Int = 4

public val TLS_CIPHER_AES_GCM_256_TAG_SIZE: Int = 16

public val TLS_CIPHER_AES_GCM_256_REC_SEQ_SIZE: Int = 8

public val TLS_CIPHER_AES_CCM_128: U16 = 53u.toUShort()

public val TLS_CIPHER_AES_CCM_128_IV_SIZE: Int = 8

public val TLS_CIPHER_AES_CCM_128_KEY_SIZE: Int = 16

public val TLS_CIPHER_AES_CCM_128_SALT_SIZE: Int = 4

public val TLS_CIPHER_AES_CCM_128_TAG_SIZE: Int = 16

public val TLS_CIPHER_AES_CCM_128_REC_SEQ_SIZE: Int = 8

public val TLS_CIPHER_CHACHA20_POLY1305: U16 = 54u.toUShort()

public val TLS_CIPHER_CHACHA20_POLY1305_IV_SIZE: Int = 12

public val TLS_CIPHER_CHACHA20_POLY1305_KEY_SIZE: Int = 32

public val TLS_CIPHER_CHACHA20_POLY1305_SALT_SIZE: Int = 0

public val TLS_CIPHER_CHACHA20_POLY1305_TAG_SIZE: Int = 16

public val TLS_CIPHER_CHACHA20_POLY1305_REC_SEQ_SIZE: Int = 8

public val TLS_CIPHER_SM4_GCM: U16 = 55u.toUShort()

public val TLS_CIPHER_SM4_GCM_IV_SIZE: Int = 8

public val TLS_CIPHER_SM4_GCM_KEY_SIZE: Int = 16

public val TLS_CIPHER_SM4_GCM_SALT_SIZE: Int = 4

public val TLS_CIPHER_SM4_GCM_TAG_SIZE: Int = 16

public val TLS_CIPHER_SM4_GCM_REC_SEQ_SIZE: Int = 8

public val TLS_CIPHER_SM4_CCM: U16 = 56u.toUShort()

public val TLS_CIPHER_SM4_CCM_IV_SIZE: Int = 8

public val TLS_CIPHER_SM4_CCM_KEY_SIZE: Int = 16

public val TLS_CIPHER_SM4_CCM_SALT_SIZE: Int = 4

public val TLS_CIPHER_SM4_CCM_TAG_SIZE: Int = 16

public val TLS_CIPHER_SM4_CCM_REC_SEQ_SIZE: Int = 8

public val TLS_CIPHER_ARIA_GCM_128: U16 = 57u.toUShort()

public val TLS_CIPHER_ARIA_GCM_128_IV_SIZE: Int = 8

public val TLS_CIPHER_ARIA_GCM_128_KEY_SIZE: Int = 16

public val TLS_CIPHER_ARIA_GCM_128_SALT_SIZE: Int = 4

public val TLS_CIPHER_ARIA_GCM_128_TAG_SIZE: Int = 16

public val TLS_CIPHER_ARIA_GCM_128_REC_SEQ_SIZE: Int = 8

public val TLS_CIPHER_ARIA_GCM_256: U16 = 58u.toUShort()

public val TLS_CIPHER_ARIA_GCM_256_IV_SIZE: Int = 8

public val TLS_CIPHER_ARIA_GCM_256_KEY_SIZE: Int = 32

public val TLS_CIPHER_ARIA_GCM_256_SALT_SIZE: Int = 4

public val TLS_CIPHER_ARIA_GCM_256_TAG_SIZE: Int = 16

public val TLS_CIPHER_ARIA_GCM_256_REC_SEQ_SIZE: Int = 8

public val TLS_SET_RECORD_TYPE: CInt = 1

public val TLS_GET_RECORD_TYPE: CInt = 2

public open class TlsCryptoInfo(
    public var version: U16,
    public var cipherType: U16,
) {
    public open fun clone(): TlsCryptoInfo = TlsCryptoInfo(version, cipherType)

    public companion object {
        public fun typeLayout(): TypeLayout<TlsCryptoInfo> =
            object : TypeLayout<TlsCryptoInfo> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): TlsCryptoInfo =
                    TlsCryptoInfo(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: TlsCryptoInfo) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.version)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.cipherType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TlsCryptoInfo =
                    TlsCryptoInfo(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TlsCryptoInfo) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.version)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.cipherType)
                }
            }
    }
}

public open class Tls12CryptoInfoAesGcm128(
    public var info: TlsCryptoInfo,
    public var iv: UByteArray,
    public var key: UByteArray,
    public var salt: UByteArray,
    public var recSeq: UByteArray,
) {
    public open fun clone(): Tls12CryptoInfoAesGcm128 = Tls12CryptoInfoAesGcm128(info, iv, key, salt, recSeq)

    public companion object {
        public fun typeLayout(): TypeLayout<Tls12CryptoInfoAesGcm128> =
            object : TypeLayout<Tls12CryptoInfoAesGcm128> {
                override val alignBytes: Long = 2L
                private val off2: Long = alignUpBytes((4L + ((TLS_CIPHER_AES_GCM_128_IV_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off3: Long = alignUpBytes((off2 + ((TLS_CIPHER_AES_GCM_128_KEY_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off4: Long = alignUpBytes((off3 + ((TLS_CIPHER_AES_GCM_128_SALT_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off4 + ((TLS_CIPHER_AES_GCM_128_REC_SEQ_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 2L)
                override fun readNative(addr: Long): Tls12CryptoInfoAesGcm128 =
                    Tls12CryptoInfoAesGcm128(
                        (TlsCryptoInfo.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_IV_SIZE).toInt())).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_KEY_SIZE).toInt())).readNative(addr + off2),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_SALT_SIZE).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_REC_SEQ_SIZE).toInt())).readNative(addr + off4),
                    )
                override fun writeNative(addr: Long, value: Tls12CryptoInfoAesGcm128) {
                    (TlsCryptoInfo.typeLayout()).writeNative(addr, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_IV_SIZE).toInt())).writeNative(addr + 4L, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_KEY_SIZE).toInt())).writeNative(addr + off2, value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_SALT_SIZE).toInt())).writeNative(addr + off3, value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_REC_SEQ_SIZE).toInt())).writeNative(addr + off4, value.recSeq)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tls12CryptoInfoAesGcm128 =
                    Tls12CryptoInfoAesGcm128(
                        (TlsCryptoInfo.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_IV_SIZE).toInt())).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_KEY_SIZE).toInt())).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_SALT_SIZE).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_REC_SEQ_SIZE).toInt())).readBytes(buf, offset + off4.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tls12CryptoInfoAesGcm128) {
                    (TlsCryptoInfo.typeLayout()).writeBytes(buf, offset, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_IV_SIZE).toInt())).writeBytes(buf, offset + 4, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_KEY_SIZE).toInt())).writeBytes(buf, offset + off2.toInt(), value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_SALT_SIZE).toInt())).writeBytes(buf, offset + off3.toInt(), value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_128_REC_SEQ_SIZE).toInt())).writeBytes(buf, offset + off4.toInt(), value.recSeq)
                }
            }
    }
}

public open class Tls12CryptoInfoAesGcm256(
    public var info: TlsCryptoInfo,
    public var iv: UByteArray,
    public var key: UByteArray,
    public var salt: UByteArray,
    public var recSeq: UByteArray,
) {
    public open fun clone(): Tls12CryptoInfoAesGcm256 = Tls12CryptoInfoAesGcm256(info, iv, key, salt, recSeq)

    public companion object {
        public fun typeLayout(): TypeLayout<Tls12CryptoInfoAesGcm256> =
            object : TypeLayout<Tls12CryptoInfoAesGcm256> {
                override val alignBytes: Long = 2L
                private val off2: Long = alignUpBytes((4L + ((TLS_CIPHER_AES_GCM_256_IV_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off3: Long = alignUpBytes((off2 + ((TLS_CIPHER_AES_GCM_256_KEY_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off4: Long = alignUpBytes((off3 + ((TLS_CIPHER_AES_GCM_256_SALT_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off4 + ((TLS_CIPHER_AES_GCM_256_REC_SEQ_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 2L)
                override fun readNative(addr: Long): Tls12CryptoInfoAesGcm256 =
                    Tls12CryptoInfoAesGcm256(
                        (TlsCryptoInfo.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_IV_SIZE).toInt())).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_KEY_SIZE).toInt())).readNative(addr + off2),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_SALT_SIZE).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_REC_SEQ_SIZE).toInt())).readNative(addr + off4),
                    )
                override fun writeNative(addr: Long, value: Tls12CryptoInfoAesGcm256) {
                    (TlsCryptoInfo.typeLayout()).writeNative(addr, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_IV_SIZE).toInt())).writeNative(addr + 4L, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_KEY_SIZE).toInt())).writeNative(addr + off2, value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_SALT_SIZE).toInt())).writeNative(addr + off3, value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_REC_SEQ_SIZE).toInt())).writeNative(addr + off4, value.recSeq)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tls12CryptoInfoAesGcm256 =
                    Tls12CryptoInfoAesGcm256(
                        (TlsCryptoInfo.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_IV_SIZE).toInt())).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_KEY_SIZE).toInt())).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_SALT_SIZE).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_REC_SEQ_SIZE).toInt())).readBytes(buf, offset + off4.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tls12CryptoInfoAesGcm256) {
                    (TlsCryptoInfo.typeLayout()).writeBytes(buf, offset, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_IV_SIZE).toInt())).writeBytes(buf, offset + 4, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_KEY_SIZE).toInt())).writeBytes(buf, offset + off2.toInt(), value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_SALT_SIZE).toInt())).writeBytes(buf, offset + off3.toInt(), value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_GCM_256_REC_SEQ_SIZE).toInt())).writeBytes(buf, offset + off4.toInt(), value.recSeq)
                }
            }
    }
}

public open class Tls12CryptoInfoAesCcm128(
    public var info: TlsCryptoInfo,
    public var iv: UByteArray,
    public var key: UByteArray,
    public var salt: UByteArray,
    public var recSeq: UByteArray,
) {
    public open fun clone(): Tls12CryptoInfoAesCcm128 = Tls12CryptoInfoAesCcm128(info, iv, key, salt, recSeq)

    public companion object {
        public fun typeLayout(): TypeLayout<Tls12CryptoInfoAesCcm128> =
            object : TypeLayout<Tls12CryptoInfoAesCcm128> {
                override val alignBytes: Long = 2L
                private val off2: Long = alignUpBytes((4L + ((TLS_CIPHER_AES_CCM_128_IV_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off3: Long = alignUpBytes((off2 + ((TLS_CIPHER_AES_CCM_128_KEY_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off4: Long = alignUpBytes((off3 + ((TLS_CIPHER_AES_CCM_128_SALT_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off4 + ((TLS_CIPHER_AES_CCM_128_REC_SEQ_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 2L)
                override fun readNative(addr: Long): Tls12CryptoInfoAesCcm128 =
                    Tls12CryptoInfoAesCcm128(
                        (TlsCryptoInfo.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_IV_SIZE).toInt())).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_KEY_SIZE).toInt())).readNative(addr + off2),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_SALT_SIZE).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_REC_SEQ_SIZE).toInt())).readNative(addr + off4),
                    )
                override fun writeNative(addr: Long, value: Tls12CryptoInfoAesCcm128) {
                    (TlsCryptoInfo.typeLayout()).writeNative(addr, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_IV_SIZE).toInt())).writeNative(addr + 4L, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_KEY_SIZE).toInt())).writeNative(addr + off2, value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_SALT_SIZE).toInt())).writeNative(addr + off3, value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_REC_SEQ_SIZE).toInt())).writeNative(addr + off4, value.recSeq)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tls12CryptoInfoAesCcm128 =
                    Tls12CryptoInfoAesCcm128(
                        (TlsCryptoInfo.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_IV_SIZE).toInt())).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_KEY_SIZE).toInt())).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_SALT_SIZE).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_REC_SEQ_SIZE).toInt())).readBytes(buf, offset + off4.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tls12CryptoInfoAesCcm128) {
                    (TlsCryptoInfo.typeLayout()).writeBytes(buf, offset, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_IV_SIZE).toInt())).writeBytes(buf, offset + 4, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_KEY_SIZE).toInt())).writeBytes(buf, offset + off2.toInt(), value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_SALT_SIZE).toInt())).writeBytes(buf, offset + off3.toInt(), value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_AES_CCM_128_REC_SEQ_SIZE).toInt())).writeBytes(buf, offset + off4.toInt(), value.recSeq)
                }
            }
    }
}

public open class Tls12CryptoInfoChacha20Poly1305(
    public var info: TlsCryptoInfo,
    public var iv: UByteArray,
    public var key: UByteArray,
    public var salt: UByteArray,
    public var recSeq: UByteArray,
) {
    public open fun clone(): Tls12CryptoInfoChacha20Poly1305 = Tls12CryptoInfoChacha20Poly1305(info, iv, key, salt, recSeq)

    public companion object {
        public fun typeLayout(): TypeLayout<Tls12CryptoInfoChacha20Poly1305> =
            object : TypeLayout<Tls12CryptoInfoChacha20Poly1305> {
                override val alignBytes: Long = 2L
                private val off2: Long = alignUpBytes((4L + ((TLS_CIPHER_CHACHA20_POLY1305_IV_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off3: Long = alignUpBytes((off2 + ((TLS_CIPHER_CHACHA20_POLY1305_KEY_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off4: Long = alignUpBytes((off3 + ((TLS_CIPHER_CHACHA20_POLY1305_SALT_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off4 + ((TLS_CIPHER_CHACHA20_POLY1305_REC_SEQ_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 2L)
                override fun readNative(addr: Long): Tls12CryptoInfoChacha20Poly1305 =
                    Tls12CryptoInfoChacha20Poly1305(
                        (TlsCryptoInfo.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_IV_SIZE).toInt())).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_KEY_SIZE).toInt())).readNative(addr + off2),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_SALT_SIZE).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_REC_SEQ_SIZE).toInt())).readNative(addr + off4),
                    )
                override fun writeNative(addr: Long, value: Tls12CryptoInfoChacha20Poly1305) {
                    (TlsCryptoInfo.typeLayout()).writeNative(addr, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_IV_SIZE).toInt())).writeNative(addr + 4L, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_KEY_SIZE).toInt())).writeNative(addr + off2, value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_SALT_SIZE).toInt())).writeNative(addr + off3, value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_REC_SEQ_SIZE).toInt())).writeNative(addr + off4, value.recSeq)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tls12CryptoInfoChacha20Poly1305 =
                    Tls12CryptoInfoChacha20Poly1305(
                        (TlsCryptoInfo.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_IV_SIZE).toInt())).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_KEY_SIZE).toInt())).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_SALT_SIZE).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_REC_SEQ_SIZE).toInt())).readBytes(buf, offset + off4.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tls12CryptoInfoChacha20Poly1305) {
                    (TlsCryptoInfo.typeLayout()).writeBytes(buf, offset, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_IV_SIZE).toInt())).writeBytes(buf, offset + 4, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_KEY_SIZE).toInt())).writeBytes(buf, offset + off2.toInt(), value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_SALT_SIZE).toInt())).writeBytes(buf, offset + off3.toInt(), value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_CHACHA20_POLY1305_REC_SEQ_SIZE).toInt())).writeBytes(buf, offset + off4.toInt(), value.recSeq)
                }
            }
    }
}

public open class Tls12CryptoInfoSm4Gcm(
    public var info: TlsCryptoInfo,
    public var iv: UByteArray,
    public var key: UByteArray,
    public var salt: UByteArray,
    public var recSeq: UByteArray,
) {
    public open fun clone(): Tls12CryptoInfoSm4Gcm = Tls12CryptoInfoSm4Gcm(info, iv, key, salt, recSeq)

    public companion object {
        public fun typeLayout(): TypeLayout<Tls12CryptoInfoSm4Gcm> =
            object : TypeLayout<Tls12CryptoInfoSm4Gcm> {
                override val alignBytes: Long = 2L
                private val off2: Long = alignUpBytes((4L + ((TLS_CIPHER_SM4_GCM_IV_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off3: Long = alignUpBytes((off2 + ((TLS_CIPHER_SM4_GCM_KEY_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off4: Long = alignUpBytes((off3 + ((TLS_CIPHER_SM4_GCM_SALT_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off4 + ((TLS_CIPHER_SM4_GCM_REC_SEQ_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 2L)
                override fun readNative(addr: Long): Tls12CryptoInfoSm4Gcm =
                    Tls12CryptoInfoSm4Gcm(
                        (TlsCryptoInfo.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_IV_SIZE).toInt())).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_KEY_SIZE).toInt())).readNative(addr + off2),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_SALT_SIZE).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_REC_SEQ_SIZE).toInt())).readNative(addr + off4),
                    )
                override fun writeNative(addr: Long, value: Tls12CryptoInfoSm4Gcm) {
                    (TlsCryptoInfo.typeLayout()).writeNative(addr, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_IV_SIZE).toInt())).writeNative(addr + 4L, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_KEY_SIZE).toInt())).writeNative(addr + off2, value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_SALT_SIZE).toInt())).writeNative(addr + off3, value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_REC_SEQ_SIZE).toInt())).writeNative(addr + off4, value.recSeq)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tls12CryptoInfoSm4Gcm =
                    Tls12CryptoInfoSm4Gcm(
                        (TlsCryptoInfo.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_IV_SIZE).toInt())).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_KEY_SIZE).toInt())).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_SALT_SIZE).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_REC_SEQ_SIZE).toInt())).readBytes(buf, offset + off4.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tls12CryptoInfoSm4Gcm) {
                    (TlsCryptoInfo.typeLayout()).writeBytes(buf, offset, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_IV_SIZE).toInt())).writeBytes(buf, offset + 4, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_KEY_SIZE).toInt())).writeBytes(buf, offset + off2.toInt(), value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_SALT_SIZE).toInt())).writeBytes(buf, offset + off3.toInt(), value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_GCM_REC_SEQ_SIZE).toInt())).writeBytes(buf, offset + off4.toInt(), value.recSeq)
                }
            }
    }
}

public open class Tls12CryptoInfoSm4Ccm(
    public var info: TlsCryptoInfo,
    public var iv: UByteArray,
    public var key: UByteArray,
    public var salt: UByteArray,
    public var recSeq: UByteArray,
) {
    public open fun clone(): Tls12CryptoInfoSm4Ccm = Tls12CryptoInfoSm4Ccm(info, iv, key, salt, recSeq)

    public companion object {
        public fun typeLayout(): TypeLayout<Tls12CryptoInfoSm4Ccm> =
            object : TypeLayout<Tls12CryptoInfoSm4Ccm> {
                override val alignBytes: Long = 2L
                private val off2: Long = alignUpBytes((4L + ((TLS_CIPHER_SM4_CCM_IV_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off3: Long = alignUpBytes((off2 + ((TLS_CIPHER_SM4_CCM_KEY_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off4: Long = alignUpBytes((off3 + ((TLS_CIPHER_SM4_CCM_SALT_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off4 + ((TLS_CIPHER_SM4_CCM_REC_SEQ_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 2L)
                override fun readNative(addr: Long): Tls12CryptoInfoSm4Ccm =
                    Tls12CryptoInfoSm4Ccm(
                        (TlsCryptoInfo.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_IV_SIZE).toInt())).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_KEY_SIZE).toInt())).readNative(addr + off2),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_SALT_SIZE).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_REC_SEQ_SIZE).toInt())).readNative(addr + off4),
                    )
                override fun writeNative(addr: Long, value: Tls12CryptoInfoSm4Ccm) {
                    (TlsCryptoInfo.typeLayout()).writeNative(addr, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_IV_SIZE).toInt())).writeNative(addr + 4L, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_KEY_SIZE).toInt())).writeNative(addr + off2, value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_SALT_SIZE).toInt())).writeNative(addr + off3, value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_REC_SEQ_SIZE).toInt())).writeNative(addr + off4, value.recSeq)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tls12CryptoInfoSm4Ccm =
                    Tls12CryptoInfoSm4Ccm(
                        (TlsCryptoInfo.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_IV_SIZE).toInt())).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_KEY_SIZE).toInt())).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_SALT_SIZE).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_REC_SEQ_SIZE).toInt())).readBytes(buf, offset + off4.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tls12CryptoInfoSm4Ccm) {
                    (TlsCryptoInfo.typeLayout()).writeBytes(buf, offset, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_IV_SIZE).toInt())).writeBytes(buf, offset + 4, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_KEY_SIZE).toInt())).writeBytes(buf, offset + off2.toInt(), value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_SALT_SIZE).toInt())).writeBytes(buf, offset + off3.toInt(), value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_SM4_CCM_REC_SEQ_SIZE).toInt())).writeBytes(buf, offset + off4.toInt(), value.recSeq)
                }
            }
    }
}

public open class Tls12CryptoInfoAriaGcm128(
    public var info: TlsCryptoInfo,
    public var iv: UByteArray,
    public var key: UByteArray,
    public var salt: UByteArray,
    public var recSeq: UByteArray,
) {
    public open fun clone(): Tls12CryptoInfoAriaGcm128 = Tls12CryptoInfoAriaGcm128(info, iv, key, salt, recSeq)

    public companion object {
        public fun typeLayout(): TypeLayout<Tls12CryptoInfoAriaGcm128> =
            object : TypeLayout<Tls12CryptoInfoAriaGcm128> {
                override val alignBytes: Long = 2L
                private val off2: Long = alignUpBytes((4L + ((TLS_CIPHER_ARIA_GCM_128_IV_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off3: Long = alignUpBytes((off2 + ((TLS_CIPHER_ARIA_GCM_128_KEY_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off4: Long = alignUpBytes((off3 + ((TLS_CIPHER_ARIA_GCM_128_SALT_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off4 + ((TLS_CIPHER_ARIA_GCM_128_REC_SEQ_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 2L)
                override fun readNative(addr: Long): Tls12CryptoInfoAriaGcm128 =
                    Tls12CryptoInfoAriaGcm128(
                        (TlsCryptoInfo.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_IV_SIZE).toInt())).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_KEY_SIZE).toInt())).readNative(addr + off2),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_SALT_SIZE).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_REC_SEQ_SIZE).toInt())).readNative(addr + off4),
                    )
                override fun writeNative(addr: Long, value: Tls12CryptoInfoAriaGcm128) {
                    (TlsCryptoInfo.typeLayout()).writeNative(addr, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_IV_SIZE).toInt())).writeNative(addr + 4L, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_KEY_SIZE).toInt())).writeNative(addr + off2, value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_SALT_SIZE).toInt())).writeNative(addr + off3, value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_REC_SEQ_SIZE).toInt())).writeNative(addr + off4, value.recSeq)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tls12CryptoInfoAriaGcm128 =
                    Tls12CryptoInfoAriaGcm128(
                        (TlsCryptoInfo.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_IV_SIZE).toInt())).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_KEY_SIZE).toInt())).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_SALT_SIZE).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_REC_SEQ_SIZE).toInt())).readBytes(buf, offset + off4.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tls12CryptoInfoAriaGcm128) {
                    (TlsCryptoInfo.typeLayout()).writeBytes(buf, offset, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_IV_SIZE).toInt())).writeBytes(buf, offset + 4, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_KEY_SIZE).toInt())).writeBytes(buf, offset + off2.toInt(), value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_SALT_SIZE).toInt())).writeBytes(buf, offset + off3.toInt(), value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_128_REC_SEQ_SIZE).toInt())).writeBytes(buf, offset + off4.toInt(), value.recSeq)
                }
            }
    }
}

public open class Tls12CryptoInfoAriaGcm256(
    public var info: TlsCryptoInfo,
    public var iv: UByteArray,
    public var key: UByteArray,
    public var salt: UByteArray,
    public var recSeq: UByteArray,
) {
    public open fun clone(): Tls12CryptoInfoAriaGcm256 = Tls12CryptoInfoAriaGcm256(info, iv, key, salt, recSeq)

    public companion object {
        public fun typeLayout(): TypeLayout<Tls12CryptoInfoAriaGcm256> =
            object : TypeLayout<Tls12CryptoInfoAriaGcm256> {
                override val alignBytes: Long = 2L
                private val off2: Long = alignUpBytes((4L + ((TLS_CIPHER_ARIA_GCM_256_IV_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off3: Long = alignUpBytes((off2 + ((TLS_CIPHER_ARIA_GCM_256_KEY_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off4: Long = alignUpBytes((off3 + ((TLS_CIPHER_ARIA_GCM_256_SALT_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off4 + ((TLS_CIPHER_ARIA_GCM_256_REC_SEQ_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 2L)
                override fun readNative(addr: Long): Tls12CryptoInfoAriaGcm256 =
                    Tls12CryptoInfoAriaGcm256(
                        (TlsCryptoInfo.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_IV_SIZE).toInt())).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_KEY_SIZE).toInt())).readNative(addr + off2),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_SALT_SIZE).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_REC_SEQ_SIZE).toInt())).readNative(addr + off4),
                    )
                override fun writeNative(addr: Long, value: Tls12CryptoInfoAriaGcm256) {
                    (TlsCryptoInfo.typeLayout()).writeNative(addr, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_IV_SIZE).toInt())).writeNative(addr + 4L, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_KEY_SIZE).toInt())).writeNative(addr + off2, value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_SALT_SIZE).toInt())).writeNative(addr + off3, value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_REC_SEQ_SIZE).toInt())).writeNative(addr + off4, value.recSeq)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tls12CryptoInfoAriaGcm256 =
                    Tls12CryptoInfoAriaGcm256(
                        (TlsCryptoInfo.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_IV_SIZE).toInt())).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_KEY_SIZE).toInt())).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_SALT_SIZE).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_REC_SEQ_SIZE).toInt())).readBytes(buf, offset + off4.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tls12CryptoInfoAriaGcm256) {
                    (TlsCryptoInfo.typeLayout()).writeBytes(buf, offset, value.info)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_IV_SIZE).toInt())).writeBytes(buf, offset + 4, value.iv)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_KEY_SIZE).toInt())).writeBytes(buf, offset + off2.toInt(), value.key)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_SALT_SIZE).toInt())).writeBytes(buf, offset + off3.toInt(), value.salt)
                    (PrimitiveArrayLayouts.uByteArray((TLS_CIPHER_ARIA_GCM_256_REC_SEQ_SIZE).toInt())).writeBytes(buf, offset + off4.toInt(), value.recSeq)
                }
            }
    }
}

public val TLS_INFO_UNSPEC: CInt = 0

public val TLS_INFO_VERSION: CInt = TLS_INFO_UNSPEC + 1

public val TLS_INFO_CIPHER: CInt = TLS_INFO_VERSION + 1

public val TLS_INFO_TXCONF: CInt = TLS_INFO_CIPHER + 1

public val TLS_INFO_RXCONF: CInt = TLS_INFO_TXCONF + 1

public val TLS_INFO_ZC_RO_TX: CInt = TLS_INFO_RXCONF + 1

public val TLS_INFO_RX_NO_PAD: CInt = TLS_INFO_ZC_RO_TX + 1

public val TLS_INFO_TX_MAX_PAYLOAD_LEN: CInt = TLS_INFO_RX_NO_PAD + 1

public val TLS_INFO_MAX1: CInt = TLS_INFO_TX_MAX_PAYLOAD_LEN + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val TLS_INFO_MAX: CInt = TLS_INFO_MAX1 - 1

public val TLS_CONF_BASE: CInt = 1

public val TLS_CONF_SW: CInt = 2

public val TLS_CONF_HW: CInt = 3

public val TLS_CONF_HW_RECORD: CInt = 4

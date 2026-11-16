// port-lint: source x86/kl.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// AES Key Locker Intrinsics
//
// The Intrinsics here correspond to those in the `keylockerintrin.h` C header.

public open class EncodeKey128Output(
    public var field0: UInt,
    public var field1: M128i,
    public var field2: M128i,
    public var field3: M128i,
    public var field4: M128i,
    public var field5: M128i,
    public var field6: M128i,
)

public open class EncodeKey256Output(
    public var field0: UInt,
    public var field1: M128i,
    public var field2: M128i,
    public var field3: M128i,
    public var field4: M128i,
    public var field5: M128i,
    public var field6: M128i,
    public var field7: M128i,
)

public open class AesOutput(
    public var field0: UByte,
    public var field1: M128i,
)

public open class WideAesOutput(
    public var field0: UByte,
    public var field1: M128i,
    public var field2: M128i,
    public var field3: M128i,
    public var field4: M128i,
    public var field5: M128i,
    public var field6: M128i,
    public var field7: M128i,
    public var field8: M128i,
)

public expect fun loadiwkey(integrityKey: M128i, keyLo: M128i, keyHi: M128i, control: UInt)

public expect fun encodekey128(keyMetadata: UInt, key: M128i): EncodeKey128Output

public expect fun encodekey256(keyMetadata: UInt, keyLo: M128i, keyHi: M128i): EncodeKey256Output

public expect fun aesenc128kl(data: M128i, handle: ConstPtr): AesOutput

public expect fun aesdec128kl(data: M128i, handle: ConstPtr): AesOutput

public expect fun aesenc256kl(data: M128i, handle: ConstPtr): AesOutput

public expect fun aesdec256kl(data: M128i, handle: ConstPtr): AesOutput

public expect fun aesencwide128kl(handle: ConstPtr, i0: M128i, i1: M128i, i2: M128i, i3: M128i, i4: M128i, i5: M128i, i6: M128i, i7: M128i): WideAesOutput

public expect fun aesdecwide128kl(handle: ConstPtr, i0: M128i, i1: M128i, i2: M128i, i3: M128i, i4: M128i, i5: M128i, i6: M128i, i7: M128i): WideAesOutput

public expect fun aesencwide256kl(handle: ConstPtr, i0: M128i, i1: M128i, i2: M128i, i3: M128i, i4: M128i, i5: M128i, i6: M128i, i7: M128i): WideAesOutput

public expect fun aesdecwide256kl(handle: ConstPtr, i0: M128i, i1: M128i, i2: M128i, i3: M128i, i4: M128i, i5: M128i, i6: M128i, i7: M128i): WideAesOutput

/**
 * Load internal wrapping key (IWKey). The 32-bit unsigned integer `control` specifies IWKey's KeySource
 * and whether backing up the key is permitted. IWKey's 256-bit encryption key is loaded from `keyLo`
 * and `keyHi`.
 *
 *  - `control[0]`: NoBackup bit. If set, the IWKey cannot be backed up.
 *  - `control[1: 4]`: KeySource bits. These bits specify the encoding method of the IWKey. The only
 *    allowed values are `0` (AES GCM SIV wrapping algorithm with the specified key) and `1` (AES GCM
 *    SIV wrapping algorithm with random keys enforced by hardware). After calling `_mm_loadiwkey` with
 *    KeySource set to `1`, software must check `ZF` to ensure that the key was loaded successfully.
 *    Using any other value may result in a General Protection Exception.
 *  - `control[5: 31]`: Reserved for future use, must be set to `0`.
 *
 * Note that setting the NoBackup bit and using the KeySource value `1` requires hardware support. These
 * permissions can be found by calling `__cpuid(0x19)` and checking the `ECX[0: 1]` bits. Failing to follow
 * these restrictions may result in a General Protection Exception.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadiwkey)
 */
public fun mmLoadiwkey(control: UInt, integrityKey: M128i, keyLo: M128i, keyHi: M128i) {
    loadiwkey(integrityKey, keyLo, keyHi, control)
}

/**
 * Wrap a 128-bit AES key into a 384-bit key handle and stores it in `handle`. Returns the `control`
 * parameter used to create the IWKey.
 *
 *  - `keyParams[0]`: If set, this key can only be used by the Kernel.
 *  - `keyParams[1]`: If set, this key can not be used to encrypt.
 *  - `keyParams[2]`: If set, this key can not be used to decrypt.
 *  - `keyParams[31: 3]`: Reserved for future use, must be set to `0`.
 *
 * Note that these restrictions need hardware support, and the supported restrictions can be found by
 * calling `__cpuid(0x19)` and checking the `EAX[0: 2]` bits. Failing to follow these restrictions may
 * result in a General Protection Exception.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_encodekey128_u32)
 */
public fun mmEncodekey128U32(keyParams: UInt, key: M128i, handle: MutPtr): UInt {
    val tupleStructVal = encodekey128(keyParams, key)
    val control = tupleStructVal.field0
    val key0 = tupleStructVal.field1
    val key1 = tupleStructVal.field2
    val key2 = tupleStructVal.field3
    writeUnaligned(handle, listOf(key0, key1, key2))
    return control
}

/**
 * Wrap a 256-bit AES key into a 512-bit key handle and stores it in `handle`. Returns the `control`
 * parameter used to create the IWKey.
 *
 *  - `keyParams[0]`: If set, this key can only be used by the Kernel.
 *  - `keyParams[1]`: If set, this key can not be used to encrypt.
 *  - `keyParams[2]`: If set, this key can not be used to decrypt.
 *  - `keyParams[31: 3]`: Reserved for future use, must be set to `0`.
 *
 * Note that these restrictions need hardware support, and the supported restrictions can be found by
 * calling `__cpuid(0x19)` and checking the `EAX[0: 2]` bits. Failing to follow these restrictions may
 * result in a General Protection Exception.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_encodekey256_u32)
 */
public fun mmEncodekey256U32(keyParams: UInt, keyLo: M128i, keyHi: M128i, handle: MutPtr): UInt {
    val tupleStructVal = encodekey256(keyParams, keyLo, keyHi)
    val control = tupleStructVal.field0
    val key0 = tupleStructVal.field1
    val key1 = tupleStructVal.field2
    val key2 = tupleStructVal.field3
    val key3 = tupleStructVal.field4
    writeUnaligned(handle, listOf(key0, key1, key2, key3))
    return control
}

/**
 * Encrypt 10 rounds of unsigned 8-bit integers in `input` using 128-bit AES key specified in the
 * 384-bit key handle `handle`. Store the resulting unsigned 8-bit integers into the corresponding
 * elements of `output`. Returns `0` if the operation was successful, and `1` if the operation failed
 * due to a handle violation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesenc128kl_u8)
 */
public fun mmAesenc128klU8(output: MutPtr, input: M128i, handle: ConstPtr): UByte {
    val output1 = output
    val tupleStructVal = aesenc128kl(input, handle)
    val status = tupleStructVal.field0
    val result = tupleStructVal.field1
    (UnresolvedTypeLayout<M128i>()).writeNative((output1).rawAddress, result)
    return status
}

/**
 * Decrypt 10 rounds of unsigned 8-bit integers in `input` using 128-bit AES key specified in the
 * 384-bit key handle `handle`. Store the resulting unsigned 8-bit integers into the corresponding
 * elements of `output`. Returns `0` if the operation was successful, and `1` if the operation failed
 * due to a handle violation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesdec128kl_u8)
 */
public fun mmAesdec128klU8(output: MutPtr, input: M128i, handle: ConstPtr): UByte {
    val output1 = output
    val tupleStructVal = aesdec128kl(input, handle)
    val status = tupleStructVal.field0
    val result = tupleStructVal.field1
    (UnresolvedTypeLayout<M128i>()).writeNative((output1).rawAddress, result)
    return status
}

/**
 * Encrypt 14 rounds of unsigned 8-bit integers in `input` using 256-bit AES key specified in the
 * 512-bit key handle `handle`. Store the resulting unsigned 8-bit integers into the corresponding
 * elements of `output`. Returns `0` if the operation was successful, and `1` if the operation failed
 * due to a handle violation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesenc256kl_u8)
 */
public fun mmAesenc256klU8(output: MutPtr, input: M128i, handle: ConstPtr): UByte {
    val output1 = output
    val tupleStructVal = aesenc256kl(input, handle)
    val status = tupleStructVal.field0
    val result = tupleStructVal.field1
    (UnresolvedTypeLayout<M128i>()).writeNative((output1).rawAddress, result)
    return status
}

/**
 * Decrypt 14 rounds of unsigned 8-bit integers in `input` using 256-bit AES key specified in the
 * 512-bit key handle `handle`. Store the resulting unsigned 8-bit integers into the corresponding
 * elements of `output`. Returns `0` if the operation was successful, and `1` if the operation failed
 * due to a handle violation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesdec256kl_u8)
 */
public fun mmAesdec256klU8(output: MutPtr, input: M128i, handle: ConstPtr): UByte {
    val output1 = output
    val tupleStructVal = aesdec256kl(input, handle)
    val status = tupleStructVal.field0
    val result = tupleStructVal.field1
    (UnresolvedTypeLayout<M128i>()).writeNative((output1).rawAddress, result)
    return status
}

/**
 * Encrypt 10 rounds of 8 groups of unsigned 8-bit integers in `input` using 128-bit AES key specified
 * in the 384-bit key handle `handle`. Store the resulting unsigned 8-bit integers into the corresponding
 * elements of `output`. Returns `0` if the operation was successful, and `1` if the operation failed
 * due to a handle violation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesencwide128kl_u8)
 */
public fun mmAesencwide128klU8(output: MutPtr, input: ConstPtr, handle: ConstPtr): UByte {
    val input1 = sliceFromRawParts(input, 8)
    val tupleStructVal = aesencwide128kl(handle, input1[0], input1[1], input1[2], input1[3], input1[4], input1[5], input1[6], input1[7])
    val status = tupleStructVal.field0
    val out0 = tupleStructVal.field1
    val out1 = tupleStructVal.field2
    val out2 = tupleStructVal.field3
    val out3 = tupleStructVal.field4
    val out4 = tupleStructVal.field5
    val out5 = tupleStructVal.field6
    val out6 = tupleStructVal.field7
    val out7 = tupleStructVal.field8
    output = listOf(out0, out1, out2, out3, out4, out5, out6, out7)
    return status
}

/**
 * Decrypt 10 rounds of 8 groups of unsigned 8-bit integers in `input` using 128-bit AES key specified
 * in the 384-bit key handle `handle`. Store the resulting unsigned 8-bit integers into the corresponding
 * elements of `output`. Returns `0` if the operation was successful, and `1` if the operation failed
 * due to a handle violation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesdecwide128kl_u8)
 */
public fun mmAesdecwide128klU8(output: MutPtr, input: ConstPtr, handle: ConstPtr): UByte {
    val input1 = sliceFromRawParts(input, 8)
    val tupleStructVal = aesdecwide128kl(handle, input1[0], input1[1], input1[2], input1[3], input1[4], input1[5], input1[6], input1[7])
    val status = tupleStructVal.field0
    val out0 = tupleStructVal.field1
    val out1 = tupleStructVal.field2
    val out2 = tupleStructVal.field3
    val out3 = tupleStructVal.field4
    val out4 = tupleStructVal.field5
    val out5 = tupleStructVal.field6
    val out6 = tupleStructVal.field7
    val out7 = tupleStructVal.field8
    output = listOf(out0, out1, out2, out3, out4, out5, out6, out7)
    return status
}

/**
 * Encrypt 14 rounds of 8 groups of unsigned 8-bit integers in `input` using 256-bit AES key specified
 * in the 512-bit key handle `handle`. Store the resulting unsigned 8-bit integers into the corresponding
 * elements of `output`. Returns `0` if the operation was successful, and `1` if the operation failed
 * due to a handle violation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesencwide256kl_u8)
 */
public fun mmAesencwide256klU8(output: MutPtr, input: ConstPtr, handle: ConstPtr): UByte {
    val input1 = sliceFromRawParts(input, 8)
    val tupleStructVal = aesencwide256kl(handle, input1[0], input1[1], input1[2], input1[3], input1[4], input1[5], input1[6], input1[7])
    val status = tupleStructVal.field0
    val out0 = tupleStructVal.field1
    val out1 = tupleStructVal.field2
    val out2 = tupleStructVal.field3
    val out3 = tupleStructVal.field4
    val out4 = tupleStructVal.field5
    val out5 = tupleStructVal.field6
    val out6 = tupleStructVal.field7
    val out7 = tupleStructVal.field8
    output = listOf(out0, out1, out2, out3, out4, out5, out6, out7)
    return status
}

/**
 * Decrypt 14 rounds of 8 groups of unsigned 8-bit integers in `input` using 256-bit AES key specified
 * in the 512-bit key handle `handle`. Store the resulting unsigned 8-bit integers into the corresponding
 * elements of `output`. Returns `0` if the operation was successful, and `1` if the operation failed
 * due to a handle violation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesdecwide256kl_u8)
 */
public fun mmAesdecwide256klU8(output: MutPtr, input: ConstPtr, handle: ConstPtr): UByte {
    val input1 = sliceFromRawParts(input, 8)
    val tupleStructVal = aesdecwide256kl(handle, input1[0], input1[1], input1[2], input1[3], input1[4], input1[5], input1[6], input1[7])
    val status = tupleStructVal.field0
    val out0 = tupleStructVal.field1
    val out1 = tupleStructVal.field2
    val out2 = tupleStructVal.field3
    val out3 = tupleStructVal.field4
    val out4 = tupleStructVal.field5
    val out5 = tupleStructVal.field6
    val out6 = tupleStructVal.field7
    val out7 = tupleStructVal.field8
    output = listOf(out0, out1, out2, out3, out4, out5, out6, out7)
    return status
}

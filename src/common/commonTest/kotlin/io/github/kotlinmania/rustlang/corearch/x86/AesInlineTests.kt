// port-lint: tests x86/aes.rs
package io.github.kotlinmania.rustlang.corearch.x86

// AES New Instructions (AES-NI)
//
// The intrinsics here correspond to those in the `wmmintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public fun testMmAesdecSi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc664949.aspx.
    val a = mmSetEpi64x(0x0123456789abcdefL, 0x8899aabbccddeeffuL.toLong())
    val k = mmSetEpi64x(0x1133557799bbddffL, 0x0022446688aacceeL)
    val e = mmSetEpi64x(0x044e4f5176fec48fL, 0xb57ecfa381da39eeuL.toLong())
    val r = mmAesdecSi128(a, k)
    assertEqM128i(r, e)
}

public fun testMmAesdeclastSi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc714178.aspx.
    val a = mmSetEpi64x(0x0123456789abcdefL, 0x8899aabbccddeeffuL.toLong())
    val k = mmSetEpi64x(0x1133557799bbddffL, 0x0022446688aacceeL)
    val e = mmSetEpi64x(0x36cad57d9072bf9eL, 0xf210dd981fa4a493uL.toLong())
    val r = mmAesdeclastSi128(a, k)
    assertEqM128i(r, e)
}

public fun testMmAesencSi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc664810.aspx.
    val a = mmSetEpi64x(0x0123456789abcdefL, 0x8899aabbccddeeffuL.toLong())
    val k = mmSetEpi64x(0x1133557799bbddffL, 0x0022446688aacceeL)
    val e = mmSetEpi64x(0x16ab0e57dfc442edL, 0x28e4ee1884504333L)
    val r = mmAesencSi128(a, k)
    assertEqM128i(r, e)
}

public fun testMmAesenclastSi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc714136.aspx.
    val a = mmSetEpi64x(0x0123456789abcdefL, 0x8899aabbccddeeffuL.toLong())
    val k = mmSetEpi64x(0x1133557799bbddffL, 0x0022446688aacceeL)
    val e = mmSetEpi64x(0xb6dd7df25d7ab320uL.toLong(), 0x4b04f98cf4c860f8L)
    val r = mmAesenclastSi128(a, k)
    assertEqM128i(r, e)
}

public fun testMmAesimcSi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc714195.aspx.
    val a = mmSetEpi64x(0x0123456789abcdefL, 0x8899aabbccddeeffuL.toLong())
    val e = mmSetEpi64x(0xc66c82284ee40aa0uL.toLong(), 0x6633441122770055L)
    val r = mmAesimcSi128(a)
    assertEqM128i(r, e)
}

public fun testMmAeskeygenassistSi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc714138.aspx.
    val a = mmSetEpi64x(0x0123456789abcdefL, 0x8899aabbccddeeffuL.toLong())
    val e = mmSetEpi64x(0x857c266b7c266e85uL.toLong(), 0xeac4eea9c4eeaceauL.toLong())
    val r = mmAeskeygenassistSi128(a, 5)
    assertEqM128i(r, e)
}

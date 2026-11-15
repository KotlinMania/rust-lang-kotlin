// port-lint: source math/exp2.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libm.math.exp2

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.math.arch.i586.expall.x87Exp2
import io.github.kotlinmania.rustlang.math.scalbn.scalbn

public val TBLSIZE: Int = 256

public var TBL: ULongArray = ulongArrayOf(0x3fe6a09e667f3d5duL, 0x3d39880000000000uL, 0x3fe6b052fa751744uL, 0x3cd8000000000000uL, 0x3fe6c012750bd9feuL, 0xbd28780000000000uL, 0x3fe6cfdcddd476bfuL, 0x3d1ec00000000000uL, 0x3fe6dfb23c651a29uL, 0xbcd8000000000000uL, 0x3fe6ef9298593ae3uL, 0xbcbc000000000000uL, 0x3fe6ff7df9519386uL, 0xbd2fd80000000000uL, 0x3fe70f7466f42da3uL, 0xbd2c880000000000uL, 0x3fe71f75e8ec5fc3uL, 0x3d13c00000000000uL, 0x3fe72f8286eacf05uL, 0xbd38300000000000uL, 0x3fe73f9a48a58152uL, 0xbd00c00000000000uL, 0x3fe74fbd35d7ccfcuL, 0x3d2f880000000000uL, 0x3fe75feb564267f1uL, 0x3d03e00000000000uL, 0x3fe77024b1ab6d48uL, 0xbd27d00000000000uL, 0x3fe780694fde5d38uL, 0xbcdd000000000000uL, 0x3fe790b938ac1d00uL, 0x3ce3000000000000uL, 0x3fe7a11473eb0178uL, 0xbced000000000000uL, 0x3fe7b17b0976d060uL, 0x3d20400000000000uL, 0x3fe7c1ed0130c133uL, 0x3ca0000000000000uL, 0x3fe7d26a62ff8636uL, 0xbd26900000000000uL, 0x3fe7e2f336cf4e3buL, 0xbd02e00000000000uL, 0x3fe7f3878491c3e8uL, 0xbd24580000000000uL, 0x3fe80427543e1b4euL, 0x3d33000000000000uL, 0x3fe814d2add1071auL, 0x3d0f000000000000uL, 0x3fe82589994ccd7euL, 0xbd21c00000000000uL, 0x3fe8364c1eb942d0uL, 0x3d29d00000000000uL, 0x3fe8471a4623cab5uL, 0x3d47100000000000uL, 0x3fe857f4179f5bbcuL, 0x3d22600000000000uL, 0x3fe868d99b4491afuL, 0xbd32c40000000000uL, 0x3fe879cad931a395uL, 0xbd23000000000000uL, 0x3fe88ac7d98a65b8uL, 0xbd2a800000000000uL, 0x3fe89bd0a4785800uL, 0xbced000000000000uL, 0x3fe8ace5422aa223uL, 0x3d33280000000000uL, 0x3fe8be05bad619fauL, 0x3d42b40000000000uL, 0x3fe8cf3216b54383uL, 0xbd2ed00000000000uL, 0x3fe8e06a5e08664cuL, 0xbd20500000000000uL, 0x3fe8f1ae99157807uL, 0x3d28280000000000uL, 0x3fe902fed0282c0euL, 0xbd1cb00000000000uL, 0x3fe9145b0b91ff96uL, 0xbd05e00000000000uL, 0x3fe925c353aa2ff9uL, 0x3cf5400000000000uL, 0x3fe93737b0cdc64auL, 0x3d17200000000000uL, 0x3fe948b82b5f98aeuL, 0xbd09000000000000uL, 0x3fe95a44cbc852cbuL, 0x3d25680000000000uL, 0x3fe96bdd9a766f21uL, 0xbd36d00000000000uL, 0x3fe97d829fde4e2auL, 0xbd01000000000000uL, 0x3fe98f33e47a23a3uL, 0x3d2d000000000000uL, 0x3fe9a0f170ca0604uL, 0xbd38a40000000000uL, 0x3fe9b2bb4d53ff89uL, 0x3d355c0000000000uL, 0x3fe9c49182a3f15buL, 0x3d26b80000000000uL, 0x3fe9d674194bb8c5uL, 0xbcec000000000000uL, 0x3fe9e86319e3238euL, 0x3d17d00000000000uL, 0x3fe9fa5e8d07f302uL, 0x3d16400000000000uL, 0x3fea0c667b5de54duL, 0xbcf5000000000000uL, 0x3fea1e7aed8eb8f6uL, 0x3d09e00000000000uL, 0x3fea309bec4a2e27uL, 0x3d2ad80000000000uL, 0x3fea42c980460a5duL, 0xbd1af00000000000uL, 0x3fea5503b23e259buL, 0x3d0b600000000000uL, 0x3fea674a8af46213uL, 0x3d38880000000000uL, 0x3fea799e1330b3a7uL, 0x3d11200000000000uL, 0x3fea8bfe53c12e8duL, 0x3d06c00000000000uL, 0x3fea9e6b5579fcd2uL, 0xbd29b80000000000uL, 0x3feab0e521356fb8uL, 0x3d2b700000000000uL, 0x3feac36bbfd3f381uL, 0x3cd9000000000000uL, 0x3fead5ff3a3c2780uL, 0x3ce4000000000000uL, 0x3feae89f995ad2a3uL, 0xbd2c900000000000uL, 0x3feafb4ce622f367uL, 0x3d16500000000000uL, 0x3feb0e07298db790uL, 0x3d2fd40000000000uL, 0x3feb20ce6c9a89a9uL, 0x3d12700000000000uL, 0x3feb33a2b84f1a4buL, 0x3d4d470000000000uL, 0x3feb468415b747e7uL, 0xbd38380000000000uL, 0x3feb59728de5593auL, 0x3c98000000000000uL, 0x3feb6c6e29f1c56auL, 0x3d0ad00000000000uL, 0x3feb7f76f2fb5e50uL, 0x3cde800000000000uL, 0x3feb928cf22749b2uL, 0xbd04c00000000000uL, 0x3feba5b030a10603uL, 0xbd0d700000000000uL, 0x3febb8e0b79a6f66uL, 0x3d0d900000000000uL, 0x3febcc1e904bc1ffuL, 0x3d02a00000000000uL, 0x3febdf69c3f3a16fuL, 0xbd1f780000000000uL, 0x3febf2c25bd71db8uL, 0xbd10a00000000000uL, 0x3fec06286141b2e9uL, 0xbd11400000000000uL, 0x3fec199bdd8552e0uL, 0x3d0be00000000000uL, 0x3fec2d1cd9fa64eeuL, 0xbd09400000000000uL, 0x3fec40ab5fffd02fuL, 0xbd0ed00000000000uL, 0x3fec544778fafd15uL, 0x3d39660000000000uL, 0x3fec67f12e57d0cbuL, 0xbd1a100000000000uL, 0x3fec7ba88988c1b6uL, 0xbd58458000000000uL, 0x3fec8f6d9406e733uL, 0xbd1a480000000000uL, 0x3feca3405751c4dfuL, 0x3ccb000000000000uL, 0x3fecb720dcef9094uL, 0x3d01400000000000uL, 0x3feccb0f2e6d1689uL, 0x3cf0200000000000uL, 0x3fecdf0b555dc412uL, 0x3cf3600000000000uL, 0x3fecf3155b5bab3buL, 0xbd06900000000000uL, 0x3fed072d4a0789bcuL, 0x3d09a00000000000uL, 0x3fed1b532b08c8fauL, 0xbd15e00000000000uL, 0x3fed2f87080d8a85uL, 0x3d1d280000000000uL, 0x3fed43c8eacaa203uL, 0x3d01a00000000000uL, 0x3fed5818dcfba491uL, 0x3cdf000000000000uL, 0x3fed6c76e862e6a1uL, 0xbd03a00000000000uL, 0x3fed80e316c9834euL, 0xbd0cd80000000000uL, 0x3fed955d71ff6090uL, 0x3cf4c00000000000uL, 0x3feda9e603db32aeuL, 0x3cff900000000000uL, 0x3fedbe7cd63a8325uL, 0x3ce9800000000000uL, 0x3fedd321f301b445uL, 0xbcf5200000000000uL, 0x3fede7d5641c05bfuL, 0xbd1d700000000000uL, 0x3fedfc97337b9aecuL, 0xbd16140000000000uL, 0x3fee11676b197d5euL, 0x3d0b480000000000uL, 0x3fee264614f5a3e7uL, 0x3d40ce0000000000uL, 0x3fee3b333b16ee5cuL, 0x3d0c680000000000uL, 0x3fee502ee78b3fb4uL, 0xbd09300000000000uL, 0x3fee653924676d68uL, 0xbce5000000000000uL, 0x3fee7a51fbc74c44uL, 0xbd07f80000000000uL, 0x3fee8f7977cdb726uL, 0xbcf3700000000000uL, 0x3feea4afa2a490e8uL, 0x3ce5d00000000000uL, 0x3feeb9f4867ccae4uL, 0x3d161a0000000000uL, 0x3feecf482d8e680duL, 0x3cf5500000000000uL, 0x3feee4aaa2188514uL, 0x3cc6400000000000uL, 0x3feefa1bee615a13uL, 0xbcee800000000000uL, 0x3fef0f9c1cb64106uL, 0xbcfa880000000000uL, 0x3fef252b376bb963uL, 0xbd2c900000000000uL, 0x3fef3ac948dd7275uL, 0x3caa000000000000uL, 0x3fef50765b6e4524uL, 0xbcf4f00000000000uL, 0x3fef6632798844fduL, 0x3cca800000000000uL, 0x3fef7bfdad9cbe38uL, 0x3cfabc0000000000uL, 0x3fef91d802243c82uL, 0xbcd4600000000000uL, 0x3fefa7c1819e908euL, 0xbd0b0c0000000000uL, 0x3fefbdba3692d511uL, 0xbcc0e00000000000uL, 0x3fefd3c22b8f7194uL, 0xbd10de8000000000uL, 0x3fefe9d96b2a23eeuL, 0x3cee430000000000uL, 0x3ff0000000000000uL, 0x0uL, 0x3ff00b1afa5abcbeuL, 0xbcb3400000000000uL, 0x3ff0163da9fb3303uL, 0xbd12170000000000uL, 0x3ff02168143b0282uL, 0x3cba400000000000uL, 0x3ff02c9a3e77806cuL, 0x3cef980000000000uL, 0x3ff037d42e11bbcauL, 0xbcc7400000000000uL, 0x3ff04315e86e7f89uL, 0x3cd8300000000000uL, 0x3ff04e5f72f65467uL, 0xbd1a3f0000000000uL, 0x3ff059b0d315855auL, 0xbd02840000000000uL, 0x3ff0650a0e3c1f95uL, 0x3cf1600000000000uL, 0x3ff0706b29ddf71auL, 0x3d15240000000000uL, 0x3ff07bd42b72a82duL, 0xbce9a00000000000uL, 0x3ff0874518759bd0uL, 0x3ce6400000000000uL, 0x3ff092bdf66607c8uL, 0xbd00780000000000uL, 0x3ff09e3ecac6f383uL, 0xbc98000000000000uL, 0x3ff0a9c79b1f3930uL, 0x3cffa00000000000uL, 0x3ff0b5586cf988fcuL, 0xbcfac80000000000uL, 0x3ff0c0f145e46c8auL, 0x3cd9c00000000000uL, 0x3ff0cc922b724816uL, 0x3d05200000000000uL, 0x3ff0d83b23395dd8uL, 0xbcfad00000000000uL, 0x3ff0e3ec32d3d1f3uL, 0x3d1bac0000000000uL, 0x3ff0efa55fdfa9a6uL, 0xbd04e80000000000uL, 0x3ff0fb66affed2f0uL, 0xbd0d300000000000uL, 0x3ff1073028d7234buL, 0x3cf1500000000000uL, 0x3ff11301d0125b5buL, 0x3cec000000000000uL, 0x3ff11edbab5e2af9uL, 0x3d16bc0000000000uL, 0x3ff12abdc06c31d5uL, 0x3ce8400000000000uL, 0x3ff136a814f2047duL, 0xbd0ed00000000000uL, 0x3ff1429aaea92de9uL, 0x3ce8e00000000000uL, 0x3ff14e95934f3138uL, 0x3ceb400000000000uL, 0x3ff15a98c8a58e71uL, 0x3d05300000000000uL, 0x3ff166a45471c3dfuL, 0x3d03380000000000uL, 0x3ff172b83c7d5211uL, 0x3d28d40000000000uL, 0x3ff17ed48695bb9fuL, 0xbd05d00000000000uL, 0x3ff18af9388c8d93uL, 0xbd1c880000000000uL, 0x3ff1972658375d66uL, 0x3d11f00000000000uL, 0x3ff1a35beb6fcba7uL, 0x3d10480000000000uL, 0x3ff1af99f81387e3uL, 0xbd47390000000000uL, 0x3ff1bbe084045d54uL, 0x3d24e40000000000uL, 0x3ff1c82f95281c43uL, 0xbd0a200000000000uL, 0x3ff1d4873168b9b2uL, 0x3ce3800000000000uL, 0x3ff1e0e75eb44031uL, 0x3ceac00000000000uL, 0x3ff1ed5022fcd938uL, 0x3d01900000000000uL, 0x3ff1f9c18438cdf7uL, 0xbd1b780000000000uL, 0x3ff2063b88628d8fuL, 0x3d2d940000000000uL, 0x3ff212be3578a81euL, 0x3cd8000000000000uL, 0x3ff21f49917ddd41uL, 0x3d2b340000000000uL, 0x3ff22bdda2791323uL, 0x3d19f80000000000uL, 0x3ff2387a6e7561e7uL, 0xbd19c80000000000uL, 0x3ff2451ffb821427uL, 0x3d02300000000000uL, 0x3ff251ce4fb2a602uL, 0xbd13480000000000uL, 0x3ff25e85711eceb0uL, 0x3d12700000000000uL, 0x3ff26b4565e27d16uL, 0x3d11d00000000000uL, 0x3ff2780e341de00fuL, 0x3d31ee0000000000uL, 0x3ff284dfe1f5633euL, 0xbd14c00000000000uL, 0x3ff291ba7591bb30uL, 0xbd13d80000000000uL, 0x3ff29e9df51fdf09uL, 0x3d08b00000000000uL, 0x3ff2ab8a66d10e9buL, 0xbd227c0000000000uL, 0x3ff2b87fd0dada3auL, 0x3d2a340000000000uL, 0x3ff2c57e39771af9uL, 0xbd10800000000000uL, 0x3ff2d285a6e402d9uL, 0xbd0ed00000000000uL, 0x3ff2df961f641579uL, 0xbcf4200000000000uL, 0x3ff2ecafa93e2ecfuL, 0xbd24980000000000uL, 0x3ff2f9d24abd8822uL, 0xbd16300000000000uL, 0x3ff306fe0a31b625uL, 0xbd32360000000000uL, 0x3ff31432edeea50buL, 0xbd70df8000000000uL, 0x3ff32170fc4cd7b8uL, 0xbd22480000000000uL, 0x3ff32eb83ba8e9a2uL, 0xbd25980000000000uL, 0x3ff33c08b2641766uL, 0x3d1ed00000000000uL, 0x3ff3496266e3fa27uL, 0xbcdc000000000000uL, 0x3ff356c55f929f0fuL, 0xbd30d80000000000uL, 0x3ff36431a2de88b9uL, 0x3d22c80000000000uL, 0x3ff371a7373aaa39uL, 0x3d20600000000000uL, 0x3ff37f26231e74feuL, 0xbd16600000000000uL, 0x3ff38cae6d05d838uL, 0xbd0ae00000000000uL, 0x3ff39a401b713ec3uL, 0xbd44720000000000uL, 0x3ff3a7db34e5a020uL, 0x3d08200000000000uL, 0x3ff3b57fbfec6e95uL, 0x3d3e800000000000uL, 0x3ff3c32dc313a8f2uL, 0x3cef800000000000uL, 0x3ff3d0e544ede122uL, 0xbd17a00000000000uL, 0x3ff3dea64c1234bbuL, 0x3d26300000000000uL, 0x3ff3ec70df1c4eccuL, 0xbd48a60000000000uL, 0x3ff3fa4504ac7e8cuL, 0xbd3cdc0000000000uL, 0x3ff40822c367a0bbuL, 0x3d25b80000000000uL, 0x3ff4160a21f72e95uL, 0x3d1ec00000000000uL, 0x3ff423fb27094646uL, 0xbd13600000000000uL, 0x3ff431f5d950a920uL, 0x3d23980000000000uL, 0x3ff43ffa3f84b9ebuL, 0x3cfa000000000000uL, 0x3ff44e0860618919uL, 0xbcf6c00000000000uL, 0x3ff45c2042a7d201uL, 0xbd0bc00000000000uL, 0x3ff46a41ed1d0016uL, 0xbd12800000000000uL, 0x3ff4786d668b3326uL, 0x3d30e00000000000uL, 0x3ff486a2b5c13c00uL, 0xbd2d400000000000uL, 0x3ff494e1e192af04uL, 0x3d0c200000000000uL, 0x3ff4a32af0d7d372uL, 0xbd1e500000000000uL, 0x3ff4b17dea6db801uL, 0x3d07800000000000uL, 0x3ff4bfdad53629e1uL, 0xbd13800000000000uL, 0x3ff4ce41b817c132uL, 0x3d00800000000000uL, 0x3ff4dcb299fddddbuL, 0x3d2c700000000000uL, 0x3ff4eb2d81d8ab96uL, 0xbd1ce00000000000uL, 0x3ff4f9b2769d2d02uL, 0x3d19200000000000uL, 0x3ff508417f4531c1uL, 0xbd08c00000000000uL, 0x3ff516daa2cf662auL, 0xbcfa000000000000uL, 0x3ff5257de83f51eauL, 0x3d4a080000000000uL, 0x3ff5342b569d4edauL, 0xbd26d80000000000uL, 0x3ff542e2f4f6ac1auL, 0xbd32440000000000uL, 0x3ff551a4ca5d94dbuL, 0x3d483c0000000000uL, 0x3ff56070dde9116buL, 0x3d24b00000000000uL, 0x3ff56f4736b529deuL, 0x3d415a0000000000uL, 0x3ff57e27dbe2c40euL, 0xbd29e00000000000uL, 0x3ff58d12d497c76fuL, 0xbd23080000000000uL, 0x3ff59c0827ff0b4cuL, 0x3d4dec0000000000uL, 0x3ff5ab07dd485427uL, 0xbcc4000000000000uL, 0x3ff5ba11fba87af4uL, 0x3d30080000000000uL, 0x3ff5c9268a59460buL, 0xbd26c80000000000uL, 0x3ff5d84590998e3fuL, 0x3d469a0000000000uL, 0x3ff5e76f15ad20e1uL, 0xbd1b400000000000uL, 0x3ff5f6a320dcebcauL, 0x3d17700000000000uL, 0x3ff605e1b976dcb8uL, 0x3d26f80000000000uL, 0x3ff6152ae6cdf715uL, 0x3d01000000000000uL, 0x3ff6247eb03a5531uL, 0xbd15d00000000000uL, 0x3ff633dd1d1929b5uL, 0xbd12d00000000000uL, 0x3ff6434634ccc313uL, 0xbcea800000000000uL, 0x3ff652b9febc8efauL, 0xbd28600000000000uL, 0x3ff6623882553397uL, 0x3d71fe0000000000uL, 0x3ff671c1c708328euL, 0xbd37200000000000uL, 0x3ff68155d44ca97euL, 0x3ce6800000000000uL, 0x3ff690f4b19e9471uL, 0xbd29780000000000uL)

// exp2(x): compute the base 2 exponential of x
//
// Accuracy: Peak error < 0.503 ulp for normalized results.
//
// Method: (accurate tables)
//
// Reduce x:
// x = k + y, for integer k and |y| <= 1/2.
// Thus we have exp2(x) = 2**k * exp2(y).
//
// Reduce y:
// y = i/TBLSIZE + z - eps[i] for integer i near y * TBLSIZE.
// Thus we have exp2(y) = exp2(i/TBLSIZE) * exp2(z - eps[i]),
// with |z - eps[i]| <= 2**-9 + 2**-39 for the table used.
//
// We compute exp2(i/TBLSIZE) via table lookup and exp2(z - eps[i]) via
// a degree-5 minimax polynomial with maximum error under 1.3 * 2**-61.
// The values in exp2t[] and eps[] are chosen such that
// exp2t[i] = exp2(i/TBLSIZE + eps[i]), and eps[i] is a small offset such
// that exp2t[i] is accurate to 2**-64.
//
// Note that the range of i is +-TBLSIZE/2, so we actually index the tables
// by i0 = i + TBLSIZE/2.  For cache efficiency, exp2t[] and eps[] are
// virtual tables, interleaved in the real table tbl[].
//
// This method is due to Gal, with many details due to Gal and Bachelis:
//
// Gal, S. and Bachelis, B.  An Accurate Elementary Mathematical Library
// for the IEEE Floating Point Standard.  TOMS 17(1), 26-46 (1991).

/**
 * Exponential, base 2 (f64)
 *
 * Calculate `2 ^ x`, that is, 2 raised to the power `x`.
 */
public fun exp2(x: Double): Double {
    var x1 = x
    if (true) {
        return x87Exp2(x1)
    }
    val redux = Double.fromBits(0x4338000000000000uL.toLong()) / TBLSIZE.toDouble()
    val p1 = Double.fromBits(0x3fe62e42fefa39efuL.toLong())
    val p2 = Double.fromBits(0x3fcebfbdff82c575uL.toLong())
    val p3 = Double.fromBits(0x3fac6b08d704a0a6uL.toLong())
    val p4 = Double.fromBits(0x3f83b2ab88f70400uL.toLong())
    val p5 = Double.fromBits(0x3f55d88003875c74uL.toLong())
    // doubleT r, t, z;
    // uint32T ix, i0;
    // union {double f; uint64T i;} u = {x};
    // union {uint32T u; int32T i;} k;
    val x1p1023 = Double.fromBits(0x7fe0000000000000uL.toLong())
    val x1p52 = Double.fromBits(0x4330000000000000uL.toLong())
    val v0x1p149 = Double.fromBits(0xb6a0000000000000uL.toLong())
    // Filter out exceptional cases.
    val ui = x1.toRawBits().toULong()
    val ix = (ui shr 32) and 0x7fffffff
    if (ix >= 0x408ff000) {
        // |x| >= 1022 or nan
        if (ix >= 0x40900000 && ui shr 63 == 0) {
            // x >= 1024 or nan
            // overflow
            x1 *= x1p1023
            return x1
        }
        if (ix >= 0x7ff00000) {
            // -inf or -nan
            return -1.0 / x1
        }
        if (ui shr 63 != 0) {
            // x <= -1022
            // underflow
            if (x1 <= -1075.0 || x1 - x1p52 + x1p52 != x1) {
                readVolatile((v0x1p149 / x1).toFloat(), typeLayoutT = PrimitiveLayouts.F32)
            }
            if (x1 <= -1075.0) {
                return 0.0
            }
        }
    } else if (ix < 0x3c900000) {
        // |x| < 0x1p-54
        return 1.0 + x1
    }
    // Reduce x, computing z, i0, and k.
    val ui1 = (x1 + redux).toRawBits().toULong()
    var i0 = ui1.toUInt()
    i0 = (i0 + ((TBLSIZE.toUInt() / 2u).toUInt()))
    val ku = i0 / TBLSIZE.toUInt() * TBLSIZE.toUInt()
    val ki = (ku.toInt()) / TBLSIZE
    i0 %= TBLSIZE.toUInt()
    val uf = Double.fromBits(ui1.toLong()) - redux
    var z = x1 - uf
    // Compute r = exp2(y) = exp2t[i0] * p(z - eps[i]).
    val t = Double.fromBits((TBL.getUnchecked(2 * i0.toInt())).toLong())
    // exp2t[i0]
    z -= Double.fromBits((TBL.getUnchecked(2 * i0.toInt() + 1)).toLong())
    // eps[i0]
    val r = t + t * z * (p1 + z * (p2 + z * (p3 + z * (p4 + z * p5))))
    return scalbn(r, ki)
}

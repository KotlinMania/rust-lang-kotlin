// port-lint: source detect/arch/riscv.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.checkFor

// Run-time feature detection on RISC-V.

/**
 * Each variant denotes a position in a bitset for a particular feature.
 *
 * PLEASE: do not use this, it is an implementation detail subject
 * to change.
 */
public sealed class Feature {
    /**
     * RV32I Base Integer Instruction Set
     */
    public data object Rv32i : Feature()
    /**
     * RV32E Base Integer Instruction Set
     */
    public data object Rv32e : Feature()
    /**
     * RV64I Base Integer Instruction Set
     */
    public data object Rv64i : Feature()
    /**
     * RV128I Base Integer Instruction Set
     */
    public data object Rv128i : Feature()
    /**
     * Has reasonably performant unaligned scalar
     */
    public data object UnalignedScalarMem : Feature()
    /**
     * Has reasonably performant unaligned vector
     */
    public data object UnalignedVectorMem : Feature()
    /**
     * "Zicsr" Extension for Control and Status Register (CSR) Instructions
     */
    public data object Zicsr : Feature()
    /**
     * "Zicntr" Extension for Base Counters and Timers
     */
    public data object Zicntr : Feature()
    /**
     * "Zihpm" Extension for Hardware Performance Counters
     */
    public data object Zihpm : Feature()
    /**
     * "Zifencei" Extension for Instruction-Fetch Fence
     */
    public data object Zifencei : Feature()
    /**
     * "Zihintntl" Extension for Non-Temporal Locality Hints
     */
    public data object Zihintntl : Feature()
    /**
     * "Zihintpause" Extension for Pause Hint
     */
    public data object Zihintpause : Feature()
    /**
     * "Zimop" Extension for May-Be-Operations
     */
    public data object Zimop : Feature()
    /**
     * "Zicbom" Extension for Cache-Block Management Instructions
     */
    public data object Zicbom : Feature()
    /**
     * "Zicboz" Extension for Cache-Block Zero Instruction
     */
    public data object Zicboz : Feature()
    /**
     * "Zicond" Extension for Integer Conditional Operations
     */
    public data object Zicond : Feature()
    /**
     * "M" Extension for Integer Multiplication and Division
     */
    public data object M : Feature()
    /**
     * "Zmmul" Extension for Integer Multiplication
     */
    public data object Zmmul : Feature()
    /**
     * "A" Extension for Atomic Instructions
     */
    public data object A : Feature()
    /**
     * "Zalrsc" Extension for Load-Reserved/Store-Conditional Instructions
     */
    public data object Zalrsc : Feature()
    /**
     * "Zaamo" Extension for Atomic Memory Operations
     */
    public data object Zaamo : Feature()
    /**
     * "Zawrs" Extension for Wait-on-Reservation-Set Instructions
     */
    public data object Zawrs : Feature()
    /**
     * "Zabha" Extension for Byte and Halfword Atomic Memory Operations
     */
    public data object Zabha : Feature()
    /**
     * "Zacas" Extension for Atomic Compare-and-Swap (CAS) Instructions
     */
    public data object Zacas : Feature()
    /**
     * "Zam" Extension for Misaligned Atomics
     */
    public data object Zam : Feature()
    /**
     * "Ztso" Extension for Total Store Ordering
     */
    public data object Ztso : Feature()
    /**
     * "F" Extension for Single-Precision Floating-Point
     */
    public data object F : Feature()
    /**
     * "D" Extension for Double-Precision Floating-Point
     */
    public data object D : Feature()
    /**
     * "Q" Extension for Quad-Precision Floating-Point
     */
    public data object Q : Feature()
    /**
     * "Zfh" Extension for Half-Precision Floating-Point
     */
    public data object Zfh : Feature()
    /**
     * "Zfhmin" Extension for Minimal Half-Precision Floating-Point
     */
    public data object Zfhmin : Feature()
    /**
     * "Zfa" Extension for Additional Floating-Point Instructions
     */
    public data object Zfa : Feature()
    /**
     * "Zfbfmin" Extension for Scalar BF16 Converts
     */
    public data object Zfbfmin : Feature()
    /**
     * "Zfinx" Extension for Single-Precision Floating-Point in Integer Registers
     */
    public data object Zfinx : Feature()
    /**
     * "Zdinx" Extension for Double-Precision Floating-Point in Integer Registers
     */
    public data object Zdinx : Feature()
    /**
     * "Zhinx" Extension for Half-Precision Floating-Point in Integer Registers
     */
    public data object Zhinx : Feature()
    /**
     * "Zhinxmin" Extension for Minimal Half-Precision Floating-Point in Integer Registers
     */
    public data object Zhinxmin : Feature()
    /**
     * "C" Extension for Compressed Instructions
     */
    public data object C : Feature()
    /**
     * "Zca" Compressed Instructions excluding Floating-Point Loads/Stores
     */
    public data object Zca : Feature()
    /**
     * "Zcf" Compressed Instructions for Single-Precision Floating-Point Loads/Stores on RV32
     */
    public data object Zcf : Feature()
    /**
     * "Zcd" Compressed Instructions for Double-Precision Floating-Point Loads/Stores
     */
    public data object Zcd : Feature()
    /**
     * "Zcb" Simple Code-size Saving Compressed Instructions
     */
    public data object Zcb : Feature()
    /**
     * "Zcmop" Extension for Compressed May-Be-Operations
     */
    public data object Zcmop : Feature()
    /**
     * "B" Extension for Bit Manipulation
     */
    public data object B : Feature()
    /**
     * "Zba" Extension for Address Generation
     */
    public data object Zba : Feature()
    /**
     * "Zbb" Extension for Basic Bit-Manipulation
     */
    public data object Zbb : Feature()
    /**
     * "Zbc" Extension for Carry-less Multiplication
     */
    public data object Zbc : Feature()
    /**
     * "Zbs" Extension for Single-Bit Instructions
     */
    public data object Zbs : Feature()
    /**
     * "Zbkb" Extension for Bit-Manipulation for Cryptography
     */
    public data object Zbkb : Feature()
    /**
     * "Zbkc" Extension for Carry-less Multiplication for Cryptography
     */
    public data object Zbkc : Feature()
    /**
     * "Zbkx" Extension for Crossbar Permutations
     */
    public data object Zbkx : Feature()
    /**
     * "Zknd" Cryptography Extension for NIST Suite: AES Decryption
     */
    public data object Zknd : Feature()
    /**
     * "Zkne" Cryptography Extension for NIST Suite: AES Encryption
     */
    public data object Zkne : Feature()
    /**
     * "Zknh" Cryptography Extension for NIST Suite: Hash Function Instructions
     */
    public data object Zknh : Feature()
    /**
     * "Zksed" Cryptography Extension for ShangMi Suite: SM4 Block Cipher Instructions
     */
    public data object Zksed : Feature()
    /**
     * "Zksh" Cryptography Extension for ShangMi Suite: SM3 Hash Function Instructions
     */
    public data object Zksh : Feature()
    /**
     * "Zkr" Entropy Source Extension
     */
    public data object Zkr : Feature()
    /**
     * "Zkn" Cryptography Extension for NIST Algorithm Suite
     */
    public data object Zkn : Feature()
    /**
     * "Zks" Cryptography Extension for ShangMi Algorithm Suite
     */
    public data object Zks : Feature()
    /**
     * "Zk" Cryptography Extension for Standard Scalar Cryptography
     */
    public data object Zk : Feature()
    /**
     * "Zkt" Cryptography Extension for Data Independent Execution Latency
     */
    public data object Zkt : Feature()
    /**
     * "V" Extension for Vector Operations
     */
    public data object V : Feature()
    /**
     * "Zve32x" Vector Extension for Embedded Processors (32-bit+; Integer)
     */
    public data object Zve32x : Feature()
    /**
     * "Zve32f" Vector Extension for Embedded Processors (32-bit+; with Single-Precision Floating-Point)
     */
    public data object Zve32f : Feature()
    /**
     * "Zve64x" Vector Extension for Embedded Processors (64-bit+; Integer)
     */
    public data object Zve64x : Feature()
    /**
     * "Zve64f" Vector Extension for Embedded Processors (64-bit+; with Single-Precision Floating-Point)
     */
    public data object Zve64f : Feature()
    /**
     * "Zve64d" Vector Extension for Embedded Processors (64-bit+; with Double-Precision Floating-Point)
     */
    public data object Zve64d : Feature()
    /**
     * "Zvfh" Vector Extension for Half-Precision Floating-Point
     */
    public data object Zvfh : Feature()
    /**
     * "Zvfhmin" Vector Extension for Minimal Half-Precision Floating-Point
     */
    public data object Zvfhmin : Feature()
    /**
     * "Zvfbfmin" Vector Extension for BF16 Converts
     */
    public data object Zvfbfmin : Feature()
    /**
     * "Zvfbfwma" Vector Extension for BF16 Widening Multiply-Add
     */
    public data object Zvfbfwma : Feature()
    /**
     * "Zvbb" Extension for Vector Basic Bit-Manipulation
     */
    public data object Zvbb : Feature()
    /**
     * "Zvbc" Extension for Vector Carryless Multiplication
     */
    public data object Zvbc : Feature()
    /**
     * "Zvkb" Extension for Vector Cryptography Bit-Manipulation
     */
    public data object Zvkb : Feature()
    /**
     * "Zvkg" Cryptography Extension for Vector GCM/GMAC
     */
    public data object Zvkg : Feature()
    /**
     * "Zvkned" Cryptography Extension for NIST Suite: Vector AES Block Cipher
     */
    public data object Zvkned : Feature()
    /**
     * "Zvknha" Cryptography Extension for Vector SHA-2 Secure Hash (SHA-256)
     */
    public data object Zvknha : Feature()
    /**
     * "Zvknhb" Cryptography Extension for Vector SHA-2 Secure Hash (SHA-256/512)
     */
    public data object Zvknhb : Feature()
    /**
     * "Zvksed" Cryptography Extension for ShangMi Suite: Vector SM4 Block Cipher
     */
    public data object Zvksed : Feature()
    /**
     * "Zvksh" Cryptography Extension for ShangMi Suite: Vector SM3 Secure Hash
     */
    public data object Zvksh : Feature()
    /**
     * "Zvkn" Cryptography Extension for NIST Algorithm Suite
     */
    public data object Zvkn : Feature()
    /**
     * "Zvknc" Cryptography Extension for NIST Algorithm Suite with Carryless Multiply
     */
    public data object Zvknc : Feature()
    /**
     * "Zvkng" Cryptography Extension for NIST Algorithm Suite with GCM
     */
    public data object Zvkng : Feature()
    /**
     * "Zvks" Cryptography Extension for ShangMi Algorithm Suite
     */
    public data object Zvks : Feature()
    /**
     * "Zvksc" Cryptography Extension for ShangMi Algorithm Suite with Carryless Multiply
     */
    public data object Zvksc : Feature()
    /**
     * "Zvksg" Cryptography Extension for ShangMi Algorithm Suite with GCM
     */
    public data object Zvksg : Feature()
    /**
     * "Zvkt" Extension for Vector Data-Independent Execution Latency
     */
    public data object Zvkt : Feature()
    /**
     * "J" Extension for Dynamically Translated Languages
     */
    public data object J : Feature()
    /**
     * "P" Extension for Packed-SIMD Instructions
     */
    public data object P : Feature()

    public open fun toStr(): String =
        when (this) {
            Feature.rv32i -> "rv32i"
            Feature.rv32e -> "rv32e"
            Feature.rv64i -> "rv64i"
            Feature.rv128i -> "rv128i"
            Feature.unalignedScalarMem -> "unaligned-scalar-mem"
            Feature.unalignedVectorMem -> "unaligned-vector-mem"
            Feature.zicsr -> "zicsr"
            Feature.zicntr -> "zicntr"
            Feature.zihpm -> "zihpm"
            Feature.zifencei -> "zifencei"
            Feature.zihintntl -> "zihintntl"
            Feature.zihintpause -> "zihintpause"
            Feature.zimop -> "zimop"
            Feature.zicbom -> "zicbom"
            Feature.zicboz -> "zicboz"
            Feature.zicond -> "zicond"
            Feature.m -> "m"
            Feature.zmmul -> "zmmul"
            Feature.a -> "a"
            Feature.zalrsc -> "zalrsc"
            Feature.zaamo -> "zaamo"
            Feature.zawrs -> "zawrs"
            Feature.zabha -> "zabha"
            Feature.zacas -> "zacas"
            Feature.zam -> "zam"
            Feature.ztso -> "ztso"
            Feature.f -> "f"
            Feature.d -> "d"
            Feature.q -> "q"
            Feature.zfh -> "zfh"
            Feature.zfhmin -> "zfhmin"
            Feature.zfa -> "zfa"
            Feature.zfbfmin -> "zfbfmin"
            Feature.zfinx -> "zfinx"
            Feature.zdinx -> "zdinx"
            Feature.zhinx -> "zhinx"
            Feature.zhinxmin -> "zhinxmin"
            Feature.c -> "c"
            Feature.zca -> "zca"
            Feature.zcf -> "zcf"
            Feature.zcd -> "zcd"
            Feature.zcb -> "zcb"
            Feature.zcmop -> "zcmop"
            Feature.b -> "b"
            Feature.zba -> "zba"
            Feature.zbb -> "zbb"
            Feature.zbc -> "zbc"
            Feature.zbs -> "zbs"
            Feature.zbkb -> "zbkb"
            Feature.zbkc -> "zbkc"
            Feature.zbkx -> "zbkx"
            Feature.zknd -> "zknd"
            Feature.zkne -> "zkne"
            Feature.zknh -> "zknh"
            Feature.zksed -> "zksed"
            Feature.zksh -> "zksh"
            Feature.zkr -> "zkr"
            Feature.zkn -> "zkn"
            Feature.zks -> "zks"
            Feature.zk -> "zk"
            Feature.zkt -> "zkt"
            Feature.v -> "v"
            Feature.zve32x -> "zve32x"
            Feature.zve32f -> "zve32f"
            Feature.zve64x -> "zve64x"
            Feature.zve64f -> "zve64f"
            Feature.zve64d -> "zve64d"
            Feature.zvfh -> "zvfh"
            Feature.zvfhmin -> "zvfhmin"
            Feature.zvfbfmin -> "zvfbfmin"
            Feature.zvfbfwma -> "zvfbfwma"
            Feature.zvbb -> "zvbb"
            Feature.zvbc -> "zvbc"
            Feature.zvkb -> "zvkb"
            Feature.zvkg -> "zvkg"
            Feature.zvkned -> "zvkned"
            Feature.zvknha -> "zvknha"
            Feature.zvknhb -> "zvknhb"
            Feature.zvksed -> "zvksed"
            Feature.zvksh -> "zvksh"
            Feature.zvkn -> "zvkn"
            Feature.zvknc -> "zvknc"
            Feature.zvkng -> "zvkng"
            Feature.zvks -> "zvks"
            Feature.zvksc -> "zvksc"
            Feature.zvksg -> "zvksg"
            Feature.zvkt -> "zvkt"
            Feature.j -> "j"
            Feature.p -> "p"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature::rv32i, Feature::rv32e, Feature::rv64i, Feature::rv128i, Feature::unalignedScalarMem, Feature::unalignedVectorMem, Feature::zicsr, Feature::zicntr, Feature::zihpm, Feature::zifencei, Feature::zihintntl, Feature::zihintpause, Feature::zimop, Feature::zicbom, Feature::zicboz, Feature::zicond, Feature::m, Feature::zmmul, Feature::a, Feature::zalrsc, Feature::zaamo, Feature::zawrs, Feature::zabha, Feature::zacas, Feature::zam, Feature::ztso, Feature::f, Feature::d, Feature::q, Feature::zfh, Feature::zfhmin, Feature::zfa, Feature::zfbfmin, Feature::zfinx, Feature::zdinx, Feature::zhinx, Feature::zhinxmin, Feature::c, Feature::zca, Feature::zcf, Feature::zcd, Feature::zcb, Feature::zcmop, Feature::b, Feature::zba, Feature::zbb, Feature::zbc, Feature::zbs, Feature::zbkb, Feature::zbkc, Feature::zbkx, Feature::zknd, Feature::zkne, Feature::zknh, Feature::zksed, Feature::zksh, Feature::zkr, Feature::zkn, Feature::zks, Feature::zk, Feature::zkt, Feature::v, Feature::zve32x, Feature::zve32f, Feature::zve64x, Feature::zve64f, Feature::zve64d, Feature::zvfh, Feature::zvfhmin, Feature::zvfbfmin, Feature::zvfbfwma, Feature::zvbb, Feature::zvbc, Feature::zvkb, Feature::zvkg, Feature::zvkned, Feature::zvknha, Feature::zvknhb, Feature::zvksed, Feature::zvksh, Feature::zvkn, Feature::zvknc, Feature::zvkng, Feature::zvks, Feature::zvksc, Feature::zvksg, Feature::zvkt, Feature::j, Feature::p)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "rv32i" -> Result.Ok(Feature::rv32i)
                "rv32e" -> Result.Ok(Feature::rv32e)
                "rv64i" -> Result.Ok(Feature::rv64i)
                "rv128i" -> Result.Ok(Feature::rv128i)
                "unaligned-scalar-mem" -> Result.Ok(Feature::unalignedScalarMem)
                "unaligned-vector-mem" -> Result.Ok(Feature::unalignedVectorMem)
                "zicsr" -> Result.Ok(Feature::zicsr)
                "zicntr" -> Result.Ok(Feature::zicntr)
                "zihpm" -> Result.Ok(Feature::zihpm)
                "zifencei" -> Result.Ok(Feature::zifencei)
                "zihintntl" -> Result.Ok(Feature::zihintntl)
                "zihintpause" -> Result.Ok(Feature::zihintpause)
                "zimop" -> Result.Ok(Feature::zimop)
                "zicbom" -> Result.Ok(Feature::zicbom)
                "zicboz" -> Result.Ok(Feature::zicboz)
                "zicond" -> Result.Ok(Feature::zicond)
                "m" -> Result.Ok(Feature::m)
                "zmmul" -> Result.Ok(Feature::zmmul)
                "a" -> Result.Ok(Feature::a)
                "zalrsc" -> Result.Ok(Feature::zalrsc)
                "zaamo" -> Result.Ok(Feature::zaamo)
                "zawrs" -> Result.Ok(Feature::zawrs)
                "zabha" -> Result.Ok(Feature::zabha)
                "zacas" -> Result.Ok(Feature::zacas)
                "zam" -> Result.Ok(Feature::zam)
                "ztso" -> Result.Ok(Feature::ztso)
                "f" -> Result.Ok(Feature::f)
                "d" -> Result.Ok(Feature::d)
                "q" -> Result.Ok(Feature::q)
                "zfh" -> Result.Ok(Feature::zfh)
                "zfhmin" -> Result.Ok(Feature::zfhmin)
                "zfa" -> Result.Ok(Feature::zfa)
                "zfbfmin" -> Result.Ok(Feature::zfbfmin)
                "zfinx" -> Result.Ok(Feature::zfinx)
                "zdinx" -> Result.Ok(Feature::zdinx)
                "zhinx" -> Result.Ok(Feature::zhinx)
                "zhinxmin" -> Result.Ok(Feature::zhinxmin)
                "c" -> Result.Ok(Feature::c)
                "zca" -> Result.Ok(Feature::zca)
                "zcf" -> Result.Ok(Feature::zcf)
                "zcd" -> Result.Ok(Feature::zcd)
                "zcb" -> Result.Ok(Feature::zcb)
                "zcmop" -> Result.Ok(Feature::zcmop)
                "b" -> Result.Ok(Feature::b)
                "zba" -> Result.Ok(Feature::zba)
                "zbb" -> Result.Ok(Feature::zbb)
                "zbc" -> Result.Ok(Feature::zbc)
                "zbs" -> Result.Ok(Feature::zbs)
                "zbkb" -> Result.Ok(Feature::zbkb)
                "zbkc" -> Result.Ok(Feature::zbkc)
                "zbkx" -> Result.Ok(Feature::zbkx)
                "zknd" -> Result.Ok(Feature::zknd)
                "zkne" -> Result.Ok(Feature::zkne)
                "zknh" -> Result.Ok(Feature::zknh)
                "zksed" -> Result.Ok(Feature::zksed)
                "zksh" -> Result.Ok(Feature::zksh)
                "zkr" -> Result.Ok(Feature::zkr)
                "zkn" -> Result.Ok(Feature::zkn)
                "zks" -> Result.Ok(Feature::zks)
                "zk" -> Result.Ok(Feature::zk)
                "zkt" -> Result.Ok(Feature::zkt)
                "v" -> Result.Ok(Feature::v)
                "zve32x" -> Result.Ok(Feature::zve32x)
                "zve32f" -> Result.Ok(Feature::zve32f)
                "zve64x" -> Result.Ok(Feature::zve64x)
                "zve64f" -> Result.Ok(Feature::zve64f)
                "zve64d" -> Result.Ok(Feature::zve64d)
                "zvfh" -> Result.Ok(Feature::zvfh)
                "zvfhmin" -> Result.Ok(Feature::zvfhmin)
                "zvfbfmin" -> Result.Ok(Feature::zvfbfmin)
                "zvfbfwma" -> Result.Ok(Feature::zvfbfwma)
                "zvbb" -> Result.Ok(Feature::zvbb)
                "zvbc" -> Result.Ok(Feature::zvbc)
                "zvkb" -> Result.Ok(Feature::zvkb)
                "zvkg" -> Result.Ok(Feature::zvkg)
                "zvkned" -> Result.Ok(Feature::zvkned)
                "zvknha" -> Result.Ok(Feature::zvknha)
                "zvknhb" -> Result.Ok(Feature::zvknhb)
                "zvksed" -> Result.Ok(Feature::zvksed)
                "zvksh" -> Result.Ok(Feature::zvksh)
                "zvkn" -> Result.Ok(Feature::zvkn)
                "zvknc" -> Result.Ok(Feature::zvknc)
                "zvkng" -> Result.Ok(Feature::zvkng)
                "zvks" -> Result.Ok(Feature::zvks)
                "zvksc" -> Result.Ok(Feature::zvksc)
                "zvksg" -> Result.Ok(Feature::zvksg)
                "zvkt" -> Result.Ok(Feature::zvkt)
                "j" -> Result.Ok(Feature::j)
                "p" -> Result.Ok(Feature::p)
                else -> Result.Err(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rv32i(): Boolean = checkFor(Feature::rv32i)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rv32e(): Boolean = checkFor(Feature::rv32e)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rv64i(): Boolean = checkFor(Feature::rv64i)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rv128i(): Boolean = checkFor(Feature::rv128i)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun unalignedScalarMem(): Boolean = checkFor(Feature::unalignedScalarMem)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun unalignedVectorMem(): Boolean = checkFor(Feature::unalignedVectorMem)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicsr(): Boolean = checkFor(Feature::zicsr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicntr(): Boolean = checkFor(Feature::zicntr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zihpm(): Boolean = checkFor(Feature::zihpm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zifencei(): Boolean = checkFor(Feature::zifencei)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zihintntl(): Boolean = checkFor(Feature::zihintntl)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zihintpause(): Boolean = checkFor(Feature::zihintpause)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zimop(): Boolean = checkFor(Feature::zimop)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicbom(): Boolean = checkFor(Feature::zicbom)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicboz(): Boolean = checkFor(Feature::zicboz)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicond(): Boolean = checkFor(Feature::zicond)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun m(): Boolean = checkFor(Feature::m)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zmmul(): Boolean = checkFor(Feature::zmmul)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun a(): Boolean = checkFor(Feature::a)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zalrsc(): Boolean = checkFor(Feature::zalrsc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zaamo(): Boolean = checkFor(Feature::zaamo)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zawrs(): Boolean = checkFor(Feature::zawrs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zabha(): Boolean = checkFor(Feature::zabha)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zacas(): Boolean = checkFor(Feature::zacas)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zam(): Boolean = checkFor(Feature::zam)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ztso(): Boolean = checkFor(Feature::ztso)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f(): Boolean = checkFor(Feature::f)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun d(): Boolean = checkFor(Feature::d)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun q(): Boolean = checkFor(Feature::q)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfh(): Boolean = checkFor(Feature::zfh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfhmin(): Boolean = checkFor(Feature::zfhmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfa(): Boolean = checkFor(Feature::zfa)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfbfmin(): Boolean = checkFor(Feature::zfbfmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfinx(): Boolean = checkFor(Feature::zfinx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zdinx(): Boolean = checkFor(Feature::zdinx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zhinx(): Boolean = checkFor(Feature::zhinx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zhinxmin(): Boolean = checkFor(Feature::zhinxmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun c(): Boolean = checkFor(Feature::c)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zca(): Boolean = checkFor(Feature::zca)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zcf(): Boolean = checkFor(Feature::zcf)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zcd(): Boolean = checkFor(Feature::zcd)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zcb(): Boolean = checkFor(Feature::zcb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zcmop(): Boolean = checkFor(Feature::zcmop)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun b(): Boolean = checkFor(Feature::b)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zba(): Boolean = checkFor(Feature::zba)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbb(): Boolean = checkFor(Feature::zbb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbc(): Boolean = checkFor(Feature::zbc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbs(): Boolean = checkFor(Feature::zbs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbkb(): Boolean = checkFor(Feature::zbkb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbkc(): Boolean = checkFor(Feature::zbkc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbkx(): Boolean = checkFor(Feature::zbkx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zknd(): Boolean = checkFor(Feature::zknd)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zkne(): Boolean = checkFor(Feature::zkne)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zknh(): Boolean = checkFor(Feature::zknh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zksed(): Boolean = checkFor(Feature::zksed)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zksh(): Boolean = checkFor(Feature::zksh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zkr(): Boolean = checkFor(Feature::zkr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zkn(): Boolean = checkFor(Feature::zkn)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zks(): Boolean = checkFor(Feature::zks)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zk(): Boolean = checkFor(Feature::zk)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zkt(): Boolean = checkFor(Feature::zkt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun v(): Boolean = checkFor(Feature::v)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve32x(): Boolean = checkFor(Feature::zve32x)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve32f(): Boolean = checkFor(Feature::zve32f)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve64x(): Boolean = checkFor(Feature::zve64x)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve64f(): Boolean = checkFor(Feature::zve64f)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve64d(): Boolean = checkFor(Feature::zve64d)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvfh(): Boolean = checkFor(Feature::zvfh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvfhmin(): Boolean = checkFor(Feature::zvfhmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvfbfmin(): Boolean = checkFor(Feature::zvfbfmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvfbfwma(): Boolean = checkFor(Feature::zvfbfwma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvbb(): Boolean = checkFor(Feature::zvbb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvbc(): Boolean = checkFor(Feature::zvbc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkb(): Boolean = checkFor(Feature::zvkb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkg(): Boolean = checkFor(Feature::zvkg)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkned(): Boolean = checkFor(Feature::zvkned)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvknha(): Boolean = checkFor(Feature::zvknha)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvknhb(): Boolean = checkFor(Feature::zvknhb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvksed(): Boolean = checkFor(Feature::zvksed)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvksh(): Boolean = checkFor(Feature::zvksh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkn(): Boolean = checkFor(Feature::zvkn)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvknc(): Boolean = checkFor(Feature::zvknc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkng(): Boolean = checkFor(Feature::zvkng)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvks(): Boolean = checkFor(Feature::zvks)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvksc(): Boolean = checkFor(Feature::zvksc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvksg(): Boolean = checkFor(Feature::zvksg)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkt(): Boolean = checkFor(Feature::zvkt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun j(): Boolean = checkFor(Feature::j)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun p(): Boolean = checkFor(Feature::p)

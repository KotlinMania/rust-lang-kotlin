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

    private fun kmDiscriminant(): Int =
        when (this) {
        Rv32i -> 0
        Rv32e -> 1
        Rv64i -> 2
        Rv128i -> 3
        UnalignedScalarMem -> 4
        UnalignedVectorMem -> 5
        Zicsr -> 6
        Zicntr -> 7
        Zihpm -> 8
        Zifencei -> 9
        Zihintntl -> 10
        Zihintpause -> 11
        Zimop -> 12
        Zicbom -> 13
        Zicboz -> 14
        Zicond -> 15
        M -> 16
        Zmmul -> 17
        A -> 18
        Zalrsc -> 19
        Zaamo -> 20
        Zawrs -> 21
        Zabha -> 22
        Zacas -> 23
        Zam -> 24
        Ztso -> 25
        F -> 26
        D -> 27
        Q -> 28
        Zfh -> 29
        Zfhmin -> 30
        Zfa -> 31
        Zfbfmin -> 32
        Zfinx -> 33
        Zdinx -> 34
        Zhinx -> 35
        Zhinxmin -> 36
        C -> 37
        Zca -> 38
        Zcf -> 39
        Zcd -> 40
        Zcb -> 41
        Zcmop -> 42
        B -> 43
        Zba -> 44
        Zbb -> 45
        Zbc -> 46
        Zbs -> 47
        Zbkb -> 48
        Zbkc -> 49
        Zbkx -> 50
        Zknd -> 51
        Zkne -> 52
        Zknh -> 53
        Zksed -> 54
        Zksh -> 55
        Zkr -> 56
        Zkn -> 57
        Zks -> 58
        Zk -> 59
        Zkt -> 60
        V -> 61
        Zve32x -> 62
        Zve32f -> 63
        Zve64x -> 64
        Zve64f -> 65
        Zve64d -> 66
        Zvfh -> 67
        Zvfhmin -> 68
        Zvfbfmin -> 69
        Zvfbfwma -> 70
        Zvbb -> 71
        Zvbc -> 72
        Zvkb -> 73
        Zvkg -> 74
        Zvkned -> 75
        Zvknha -> 76
        Zvknhb -> 77
        Zvksed -> 78
        Zvksh -> 79
        Zvkn -> 80
        Zvknc -> 81
        Zvkng -> 82
        Zvks -> 83
        Zvksc -> 84
        Zvksg -> 85
        Zvkt -> 86
        J -> 87
        P -> 88
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public open fun toStr(): String =
        when (this) {
            Feature.Rv32i -> "rv32i"
            Feature.Rv32e -> "rv32e"
            Feature.Rv64i -> "rv64i"
            Feature.Rv128i -> "rv128i"
            Feature.UnalignedScalarMem -> "unaligned-scalar-mem"
            Feature.UnalignedVectorMem -> "unaligned-vector-mem"
            Feature.Zicsr -> "zicsr"
            Feature.Zicntr -> "zicntr"
            Feature.Zihpm -> "zihpm"
            Feature.Zifencei -> "zifencei"
            Feature.Zihintntl -> "zihintntl"
            Feature.Zihintpause -> "zihintpause"
            Feature.Zimop -> "zimop"
            Feature.Zicbom -> "zicbom"
            Feature.Zicboz -> "zicboz"
            Feature.Zicond -> "zicond"
            Feature.M -> "m"
            Feature.Zmmul -> "zmmul"
            Feature.A -> "a"
            Feature.Zalrsc -> "zalrsc"
            Feature.Zaamo -> "zaamo"
            Feature.Zawrs -> "zawrs"
            Feature.Zabha -> "zabha"
            Feature.Zacas -> "zacas"
            Feature.Zam -> "zam"
            Feature.Ztso -> "ztso"
            Feature.F -> "f"
            Feature.D -> "d"
            Feature.Q -> "q"
            Feature.Zfh -> "zfh"
            Feature.Zfhmin -> "zfhmin"
            Feature.Zfa -> "zfa"
            Feature.Zfbfmin -> "zfbfmin"
            Feature.Zfinx -> "zfinx"
            Feature.Zdinx -> "zdinx"
            Feature.Zhinx -> "zhinx"
            Feature.Zhinxmin -> "zhinxmin"
            Feature.C -> "c"
            Feature.Zca -> "zca"
            Feature.Zcf -> "zcf"
            Feature.Zcd -> "zcd"
            Feature.Zcb -> "zcb"
            Feature.Zcmop -> "zcmop"
            Feature.B -> "b"
            Feature.Zba -> "zba"
            Feature.Zbb -> "zbb"
            Feature.Zbc -> "zbc"
            Feature.Zbs -> "zbs"
            Feature.Zbkb -> "zbkb"
            Feature.Zbkc -> "zbkc"
            Feature.Zbkx -> "zbkx"
            Feature.Zknd -> "zknd"
            Feature.Zkne -> "zkne"
            Feature.Zknh -> "zknh"
            Feature.Zksed -> "zksed"
            Feature.Zksh -> "zksh"
            Feature.Zkr -> "zkr"
            Feature.Zkn -> "zkn"
            Feature.Zks -> "zks"
            Feature.Zk -> "zk"
            Feature.Zkt -> "zkt"
            Feature.V -> "v"
            Feature.Zve32x -> "zve32x"
            Feature.Zve32f -> "zve32f"
            Feature.Zve64x -> "zve64x"
            Feature.Zve64f -> "zve64f"
            Feature.Zve64d -> "zve64d"
            Feature.Zvfh -> "zvfh"
            Feature.Zvfhmin -> "zvfhmin"
            Feature.Zvfbfmin -> "zvfbfmin"
            Feature.Zvfbfwma -> "zvfbfwma"
            Feature.Zvbb -> "zvbb"
            Feature.Zvbc -> "zvbc"
            Feature.Zvkb -> "zvkb"
            Feature.Zvkg -> "zvkg"
            Feature.Zvkned -> "zvkned"
            Feature.Zvknha -> "zvknha"
            Feature.Zvknhb -> "zvknhb"
            Feature.Zvksed -> "zvksed"
            Feature.Zvksh -> "zvksh"
            Feature.Zvkn -> "zvkn"
            Feature.Zvknc -> "zvknc"
            Feature.Zvkng -> "zvkng"
            Feature.Zvks -> "zvks"
            Feature.Zvksc -> "zvksc"
            Feature.Zvksg -> "zvksg"
            Feature.Zvkt -> "zvkt"
            Feature.J -> "j"
            Feature.P -> "p"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature.Rv32i, Feature.Rv32e, Feature.Rv64i, Feature.Rv128i, Feature.UnalignedScalarMem, Feature.UnalignedVectorMem, Feature.Zicsr, Feature.Zicntr, Feature.Zihpm, Feature.Zifencei, Feature.Zihintntl, Feature.Zihintpause, Feature.Zimop, Feature.Zicbom, Feature.Zicboz, Feature.Zicond, Feature.M, Feature.Zmmul, Feature.A, Feature.Zalrsc, Feature.Zaamo, Feature.Zawrs, Feature.Zabha, Feature.Zacas, Feature.Zam, Feature.Ztso, Feature.F, Feature.D, Feature.Q, Feature.Zfh, Feature.Zfhmin, Feature.Zfa, Feature.Zfbfmin, Feature.Zfinx, Feature.Zdinx, Feature.Zhinx, Feature.Zhinxmin, Feature.C, Feature.Zca, Feature.Zcf, Feature.Zcd, Feature.Zcb, Feature.Zcmop, Feature.B, Feature.Zba, Feature.Zbb, Feature.Zbc, Feature.Zbs, Feature.Zbkb, Feature.Zbkc, Feature.Zbkx, Feature.Zknd, Feature.Zkne, Feature.Zknh, Feature.Zksed, Feature.Zksh, Feature.Zkr, Feature.Zkn, Feature.Zks, Feature.Zk, Feature.Zkt, Feature.V, Feature.Zve32x, Feature.Zve32f, Feature.Zve64x, Feature.Zve64f, Feature.Zve64d, Feature.Zvfh, Feature.Zvfhmin, Feature.Zvfbfmin, Feature.Zvfbfwma, Feature.Zvbb, Feature.Zvbc, Feature.Zvkb, Feature.Zvkg, Feature.Zvkned, Feature.Zvknha, Feature.Zvknhb, Feature.Zvksed, Feature.Zvksh, Feature.Zvkn, Feature.Zvknc, Feature.Zvkng, Feature.Zvks, Feature.Zvksc, Feature.Zvksg, Feature.Zvkt, Feature.J, Feature.P)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "rv32i" -> Result.Ok<Feature, Unit>(Feature.Rv32i)
                "rv32e" -> Result.Ok<Feature, Unit>(Feature.Rv32e)
                "rv64i" -> Result.Ok<Feature, Unit>(Feature.Rv64i)
                "rv128i" -> Result.Ok<Feature, Unit>(Feature.Rv128i)
                "unaligned-scalar-mem" -> Result.Ok<Feature, Unit>(Feature.UnalignedScalarMem)
                "unaligned-vector-mem" -> Result.Ok<Feature, Unit>(Feature.UnalignedVectorMem)
                "zicsr" -> Result.Ok<Feature, Unit>(Feature.Zicsr)
                "zicntr" -> Result.Ok<Feature, Unit>(Feature.Zicntr)
                "zihpm" -> Result.Ok<Feature, Unit>(Feature.Zihpm)
                "zifencei" -> Result.Ok<Feature, Unit>(Feature.Zifencei)
                "zihintntl" -> Result.Ok<Feature, Unit>(Feature.Zihintntl)
                "zihintpause" -> Result.Ok<Feature, Unit>(Feature.Zihintpause)
                "zimop" -> Result.Ok<Feature, Unit>(Feature.Zimop)
                "zicbom" -> Result.Ok<Feature, Unit>(Feature.Zicbom)
                "zicboz" -> Result.Ok<Feature, Unit>(Feature.Zicboz)
                "zicond" -> Result.Ok<Feature, Unit>(Feature.Zicond)
                "m" -> Result.Ok<Feature, Unit>(Feature.M)
                "zmmul" -> Result.Ok<Feature, Unit>(Feature.Zmmul)
                "a" -> Result.Ok<Feature, Unit>(Feature.A)
                "zalrsc" -> Result.Ok<Feature, Unit>(Feature.Zalrsc)
                "zaamo" -> Result.Ok<Feature, Unit>(Feature.Zaamo)
                "zawrs" -> Result.Ok<Feature, Unit>(Feature.Zawrs)
                "zabha" -> Result.Ok<Feature, Unit>(Feature.Zabha)
                "zacas" -> Result.Ok<Feature, Unit>(Feature.Zacas)
                "zam" -> Result.Ok<Feature, Unit>(Feature.Zam)
                "ztso" -> Result.Ok<Feature, Unit>(Feature.Ztso)
                "f" -> Result.Ok<Feature, Unit>(Feature.F)
                "d" -> Result.Ok<Feature, Unit>(Feature.D)
                "q" -> Result.Ok<Feature, Unit>(Feature.Q)
                "zfh" -> Result.Ok<Feature, Unit>(Feature.Zfh)
                "zfhmin" -> Result.Ok<Feature, Unit>(Feature.Zfhmin)
                "zfa" -> Result.Ok<Feature, Unit>(Feature.Zfa)
                "zfbfmin" -> Result.Ok<Feature, Unit>(Feature.Zfbfmin)
                "zfinx" -> Result.Ok<Feature, Unit>(Feature.Zfinx)
                "zdinx" -> Result.Ok<Feature, Unit>(Feature.Zdinx)
                "zhinx" -> Result.Ok<Feature, Unit>(Feature.Zhinx)
                "zhinxmin" -> Result.Ok<Feature, Unit>(Feature.Zhinxmin)
                "c" -> Result.Ok<Feature, Unit>(Feature.C)
                "zca" -> Result.Ok<Feature, Unit>(Feature.Zca)
                "zcf" -> Result.Ok<Feature, Unit>(Feature.Zcf)
                "zcd" -> Result.Ok<Feature, Unit>(Feature.Zcd)
                "zcb" -> Result.Ok<Feature, Unit>(Feature.Zcb)
                "zcmop" -> Result.Ok<Feature, Unit>(Feature.Zcmop)
                "b" -> Result.Ok<Feature, Unit>(Feature.B)
                "zba" -> Result.Ok<Feature, Unit>(Feature.Zba)
                "zbb" -> Result.Ok<Feature, Unit>(Feature.Zbb)
                "zbc" -> Result.Ok<Feature, Unit>(Feature.Zbc)
                "zbs" -> Result.Ok<Feature, Unit>(Feature.Zbs)
                "zbkb" -> Result.Ok<Feature, Unit>(Feature.Zbkb)
                "zbkc" -> Result.Ok<Feature, Unit>(Feature.Zbkc)
                "zbkx" -> Result.Ok<Feature, Unit>(Feature.Zbkx)
                "zknd" -> Result.Ok<Feature, Unit>(Feature.Zknd)
                "zkne" -> Result.Ok<Feature, Unit>(Feature.Zkne)
                "zknh" -> Result.Ok<Feature, Unit>(Feature.Zknh)
                "zksed" -> Result.Ok<Feature, Unit>(Feature.Zksed)
                "zksh" -> Result.Ok<Feature, Unit>(Feature.Zksh)
                "zkr" -> Result.Ok<Feature, Unit>(Feature.Zkr)
                "zkn" -> Result.Ok<Feature, Unit>(Feature.Zkn)
                "zks" -> Result.Ok<Feature, Unit>(Feature.Zks)
                "zk" -> Result.Ok<Feature, Unit>(Feature.Zk)
                "zkt" -> Result.Ok<Feature, Unit>(Feature.Zkt)
                "v" -> Result.Ok<Feature, Unit>(Feature.V)
                "zve32x" -> Result.Ok<Feature, Unit>(Feature.Zve32x)
                "zve32f" -> Result.Ok<Feature, Unit>(Feature.Zve32f)
                "zve64x" -> Result.Ok<Feature, Unit>(Feature.Zve64x)
                "zve64f" -> Result.Ok<Feature, Unit>(Feature.Zve64f)
                "zve64d" -> Result.Ok<Feature, Unit>(Feature.Zve64d)
                "zvfh" -> Result.Ok<Feature, Unit>(Feature.Zvfh)
                "zvfhmin" -> Result.Ok<Feature, Unit>(Feature.Zvfhmin)
                "zvfbfmin" -> Result.Ok<Feature, Unit>(Feature.Zvfbfmin)
                "zvfbfwma" -> Result.Ok<Feature, Unit>(Feature.Zvfbfwma)
                "zvbb" -> Result.Ok<Feature, Unit>(Feature.Zvbb)
                "zvbc" -> Result.Ok<Feature, Unit>(Feature.Zvbc)
                "zvkb" -> Result.Ok<Feature, Unit>(Feature.Zvkb)
                "zvkg" -> Result.Ok<Feature, Unit>(Feature.Zvkg)
                "zvkned" -> Result.Ok<Feature, Unit>(Feature.Zvkned)
                "zvknha" -> Result.Ok<Feature, Unit>(Feature.Zvknha)
                "zvknhb" -> Result.Ok<Feature, Unit>(Feature.Zvknhb)
                "zvksed" -> Result.Ok<Feature, Unit>(Feature.Zvksed)
                "zvksh" -> Result.Ok<Feature, Unit>(Feature.Zvksh)
                "zvkn" -> Result.Ok<Feature, Unit>(Feature.Zvkn)
                "zvknc" -> Result.Ok<Feature, Unit>(Feature.Zvknc)
                "zvkng" -> Result.Ok<Feature, Unit>(Feature.Zvkng)
                "zvks" -> Result.Ok<Feature, Unit>(Feature.Zvks)
                "zvksc" -> Result.Ok<Feature, Unit>(Feature.Zvksc)
                "zvksg" -> Result.Ok<Feature, Unit>(Feature.Zvksg)
                "zvkt" -> Result.Ok<Feature, Unit>(Feature.Zvkt)
                "j" -> Result.Ok<Feature, Unit>(Feature.J)
                "p" -> Result.Ok<Feature, Unit>(Feature.P)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rv32i(): Boolean = checkFor(Feature.Rv32i)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rv32e(): Boolean = checkFor(Feature.Rv32e)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rv64i(): Boolean = checkFor(Feature.Rv64i)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rv128i(): Boolean = checkFor(Feature.Rv128i)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun unalignedScalarMem(): Boolean = checkFor(Feature.UnalignedScalarMem)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun unalignedVectorMem(): Boolean = checkFor(Feature.UnalignedVectorMem)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicsr(): Boolean = checkFor(Feature.Zicsr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicntr(): Boolean = checkFor(Feature.Zicntr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zihpm(): Boolean = checkFor(Feature.Zihpm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zifencei(): Boolean = checkFor(Feature.Zifencei)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zihintntl(): Boolean = checkFor(Feature.Zihintntl)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zihintpause(): Boolean = checkFor(Feature.Zihintpause)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zimop(): Boolean = checkFor(Feature.Zimop)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicbom(): Boolean = checkFor(Feature.Zicbom)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicboz(): Boolean = checkFor(Feature.Zicboz)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zicond(): Boolean = checkFor(Feature.Zicond)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun m(): Boolean = checkFor(Feature.M)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zmmul(): Boolean = checkFor(Feature.Zmmul)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun a(): Boolean = checkFor(Feature.A)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zalrsc(): Boolean = checkFor(Feature.Zalrsc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zaamo(): Boolean = checkFor(Feature.Zaamo)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zawrs(): Boolean = checkFor(Feature.Zawrs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zabha(): Boolean = checkFor(Feature.Zabha)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zacas(): Boolean = checkFor(Feature.Zacas)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zam(): Boolean = checkFor(Feature.Zam)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ztso(): Boolean = checkFor(Feature.Ztso)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f(): Boolean = checkFor(Feature.F)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun d(): Boolean = checkFor(Feature.D)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun q(): Boolean = checkFor(Feature.Q)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfh(): Boolean = checkFor(Feature.Zfh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfhmin(): Boolean = checkFor(Feature.Zfhmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfa(): Boolean = checkFor(Feature.Zfa)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfbfmin(): Boolean = checkFor(Feature.Zfbfmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zfinx(): Boolean = checkFor(Feature.Zfinx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zdinx(): Boolean = checkFor(Feature.Zdinx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zhinx(): Boolean = checkFor(Feature.Zhinx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zhinxmin(): Boolean = checkFor(Feature.Zhinxmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun c(): Boolean = checkFor(Feature.C)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zca(): Boolean = checkFor(Feature.Zca)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zcf(): Boolean = checkFor(Feature.Zcf)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zcd(): Boolean = checkFor(Feature.Zcd)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zcb(): Boolean = checkFor(Feature.Zcb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zcmop(): Boolean = checkFor(Feature.Zcmop)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun b(): Boolean = checkFor(Feature.B)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zba(): Boolean = checkFor(Feature.Zba)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbb(): Boolean = checkFor(Feature.Zbb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbc(): Boolean = checkFor(Feature.Zbc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbs(): Boolean = checkFor(Feature.Zbs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbkb(): Boolean = checkFor(Feature.Zbkb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbkc(): Boolean = checkFor(Feature.Zbkc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zbkx(): Boolean = checkFor(Feature.Zbkx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zknd(): Boolean = checkFor(Feature.Zknd)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zkne(): Boolean = checkFor(Feature.Zkne)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zknh(): Boolean = checkFor(Feature.Zknh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zksed(): Boolean = checkFor(Feature.Zksed)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zksh(): Boolean = checkFor(Feature.Zksh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zkr(): Boolean = checkFor(Feature.Zkr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zkn(): Boolean = checkFor(Feature.Zkn)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zks(): Boolean = checkFor(Feature.Zks)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zk(): Boolean = checkFor(Feature.Zk)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zkt(): Boolean = checkFor(Feature.Zkt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun v(): Boolean = checkFor(Feature.V)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve32x(): Boolean = checkFor(Feature.Zve32x)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve32f(): Boolean = checkFor(Feature.Zve32f)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve64x(): Boolean = checkFor(Feature.Zve64x)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve64f(): Boolean = checkFor(Feature.Zve64f)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zve64d(): Boolean = checkFor(Feature.Zve64d)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvfh(): Boolean = checkFor(Feature.Zvfh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvfhmin(): Boolean = checkFor(Feature.Zvfhmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvfbfmin(): Boolean = checkFor(Feature.Zvfbfmin)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvfbfwma(): Boolean = checkFor(Feature.Zvfbfwma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvbb(): Boolean = checkFor(Feature.Zvbb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvbc(): Boolean = checkFor(Feature.Zvbc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkb(): Boolean = checkFor(Feature.Zvkb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkg(): Boolean = checkFor(Feature.Zvkg)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkned(): Boolean = checkFor(Feature.Zvkned)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvknha(): Boolean = checkFor(Feature.Zvknha)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvknhb(): Boolean = checkFor(Feature.Zvknhb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvksed(): Boolean = checkFor(Feature.Zvksed)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvksh(): Boolean = checkFor(Feature.Zvksh)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkn(): Boolean = checkFor(Feature.Zvkn)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvknc(): Boolean = checkFor(Feature.Zvknc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkng(): Boolean = checkFor(Feature.Zvkng)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvks(): Boolean = checkFor(Feature.Zvks)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvksc(): Boolean = checkFor(Feature.Zvksc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvksg(): Boolean = checkFor(Feature.Zvksg)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun zvkt(): Boolean = checkFor(Feature.Zvkt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun j(): Boolean = checkFor(Feature.J)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun p(): Boolean = checkFor(Feature.P)

@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.s390x.vector

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16

private object S390xVectorJniBridge {
    init {
        loadNativeLibrary()
    }

    private fun loadNativeLibrary() {
        val osRaw = System.getProperty("os.name", "").lowercase()
        val archRaw = System.getProperty("os.arch", "").lowercase()
        val hostPlatform =
            when {
                osRaw.contains("win") -> "windows"
                osRaw.contains("mac") || osRaw.contains("darwin") -> "macos"
                else -> "linux"
            }
        val hostArch =
            when {
                archRaw == "aarch64" || archRaw == "arm64" -> "aarch64"
                archRaw == "amd64" || archRaw == "x86_64" -> "x86_64"
                else -> archRaw
            }
        val libFileName =
            when (hostPlatform) {
                "windows" -> "kmcore_jni.dll"
                "macos" -> "libkmcore_jni.dylib"
                else -> "libkmcore_jni.so"
            }
        val pathSep = System.getProperty("path.separator", ":")
        val libPathProp = System.getProperty("java.library.path", "")
        for (dirEntry in libPathProp.split(pathSep)) {
            if (dirEntry.isEmpty()) continue
            val candidate = java.io.File(dirEntry, libFileName)
            if (candidate.isFile) {
                System.load(candidate.absolutePath)
                return
            }
        }
        val resourcePath = "io/github/kotlinmania/rustlang/corearch/native/$hostPlatform-$hostArch/$libFileName"
        val stream = S390xVectorJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("kmcore_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchS390xVectorLcbb(a: Long, b: Int): Int
}

public actual fun nearbyintV4f32(a: VectorFloat): VectorFloat =
    VectorFloat()

public actual fun nearbyintV2f64(a: VectorDouble): VectorDouble =
    VectorDouble()

public actual fun roundevenV4f32(a: VectorFloat): VectorFloat =
    VectorFloat()

public actual fun roundevenV2f64(a: VectorDouble): VectorDouble =
    VectorDouble()

public actual fun vsra(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vsrl(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vsl(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vsrab(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vsrlb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vslb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun verimb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar, d: Int): VectorSignedChar =
    VectorSignedChar()

public actual fun verimh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort, d: Int): VectorSignedShort =
    VectorSignedShort()

public actual fun verimf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt, d: Int): VectorSignedInt =
    VectorSignedInt()

public actual fun verimg(a: VectorSignedLongLong, b: VectorSignedLongLong, c: VectorSignedLongLong, d: Int): VectorSignedLongLong =
    VectorSignedLongLong()

public actual fun vperm(a: VectorSignedChar, b: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vsumb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vsumh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vsumgh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedLongLong =
    VectorUnsignedLongLong()

public actual fun vsumgf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong =
    VectorUnsignedLongLong()

public actual fun vsumqf(a: VectorUnsignedInt, b: VectorUnsignedInt): U128 =
    U128()

public actual fun vsumqg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128 =
    U128()

public actual fun vscbiq(a: U128, b: U128): U128 =
    U128()

public actual fun vsbiq(a: U128, b: U128, c: U128): U128 =
    U128()

public actual fun vsbcbiq(a: U128, b: U128, c: U128): U128 =
    U128()

public actual fun vacq(a: U128, b: U128, c: U128): U128 =
    U128()

public actual fun vscbib(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vscbih(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vscbif(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vscbig(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong =
    VectorUnsignedLongLong()

public actual fun vfaeb(a: VectorSignedChar, b: VectorSignedChar, c: Int): VectorSignedChar =
    VectorSignedChar()

public actual fun vfaeh(a: VectorSignedShort, b: VectorSignedShort, c: Int): VectorSignedShort =
    VectorSignedShort()

public actual fun vfaef(a: VectorSignedInt, b: VectorSignedInt, c: Int): VectorSignedInt =
    VectorSignedInt()

public actual fun vfaezb(a: VectorSignedChar, b: VectorSignedChar, c: Int): VectorSignedChar =
    VectorSignedChar()

public actual fun vfaezh(a: VectorSignedShort, b: VectorSignedShort, c: Int): VectorSignedShort =
    VectorSignedShort()

public actual fun vfaezf(a: VectorSignedInt, b: VectorSignedInt, c: Int): VectorSignedInt =
    VectorSignedInt()

public actual fun vfaebs(a: VectorSignedChar, b: VectorSignedChar, c: Int): PackedTuple<VectorSignedChar, Int> =
    null as PackedTuple<VectorSignedChar, Int>

public actual fun vfaehs(a: VectorSignedShort, b: VectorSignedShort, c: Int): PackedTuple<VectorSignedShort, Int> =
    null as PackedTuple<VectorSignedShort, Int>

public actual fun vfaefs(a: VectorSignedInt, b: VectorSignedInt, c: Int): PackedTuple<VectorSignedInt, Int> =
    null as PackedTuple<VectorSignedInt, Int>

public actual fun vfaezbs(a: VectorSignedChar, b: VectorSignedChar, c: Int): PackedTuple<VectorSignedChar, Int> =
    null as PackedTuple<VectorSignedChar, Int>

public actual fun vfaezhs(a: VectorSignedShort, b: VectorSignedShort, c: Int): PackedTuple<VectorSignedShort, Int> =
    null as PackedTuple<VectorSignedShort, Int>

public actual fun vfaezfs(a: VectorSignedInt, b: VectorSignedInt, c: Int): PackedTuple<VectorSignedInt, Int> =
    null as PackedTuple<VectorSignedInt, Int>

public actual fun vll(a: UInt, b: ConstPtr): VectorSignedChar =
    VectorSignedChar()

public actual fun vstl(a: VectorSignedChar, b: UInt, c: MutPtr) {
}

public actual fun vlrl(a: UInt, b: ConstPtr): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vstrl(a: VectorUnsignedChar, b: UInt, c: MutPtr) {
}

public actual fun lcbb(a: ConstPtr, b: UInt): UInt =
    S390xVectorJniBridge.corearchS390xVectorLcbb(a.rawAddress, b.toInt()).toUInt()

public actual fun vlbb(a: ConstPtr, b: UInt): MaybeUninit<VectorSignedChar> =
    MaybeUninit.uninit(VectorSignedChar.typeLayout())

public actual fun vpksh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar =
    VectorSignedChar()

public actual fun vpksf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort =
    VectorSignedShort()

public actual fun vpksg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedInt =
    VectorSignedInt()

public actual fun vpklsh(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vpklsf(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vpklsg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vpkshs(a: VectorSignedShort, b: VectorSignedShort): PackedTuple<VectorSignedChar, Int> =
    null as PackedTuple<VectorSignedChar, Int>

public actual fun vpksfs(a: VectorSignedInt, b: VectorSignedInt): PackedTuple<VectorSignedShort, Int> =
    null as PackedTuple<VectorSignedShort, Int>

public actual fun vpksgs(a: VectorSignedLongLong, b: VectorSignedLongLong): PackedTuple<VectorSignedInt, Int> =
    null as PackedTuple<VectorSignedInt, Int>

public actual fun vpklshs(a: VectorUnsignedShort, b: VectorUnsignedShort): PackedTuple<VectorUnsignedChar, Int> =
    null as PackedTuple<VectorUnsignedChar, Int>

public actual fun vpklsfs(a: VectorUnsignedInt, b: VectorUnsignedInt): PackedTuple<VectorUnsignedShort, Int> =
    null as PackedTuple<VectorUnsignedShort, Int>

public actual fun vpklsgs(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): PackedTuple<VectorUnsignedInt, Int> =
    null as PackedTuple<VectorUnsignedInt, Int>

public actual fun vavgb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vavgh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vavgf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vavgg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong =
    VectorSignedLongLong()

public actual fun vavglb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vavglh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vavglf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vavglg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong =
    VectorUnsignedLongLong()

public actual fun vcksm(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmhb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vmhh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vmhf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vmlhb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vmlhh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vmlhf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmaeb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vmaeh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vmaef(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong =
    VectorSignedLongLong()

public actual fun vmaleb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vmaleh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmalef(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong =
    VectorUnsignedLongLong()

public actual fun vmaob(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vmaoh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vmaof(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong =
    VectorSignedLongLong()

public actual fun vmalob(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vmaloh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmalof(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong =
    VectorUnsignedLongLong()

public actual fun vmahb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vmahh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vmahf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vmalhb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vmalhh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vmalhf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vmalb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar =
    VectorSignedChar()

public actual fun vmalh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    VectorSignedShort()

public actual fun vmalf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt =
    VectorSignedInt()

public actual fun vmallb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vmallh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vmallf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vgfmb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vgfmh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vgfmf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong =
    VectorUnsignedLongLong()

public actual fun vgfmg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128 =
    U128()

public actual fun vgfmab(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vgfmah(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vgfmaf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong =
    VectorUnsignedLongLong()

public actual fun vgfmag(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: U128): U128 =
    U128()

public actual fun vbperm(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedLongLong =
    VectorUnsignedLongLong()

public actual fun vftcisb(a: VectorFloat, b: UInt): PackedTuple<VectorBoolInt, Int> =
    null as PackedTuple<VectorBoolInt, Int>

public actual fun vftcidb(a: VectorDouble, b: UInt): PackedTuple<VectorBoolLongLong, Int> =
    null as PackedTuple<VectorBoolLongLong, Int>

public actual fun vtm(a: I8x16, b: I8x16): Int =
    0

public actual fun vstrsb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    null as PackedTuple<VectorUnsignedChar, Int>

public actual fun vstrsh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    null as PackedTuple<VectorUnsignedChar, Int>

public actual fun vstrsf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    null as PackedTuple<VectorUnsignedChar, Int>

public actual fun vstrszb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    null as PackedTuple<VectorUnsignedChar, Int>

public actual fun vstrszh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    null as PackedTuple<VectorUnsignedChar, Int>

public actual fun vstrszf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    null as PackedTuple<VectorUnsignedChar, Int>

public actual fun vistrb(a: VectorUnsignedChar): VectorUnsignedChar =
    VectorUnsignedChar()

public actual fun vistrh(a: VectorUnsignedShort): VectorUnsignedShort =
    VectorUnsignedShort()

public actual fun vistrf(a: VectorUnsignedInt): VectorUnsignedInt =
    VectorUnsignedInt()

public actual fun vistrbs(a: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    null as PackedTuple<VectorUnsignedChar, Int>

public actual fun vistrhs(a: VectorUnsignedShort): PackedTuple<VectorUnsignedShort, Int> =
    null as PackedTuple<VectorUnsignedShort, Int>

public actual fun vistrfs(a: VectorUnsignedInt): PackedTuple<VectorUnsignedInt, Int> =
    null as PackedTuple<VectorUnsignedInt, Int>

public actual fun vmslg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: U128, d: UInt): U128 =
    U128()

public actual fun vstrcb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): VectorBoolChar =
    VectorBoolChar()

public actual fun vstrch(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): VectorBoolShort =
    VectorBoolShort()

public actual fun vstrcf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): VectorBoolInt =
    VectorBoolInt()

public actual fun vstrcbs(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): PackedTuple<VectorBoolChar, Int> =
    null as PackedTuple<VectorBoolChar, Int>

public actual fun vstrchs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): PackedTuple<VectorBoolShort, Int> =
    null as PackedTuple<VectorBoolShort, Int>

public actual fun vstrcfs(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): PackedTuple<VectorBoolInt, Int> =
    null as PackedTuple<VectorBoolInt, Int>

public actual fun vstrczb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): VectorBoolChar =
    VectorBoolChar()

public actual fun vstrczh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): VectorBoolShort =
    VectorBoolShort()

public actual fun vstrczf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): VectorBoolInt =
    VectorBoolInt()

public actual fun vstrczbs(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): PackedTuple<VectorBoolChar, Int> =
    null as PackedTuple<VectorBoolChar, Int>

public actual fun vstrczhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): PackedTuple<VectorBoolShort, Int> =
    null as PackedTuple<VectorBoolShort, Int>

public actual fun vstrczfs(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): PackedTuple<VectorBoolInt, Int> =
    null as PackedTuple<VectorBoolInt, Int>

public actual fun vfeeb(a: I8x16, b: I8x16): I8x16 =
    null as I8x16

public actual fun vfeeh(a: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun vfeef(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun vfeezb(a: I8x16, b: I8x16): I8x16 =
    null as I8x16

public actual fun vfeezh(a: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun vfeezf(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun vfeebs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    null as PackedTuple<I8x16, Int>

public actual fun vfeehs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    null as PackedTuple<I16x8, Int>

public actual fun vfeefs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    null as PackedTuple<I32x4, Int>

public actual fun vfeezbs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    null as PackedTuple<I8x16, Int>

public actual fun vfeezhs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    null as PackedTuple<I16x8, Int>

public actual fun vfeezfs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    null as PackedTuple<I32x4, Int>

public actual fun vfeneb(a: I8x16, b: I8x16): I8x16 =
    null as I8x16

public actual fun vfeneh(a: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun vfenef(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun vfenezb(a: I8x16, b: I8x16): I8x16 =
    null as I8x16

public actual fun vfenezh(a: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun vfenezf(a: I32x4, b: I32x4): I32x4 =
    null as I32x4

public actual fun vfenebs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    null as PackedTuple<I8x16, Int>

public actual fun vfenehs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    null as PackedTuple<I16x8, Int>

public actual fun vfenefs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    null as PackedTuple<I32x4, Int>

public actual fun vfenezbs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    null as PackedTuple<I8x16, Int>

public actual fun vfenezhs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    null as PackedTuple<I16x8, Int>

public actual fun vfenezfs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    null as PackedTuple<I32x4, Int>

public actual fun vclfnhs(a: VectorSignedShort, immarg: Int): VectorFloat =
    VectorFloat()

public actual fun vclfnls(a: VectorSignedShort, immarg: Int): VectorFloat =
    VectorFloat()

public actual fun vcfn(a: VectorSignedShort, immarg: Int): VectorSignedShort =
    VectorSignedShort()

public actual fun vcnf(a: VectorSignedShort, immarg: Int): VectorSignedShort =
    VectorSignedShort()

public actual fun vcrnfs(a: VectorFloat, b: VectorFloat, immarg: Int): VectorSignedShort =
    VectorSignedShort()

public actual fun minnumV4f32(a: VectorFloat, b: VectorFloat): VectorFloat =
    VectorFloat()

public actual fun minnumV2f64(a: VectorDouble, b: VectorDouble): VectorDouble =
    VectorDouble()

public actual fun maxnumV4f32(a: VectorFloat, b: VectorFloat): VectorFloat =
    VectorFloat()

public actual fun maxnumV2f64(a: VectorDouble, b: VectorDouble): VectorDouble =
    VectorDouble()

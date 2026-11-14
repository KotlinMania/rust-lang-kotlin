// port-lint: tests math/rem_pio2.rs
package io.github.kotlinmania.rustlang.libm.math.rempio2

public class RemPio2InlineTests {
    // Note(correctness): inaccurate results on i586
    @kotlin.test.Test
    public fun testNearPi() {
        val arg = 3.141592025756836
        val arg1 = readVolatile(arg)
        check(remPio2(arg1) == Triple(2, -6.278329573009626e-7, -2.1125998133974653e-23))
        val arg2 = 3.141592033207416
        val arg3 = readVolatile(arg2)
        check(remPio2(arg3) == Triple(2, -6.20382377148128e-7, -2.1125998133974653e-23))
        val arg4 = 3.141592144966125
        val arg5 = readVolatile(arg4)
        check(remPio2(arg5) == Triple(2, -5.086236681942706e-7, -2.1125998133974653e-23))
        val arg6 = 3.141592979431152
        val arg7 = readVolatile(arg6)
        check(remPio2(arg7) == Triple(2, 3.2584135866119817e-7, -2.1125998133974653e-23))
    }

    @kotlin.test.Test
    public fun testOverflowB9b847() {
        remPio2(-3054214.5490637687)
    }

    @kotlin.test.Test
    public fun testOverflow4747b9() {
        remPio2(917340800458.2274)
    }
}

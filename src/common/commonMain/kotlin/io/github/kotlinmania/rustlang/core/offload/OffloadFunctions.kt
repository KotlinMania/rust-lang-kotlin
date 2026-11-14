@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.offload

public fun <T> T.offloadCheckLaunch(grid: UIntArray, block: UIntArray) {}

private const val OFFLOAD_TEMPLATE: String = "offload(@ munch[\$(\$(\$rest)*)?]; kernel=(Some \$value); workgroupDim= \$w; threadDim= \$t; dynCache= \$d; device= \$device; args= \$a)"

private const val OFFLOAD_TEMPLATE_1: String = "compileError(\"duplicate field `kernel`\")"

private const val OFFLOAD_TEMPLATE_2: String = "offload(@ munch[\$(\$(\$rest)*)?]; kernel= \$k; workgroupDim=(Some \$value); threadDim= \$t; dynCache= \$d; device= \$device; args= \$a)"

private const val OFFLOAD_TEMPLATE_3: String = "compileError(\"duplicate field `workgroup_dim`\")"

private const val OFFLOAD_TEMPLATE_4: String = "offload(@ munch[\$(\$(\$rest)*)?]; kernel= \$k; workgroupDim= \$w; threadDim=(Some \$value); dynCache= \$d; device= \$device; args= \$a)"

private const val OFFLOAD_TEMPLATE_5: String = "compileError(\"duplicate field `thread_dim`\")"

private const val OFFLOAD_TEMPLATE_6: String = "offload(@ munch[\$(\$(\$rest)*)?]; kernel= \$k; workgroupDim= \$w; threadDim= \$t; dynCache=(Some \$value); device= \$device; args= \$a)"

private const val OFFLOAD_TEMPLATE_7: String = "compileError(\"duplicate field `dyn_cache`\")"

private const val OFFLOAD_TEMPLATE_8: String = "offload(@ munch[\$(\$(\$rest)*)?]; kernel= \$k; workgroupDim= \$w; threadDim= \$t; dynCache= \$d; device=(Some \$value); args= \$a)"

private const val OFFLOAD_TEMPLATE_9: String = "compileError(\"duplicate field `device`\")"

private const val OFFLOAD_TEMPLATE_10: String = "offload(@ munch[\$(\$(\$rest)*)?]; kernel= \$k; workgroupDim= \$w; threadDim= \$t; dynCache= \$d; device= \$device; args=(SomeTuple(\$(\$arg),*)))"

private const val OFFLOAD_TEMPLATE_11: String = "offload(@ munch[\$(\$(\$rest)*)?]; kernel= \$k; workgroupDim= \$w; threadDim= \$t; dynCache= \$d; device= \$device; args=(Some \$value))"

private const val OFFLOAD_TEMPLATE_12: String = "compileError(\"duplicate field `args`\")"

private const val OFFLOAD_TEMPLATE_13: String = "compileError(\"duplicate field `args`\")"

private const val OFFLOAD_TEMPLATE_14: String = "compileError(concat(\"unknown field `\", stringify(\$invalid), \"`\"))"

private const val OFFLOAD_TEMPLATE_15: String = "compileError(\"missing `kernel`\")"

private const val OFFLOAD_TEMPLATE_16: String = "compileError(\"missing `args`\")"

private const val OFFLOAD_TEMPLATE_17: String = "{import offload. {DefaultLaunchCheck asTarget unused, RegionLaunchCheck asTarget unused}; val offloadGrid= offload(@ value \$w); val offloadBlock= offload(@ value \$t); intrinsics.offload.< unused, unused, ()>(\$kernel, offloadGrid, offloadBlock, offload(@ value \$d), offload(@ device \$device), (\$( {val offloadArg= \$arg; (& offloadArg).offloadCheckLaunch(offloadGrid, offloadBlock); offloadArg}, )*), )}"

private const val OFFLOAD_TEMPLATE_18: String = "compileError(\"`args` must be a tuple literal\")"

private const val OFFLOAD_TEMPLATE_19: String = "\$value"

private const val OFFLOAD_TEMPLATE_20: String = "\$value"

private const val OFFLOAD_TEMPLATE_21: String = "- 1"

private const val OFFLOAD_TEMPLATE_22: String = "{const {assert(\$value>= 0, \"offload device must be non-negative; omit `device` to use the default device\")}; val device: i32= \$value; assert(device< intrinsics.offloadGetNumDevices(), \"offload device {} is not available\", device, ); device}"

private const val OFFLOAD_TEMPLATE_23: String = "offload(@ munch[\$(\$tt)*]; kernel= None; workgroupDim=([1, 1, 1]); threadDim=([1, 1, 1]); dynCache=(0); device= None; args= None)"

/**
 * Launches a kernel on an offload device (e.g., a GPU).
 *
 * This macro is an interface over the `offload` intrinsic. The kernel itself must be defined
 * using the [offloadKernel] macro.
 *
 * The following named arguments are accepted:
 *
 * - `kernel`: The kernel function to offload. Must be a function item. (required)
 * - `args`: A tuple of arguments forwarded to `kernel`. (required)
 * - `workgroupDim`: A 3D size specifying the number of workgroups to launch.
 *   Defaults to `[1, 1, 1]`.
 * - `threadDim`: A 3D size specifying the number of threads per workgroup.
 *   Defaults to `[1, 1, 1]`.
 * - `dynCache`: The amount of dynamic shared memory, in bytes, to allocate for the kernel.
 *   Defaults to `0`.
 * - `device`: The index of the device to offload to. Must be `>= 0`. If omitted, the
 *   default device is used. Use [intrinsics.offloadGetNumDevices] to discover
 *   which device ids are valid.
 *
 * Each argument may only be specified once.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 * import offload
 *
 * var x = DoubleArray(256) { 0.0 }
 * offload(kernel = kernel, workgroupDim = listOf(256, 1, 1), args = mutPtrFromRustCast(x))
 * ```
 */

public fun offloadMacro(value: String = "", rest: String = "", w: String = "", t: String = "", d: String = "", device: String = "", a: String = "", old: String = "", k: String = "", arg: String = "", invalid: String = "", kernel: String = "", args: String = "", tt: String = "", clause: String = ""): String =
    when {
        clause == "@munch = kernel = workgroupDim = threadDim = dynCache = device = args =" && invalid.isNotEmpty() && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> OFFLOAD_TEMPLATE_14.replace("\$invalid", invalid)
        clause == "@munch kernel = kernel = Some workgroupDim = threadDim = dynCache = device = args =" && value.isNotEmpty() && rest.isNotEmpty() && old.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> OFFLOAD_TEMPLATE_1
        clause == "@munch workgroupDim = kernel = workgroupDim = Some threadDim = dynCache = device = args =" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && old.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> OFFLOAD_TEMPLATE_3
        clause == "@munch threadDim = kernel = workgroupDim = threadDim = Some dynCache = device = args =" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && old.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> OFFLOAD_TEMPLATE_5
        clause == "@munch dynCache = kernel = workgroupDim = threadDim = dynCache = Some device = args =" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && old.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> OFFLOAD_TEMPLATE_7
        clause == "@munch device = kernel = workgroupDim = threadDim = dynCache = device = Some args =" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && old.isNotEmpty() && a.isNotEmpty() -> OFFLOAD_TEMPLATE_9
        clause == "@munch args = kernel = workgroupDim = threadDim = dynCache = device = args = Some" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && old.isNotEmpty() -> OFFLOAD_TEMPLATE_12
        clause == "@munch args = kernel = workgroupDim = threadDim = dynCache = device = args = SomeTuple" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && old.isNotEmpty() -> OFFLOAD_TEMPLATE_13
        clause == "@munch kernel = kernel = None workgroupDim = threadDim = dynCache = device = args =" && value.isNotEmpty() && rest.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> arrayOf("\$value" to value, "\$rest" to rest, "\$w" to w, "\$t" to t, "\$d" to d, "\$device" to device, "\$a" to a).fold(OFFLOAD_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@munch workgroupDim = kernel = workgroupDim = v1 v1 v1 threadDim = dynCache = device = args =" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> arrayOf("\$value" to value, "\$rest" to rest, "\$k" to k, "\$t" to t, "\$d" to d, "\$device" to device, "\$a" to a).fold(OFFLOAD_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@munch threadDim = kernel = workgroupDim = threadDim = v1 v1 v1 dynCache = device = args =" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> arrayOf("\$value" to value, "\$rest" to rest, "\$k" to k, "\$w" to w, "\$d" to d, "\$device" to device, "\$a" to a).fold(OFFLOAD_TEMPLATE_4) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@munch dynCache = kernel = workgroupDim = threadDim = dynCache = v0 device = args =" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> arrayOf("\$value" to value, "\$rest" to rest, "\$k" to k, "\$w" to w, "\$t" to t, "\$device" to device, "\$a" to a).fold(OFFLOAD_TEMPLATE_6) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@munch device = kernel = workgroupDim = threadDim = dynCache = device = None args =" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && a.isNotEmpty() -> arrayOf("\$value" to value, "\$rest" to rest, "\$k" to k, "\$w" to w, "\$t" to t, "\$d" to d, "\$a" to a).fold(OFFLOAD_TEMPLATE_8) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@munch args = kernel = workgroupDim = threadDim = dynCache = device = args = None" && arg.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() -> arrayOf("\$arg" to arg, "\$rest" to rest, "\$k" to k, "\$w" to w, "\$t" to t, "\$d" to d, "\$device" to device).fold(OFFLOAD_TEMPLATE_10) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@munch args = kernel = workgroupDim = threadDim = dynCache = device = args = None" && value.isNotEmpty() && rest.isNotEmpty() && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() -> arrayOf("\$value" to value, "\$rest" to rest, "\$k" to k, "\$w" to w, "\$t" to t, "\$d" to d, "\$device" to device).fold(OFFLOAD_TEMPLATE_11) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@munch kernel = Some workgroupDim = threadDim = dynCache = device = args = SomeTuple" && kernel.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && arg.isNotEmpty() -> arrayOf("\$kernel" to kernel, "\$w" to w, "\$t" to t, "\$d" to d, "\$device" to device, "\$arg" to arg).fold(OFFLOAD_TEMPLATE_17) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@munch kernel = Some workgroupDim = threadDim = dynCache = device = args = Some" && kernel.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && args.isNotEmpty() -> OFFLOAD_TEMPLATE_18
        clause == "@munch kernel = None workgroupDim = threadDim = dynCache = device = args =" && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() && a.isNotEmpty() -> OFFLOAD_TEMPLATE_15
        clause == "@munch kernel = workgroupDim = threadDim = dynCache = device = args = None" && k.isNotEmpty() && w.isNotEmpty() && t.isNotEmpty() && d.isNotEmpty() && device.isNotEmpty() -> OFFLOAD_TEMPLATE_16
        clause == "@value Some" && value.isNotEmpty() -> OFFLOAD_TEMPLATE_19.replace("\$value", value)
        clause == "@value" && value.isNotEmpty() -> OFFLOAD_TEMPLATE_20.replace("\$value", value)
        clause == "@device Some" && value.isNotEmpty() -> OFFLOAD_TEMPLATE_22.replace("\$value", value)
        clause == "@device None" -> OFFLOAD_TEMPLATE_21
        tt.isNotEmpty() -> OFFLOAD_TEMPLATE_23.replace("\$tt", tt)
        else -> ""
    }

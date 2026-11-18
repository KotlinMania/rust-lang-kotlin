// port-lint: source sys/thread/unix.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.thread.unix

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.dropguard.DropGuard
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.checkedNextMultipleOf
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.CPU_COUNT
import io.github.kotlinmania.rustlang.libc.clockNanosleep
import io.github.kotlinmania.rustlang.libc.prctl
import io.github.kotlinmania.rustlang.libc.pthreadAttrDestroy
import io.github.kotlinmania.rustlang.libc.pthreadAttrInit
import io.github.kotlinmania.rustlang.libc.pthreadAttrSetstacksize
import io.github.kotlinmania.rustlang.libc.pthreadCreate
import io.github.kotlinmania.rustlang.libc.pthreadDetach
import io.github.kotlinmania.rustlang.libc.pthreadJoin
import io.github.kotlinmania.rustlang.libc.schedGetaffinity
import io.github.kotlinmania.rustlang.libc.schedYield
import io.github.kotlinmania.rustlang.libc.sysconf
import io.github.kotlinmania.rustlang.libc.timespec.default
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.CpuSetT
import io.github.kotlinmania.rustlang.libc.wasi.EINTR
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.PTHREAD_STACK_MIN
import io.github.kotlinmania.rustlang.libc.wasi.PthreadT
import io.github.kotlinmania.rustlang.libc.wasi.SC_NPROCESSORS_ONLN
import io.github.kotlinmania.rustlang.libc.wasi.TIMER_ABSTIME
import io.github.kotlinmania.rustlang.libc.wasi.TimeT
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.fs.exists
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.max
import io.github.kotlinmania.rustlang.std.min
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.helpers.copaque.COpaque
import io.github.kotlinmania.rustlang.std.sys.pal.Conf
import io.github.kotlinmania.rustlang.std.sys.pal.weak.syscall
import io.github.kotlinmania.rustlang.std.sys.weak.dlsym
import io.github.kotlinmania.rustlang.std.thread.lifecycle.ThreadInit
import io.github.kotlinmania.rustlang.std.time.Instant

public fun interface FnCallback {
    public operator fun invoke(arg0: Path): Pair<Any?, Path>?
}

public val DEFAULT_MIN_STACK_SIZE: Int = 2 * 1024 * 1024

// 0 indicates that the stack size configured in the ESP-IDF/NuttX menuconfig system should be used
public open class Thread(
    public var id: PthreadT,
) {
    // Some platforms may have pthreadT as a pointer in which case we still want
    // a thread to be Send/Sync

    public open fun join() {
        val id = this.intoId()
        val ret = pthreadJoin(id, MutPtr(0L))
        check(ret == 0) { "failed to join thread: ${Error.fromRawOsError(ret)}" }
    }

    public open fun id(): PthreadT = this.id

    public open fun intoId(): PthreadT = this.id

    public open fun drop() {
        val ret = pthreadDetach(this.id)
        check(ret == 0)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Thread> =
            object : TypeLayout<Thread> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Thread =
                    Thread(
                        (PrimitiveLayouts.I64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Thread) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.id)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Thread =
                    Thread(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Thread) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.id)
                }
            }

        // unchecked: see thread.Builder.spawnUnchecked for safety requirements
        // even without panics, this helps for Miri backtraces
        public fun new(stack: Int, init: ThreadInit): Result<Thread, Error> {
            fun threadStart(data: MutPtr): MutPtr {
                run {
                    // SAFETY: we are simply recreating the box that was leaked earlier.
                    val init1 = Box.fromRaw(MutPtr(data.rawAddress, data.rawMeta))
                    val rustStart = init1.init()
                    // Now that the thread information is set, set up our stack
                    // overflow handler.
                    rustStart()
                }
                return MutPtr(0L)
            }
            val data = init
            val attr = pin(COpaque.uninit())
            // Note(pin-ergonomics): remove the next line.
            val attr1 = attr.intoRef()
            check(pthreadAttrInit(attr1.get()) == 0)
            val attr2 = DropGuard.new(attr1, { attr2 -> check(pthreadAttrDestroy(attr2.get()) == 0) })
            run {
                val stackSize = max(stack, minStackSize(attr2.get()))
                when (val matchVal = pthreadAttrSetstacksize(attr2.get(), stackSize)) {
                    0 -> {}
                    else -> {
                        val n = matchVal
                        check(n == EINVAL)
                        // EINVAL means |stackSize| is either too small or not a
                        // multiple of the system page size. Because it's definitely
                        // >= PTHREAD_STACK_MIN, it must be an alignment issue.
                        // Round up to the nearest page and try again.
                        val pageSize = Conf.pageSize()
                        val stackSize1 = stackSize.checkedNextMultipleOf(pageSize)
                        // Some libc implementations, e.g. musl, place an upper bound
                        // on the stack size, in which case we can only gracefully return
                        // an error here.
                        if (stackSize1.isNoneOr({ stackSize2 ->
                            pthreadAttrSetstacksize(attr2.get(), stackSize2) != 0
                        })) {
                            return Result.Err<Thread, Error>(constError(ErrorKind.InvalidInput, "invalid stack size"))
                        }
                    }
                }
            }
            val data1 = Box.intoRaw(data)
            val localStorageL107C8 = AddressableLocal(0L, UnresolvedTypeLayout<PthreadT>())
            val native by localStorageL107C8
            val ret = pthreadCreate(RustMutableReference(localStorageL107C8.asMutPtr(), localStorageL107C8.layout), attr2.get(), ::threadStart, mutPtrFromRustCast(data1))
            return if (ret == 0) {
                Result.Ok<Thread, Error>(Thread(id = native))
            } else {
                // The thread failed to start and as a result `data` was not consumed.
                // Therefore, it is safe to reconstruct the box so that it gets deallocated.
                (Box.fromRaw(data1)).let { }
                Result.Err<Thread, Error>(Error.fromRawOsError(ret))
            }
        }
    }
}

public fun availableParallelism(): Result<NonZero<Int, NonZeroUsizeInner>, Error> {
    var quota = Int.MAX_VALUE
    run {
        quota = quota().max(1)
        val localStorageL178C16 = AddressableLocal(CpuSetT(), UnresolvedTypeLayout<CpuSetT>())
        val set by localStorageL178C16
        if (schedGetaffinity(0, sizeOf<CpuSetT>(typeLayoutT = UnresolvedTypeLayout<CpuSetT>()), RustMutableReference(localStorageL178C16.asMutPtr(), localStorageL178C16.layout)) == 0) {
            val count = CPU_COUNT(set).toInt()
            val count1 = count.min(quota)
            // According to schedGetaffinity's API it should always be non-zero, but
            // some old MIPS kernels were buggy and zero-initialized the mask if
            // none was explicitly set.
            // In that case we use the sysconf fallback.
            when (val matchVal = NonZero.new<NonZeroUsizeInner, Int>(count1)) {
                null -> {}
                else -> {
                    val count2 = matchVal
                    return Result.Ok<NonZero<Int, NonZeroUsizeInner>, Error>(count2)
                }
            }
        }
    }
    return when (val matchVal1 = sysconf(SC_NPROCESSORS_ONLN)) {
        -1 -> Result.Err<NonZero<Int, NonZeroUsizeInner>, Error>(Error.lastOsError())
        0 -> Result.Err<NonZero<Int, NonZeroUsizeInner>, Error>(Error.UNKNOWN_THREAD_COUNT)
        else -> {
            val cpus = matchVal1
            val count3 = cpus.toInt()
            // Cover the unusual situation where we were able to get the quota but not the affinity mask
            val count4 = count3.min(quota)
            Result.Ok<NonZero<Int, NonZeroUsizeInner>, Error>(NonZero.newUnchecked<NonZeroUsizeInner, Int>(count4))
        }
    }
}

public fun currentOsId(): ULong? {
    // `libc.gettid` is only available on glibc 2.30+, but the syscall is available
    // since Linux 2.4.11.
    syscall(gettid, libc, pidT)
    // SAFETY: FFI call with no preconditions.
    val id: PidT = gettid()
    return id.toULong()
}

public fun truncateCstr(cstr: CStr, maxWithNul: Int = 0): UByteArray {
    val result = MutableList(maxWithNul) { 0 }
    for ((src, dst) in (cstr.toBytes().iter().zip(result.subList(0, maxWithNul - 1)))) {
        dst = src.toUByte()
    }
    return result
}

public fun setName(name: CStr) {
    val PR_SET_NAME: CInt = 15
    run {
        val res = prctl(PR_SET_NAME, name.asPtr(), 0.toULong(), 0.toULong(), 0.toULong())
        // We have no good way of propagating errors here, but in debug-builds let's check that this actually worked.
        check(res == 0)
    }
}

public fun sleep(dur: Duration) {
    // POSIX specifies that `nanosleep` uses CLOCK_REALTIME, but is not
    // affected by clock adjustments. The timing of `sleep` however should
    // be tied to `Instant` where possible. Thus, we use `clockNanosleep`
    // with a relative time interval instead, which allows explicitly
    // specifying the clock.
    //
    // In practice, most systems (like e.g. Linux) actually use
    // CLOCK_MONOTONIC for `nanosleep` anyway, but others like FreeBSD don't
    // so it's better to be safe.
    //
    // wasi-libc prior to WebAssembly/wasi-libc#696 has a broken implementation
    // of `nanosleep` which used `CLOCK_REALTIME` even though it is unsupported
    // on WASIp2. Using `clockNanosleep` directly bypasses the issue.
    fun nanosleep(rqtp: ConstPtr, rmtp: MutPtr): CInt = clockNanosleep(io.github.kotlinmania.rustlang.std.sys.time.unix.Instant.CLOCK_ID, 0, rqtp, rmtp)
    var secs = dur.asSecs()
    var nsecs = dur.subsecNanos()
    // If we're awoken with a signal then the return value will be -1 and
    // nanosleep will fill in `ts` with the remaining time.
    while (secs > 0 || nsecs > 0) {
        val localStorageL610C12 = AddressableLocal(default(), UnresolvedTypeLayout<Any?>())
        val ts by localStorageL610C12
        ts!!.tvSec = minOf(TimeT.MAX_VALUE.toULong(), secs).toLong()
        ts.tvNsec = nsecs
        secs -= ts.tvSec.toULong()
        val tsPtr = localStorageL610C12.asMutPtr()
        val r = nanosleep((tsPtr).asConstPtr(), tsPtr)
        if (r != 0) {
            check(r == EINTR)
            secs += ts.tvSec.toULong()
            nsecs = ts.tvNsec
        } else {
            nsecs = 0
        }
    }
}

// Any unix that has clockNanosleep
// If this list changes update the MIRI chockNanosleep shim
public fun sleepUntil(deadline: Instant) {
    val timespec = deadline.intoInner().intoTimespec()
    if (timespec.tvSec < 0L) {
        // `clockNanosleep` fails with EINVAL if
        // > The tp argument to clockSettime() is outside the range for the
        // > given clock ID.
        //
        // This specification allows *any* clock range, which means we'd
        // theoretically have to detect whether the time point is in the
        // future (and block indefinitely) or the past (and return immediately)
        // when encountering `EINVAL`. But since all existing implementations
        // interpret this as saying that negative `tvSec` values are unsupported,
        // we can just test that and return – given that POSIX specifies that
        // `CLOCK_MONOTONIC` measures the time "since an unspecified amount
        // in the past" negative values are definitely in the past. If you
        // observe any platform returning `EINVAL` for more cases, please
        // file a bug; we'd need to  add logic handling `EINVAL` when it
        // occurs.
        return
    }
    val ts = timespec.toTimespec() ?: run {
        // The deadline is further in the future then can be passed to
        // clockNanosleep. We have to use Self.sleep instead. This might
        // happen on 32 bit platforms, especially closer to 2038.
        val now = Instant.now()
        when (val matchVal = deadline.checkedDurationSince(now)) {
            null -> {}
            else -> {
                val delay = matchVal
                sleep(delay)
            }
        }
        return
    }
    while (true) {
        val res = clockNanosleep(io.github.kotlinmania.rustlang.std.sys.time.unix.Instant.CLOCK_ID, TIMER_ABSTIME, ts, MutPtr(0L))
        if (res == 0) {
            break
        } else {
            check(res == EINTR)
        }
    }
}

public fun yieldNow() {
    val ret = schedYield()
    check(ret == 0)
}

public sealed class Cgroup {
    public data object V1 : Cgroup()
    public data object V2 : Cgroup()

    public fun rustEquals(other: Cgroup): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        V1 -> 0
        V2 -> 1
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

/**
 * Returns cgroup CPU quota in core-equivalents, rounded down or usize.Max if the quota cannot
 * be determined or is not set.
 */
public fun quota(): Int {
    var quota = Int.MAX_VALUE
    if (false) {
        // Attempting to open a file fails under default flags due to isolation.
        // And Miri does not have parallelism anyway.
        return quota
    }
    val unused: Unit? =
        run {
            val buf = ArrayList(128)
            // find our place in the cgroup hierarchy
            when (val tryResult = when (val tryResult = File.open("/proc/self/cgroup").ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }.readToEnd(buf).ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
            val (cgroupPath, version) =
                when (val tryResult = buf.split({ c -> c == 10u }).fold(null, closure@{ previous, line ->
                    var fields = line.splitn(3, { c -> c == 58u })
                    // 2nd field is a list of controllers for v1 or empty for v2
                    val version =
                        run {
                            val matchVal = fields.nth(1)
                            when {
                                matchVal != null && matchVal == "".encodeToByteArray() -> Cgroup.V2
                                matchVal != null && fromUtf8(matchVal).isOkAnd({ c -> c.split(',').any({ c1 -> c1 == "cpu" }) }) -> {
                                    val controllers = matchVal
                                    Cgroup.V1
                                }
                                else -> { return@closure previous }
                            }
                        }
                    // already-found v1 trumps v2 since it explicitly specifies its controllers
                    if (previous != null && version == Cgroup.V2) {
                        return@closure previous
                    }
                    val path = when (val tryResult = fields.last()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()) }
                    // skip leading slash
                    Pair(path[1 until path.size].toOwned(), version)
                })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
            val cgroupPath1 = PathBuf.from(OsString.fromVec(cgroupPath))
            quota = when (version) {
                Cgroup.V1 -> quotaV1(cgroupPath1)
                Cgroup.V2 -> quotaV2(cgroupPath1)
            }
        }
    return quota
}

public fun quotaV2(groupPath: PathBuf): Int {
    var quota = Int.MAX_VALUE
    val path = PathBuf.withCapacity(128)
    var readBuf = ""
    // standard mount location defined in file-hierarchy(7) manpage
    val cgroupMount = "/sys/fs/cgroup"
    path.push(cgroupMount)
    path.push(groupPath)
    path.push("cgroup.controllers")
    // skip if we're not looking at cgroup2
    if (run {
        val matchVal = exists(path)
        when {
            matchVal is Result.Err<*, *> -> true
            matchVal is Result.Ok<*, *> && matchVal.okValue() == false -> {
                val okValueVal = matchVal.okValue()
                true
            }
            else -> false
        }
    }) {
        return Int.MAX_VALUE
    }
    path.pop()
    val unused: Unit? =
        while (path.startsWith(cgroupMount)) {
            path.push("cpu.max")
            readBuf = ""
            if (File.open(path).andThen({ f -> f.readToString(readBuf) }).isOk()) {
                val rawQuota = when (val tryResult = readBuf.lines().next()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                val rawQuota1 = rawQuota.split(' ')
                val limit = when (val tryResult = rawQuota1.next()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                val period = when (val tryResult = rawQuota1.next()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }
                run {
                    val matchVal1 = Pair((limit.toIntOrNull()?.let { Result.Ok<Int, String>(it) } ?: Result.Err<Int, String>("failed to parse Int")), (period.toIntOrNull()?.let { Result.Ok<Int, String>(it) } ?: Result.Err<Int, String>("failed to parse Int")))
                    when {
                        matchVal1.first is Result.Ok<*, *> && matchVal1.second is Result.Ok<*, *> && period > 0 -> {
                            val limit1 = (matchVal1.first as Result.Ok<*, *>).okValue()
                            val period1 = (matchVal1.second as Result.Ok<*, *>).okValue()
                            quota = quota.min(limit1 / period1)
                        }
                        else -> {}
                    }
                }
            }
            path.pop()
            // pop filename
            path.pop()
            // pop dir
        }
    return quota
}

public fun quotaV1(groupPath: PathBuf): Int {
    var quota = Int.MAX_VALUE
    val path = PathBuf.withCapacity(128)
    var readBuf = ""
    // Hardcode commonly used locations mentioned in the cgroups(7) manpage
    // if that doesn't work scan mountinfo and adjust `groupPath` for bind-mounts
    val mounts: List<FnCallback> = listOf({ p -> Pair("/sys/fs/cgroup/cpu", p) }, { p -> Pair("/sys/fs/cgroup/cpu,cpuacct", p) }, ::findMountpoint)
    for (mount in mounts) {
        val (mount1, groupPath1) = mount(groupPath) ?: continue
        path.clear()
        path.push(mount1.asRef())
        path.push(groupPath1)
        // skip if we guessed the mount incorrectly
        if (run {
            val matchVal = exists(path)
            when {
                matchVal is Result.Err<*, *> -> true
                matchVal is Result.Ok<*, *> && matchVal.okValue() == false -> {
                    val okValueVal = matchVal.okValue()
                    true
                }
                else -> false
            }
        }) {
            continue
        }
        while (path.startsWith(mount1.asRef())) {
            var parseFile =
                closure1@{ name ->
                    path.push(name)
                    readBuf = ""
                    val f = File.open(path)
                    path.pop()
                    // restore buffer before any early returns
                    when (val tryResult = when (val tryResult = f.ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()) }.readToString(readBuf).ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()) }
                    val parsed = when (val tryResult = (readBuf.trim().toIntOrNull()?.let { Result.Ok<Int, String>(it) } ?: Result.Err<Int, String>("failed to parse Int")).ok()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()) }
                    parsed
                }
            val limit = parseFile("cpu.cfs_quota_us")
            val period = parseFile("cpu.cfs_period_us")
            run {
                val matchVal1 = Pair(limit, period)
                when {
                    matchVal1.first != null && matchVal1.second != null && period > 0 -> {
                        val limit1 = matchVal1.first
                        val period1 = matchVal1.second
                        quota = quota.min(limit1 / period1)
                    }
                    else -> {}
                }
            }
            path.pop()
        }
        // we passed the tryExists above so we should have traversed the correct hierarchy
        // when reaching this line
        break
    }
    return quota
}

/**
 * Scan mountinfo for cgroup v1 mountpoint with a cpu controller
 *
 * If the cgroupfs is a bind mount then `groupPath` is adjusted to skip
 * over the already-included prefix
 */
public fun findMountpoint(groupPath: Path): Pair<String, Path>? {
    val reader = (File.openBuffered("/proc/self/mountinfo").ok() ?: return null)
    var line = ""
    while (true) {
        line = ""
        if ((reader.readLine(line).ok() ?: return null) == 0) {
            break
        }
        val line1 = line.trim()
        val items = line1.split(' ')
        val subPath = (items.nth(3) ?: return null)
        val mountPoint = (items.next() ?: return null)
        val mountOpts = (items.nextBack() ?: return null)
        val filesystemType = (items.nthBack(1) ?: return null)
        if (filesystemType != "cgroup" || !mountOpts.split(',').any({ opt -> opt == "cpu" })) {
            // not a cgroup / not a cpu-controller
            continue
        }
        val subPath1 = (Path.new(subPath).takeIf { it.startsWith("/") }?.removePrefix("/").ok() ?: return null)
        if (!groupPath.startsWith(subPath1)) {
            // this is a bind-mount and the bound subdirectory
            // does not contain the cgroup this process belongs to
            continue
        }
        val trimmedGroupPath = (groupPath.stripPrefix(subPath1).ok() ?: return null)
        return Pair(mountPoint.toOwned(), trimmedGroupPath)
    }
    return null
}

// glibc >= 2.15 has a __pthread_get_minstack() function that returns
// PTHREAD_STACK_MIN plus bytes needed for thread-local storage.
// We need that information to avoid blowing up when a small stack
// is created in an application with big thread-local storage requirements.
// See #6233 for rationale and details.
public fun minStackSize(attr: ConstPtr): Int {
    // We use dlsym to avoid an ELF version dependency on GLIBC_PRIVATE. (#23628)
    // We shouldn't really be using such an internal symbol, but there's currently
    // no other way to account for the TLS size.
    dlsym(pthreadGetMinstack, attr, libc, pthreadAttrT, libc, sizeT)
    return when (val matchVal = pthreadGetMinstack.get()) {
        null -> PTHREAD_STACK_MIN
        else -> {
            val f = matchVal
            f(attr)
        }
    }
}

// port-lint: source sys/random/linux.rs
package io.github.kotlinmania.rustlang.std.sys.random.linux

import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.libc.Poll
import io.github.kotlinmania.rustlang.libc.unix.Pollfd
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.GRND_INSECURE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.GRND_NONBLOCK
import io.github.kotlinmania.rustlang.libc.wasi.EAGAIN
import io.github.kotlinmania.rustlang.libc.wasi.EINTR
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.ENOSYS
import io.github.kotlinmania.rustlang.libc.wasi.EPERM
import io.github.kotlinmania.rustlang.libc.wasi.POLLIN
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Acquire
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Release
import io.github.kotlinmania.rustlang.std.sync.oncelock.OnceLock
import io.github.kotlinmania.rustlang.std.sys.io.Errno
import io.github.kotlinmania.rustlang.std.sys.pal.weak.syscall
import io.github.kotlinmania.rustlang.std.sys.random.unixlegacy.DEVICE

// Random data generation with the Linux kernel.
//
// The first interface random data interface to be introduced on Linux were
// the `/ dev / random` and `/ dev / urandom` special files. As paths can become
// unreachable when inside a chroot and when the file descriptors are exhausted,
// this was not enough to provide userspace with a reliable source of randomness,
// so when the OpenBSD 5.6 introduced the `getentropy` syscall, Linux 3.17 got
// its very own `getrandom`  syscall to match.[^1] Unfortunately, even if our
// minimum supported version were high enough, we still couldn't rely on the
// syscall being available, as it is blocked in `seccomp` by default.
//
// The question is therefore which of the random sources to use. Historically,
// the kernel contained two pools: the blocking and non-blocking pool. The
// blocking pool used entropy estimation to limit the amount of available
// bytes, while the non-blocking pool, once initialized using the blocking
// pool, uses a CPRNG to return an unlimited number of random bytes. With a
// strong enough CPRNG however, the entropy estimation didn't contribute that
// much towards security while being an excellent vector for DoS attacks. Thus,
// the blocking pool was removed in kernel version 5.6.[^2] That patch did not
// magically increase the quality of the non-blocking pool, however, so we can
// safely consider it strong enough even in older kernel versions and use it
// unconditionally.
//
// One additional consideration to make is that the non-blocking pool is not
// always initialized during early boot. We want the best quality of randomness
// for the output of `SystemRng` so we simply wait until it is initialized.
// When `HashMap` keys however, this represents a potential source of
// deadlocks, as the additional entropy may only be generated once the program
// makes forward progress. In that case, we just use the best random data the
// system has available at the time.
//
// So in conclusion, we always want the output of the non-blocking pool, but
// may need to wait until it is initialized. The default behavior of `getrandom`
// is to wait until the non-blocking pool is initialized and then draw from there,
// so if `getrandom` is available, we use its default to generate the bytes. For
// `HashMap`, however, we need to specify the `GRND_INSECURE` flags, but that
// is only available starting with kernel version 5.6. Thus, if we detect that
// the flag is unsupported, we try `GRND_NONBLOCK` instead, which will only
// succeed if the pool is initialized. If it isn't, we fall back to the file
// access method.
//
// The behavior of `/ dev / urandom` is inverse to that of `getrandom`: it always
// yields data, even when the pool is not initialized. For generating `HashMap`
// keys, this is not important, so we can use it directly. For secure data
// however, we need to wait until initialization, which we can do by `poll`ing
// `/ dev / random`.
//
// TLDR: our fallback strategies are:
//
// Secure data                                 | `HashMap` keys
// --------------------------------------------|------------------
// getrandom(0)                                | getrandom(GRND_INSECURE)
// poll("/dev/random") && read("/dev/urandom") | getrandom(GRND_NONBLOCK)
//                                             | read("/dev/urandom")
//
// [^1]: <https://lwn.net/Articles/606141/>
// [^2]: <https://lwn.net/Articles/808575/>

public fun getrandom(bytes: MutableList<UByte>, insecure: Boolean) {
    var bytes1 = bytes
    val GETRANDOM_AVAILABLE: Atomic<Boolean, Align1<UByte>>  = AtomicBool.new(true)
    val GRND_INSECURE_AVAILABLE: Atomic<Boolean, Align1<UByte>>  = AtomicBool.new(true)
    val URANDOM_READY: Atomic<Boolean, Align1<UByte>>  = AtomicBool.new(false)
    val DEVICE: OnceLock<File>  = OnceLock.new()
    // A weak symbol allows interposition, e.g. for perf measurements that want to
    // disable randomness for consistency. Otherwise, we'll try a raw syscall.
    // (`getrandom` was added in glibc 2.25, musl 1.1.20, android API level 28)
    syscall(getrandom, buffer, libc, cVoid, length, libc, sizeT, flags, libc, cUint, libc, ssizeT)
    if (GETRANDOM_AVAILABLE.load(Relaxed)) {
        while (true) {
            if (bytes1.isEmpty()) {
                return
            }
            val flags =
                if (insecure) {
                    if (GRND_INSECURE_AVAILABLE.load(Relaxed)) {
                        GRND_INSECURE
                    } else {
                        GRND_NONBLOCK
                    }
                } else {
                    0
                }
            val ret = getrandom(bytes1.asMutPtr(), bytes1.size, flags)
            if (ret != -1) {
                bytes1 = bytes1.subList(ret.toInt(), bytes1.size)
            } else {
                run {
                    val matchVal = Errno()
                    when {
                        matchVal == EINTR -> { continue }
                        // `GRND_INSECURE` is not available, try
                        // `GRND_NONBLOCK`.
                        matchVal == EINVAL && flags == GRND_INSECURE -> {
                            GRND_INSECURE_AVAILABLE.store(false, Relaxed)
                            continue
                        }
                        // The pool is not initialized yet, fall back to
                        // /dev/urandom for now.
                        matchVal == EAGAIN && flags == GRND_NONBLOCK -> { break }
                        // `getrandom` is unavailable or blocked by seccomp.
                        // Don't try it again and fall back to /dev/urandom.
                        matchVal == ENOSYS || matchVal == EPERM -> {
                            GETRANDOM_AVAILABLE.store(false, Relaxed)
                            break
                        }
                        else -> {
                            val other = matchVal
                            error("failed to generate random data: errno=$other, flags=$flags")
                        }
                    }
                }
            }
        }
    }
    // When we want cryptographic strength, we need to wait for the CPRNG-pool
    // to become initialized. Do this by polling `/ dev / random` until it is ready.
    if (insecure.inv()) {
        if (!URANDOM_READY.load(Acquire)) {
            val random = File.open("/dev/random").expect("failed to open /dev/random")
            val fd = Pollfd(fd = random.asRawFd(), events = POLLIN, revents = 0)
            while (!URANDOM_READY.load(Acquire)) {
                val ret1 = Poll(fd, 1, -1)
                when {
                    ret1 == 1 -> {
                        check(fd.revents == POLLIN)
                        URANDOM_READY.store(true, Release)
                        break
                    }
                    ret1 == -1 && Errno() == EINTR -> { continue }
                    else -> error("poll(\"/dev/random\") failed")
                }
            }
        }
    }
    DEVICE.getOrTryInit({ File.open("/dev/urandom") }).andThen({ dev -> dev.readExact(bytes1) }).expect("failed to generate random data")
}

public fun fillBytes(bytes: MutableList<UByte>) {
    getrandom(bytes, false)
}

public fun hashmapRandomKeys(): Pair<ULong, ULong> {
    val bytes = MutableList(16) { 0u.toUByte() }
    getrandom(bytes, true)
    val k1 = run { val bytesTmp0 = bytes[0 until 8]; ((bytesTmp0[7].toULong() shl 56) or (bytesTmp0[6].toULong() shl 48) or (bytesTmp0[5].toULong() shl 40) or (bytesTmp0[4].toULong() shl 32) or (bytesTmp0[3].toULong() shl 24) or (bytesTmp0[2].toULong() shl 16) or (bytesTmp0[1].toULong() shl 8) or bytesTmp0[0].toULong()) }
    val k2 = run { val bytesTmp1 = bytes[8 until bytes.size]; ((bytesTmp1[7].toULong() shl 56) or (bytesTmp1[6].toULong() shl 48) or (bytesTmp1[5].toULong() shl 40) or (bytesTmp1[4].toULong() shl 32) or (bytesTmp1[3].toULong() shl 24) or (bytesTmp1[2].toULong() shl 16) or (bytesTmp1[1].toULong() shl 8) or bytesTmp1[0].toULong()) }
    return Pair(k1, k2)
}

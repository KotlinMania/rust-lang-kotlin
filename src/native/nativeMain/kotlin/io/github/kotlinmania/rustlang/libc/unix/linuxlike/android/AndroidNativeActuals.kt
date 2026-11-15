@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidArc4random
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidArc4randomBuf
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidArc4randomUniform
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidBasename
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidClockNanosleep
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidClone
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidDaemon
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidDirname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidDlIteratePhdr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidEndgrent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidEndpwent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidEpollCreate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidEpollCreate1
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidEpollCtl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidEpollWait
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidErrno
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidEventfd
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidEventfdRead
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidEventfdWrite
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFaccessat
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFallocate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFallocate64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFflushUnlocked
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFgetsUnlocked
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFgetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFlistxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFreadUnlocked
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFremovexattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFsetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidFwriteUnlocked
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetauxval
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetentropy
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetgrent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetgrgid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetgrgidR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetgrnam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetgrnamR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetgrouplist
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetnameinfo
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetoptLong
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetpriority
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetpwent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetrandom
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetrlimit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetrlimit64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGettid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGettimeofday
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetutent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidGetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidInitgroups
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidInotifyAddWatch
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidInotifyInit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidInotifyInit1
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidInotifyRmWatch
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidKlogctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidLgetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidListxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidLlistxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidLremovexattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidLsetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidMadvise
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidMallocInfo
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidMallocUsableSize
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidMemfdCreate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidMemmem
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidMlock2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidMount
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidMprotect
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidMsync
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPersonality
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPopen
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPosixFallocate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPosixFallocate64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPpoll
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPrctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPreadv
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPreadv2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPrlimit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPrlimit64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidProcessVmReadv
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidProcessVmWritev
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPtrace
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPwritev
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidPwritev2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidReallocarray
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidRegcomp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidRegerror
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidRegexec
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidRegfree
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidRemovexattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidRenameat2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedCpualloc
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedCpucount
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedCpufree
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedGetaffinity
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedGetcpu
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedGetparam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedGetscheduler
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedSetaffinity
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedSetparam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSchedSetscheduler
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSeekdir
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSemClose
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSemGetvalue
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSemOpen
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSemTimedwait
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSemUnlink
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSendfile
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSendfile64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetfsgid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetfsuid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetgrent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSethostname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetns
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetpriority
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetpwent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetrlimit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetrlimit64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSettimeofday
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetutent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSigaltstack
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSignalfd
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSigsuspend
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSigtimedwait
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSigwait
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSplice
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidStrerrorR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSwapoff
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSwapon
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSync
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSyncfs
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSyscall
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSysinfo
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSystemPropertyFind
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSystemPropertyGet
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidSystemPropertySet
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTee
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTelldir
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTgkill
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTimerCreate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTimerDelete
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTimerGetoverrun
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTimerGettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTimerSettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTimerfdCreate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTimerfdGettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidTimerfdSettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidUmount
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidUmount2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidUnshare
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidUtmpname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.cinterop.rustlangLibcUnixLinuxlikeAndroidVmsplice
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdT
import io.github.kotlinmania.rustlang.unix.linuxlike.TimerT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun setpwent() {
    rustlangLibcUnixLinuxlikeAndroidSetpwent()
}

public actual fun endpwent() {
    rustlangLibcUnixLinuxlikeAndroidEndpwent()
}

public actual fun getpwent(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidGetpwent()?.toLong() ?: 0L)

public actual fun setgrent() {
    rustlangLibcUnixLinuxlikeAndroidSetgrent()
}

public actual fun endgrent() {
    rustlangLibcUnixLinuxlikeAndroidEndgrent()
}

public actual fun getgrent(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidGetgrent()?.toLong() ?: 0L)

public actual fun getrlimit64(resource: CInt, rlim: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidGetrlimit64(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun setrlimit64(resource: CInt, rlim: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSetrlimit64(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun getrlimit(resource: CInt, rlim: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidGetrlimit(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun setrlimit(resource: CInt, rlim: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSetrlimit(resource, rlim.rawAddress.toCPointer<ByteVar>())

public actual fun prlimit(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidPrlimit(pid, resource, newLimit.rawAddress.toCPointer<ByteVar>(), oldLimit.rawAddress.toCPointer<ByteVar>())

public actual fun prlimit64(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidPrlimit64(pid, resource, newLimit.rawAddress.toCPointer<ByteVar>(), oldLimit.rawAddress.toCPointer<ByteVar>())

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcUnixLinuxlikeAndroidStrerrorR(errnum, buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidGettimeofday(tp.rawAddress.toCPointer<ByteVar>(), tz.rawAddress.toCPointer<ByteVar>())

public actual fun mlock2(addr: ConstPtr, len: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidMlock2(addr.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidMadvise(addr.rawAddress.toCPointer<ByteVar>(), len, advice)

public actual fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidMsync(addr.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidMprotect(addr.rawAddress.toCPointer<ByteVar>(), len, prot)

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SizeT, serv: MutPtr, servlen: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidGetnameinfo(sa.rawAddress.toCPointer<ByteVar>(), salen, host.rawAddress.toCPointer<ByteVar>(), hostlen, serv.rawAddress.toCPointer<ByteVar>(), servlen, flags)

public actual fun preadv(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidPreadv(fd, iov.rawAddress.toCPointer<ByteVar>(), count, offset)

public actual fun pwritev(fd: CInt, iov: ConstPtr, count: CInt, offset: OffT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidPwritev(fd, iov.rawAddress.toCPointer<ByteVar>(), count, offset)

public actual fun processVmReadv(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidProcessVmReadv(pid, localIov.rawAddress.toCPointer<ByteVar>(), localIovCount, remoteIov.rawAddress.toCPointer<ByteVar>(), remoteIovCount, flags)

public actual fun processVmWritev(pid: PidT, localIov: ConstPtr, localIovCount: CUlong, remoteIov: ConstPtr, remoteIovCount: CUlong, flags: CUlong): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidProcessVmWritev(pid, localIov.rawAddress.toCPointer<ByteVar>(), localIovCount, remoteIov.rawAddress.toCPointer<ByteVar>(), remoteIovCount, flags)

public actual fun ptrace(request: CInt, vararg variadicArgs: Long): CLong =
    rustlangLibcUnixLinuxlikeAndroidPtrace(request, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun getpriority(which: CInt, who: IdT): CInt =
    rustlangLibcUnixLinuxlikeAndroidGetpriority(which, who)

public actual fun setpriority(which: CInt, who: IdT, prio: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidSetpriority(which, who, prio)

public actual fun schedCpualloc(count: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidSchedCpualloc(count)?.toLong() ?: 0L)

public actual fun schedCpufree(set: MutPtr) {
    rustlangLibcUnixLinuxlikeAndroidSchedCpufree(set.rawAddress.toCPointer<ByteVar>())
}

public actual fun schedCpucount(setsize: SizeT, set: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedCpucount(setsize, set.rawAddress.toCPointer<ByteVar>())

public actual fun schedGetcpu(): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedGetcpu()

public actual fun mallinfo(): io.github.kotlinmania.rustlang.unix.linuxlike.android.Mallinfo =
    io.github.kotlinmania.rustlang.unix.linuxlike.android.Mallinfo()

public actual fun mallocInfo(options: CInt, stream: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidMallocInfo(options, stream.rawAddress.toCPointer<ByteVar>())

public actual fun mallocUsableSize(ptr: ConstPtr): SizeT =
    rustlangLibcUnixLinuxlikeAndroidMallocUsableSize(ptr.rawAddress.toCPointer<ByteVar>())

public actual fun utmpname(name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidUtmpname(name.rawAddress.toCPointer<ByteVar>())

public actual fun setutent() {
    rustlangLibcUnixLinuxlikeAndroidSetutent()
}

public actual fun getutent(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidGetutent()?.toLong() ?: 0L)

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    rustlangLibcUnixLinuxlikeAndroidSeekdir(dirp.rawAddress.toCPointer<ByteVar>(), loc)
}

public actual fun telldir(dirp: MutPtr): CLong =
    rustlangLibcUnixLinuxlikeAndroidTelldir(dirp.rawAddress.toCPointer<ByteVar>())

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    rustlangLibcUnixLinuxlikeAndroidFallocate(fd, mode, offset, len)

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    rustlangLibcUnixLinuxlikeAndroidFallocate64(fd, mode, offset, len)

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    rustlangLibcUnixLinuxlikeAndroidPosixFallocate(fd, offset, len)

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    rustlangLibcUnixLinuxlikeAndroidPosixFallocate64(fd, offset, len)

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidGetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size)

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidLgetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size)

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidFgetxattr(filedes, name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size)

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidSetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidLsetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidFsetxattr(filedes, name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidListxattr(path.rawAddress.toCPointer<ByteVar>(), list.rawAddress.toCPointer<ByteVar>(), size)

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidLlistxattr(path.rawAddress.toCPointer<ByteVar>(), list.rawAddress.toCPointer<ByteVar>(), size)

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidFlistxattr(filedes, list.rawAddress.toCPointer<ByteVar>(), size)

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidRemovexattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>())

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidLremovexattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>())

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidFremovexattr(filedes, name.rawAddress.toCPointer<ByteVar>())

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidSignalfd(fd, mask.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun timerfdCreate(clock: ClockidT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidTimerfdCreate(clock, flags)

public actual fun timerfdGettime(fd: CInt, currentValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidTimerfdGettime(fd, currentValue.rawAddress.toCPointer<ByteVar>())

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidTimerfdSettime(fd, flags, newValue.rawAddress.toCPointer<ByteVar>(), oldValue.rawAddress.toCPointer<ByteVar>())

public actual fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidTimerCreate(clockid, sevp.rawAddress.toCPointer<ByteVar>(), timerid.rawAddress.toCPointer<ByteVar>())

public actual fun timerDelete(timerid: TimerT): CInt =
    rustlangLibcUnixLinuxlikeAndroidTimerDelete(timerid.rawAddress.toCPointer<ByteVar>())

public actual fun timerGetoverrun(timerid: TimerT): CInt =
    rustlangLibcUnixLinuxlikeAndroidTimerGetoverrun(timerid.rawAddress.toCPointer<ByteVar>())

public actual fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidTimerGettime(timerid.rawAddress.toCPointer<ByteVar>(), currValue.rawAddress.toCPointer<ByteVar>())

public actual fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidTimerSettime(timerid.rawAddress.toCPointer<ByteVar>(), flags, newValue.rawAddress.toCPointer<ByteVar>(), oldValue.rawAddress.toCPointer<ByteVar>())

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    rustlangLibcUnixLinuxlikeAndroidSyscall(num, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedGetaffinity(pid, cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedSetaffinity(pid, cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun epollCreate(size: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidEpollCreate(size)

public actual fun epollCreate1(flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidEpollCreate1(flags)

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidEpollWait(epfd, events.rawAddress.toCPointer<ByteVar>(), maxevents, timeout)

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidEpollCtl(epfd, op, fd, event.rawAddress.toCPointer<ByteVar>())

public actual fun unshare(flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidUnshare(flags)

public actual fun umount(target: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidUmount(target.rawAddress.toCPointer<ByteVar>())

public actual fun schedGetPriorityMax(policy: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(policy)

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidTee(fdIn, fdOut, len, flags)

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSettimeofday(tv.rawAddress.toCPointer<ByteVar>(), tz.rawAddress.toCPointer<ByteVar>())

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidSplice(fdIn, offIn.rawAddress.toCPointer<ByteVar>(), fdOut, offOut.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidEventfd(initval, flags)

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidEventfdRead(fd, value.rawAddress.toCPointer<ByteVar>())

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    rustlangLibcUnixLinuxlikeAndroidEventfdWrite(fd, value)

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval(pid, tp.rawAddress.toCPointer<ByteVar>())

public actual fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSemTimedwait(sem.rawAddress.toCPointer<ByteVar>(), abstime.rawAddress.toCPointer<ByteVar>())

public actual fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSemGetvalue(sem.rawAddress.toCPointer<ByteVar>(), sval.rawAddress.toCPointer<ByteVar>())

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedSetparam(pid, param.rawAddress.toCPointer<ByteVar>())

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidSetns(fd, nstype)

public actual fun swapoff(puath: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSwapoff(puath.rawAddress.toCPointer<ByteVar>())

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidVmsplice(fd, iov.rawAddress.toCPointer<ByteVar>(), nrSegs, flags)

public actual fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidMount(src.rawAddress.toCPointer<ByteVar>(), target.rawAddress.toCPointer<ByteVar>(), fstype.rawAddress.toCPointer<ByteVar>(), flags, data.rawAddress.toCPointer<ByteVar>())

public actual fun personality(persona: CUint): CInt =
    rustlangLibcUnixLinuxlikeAndroidPersonality(persona)

public actual fun prctl(option: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixLinuxlikeAndroidPrctl(option, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedGetparam(pid, param.rawAddress.toCPointer<ByteVar>())

public actual fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidPpoll(fds.rawAddress.toCPointer<ByteVar>(), nfds, timeout.rawAddress.toCPointer<ByteVar>(), sigmask.rawAddress.toCPointer<ByteVar>())

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixLinuxlikeAndroidClone(0L.toCPointer<ByteVar>(), childStack.rawAddress.toCPointer<ByteVar>(), flags, arg.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedGetscheduler(pid: PidT): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(pid)

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidClockNanosleep(clkId, flags, rqtp.rawAddress.toCPointer<ByteVar>(), rmtp.rawAddress.toCPointer<ByteVar>())

public actual fun sethostname(name: ConstPtr, len: SizeT): CInt =
    rustlangLibcUnixLinuxlikeAndroidSethostname(name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun schedGetPriorityMin(policy: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(policy)

public actual fun sysinfo(info: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSysinfo(info.rawAddress.toCPointer<ByteVar>())

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidUmount2(target.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidSwapon(path.rawAddress.toCPointer<ByteVar>(), swapflags)

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSchedSetscheduler(pid, policy, param.rawAddress.toCPointer<ByteVar>())

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidSendfile(outFd, inFd, offset.rawAddress.toCPointer<ByteVar>(), count)

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidSendfile64(outFd, inFd, offset.rawAddress.toCPointer<ByteVar>(), count)

public actual fun setfsgid(gid: GidT): CInt =
    rustlangLibcUnixLinuxlikeAndroidSetfsgid(gid)

public actual fun setfsuid(uid: UidT): CInt =
    rustlangLibcUnixLinuxlikeAndroidSetfsuid(uid)

public actual fun sigsuspend(mask: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSigsuspend(mask.rawAddress.toCPointer<ByteVar>())

public actual fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidGetgrgidR(gid, grp.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSigaltstack(ss.rawAddress.toCPointer<ByteVar>(), oss.rawAddress.toCPointer<ByteVar>())

public actual fun semClose(sem: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSemClose(sem.rawAddress.toCPointer<ByteVar>())

public actual fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidGetgrnamR(name.rawAddress.toCPointer<ByteVar>(), grp.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, result.rawAddress.toCPointer<ByteVar>())

public actual fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidSemOpen(name.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)?.toLong() ?: 0L)

public actual fun getgrnam(name: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidGetgrnam(name.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun semUnlink(name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSemUnlink(name.rawAddress.toCPointer<ByteVar>())

public actual fun daemon(nochdir: CInt, noclose: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidDaemon(nochdir, noclose)

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSigtimedwait(set.rawAddress.toCPointer<ByteVar>(), info.rawAddress.toCPointer<ByteVar>(), timeout.rawAddress.toCPointer<ByteVar>())

public actual fun sigwait(set: ConstPtr, sig: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSigwait(set.rawAddress.toCPointer<ByteVar>(), sig.rawAddress.toCPointer<ByteVar>())

public actual fun getgrgid(gid: GidT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidGetgrgid(gid)?.toLong() ?: 0L)

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidGetgrouplist(user.rawAddress.toCPointer<ByteVar>(), group, groups.rawAddress.toCPointer<ByteVar>(), ngroups.rawAddress.toCPointer<ByteVar>())

public actual fun initgroups(user: ConstPtr, group: GidT): CInt =
    rustlangLibcUnixLinuxlikeAndroidInitgroups(user.rawAddress.toCPointer<ByteVar>(), group)

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidPopen(command.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidFaccessat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode, flags)

public actual fun errno(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidErrno()?.toLong() ?: 0L)

public actual fun inotifyRmWatch(fd: CInt, wd: UInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(fd, wd)

public actual fun inotifyInit(): CInt =
    rustlangLibcUnixLinuxlikeAndroidInotifyInit()

public actual fun inotifyInit1(flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidInotifyInit1(flags)

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidInotifyAddWatch(fd, path.rawAddress.toCPointer<ByteVar>(), mask)

public actual fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidRegcomp(preg.rawAddress.toCPointer<ByteVar>(), pattern.rawAddress.toCPointer<ByteVar>(), cflags)

public actual fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidRegexec(preg.rawAddress.toCPointer<ByteVar>(), input.rawAddress.toCPointer<ByteVar>(), nmatch, pmatch.rawAddress.toCPointer<ByteVar>(), eflags)

public actual fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT =
    rustlangLibcUnixLinuxlikeAndroidRegerror(errcode, preg.rawAddress.toCPointer<ByteVar>(), errbuf.rawAddress.toCPointer<ByteVar>(), errbufSize)

public actual fun regfree(preg: MutPtr) {
    rustlangLibcUnixLinuxlikeAndroidRegfree(preg.rawAddress.toCPointer<ByteVar>())
}

public actual fun androidSetAbortMessage(msg: ConstPtr) {
    rustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage(msg.rawAddress.toCPointer<ByteVar>())
}

public actual fun gettid(): PidT =
    rustlangLibcUnixLinuxlikeAndroidGettid()

public actual fun tgkill(tgid: PidT, tid: PidT, sig: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidTgkill(tgid, tid, sig)

public actual fun getauxval(type: CUlong): CUlong =
    rustlangLibcUnixLinuxlikeAndroidGetauxval(type)

public actual fun getrandom(buf: MutPtr, buflen: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidGetrandom(buf.rawAddress.toCPointer<ByteVar>(), buflen, flags)

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcUnixLinuxlikeAndroidGetentropy(buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun systemPropertySet(name: ConstPtr, value: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSystemPropertySet(name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())

public actual fun systemPropertyGet(name: ConstPtr, value: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSystemPropertyGet(name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())

public actual fun systemPropertyFind(name: ConstPtr): ConstPtr =
    ConstPtr(rustlangLibcUnixLinuxlikeAndroidSystemPropertyFind(name.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun systemPropertyFindNth(n: CUint): ConstPtr =
    ConstPtr(rustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth(n)?.toLong() ?: 0L)

public actual fun systemPropertyForeach(callback: SystemPropertyForeachCallbackCallback, cookie: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach(0L.toCPointer<ByteVar>(), cookie.rawAddress.toCPointer<ByteVar>())

public actual fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidDlIteratePhdr(0L.toCPointer<ByteVar>(), data.rawAddress.toCPointer<ByteVar>())

public actual fun arc4random(): UInt =
    rustlangLibcUnixLinuxlikeAndroidArc4random()

public actual fun arc4randomUniform(upperBound: UInt): UInt =
    rustlangLibcUnixLinuxlikeAndroidArc4randomUniform(upperBound)

public actual fun arc4randomBuf(buf: MutPtr, n: SizeT) {
    rustlangLibcUnixLinuxlikeAndroidArc4randomBuf(buf.rawAddress.toCPointer<ByteVar>(), n)
}

public actual fun reallocarray(ptr: MutPtr, nmemb: SizeT, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidReallocarray(ptr.rawAddress.toCPointer<ByteVar>(), nmemb, size)?.toLong() ?: 0L)

public actual fun dirname(path: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidDirname(path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun basename(path: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidBasename(path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidGetoptLong(argc, argv.rawAddress.toCPointer<ByteVar>(), optstring.rawAddress.toCPointer<ByteVar>(), longopts.rawAddress.toCPointer<ByteVar>(), longindex.rawAddress.toCPointer<ByteVar>())

public actual fun sync() {
    rustlangLibcUnixLinuxlikeAndroidSync()
}

public actual fun syncfs(fd: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidSyncfs(fd)

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidMemmem(haystack.rawAddress.toCPointer<ByteVar>(), haystacklen, needle.rawAddress.toCPointer<ByteVar>(), needlelen)?.toLong() ?: 0L)

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    rustlangLibcUnixLinuxlikeAndroidFreadUnlocked(buf.rawAddress.toCPointer<ByteVar>(), size, nobj, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fwriteUnlocked(buf: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    rustlangLibcUnixLinuxlikeAndroidFwriteUnlocked(buf.rawAddress.toCPointer<ByteVar>(), size, nobj, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fflushUnlocked(stream: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeAndroidFflushUnlocked(stream.rawAddress.toCPointer<ByteVar>())

public actual fun fgetsUnlocked(buf: MutPtr, size: CInt, stream: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeAndroidFgetsUnlocked(buf.rawAddress.toCPointer<ByteVar>(), size, stream.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    rustlangLibcUnixLinuxlikeAndroidKlogctl(syslogType, bufp.rawAddress.toCPointer<ByteVar>(), len)

public actual fun memfdCreate(name: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixLinuxlikeAndroidMemfdCreate(name.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun renameat2(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixLinuxlikeAndroidRenameat2(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun preadv2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidPreadv2(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset, flags)

public actual fun pwritev2(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT, flags: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeAndroidPwritev2(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset, flags)

@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxAccept4
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxClockNanosleep
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxClone
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxDup3
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxEndnetent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxEpollCreate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxEpollCreate1
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxEpollCtl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxEpollPwait
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxEpollWait
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxEventfd
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxEventfdRead
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxEventfdWrite
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFallocate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFallocate64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFanotifyInit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFgetpos64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFgetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFlistxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFopen64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFreadUnlocked
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFremovexattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFsetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxFtok
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxGetdtablesize
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxGetgrouplist
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxGethostid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxGetnetbyaddr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxGetnetbyname
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxGetnetent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxGetspnamR
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxGetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxInotifyAddWatch
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxInotifyInit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxInotifyInit1
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxInotifyRmWatch
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxKlogctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxLcong48
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxLgetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxListxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxLlistxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxLremovexattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxLsetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxLutimes
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMkfifoat
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMkstemps
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMqClose
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMqGetattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMqOpen
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMqReceive
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMqSend
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMqSetattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMqTimedreceive
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMqTimedsend
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMqUnlink
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMrand48
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMremap
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMsgctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMsgget
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMsgrcv
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxMsgsnd
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxNameToHandleAt
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxOpenByHandleAt
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPersonality
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixFallocate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixFallocate64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixMadvise
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawn
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxPosixSpawnp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxQuotactl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxReadahead
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxReboot
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxRemapFilePages
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxRemovexattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSchedGetparam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSchedGetscheduler
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSchedSetaffinity
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSchedSetparam
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSchedSetscheduler
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSeed48
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSemctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSemget
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSemop
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSendfile
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSendfile64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSetfsgid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSetfsuid
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSetnetent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSetns
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSetxattr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxShmOpen
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxShmUnlink
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSigaltstack
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSignalfd
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSigtimedwait
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSigwaitinfo
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSplice
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSwapoff
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSwapon
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSync
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSyncFileRange
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSyncfs
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxSyscall
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxTee
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxTimerfdCreate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxTimerfdGettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxTimerfdSettime
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxTmpfile64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxUmount
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxUmount2
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxUnshare
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxVhangup
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.cinterop.rustlangLibcUnixLinuxlikeLinuxVmsplice
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.KeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun getspnamR(name: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGetspnamR(name.rawAddress.toCPointer<ByteVar>(), spbuf.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), buflen, spbufp.rawAddress.toCPointer<ByteVar>())

public actual fun mqOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MqdT =
    rustlangLibcUnixLinuxlikeLinuxMqOpen(name.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun mqClose(mqd: MqdT): CInt =
    rustlangLibcUnixLinuxlikeLinuxMqClose(mqd)

public actual fun mqUnlink(name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMqUnlink(name.rawAddress.toCPointer<ByteVar>())

public actual fun mqReceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxMqReceive(mqd, msgPtr.rawAddress.toCPointer<ByteVar>(), msgLen, msgPrio.rawAddress.toCPointer<ByteVar>())

public actual fun mqTimedreceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr, absTimeout: ConstPtr): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxMqTimedreceive(mqd, msgPtr.rawAddress.toCPointer<ByteVar>(), msgLen, msgPrio.rawAddress.toCPointer<ByteVar>(), absTimeout.rawAddress.toCPointer<ByteVar>())

public actual fun mqSend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint): CInt =
    rustlangLibcUnixLinuxlikeLinuxMqSend(mqd, msgPtr.rawAddress.toCPointer<ByteVar>(), msgLen, msgPrio)

public actual fun mqTimedsend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint, absTimeout: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMqTimedsend(mqd, msgPtr.rawAddress.toCPointer<ByteVar>(), msgLen, msgPrio, absTimeout.rawAddress.toCPointer<ByteVar>())

public actual fun mqGetattr(mqd: MqdT, attr: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMqGetattr(mqd, attr.rawAddress.toCPointer<ByteVar>())

public actual fun mqSetattr(mqd: MqdT, newattr: ConstPtr, oldattr: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMqSetattr(mqd, newattr.rawAddress.toCPointer<ByteVar>(), oldattr.rawAddress.toCPointer<ByteVar>())

public actual fun mrand48(): CLong =
    rustlangLibcUnixLinuxlikeLinuxMrand48()

public actual fun seed48(xseed: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxSeed48(xseed.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun lcong48(p: MutPtr) {
    rustlangLibcUnixLinuxlikeLinuxLcong48(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxLutimes(file.rawAddress.toCPointer<ByteVar>(), times.rawAddress.toCPointer<ByteVar>())

public actual fun shmOpen(name: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    rustlangLibcUnixLinuxlikeLinuxShmOpen(name.rawAddress.toCPointer<ByteVar>(), oflag, mode)

public actual fun shmUnlink(name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxShmUnlink(name.rawAddress.toCPointer<ByteVar>())

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    rustlangLibcUnixLinuxlikeLinuxFtok(pathname.rawAddress.toCPointer<ByteVar>(), projId)

public actual fun semget(key: KeyT, nsems: CInt, semflag: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxSemget(key, nsems, semflag)

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    rustlangLibcUnixLinuxlikeLinuxSemop(semid, sops.rawAddress.toCPointer<ByteVar>(), nsops)

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixLinuxlikeLinuxSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun msgctl(msqid: CInt, cmd: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxMsgctl(msqid, cmd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun msgget(key: KeyT, msgflg: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxMsgget(key, msgflg)

public actual fun msgrcv(msqid: CInt, msgp: MutPtr, msgsz: SizeT, msgtyp: CLong, msgflg: CInt): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxMsgrcv(msqid, msgp.rawAddress.toCPointer<ByteVar>(), msgsz, msgtyp, msgflg)

public actual fun msgsnd(msqid: CInt, msgp: ConstPtr, msgsz: SizeT, msgflg: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxMsgsnd(msqid, msgp.rawAddress.toCPointer<ByteVar>(), msgsz, msgflg)

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    rustlangLibcUnixLinuxlikeLinuxFallocate(fd, mode, offset, len)

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixFallocate(fd, offset, len)

public actual fun readahead(fd: CInt, offset: Off64T, count: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxReadahead(fd, offset, count)

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxGetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size)

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxLgetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size)

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxFgetxattr(filedes, name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size)

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxSetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxLsetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxFsetxattr(filedes, name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxListxattr(path.rawAddress.toCPointer<ByteVar>(), list.rawAddress.toCPointer<ByteVar>(), size)

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxLlistxattr(path.rawAddress.toCPointer<ByteVar>(), list.rawAddress.toCPointer<ByteVar>(), size)

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxFlistxattr(filedes, list.rawAddress.toCPointer<ByteVar>(), size)

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxRemovexattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>())

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxLremovexattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>())

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxFremovexattr(filedes, name.rawAddress.toCPointer<ByteVar>())

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxSignalfd(fd, mask.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun timerfdCreate(clockid: ClockidT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxTimerfdCreate(clockid, flags)

public actual fun timerfdGettime(fd: CInt, currValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxTimerfdGettime(fd, currValue.rawAddress.toCPointer<ByteVar>())

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxTimerfdSettime(fd, flags, newValue.rawAddress.toCPointer<ByteVar>(), oldValue.rawAddress.toCPointer<ByteVar>())

public actual fun quotactl(cmd: CInt, special: ConstPtr, id: CInt, data: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxQuotactl(cmd, special.rawAddress.toCPointer<ByteVar>(), id, data.rawAddress.toCPointer<ByteVar>())

public actual fun epollPwait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt, sigmask: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxEpollPwait(epfd, events.rawAddress.toCPointer<ByteVar>(), maxevents, timeout, sigmask.rawAddress.toCPointer<ByteVar>())

public actual fun dup3(oldfd: CInt, newfd: CInt, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxDup3(oldfd, newfd, flags)

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxSigtimedwait(set.rawAddress.toCPointer<ByteVar>(), info.rawAddress.toCPointer<ByteVar>(), timeout.rawAddress.toCPointer<ByteVar>())

public actual fun sigwaitinfo(set: ConstPtr, info: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxSigwaitinfo(set.rawAddress.toCPointer<ByteVar>(), info.rawAddress.toCPointer<ByteVar>())

public actual fun accept4(fd: CInt, addr: MutPtr, len: MutPtr, flg: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxAccept4(fd, addr.rawAddress.toCPointer<ByteVar>(), len.rawAddress.toCPointer<ByteVar>(), flg)

public actual fun reboot(howTo: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxReboot(howTo)

public actual fun setfsgid(gid: GidT): CInt =
    rustlangLibcUnixLinuxlikeLinuxSetfsgid(gid)

public actual fun setfsuid(uid: UidT): CInt =
    rustlangLibcUnixLinuxlikeLinuxSetfsuid(uid)

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    rustlangLibcUnixLinuxlikeLinuxMkfifoat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun syncFileRange(fd: CInt, offset: Off64T, nbytes: Off64T, flags: CUint): CInt =
    rustlangLibcUnixLinuxlikeLinuxSyncFileRange(fd, offset, nbytes, flags)

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixMadvise(addr.rawAddress.toCPointer<ByteVar>(), len, advice)

public actual fun mremap(addr: MutPtr, len: SizeT, newLen: SizeT, flags: CInt, vararg variadicArgs: Long): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxMremap(addr.rawAddress.toCPointer<ByteVar>(), len, newLen, flags, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)?.toLong() ?: 0L)

public actual fun remapFilePages(addr: MutPtr, size: SizeT, prot: CInt, pgoff: SizeT, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxRemapFilePages(addr.rawAddress.toCPointer<ByteVar>(), size, prot, pgoff, flags)

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxMkstemps(template.rawAddress.toCPointer<ByteVar>(), suffixlen)

public actual fun vhangup(): CInt =
    rustlangLibcUnixLinuxlikeLinuxVhangup()

public actual fun sync() {
    rustlangLibcUnixLinuxlikeLinuxSync()
}

public actual fun syncfs(fd: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxSyncfs(fd)

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    rustlangLibcUnixLinuxlikeLinuxSyscall(num, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxSchedSetaffinity(pid, cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun epollCreate(size: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxEpollCreate(size)

public actual fun epollCreate1(flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxEpollCreate1(flags)

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxEpollWait(epfd, events.rawAddress.toCPointer<ByteVar>(), maxevents, timeout)

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxEpollCtl(epfd, op, fd, event.rawAddress.toCPointer<ByteVar>())

public actual fun unshare(flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxUnshare(flags)

public actual fun umount(target: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxUmount(target.rawAddress.toCPointer<ByteVar>())

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxTee(fdIn, fdOut, len, flags)

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxSplice(fdIn, offIn.rawAddress.toCPointer<ByteVar>(), fdOut, offOut.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxEventfd(initval, flags)

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxEventfdRead(fd, value.rawAddress.toCPointer<ByteVar>())

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    rustlangLibcUnixLinuxlikeLinuxEventfdWrite(fd, value)

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval(pid, tp.rawAddress.toCPointer<ByteVar>())

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxSchedSetparam(pid, param.rawAddress.toCPointer<ByteVar>())

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxSetns(fd, nstype)

public actual fun swapoff(path: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxSwapoff(path.rawAddress.toCPointer<ByteVar>())

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxVmsplice(fd, iov.rawAddress.toCPointer<ByteVar>(), nrSegs, flags)

public actual fun personality(persona: CUlong): CInt =
    rustlangLibcUnixLinuxlikeLinuxPersonality(persona)

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxSchedGetparam(pid, param.rawAddress.toCPointer<ByteVar>())

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixLinuxlikeLinuxClone(0L.toCPointer<ByteVar>(), childStack.rawAddress.toCPointer<ByteVar>(), flags, arg.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedGetscheduler(pid: PidT): CInt =
    rustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(pid)

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxClockNanosleep(clkId, flags, rqtp.rawAddress.toCPointer<ByteVar>(), rmtp.rawAddress.toCPointer<ByteVar>())

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxUmount2(target.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxSwapon(path.rawAddress.toCPointer<ByteVar>(), swapflags)

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxSchedSetscheduler(pid, policy, param.rawAddress.toCPointer<ByteVar>())

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxSendfile(outFd, inFd, offset.rawAddress.toCPointer<ByteVar>(), count)

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxSigaltstack(ss.rawAddress.toCPointer<ByteVar>(), oss.rawAddress.toCPointer<ByteVar>())

public actual fun getdtablesize(): CInt =
    rustlangLibcUnixLinuxlikeLinuxGetdtablesize()

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxGetgrouplist(user.rawAddress.toCPointer<ByteVar>(), group, groups.rawAddress.toCPointer<ByteVar>(), ngroups.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawn(pid.rawAddress.toCPointer<ByteVar>(), path.rawAddress.toCPointer<ByteVar>(), fileActions.rawAddress.toCPointer<ByteVar>(), attrp.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnp(pid.rawAddress.toCPointer<ByteVar>(), file.rawAddress.toCPointer<ByteVar>(), fileActions.rawAddress.toCPointer<ByteVar>(), attrp.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(attr.rawAddress.toCPointer<ByteVar>(), default.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(attr.rawAddress.toCPointer<ByteVar>(), default.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(attr.rawAddress.toCPointer<ByteVar>(), default.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(attr.rawAddress.toCPointer<ByteVar>(), default.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(attr.rawAddress.toCPointer<ByteVar>(), flags.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(attr.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(attr.rawAddress.toCPointer<ByteVar>(), flags.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(attr.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun posixSpawnattrGetschedpolicy(attr: ConstPtr, flags: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(attr.rawAddress.toCPointer<ByteVar>(), flags.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetschedpolicy(attr: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(attr.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun posixSpawnattrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(attr.rawAddress.toCPointer<ByteVar>(), param.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(attr.rawAddress.toCPointer<ByteVar>(), param.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(actions.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(actions.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(actions.rawAddress.toCPointer<ByteVar>(), fd, path.rawAddress.toCPointer<ByteVar>(), oflag, mode)

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(actions.rawAddress.toCPointer<ByteVar>(), fd)

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(actions.rawAddress.toCPointer<ByteVar>(), fd, newfd)

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    rustlangLibcUnixLinuxlikeLinuxFreadUnlocked(buf.rawAddress.toCPointer<ByteVar>(), size, nobj, stream.rawAddress.toCPointer<ByteVar>())

public actual fun inotifyRmWatch(fd: CInt, wd: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(fd, wd)

public actual fun inotifyInit(): CInt =
    rustlangLibcUnixLinuxlikeLinuxInotifyInit()

public actual fun inotifyInit1(flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxInotifyInit1(flags)

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxInotifyAddWatch(fd, path.rawAddress.toCPointer<ByteVar>(), mask)

public actual fun fanotifyInit(flags: CUint, eventFFlags: CUint): CInt =
    rustlangLibcUnixLinuxlikeLinuxFanotifyInit(flags, eventFFlags)

public actual fun gethostid(): CLong =
    rustlangLibcUnixLinuxlikeLinuxGethostid()

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxKlogctl(syslogType, bufp.rawAddress.toCPointer<ByteVar>(), len)

public actual fun nameToHandleAt(dirfd: CInt, path: ConstPtr, handle: MutPtr, mountId: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxNameToHandleAt(dirfd, path.rawAddress.toCPointer<ByteVar>(), handle.rawAddress.toCPointer<ByteVar>(), mountId.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun openByHandleAt(mountFd: CInt, handle: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixLinuxlikeLinuxOpenByHandleAt(mountFd, handle.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun getnetbyname(name: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGetnetbyname(name.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getnetbyaddr(net: UInt, type: CInt): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGetnetbyaddr(net, type)?.toLong() ?: 0L)

public actual fun getnetent(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxGetnetent()?.toLong() ?: 0L)

public actual fun setnetent(stayopen: CInt) {
    rustlangLibcUnixLinuxlikeLinuxSetnetent(stayopen)
}

public actual fun endnetent() {
    rustlangLibcUnixLinuxlikeLinuxEndnetent()
}

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    rustlangLibcUnixLinuxlikeLinuxFallocate64(fd, mode, offset, len)

public actual fun fgetpos64(stream: MutPtr, ptr: MutPtr): CInt =
    rustlangLibcUnixLinuxlikeLinuxFgetpos64(stream.rawAddress.toCPointer<ByteVar>(), ptr.rawAddress.toCPointer<ByteVar>())

public actual fun fopen64(filename: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxFopen64(filename.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    rustlangLibcUnixLinuxlikeLinuxPosixFallocate64(fd, offset, len)

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    rustlangLibcUnixLinuxlikeLinuxSendfile64(outFd, inFd, offset.rawAddress.toCPointer<ByteVar>(), count)

public actual fun tmpfile64(): MutPtr =
    MutPtr(rustlangLibcUnixLinuxlikeLinuxTmpfile64()?.toLong() ?: 0L)

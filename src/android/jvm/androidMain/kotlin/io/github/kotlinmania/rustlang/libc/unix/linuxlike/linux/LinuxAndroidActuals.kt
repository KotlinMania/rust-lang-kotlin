@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.KeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

private object LibcUnixLinuxlikeLinuxJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGetspnamR(name: Long, spbuf: Long, buf: Long, buflen: Long, spbufp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMqOpen(name: Long, oflag: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMqClose(mqd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMqUnlink(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMqReceive(mqd: Int, msgPtr: Long, msgLen: Long, msgPrio: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMqTimedreceive(mqd: Int, msgPtr: Long, msgLen: Long, msgPrio: Long, absTimeout: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMqSend(mqd: Int, msgPtr: Long, msgLen: Long, msgPrio: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMqTimedsend(mqd: Int, msgPtr: Long, msgLen: Long, msgPrio: Int, absTimeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMqGetattr(mqd: Int, attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMqSetattr(mqd: Int, newattr: Long, oldattr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMrand48(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSeed48(xseed: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxLcong48(p: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxLutimes(file: Long, times: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxShmOpen(name: Long, oflag: Int, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxShmUnlink(name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFtok(pathname: Long, projId: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSemget(key: Int, nsems: Int, semflag: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSemop(semid: Int, sops: Long, nsops: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSemctl(semid: Int, semnum: Int, cmd: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMsgctl(msqid: Int, cmd: Int, buf: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMsgget(key: Int, msgflg: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMsgrcv(msqid: Int, msgp: Long, msgsz: Long, msgtyp: Long, msgflg: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMsgsnd(msqid: Int, msgp: Long, msgsz: Long, msgflg: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFallocate(fd: Int, mode: Int, offset: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixFallocate(fd: Int, offset: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxReadahead(fd: Int, offset: Long, count: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGetxattr(path: Long, name: Long, value: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxLgetxattr(path: Long, name: Long, value: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFgetxattr(filedes: Int, name: Long, value: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSetxattr(path: Long, name: Long, value: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxLsetxattr(path: Long, name: Long, value: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFsetxattr(filedes: Int, name: Long, value: Long, size: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxListxattr(path: Long, list: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxLlistxattr(path: Long, list: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFlistxattr(filedes: Int, list: Long, size: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxRemovexattr(path: Long, name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxLremovexattr(path: Long, name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFremovexattr(filedes: Int, name: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSignalfd(fd: Int, mask: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxTimerfdCreate(clockid: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxTimerfdGettime(fd: Int, currValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxTimerfdSettime(fd: Int, flags: Int, newValue: Long, oldValue: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxQuotactl(cmd: Int, special: Long, id: Int, data: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxEpollPwait(epfd: Int, events: Long, maxevents: Int, timeout: Int, sigmask: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxDup3(oldfd: Int, newfd: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSigtimedwait(set: Long, info: Long, timeout: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSigwaitinfo(set: Long, info: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxAccept4(fd: Int, addr: Long, len: Long, flg: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxReboot(howTo: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSetfsgid(gid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSetfsuid(uid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMkfifoat(dirfd: Int, pathname: Long, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSyncFileRange(fd: Int, offset: Long, nbytes: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixMadvise(addr: Long, len: Long, advice: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMremap(addr: Long, len: Long, newLen: Long, flags: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxRemapFilePages(addr: Long, size: Long, prot: Int, pgoff: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxMkstemps(template: Long, suffixlen: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxVhangup(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSync()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSyncfs(fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSyscall(num: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSchedSetaffinity(pid: Int, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxEpollCreate(size: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxEpollCreate1(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxEpollWait(epfd: Int, events: Long, maxevents: Int, timeout: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxEpollCtl(epfd: Int, op: Int, fd: Int, event: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxUnshare(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxUmount(target: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxTee(fdIn: Int, fdOut: Int, len: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSplice(fdIn: Int, offIn: Long, fdOut: Int, offOut: Long, len: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxEventfd(initval: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxEventfdRead(fd: Int, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxEventfdWrite(fd: Int, value: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval(pid: Int, tp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSchedSetparam(pid: Int, param: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSetns(fd: Int, nstype: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSwapoff(path: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxVmsplice(fd: Int, iov: Long, nrSegs: Long, flags: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPersonality(persona: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSchedGetparam(pid: Int, param: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxClone(cb: Long, childStack: Long, flags: Int, arg: Long, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(pid: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxClockNanosleep(clkId: Int, flags: Int, rqtp: Long, rmtp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxUmount2(target: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSwapon(path: Long, swapflags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSchedSetscheduler(pid: Int, policy: Int, param: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSendfile(outFd: Int, inFd: Int, offset: Long, count: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSigaltstack(ss: Long, oss: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGetdtablesize(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGetgrouplist(user: Long, group: Int, groups: Long, ngroups: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawn(pid: Long, path: Long, fileActions: Long, attrp: Long, argv: Long, envp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnp(pid: Long, file: Long, fileActions: Long, attrp: Long, argv: Long, envp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(attr: Long, default: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(attr: Long, default: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(attr: Long, default: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(attr: Long, default: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(attr: Long, flags: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(attr: Long, flags: Short): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(attr: Long, flags: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(attr: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(attr: Long, flags: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(attr: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(attr: Long, param: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(attr: Long, param: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(actions: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(actions: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(actions: Long, fd: Int, path: Long, oflag: Int, mode: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(actions: Long, fd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(actions: Long, fd: Int, newfd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFreadUnlocked(buf: Long, size: Long, nobj: Long, stream: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(fd: Int, wd: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxInotifyInit(): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxInotifyInit1(flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxInotifyAddWatch(fd: Int, path: Long, mask: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFanotifyInit(flags: Int, eventFFlags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGethostid(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxKlogctl(syslogType: Int, bufp: Long, len: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxNameToHandleAt(dirfd: Int, path: Long, handle: Long, mountId: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxOpenByHandleAt(mountFd: Int, handle: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGetnetbyname(name: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGetnetbyaddr(net: Int, type: Int): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGetnetent(): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSetnetent(stayopen: Int)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxEndnetent()

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFallocate64(fd: Int, mode: Int, offset: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFgetpos64(stream: Long, ptr: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxFopen64(filename: Long, mode: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxPosixFallocate64(fd: Int, offset: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxSendfile64(outFd: Int, inFd: Int, offset: Long, count: Long): Long

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxTmpfile64(): Long
}

public actual fun getspnamR(name: ConstPtr, spbuf: MutPtr, buf: MutPtr, buflen: SizeT, spbufp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxGetspnamR(name.rawAddress, spbuf.rawAddress, buf.rawAddress, buflen.toLong(), spbufp.rawAddress)

public actual fun mqOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MqdT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMqOpen(name.rawAddress, oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun mqClose(mqd: MqdT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMqClose(mqd)

public actual fun mqUnlink(name: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMqUnlink(name.rawAddress)

public actual fun mqReceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMqReceive(mqd, msgPtr.rawAddress, msgLen.toLong(), msgPrio.rawAddress)

public actual fun mqTimedreceive(mqd: MqdT, msgPtr: MutPtr, msgLen: SizeT, msgPrio: MutPtr, absTimeout: ConstPtr): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMqTimedreceive(mqd, msgPtr.rawAddress, msgLen.toLong(), msgPrio.rawAddress, absTimeout.rawAddress)

public actual fun mqSend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMqSend(mqd, msgPtr.rawAddress, msgLen.toLong(), msgPrio.toInt())

public actual fun mqTimedsend(mqd: MqdT, msgPtr: ConstPtr, msgLen: SizeT, msgPrio: CUint, absTimeout: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMqTimedsend(mqd, msgPtr.rawAddress, msgLen.toLong(), msgPrio.toInt(), absTimeout.rawAddress)

public actual fun mqGetattr(mqd: MqdT, attr: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMqGetattr(mqd, attr.rawAddress)

public actual fun mqSetattr(mqd: MqdT, newattr: ConstPtr, oldattr: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMqSetattr(mqd, newattr.rawAddress, oldattr.rawAddress)

public actual fun mrand48(): CLong =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMrand48()

public actual fun seed48(xseed: MutPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSeed48(xseed.rawAddress))

public actual fun lcong48(p: MutPtr) {
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxLcong48(p.rawAddress)
}

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxLutimes(file.rawAddress, times.rawAddress)

public actual fun shmOpen(name: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxShmOpen(name.rawAddress, oflag, mode.toInt())

public actual fun shmUnlink(name: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxShmUnlink(name.rawAddress)

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFtok(pathname.rawAddress, projId)

public actual fun semget(key: KeyT, nsems: CInt, semflag: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSemget(key, nsems, semflag)

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSemop(semid, sops.rawAddress, nsops.toLong())

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun msgctl(msqid: CInt, cmd: CInt, buf: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMsgctl(msqid, cmd, buf.rawAddress)

public actual fun msgget(key: KeyT, msgflg: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMsgget(key, msgflg)

public actual fun msgrcv(msqid: CInt, msgp: MutPtr, msgsz: SizeT, msgtyp: CLong, msgflg: CInt): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMsgrcv(msqid, msgp.rawAddress, msgsz.toLong(), msgtyp, msgflg)

public actual fun msgsnd(msqid: CInt, msgp: ConstPtr, msgsz: SizeT, msgflg: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMsgsnd(msqid, msgp.rawAddress, msgsz.toLong(), msgflg)

public actual fun fallocate(fd: CInt, mode: CInt, offset: OffT, len: OffT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFallocate(fd, mode, offset, len)

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixFallocate(fd, offset, len)

public actual fun readahead(fd: CInt, offset: Off64T, count: SizeT): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxReadahead(fd, offset, count.toLong())

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxGetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong())

public actual fun lgetxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxLgetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong())

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFgetxattr(filedes, name.rawAddress, value.rawAddress, size.toLong())

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong(), flags)

public actual fun lsetxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxLsetxattr(path.rawAddress, name.rawAddress, value.rawAddress, size.toLong(), flags)

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFsetxattr(filedes, name.rawAddress, value.rawAddress, size.toLong(), flags)

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxListxattr(path.rawAddress, list.rawAddress, size.toLong())

public actual fun llistxattr(path: ConstPtr, list: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxLlistxattr(path.rawAddress, list.rawAddress, size.toLong())

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFlistxattr(filedes, list.rawAddress, size.toLong())

public actual fun removexattr(path: ConstPtr, name: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxRemovexattr(path.rawAddress, name.rawAddress)

public actual fun lremovexattr(path: ConstPtr, name: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxLremovexattr(path.rawAddress, name.rawAddress)

public actual fun fremovexattr(filedes: CInt, name: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFremovexattr(filedes, name.rawAddress)

public actual fun signalfd(fd: CInt, mask: ConstPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSignalfd(fd, mask.rawAddress, flags)

public actual fun timerfdCreate(clockid: ClockidT, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxTimerfdCreate(clockid.toInt(), flags)

public actual fun timerfdGettime(fd: CInt, currValue: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxTimerfdGettime(fd, currValue.rawAddress)

public actual fun timerfdSettime(fd: CInt, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxTimerfdSettime(fd, flags, newValue.rawAddress, oldValue.rawAddress)

public actual fun quotactl(cmd: CInt, special: ConstPtr, id: CInt, data: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxQuotactl(cmd, special.rawAddress, id, data.rawAddress)

public actual fun epollPwait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt, sigmask: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxEpollPwait(epfd, events.rawAddress, maxevents, timeout, sigmask.rawAddress)

public actual fun dup3(oldfd: CInt, newfd: CInt, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxDup3(oldfd, newfd, flags)

public actual fun sigtimedwait(set: ConstPtr, info: MutPtr, timeout: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSigtimedwait(set.rawAddress, info.rawAddress, timeout.rawAddress)

public actual fun sigwaitinfo(set: ConstPtr, info: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSigwaitinfo(set.rawAddress, info.rawAddress)

public actual fun accept4(fd: CInt, addr: MutPtr, len: MutPtr, flg: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxAccept4(fd, addr.rawAddress, len.rawAddress, flg)

public actual fun reboot(howTo: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxReboot(howTo)

public actual fun setfsgid(gid: GidT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSetfsgid(gid)

public actual fun setfsuid(uid: UidT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSetfsuid(uid)

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMkfifoat(dirfd, pathname.rawAddress, mode.toInt())

public actual fun syncFileRange(fd: CInt, offset: Off64T, nbytes: Off64T, flags: CUint): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSyncFileRange(fd, offset, nbytes, flags.toInt())

public actual fun posixMadvise(addr: MutPtr, len: SizeT, advice: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixMadvise(addr.rawAddress, len.toLong(), advice)

public actual fun mremap(addr: MutPtr, len: SizeT, newLen: SizeT, flags: CInt, vararg variadicArgs: Long): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMremap(addr.rawAddress, len.toLong(), newLen.toLong(), flags, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L))

public actual fun remapFilePages(addr: MutPtr, size: SizeT, prot: CInt, pgoff: SizeT, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxRemapFilePages(addr.rawAddress, size.toLong(), prot, pgoff.toLong(), flags)

public actual fun mkstemps(template: MutPtr, suffixlen: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxMkstemps(template.rawAddress, suffixlen)

public actual fun vhangup(): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxVhangup()

public actual fun sync() {
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSync()
}

public actual fun syncfs(fd: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSyncfs(fd)

public actual fun syscall(num: CLong, vararg variadicArgs: Long): CLong =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSyscall(num, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedSetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSchedSetaffinity(pid, cpusetsize.toLong(), cpuset.rawAddress)

public actual fun epollCreate(size: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxEpollCreate(size)

public actual fun epollCreate1(flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxEpollCreate1(flags)

public actual fun epollWait(epfd: CInt, events: MutPtr, maxevents: CInt, timeout: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxEpollWait(epfd, events.rawAddress, maxevents, timeout)

public actual fun epollCtl(epfd: CInt, op: CInt, fd: CInt, event: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxEpollCtl(epfd, op, fd, event.rawAddress)

public actual fun unshare(flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxUnshare(flags)

public actual fun umount(target: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxUmount(target.rawAddress)

public actual fun tee(fdIn: CInt, fdOut: CInt, len: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxTee(fdIn, fdOut, len.toLong(), flags.toInt())

public actual fun splice(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSplice(fdIn, offIn.rawAddress, fdOut, offOut.rawAddress, len.toLong(), flags.toInt())

public actual fun eventfd(initval: CUint, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxEventfd(initval.toInt(), flags)

public actual fun eventfdRead(fd: CInt, value: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxEventfdRead(fd, value.rawAddress)

public actual fun eventfdWrite(fd: CInt, value: EventfdT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxEventfdWrite(fd, value.toLong())

public actual fun schedRrGetInterval(pid: PidT, tp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval(pid, tp.rawAddress)

public actual fun schedSetparam(pid: PidT, param: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSchedSetparam(pid, param.rawAddress)

public actual fun setns(fd: CInt, nstype: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSetns(fd, nstype)

public actual fun swapoff(path: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSwapoff(path.rawAddress)

public actual fun vmsplice(fd: CInt, iov: ConstPtr, nrSegs: SizeT, flags: CUint): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxVmsplice(fd, iov.rawAddress, nrSegs.toLong(), flags.toInt())

public actual fun personality(persona: CUlong): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPersonality(persona.toLong())

public actual fun schedGetparam(pid: PidT, param: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSchedGetparam(pid, param.rawAddress)

public actual fun clone(cb: CloneCbCallback, childStack: MutPtr, flags: CInt, arg: MutPtr, vararg variadicArgs: Long): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxClone(0L, childStack.rawAddress, flags, arg.rawAddress, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun schedGetscheduler(pid: PidT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(pid)

public actual fun clockNanosleep(clkId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxClockNanosleep(clkId.toInt(), flags, rqtp.rawAddress, rmtp.rawAddress)

public actual fun umount2(target: ConstPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxUmount2(target.rawAddress, flags)

public actual fun swapon(path: ConstPtr, swapflags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSwapon(path.rawAddress, swapflags)

public actual fun schedSetscheduler(pid: PidT, policy: CInt, param: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSchedSetscheduler(pid, policy, param.rawAddress)

public actual fun sendfile(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSendfile(outFd, inFd, offset.rawAddress, count.toLong())

public actual fun sigaltstack(ss: ConstPtr, oss: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSigaltstack(ss.rawAddress, oss.rawAddress)

public actual fun getdtablesize(): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxGetdtablesize()

public actual fun getgrouplist(user: ConstPtr, group: GidT, groups: MutPtr, ngroups: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxGetgrouplist(user.rawAddress, group, groups.rawAddress, ngroups.rawAddress)

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawn(pid.rawAddress, path.rawAddress, fileActions.rawAddress, attrp.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnp(pid.rawAddress, file.rawAddress, fileActions.rawAddress, attrp.rawAddress, argv.rawAddress, envp.rawAddress)

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit(attr.rawAddress)

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(attr.rawAddress)

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(attr.rawAddress, default.rawAddress)

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(attr.rawAddress, default.rawAddress)

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(attr.rawAddress, default.rawAddress)

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(attr.rawAddress, default.rawAddress)

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(attr.rawAddress, flags.rawAddress)

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(attr.rawAddress, flags)

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(attr.rawAddress, flags.rawAddress)

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(attr.rawAddress, flags)

public actual fun posixSpawnattrGetschedpolicy(attr: ConstPtr, flags: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(attr.rawAddress, flags.rawAddress)

public actual fun posixSpawnattrSetschedpolicy(attr: MutPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(attr.rawAddress, flags)

public actual fun posixSpawnattrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(attr.rawAddress, param.rawAddress)

public actual fun posixSpawnattrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(attr.rawAddress, param.rawAddress)

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(actions.rawAddress)

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(actions.rawAddress)

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(actions.rawAddress, fd, path.rawAddress, oflag, mode.toInt())

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(actions.rawAddress, fd)

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(actions.rawAddress, fd, newfd)

public actual fun freadUnlocked(buf: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFreadUnlocked(buf.rawAddress, size.toLong(), nobj.toLong(), stream.rawAddress).toULong()

public actual fun inotifyRmWatch(fd: CInt, wd: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(fd, wd)

public actual fun inotifyInit(): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxInotifyInit()

public actual fun inotifyInit1(flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxInotifyInit1(flags)

public actual fun inotifyAddWatch(fd: CInt, path: ConstPtr, mask: UInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxInotifyAddWatch(fd, path.rawAddress, mask.toInt())

public actual fun fanotifyInit(flags: CUint, eventFFlags: CUint): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFanotifyInit(flags.toInt(), eventFFlags.toInt())

public actual fun gethostid(): CLong =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxGethostid()

public actual fun klogctl(syslogType: CInt, bufp: MutPtr, len: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxKlogctl(syslogType, bufp.rawAddress, len)

public actual fun nameToHandleAt(dirfd: CInt, path: ConstPtr, handle: MutPtr, mountId: MutPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxNameToHandleAt(dirfd, path.rawAddress, handle.rawAddress, mountId.rawAddress, flags)

public actual fun openByHandleAt(mountFd: CInt, handle: MutPtr, flags: CInt): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxOpenByHandleAt(mountFd, handle.rawAddress, flags)

public actual fun getnetbyname(name: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxGetnetbyname(name.rawAddress))

public actual fun getnetbyaddr(net: UInt, type: CInt): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxGetnetbyaddr(net.toInt(), type))

public actual fun getnetent(): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxGetnetent())

public actual fun setnetent(stayopen: CInt) {
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSetnetent(stayopen)
}

public actual fun endnetent() {
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxEndnetent()
}

public actual fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFallocate64(fd, mode, offset, len)

public actual fun fgetpos64(stream: MutPtr, ptr: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFgetpos64(stream.rawAddress, ptr.rawAddress)

public actual fun fopen64(filename: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxFopen64(filename.rawAddress, mode.rawAddress))

public actual fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxPosixFallocate64(fd, offset, len)

public actual fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT =
    LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxSendfile64(outFd, inFd, offset.rawAddress, count.toLong())

public actual fun tmpfile64(): MutPtr =
    MutPtr(LibcUnixLinuxlikeLinuxJniBridge.rustlangLibcUnixLinuxlikeLinuxTmpfile64())

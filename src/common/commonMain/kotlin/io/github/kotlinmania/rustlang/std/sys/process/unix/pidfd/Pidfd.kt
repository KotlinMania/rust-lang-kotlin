// port-lint: source sys/process/unix/pidfd.rs
package io.github.kotlinmania.rustlang.std.sys.process.unix.pidfd

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.CInt
import io.github.kotlinmania.rustlang.libc.SysPidfdOpen
import io.github.kotlinmania.rustlang.libc.SysPidfdSendSignal
import io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.bits.types.siginfot.syscall
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.pidfd.PIDFD_GET_INFO
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.pidfd.PIDFD_INFO_EXIT
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.pidfd.PIDFD_INFO_PID
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.pidfd.PIDFD_SIGNAL_PROCESS_GROUP
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.pidfd.PidfdInfo
import io.github.kotlinmania.rustlang.libc.new.musl.signal.SiginfoT
import io.github.kotlinmania.rustlang.libc.unix.bsd.WNOHANG
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.P_PIDFD
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIGKILL
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.WEXITED
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.Ioctl
import io.github.kotlinmania.rustlang.libc.wasi.ECHILD
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.ENOTTY
import io.github.kotlinmania.rustlang.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.os.fd.RawFd
import io.github.kotlinmania.rustlang.process.id
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.sys.AsInner
import io.github.kotlinmania.rustlang.sys.FromInner
import io.github.kotlinmania.rustlang.sys.IntoInner
import io.github.kotlinmania.rustlang.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.sys.pal.unix.cvtR
import io.github.kotlinmania.rustlang.sys.process.unix.unix.ExitStatus

public open class PidFd(
    public var value: FileDesc,
) : AsInner<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, PidFd>, FromRawFd<PidFd>, IntoRawFd {
    public var field0: FileDesc
        get() = value
        set(v) {
            value = v
        }

    public open fun kill(): Result<Unit, Error> = this.sendSignal(SIGKILL)

    @kotlin.test.Test
    public open fun pid(): Result<UInt, Error> {
        // since kernel 6.13
        // https://lore.kernel.org/all/20241010155401.2268522-1-luca.boccassi@gmail.com/
        val localStorageL31C8 = AddressableLocal(PidfdInfo(), ObjectSlotLayout<PidfdInfo>())
        val pidfdInfo by localStorageL31C8
        pidfdInfo.mask = PIDFD_INFO_PID.toULong()
        run {
            val matchVal = cvt(Ioctl(this.value.asRawFd(), PIDFD_GET_INFO, RustMutableReference(localStorageL31C8.asMutPtr(), localStorageL31C8.layout)))
            when {
                matchVal is Result.Ok<*, *> -> {}
                matchVal is Result.Err<*, *> && when (val matchVal1 = (matchVal as Result.Err<*, *>).error.rawOsError()) {
                    EINVAL, ENOTTY -> true
                    else -> false
                } -> {
                    val e = (matchVal as Result.Err<*, *>).error
                    // kernel doesn't support that ioctl, try the glibc helper that looks at procfs
                    weak(pidfdGetpid, pidfd, RawFd, libc, pidT)
                    when (val matchVal2 = pidfdGetpid.get()) {
                        null -> {}
                        else -> {
                            val pidfdGetpid = matchVal2
                            val pid: CInt = when (val tryResult = cvt(pidfdGetpid(this.value.asRawFd()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                            return Result.Ok(pid.toUInt())
                        }
                    }
                    return Result.Err(e)
                }
                else -> {
                    val e1 = (matchVal as Result.Err<*, *>).error
                    return Result.Err(e1)
                }
            }
        }
        return Result.Ok(pidfdInfo.pid)
    }

    public open fun exitForReapedChild(): Result<ExitStatus, Error> {
        // since kernel 6.15
        // https://lore.kernel.org/linux-fsdevel/20250305-work-pidfs-killOnLastClose-v3-0-c8c3d8361705@kernel.org/T/
        val localStorageL56C8 = AddressableLocal(PidfdInfo(), ObjectSlotLayout<PidfdInfo>())
        val pidfdInfo by localStorageL56C8
        pidfdInfo.mask = PIDFD_INFO_EXIT.toULong()
        when (val tryResult = cvt(Ioctl(this.value.asRawFd(), PIDFD_GET_INFO, RustMutableReference(localStorageL56C8.asMutPtr(), localStorageL56C8.layout)))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok(ExitStatus.new(pidfdInfo.exitCode))
    }

    public open fun waitid(options: CInt): Result<ExitStatus?, Error> {
        val localStorageL63C8 = AddressableLocal(SiginfoT(), ObjectSlotLayout<SiginfoT>())
        val siginfo by localStorageL63C8
        val r = cvtR({ io.github.kotlinmania.rustlang.libc.waitid(P_PIDFD, this.value.asRawFd().toUInt(), RustMutableReference(localStorageL63C8.asMutPtr(), localStorageL63C8.layout), options) })
        when {
            r is Result.Err<*, *> && (r as Result.Err<*, *>).error.rawOsError() == ECHILD -> {
                val waitidErr = (r as Result.Err<*, *>).error
                // already reaped
                run {
                    val matchVal = this.exitForReapedChild()
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val exitStatus = matchVal.value
                        return Result.Ok(exitStatus)
                    }; is Result.Err<*, *> -> {
                        return Result.Err(waitidErr)
                    } }
                }
            }
            r is Result.Err<*, *> -> {
                val e = (r as Result.Err<*, *>).error
                return Result.Err(e)
            }
            else -> {}
        }
        return if (siginfo.siPid() == 0) {
            Result.Ok(null)
        } else {
            Result.Ok(ExitStatus.fromWaitidSiginfo(siginfo))
        }
    }

    public open fun sendSignal(signal: Int): Result<Unit, Error> = cvt(syscall(SysPidfdSendSignal, this.value.asRawFd().toLong(), signal.toLong(), ConstPtr(0L).toLong(), 0L)).map({ Unit })

    public open fun sendProcessGroupSignal(signal: Int): Result<Unit, Error> {
        // since kernel 6.9
        // https://lore.kernel.org/all/20240210-chihuahua-hinzog-3945b6abd44a@brauner/
        return cvt(syscall(SysPidfdSendSignal, this.value.asRawFd().toLong(), signal.toLong(), ConstPtr(0L).toLong(), PIDFD_SIGNAL_PROCESS_GROUP.toLong())).map({ Unit })
    }

    public open fun wait(): Result<ExitStatus, Error> {
        val r = when (val tryResult = this.waitid(WEXITED)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return when (r) {
            null -> error("waitid with WEXITED should not return None")
            else -> {
                val exitStatus = r
                Result.Ok(exitStatus)
            }
        }
    }

    public open fun tryWait(): Result<ExitStatus?, Error> = this.waitid(WEXITED or WNOHANG)

    public override fun asInner(): FileDesc = this.value

    public override fun intoInner(): FileDesc = this.value

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public companion object {
        @kotlin.test.Test
        public fun currentProcess(): Result<PidFd, Error> {
            val pid = id()
            val pidfd = when (val tryResult = cvt(syscall(SysPidfdOpen, pid.toLong(), 0L))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            return Result.Ok(PidFd.fromRawFd(pidfd.toInt()))
        }

        public fun fromInner(inner: FileDesc): PidFd = PidFd(inner)

        public fun fromRawFd(fd: RawFd): PidFd = PidFd(FileDesc.fromRawFd(fd))
    }
}

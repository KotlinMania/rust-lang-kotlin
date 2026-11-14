// port-lint: source unix/linux_like/linux/musl/lfs64.rs
package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.ModeT
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.Stat
import io.github.kotlinmania.rustlang.libc.Statfs
import io.github.kotlinmania.rustlang.libc.Statvfs
import io.github.kotlinmania.rustlang.libc.creat
import io.github.kotlinmania.rustlang.libc.fallocate
import io.github.kotlinmania.rustlang.libc.fgetpos
import io.github.kotlinmania.rustlang.libc.fopen
import io.github.kotlinmania.rustlang.libc.freopen
import io.github.kotlinmania.rustlang.libc.fseeko
import io.github.kotlinmania.rustlang.libc.fsetpos
import io.github.kotlinmania.rustlang.libc.fstat
import io.github.kotlinmania.rustlang.libc.fstatat
import io.github.kotlinmania.rustlang.libc.fstatfs
import io.github.kotlinmania.rustlang.libc.fstatvfs
import io.github.kotlinmania.rustlang.libc.ftello
import io.github.kotlinmania.rustlang.libc.ftruncate
import io.github.kotlinmania.rustlang.libc.getrlimit
import io.github.kotlinmania.rustlang.libc.lseek
import io.github.kotlinmania.rustlang.libc.lstat
import io.github.kotlinmania.rustlang.libc.mmap
import io.github.kotlinmania.rustlang.libc.pread
import io.github.kotlinmania.rustlang.libc.preadv
import io.github.kotlinmania.rustlang.libc.prlimit
import io.github.kotlinmania.rustlang.libc.pwrite
import io.github.kotlinmania.rustlang.libc.pwritev
import io.github.kotlinmania.rustlang.libc.readdir
import io.github.kotlinmania.rustlang.libc.sendfile
import io.github.kotlinmania.rustlang.libc.setrlimit
import io.github.kotlinmania.rustlang.libc.tmpfile
import io.github.kotlinmania.rustlang.libc.truncate
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Off64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.posixFallocate
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.posixFadvise
import io.github.kotlinmania.rustlang.libc.unix.readdirR

public fun creat64(path: ConstPtr, mode: ModeT): CInt = creat(path, mode)

public fun fallocate64(fd: CInt, mode: CInt, offset: Off64T, len: Off64T): CInt = fallocate(fd, mode, offset, len)

public fun fgetpos64(stream: MutPtr, pos: MutPtr): CInt = fgetpos(stream, pos)

public fun fopen64(pathname: ConstPtr, mode: ConstPtr): MutPtr = fopen(pathname, mode)

public fun freopen64(pathname: ConstPtr, mode: ConstPtr, stream: MutPtr): MutPtr = freopen(pathname, mode, stream)

public fun fseeko64(stream: MutPtr, offset: Off64T, whence: CInt): CInt = fseeko(stream, offset, whence)

public fun fsetpos64(stream: MutPtr, pos: ConstPtr): CInt = fsetpos(stream, pos)

public fun fstat64(fildes: CInt, buf: MutPtr): CInt = fstat(fildes, buf)

public fun fstatat64(fd: CInt, path: ConstPtr, buf: MutPtr, flag: CInt): CInt = fstatat(fd, path, buf, flag)

public fun fstatfs64(fd: CInt, buf: MutPtr): CInt = fstatfs(fd, buf)

public fun fstatvfs64(fd: CInt, buf: MutPtr): CInt = fstatvfs(fd, buf)

public fun ftello64(stream: MutPtr): Off64T = ftello(stream)

public fun ftruncate64(fd: CInt, length: Off64T): CInt = ftruncate(fd, length)

public fun getrlimit64(resource: CInt, rlim: MutPtr): CInt = getrlimit(resource, rlim)

public fun lseek64(fd: CInt, offset: Off64T, whence: CInt): Off64T = lseek(fd, offset, whence)

public fun lstat64(path: ConstPtr, buf: MutPtr): CInt = lstat(path, buf)

public fun mmap64(addr: MutPtr, length: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: Off64T): MutPtr = mmap(addr, length, prot, flags, fd, offset)

public fun posixFadvise64(fd: CInt, offset: Off64T, len: Off64T, advice: CInt): CInt = posixFadvise(fd, offset, len, advice)

public fun posixFallocate64(fd: CInt, offset: Off64T, len: Off64T): CInt = posixFallocate(fd, offset, len)

public fun pread64(fd: CInt, buf: MutPtr, count: SizeT, offset: Off64T): SsizeT = pread(fd, buf, count, offset)

public fun preadv64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT = preadv(fd, iov, iovcnt, offset)

public fun prlimit64(pid: PidT, resource: CInt, newLimit: ConstPtr, oldLimit: MutPtr): CInt = prlimit(pid, resource, newLimit, oldLimit)

public fun pwrite64(fd: CInt, buf: ConstPtr, count: SizeT, offset: Off64T): SsizeT = pwrite(fd, buf, count, offset)

public fun pwritev64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT = pwritev(fd, iov, iovcnt, offset)

public fun readdir64(dirp: MutPtr): MutPtr = MutPtr((readdir(dirp)).rawAddress)

public fun readdir64R(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt = readdirR(dirp, entry, result)

public fun sendfile64(outFd: CInt, inFd: CInt, offset: MutPtr, count: SizeT): SsizeT = sendfile(outFd, inFd, offset, count)

public fun setrlimit64(resource: CInt, rlim: ConstPtr): CInt = setrlimit(resource, rlim)

public fun stat64(pathname: ConstPtr, statbuf: MutPtr): CInt = Stat(pathname, statbuf)

public fun statfs64(pathname: ConstPtr, buf: MutPtr): CInt = Statfs(pathname, buf)

public fun statvfs64(path: ConstPtr, buf: MutPtr): CInt = Statvfs(path, buf)

public fun tmpfile64(): MutPtr = tmpfile()

public fun truncate64(path: ConstPtr, length: Off64T): CInt = truncate(path, length)

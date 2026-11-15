#ifndef RUSTLANG_LIBC_UNIX_LINUXLIKE_LIBC_UNIX_LINUXLIKE_WRAPPER_H
#define RUSTLANG_LIBC_UNIX_LINUXLIKE_LIBC_UNIX_LINUXLIKE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcUnixLinuxlikeIoctl(int32_t fd, uint64_t request, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeLibcCurrentSigrtmax(void);
int32_t rustlangLibcUnixLinuxlikeLibcCurrentSigrtmin(void);
int32_t rustlangLibcUnixLinuxlikeSemDestroy(void* sem);
int32_t rustlangLibcUnixLinuxlikeSemInit(void* sem, int32_t pshared, uint32_t value);
int32_t rustlangLibcUnixLinuxlikeFdatasync(int32_t fd);
int32_t rustlangLibcUnixLinuxlikeMincore(void* addr, uint64_t len, void* vec);
int32_t rustlangLibcUnixLinuxlikeClockGetres(int32_t clkId, void* tp);
int32_t rustlangLibcUnixLinuxlikeClockGettime(int32_t clkId, void* tp);
int32_t rustlangLibcUnixLinuxlikeClockSettime(int32_t clkId, void* tp);
int32_t rustlangLibcUnixLinuxlikeClockGetcpuclockid(int32_t pid, void* clkId);
int32_t rustlangLibcUnixLinuxlikeGetitimer(int32_t which, void* currValue);
int32_t rustlangLibcUnixLinuxlikeSetitimer(int32_t which, void* newValue, void* oldValue);
int32_t rustlangLibcUnixLinuxlikeDirfd(void* dirp);
void* rustlangLibcUnixLinuxlikeMemalign(uint64_t align, uint64_t size);
int32_t rustlangLibcUnixLinuxlikeSetgroups(uint64_t ngroups, void* ptr);
int32_t rustlangLibcUnixLinuxlikePipe2(void* fds, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeStatfs(const char* path, void* buf);
int32_t rustlangLibcUnixLinuxlikeFstatfs(int32_t fd, void* buf);
void* rustlangLibcUnixLinuxlikeMemrchr(void* cx, int32_t c, uint64_t n);
int32_t rustlangLibcUnixLinuxlikePosixFadvise(int32_t fd, int64_t offset, int64_t len, int32_t advise);
int32_t rustlangLibcUnixLinuxlikeFutimens(int32_t fd, void* times);
int32_t rustlangLibcUnixLinuxlikeUtimensat(int32_t dirfd, const char* path, void* times, int32_t flag);
void* rustlangLibcUnixLinuxlikeDuplocale(void* base);
void rustlangLibcUnixLinuxlikeFreelocale(void* loc);
void* rustlangLibcUnixLinuxlikeNewlocale(int32_t mask, const char* locale, void* base);
void* rustlangLibcUnixLinuxlikeUselocale(void* loc);
int32_t rustlangLibcUnixLinuxlikeMknodat(int32_t dirfd, const char* pathname, uint32_t mode, int32_t dev);
int32_t rustlangLibcUnixLinuxlikePtsnameR(int32_t fd, char* buf, uint64_t buflen);
int32_t rustlangLibcUnixLinuxlikeClearenv(void);
int32_t rustlangLibcUnixLinuxlikeWaitid(uint32_t idtype, uint32_t id, void* infop, int32_t options);
int32_t rustlangLibcUnixLinuxlikeGetresuid(void* ruid, void* euid, void* suid);
int32_t rustlangLibcUnixLinuxlikeGetresgid(void* rgid, void* egid, void* sgid);
int32_t rustlangLibcUnixLinuxlikeAcct(const char* filename);
int32_t rustlangLibcUnixLinuxlikeBrk(void* addr);
void* rustlangLibcUnixLinuxlikeSbrk(int64_t increment);
int32_t rustlangLibcUnixLinuxlikeVfork(void);
int32_t rustlangLibcUnixLinuxlikeSetresgid(int32_t rgid, int32_t egid, int32_t sgid);
int32_t rustlangLibcUnixLinuxlikeSetresuid(int32_t ruid, int32_t euid, int32_t suid);
int32_t rustlangLibcUnixLinuxlikeWait4(int32_t pid, void* status, int32_t options, void* rusage);
int32_t rustlangLibcUnixLinuxlikeLoginTty(int32_t fd);
int32_t rustlangLibcUnixLinuxlikeExecvpe(const char* file, void* argv, void* envp);
int32_t rustlangLibcUnixLinuxlikeFexecve(int32_t fd, void* argv, void* envp);
int32_t rustlangLibcUnixLinuxlikeGetifaddrs(void* ifap);
void rustlangLibcUnixLinuxlikeFreeifaddrs(void* ifa);
int32_t rustlangLibcUnixLinuxlikeBind(int32_t arg0Socket, void* address, uint32_t addressLen);
int64_t rustlangLibcUnixLinuxlikeWritev(int32_t fd, void* iov, int32_t iovcnt);
int64_t rustlangLibcUnixLinuxlikeReadv(int32_t fd, void* iov, int32_t iovcnt);
int64_t rustlangLibcUnixLinuxlikeSendmsg(int32_t fd, void* msg, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeRecvmsg(int32_t fd, void* msg, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeUname(void* buf);
char* rustlangLibcUnixLinuxlikeStrchrnul(const char* s, int32_t c);
uint64_t rustlangLibcUnixLinuxlikeStrftime(char* s, uint64_t max, const char* format, void* tm);
uint64_t rustlangLibcUnixLinuxlikeStrftimeL(char* s, uint64_t max, const char* format, void* tm, void* locale);
char* rustlangLibcUnixLinuxlikeStrptime(const char* s, const char* format, void* tm);
int32_t rustlangLibcUnixLinuxlikeMkostemp(char* arg0Template, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeMkostemps(char* arg0Template, int32_t suffixlen, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeGetdomainname(char* name, uint64_t len);
int32_t rustlangLibcUnixLinuxlikeSetdomainname(const char* name, uint64_t len);
void* rustlangLibcUnixLinuxlikeIfNameindex(void);
void rustlangLibcUnixLinuxlikeIfFreenameindex(void* ptr);
int32_t rustlangLibcUnixLinuxlikeGetpwnamR(const char* name, void* pwd, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixLinuxlikeGetpwuidR(int32_t uid, void* pwd, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixLinuxlikeFstatfs64(int32_t fd, void* buf);
int32_t rustlangLibcUnixLinuxlikeStatvfs64(const char* path, void* buf);
int32_t rustlangLibcUnixLinuxlikeFstatvfs64(int32_t fd, void* buf);
int32_t rustlangLibcUnixLinuxlikeStatfs64(const char* path, void* buf);
int32_t rustlangLibcUnixLinuxlikeCreat64(const char* path, uint32_t mode);
int32_t rustlangLibcUnixLinuxlikeFstat64(int32_t fildes, void* buf);
int32_t rustlangLibcUnixLinuxlikeFstatat64(int32_t dirfd, const char* pathname, void* buf, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeFtruncate64(int32_t fd, int64_t length);
int64_t rustlangLibcUnixLinuxlikeLseek64(int32_t fd, int64_t offset, int32_t whence);
int32_t rustlangLibcUnixLinuxlikeLstat64(const char* path, void* buf);
void* rustlangLibcUnixLinuxlikeMmap64(void* addr, uint64_t len, int32_t prot, int32_t flags, int32_t fd, int64_t offset);
int32_t rustlangLibcUnixLinuxlikeOpen64(const char* path, int32_t oflag, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeOpenat64(int32_t fd, const char* path, int32_t oflag, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikePosixFadvise64(int32_t fd, int64_t offset, int64_t len, int32_t advise);
int64_t rustlangLibcUnixLinuxlikePread64(int32_t fd, void* buf, uint64_t count, int64_t offset);
int64_t rustlangLibcUnixLinuxlikePwrite64(int32_t fd, void* buf, uint64_t count, int64_t offset);
void* rustlangLibcUnixLinuxlikeReaddir64(void* dirp);
int32_t rustlangLibcUnixLinuxlikeReaddir64R(void* dirp, void* entry, void* result);
int32_t rustlangLibcUnixLinuxlikeStat64(const char* path, void* buf);
int32_t rustlangLibcUnixLinuxlikeTruncate64(const char* path, int64_t length);
int64_t rustlangLibcUnixLinuxlikePreadv64(int32_t fd, void* iov, int32_t iovcnt, int64_t offset);
int64_t rustlangLibcUnixLinuxlikePwritev64(int32_t fd, void* iov, int32_t iovcnt, int64_t offset);
int32_t rustlangLibcUnixLinuxlikeForkpty(void* amaster, char* name, void* termp, void* winp);
int32_t rustlangLibcUnixLinuxlikeOpenpty(void* amaster, void* aslave, char* name, void* termp, void* winp);
int32_t rustlangLibcUnixLinuxlikeStatx(int32_t dirfd, const char* pathname, int32_t flags, uint32_t mask, void* statxbuf);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_UNIX_LINUXLIKE_LIBC_UNIX_LINUXLIKE_WRAPPER_H */

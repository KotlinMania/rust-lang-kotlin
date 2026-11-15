#ifndef RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_MUSL_LIBC_UNIX_LINUXLIKE_LINUX_MUSL_WRAPPER_H
#define RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_MUSL_LIBC_UNIX_LINUXLIKE_LINUX_MUSL_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcUnixLinuxlikeLinuxMuslGetrlimit(int32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslSetrlimit(int32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslPrlimit(int32_t pid, int32_t resource, void* newLimit, void* oldLimit);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslGettimeofday(void* tp, void* tz);
int64_t rustlangLibcUnixLinuxlikeLinuxMuslPtrace(int32_t request, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslGetpriority(int32_t which, uint32_t who);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslSetpriority(int32_t which, uint32_t who, int32_t prio);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslFanotifyMark(int32_t fd, uint32_t flags, uint64_t mask, int32_t dirfd, const char* path);
int64_t rustlangLibcUnixLinuxlikeLinuxMuslPreadv2(int32_t fd, void* iov, int32_t iovcnt, int64_t offset, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeLinuxMuslPwritev2(int32_t fd, void* iov, int32_t iovcnt, int64_t offset, int32_t flags);
uint64_t rustlangLibcUnixLinuxlikeLinuxMuslGetauxval(uint64_t type);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslRenameat2(int32_t olddirfd, const char* oldpath, int32_t newdirfd, const char* newpath, uint32_t flags);
void rustlangLibcUnixLinuxlikeLinuxMuslExplicitBzero(void* s, uint64_t len);
void* rustlangLibcUnixLinuxlikeLinuxMuslReallocarray(void* ptr, uint64_t nmemb, uint64_t size);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslAdjtimex(void* buf);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslClockAdjtime(uint32_t clkId, void* buf);
char* rustlangLibcUnixLinuxlikeLinuxMuslCtermid(char* s);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslMemfdCreate(const char* name, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslMlock2(void* addr, uint64_t len, uint32_t flags);
uint64_t rustlangLibcUnixLinuxlikeLinuxMuslMallocUsableSize(void* ptr);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslEuidaccess(const char* pathname, int32_t mode);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslEaccess(const char* pathname, int32_t mode);
char* rustlangLibcUnixLinuxlikeLinuxMuslAsctimeR(void* tm, char* buf);
char* rustlangLibcUnixLinuxlikeLinuxMuslDirname(char* path);
char* rustlangLibcUnixLinuxlikeLinuxMuslBasename(char* path);
int64_t rustlangLibcUnixLinuxlikeLinuxMuslGetrandom(void* buf, uint64_t buflen, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddchdirNp(void* actions, const char* path);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslPosixSpawnFileActionsAddfchdirNp(void* actions, int32_t fd);
void* rustlangLibcUnixLinuxlikeLinuxMuslGetutxent(void);
void* rustlangLibcUnixLinuxlikeLinuxMuslGetutxid(void* ut);
void* rustlangLibcUnixLinuxlikeLinuxMuslGetutxline(void* ut);
void* rustlangLibcUnixLinuxlikeLinuxMuslPututxline(void* ut);
void rustlangLibcUnixLinuxlikeLinuxMuslSetutxent(void);
void rustlangLibcUnixLinuxlikeLinuxMuslEndutxent(void);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslUtmpxname(const char* file);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslPthreadTryjoinNp(void* thread, void* retval);
int32_t rustlangLibcUnixLinuxlikeLinuxMuslPthreadTimedjoinNp(void* thread, void* retval, void* abstime);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_MUSL_LIBC_UNIX_LINUXLIKE_LINUX_MUSL_WRAPPER_H */

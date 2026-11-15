#ifndef RUSTLANG_LIBC_UNIX_BSD_LIBC_UNIX_BSD_WRAPPER_H
#define RUSTLANG_LIBC_UNIX_BSD_LIBC_UNIX_BSD_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcUnixBsdGetrlimit(int32_t resource, void* rlim);
int32_t rustlangLibcUnixBsdSetrlimit(int32_t resource, void* rlim);
int32_t rustlangLibcUnixBsdStrerrorR(int32_t errnum, char* buf, uint64_t buflen);
int32_t rustlangLibcUnixBsdAbs(int32_t i);
int64_t rustlangLibcUnixBsdLabs(int64_t i);
int32_t rustlangLibcUnixBsdRand(void);
void rustlangLibcUnixBsdSrand(uint32_t seed);
int32_t rustlangLibcUnixBsdGetifaddrs(void* ifap);
void rustlangLibcUnixBsdFreeifaddrs(void* ifa);
int32_t rustlangLibcUnixBsdSetgroups(int32_t ngroups, void* ptr);
int32_t rustlangLibcUnixBsdSetlogin(const char* name);
int32_t rustlangLibcUnixBsdIoctl(int32_t fd, uint64_t request, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixBsdKqueue(void);
int32_t rustlangLibcUnixBsdUnmount(const char* target, int32_t arg);
void* rustlangLibcUnixBsdGetpwent(void);
void rustlangLibcUnixBsdSetpwent(void);
void rustlangLibcUnixBsdEndpwent(void);
void rustlangLibcUnixBsdEndgrent(void);
void* rustlangLibcUnixBsdGetgrent(void);
const char* rustlangLibcUnixBsdGetprogname(void);
void rustlangLibcUnixBsdSetprogname(const char* name);
int32_t rustlangLibcUnixBsdGetloadavg(void* loadavg, int32_t nelem);
void* rustlangLibcUnixBsdIfNameindex(void);
void rustlangLibcUnixBsdIfFreenameindex(void* ptr);
int32_t rustlangLibcUnixBsdGetpeereid(int32_t arg0Socket, void* euid, void* egid);
int32_t rustlangLibcUnixBsdGlob(const char* pattern, int32_t flags, void* errfunc, void* pglob);
void rustlangLibcUnixBsdGlobfree(void* pglob);
int32_t rustlangLibcUnixBsdPosixMadvise(void* addr, uint64_t len, int32_t advice);
int32_t rustlangLibcUnixBsdShmUnlink(const char* name);
void rustlangLibcUnixBsdSeekdir(void* dirp, int64_t loc);
int64_t rustlangLibcUnixBsdTelldir(void* dirp);
int32_t rustlangLibcUnixBsdMadvise(void* addr, uint64_t len, int32_t advice);
int32_t rustlangLibcUnixBsdMinherit(void* addr, uint64_t len, int32_t inherit);
int32_t rustlangLibcUnixBsdMsync(void* addr, uint64_t len, int32_t flags);
int64_t rustlangLibcUnixBsdRecvfrom(int32_t arg0Socket, void* buf, uint64_t len, int32_t flags, void* addr, void* addrlen);
int32_t rustlangLibcUnixBsdMkstemps(char* arg0Template, int32_t suffixlen);
int32_t rustlangLibcUnixBsdFutimes(int32_t fd, void* times);
char* rustlangLibcUnixBsdNlLanginfo(int32_t item);
int32_t rustlangLibcUnixBsdBind(int32_t arg0Socket, void* address, uint32_t addressLen);
int64_t rustlangLibcUnixBsdWritev(int32_t fd, void* iov, int32_t iovcnt);
int64_t rustlangLibcUnixBsdReadv(int32_t fd, void* iov, int32_t iovcnt);
int64_t rustlangLibcUnixBsdSendmsg(int32_t fd, void* msg, int32_t flags);
int64_t rustlangLibcUnixBsdRecvmsg(int32_t fd, void* msg, int32_t flags);
void rustlangLibcUnixBsdSync(void);
int32_t rustlangLibcUnixBsdGetgrgidR(int32_t gid, void* grp, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixBsdSigaltstack(void* ss, void* oss);
int32_t rustlangLibcUnixBsdSigsuspend(void* mask);
int32_t rustlangLibcUnixBsdSemClose(void* sem);
int32_t rustlangLibcUnixBsdGetdtablesize(void);
int32_t rustlangLibcUnixBsdGetgrnamR(const char* name, void* grp, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixBsdPthreadSigmask(int32_t how, void* set, void* oldset);
void* rustlangLibcUnixBsdSemOpen(const char* name, int32_t oflag, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
void* rustlangLibcUnixBsdGetgrnam(const char* name);
int32_t rustlangLibcUnixBsdPthreadCancel(uint64_t thread);
int32_t rustlangLibcUnixBsdPthreadKill(uint64_t thread, int32_t sig);
int32_t rustlangLibcUnixBsdSchedGetPriorityMin(int32_t policy);
int32_t rustlangLibcUnixBsdSchedGetPriorityMax(int32_t policy);
int32_t rustlangLibcUnixBsdSemUnlink(const char* name);
int32_t rustlangLibcUnixBsdGetpwnamR(const char* name, void* pwd, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixBsdGetpwuidR(int32_t uid, void* pwd, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixBsdSigwait(void* set, void* sig);
int32_t rustlangLibcUnixBsdPthreadAtfork(void* prepare, void* parent, void* child);
void* rustlangLibcUnixBsdGetgrgid(int32_t gid);
void* rustlangLibcUnixBsdPopen(const char* command, const char* mode);
int32_t rustlangLibcUnixBsdFaccessat(int32_t dirfd, const char* pathname, int32_t mode, int32_t flags);
int32_t rustlangLibcUnixBsdPthreadCreate(void* native, void* attr, void* f, void* value);
int32_t rustlangLibcUnixBsdAcct(const char* filename);
int32_t rustlangLibcUnixBsdWait4(int32_t pid, void* status, int32_t options, void* rusage);
int32_t rustlangLibcUnixBsdGetitimer(int32_t which, void* currValue);
int32_t rustlangLibcUnixBsdSetitimer(int32_t which, void* newValue, void* oldValue);
int32_t rustlangLibcUnixBsdRegcomp(void* preg, const char* pattern, int32_t cflags);
int32_t rustlangLibcUnixBsdRegexec(void* preg, const char* input, uint64_t nmatch, void* pmatch, int32_t eflags);
uint64_t rustlangLibcUnixBsdRegerror(int32_t errcode, void* preg, char* errbuf, uint64_t errbufSize);
void rustlangLibcUnixBsdRegfree(void* preg);
uint32_t rustlangLibcUnixBsdArc4random(void);
void rustlangLibcUnixBsdArc4randomBuf(void* buf, uint64_t size);
uint32_t rustlangLibcUnixBsdArc4randomUniform(uint32_t l);
double rustlangLibcUnixBsdDrand48(void);
double rustlangLibcUnixBsdErand48(void* xseed);
int64_t rustlangLibcUnixBsdLrand48(void);
int64_t rustlangLibcUnixBsdNrand48(void* xseed);
int64_t rustlangLibcUnixBsdMrand48(void);
int64_t rustlangLibcUnixBsdJrand48(void* xseed);
void rustlangLibcUnixBsdSrand48(int64_t seed);
void* rustlangLibcUnixBsdSeed48(void* xseed);
void rustlangLibcUnixBsdLcong48(void* p);
int32_t rustlangLibcUnixBsdGetoptLong(int32_t argc, void* argv, const char* optstring, void* longopts, void* longindex);
uint64_t rustlangLibcUnixBsdStrftime(char* buf, uint64_t maxsize, const char* format, void* timeptr);
uint64_t rustlangLibcUnixBsdStrftimeL(char* buf, uint64_t maxsize, const char* format, void* timeptr, void* locale);
char* rustlangLibcUnixBsdDevname(int32_t dev, uint32_t modeT);
int32_t rustlangLibcUnixBsdIssetugid(void);
int32_t rustlangLibcUnixBsdSyscall(int32_t num, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_UNIX_BSD_LIBC_UNIX_BSD_WRAPPER_H */

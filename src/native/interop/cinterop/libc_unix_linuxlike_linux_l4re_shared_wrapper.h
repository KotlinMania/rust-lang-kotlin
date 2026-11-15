#ifndef RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_L4RE_SHARED_LIBC_UNIX_LINUXLIKE_LINUX_L4RE_SHARED_WRAPPER_H
#define RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_L4RE_SHARED_LIBC_UNIX_LINUXLIKE_LINUX_L4RE_SHARED_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedIopl(int32_t level);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedIoperm(uint64_t from, uint64_t num, int32_t turnOn);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedAioRead(void* aiocbp);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedAioWrite(void* aiocbp);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedAioFsync(int32_t op, void* aiocbp);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedAioError(void* aiocbp);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedAioReturn(void* aiocbp);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedAioSuspend(void* aiocbList, int32_t nitems, void* timeout);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedAioCancel(int32_t fd, void* aiocbp);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedLioListio(int32_t mode, void* aiocbList, int32_t nitems, void* sevp);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedPwritev(int32_t fd, void* iov, int32_t iovcnt, int64_t offset);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedPreadv(int32_t fd, void* iov, int32_t iovcnt, int64_t offset);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedGetnameinfo(void* sa, uint32_t salen, char* host, uint32_t hostlen, char* serv, uint32_t servlen, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedGetloadavg(void* loadavg, int32_t nelem);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmReadv(int32_t pid, void* localIov, uint64_t liovcnt, void* remoteIov, uint64_t riovcnt, uint64_t flags);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedProcessVmWritev(int32_t pid, void* localIov, uint64_t liovcnt, void* remoteIov, uint64_t riovcnt, uint64_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedFutimes(int32_t fd, void* times);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedStrerrorR(int32_t errnum, char* buf, uint64_t buflen);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedAbs(int32_t i);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedLabs(int64_t i);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedRand(void);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand(uint32_t seed);
double rustlangLibcUnixLinuxlikeLinuxL4reSharedDrand48(void);
double rustlangLibcUnixLinuxlikeLinuxL4reSharedErand48(void* xseed);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedLrand48(void);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedNrand48(void* xseed);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedJrand48(void* xseed);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedSrand48(int64_t seed);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedSetpwent(void);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedEndpwent(void);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedGetpwent(void);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedSetgrent(void);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedEndgrent(void);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrent(void);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedSetspent(void);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedEndspent(void);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspent(void);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedGetspnam(const char* name);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedShmget(int32_t key, uint64_t size, int32_t shmflg);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedShmat(int32_t shmid, void* shmaddr, int32_t shmflg);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedShmdt(void* shmaddr);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedShmctl(int32_t shmid, int32_t cmd, void* buf);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedMprotect(void* addr, uint64_t len, int32_t prot);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedErrnoLocation(void);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedGlob(const char* pattern, int32_t flags, void* errfunc, void* pglob);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedGlobfree(void* pglob);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedSeekdir(void* dirp, int64_t loc);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedTelldir(void* dirp);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedMadvise(void* addr, uint64_t len, int32_t advice);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedMsync(void* addr, uint64_t len, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedRecvfrom(int32_t arg0Socket, void* buf, uint64_t len, int32_t flags, void* addr, void* addrlen);
char* rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfo(int32_t item);
char* rustlangLibcUnixLinuxlikeLinuxL4reSharedNlLanginfoL(int32_t item, void* locale);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetaffinity(int32_t pid, uint64_t cpusetsize, void* cpuset);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMax(int32_t policy);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSettimeofday(void* tv, void* tz);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSemTimedwait(void* sem, void* abstime);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSemGetvalue(void* sem, void* sval);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedMount(const char* src, const char* target, const char* fstype, uint64_t flags, void* data);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedPrctl(int32_t option, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedPpoll(void* fds, uint64_t nfds, void* timeout, void* sigmask);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSethostname(const char* name, uint64_t len);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetPriorityMin(int32_t policy);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSysinfo(void* info);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSigsuspend(void* mask);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgidR(int32_t gid, void* grp, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSemClose(void* sem);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnamR(const char* name, void* grp, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedInitgroups(const char* user, int32_t group);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedSemOpen(const char* name, int32_t oflag, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrnam(const char* name);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSemUnlink(const char* name);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedDaemon(int32_t nochdir, int32_t noclose);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSigwait(void* set, void* sig);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedGetgrgid(int32_t gid);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedPopen(const char* command, const char* mode);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedFaccessat(int32_t dirfd, const char* pathname, int32_t mode, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedDlIteratePhdr(void* callback, void* data);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedSetmntent(const char* filename, const char* ty);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedGetmntent(void* stream);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedAddmntent(void* stream, void* mnt);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedEndmntent(void* streamp);
char* rustlangLibcUnixLinuxlikeLinuxL4reSharedHasmntopt(void* mnt, const char* opt);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedRegcomp(void* preg, const char* pattern, int32_t cflags);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedRegexec(void* preg, const char* input, uint64_t nmatch, void* pmatch, int32_t eflags);
uint64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedRegerror(int32_t errcode, void* preg, char* errbuf, uint64_t errbufSize);
void rustlangLibcUnixLinuxlikeLinuxL4reSharedRegfree(void* preg);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvOpen(const char* tocode, const char* fromcode);
uint64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedIconv(void* cd, void* inbuf, void* inbytesleft, void* outbuf, void* outbytesleft);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedIconvClose(void* cd);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedGettid(void);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerCreate(uint32_t clockid, void* sevp, void* timerid);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerDelete(void* timerid);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGetoverrun(void* timerid);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerGettime(void* timerid, void* currValue);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedTimerSettime(void* timerid, int32_t flags, void* newValue, void* oldValue);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedMemmem(void* haystack, uint64_t haystacklen, void* needle, uint64_t needlelen);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedSchedGetcpu(void);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedGetoptLong(int32_t argc, void* argv, const char* optstring, void* longopts, void* longindex);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedCopyFileRange(int32_t fdIn, void* offIn, int32_t fdOut, void* offOut, uint64_t len, uint32_t flags);
void* rustlangLibcUnixLinuxlikeLinuxL4reSharedFreopen64(const char* filename, const char* mode, void* file);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedFseeko64(void* stream, int64_t offset, int32_t whence);
int32_t rustlangLibcUnixLinuxlikeLinuxL4reSharedFsetpos64(void* stream, void* ptr);
int64_t rustlangLibcUnixLinuxlikeLinuxL4reSharedFtello64(void* stream);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_L4RE_SHARED_LIBC_UNIX_LINUXLIKE_LINUX_L4RE_SHARED_WRAPPER_H */

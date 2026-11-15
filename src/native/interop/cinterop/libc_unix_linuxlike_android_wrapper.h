#ifndef RUSTLANG_LIBC_UNIX_LINUXLIKE_ANDROID_LIBC_UNIX_LINUXLIKE_ANDROID_WRAPPER_H
#define RUSTLANG_LIBC_UNIX_LINUXLIKE_ANDROID_LIBC_UNIX_LINUXLIKE_ANDROID_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangLibcUnixLinuxlikeAndroidSetpwent(void);
void rustlangLibcUnixLinuxlikeAndroidEndpwent(void);
void* rustlangLibcUnixLinuxlikeAndroidGetpwent(void);
void rustlangLibcUnixLinuxlikeAndroidSetgrent(void);
void rustlangLibcUnixLinuxlikeAndroidEndgrent(void);
void* rustlangLibcUnixLinuxlikeAndroidGetgrent(void);
int32_t rustlangLibcUnixLinuxlikeAndroidGetrlimit64(int32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeAndroidSetrlimit64(int32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeAndroidGetrlimit(int32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeAndroidSetrlimit(int32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeAndroidPrlimit(int32_t pid, int32_t resource, void* newLimit, void* oldLimit);
int32_t rustlangLibcUnixLinuxlikeAndroidPrlimit64(int32_t pid, int32_t resource, void* newLimit, void* oldLimit);
int32_t rustlangLibcUnixLinuxlikeAndroidStrerrorR(int32_t errnum, char* buf, uint64_t buflen);
int32_t rustlangLibcUnixLinuxlikeAndroidGettimeofday(void* tp, void* tz);
int32_t rustlangLibcUnixLinuxlikeAndroidMlock2(void* addr, uint64_t len, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidMadvise(void* addr, uint64_t len, int32_t advice);
int32_t rustlangLibcUnixLinuxlikeAndroidMsync(void* addr, uint64_t len, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidMprotect(void* addr, uint64_t len, int32_t prot);
int32_t rustlangLibcUnixLinuxlikeAndroidGetnameinfo(void* sa, uint32_t salen, char* host, uint64_t hostlen, char* serv, uint64_t servlen, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeAndroidPreadv(int32_t fd, void* iov, int32_t count, int64_t offset);
int64_t rustlangLibcUnixLinuxlikeAndroidPwritev(int32_t fd, void* iov, int32_t count, int64_t offset);
int64_t rustlangLibcUnixLinuxlikeAndroidProcessVmReadv(int32_t pid, void* localIov, uint64_t localIovCount, void* remoteIov, uint64_t remoteIovCount, uint64_t flags);
int64_t rustlangLibcUnixLinuxlikeAndroidProcessVmWritev(int32_t pid, void* localIov, uint64_t localIovCount, void* remoteIov, uint64_t remoteIovCount, uint64_t flags);
int64_t rustlangLibcUnixLinuxlikeAndroidPtrace(int32_t request, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeAndroidGetpriority(int32_t which, uint32_t who);
int32_t rustlangLibcUnixLinuxlikeAndroidSetpriority(int32_t which, uint32_t who, int32_t prio);
void* rustlangLibcUnixLinuxlikeAndroidSchedCpualloc(uint64_t count);
void rustlangLibcUnixLinuxlikeAndroidSchedCpufree(void* set);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedCpucount(uint64_t setsize, void* set);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedGetcpu(void);
int32_t rustlangLibcUnixLinuxlikeAndroidMallocInfo(int32_t options, void* stream);
uint64_t rustlangLibcUnixLinuxlikeAndroidMallocUsableSize(void* ptr);
int32_t rustlangLibcUnixLinuxlikeAndroidUtmpname(const char* name);
void rustlangLibcUnixLinuxlikeAndroidSetutent(void);
void* rustlangLibcUnixLinuxlikeAndroidGetutent(void);
void rustlangLibcUnixLinuxlikeAndroidSeekdir(void* dirp, int64_t loc);
int64_t rustlangLibcUnixLinuxlikeAndroidTelldir(void* dirp);
int32_t rustlangLibcUnixLinuxlikeAndroidFallocate(int32_t fd, int32_t mode, int64_t offset, int64_t len);
int32_t rustlangLibcUnixLinuxlikeAndroidFallocate64(int32_t fd, int32_t mode, int64_t offset, int64_t len);
int32_t rustlangLibcUnixLinuxlikeAndroidPosixFallocate(int32_t fd, int64_t offset, int64_t len);
int32_t rustlangLibcUnixLinuxlikeAndroidPosixFallocate64(int32_t fd, int64_t offset, int64_t len);
int64_t rustlangLibcUnixLinuxlikeAndroidGetxattr(const char* path, const char* name, void* value, uint64_t size);
int64_t rustlangLibcUnixLinuxlikeAndroidLgetxattr(const char* path, const char* name, void* value, uint64_t size);
int64_t rustlangLibcUnixLinuxlikeAndroidFgetxattr(int32_t filedes, const char* name, void* value, uint64_t size);
int32_t rustlangLibcUnixLinuxlikeAndroidSetxattr(const char* path, const char* name, void* value, uint64_t size, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidLsetxattr(const char* path, const char* name, void* value, uint64_t size, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidFsetxattr(int32_t filedes, const char* name, void* value, uint64_t size, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeAndroidListxattr(const char* path, char* list, uint64_t size);
int64_t rustlangLibcUnixLinuxlikeAndroidLlistxattr(const char* path, char* list, uint64_t size);
int64_t rustlangLibcUnixLinuxlikeAndroidFlistxattr(int32_t filedes, char* list, uint64_t size);
int32_t rustlangLibcUnixLinuxlikeAndroidRemovexattr(const char* path, const char* name);
int32_t rustlangLibcUnixLinuxlikeAndroidLremovexattr(const char* path, const char* name);
int32_t rustlangLibcUnixLinuxlikeAndroidFremovexattr(int32_t filedes, const char* name);
int32_t rustlangLibcUnixLinuxlikeAndroidSignalfd(int32_t fd, void* mask, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidTimerfdCreate(uint32_t clock, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidTimerfdGettime(int32_t fd, void* currentValue);
int32_t rustlangLibcUnixLinuxlikeAndroidTimerfdSettime(int32_t fd, int32_t flags, void* newValue, void* oldValue);
int32_t rustlangLibcUnixLinuxlikeAndroidTimerCreate(uint32_t clockid, void* sevp, void* timerid);
int32_t rustlangLibcUnixLinuxlikeAndroidTimerDelete(void* timerid);
int32_t rustlangLibcUnixLinuxlikeAndroidTimerGetoverrun(void* timerid);
int32_t rustlangLibcUnixLinuxlikeAndroidTimerGettime(void* timerid, void* currValue);
int32_t rustlangLibcUnixLinuxlikeAndroidTimerSettime(void* timerid, int32_t flags, void* newValue, void* oldValue);
int64_t rustlangLibcUnixLinuxlikeAndroidSyscall(int64_t num, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedGetaffinity(int32_t pid, uint64_t cpusetsize, void* cpuset);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedSetaffinity(int32_t pid, uint64_t cpusetsize, void* cpuset);
int32_t rustlangLibcUnixLinuxlikeAndroidEpollCreate(int32_t size);
int32_t rustlangLibcUnixLinuxlikeAndroidEpollCreate1(int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidEpollWait(int32_t epfd, void* events, int32_t maxevents, int32_t timeout);
int32_t rustlangLibcUnixLinuxlikeAndroidEpollCtl(int32_t epfd, int32_t op, int32_t fd, void* event);
int32_t rustlangLibcUnixLinuxlikeAndroidUnshare(int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidUmount(const char* target);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMax(int32_t policy);
int64_t rustlangLibcUnixLinuxlikeAndroidTee(int32_t fdIn, int32_t fdOut, uint64_t len, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidSettimeofday(void* tv, void* tz);
int64_t rustlangLibcUnixLinuxlikeAndroidSplice(int32_t fdIn, void* offIn, int32_t fdOut, void* offOut, uint64_t len, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidEventfd(uint32_t initval, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidEventfdRead(int32_t fd, void* value);
int32_t rustlangLibcUnixLinuxlikeAndroidEventfdWrite(int32_t fd, uint64_t value);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedRrGetInterval(int32_t pid, void* tp);
int32_t rustlangLibcUnixLinuxlikeAndroidSemTimedwait(void* sem, void* abstime);
int32_t rustlangLibcUnixLinuxlikeAndroidSemGetvalue(void* sem, void* sval);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedSetparam(int32_t pid, void* param);
int32_t rustlangLibcUnixLinuxlikeAndroidSetns(int32_t fd, int32_t nstype);
int32_t rustlangLibcUnixLinuxlikeAndroidSwapoff(const char* puath);
int64_t rustlangLibcUnixLinuxlikeAndroidVmsplice(int32_t fd, void* iov, uint64_t nrSegs, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidMount(const char* src, const char* target, const char* fstype, uint64_t flags, void* data);
int32_t rustlangLibcUnixLinuxlikeAndroidPersonality(uint32_t persona);
int32_t rustlangLibcUnixLinuxlikeAndroidPrctl(int32_t option, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedGetparam(int32_t pid, void* param);
int32_t rustlangLibcUnixLinuxlikeAndroidPpoll(void* fds, uint32_t nfds, void* timeout, void* sigmask);
int32_t rustlangLibcUnixLinuxlikeAndroidClone(void* cb, void* childStack, int32_t flags, void* arg, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedGetscheduler(int32_t pid);
int32_t rustlangLibcUnixLinuxlikeAndroidClockNanosleep(uint32_t clkId, int32_t flags, void* rqtp, void* rmtp);
int32_t rustlangLibcUnixLinuxlikeAndroidSethostname(const char* name, uint64_t len);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedGetPriorityMin(int32_t policy);
int32_t rustlangLibcUnixLinuxlikeAndroidSysinfo(void* info);
int32_t rustlangLibcUnixLinuxlikeAndroidUmount2(const char* target, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidSwapon(const char* path, int32_t swapflags);
int32_t rustlangLibcUnixLinuxlikeAndroidSchedSetscheduler(int32_t pid, int32_t policy, void* param);
int64_t rustlangLibcUnixLinuxlikeAndroidSendfile(int32_t outFd, int32_t inFd, void* offset, uint64_t count);
int64_t rustlangLibcUnixLinuxlikeAndroidSendfile64(int32_t outFd, int32_t inFd, void* offset, uint64_t count);
int32_t rustlangLibcUnixLinuxlikeAndroidSetfsgid(int32_t gid);
int32_t rustlangLibcUnixLinuxlikeAndroidSetfsuid(int32_t uid);
int32_t rustlangLibcUnixLinuxlikeAndroidSigsuspend(void* mask);
int32_t rustlangLibcUnixLinuxlikeAndroidGetgrgidR(int32_t gid, void* grp, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixLinuxlikeAndroidSigaltstack(void* ss, void* oss);
int32_t rustlangLibcUnixLinuxlikeAndroidSemClose(void* sem);
int32_t rustlangLibcUnixLinuxlikeAndroidGetgrnamR(const char* name, void* grp, char* buf, uint64_t buflen, void* result);
void* rustlangLibcUnixLinuxlikeAndroidSemOpen(const char* name, int32_t oflag, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
void* rustlangLibcUnixLinuxlikeAndroidGetgrnam(const char* name);
int32_t rustlangLibcUnixLinuxlikeAndroidSemUnlink(const char* name);
int32_t rustlangLibcUnixLinuxlikeAndroidDaemon(int32_t nochdir, int32_t noclose);
int32_t rustlangLibcUnixLinuxlikeAndroidSigtimedwait(void* set, void* info, void* timeout);
int32_t rustlangLibcUnixLinuxlikeAndroidSigwait(void* set, void* sig);
void* rustlangLibcUnixLinuxlikeAndroidGetgrgid(int32_t gid);
int32_t rustlangLibcUnixLinuxlikeAndroidGetgrouplist(const char* user, int32_t group, void* groups, void* ngroups);
int32_t rustlangLibcUnixLinuxlikeAndroidInitgroups(const char* user, int32_t group);
void* rustlangLibcUnixLinuxlikeAndroidPopen(const char* command, const char* mode);
int32_t rustlangLibcUnixLinuxlikeAndroidFaccessat(int32_t dirfd, const char* pathname, int32_t mode, int32_t flags);
void* rustlangLibcUnixLinuxlikeAndroidErrno(void);
int32_t rustlangLibcUnixLinuxlikeAndroidInotifyRmWatch(int32_t fd, uint32_t wd);
int32_t rustlangLibcUnixLinuxlikeAndroidInotifyInit(void);
int32_t rustlangLibcUnixLinuxlikeAndroidInotifyInit1(int32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidInotifyAddWatch(int32_t fd, const char* path, uint32_t mask);
int32_t rustlangLibcUnixLinuxlikeAndroidRegcomp(void* preg, const char* pattern, int32_t cflags);
int32_t rustlangLibcUnixLinuxlikeAndroidRegexec(void* preg, const char* input, uint64_t nmatch, void* pmatch, int32_t eflags);
uint64_t rustlangLibcUnixLinuxlikeAndroidRegerror(int32_t errcode, void* preg, char* errbuf, uint64_t errbufSize);
void rustlangLibcUnixLinuxlikeAndroidRegfree(void* preg);
void rustlangLibcUnixLinuxlikeAndroidAndroidSetAbortMessage(const char* msg);
int32_t rustlangLibcUnixLinuxlikeAndroidGettid(void);
int32_t rustlangLibcUnixLinuxlikeAndroidTgkill(int32_t tgid, int32_t tid, int32_t sig);
uint64_t rustlangLibcUnixLinuxlikeAndroidGetauxval(uint64_t type);
int64_t rustlangLibcUnixLinuxlikeAndroidGetrandom(void* buf, uint64_t buflen, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidGetentropy(void* buf, uint64_t buflen);
int32_t rustlangLibcUnixLinuxlikeAndroidSystemPropertySet(const char* name, const char* value);
int32_t rustlangLibcUnixLinuxlikeAndroidSystemPropertyGet(const char* name, char* value);
void* rustlangLibcUnixLinuxlikeAndroidSystemPropertyFind(const char* name);
void* rustlangLibcUnixLinuxlikeAndroidSystemPropertyFindNth(uint32_t n);
int32_t rustlangLibcUnixLinuxlikeAndroidSystemPropertyForeach(void* callback, void* cookie);
int32_t rustlangLibcUnixLinuxlikeAndroidDlIteratePhdr(void* callback, void* data);
uint32_t rustlangLibcUnixLinuxlikeAndroidArc4random(void);
uint32_t rustlangLibcUnixLinuxlikeAndroidArc4randomUniform(uint32_t upperBound);
void rustlangLibcUnixLinuxlikeAndroidArc4randomBuf(void* buf, uint64_t n);
void* rustlangLibcUnixLinuxlikeAndroidReallocarray(void* ptr, uint64_t nmemb, uint64_t size);
char* rustlangLibcUnixLinuxlikeAndroidDirname(const char* path);
char* rustlangLibcUnixLinuxlikeAndroidBasename(const char* path);
int32_t rustlangLibcUnixLinuxlikeAndroidGetoptLong(int32_t argc, void* argv, const char* optstring, void* longopts, void* longindex);
void rustlangLibcUnixLinuxlikeAndroidSync(void);
int32_t rustlangLibcUnixLinuxlikeAndroidSyncfs(int32_t fd);
void* rustlangLibcUnixLinuxlikeAndroidMemmem(void* haystack, uint64_t haystacklen, void* needle, uint64_t needlelen);
uint64_t rustlangLibcUnixLinuxlikeAndroidFreadUnlocked(void* buf, uint64_t size, uint64_t nobj, void* stream);
uint64_t rustlangLibcUnixLinuxlikeAndroidFwriteUnlocked(void* buf, uint64_t size, uint64_t nobj, void* stream);
int32_t rustlangLibcUnixLinuxlikeAndroidFflushUnlocked(void* stream);
char* rustlangLibcUnixLinuxlikeAndroidFgetsUnlocked(char* buf, int32_t size, void* stream);
int32_t rustlangLibcUnixLinuxlikeAndroidKlogctl(int32_t syslogType, char* bufp, int32_t len);
int32_t rustlangLibcUnixLinuxlikeAndroidMemfdCreate(const char* name, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeAndroidRenameat2(int32_t olddirfd, const char* oldpath, int32_t newdirfd, const char* newpath, uint32_t flags);
int64_t rustlangLibcUnixLinuxlikeAndroidPreadv2(int32_t fd, void* iov, int32_t iovcnt, int64_t offset, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeAndroidPwritev2(int32_t fd, void* iov, int32_t iovcnt, int64_t offset, int32_t flags);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_UNIX_LINUXLIKE_ANDROID_LIBC_UNIX_LINUXLIKE_ANDROID_WRAPPER_H */

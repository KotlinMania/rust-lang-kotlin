#ifndef RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_GNU_LIBC_UNIX_LINUXLIKE_LINUX_GNU_WRAPPER_H
#define RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_GNU_LIBC_UNIX_LINUXLIKE_LINUX_GNU_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* rustlangLibcUnixLinuxlikeLinuxGnuGetENVIRON(void);
void rustlangLibcUnixLinuxlikeLinuxGnuSetENVIRON(void* km_val);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuFgetspentR(void* fp, void* spbuf, char* buf, uint64_t buflen, void* spbufp);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuSgetspentR(const char* s, void* spbuf, char* buf, uint64_t buflen, void* spbufp);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetspentR(void* spbuf, char* buf, uint64_t buflen, void* spbufp);
void rustlangLibcUnixLinuxlikeLinuxGnuQsortR(void* base, uint64_t num, uint64_t size, void* compar, void* arg);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuSendmmsg(int32_t sockfd, void* msgvec, uint32_t vlen, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuRecvmmsg(int32_t sockfd, void* msgvec, uint32_t vlen, int32_t flags, void* timeout);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit64(uint32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit64(uint32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetrlimit(uint32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuSetrlimit(uint32_t resource, void* rlim);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPrlimit(int32_t pid, uint32_t resource, void* newLimit, void* oldLimit);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPrlimit64(int32_t pid, uint32_t resource, void* newLimit, void* oldLimit);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuUtmpname(const char* file);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuUtmpxname(const char* file);
void* rustlangLibcUnixLinuxlikeLinuxGnuGetutxent(void);
void* rustlangLibcUnixLinuxlikeLinuxGnuGetutxid(void* ut);
void* rustlangLibcUnixLinuxlikeLinuxGnuGetutxline(void* ut);
void* rustlangLibcUnixLinuxlikeLinuxGnuPututxline(void* ut);
void rustlangLibcUnixLinuxlikeLinuxGnuSetutxent(void);
void rustlangLibcUnixLinuxlikeLinuxGnuEndutxent(void);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetpt(void);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuMallopt(int32_t param, int32_t value);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGettimeofday(void* tp, void* tz);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetentropy(void* buf, uint64_t buflen);
int64_t rustlangLibcUnixLinuxlikeLinuxGnuGetrandom(void* buf, uint64_t buflen, uint32_t flags);
uint64_t rustlangLibcUnixLinuxlikeLinuxGnuGetauxval(uint64_t type);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuAdjtimex(void* buf);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuNtpAdjtime(void* buf);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuNtpGettime(void* buf);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuClockAdjtime(uint32_t clkId, void* buf);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuFanotifyMark(int32_t fd, uint32_t flags, uint64_t mask, int32_t dirfd, const char* path);
int64_t rustlangLibcUnixLinuxlikeLinuxGnuPreadv2(int32_t fd, void* iov, int32_t iovcnt, int64_t offset, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeLinuxGnuPwritev2(int32_t fd, void* iov, int32_t iovcnt, int64_t offset, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeLinuxGnuPreadv64v2(int32_t fd, void* iov, int32_t iovcnt, int64_t offset, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeLinuxGnuPwritev64v2(int32_t fd, void* iov, int32_t iovcnt, int64_t offset, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuRenameat2(int32_t olddirfd, const char* oldpath, int32_t newdirfd, const char* newpath, uint32_t flags);
void rustlangLibcUnixLinuxlikeLinuxGnuExplicitBzero(void* s, uint64_t len);
void* rustlangLibcUnixLinuxlikeLinuxGnuReallocarray(void* ptr, uint64_t nmemb, uint64_t size);
char* rustlangLibcUnixLinuxlikeLinuxGnuCtermid(char* s);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuBacktrace(void* buf, int32_t sz);
void* rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbols(void* buffer, int32_t len);
void rustlangLibcUnixLinuxlikeLinuxGnuBacktraceSymbolsFd(void* buffer, int32_t len, int32_t fd);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGlob64(const char* pattern, int32_t flags, void* errfunc, void* pglob);
void rustlangLibcUnixLinuxlikeLinuxGnuGlobfree64(void* pglob);
int64_t rustlangLibcUnixLinuxlikeLinuxGnuPtrace(uint32_t request, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrGetaffinityNp(void* attr, uint64_t cpusetsize, void* cpuset);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPthreadAttrSetaffinityNp(void* attr, uint64_t cpusetsize, void* cpuset);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetpriority(uint32_t which, uint32_t who);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuSetpriority(uint32_t which, uint32_t who, int32_t prio);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrGetkindNp(void* attr, void* val);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPthreadRwlockattrSetkindNp(void* attr, int32_t val);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPthreadTryjoinNp(uint64_t thread, void* retval);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPthreadTimedjoinNp(uint64_t thread, void* retval, void* abstime);
void rustlangLibcUnixLinuxlikeLinuxGnuMallocStats(void);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuMallocInfo(int32_t options, void* stream);
uint64_t rustlangLibcUnixLinuxlikeLinuxGnuMallocUsableSize(void* ptr);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetpwentR(void* pwd, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetgrentR(void* grp, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuFgetpwentR(void* stream, void* pwd, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuFgetgrentR(void* stream, void* grp, char* buf, uint64_t buflen, void* result);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetnetentR(void* resultBuf, char* buf, uint64_t buflen, void* result, void* hErrnop);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetnetbynameR(const char* name, void* resultBuf, char* buf, uint64_t buflen, void* result, void* hErrnop);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuGetnetbyaddrR(uint32_t net, int32_t type, void* resultBuf, char* buf, uint64_t buflen, void* result, void* hErrnop);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPutpwent(void* p, void* stream);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPutgrent(void* grp, void* stream);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuSethostid(int64_t hostid);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuMemfdCreate(const char* name, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuMlock2(void* addr, uint64_t len, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuEuidaccess(const char* pathname, int32_t mode);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuEaccess(const char* pathname, int32_t mode);
char* rustlangLibcUnixLinuxlikeLinuxGnuAsctimeR(void* tm, char* buf);
char* rustlangLibcUnixLinuxlikeLinuxGnuCtimeR(void* timep, char* buf);
char* rustlangLibcUnixLinuxlikeLinuxGnuDirname(char* path);
char* rustlangLibcUnixLinuxlikeLinuxGnuPosixBasename(char* path);
char* rustlangLibcUnixLinuxlikeLinuxGnuGnuBasename(const char* path);
void* rustlangLibcUnixLinuxlikeLinuxGnuDlmopen(int64_t lmid, const char* filename, int32_t flag);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuDlinfo(void* handle, int32_t request, void* info);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuDladdr1(void* addr, void* info, void* extraInfo, int32_t flags);
void* rustlangLibcUnixLinuxlikeLinuxGnuDlvsym(void* handle, const char* symbol, const char* version);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuMallocTrim(uint64_t pad);
const char* rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcRelease(void);
const char* rustlangLibcUnixLinuxlikeLinuxGnuGnuGetLibcVersion(void);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddchdirNp(void* actions, const char* path);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddfchdirNp(void* actions, int32_t fd);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddclosefromNp(void* actions, int32_t from);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuPosixSpawnFileActionsAddtcsetpgrpNp(void* actions, int32_t tcfd);
void* rustlangLibcUnixLinuxlikeLinuxGnuGetmntentR(void* stream, void* mntbuf, char* buf, int32_t buflen);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuExecveat(int32_t dirfd, const char* pathname, void* argv, void* envp, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuCloseRange(uint32_t first, uint32_t last, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuMqNotify(int32_t mqdes, void* sevp);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuEpollPwait2(int32_t epfd, void* events, int32_t maxevents, void* timeout, void* sigmask);
void* rustlangLibcUnixLinuxlikeLinuxGnuMempcpy(void* dest, void* src, uint64_t n);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuTgkill(int32_t tgid, int32_t tid, int32_t sig);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_GNU_LIBC_UNIX_LINUXLIKE_LINUX_GNU_WRAPPER_H */

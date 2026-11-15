#ifndef RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_LIBC_UNIX_LINUXLIKE_LINUX_WRAPPER_H
#define RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_LIBC_UNIX_LINUXLIKE_LINUX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcUnixLinuxlikeLinuxGetspnamR(const char* name, void* spbuf, char* buf, uint64_t buflen, void* spbufp);
int32_t rustlangLibcUnixLinuxlikeLinuxMqOpen(const char* name, int32_t oflag, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeLinuxMqClose(int32_t mqd);
int32_t rustlangLibcUnixLinuxlikeLinuxMqUnlink(const char* name);
int64_t rustlangLibcUnixLinuxlikeLinuxMqReceive(int32_t mqd, char* msgPtr, uint64_t msgLen, void* msgPrio);
int64_t rustlangLibcUnixLinuxlikeLinuxMqTimedreceive(int32_t mqd, char* msgPtr, uint64_t msgLen, void* msgPrio, void* absTimeout);
int32_t rustlangLibcUnixLinuxlikeLinuxMqSend(int32_t mqd, const char* msgPtr, uint64_t msgLen, uint32_t msgPrio);
int32_t rustlangLibcUnixLinuxlikeLinuxMqTimedsend(int32_t mqd, const char* msgPtr, uint64_t msgLen, uint32_t msgPrio, void* absTimeout);
int32_t rustlangLibcUnixLinuxlikeLinuxMqGetattr(int32_t mqd, void* attr);
int32_t rustlangLibcUnixLinuxlikeLinuxMqSetattr(int32_t mqd, void* newattr, void* oldattr);
int64_t rustlangLibcUnixLinuxlikeLinuxMrand48(void);
void* rustlangLibcUnixLinuxlikeLinuxSeed48(void* xseed);
void rustlangLibcUnixLinuxlikeLinuxLcong48(void* p);
int32_t rustlangLibcUnixLinuxlikeLinuxLutimes(const char* file, void* times);
int32_t rustlangLibcUnixLinuxlikeLinuxShmOpen(const char* name, int32_t oflag, uint32_t mode);
int32_t rustlangLibcUnixLinuxlikeLinuxShmUnlink(const char* name);
int32_t rustlangLibcUnixLinuxlikeLinuxFtok(const char* pathname, int32_t projId);
int32_t rustlangLibcUnixLinuxlikeLinuxSemget(int32_t key, int32_t nsems, int32_t semflag);
int32_t rustlangLibcUnixLinuxlikeLinuxSemop(int32_t semid, void* sops, uint64_t nsops);
int32_t rustlangLibcUnixLinuxlikeLinuxSemctl(int32_t semid, int32_t semnum, int32_t cmd, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeLinuxMsgctl(int32_t msqid, int32_t cmd, void* buf);
int32_t rustlangLibcUnixLinuxlikeLinuxMsgget(int32_t key, int32_t msgflg);
int64_t rustlangLibcUnixLinuxlikeLinuxMsgrcv(int32_t msqid, void* msgp, uint64_t msgsz, int64_t msgtyp, int32_t msgflg);
int32_t rustlangLibcUnixLinuxlikeLinuxMsgsnd(int32_t msqid, void* msgp, uint64_t msgsz, int32_t msgflg);
int32_t rustlangLibcUnixLinuxlikeLinuxFallocate(int32_t fd, int32_t mode, int64_t offset, int64_t len);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixFallocate(int32_t fd, int64_t offset, int64_t len);
int64_t rustlangLibcUnixLinuxlikeLinuxReadahead(int32_t fd, int64_t offset, uint64_t count);
int64_t rustlangLibcUnixLinuxlikeLinuxGetxattr(const char* path, const char* name, void* value, uint64_t size);
int64_t rustlangLibcUnixLinuxlikeLinuxLgetxattr(const char* path, const char* name, void* value, uint64_t size);
int64_t rustlangLibcUnixLinuxlikeLinuxFgetxattr(int32_t filedes, const char* name, void* value, uint64_t size);
int32_t rustlangLibcUnixLinuxlikeLinuxSetxattr(const char* path, const char* name, void* value, uint64_t size, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxLsetxattr(const char* path, const char* name, void* value, uint64_t size, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxFsetxattr(int32_t filedes, const char* name, void* value, uint64_t size, int32_t flags);
int64_t rustlangLibcUnixLinuxlikeLinuxListxattr(const char* path, char* list, uint64_t size);
int64_t rustlangLibcUnixLinuxlikeLinuxLlistxattr(const char* path, char* list, uint64_t size);
int64_t rustlangLibcUnixLinuxlikeLinuxFlistxattr(int32_t filedes, char* list, uint64_t size);
int32_t rustlangLibcUnixLinuxlikeLinuxRemovexattr(const char* path, const char* name);
int32_t rustlangLibcUnixLinuxlikeLinuxLremovexattr(const char* path, const char* name);
int32_t rustlangLibcUnixLinuxlikeLinuxFremovexattr(int32_t filedes, const char* name);
int32_t rustlangLibcUnixLinuxlikeLinuxSignalfd(int32_t fd, void* mask, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxTimerfdCreate(uint32_t clockid, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxTimerfdGettime(int32_t fd, void* currValue);
int32_t rustlangLibcUnixLinuxlikeLinuxTimerfdSettime(int32_t fd, int32_t flags, void* newValue, void* oldValue);
int32_t rustlangLibcUnixLinuxlikeLinuxQuotactl(int32_t cmd, const char* special, int32_t id, char* data);
int32_t rustlangLibcUnixLinuxlikeLinuxEpollPwait(int32_t epfd, void* events, int32_t maxevents, int32_t timeout, void* sigmask);
int32_t rustlangLibcUnixLinuxlikeLinuxDup3(int32_t oldfd, int32_t newfd, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxSigtimedwait(void* set, void* info, void* timeout);
int32_t rustlangLibcUnixLinuxlikeLinuxSigwaitinfo(void* set, void* info);
int32_t rustlangLibcUnixLinuxlikeLinuxAccept4(int32_t fd, void* addr, void* len, int32_t flg);
int32_t rustlangLibcUnixLinuxlikeLinuxReboot(int32_t howTo);
int32_t rustlangLibcUnixLinuxlikeLinuxSetfsgid(int32_t gid);
int32_t rustlangLibcUnixLinuxlikeLinuxSetfsuid(int32_t uid);
int32_t rustlangLibcUnixLinuxlikeLinuxMkfifoat(int32_t dirfd, const char* pathname, uint32_t mode);
int32_t rustlangLibcUnixLinuxlikeLinuxSyncFileRange(int32_t fd, int64_t offset, int64_t nbytes, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixMadvise(void* addr, uint64_t len, int32_t advice);
void* rustlangLibcUnixLinuxlikeLinuxMremap(void* addr, uint64_t len, uint64_t newLen, int32_t flags, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeLinuxRemapFilePages(void* addr, uint64_t size, int32_t prot, uint64_t pgoff, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxMkstemps(char* arg0Template, int32_t suffixlen);
int32_t rustlangLibcUnixLinuxlikeLinuxVhangup(void);
void rustlangLibcUnixLinuxlikeLinuxSync(void);
int32_t rustlangLibcUnixLinuxlikeLinuxSyncfs(int32_t fd);
int64_t rustlangLibcUnixLinuxlikeLinuxSyscall(int64_t num, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeLinuxSchedSetaffinity(int32_t pid, uint64_t cpusetsize, void* cpuset);
int32_t rustlangLibcUnixLinuxlikeLinuxEpollCreate(int32_t size);
int32_t rustlangLibcUnixLinuxlikeLinuxEpollCreate1(int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxEpollWait(int32_t epfd, void* events, int32_t maxevents, int32_t timeout);
int32_t rustlangLibcUnixLinuxlikeLinuxEpollCtl(int32_t epfd, int32_t op, int32_t fd, void* event);
int32_t rustlangLibcUnixLinuxlikeLinuxUnshare(int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxUmount(const char* target);
int64_t rustlangLibcUnixLinuxlikeLinuxTee(int32_t fdIn, int32_t fdOut, uint64_t len, uint32_t flags);
int64_t rustlangLibcUnixLinuxlikeLinuxSplice(int32_t fdIn, void* offIn, int32_t fdOut, void* offOut, uint64_t len, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxEventfd(uint32_t initval, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxEventfdRead(int32_t fd, void* value);
int32_t rustlangLibcUnixLinuxlikeLinuxEventfdWrite(int32_t fd, uint64_t value);
int32_t rustlangLibcUnixLinuxlikeLinuxSchedRrGetInterval(int32_t pid, void* tp);
int32_t rustlangLibcUnixLinuxlikeLinuxSchedSetparam(int32_t pid, void* param);
int32_t rustlangLibcUnixLinuxlikeLinuxSetns(int32_t fd, int32_t nstype);
int32_t rustlangLibcUnixLinuxlikeLinuxSwapoff(const char* path);
int64_t rustlangLibcUnixLinuxlikeLinuxVmsplice(int32_t fd, void* iov, uint64_t nrSegs, uint32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxPersonality(uint64_t persona);
int32_t rustlangLibcUnixLinuxlikeLinuxSchedGetparam(int32_t pid, void* param);
int32_t rustlangLibcUnixLinuxlikeLinuxClone(void* cb, void* childStack, int32_t flags, void* arg, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);
int32_t rustlangLibcUnixLinuxlikeLinuxSchedGetscheduler(int32_t pid);
int32_t rustlangLibcUnixLinuxlikeLinuxClockNanosleep(uint32_t clkId, int32_t flags, void* rqtp, void* rmtp);
int32_t rustlangLibcUnixLinuxlikeLinuxUmount2(const char* target, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxSwapon(const char* path, int32_t swapflags);
int32_t rustlangLibcUnixLinuxlikeLinuxSchedSetscheduler(int32_t pid, int32_t policy, void* param);
int64_t rustlangLibcUnixLinuxlikeLinuxSendfile(int32_t outFd, int32_t inFd, void* offset, uint64_t count);
int32_t rustlangLibcUnixLinuxlikeLinuxSigaltstack(void* ss, void* oss);
int32_t rustlangLibcUnixLinuxlikeLinuxGetdtablesize(void);
int32_t rustlangLibcUnixLinuxlikeLinuxGetgrouplist(const char* user, int32_t group, void* groups, void* ngroups);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawn(void* pid, const char* path, void* fileActions, void* attrp, void* argv, void* envp);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnp(void* pid, const char* file, void* fileActions, void* attrp, void* argv, void* envp);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrInit(void* attr);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrDestroy(void* attr);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigdefault(void* attr, void* arg1Default);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigdefault(void* attr, void* arg1Default);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetsigmask(void* attr, void* arg1Default);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetsigmask(void* attr, void* arg1Default);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetflags(void* attr, void* flags);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetflags(void* attr, int16_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetpgroup(void* attr, void* flags);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetpgroup(void* attr, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedpolicy(void* attr, void* flags);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedpolicy(void* attr, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrGetschedparam(void* attr, void* param);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnattrSetschedparam(void* attr, void* param);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsInit(void* actions);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsDestroy(void* actions);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddopen(void* actions, int32_t fd, const char* path, int32_t oflag, uint32_t mode);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAddclose(void* actions, int32_t fd);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixSpawnFileActionsAdddup2(void* actions, int32_t fd, int32_t newfd);
uint64_t rustlangLibcUnixLinuxlikeLinuxFreadUnlocked(void* buf, uint64_t size, uint64_t nobj, void* stream);
int32_t rustlangLibcUnixLinuxlikeLinuxInotifyRmWatch(int32_t fd, int32_t wd);
int32_t rustlangLibcUnixLinuxlikeLinuxInotifyInit(void);
int32_t rustlangLibcUnixLinuxlikeLinuxInotifyInit1(int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxInotifyAddWatch(int32_t fd, const char* path, uint32_t mask);
int32_t rustlangLibcUnixLinuxlikeLinuxFanotifyInit(uint32_t flags, uint32_t eventFFlags);
int64_t rustlangLibcUnixLinuxlikeLinuxGethostid(void);
int32_t rustlangLibcUnixLinuxlikeLinuxKlogctl(int32_t syslogType, char* bufp, int32_t len);
int32_t rustlangLibcUnixLinuxlikeLinuxNameToHandleAt(int32_t dirfd, const char* path, void* handle, void* mountId, int32_t flags);
int32_t rustlangLibcUnixLinuxlikeLinuxOpenByHandleAt(int32_t mountFd, void* handle, int32_t flags);
void* rustlangLibcUnixLinuxlikeLinuxGetnetbyname(const char* name);
void* rustlangLibcUnixLinuxlikeLinuxGetnetbyaddr(uint32_t net, int32_t type);
void* rustlangLibcUnixLinuxlikeLinuxGetnetent(void);
void rustlangLibcUnixLinuxlikeLinuxSetnetent(int32_t stayopen);
void rustlangLibcUnixLinuxlikeLinuxEndnetent(void);
int32_t rustlangLibcUnixLinuxlikeLinuxFallocate64(int32_t fd, int32_t mode, int64_t offset, int64_t len);
int32_t rustlangLibcUnixLinuxlikeLinuxFgetpos64(void* stream, void* ptr);
void* rustlangLibcUnixLinuxlikeLinuxFopen64(const char* filename, const char* mode);
int32_t rustlangLibcUnixLinuxlikeLinuxPosixFallocate64(int32_t fd, int64_t offset, int64_t len);
int64_t rustlangLibcUnixLinuxlikeLinuxSendfile64(int32_t outFd, int32_t inFd, void* offset, uint64_t count);
void* rustlangLibcUnixLinuxlikeLinuxTmpfile64(void);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_LIBC_UNIX_LINUXLIKE_LINUX_WRAPPER_H */

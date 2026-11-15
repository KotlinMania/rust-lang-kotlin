#ifndef RUSTLANG_LIBC_NEW_COMMON_POSIX_PTHREAD_LIBC_NEW_COMMON_POSIX_PTHREAD_WRAPPER_H
#define RUSTLANG_LIBC_NEW_COMMON_POSIX_PTHREAD_LIBC_NEW_COMMON_POSIX_PTHREAD_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcNewCommonPosixPthreadPthreadAtfork(void* prepare, void* parent, void* child);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrGetguardsize(void* attr, void* guardsize);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(void* attr, void* inheritsched);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedparam(void* attr, void* param);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(void* attr, void* policy);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrGetstack(void* attr, void* stackaddr, void* stacksize);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrSetguardsize(void* attr, uint64_t guardsize);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(void* attr, int32_t inheritsched);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedparam(void* attr, void* param);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(void* attr, int32_t policy);
int32_t rustlangLibcNewCommonPosixPthreadPthreadAttrSetstack(void* attr, void* stackaddr, uint64_t stacksize);
int32_t rustlangLibcNewCommonPosixPthreadPthreadBarrierDestroy(void* barrier);
int32_t rustlangLibcNewCommonPosixPthreadPthreadBarrierInit(void* barrier, void* attr, uint32_t count);
int32_t rustlangLibcNewCommonPosixPthreadPthreadBarrierWait(void* barrier);
int32_t rustlangLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(void* attr);
int32_t rustlangLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(void* attr, void* shared);
int32_t rustlangLibcNewCommonPosixPthreadPthreadBarrierattrInit(void* attr);
int32_t rustlangLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(void* attr, int32_t shared);
int32_t rustlangLibcNewCommonPosixPthreadPthreadCancel(void* thread);
int32_t rustlangLibcNewCommonPosixPthreadPthreadCondattrGetclock(void* attr, void* clockId);
int32_t rustlangLibcNewCommonPosixPthreadPthreadCondattrGetpshared(void* attr, void* pshared);
int32_t rustlangLibcNewCommonPosixPthreadPthreadCondattrSetclock(void* attr, uint32_t clockId);
int32_t rustlangLibcNewCommonPosixPthreadPthreadCondattrSetpshared(void* attr, int32_t pshared);
int32_t rustlangLibcNewCommonPosixPthreadPthreadCreate(void* native, void* attr, void* f, void* value);
int32_t rustlangLibcNewCommonPosixPthreadPthreadGetcpuclockid(void* thread, void* clkId);
int32_t rustlangLibcNewCommonPosixPthreadPthreadGetschedparam(void* native, void* policy, void* param);
int32_t rustlangLibcNewCommonPosixPthreadPthreadKill(void* thread, int32_t sig);
int32_t rustlangLibcNewCommonPosixPthreadPthreadMutexConsistent(void* mutex);
int32_t rustlangLibcNewCommonPosixPthreadPthreadMutexTimedlock(void* lock, void* abstime);
int32_t rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(void* attr, void* protocol);
int32_t rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(void* attr, void* pshared);
int32_t rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(void* attr, void* robustness);
int32_t rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(void* attr, int32_t protocol);
int32_t rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(void* attr, int32_t pshared);
int32_t rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(void* attr, int32_t robustness);
int32_t rustlangLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(void* attr, void* val);
int32_t rustlangLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(void* attr, int32_t val);
int32_t rustlangLibcNewCommonPosixPthreadPthreadOnce(void* control, void* routine);
int32_t rustlangLibcNewCommonPosixPthreadPthreadSetschedparam(void* native, int32_t policy, void* param);
int32_t rustlangLibcNewCommonPosixPthreadPthreadSetschedprio(void* native, int32_t priority);
int32_t rustlangLibcNewCommonPosixPthreadPthreadSigmask(int32_t how, void* set, void* oldset);
int32_t rustlangLibcNewCommonPosixPthreadPthreadSpinDestroy(void* lock);
int32_t rustlangLibcNewCommonPosixPthreadPthreadSpinInit(void* lock, int32_t pshared);
int32_t rustlangLibcNewCommonPosixPthreadPthreadSpinLock(void* lock);
int32_t rustlangLibcNewCommonPosixPthreadPthreadSpinTrylock(void* lock);
int32_t rustlangLibcNewCommonPosixPthreadPthreadSpinUnlock(void* lock);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_NEW_COMMON_POSIX_PTHREAD_LIBC_NEW_COMMON_POSIX_PTHREAD_WRAPPER_H */

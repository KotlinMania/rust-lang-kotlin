#ifndef LIBC_NEW_HELENOS_FIBRILSYNCH_FIBRIL_SYNCH_NEW_HELENOS_FIBRILSYNCH_FIBRIL_SYNCH_WRAPPER_H
#define LIBC_NEW_HELENOS_FIBRILSYNCH_FIBRIL_SYNCH_NEW_HELENOS_FIBRILSYNCH_FIBRIL_SYNCH_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void libcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock(void* mutex);
void libcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock(void* mutex);
bool libcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock(void* mutex);
bool libcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked(void* mutex);
void libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize(void* rwlock);
void libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock(void* rwlock);
void libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock(void* rwlock);
void libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock(void* rwlock);
void libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock(void* rwlock);
bool libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked(void* rwlock);
bool libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked(void* rwlock);
bool libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked(void* rwlock);
void libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize(void* condvar);
void libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait(void* condvar, void* mutex);
int32_t libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout(void* condvar, void* mutex, int64_t timeout);
void libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal(void* condvar);
void libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast(void* condvar);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_HELENOS_FIBRILSYNCH_FIBRIL_SYNCH_NEW_HELENOS_FIBRILSYNCH_FIBRIL_SYNCH_WRAPPER_H */

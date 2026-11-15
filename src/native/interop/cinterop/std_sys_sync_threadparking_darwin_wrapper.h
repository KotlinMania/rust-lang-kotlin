#ifndef RUSTLANG_STD_SYS_SYNC_THREADPARKING_DARWIN_STD_SYS_SYNC_THREADPARKING_DARWIN_WRAPPER_H
#define RUSTLANG_STD_SYS_SYNC_THREADPARKING_DARWIN_STD_SYS_SYNC_THREADPARKING_DARWIN_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint64_t rustlangStdSysSyncThreadparkingDarwinDispatchTime(uint64_t when, int64_t delta);
void* rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(int64_t val);
int64_t rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreWait(void* dsema, uint64_t timeout);
int64_t rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(void* dsema);
void rustlangStdSysSyncThreadparkingDarwinDispatchRelease(void* object);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_SYNC_THREADPARKING_DARWIN_STD_SYS_SYNC_THREADPARKING_DARWIN_WRAPPER_H */

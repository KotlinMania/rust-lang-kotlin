#ifndef STD_SYS_SYNC_THREADPARKING_DARWIN_SYS_SYNC_THREADPARKING_DARWIN_WRAPPER_H
#define STD_SYS_SYNC_THREADPARKING_DARWIN_SYS_SYNC_THREADPARKING_DARWIN_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint64_t stdSysSyncThreadparkingDarwinDispatchTime(uint64_t when, int64_t delta);
void* stdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(int64_t val);
int64_t stdSysSyncThreadparkingDarwinDispatchSemaphoreWait(void* dsema, uint64_t timeout);
int64_t stdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(void* dsema);
void stdSysSyncThreadparkingDarwinDispatchRelease(void* object);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_SYNC_THREADPARKING_DARWIN_SYS_SYNC_THREADPARKING_DARWIN_WRAPPER_H */

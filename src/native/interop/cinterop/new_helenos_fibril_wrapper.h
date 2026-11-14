#ifndef LIBC_NEW_HELENOS_FIBRIL_NEW_HELENOS_FIBRIL_WRAPPER_H
#define LIBC_NEW_HELENOS_FIBRIL_NEW_HELENOS_FIBRIL_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* libcNewHelenosFibrilFibrilCreateGeneric(void* func, void* arg, uint64_t stacksize);
void libcNewHelenosFibrilFibrilStart(void* f);
void libcNewHelenosFibrilFibrilExit(int64_t retval);
void libcNewHelenosFibrilFibrilDetach(void* f);
void libcNewHelenosFibrilFibrilYield(void);
void libcNewHelenosFibrilFibrilUsleep(int64_t usec);
void* libcNewHelenosFibrilFibrilGetId(void);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_HELENOS_FIBRIL_NEW_HELENOS_FIBRIL_WRAPPER_H */

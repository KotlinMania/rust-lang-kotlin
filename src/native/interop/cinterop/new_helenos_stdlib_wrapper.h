#ifndef LIBC_NEW_HELENOS_STDLIB_NEW_HELENOS_STDLIB_WRAPPER_H
#define LIBC_NEW_HELENOS_STDLIB_NEW_HELENOS_STDLIB_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* libcNewHelenosStdlibMalloc(uint64_t size);
void* libcNewHelenosStdlibCalloc(uint64_t nmemb, uint64_t size);
void* libcNewHelenosStdlibRealloc(void* addr, uint64_t size);
void libcNewHelenosStdlibFree(void* addr);
void* libcNewHelenosStdlibMemalign(uint64_t align, uint64_t size);
int32_t libcNewHelenosStdlibRand(void);
void libcNewHelenosStdlibSrand(uint32_t seed);
void libcNewHelenosStdlibExit(int32_t code);
void libcNewHelenosStdlibAbort(void);
char* libcNewHelenosStdlibGetenv(const char* env);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_HELENOS_STDLIB_NEW_HELENOS_STDLIB_WRAPPER_H */

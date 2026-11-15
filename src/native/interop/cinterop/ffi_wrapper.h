#ifndef CORE_FFI_FFI_WRAPPER_H
#define CORE_FFI_FFI_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* coreFfiMemcpy(void* dest, void* src, uint64_t n);
void* coreFfiMemmove(void* dest, void* src, uint64_t n);
void* coreFfiMemset(void* s, int32_t c, uint64_t n);
int32_t coreFfiMemcmp(void* s1, void* s2, uint64_t n);
int32_t coreFfiBcmp(void* s1, void* s2, uint64_t n);
uint64_t coreFfiStrlen(const char* s);

#ifdef __cplusplus
}
#endif

#endif /* CORE_FFI_FFI_WRAPPER_H */

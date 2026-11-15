#ifndef RUSTLANG_CORE_FFI_CORE_FFI_WRAPPER_H
#define RUSTLANG_CORE_FFI_CORE_FFI_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* rustlangCoreFfiMemcpy(void* dest, void* src, uint64_t n);
void* rustlangCoreFfiMemmove(void* dest, void* src, uint64_t n);
void* rustlangCoreFfiMemset(void* s, int32_t c, uint64_t n);
int32_t rustlangCoreFfiMemcmp(void* s1, void* s2, uint64_t n);
int32_t rustlangCoreFfiBcmp(void* s1, void* s2, uint64_t n);
uint64_t rustlangCoreFfiStrlen(const char* s);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_CORE_FFI_CORE_FFI_WRAPPER_H */

#ifndef RUSTLANG_LIBC_WINDOWS_GNU_LIBC_WINDOWS_GNU_WRAPPER_H
#define RUSTLANG_LIBC_WINDOWS_GNU_LIBC_WINDOWS_GNU_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcWindowsGnuStrcasecmp(const char* s1, const char* s2);
int32_t rustlangLibcWindowsGnuStrncasecmp(const char* s1, const char* s2, uint64_t n);
void* rustlangLibcWindowsGnuWmemchr(void* cx, uint16_t c, uint64_t n);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_WINDOWS_GNU_LIBC_WINDOWS_GNU_WRAPPER_H */

#ifndef RUSTLANG_LIBC_WINDOWS_MSVC_LIBC_WINDOWS_MSVC_WRAPPER_H
#define RUSTLANG_LIBC_WINDOWS_MSVC_LIBC_WINDOWS_MSVC_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcWindowsMsvcStricmp(const char* s1, const char* s2);
int32_t rustlangLibcWindowsMsvcStrnicmp(const char* s1, const char* s2, uint64_t n);
void* rustlangLibcWindowsMsvcMemccpy(void* dest, void* src, int32_t c, uint64_t count);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_WINDOWS_MSVC_LIBC_WINDOWS_MSVC_WRAPPER_H */

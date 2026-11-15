#ifndef RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_GNU_B64_POWERPC64_LIBC_UNIX_LINUXLIKE_LINUX_GNU_B64_POWERPC64_WRAPPER_H
#define RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_GNU_B64_POWERPC64_LIBC_UNIX_LINUXLIKE_LINUX_GNU_B64_POWERPC64_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcUnixLinuxlikeLinuxGnuB64Powerpc64Sysctl(void* name, int32_t namelen, void* oldp, void* oldlenp, void* newp, uint64_t newlen);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuB64Powerpc64Getcontext(void* ucp);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuB64Powerpc64Setcontext(void* ucp);
int32_t rustlangLibcUnixLinuxlikeLinuxGnuB64Powerpc64Swapcontext(void* oucp, void* ucp);
void rustlangLibcUnixLinuxlikeLinuxGnuB64Powerpc64Makecontext(void* ucp, void* func, int32_t argc, int32_t km_vac, int64_t km_va0, int64_t km_va1, int64_t km_va2, int64_t km_va3);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_UNIX_LINUXLIKE_LINUX_GNU_B64_POWERPC64_LIBC_UNIX_LINUXLIKE_LINUX_GNU_B64_POWERPC64_WRAPPER_H */

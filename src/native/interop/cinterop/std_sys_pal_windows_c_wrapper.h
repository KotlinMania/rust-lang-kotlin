#ifndef RUSTLANG_STD_SYS_PAL_WINDOWS_C_STD_SYS_PAL_WINDOWS_C_WRAPPER_H
#define RUSTLANG_STD_SYS_PAL_WINDOWS_C_STD_SYS_PAL_WINDOWS_C_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangStdSysPalWindowsCProcessPrng(void* pbdata, uint64_t cbdata);
int32_t rustlangStdSysPalWindowsCNtCreateNamedPipeFile(void* filehandle, uint32_t desiredaccess, void* objectattributes, void* iostatusblock, uint32_t shareaccess, uint32_t createdisposition, uint32_t createoptions, uint32_t namedpipetype, uint32_t readmode, uint32_t completionmode, uint32_t maximuminstances, uint32_t inboundquota, uint32_t outboundquota, void* defaulttimeout);
int32_t rustlangStdSysPalWindowsCAtexit(void* cb);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_PAL_WINDOWS_C_STD_SYS_PAL_WINDOWS_C_WRAPPER_H */
